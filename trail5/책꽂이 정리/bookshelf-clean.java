import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

class Bookshelf {
    public static final String SPACE = " ";
	private int len; //꽂혀있는 책 수
    private Book first; //첫번째 책
    private Book last; //마지막 책

    public Bookshelf() {
        len = 0;
        first = null;
        last = null;
    }

    public int size() { return len; }
    public Book getFirst() { return first; }
    public Book getLast() { return last; }

    public void setSize(int size) { len = size; }
    public void setFirst(Book book) {
        first = book;
        if(first != null) first.setPrevious(null);
    }
    public void setLast(Book book) {
        last = book;
        if(last != null) last.setNext(null);
    }
    
	//맨 앞에 book 추가
    public void addFirst(Book book) {
    	Book.connect(book, first);
    	if(len == 0) {
    		last = book;
    		book.setNext(null);
    	}
    	first = book;
    	len++;
    }
    
	//맨 앞에 books개의 책 추가
    public void addFirst(Book from, Book to, int books) {
    	Book.connect(to, first);
    	if(len == 0) setLast(to);
    	first = from;
    	len += books;
    }

	//맨 뒤에 book 추가
    public void addLast(Book book) {
        Book.connect(last, book);
        if(len == 0) {
        	first = book;
        	book.setPrevious(null);
        }
        last = book;
        len++;
    }
    
	//맨 뒤에 books개의 책 추가
    public void addLast(Book from, Book to, int books) {
    	Book.connect(last, from);
    	if(len == 0) setFirst(from);
        last = to;
    	len += books;
    }
    
	//첫번째 책 제거 후 반환
    public Book removeFirst() {
    	if(first == null) return null;
    	Book output = first;
    	setFirst(output.getNext());
    	output.setNext(null);
    	len--;
        if(len == 0) last = null;
    	return output;
    }
    
	//마지막 책 제거 후 반환
    public Book removeLast() {
    	if(last == null) return null;
    	Book output = last;
    	setLast(output.getPrevious());
    	output.setPrevious(null);
    	len--;
        if(len == 0) first = null;
    	return output;
    }
    
    public void clear() {
    	len = 0;
    	first = null;
    	last = null;
    }

	//책장 정보 출력
    public void print() {
        System.out.print(len + SPACE);
        for(Book book=first; book!=null; book=book.getNext()) System.out.print(book.getNumber() + SPACE);
        System.out.println();
    }
}

class Book {
    private int num; //책 번호
    private Book prev; //바로 앞 책
    private Book next; //바로 뒤 책

    public Book(int number) {
        num = number;
        prev = null;
        next = null;
    }

    public int getNumber() { return num; }
    public Book getPrevious() { return prev; }
    public Book getNext() { return next; }

    public void setPrevious(Book book) { prev = book; }
    public void setNext(Book book) { next = book; }

	//left와 right를 앞뒤로 연결
    public static void connect(Book left, Book right) {
        if(left == right) return;
        if(left != null) left.next = right;
        if(right != null) right.prev = left;
    }
}

public class Main {
	public static final String SPACE = " ";

    public static void main(String[] args) throws IOException, NumberFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] line = br.readLine().split(SPACE);
        int n = Integer.parseInt(line[0]); //책 수(1 ~ 250_000)
        int k = Integer.parseInt(line[1]); //책장 수(1 ~ 100)
        int q = Integer.parseInt(br.readLine()); //연산 수(1 ~ 250_000)
        Bookshelf[] shelves = new Bookshelf[k + 1];
        Book front = null, back = null;
        int i, j, command;
		//책장 초기화(모든 책이 번호 순서대로 1번 책꽂이에 있음)
        for(i=1; i<=k; i++) shelves[i] = new Bookshelf();
        for(i=1; i<=n; i++) shelves[1].addLast(new Book(i));
		//연산 실행
        while(q-- > 0) {
			line = br.readLine().split(SPACE);
            command = Integer.parseInt(line[0]); //연산(1 ~ 4)
            i = Integer.parseInt(line[1]); //1 ~ n
            j = Integer.parseInt(line[2]); //1 ~ n
            switch(command) {
				//i번 책꽂이의 첫번째 책을 j번 책꽂이의 맨 뒤로 이동
                case 1:
                front = shelves[i].getFirst();
				//책이 없거나 목적지가 제자리일 경우 아무것도 안 함
                if(front != null && front != shelves[j].getLast()) {
                    front = shelves[i].removeFirst();
                    shelves[j].addLast(front);
                }
                break;
				//i번 책꽂이의 마지막 책을 j번 책꽂이의 맨 앞으로 이동
                case 2:
                back = shelves[i].getLast();
				//책이 없거나 목적지가 제자리일 경우 아무것도 안 함
                if(back != null && back != shelves[j].getFirst()) {
                    back = shelves[i].removeLast();
                    shelves[j].addFirst(back);
                }
                break;
				//i번 책꽂이의 모든 책을 j번 책꽂이의 맨 앞으로 이동(순서 유지)
                case 3:
				//책이 없거나 목적지가 제자리일 경우 아무것도 안 함
                if(i != j && shelves[i].size() > 0) {
                    front = shelves[i].getFirst();
                    back = shelves[i].getLast();
                    if(front == back) shelves[j].addFirst(front);
                    else shelves[j].addFirst(front, back, shelves[i].size());
                    shelves[i].clear();
                }
                break;
				//i번 책꽂이의 모든 책을 j번 책꽂이의 맨 뒤로 이동(순서 유지)
                case 4:
				//책이 없거나 목적지가 제자리일 경우 아무것도 안 함
                if(i != j && shelves[i].size() > 0) {
                    front = shelves[i].getFirst();
                    back = shelves[i].getLast();
                    if(front == back) shelves[j].addLast(back);
                    else shelves[j].addLast(front, back, shelves[i].size());
                    shelves[i].clear();
                }
                break;
            }
        }
        for(i=1; i<=k; i++) shelves[i].print(); //모든 연산 실행 이후 결과 출력
    }
}