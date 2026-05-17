import java.util.Scanner;

public class Main {
    public static final int NA = -999999;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //1 ~ 500
        int m = sc.nextInt(); //1 ~ (n + 1) / 2
        //dp[i][j][]: arr[0] ~ arr[i - 1] 범위에서 j개의 구간(겹치거나 붙을 수 없음)을 선택했을 때 모든 숫자 합의 최댓값
        int[][][] dp = new int[n + 1][m + 1][2];
        int[] arr = new int[n];
        int i, j, k, l;
        for(i=0; i<n; i++) arr[i] = sc.nextInt(); //-1000 ~ 1000
        //dp 초기화
        for(i=0; i<=n; i++) {
            for(j=0; j<=m; j++) {
                dp[i][j][0] = j == 0 ? 0 : NA; //dp[i][j][0]: arr[i - 1]가 마지막 구간에 없음
                dp[i][j][1] = NA; // dp[i][j][1]: arr[i - 1]가 마지막 구간에 있음
            }
        }
        //dp 채우기
        for(i=1; i<=n; i++) {
            for(j=1; j<=m; j++) {
                //j번째 구간에 arr[i]가 없음
                dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1]);
                //j번째 구간에 arr[i]가 있음(구간의 시작점인 경우, 이어지고 있는 구간에 합쳐진 경우)
                dp[i][j][1] = Math.max(dp[i - 1][j - 1][0], dp[i - 1][j][1]) + arr[i - 1];
            }
        }
        //arr[0] ~ arr[n - 1]에서 m개의 구간을 선택했을 때 모든 숫자 합의 최댓값
        System.out.println(Math.max(dp[n][m][0], dp[n][m][1]));
    }
}