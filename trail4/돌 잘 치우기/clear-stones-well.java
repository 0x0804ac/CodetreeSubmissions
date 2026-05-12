import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

class Tile {
    private int r;
    private int c;
    private boolean s;
    private boolean v;

    public Tile(int row, int column, boolean stone) {
        r = row; //격자에서의 행
        c = column; //격자에서의 열
        s = stone; //돌 여부
        v = false; //BFS 방문 여부
    }

    public int getRow() { return r; }
    public int getColumn() { return c; }
    public boolean isStone() { return s; }
    public boolean isVisited() { return v; }

    public void setStone(boolean value) { s = value; }
    public void setVisited(boolean value) { v = value; }
}

class Grid {
    public static final int[] DX = {1, 0, -1, 0};
    public static final int[] DY = {0, 1, 0, -1};
    public static final int MAX_STONES = 8;

    private int len;
    private Tile[][] g;
    private LinkedList<Tile> q; //BFS 큐
    private ArrayList<Tile> stones; //초기 입력으로 주어진 돌 리스트
    private ArrayList<Tile> start; //시작점 리스트
    private int output; //도달 가능한 칸 수의 최댓값

    public Grid(int gridSize, int startPoints) {
        len = gridSize;
        g = new Tile[len][len];
        q = new LinkedList<Tile>();
        stones = new ArrayList<Tile>(MAX_STONES);
        start = new ArrayList<Tile>(startPoints);
        output = 0;
    }

    //index가 (0 ~ len - 1) 범위 내에 있는지 검사
    public boolean isValidIndex(int index) {
        if(index < 0) return false;
        return index < len;
    }

    public Tile getTile(int row, int column) {
        if(isValidIndex(row) && isValidIndex(column)) return g[row][column];
        else return null;
    }

    public void setTile(int row, int column, boolean value) {
        if(isValidIndex(row) && isValidIndex(column)) {
            Tile tile = new Tile(row, column, value);
            g[row][column] = tile;
            if(value) stones.add(tile); //돌일 경우 돌 리스트에 추가
        }
    }

    //(row, column) 위치를 시작점으로 추가
    public void addStartPoint(int row, int column) {
        if(isValidIndex(row) && isValidIndex(column) && !g[row][column].isStone()) start.add(g[row][column]);
    }
    
    //count개의 돌을 치웠을 때 방문할 수 있는 칸의 수 최댓값 출력
    public void clearStones(int count) {
        removeStones(0, count);
        System.out.println(output);
    }

    //stones[index]를 치울지 판단
    private void removeStones(int index, int count) {
        //다 치웠으면 BFS 이후 최댓값 갱신
        if(count == 0) {
            output = Math.max(output, bfs());
            return;
        }
        if(index < stones.size()) {
            //stones[index]를 치우고 다음으로 진행
            stones.get(index).setStone(false);
            removeStones(index + 1, count - 1);
            stones.get(index).setStone(true);
            //stones[index]를 치우지 않고 다음으로 진행
            removeStones(index + 1, count);
        }
    }

    //BFS를 통해 방문되는 타일의 개수
    private int bfs() {
        int result = 0;
        //시작점들을 큐에 추가
        for(Tile t : start) {
            t.setVisited(true);
            q.add(t);
            result++;
        }
        //BFS를 통해 방문되지 않은 돌이 아닌 타일로 이동
        while(!q.isEmpty()) {
            Tile next = q.poll();
            for(int i=0; i<4; i++) {
                int x = next.getRow() + DX[i];
                int y = next.getColumn() + DY[i];
                if(isValidIndex(x) && isValidIndex(y) && !g[x][y].isVisited() && !g[x][y].isStone()) {
                    g[x][y].setVisited(true);
                    q.add(g[x][y]);
                    result++;
                }
            }
        }
        //다음 BFS를 위해 방문 정보 초기화
        for(Tile[] line : g) {
            for(Tile t : line) t.setVisited(false);
        }
        return result;
    }
}

public class Main {
    public static final int STONE = 1;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //격자 크기(3 ~ 100)
        int k = sc.nextInt(); //시작점 개수(1 ~ n * n)
        int m = sc.nextInt(); //치워야 할 돌의 개수(0 ~ min(입력된 돌의 개수, 8))
        Grid grid = new Grid(n, k);
        int i, j;
        for(i=0; i<n; i++) {
            for(j=0; j<n; j++) grid.setTile(i, j, sc.nextInt() == STONE);
        }
        for(i=0; i<k; i++) {
            int x = sc.nextInt() - 1;
            int y = sc.nextInt() - 1;
            grid.addStartPoint(x, y);
        }
        grid.clearStones(m);
    }
}