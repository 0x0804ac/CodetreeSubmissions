import java.util.LinkedList;
import java.util.Scanner;

class Grid {
    public static final int[] DX = {1, 0, -1, 0};
    public static final int[] DY = {0, 1, 0, -1};
    public static final int START = 0; //초기 입력에서 격자의 가장 바깥쪽은 전부 물
    public static final int INVALID = -1;

    private int r; //2차원 배열 행의 개수
    private int c; //2차원 배열 열의 개수
    private boolean[][] g; //격자 정보(true이면 빙하, false이면 물)
    private boolean[][] v; //BFS 방문 정보
    private LinkedList<int[]> q; //BFS 큐

    public Grid(int row, int column) {
        r = row;
        c = column;
        g = new boolean[r][c];
        v = new boolean[r][c];
        q = new LinkedList<int[]>();
    }

    //(row, column)이 격자 내부에 있는지 검사
    public boolean isInside(int row, int column) {
        if(row < 0 || column < 0) return false;
        return row < r && column < c;
    }

    public void setTile(int row, int column, boolean value) {
        if(isInside(row, column)) g[row][column] = value;
    }

    //(0, 0)에서 BFS 실행
    public int tick() {
        return bfs(START, START);
    }

    private int[] arr(int n1, int n2) {
        return new int[] {n1, n2};
    }

    //(row, column)에서 BFS 실행
    private int bfs(int row, int column) {
        if(!isInside(row, column)) return INVALID;
        int result = 0; //이번 탐색에서 녹은 빙하 칸의 수
        int i, j;
        //시작점을 큐에 추가
        v[row][column] = true;
        q.add(arr(row, column));
        //물을 따라서 방문되지 않은 칸으로 이동
        while(!q.isEmpty()) {
            int[] next = q.poll();
            for(i=0; i<4; i++) {
                int x = next[0] + DX[i];
                int y = next[1] + DY[i];
                if(isInside(x, y) && !v[x][y]) {
                    v[x][y] = true;
                    if(g[x][y]) {
                        g[x][y] = false; //물이 빙하와 만나면 빙하가 녹아서 물이 됨
                        result++;
                    }
                    else q.add(arr(x, y));
                }
            }
        }
        //다음 BFS를 위해 방문 정보 초기화
        for(i=0; i<r; i++) {
            for(j=0; j<c; j++) v[i][j] = false;
        }
        return result;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //격자 행(3 ~ 200)
        int m = sc.nextInt(); //격자 열(3 ~ 200)
        Grid grid = new Grid(n, m);
        int time = 0, count = 0, melted = 0;
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                boolean input = sc.nextInt() == 1; //물: 0, 빙하: 1
                grid.setTile(i, j, input);
                if(input) count++; //빙하 칸의 수 저장
            }
        }
        while(count > 0) {
            melted = grid.tick();
            time++; //1초 경과(BFS 1회)
            count -= melted;
        }
        System.out.println(time + " " + melted); //소요 시간, 마지막으로 녹은 빙하의 수
    }
}