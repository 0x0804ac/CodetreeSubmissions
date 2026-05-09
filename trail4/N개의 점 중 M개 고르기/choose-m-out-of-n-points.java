import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

class Point {
    private int id;
    private int x;
    private int y;

    public Point(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getId() { return id; }
    public int getX() { return x; }
    public int getY() { return y; }

    //다른 점과의 거리의 제곱
    public int getDistanceSquared(Point other) {
        return (x - other.x) * (x - other.x) + (y - other.y) * (y - other.y);
    }

    @Override
    public String toString() {
        return id + "(" + x + "," + y + ")";
    }
}

public class Main {
    public static ArrayList<Point> pointList; //점 리스트
    public static ArrayList<int[]> distanceList; //점과 점 사이의 거리 리스트
    public static ArrayList<Integer> tempList; //점 선택 시 사용하는 리스트
    public static int output = Integer.MAX_VALUE;
    public static int m;

    public static void debug() {
        System.out.println(pointList);
        for(int[] distances : distanceList) System.out.println(Arrays.toString(distances));
    }

    //distanceList, tempList 초기화
    public static void init(int pointCount) {
        int i, j;
        distanceList = new ArrayList<int[]>(pointCount);
        //a < b일 경우 distanceList.get(b)[a]로 거리 조회
        for(i=0; i<pointCount; i++) {
            int[] arr = new int[i + 1];
            for(j=0; j<=i; j++) {
                if(i != j) arr[j] = pointList.get(i).getDistanceSquared(pointList.get(j));
            }
            distanceList.add(arr);
        }
        tempList = new ArrayList<Integer>(m);
    }

    //가장 먼 두 점 사이의 거리의 최소값 계산
    public static void calc() {
        int result = 0;
        int distance, i, j;
        //tempList의 서로 다른 두 점에 대하여 거리 확인
        for(i=1; i<m; i++) {
            Point p = pointList.get(tempList.get(i));
            for(j=0; j<i; j++) {
                Point q = pointList.get(tempList.get(j));
                distance = distanceList.get(p.getId())[q.getId()];
                result = Math.max(result, distance);
            }
        }
        //System.out.println(tempList + ": " + result);
        output = Math.min(output, result);
    }
    
    //pointList.get(index) 선택하기
    public static void func(int index) {
        int len = pointList.size();
        //m개가 선택되었다면 최대 거리 확인
        if(tempList.size() == m) {
            calc();
            return;
        }
        if(index >= len) return;
        tempList.add(index);
        func(index + 1); //pointList.get(index)를 선택하고 진행
        tempList.remove(tempList.size() - 1);
        func(index + 1); //pointList.get(index)를 선택하지 않고 진행
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //점 개수(2 ~ 20)
        m = sc.nextInt(); //선택할 점의 개수(2 ~ n)
        pointList = new ArrayList<Point>(n);
        for(int i=0; i<n; i++) pointList.add(new Point(i, sc.nextInt(), sc.nextInt())); //좌표값: 1 ~ 100
        init(n);
        //debug();
        func(0);
        System.out.println(output);
    }
}