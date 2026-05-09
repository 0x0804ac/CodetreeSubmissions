import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static final char EMPTY = '.';
    public static final char START = 'S';
    public static final char END = 'E';
    public static final int MIN_COINS = 3; //최소 3개의 동전을 수집해야 함
    public static final int MULTIPLY_X = 100;
    public static final int MULTIPLY_Y = 1;

    //시작점, 놓여있는 동전과 도착점이 들어있는 리스트
    public static ArrayList<Character> waypoints = new ArrayList<Character>(10);
    //key: 점, value: 위치 (x, y)에 대응하는 int 값(x * 100 + y)
    public static HashMap<Character, Integer> gridMap = new HashMap<Character, Integer>(20);
    //동전 수집할 때 사용하는 임시 리스트
    public static LinkedList<Character> tempList = new LinkedList<Character>();
    //최단 경로의 길이
    public static int output = Integer.MAX_VALUE;
    //격자의 크기(2 ~ 20)
    public static int N = 20;

    public static void debug() {
        int count = 0;
        for(char waypoint : tempList) {
            int value = gridMap.get(waypoint);
            System.out.print(waypoint + "(" + (value / MULTIPLY_X) + ", " + (value % MULTIPLY_X) + ") - ");
            if(waypoint != START && waypoint != END) count++;
        }
        System.out.println(count + " coins");
    }

    //좌표 (x, y) -> int 값 (x * 100 + y)로 변환(예시: (3, 2) -> 302)
    public static int mapToInt(int x, int y) {
        return x * MULTIPLY_X + y * MULTIPLY_Y;
    }

    //초기화
    public static void init(String[] board) {
        N = board.length;
        int i, j;
        //빈 칸을 제외하고 기록
        for(i=0; i<N; i++) {
            for(j=0; j<N; j++) {
                char ch = board[i].charAt(j);
                if(ch == EMPTY) continue;
                gridMap.put(ch, mapToInt(i, j));
                if(ch != START && ch != END) waypoints.add(ch);
            }
        }
        waypoints.sort(null); //동전 번호 순 정렬
        waypoints.add(0, START); //맨 앞에 시작점 기록
        waypoints.add(END); //맨 뒤에 도착점 기록
        //for(char waypoint : waypoints) System.out.print(waypoint + ":" + gridMap.get(waypoint) + " ");
        //System.out.println();
    }

    //from에서 to까지의 최단 경로 길이
    public static int getDistance(char from, char to) {
        int start = gridMap.get(from), end = gridMap.get(to);
        int x = start / MULTIPLY_X - end / MULTIPLY_X, y = start % MULTIPLY_X - end % MULTIPLY_X;
        return Math.abs(x) + Math.abs(y);
    }

    //완성된 경로의 길이 계산
    public static void calc() {
        tempList.addFirst(START); //시작점 추가
        tempList.addLast(END); //도착점 추가
        //debug();
        char prev = EMPTY;
        int distance = 0;
        for(char waypoint : tempList) {
            if(prev != EMPTY) distance += getDistance(prev, waypoint);
            prev = waypoint;
        }
        tempList.pollFirst(); //시작점 제거
        tempList.pollLast(); //도착점 제거
        output = Math.min(output, distance); //최솟값 계산
    }

    //경로의 index번째 동전 탐색
    public static void func(int index) {
        if(tempList.size() == MIN_COINS) calc(); //동전 수집 완료: 경로 길이 계산
        //index번째 동전이 존재할 경우
        else if(index < waypoints.size() - 1) {
            tempList.add(waypoints.get(index));
            func(index + 1); //동전을 추가하고 다음 동전으로
            tempList.pollLast();
            func(index + 1); //동전을 추가하지 않고 다음 동전으로
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        String[] grid = new String[N];
        for(int i=0; i<N; i++) grid[i] = sc.next();
        init(grid);
        func(1);
        System.out.println(output == Integer.MAX_VALUE ? -1 : output);
    }
}