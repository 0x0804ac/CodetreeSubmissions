import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

class Tile {
    private int r;
    private int c;
    private boolean w;
    private int d;

    public Tile(int row, int column, boolean wall) {
        r = row;
        c = column;
        w = wall;
        d = Integer.MAX_VALUE;
    }

    public int getRow() { return r; }
    public int getColumn() { return c; }
    public boolean isWall() { return w; }
    public int getDistance() { return d; }

    public void setWall(boolean value) { w = value; }
    public void setDistance(int value) { d = value; }
}

class Grid {
    public static final int[] DX = {1, 0, -1, 0};
    public static final int[] DY = {0, 1, 0, -1};
    public static final int INF = 1_111_111_111;

    private int len;
    private Tile[][] g;
    private LinkedList<Tile> q;
    private ArrayList<Tile> w;
    private Tile s;
    private Tile e;
    private int output;

    public Grid(int size) {
        len = size;
        g = new Tile[len][len];
        q = new LinkedList<Tile>();
        w = new ArrayList<Tile>();
        s = null;
        e = null;
        output = INF;
    }

    //index가 (0 ~ len - 1) 범위에 있는지 검사
    public boolean isValidIndex(int index) {
        if(index < 0) return false;
        return index < len;
    }

    public void setTile(int row, int column, boolean wall) {
        if(isValidIndex(row) && isValidIndex(column)) {
            g[row][column] = new Tile(row, column, wall);
            if(wall) w.add(g[row][column]);
        }
    }

    public void setStartPoint(int row, int column) {
        if(isValidIndex(row) && isValidIndex(column) && !g[row][column].isWall()) s = g[row][column];
    }

    public void setEndPoint(int row, int column) {
        if(isValidIndex(row) && isValidIndex(column) && !g[row][column].isWall()) e = g[row][column];
    }

    //moves개의 벽을 없애고 최단 거리 출력
    public void move(int moves) {
        removeWall(0, moves); //w[0]부터 하나씩 없애보기
        System.out.println(output == INF ? -1 : output); //최단 거리(탈출 경로가 없을 경우 -1) 출력
    }

    //w[index] 없애보기(walls개의 벽을 더 없애야 함)
    private void removeWall(int index, int walls) {
        //없앨 벽이 더 없다면 BFS로 최단 경로 탐색
        if(walls == 0) {
            bfs();
            return;
        }
        if(index == w.size()) return;
        Tile t = w.get(index);
        t.setWall(false);
        removeWall(index + 1, walls - 1);
        t.setWall(true);
        removeWall(index + 1, walls);
    }

    private void bfs() {
        int i, j;
        s.setDistance(0);
        q.add(s);
        while(!q.isEmpty()) {
            Tile t = q.poll();
            for(i=0; i<4; i++) {
                int x = t.getRow() + DX[i];
                int y = t.getColumn() + DY[i];
                if(isValidIndex(x) && isValidIndex(y) && !g[x][y].isWall()) {
                    if(g[x][y].getDistance() == INF || g[x][y].getDistance() > t.getDistance() + 1) {
                        g[x][y].setDistance(t.getDistance() + 1);
                        q.add(g[x][y]);
                    }
                }
            }
        }
        output = Math.min(output, e.getDistance());
        for(i=0; i<len; i++) {
            for(j=0; j<len; j++) g[i][j].setDistance(INF);
        }
    }
}

public class Main {
    public static final int WALL = 1;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //격자 크기(3 ~ 100)
        int k = sc.nextInt(); //없앨 벽의 수(0 ~ 초기 입력 벽의 수(최대 8))
        Grid grid = new Grid(n);
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) grid.setTile(i, j, sc.nextInt() == WALL); //벽이라면 1, 아니면 0
        }
        grid.setStartPoint(sc.nextInt() - 1, sc.nextInt() - 1); //시작점 행과 열(1 ~ n)
        grid.setEndPoint(sc.nextInt() - 1, sc.nextInt() - 1); //도착점 행과 열(1 ~ n)
        grid.move(k);
    }
}