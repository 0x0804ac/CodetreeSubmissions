import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

class Point implements Comparable<Point> {
    private int x;
    private int y;
    
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    //x좌표 -> y좌표 순서대로 정렬
    @Override
    public int compareTo(Point other) {
        if(x == other.x) return y - other.y;
        else return x - other.x;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

public class Main {
    public static void main(String[] args) throws IOException, NumberFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        TreeSet<Point> set = new TreeSet<>();
        int[] area = new int[4]; //각 영역에 속한 점의 수(영역을 나누는 기준 좌표는 짝수)
        int n = Integer.parseInt(br.readLine()); //1 ~ 1000
        int output = n; //4개의 영역 중 가장 많이 속한 영역의 점 개수의 최솟값
        int i, x, y;
        //n개의 점 추가
        for(i=0; i<n; i++) {
            String[] line = br.readLine().split(" ");
            set.add(new Point(Integer.parseInt(line[0]), Integer.parseInt(line[1]))); //1 ~ 1000(홀수)
        }
        ArrayList<Point> list = new ArrayList<>(set);
        //먼저 기준 y를 결정
        for(y=2; y<1000; y+=2) {
            for(i=0; i<4; i++) area[i] = 0; //area 초기화
            //기준 x가 0일 때
            for(Point p : list) {
                if(p.getY() > y) area[0]++; //↗ 방향 영역
                else area[3]++; //↘ 방향 영역
            }
            //기준 x를 증가시키면서 영역 이동
            for(i=0; i<n; i++) {
                if(i == 0 || list.get(i).getX() != list.get(i - 1).getX()) {
                    output = Math.min(output, Math.max(
                        Math.max(area[0], area[1]), Math.max(area[2], area[3])
                    ));
                }
                if(list.get(i).getY() > y) {
                    //↗ 방향 영역에서 ↖ 방향 영역으로 이동
                    area[0]--;
                    area[1]++;
                }
                else {
                    //↘ 방향 영역에서 ↙ 방향 영역으로 이동
                    area[3]--;
                    area[2]++;
                }
            }
        }
        System.out.println(output);
    }
}