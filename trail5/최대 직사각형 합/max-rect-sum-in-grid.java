import java.util.Scanner;

public class Main {
    public static int[][] sum; //2차원 누적합

    //누적합을 이용하여 범위 안의 숫자들의 합 계산
    public static int area(int fromRow, int fromColumn, int toRow, int toColumn) {
        return sum[toRow][toColumn] - sum[toRow][fromColumn - 1] - sum[fromRow - 1][toColumn] + sum[fromRow - 1][fromColumn - 1];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //1 ~ 300
        int output = Integer.MIN_VALUE; //직사각형 내 숫자들의 합의 최댓값
        int[][] grid = new int[n][n]; //격자
        sum = new int[n + 1][n + 1]; //sum[i][j]: grid[0 ~ i-1][0 ~ j-1] 숫자들의 합
        int i, j, k, mid, col;
        for(i=0; i<n; i++) {
            for(j=0; j<n; j++) grid[i][j] = sc.nextInt(); //-1000 ~ 1000
        }
        //sum 채우기
        for(i=1; i<=n; i++) {
            for(j=1; j<=n; j++) sum[i][j] = sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1] + grid[i - 1][j - 1];
        }
        //행의 범위(i ~ j)를 고정 후에 열마다 최댓값을 계산
        for(i=1; i<=n; i++) {
            for(j=i; j<=n; j++) {
                //최대 연속 부분 수열 구하는 방법과 유사
                mid = area(i, 1, j, 1);
                output = Math.max(output, mid);
                //(k - 1)열까지의 합을 포함할 것인지 버릴 것인지 판단
                for(k=2; k<=n; k++) {
                    col = area(i, k, j, k);
                    mid = Math.max(mid + col, col);
                    output = Math.max(output, mid);
                }
                output = Math.max(output, mid);
            }
        }
        System.out.println(output); //격자 내부의 임의의 직사각형에 대하여 직사각형 내 숫자들 합의 최댓값
    }
}
