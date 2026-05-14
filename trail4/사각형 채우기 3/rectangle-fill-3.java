import java.util.Scanner;

public class Main {
    public static final long MOD = 1_000_000_007L;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //1 ~ 1000
        long[] dp = new long[n + 1]; //dp[i]: 2 * i 크기의 사각형을 1 * 2, 2 * 1, 1 * 1 크기의 사각형으로 채우는 방법의 수
        long count;
        int i, j;
        dp[0] = 1;
        dp[1] = 2;
        for(i=2; i<=n; i++) {
            count = 0L;
            for(j=1; j<=i; j++) count += dp[i - j] * 2L; //마지막 j줄 채우는 방법은 2가지
            count += dp[i - 2]; //마지막 2줄 채우기는 3가지이기 때문에 +1
            dp[i] = count % MOD;
        }
        System.out.println(dp[n]);
    }
}