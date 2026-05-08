import java.util.LinkedList;
import java.util.Scanner;

class Marble implements Comparable<Marble> {
    public static final char[] DIR = new char[] { 'U', 'D', 'L', 'R' };
    public static final int[] DX = new int[] { -1, 1, 0, 0 };
    public static final int[] DY = new int[] { 0, 0, -1, 1 };

    private Board bd;
    private int id;
    private int w;
    private int x;
    private int y;
    private int dx;
    private int dy;

    public Marble(Board board, int id, int row, int column, char direction, int weight) {
        bd = board;
        this.id = id;
        w = weight;
        x = row;
        y = column;
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

    public int getId() { return id; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWeight() { return w; }
    public void setWeight(int weight) { w = weight; }

    //보드 안에 있는지 검사
    public boolean isInsideBoard() {
        int len = bd.size();
        if(x < 0 || y < 0) return false;
        return x < len && y < len;
    }

    //방향 전환
    public void changeDirection() {
        dx = -dx;
        dy = -dy;
    }

    //1초 동안 움직임
    public void move() {
        x += dx;
        y += dy;
        //벽에 부딪히면 방향 전환
        if(!isInsideBoard()) {
            changeDirection();
            x += dx;
            y += dy;
        }
    }

    //우선순위가 높은 구슬: 번호가 높음
    @Override
    public int compareTo(Marble other) {
        return Integer.compare(id, other.id);
    }

    //2개의 구슬 합치기
    public static Marble combine(Marble m1, Marble m2) {
        Marble output;
        if(m1.compareTo(m2) > 0) output = m1;
        else output = m2;
        output.setWeight(m1.getWeight() + m2.getWeight());
        return output;
    }
}

class Board {
    private int len;
    private Marble[][] grid;
    private Marble[][] temp;

    public Board(int size) {
        len = size;
        grid = new Marble[len][len];
        temp = new Marble[len][len];
    }

    public int size() { return len; }

    //index가 (0 ~ len - 1) 범위에 있는지 검사
    public boolean isValidIndex(int index) {
        if(index < 0) return false;
        return index < len;
    }

    public Marble getMarble(int x, int y) {
        if(isValidIndex(x) && isValidIndex(y)) return grid[x][y];
        else return null;
    }

    public void setMarble(int x, int y, Marble marble) {
        if(isValidIndex(x) && isValidIndex(y)) grid[x][y] = marble;
    }

    //1초 동안 움직임
    public void tick() {
        int i, j, x, y;
        //임시 격자 초기화
        for(i=0; i<len; i++) {
            for(j=0; j<len; j++) temp[i][j] = null;
        }
        //한 칸씩 처리
        for(i=0; i<len; i++) {
            for(j=0; j<len; j++) {
                Marble marble = grid[i][j];
                if(marble == null) continue;
                marble.move();
                x = marble.getX();
                y = marble.getY();
                if(temp[x][y] == null) temp[x][y] = marble;
                else temp[x][y] = Marble.combine(temp[x][y], marble); //구슬 충돌
            }
        }
        //이동 결과를 격자에 저장
        for(i=0; i<len; i++) System.arraycopy(temp[i], 0, grid[i], 0, len);
    }

    public void print() {
        int count = 0, output = 0;
        for(Marble[] line : grid) {
            for(Marble m : line) {
                if(m != null) {
                    count++;
                    output = Math.max(output, m.getWeight());
                }
            }
        }
        System.out.println(count + " " + output);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int t = sc.nextInt();
        Board board = new Board(n);
        int i, r, c, w;
        char d;
        for(i=1; i<=m; i++) {
            r = sc.nextInt() - 1;
            c = sc.nextInt() - 1;
            d = sc.next().charAt(0);
            w = sc.nextInt();
            Marble marble = new Marble(board, i, r, c, d, w);
            board.setMarble(r, c, marble);
        }
        for(i=0; i<t; i++) board.tick();
        board.print();
    }
}