import java.util.LinkedList;
import java.util.Scanner;

class Grid {
    public static final int[] DX = {1, 0, -1, 0};
    public static final int[] DY = {0, 1, 0, -1};
    public static final int REACHABLE = 0;
    public static final int UNREACHABLE = 1;
    public static final int VISITED = 2;

    private int len;
    private int[][] g;
    private LinkedList<int[]> q; //BFS 큐
    private int output; //도달 가능한 칸 수(시작점 포함)

    public Grid(int size) {
        len = size;
        g = new int[len][len];
        q = new LinkedList<int[]>();
        output = 0;
    }

    //index가 (0 ~ len - 1) 범위에 있는지 검사
    public boolean isValidIndex(int index) {
        if(index < 0) return false;
        return index < len;
    }

    public void setTile(int row, int column, int value) {
        if(isValidIndex(row) && isValidIndex(column)) {
            if(value == REACHABLE) g[row][column] = REACHABLE;
            else g[row][column] = UNREACHABLE;
        }
    }

    //(row, column)을 시작점으로 추가
    public void addStartPosition(int row, int column) {
        if(g[row][column] == REACHABLE) {
            setVisited(row, column, true);
            q.add(arr(row, column));
            output++;
        }
    }

    //BFS를 통해 격자 안에서 갈 수 있는 칸으로 이동
    public void bfs() {
        while(!q.isEmpty()) {
            int[] next = q.poll();
            for(int i=0; i<4; i++) {
                int x = next[0] + DX[i];
                int y = next[1] + DY[i];
                if(isValidIndex(x) && isValidIndex(y) && g[x][y] == REACHABLE) {
                    setVisited(x, y, true);
                    q.add(arr(x, y));
                    output++;
                }
            }
        }
        System.out.println(output);
    }

    private int[] arr(int n1, int n2) {
        return new int[] {n1, n2};
    }

    private void setVisited(int row, int column, boolean value) {
        if(value) {
            if(g[row][column] < VISITED) g[row][column] += VISITED;
        }
        else {
            if(g[row][column] >= VISITED) g[row][column] -= VISITED;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //격자 크기(1 ~ 100)
        int k = sc.nextInt(); //시작점 개수(1 ~ n * n)
        Grid grid = new Grid(n);
        int i, j, x, y;
        for(i=0; i<n; i++) {
            for(j=0; j<n; j++) grid.setTile(i, j, sc.nextInt()); //여기로 이동할 수 있으면 0, 없으면 1
        }
        for(i=0; i<k; i++) {
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
            grid.addStartPosition(x, y);
        }
        grid.bfs();
    }
}