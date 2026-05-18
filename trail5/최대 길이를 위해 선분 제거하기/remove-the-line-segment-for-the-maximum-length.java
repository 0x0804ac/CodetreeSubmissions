import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

class Point implements Comparable<Point> {
    private int pos; //좌표
    private int value; //선분의 시작점: +1, 선분의 끝점: -1

    public Point(int x, boolean isStartPoint) {
        pos = x;
        value = isStartPoint ? 1 : -1;
    }

    public int getPosition() { return pos; }
    public int getValue() { return value; }

	//좌표 우선 비교
    @Override
    public int compareTo(Point other) {
        if(pos == other.pos) return value - other.value;
        else return pos - other.pos;
    }
}

class Segment implements Comparable<Segment> {
    private Point begin; //시작점
    private Point end; //끝점

    public Segment(Point from, Point to) {
        begin = from;
        end = to;
    }

    public Segment(int from, int to) {
        begin = new Point(from, true);
        end = new Point(to, false);
    }

    public Point startPoint() { return begin; }
    public Point endPoint() { return end; }

	//시작점 -> 끝점 순서대로 비교
    @Override
    public int compareTo(Segment other) {
        int value = begin.compareTo(other.begin);
        if(value == 0) return end.compareTo(other.end);
        else return value;
    }
}

public class Main {
    public static void main(String[] args) throws IOException, NumberFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        TreeMap<Segment, Integer> lenMap = new TreeMap<>(); //선분 기록
        TreeMap<Integer, Integer> intMap = new TreeMap<>(); //점 기록
        int n = Integer.parseInt(br.readLine()); //1 ~ 100_000
		int value = 0, output = 0, min = Integer.MAX_VALUE, x1 = 0, x2 = 0;
        int i, len;
		//n개의 선분 입력
        for(i=0; i<n; i++) {
			String[] line = br.readLine().split(" ");
            x1 = Integer.parseInt(line[0]);
            x2 = Integer.parseInt(line[1]);
            lenMap.put(new Segment(x1, x2), 0); //선분 추가
            intMap.put(x1, intMap.getOrDefault(x1, 0) + 1); //시작점(+1) 추가
            intMap.put(x2, intMap.getOrDefault(x2, 0) - 1); //끝점(-1) 추가
        }
        ArrayList<Segment> sorted = new ArrayList<>(lenMap.keySet());
        int[] left = new int[n + 1]; //모든 왼쪽 선분에 대하여 끝점의 최댓값
        int[] right = new int[n + 1]; //모든 오른쪽 선분에 대하여 시작점의 최솟값
		//left 채우기
        for(i=1; i<=n; i++) left[i] = Math.max(left[i - 1], sorted.get(i - 1).endPoint().getPosition());
		//right 채우기
        right[n] = Integer.MAX_VALUE;
        for(i=n-1; i>=0; i--) right[i] = Math.min(right[i + 1], sorted.get(i).startPoint().getPosition());
		//n개의 선분
        for(i=0; i<n; i++) {
            Segment s = sorted.get(i);
            x1 = Math.max(s.startPoint().getPosition(), left[i]);
            x2 = Math.min(s.endPoint().getPosition(), right[i + 1]);
            len = Math.max(0, x2 - x1); //다른 선분과 겹치지 않는 구간의 길이
            if(min > len) min = len;
            lenMap.put(s, len);
        }
        boolean recording = false;
        len = 0;
		//모든 시작점과 끝점에 대하여 탐색
        for(Entry<Integer, Integer> e : intMap.entrySet()) {
            value += e.getValue();
			//시작점
            if(!recording && value > 0) {
                recording = true;
                x1 = e.getKey();
            }
			//끝점(시작점까지의 길이 합산)
            else if(recording && value == 0) {
                x2 = e.getKey();
                len += (x2 - x1);
                recording = false;
            }
        }
        output = len - min; //선분 하나를 제거했을 때 선분이 그어진 영역의 길이의 최댓값
        System.out.println(output);
    }
}