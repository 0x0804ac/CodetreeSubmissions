import java.util.Scanner;

public class Main {
    public static final int NA = Integer.MIN_VALUE;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //1 ~ 100
        int m = sc.nextInt(); //1 ~ 10000
        int[] w = new int[n + 1]; //w[i]: i번 보석의 무게(1 ~ n)
        int[] v = new int[n + 1]; //v[i]: i번 보석의 가치(1 ~ n)
        int[][] dp = new int[n + 1][m + 1]; //dp[i][j]: (1 ~ i)번 보석 중에서 고른 보석의 무게 합이 j일 때 최대 가치
        int i, j, v1, v2;
        for(i=1; i<=n; i++) {
            w[i] = sc.nextInt(); //1 ~ 10000
            v[i] = sc.nextInt(); //1 ~ 10000
        }
        //dp 초기화
        for(i=0; i<=n; i++) {
            dp[i][0] = 0;
            for(j=1; j<=m; j++) dp[i][j] = NA;
        }
        //dp 채우기
        for(i=1; i<=n; i++) {
            for(j=1; j<=m; j++) {
                v1 = dp[i - 1][j]; //j번 보석을 고르지 않음
                //무게 여유가 있어서 j번 보석을 고를 수 있는 경우
                if(w[i] <= j) {
                    v2 = dp[i - 1][j - w[i]] + v[i]; //j번 보석을 고름
                    dp[i][j] = Math.max(v1, v2);
                }
                else dp[i][j] = v1;
            }
        }
        int output = 0;
        //(1 ~ n)번 보석 중에서 고른 보석의 무게 합이 m 이하일 때 최대 가치
        for(j=1; j<=m; j++) {
            if(output < dp[n][j]) output = dp[n][j];
        }
        System.out.println(output);
    }
}