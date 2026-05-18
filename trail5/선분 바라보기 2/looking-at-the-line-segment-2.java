import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.TreeSet;

class Point {
	private int x; //x좌표
	private int y; //y좌표
	private int value; //선분의 시작점: +1, 선분의 끝점: -1
	private int id; //선분 id
	
	public Point(int x, int y, boolean isStartPoint, int id) {
		this.x = x;
		this.y = y;
		value = isStartPoint ? 1 : -1;
		this.id = id;
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	public int getValue() { return value; }
	public int getId() { return id; }
}

public class Main {
	public static void main(String[] args) throws IOException, NumberFormatException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine()); //1 ~ 50_000
		TreeSet<Point> pointSet = new TreeSet<Point>(Comparator.comparingInt(Point::getX)); //x좌표 기준 정렬
		boolean[] visible = new boolean[N]; //visible[i]: id가 i인 선분이 아래쪽(-y)에서 보이는지 여부
		int i, x1, x2, y, value;
        //N개의 선분(겹치는 선분이 없음)
		for(i=0; i<N; i++) {
            String[] line = br.readLine().split(" ");
			y = Integer.parseInt(line[0]); //-1_000_000_000 ~ 1_000_000_000
			x1 = Integer.parseInt(line[1]); //-1_000_000_000 ~ 1_000_000_000
			x2 = Integer.parseInt(line[2]); //-1_000_000_000 ~ 1_000_000_000 (x1 < x2)
			pointSet.add(new Point(x1, y, true, i)); //선분 i의 시작점 등록
			pointSet.add(new Point(x2, y, false, i)); //선분 i의 끝점 등록
		}
		TreeSet<Point> set = new TreeSet<>(Comparator.comparingInt(Point::getY)); //y좌표 기준 정렬
        //왼쪽 점부터 순회
		for(Point p : pointSet) {
			x1 = p.getX();
			y = p.getY();
			i = p.getId();
			value = p.getValue();
            //시작점
			if(value == 1) {
                //다른 선이 없거나 더 위에 있다면 아래에서 보이는 선으로 표시
				if(set.isEmpty() || set.first().getY() > y) visible[i] = true;
                //다른 선 목록에 추가
				set.add(p);
			}
            //끝점
			else if(value == -1) {
                //다른 선 목록에서 제거
				set.remove(p);
                //바로 위에 있던 선이 있다면 아래에서 보이는 선으로 표시
				if(!set.isEmpty()) visible[set.first().getId()] = true;
				else visible[i] = true;
			}
		}
		int output = 0; //아래에서 보이는 선의 개수
		for(boolean b : visible) {
			if(b) output++;
		}
		System.out.println(output);
    }
}
