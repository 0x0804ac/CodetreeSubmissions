import java.util.LinkedList;
import java.util.Scanner;

class Tile implements Comparable<Tile> {
    private int num; //값
    private int r; //격자에서의 행
    private int c; //격자에서의 열
    private boolean visited; //BFS 방문 정보

    public Tile(int row, int column, int value) {
        num = value;
        r = row;
        c = column;
        visited = false;
    }

    public int getValue() { return num; }
    public int getRow() { return r; }
    public int getColumn() { return c; }
    public boolean isVisited() { return visited; }

    public void setVisited(boolean value) { visited = value; }

    //타일 우선 순위: 높은 값 -> 앞 행 -> 앞 열
    @Override
    public int compareTo(Tile other) {
        if(num != other.num) return num - other.num;
        else if(r != other.r) return other.r - r;
        else return other.c - c;
    }

    @Override
    public String toString() {
        return num + "(" + r + "," + c + ")";
    }
}

class Board {
    public static final int[] DX = {1, 0, -1, 0};
    public static final int[] DY = {0, 1, 0, -1};

    private int len;
    private Tile[][] g;
    private LinkedList<Tile> q; //BFS 큐

    public Board(int size) {
        len = size;
        g = new Tile[len + 1][len + 1];
        q = new LinkedList<Tile>();
    }

    //index가 (1 ~ len) 범위에 있는지 검사
    public boolean isValidIndex(int index) {
        if(index <= 0) return false;
        return index <= len;
    }

    public void setTile(int row, int column, int value) {
        if(isValidIndex(row) && isValidIndex(column) && value > 0) {
            g[row][column] = new Tile(row, column, value);
        }
    }

    //(row, column)에서 한 번 이동했을 때 도착 지점(이동하지 못할 경우 null)
    public Tile move(int row, int column) {
        if(!isValidIndex(row) || !isValidIndex(column)) return null;
        Tile destination = null;
        g[row][column].setVisited(true);
        q.add(g[row][column]);
        //BFS를 통해 출발지보다 작은 값의 타일로 이동하면서 최댓값 저장
        while(!q.isEmpty()) {
            Tile next = q.poll();
            for(int i=0; i<4; i++) {
                int x = next.getRow() + DX[i];
                int y = next.getColumn() + DY[i];
                if(isValidIndex(x) && isValidIndex(y) && !g[x][y].isVisited() && g[x][y].getValue() < g[row][column].getValue()) {
                    g[x][y].setVisited(true);
                    q.add(g[x][y]);
                    if(destination == null || destination.compareTo(g[x][y]) < 0) destination = g[x][y];
                }
            }
        }
        //다음 BFS를 위해 초기화
        for(Tile[] line : g) {
            for(Tile t : line) {
                if(t != null) t.setVisited(false);
            }
        }
        return destination;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //격자 크기(1 ~ 100)
        int k = sc.nextInt(); //이동 횟수(1 ~ 100)
        Board b = new Board(n);
        int i, j;
        for(i=1; i<=n; i++) {
            for(j=1; j<=n; j++) b.setTile(i, j, sc.nextInt()); //타일 값: 1 ~ 100
        }
        int r = sc.nextInt(); //시작 위치 행(1 ~ n)
        int c = sc.nextInt(); //시작 위치 열(1 ~ n)
        for(i=0; i<k; i++) {
            Tile result = b.move(r, c);
            if(result == null) break;
            r = result.getRow();
            c = result.getColumn();
        }
        System.out.println(r + " " + c); //최종 목적지 행과 열(1 ~ n)
    }
}