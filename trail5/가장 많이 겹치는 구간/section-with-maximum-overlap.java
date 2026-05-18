import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.TreeSet;

class Point implements Comparable<Point> {
    private int pos; //좌표
    private int value; //시작점: +1, 끝점: -1

    public Point(int position, boolean isStartingPoint) {
        pos = position;
        value = isStartingPoint ? 1 : -1;
    }

    public int getPosition() { return pos; }
    public int getValue() { return value; }

    //좌표 기준 정렬
    @Override
    public int compareTo(Point other) {
        return pos - other.pos;
    }

    @Override
    public String toString() {
        if(value == 1) return "( " + pos + "~ )";
        else return "( ~" + pos + " )";
    }
}

public class Main {
    public static void main(String[] args) throws IOException, NumberFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine()); //1 ~ 100_000
        TreeSet<Point> set = new TreeSet<>();
        int i;
        //n개의 구간
        for(i=0; i<n; i++) {
            String[] line = br.readLine().split(" ");
            set.add(new Point(Integer.parseInt(line[0]), true)); //구간 시작(1 ~ 200_000)
            set.add(new Point(Integer.parseInt(line[1]), false)); //구간 끝(1 ~ 200_000)
        }
        int output = 0;
        i = 0;
        //값의 중간합이 그 순간 겹치는 구간의 수가 됨
        for(Point p : set) {
            i += p.getValue();
            if(i > output) output = i;
        }
        System.out.println(output); //가장 많이 겹치는 구간 수 출력
    }
}