import java.util.LinkedList;
import java.util.Scanner;

class Tile {
    private boolean s; //뱀 여부
    private int d; //BFS 시작점으로부터의 최단거리

    public Tile(boolean snake) {
        s = snake;
        d = Integer.MAX_VALUE;
    }

    public boolean isSnakeTile() { return s; }
    public int getDistance() { return d; }

    public void setDistance(int value) { d = value; }
}

class Grid {
    public static final int[] DX = {1, 0, -1, 0};
    public static final int[] DY = {0, 1, 0, -1};

    private int r; //열 수
    private int c; //행 수
    private Tile[][] g; //격자
    private LinkedList<int[]> q; //BFS 큐

    public Grid(int row, int column) {
        r = row;
        c = column;
        g = new Tile[r][c];
        q = new LinkedList<int[]>();
    }

    //(row, column)이 격자 내부에 있는지 검사
    public boolean isInside(int row, int column) {
        if(row < 0 || column < 0) return false;
        return row < r && column < c;
    }

    public void setTile(int row, int column, Tile tile) {
        if(isInside(row, column)) g[row][column] = tile;
    }

    //탈출 여부 탐색 후 최단 거리 출력
    public void escape() {
        bfs(0, 0);
        int output = g[r - 1][c - 1].getDistance();
        System.out.println(output == Integer.MAX_VALUE ? -1 : output);
    }

    private int[] arr(int n1, int n2) {
        return new int[] {n1, n2};
    }

    //(row, column)를 시작점으로 BFS 실행
    private void bfs(int row, int column) {
        g[row][column].setDistance(0);
        q.offer(arr(row, column));
        while(!q.isEmpty()) {
            int[] a = q.poll();
            Tile tile = g[a[0]][a[1]];
            for(int i=0; i<4; i++) {
                int x = a[0] + DX[i];
                int y = a[1] + DY[i];
                if(isInside(x, y)) {
                    Tile nextTile = g[x][y];
                    if(!nextTile.isSnakeTile() && nextTile.getDistance() > tile.getDistance() + 1) {
                        nextTile.setDistance(tile.getDistance() + 1);
                        q.add(arr(x, y));
                    }
                }
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //행 수(2 ~ 100)
        int m = sc.nextInt(); //열 수(2 ~ 100)
        Grid grid = new Grid(n, m);
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                int input = sc.nextInt(); //뱀이 있으면 0, 없으면 1
                grid.setTile(i, j, new Tile(input == 0));
            }
        }
        grid.escape();
    }
}