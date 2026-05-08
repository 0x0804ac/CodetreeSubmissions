import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Scanner;

class Marble implements Comparable<Marble> {
    public static final char[] DIR = new char[] { 'U', 'D', 'L', 'R' };
    public static final double[] DX = new double[] { 0.0, 0.0, -0.5, 0.5 };
    public static final double[] DY = new double[] { 0.5, -0.5, 0.0, 0.0 };
    public static final double BOUND = 1000.0;
    public static final long NEGATIVE_X = 1_0000_0_0_0000_0L;
    public static final long VALUE_X = 1_0_0000_0L;
    public static final long NEGATIVE_Y = 1_0000_0L;
    public static final long VALUE_Y = 1L;

    private Board bd;
    private int id;
    private double x;
    private double y;
    private double dx;
    private double dy;
    private int w;

    public Marble(Board board, int id, double x, double y, char direction, int weight) {
        bd = board;
        this.id = id;
        this.x = x;
        this.y = y;
        w = weight;
        dx = 0;
        dy = 0;
        for(int i=0; i<4; i++) {
            if(direction == DIR[i]) {
                dx = DX[i];
                dy = DY[i];
                break;
            }
        }
    }

    public Board getBoard() { return bd; }
    public int getId() { return id; }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getDx() { return dx; }
    public double getDy() { return dy; }
    public int getWeight() { return w; }

    //충돌이 발생할 수 있는 구역의 바깥쪽에 있는지 검사
    public boolean isOutOfBounds() {
        if(x < -BOUND || y < -BOUND) return true;
        return x > BOUND || y > BOUND;
    }

    //좌표값을 하나의 long 값(A_XXXX_x_B_YYYY_y)으로 변환
    //예시: (-21.5, 55.5) -> 1_0021_5_0_0055_5
    public long coordsToLong() {
        long output = 0L;
        long xl = ((long) (10.0 * Math.abs(x))) * VALUE_X;
        long yl = ((long) (10.0 * Math.abs(y))) * VALUE_Y;
        if(x < 0) output += NEGATIVE_X;
        if(y < 0) output += NEGATIVE_Y;
        return output + xl + yl;
    }

    //1초 동안 움직임
    public void move() {
        x += dx;
        y += dy;
    }

    //우선순위가 높은 구슬: 더 무거움, 무게가 같은 경우 번호가 큼
    @Override
    public int compareTo(Marble other) {
        if(w != other.w) return Integer.compare(other.w, w);
        else return Integer.compare(other.id, id);
    }
}

class Board {
    private LinkedList<Marble> marbleList;
    private HashMap<Long, Marble> boardMap;

    public Board() {
        marbleList = new LinkedList<Marble>();
        boardMap = new HashMap<Long, Marble>(140);
    }

    //새로운 구슬 정보 추가
    public void addMarble(int id, int x, int y, char direction, int weight) {
        marbleList.add(new Marble(this, id, 0.0 + x, 0.0 + y, direction, weight));
    }

    //남아있는 구슬이 없는지 검사
    public boolean isEmpty() {
        return marbleList.isEmpty();
    }

    //구슬 전체 삭제
    public void clear() {
        marbleList.clear();
    }

    //1초 동안 진행 후 충돌 여부 반환
    public boolean tick() {
        long key;
        double x, y;
        for(Marble m : marbleList) m.move(); //리스트의 모든 구슬을 옮김
        marbleList.removeIf(m -> m.isOutOfBounds()); //범위 밖으로 벗어난 구슬 제거
        if(marbleList.isEmpty()) return false; //구슬이 모두 제거된 경우 종료
        int size = marbleList.size();
        while(!marbleList.isEmpty()) {
            Marble m = marbleList.pollLast();
            key = m.coordsToLong();
            //같은 위치에 구슬이 없으면 등록, 더 낮은 우선순위의 구슬이 있다면 덮어씀
            if(!boardMap.containsKey(key) || boardMap.get(key).compareTo(m) > 0) boardMap.put(key, m);
        }
        //등록 결과를 리스트에 옮김
        for(Entry<Long, Marble> e : boardMap.entrySet()) {
            marbleList.add(e.getValue());
        }
        boardMap.clear();
        return size != marbleList.size(); //충돌하여 구슬 개수가 변했는지 확인
    }
}

public class Main {
    public static final String SPACE = " ";

    public static void main(String[] args) throws IOException, NumberFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine()); //테스트 케이스 수
        Board board = new Board();
        int i, j, n, w, x, y, sec;
        char d;
        for(i=0; i<t; i++) {
            n = Integer.parseInt(br.readLine()); //구슬 개수
            for(j=1; j<=n; j++) {
                String[] line = br.readLine().split(SPACE);
                x = Integer.parseInt(line[0]); //x
                y = Integer.parseInt(line[1]); //y
                w = Integer.parseInt(line[2]); //weight
                d = line[3].charAt(0); //direction
                board.addMarble(j, x, y, d, w);
            }
            sec = -1; //마지막으로 충돌이 일어난 시각
            for(j=1; j<=4000; j++) {
                if(board.tick()) sec = j; //충돌 발생 시각 기록
                if(board.isEmpty()) break; //남은 구슬이 없다면 조기 종료
            }
            System.out.println(sec);
            board.clear(); //다음 테스트 케이스를 위해 초기화
        }
    }
}