import java.util.Scanner;

class Grid {
    public static final int TILE_MIN = 1;
    public static final int TILE_MAX = 100;
    public static final int INFINITY = 999;

    private int len;
    private int[][] g;
    
    public Grid(int size) {
        len = size;
        g = new int[len][len];
    }

    //index가 (0 ~ len - 1) 범위 내인지 검사
    public boolean isValidIndex(int index) {
        if(index < 0) return false;
        return index < len;
    }

    public void setTile(int row, int column, int value) {
        if(isValidIndex(row) && isValidIndex(column) && value >= TILE_MIN && value <= TILE_MAX) g[row][column] = value;
    }

    //왼쪽 위에서 오른쪽 아래로 이동할 때 (최댓값 - 최솟값)의 최솟값 출력
    public int move() {
        int[][] dp = new int[len][len]; //dp[i][j]: (0, 0)에서 (i, j)까지 이동
        int[] range = new int[] {TILE_MAX, TILE_MIN}; //최솟값, 최댓값
        int output = INFINITY;
        int i, j;
        for(i=0; i<len; i++) {
            for(j=0; j<len; j++) {
                range[0] = Math.min(range[0], g[i][j]);
                range[1] = Math.max(range[1], g[i][j]);
            }
        }
        //최솟값을 lb로 가정하고 dp 채우기
        for(int lb=range[0]; lb<=range[1]; lb++) {
            //최솟값 미만인 곳은 사용하지 않음
            for(i=0; i<len; i++) {
                for(j=0; j<len; j++) {
                    if(g[i][j] < lb) g[i][j] = INFINITY;
                }
            }
            //dp 초기화
            for(i=0; i<len; i++) {
                for(j=0; j<len; j++) dp[i][j] = INFINITY;
            }
            dp[0][0] = g[0][0];
            //dp 첫 행, 첫 열 채우기
            for(i=1; i<len; i++) {
                dp[i][0] = Math.max(dp[i - 1][0], g[i][0]);
                dp[0][i] = Math.max(dp[0][i - 1], g[0][i]);
            }
            //dp 채우기
            for(i=1; i<len; i++) {
                for(j=1; j<len; j++) {
                    dp[i][j] = Math.max(Math.min(dp[i - 1][j], dp[i][j - 1]), g[i][j]);
                }
            }
            //최솟값 lb로 (len - 1, len - 1)에 도달 가능하면 최솟값 갱신
            if(dp[len - 1][len - 1] != INFINITY) {
                output = Math.min(output, dp[len - 1][len - 1] - lb);
            }
        }
        return output;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //격자 크기(1 ~ 100)
        Grid grid = new Grid(n);
        int i, j;
        for(i=0; i<n; i++) {
            for(j=0; j<n; j++) grid.setTile(i, j, sc.nextInt()); //1 ~ 100
        }
        System.out.println(grid.move());
    }
}