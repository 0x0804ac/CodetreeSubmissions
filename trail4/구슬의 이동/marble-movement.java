import java.util.ArrayList;
import java.util.Scanner;

class Marble implements Comparable<Marble> {
    public static final char[] DIR = new char[] { 'U', 'D', 'L', 'R' };
    public static final int[] DX = new int[] { -1, 1, 0, 0 };
    public static final int[] DY = new int[] { 0, 0, -1, 1 };

    private Board bd;
    private int id;
    private int x;
    private int y;
    private int v;
    private char d;

    public Marble(Board board, int id, int x, int y, char direction, int velocity) {
        bd = board;
        this.id = id;
        this.x = x;
        this.y = y;
        v = velocity;
        d = direction;
    }

    public Board getBoard() { return bd; }
    public int getId() { return id; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getVelocity() { return v; }
    public char getDirection() { return d; }

    //방향 전환 후 반환
    private int reverse(int direction) {
        if(direction >= 0 && direction < 4) {
            direction ^= 1;
            d = DIR[direction];
        }
        return direction;
    }

    //구슬을 움직임
    public void move() {
        int index = -1;
        int i, j, nextX, nextY;
        for(i=0; i<v; i++) {
            for(j=0; j<4; j++) {
                if(d == DIR[j]) {
                    index = j;
                    break;
                }
            }
            nextX = x + DX[index];
            nextY = y + DY[index];
            //벽에 부딪히면 반대 방향으로 전환
            if(!bd.isValidIndex(nextX, nextY)) {
                index = reverse(index);
                nextX = x + DX[index];
                nextY = y + DY[index];
            }
            x = nextX;
            y = nextY;
        }
    }

    //우선순위가 높은 구슬: 속도가 빠름, 같을 경우 번호가 큼
    @Override
    public int compareTo(Marble other) {
        if(v != other.v) return Integer.compare(other.v, v);
        else return Integer.compare(other.id, id);
    }
}

class Board {
    private ArrayList<Marble>[] grid;
    private ArrayList<Marble> temp;
    private int len;
    private int cap;

    public Board(int size, int limit) {
        len = size;
        grid = new ArrayList[len * len];
        for(int i=0; i<grid.length; i++) grid[i] = new ArrayList<Marble>(len);
        temp = new ArrayList<Marble>(len * len);
        cap = limit;
    }

    //grid의 (row, column) 위치에 새 구슬 추가
    public void addMarble(int id, int row, int column, char direction, int velocity) {
        grid[row * len + column].add(new Marble(this, id, row, column, direction, velocity));
    }

    //grid에 기존 구슬 추가
    public void addMarble(Marble marble) {
        grid[marble.getX() * len + marble.getY()].add(marble);
    }

    //(x, y)가 grid 내부에 있는지 검사
    public boolean isValidIndex(int x, int y) {
        if(x < 0 || y < 0) return false;
        return x < len && y < len;
    }
    
    //grid의 (x, y) 칸 반환
    public ArrayList<Marble> get(int x, int y) {
        if(isValidIndex(x, y)) return grid[x * len + y];
        else return null;
    }

    //1초 동안 움직임
    public void tick() {
        //구슬을 임시 리스트에 저장
        for(ArrayList<Marble> d : grid) {
            temp.addAll(d);
            d.clear();
        }
        //구슬 이동 후 원래 리스트로 복귀
        for(Marble m : temp) {
            m.move();
            addMarble(m);
        }
        temp.clear();
        for(ArrayList<Marble> d : grid) {
            d.sort(null);
            int size = d.size();
            while(size > cap) d.remove(--size);
        }
    }

    //남아있는 구슬의 개수
    public int getMarbles() {
        int count = 0;
        for(ArrayList<Marble> d : grid) {
            for(Marble m : d) count++;
        }
        return count;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //격자 크기
        int m = sc.nextInt(); //최초 구슬 개수
        int t = sc.nextInt(); //시간
        int k = sc.nextInt(); //한 칸에 남을 수 있는 최대 구슬 수
        int i, r, c, v;
        char d;
        Board board = new Board(n, k);
        //구슬 생성 및 격자에 추가
        for(i=1; i<=m; i++) {
            r = sc.nextInt(); //행(1 ~ n)
            c = sc.nextInt(); //열(1 ~ n)
            d = sc.next().charAt(0); //초기 속도의 방향
            v = sc.nextInt(); //초기 속력
            board.addMarble(i, r - 1, c - 1, d, v);
        }
        //구슬을 t초 동안 이동시킴
        for(i=0; i<t; i++) board.tick();
        System.out.println(board.getMarbles());
    }
}