import java.util.LinkedList;
import java.util.Scanner;

class SnakeField {
    public static final int[] DX = {1, 0, -1, 0};
    public static final int[] DY = {0, 1, 0, -1};
    public static final int SNAKE = 0;
    public static final int SAFE = 1;
    public static final int VISITED = 2;

    private int r;
    private int c;
    private int[][] bd;
    private LinkedList<int[]> q; //BFS에 사용되는 큐

    public SnakeField(int row, int column) {
        r = row;
        c = column;
        bd = new int[row][column];
        q = new LinkedList<int[]>();
    }

    //(row, column)이 영역 내부에 있는지 검사
    public boolean isInField(int row, int column) {
        if(row < 0 || column < 0) return false;
        return row < r && column < c;
    }

    public void setSquare(int row, int column, int value) {
        if(isInField(row, column)) {
            if(value == SAFE) bd[row][column] = SAFE;
            else bd[row][column] = SNAKE;
        }
    }

    //(0, 0)에서 (r - 1, c - 1)으로 갈 수 있는지 BFS 탐색 후 출력
    public void escape() {
        int x = 0, y = 0;
        setVisited(x, y, true);
        q.add(arr(x, y));
        while(!q.isEmpty()) {
            int[] next = q.poll();
            for(int i=0; i<4; i++) {
                x = next[0] + DX[i];
                y = next[1] + DY[i];
                //영역 내에서 뱀이 없고 아직 방문하지 않은 칸으로만 이동 가능
                if(isInField(x, y) && bd[x][y] == SAFE) {
                    setVisited(x, y, true);
                    q.add(arr(x, y));
                }
            }
        }
        System.out.println(isVisited(r - 1, c - 1) ? 1 : 0);
    }

    private boolean isVisited(int row, int column) {
        return bd[row][column] >= VISITED;
    }

    private void setVisited(int row, int column, boolean value) {
        if(value) {
            if(bd[row][column] < VISITED) bd[row][column] += VISITED;
        }
        else {
            if(bd[row][column] >= VISITED) bd[row][column] -= VISITED;
        }
    }

    private int[] arr(int n1, int n2) {
        return new int[] {n1, n2};
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //영역 행(2 ~ 100)
        int m = sc.nextInt(); //영역 열(2 ~ 100)
        SnakeField f = new SnakeField(n, m);
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) f.setSquare(i, j, sc.nextInt()); //뱀이 있으면 0, 없으면 1
        }
        f.escape();
    }
}