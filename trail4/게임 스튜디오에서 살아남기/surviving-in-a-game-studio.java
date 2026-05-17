import java.util.Scanner;

public class Main {
    public static final int MOD = 1_000_000_007;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] dp = new int[n][9]; //dp[i][j]: 길이가 (i + 1)이고 ('T'의 개수) * 3 + (연속 'B'의 개수) == j인 문자열의 개수
        int i, j, k;
        dp[0][0] = 1; //문자열 "G"
        dp[0][1] = 1; //문자열 "B"
        dp[0][3] = 1; //문자열 "T"
        //dp 채우기
        for(i=1; i<n; i++) {
            for(j=0; j<9; j++) {
                if(j % 3 == 0) {
                    //'G' 추가(연속 'B'가 0이 됨)
                    for(k=j; k<j+3; k++) dp[i][j] = (dp[i][j] + dp[i - 1][k] % MOD) % MOD;
                }
                //연속 'B'가 1 이하인 경우 'B' 추가 가능(연속 'B' 1 증가)
                if(j % 3 > 0) dp[i][j] = (dp[i][j] + dp[i - 1][j - 1] % MOD) % MOD;
                //'T'가 1개 이하인 경우 'T' 추가 가능('T' 1개 증가, 연속 'B'가 0이 됨)
                if(j == 3 || j == 6) {
                    for(k=j-3; k<j; k++) dp[i][j] = (dp[i][j] + dp[i - 1][k] % MOD) % MOD;
                }
            }
        }
        int output = 0;
        //길이가 n인 모든 문자열('T' 3개 미만, 연속 'B'가 3개 이상 포함되지 않음)의 개수
        for(i=0; i<9; i++) output = (output + dp[n - 1][i]) % MOD;
        System.out.println(output);
    }
}