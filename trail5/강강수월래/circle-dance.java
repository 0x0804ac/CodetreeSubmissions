import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.TreeMap;

class Student {
	private int num; //num번 학생
	private Circle circle; //속한 원
	private Student left; //왼쪽에 있는 학생
	private Student right; //오른쪽에 있는 학생
	
	public Student(int number) {
		num = number;
		circle = null;
		left = null;
		right = null;
	}
	
	public int getNumber() { return num; }
	public Circle getCircle() {return circle; }
	public Student getLeft() {return left; }
	public Student getRight() { return right; }

	public void setCircle(Circle circle) { this.circle = circle; }
	public void setLeft(Student left) { this.left = left; }
	public void setRight(Student right) { this.right = right; }
	
    //left와 right를 좌우로 연결
	public static void connect(Student left, Student right) {
		if(left == right) return;
		if(left != null) left.right = right;
		if(right != null) right.left = left;
	}
	
    //left와 right 사이의 거리
	public static int distance(Student left, Student right) {
		if(left == null || right == null || left.circle != right.circle) return -1;
		if(left == right) return 0;
		Student st = left;
		int output1 = 0, output2 = 0;
		do {
			output1++;
			st = st.right;
		} while(st != right);
		do {
			output2++;
			st = st.right;
		} while(st != left);
		return Math.min(output1, output2); //두 방향으로 거리를 쟀을 때 더 짧은 결과를 반환
	}
}

class Circle {
	private Student head; //번호가 가장 작은 학생
	
	public Circle() {
		head = null;
	}
	
	public Circle(Student from, Student to) {
		head = from;
		Student st = from;
		if(from != to) {
			while(st.getCircle() != this) {
				st.setCircle(this);
				st = st.getLeft();
			}
		}
	}
	
	public Student getHead() { return head; }

	public void setHead(Student student) {
		if(student.getCircle() == this) head = student;
	}
	
    //원에서 head의 오른쪽에 학생 추가
	public void add(Student student) {
		if(student == null) return;
		student.setCircle(this);
		if(head == null) head = student;
		else {
			Student right = head.getRight();
			if(right == null) {
				Student.connect(head, student);
				Student.connect(student, head);
			}
			else {
				Student.connect(head, student);
				Student.connect(student, right);
			}
		}
	}
	
    //원에서 (newHead ~ end) 범위의 학생을 새로운 원으로 분리
	public void split(Student newHead, Student end) {
		Student newEnd = end.getRight();
		if(newHead.getCircle() != end.getCircle()) {
			Circle.merge(newEnd, newHead);
		}
		else {
			Student st = newHead.getRight();
			Student.connect(newHead, newEnd);
			Student.connect(end, st);
			head = end;
			Circle circle = new Circle(newHead, newEnd);
			circle.head = newHead;
		}
	}
	
    //2개의 원을 합쳐서 newLeft와 newRight가 좌우로 연결되게 함
	public static void merge(Student newRight, Student newLeft) {
		if(newRight == null || newLeft == null) return;
		if(newRight.getLeft() == newLeft) return;
		Student s = newRight.getLeft(), t = newLeft.getRight();
		Circle c = newLeft.getCircle();
		Student.connect(newLeft, newRight);
		Student.connect(s, t);
		if(c == newRight.getCircle()) {
			Student.connect(newLeft, newRight);
			Circle split = new Circle(s, t);
			c.head = newLeft;
			split.head = s;
		}
		else {
			while(s.getCircle() != c) {
				s.setCircle(c);
				s = s.getLeft();
			}
		}
	}

    //원 정보(head부터 반시계 방향으로 학생 번호) 출력
	public void print() {
		if(head == null) return;
		if(head.getRight() == null) System.out.println(head.getNumber());
		else {
			Student st = head;
			do {
				System.out.print(st.getNumber() + " ");
				st = st.getRight();
			} while(st != head);
			System.out.println();
		}
	}
}

public class Main {
    public static final String SPACE = " ";

	public static void main(String[] args) throws IOException, NumberFormatException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(SPACE);
        int N = Integer.parseInt(line[0]); //학생 수(2 ~ 100_000)
        int M = Integer.parseInt(line[1]); //최초 원 수(2 ~ 10)
        int Q = Integer.parseInt(line[2]); //동작 수(1 ~ 10_000)
        //학생 번호가 너무 커서 배열 대신 map 사용
        TreeMap<Integer, Student> map = new TreeMap<Integer, Student>();
        Circle c;
        Student s, t;
        int i, j, A, B;
        //원 입력
        for(i=0; i<M; i++) {
            line = br.readLine().split(SPACE);
        	c = new Circle();
            A = Integer.parseInt(line[0]); //원의 학생 수
            for(j=1; j<=A; j++) {
                B = Integer.parseInt(line[j]); //학생 번호(1 ~ 100_000_000, 중복 없음)
                s = new Student(B);
                map.put(B, s);
                c.add(s);
            }
        }
        //동작 입력
        for(i=0; i<Q; i++) {
            line = br.readLine().split(SPACE);
            j = Integer.parseInt(line[0]); //동작
            A = Integer.parseInt(line[1]);
            s = map.get(A);
            c = s.getCircle();
            switch(j) {
            //A번 학생과 B번 학생이 있는 원 합치기
            case 1:
                B = Integer.parseInt(line[2]);
                t = map.get(B);
                Circle.merge(s, t);
                break;
            //A번 학생과 B번 학생이 속한 원 쪼개기(A번 학생부터 시계 방향으로 B번 학생 직전까지 학생들을 분리)
            case 2:
                B = Integer.parseInt(line[2]);
                t = map.get(B);
                c.split(s, t);
                break;
            //A번 학생이 속한 원 정보(head부터 반시계 방향으로 학생 번호) 출력
            case 3:
            	for(Entry<Integer, Student> entry : map.entrySet()) {
            		t = entry.getValue();
            		if(t.getCircle() == c) {
            			c.setHead(t);
            			break;
            		}
            	}
                c.print();
                break;
            }
        }
	}
}
