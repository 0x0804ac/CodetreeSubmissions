import java.util.Scanner;

public class Main {
    public static final int[] DX = new int[] { -1, -1, 1, 1 };
    public static final int[] DY = new int[] { 1, -1, -1, 1 };

    //grid[top ~ bottom][left ~ right] 내부의 모든 기울어진 직사각형에 대하여 가장 큰 합
    public static int getLargestSum(int[][] grid, int size, int top, int left) {
        int bottom = top + size - 1, right = left + size - 1, output = 0;
        int i, r, c, sum, dir;
        //grid[bottom][i]에서 시작하는 직사각형 만들기
        for(i=left+1; i<right; i++) {
            sum = 0;
            dir = 0;
            r = bottom;
            c = i;
            while(dir < DX.length) {
                sum += grid[r][c];
                r += DX[dir];
                c += DY[dir];
                if(r == top || r == bottom || c == left || c == right) dir++; //방향 전환
            }
            output = Math.max(output, sum);
        }
        return output;
    }

    //grid 내부의 모든 size * size 구역에 대하여 최대 합 계산
    public static int calc(int[][] grid, int size) {
        if(size < 3) return -1;
        int output = 0;
        int i, j;
        for(i=0; i<=grid.length-size; i++) {
            for(j=0; j<=grid[i].length-size; j++) {
                output = Math.max(output, getLargestSum(grid, size, i, j));
            }
        }
        return output;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), output = 0;
        int[][] grid = new int[n][n];
        int i, j;
        for(i=0; i<n; i++) {
            for(j=0; j<n; j++) grid[i][j] = sc.nextInt();
        }
        //모든 크기의 직사각형에 대하여 최대 합 계산
        for(i=3; i<=n; i++) {
            output = Math.max(output, calc(grid, i));
        }
        System.out.println(output);
    }
}
