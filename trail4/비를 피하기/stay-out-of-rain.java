import java.util.LinkedList;
import java.util.Scanner;

class Grid {
    public static final int[] DX = {1, 0, -1, 0};
    public static final int[] DY = {0, 1, 0, -1};
    public static final int INF = 1_111_111_111;
    public static final int EMPTY = 0;
    public static final int WALL = 1;
    public static final int PERSON = 2;
    public static final int SHELTER = 3;

    private int len;
    private int[][] g; //격자 정보
    private int[][] d; //최단 거리 정보
    private LinkedList<int[]> q; //BFS 큐

    public Grid(int size) {
        len = size;
        g = new int[len][len];
        d = new int[len][len];
        for(int i=0; i<len; i++) {
            for(int j=0; j<len; j++) d[i][j] = INF; //최단 거리 초기화
        }
        q = new LinkedList<int[]>();
    }

    //index가 (0 ~ len - 1) 범위 내에 있는지 검사
    public boolean isValidIndex(int index) {
        if(index < 0) return false;
        return index < len;
    }

    public void setTile(int row, int column, int value) {
        if(isValidIndex(row) && isValidIndex(column)) g[row][column] = value;
    }

    //모든 사람에 대하여 최단 거리 계산 후 출력
    public void move() {
        int[][] output = new int[len][len];
        int i, j;
        for(i=0; i<len; i++) {
            for(j=0; j<len; j++) {
                //한 사람 당 BFS 1회
                if(g[i][j] == PERSON) {
                    output[i][j] = bfs(i, j);
                    resetVisited();
                }
            }
        }
        for(i=0; i<len; i++) {
            for(j=0; j<len; j++) System.out.print(output[i][j] + " ");
            System.out.println();
        }
    }

    private int[] arr(int n1, int n2) {
        return new int[] {n1, n2};
    }

    //BFS 방문 정보 초기화
    private void resetVisited() {
        for(int i=0; i<len; i++) {
            for(int j=0; j<len; j++) d[i][j] = INF;
        }
    }

    //(row, column)을 시작점으로 하는 BFS 실행
    private int bfs(int row, int column) {
        d[row][column] = 0;
        q.add(arr(row, column));
        while(!q.isEmpty()) {
            int[] a = q.poll();
            for(int i=0; i<4; i++) {
                int x = a[0] + DX[i];
                int y = a[1] + DY[i];
                if(isValidIndex(x) && isValidIndex(y) && g[x][y] != WALL) {
                    //비를 피하는 곳에 도달했을 경우 조기 종료
                    if(g[x][y] == SHELTER) {
                        q.clear();
                        return d[a[0]][a[1]] + 1;
                    }
                    //벽이 아닌 곳으로 이동하면서 거리 기록
                    else if(g[x][y] == EMPTY || g[x][y] == PERSON) {
                        if(d[x][y] > d[a[0]][a[1]] + 1) {
                            d[x][y] = d[a[0]][a[1]] + 1;
                            q.add(arr(x, y));
                        }
                    }
                }
            }
        }
        return -1; //비를 피하지 못할 경우
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //격자 크기(2 ~ 100)
        int h = sc.nextInt(); //인원(1 ~ n * n)
        int m = sc.nextInt(); //비를 피할 수 있는 공간의 수(1 ~ n * n)
        Grid grid = new Grid(n);
        int i, j;
        for(i=0; i<n; i++) {
            for(j=0; j<n; j++) grid.setTile(i, j, sc.nextInt());
        }
        grid.move();
    }
}