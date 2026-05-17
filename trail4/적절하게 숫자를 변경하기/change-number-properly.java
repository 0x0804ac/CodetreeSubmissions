import java.util.Scanner;

public class Main {
    public static final int NA = -1;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(); //1 ~ 500
        int M = sc.nextInt(); //0 ~ 100
        int[] seq = new int[N]; //수열
        int inequality = 0; //수열에서 인접한 두 숫자가 다른 횟수
        int i, j, k, l;
        for(i=0; i<N; i++) seq[i] = sc.nextInt(); //1 ~ 4
        for(i=0; i<N-1; i++) {
            if(seq[i] != seq[i + 1]) inequality++;
        }
        //입력된 수열이 '비슷한 수열'이면 종료
        if(M >= inequality) {
            System.out.println(N);
            return;
        }
        //dp[i][j][k]: seq[0] ~ seq[i]에서의 최대 유사도(숫자 변경 j회, 마지막 숫자가 (k + 1))
        int[][][] dp = new int[N][M + 1][4];
        //dp 초기화
        for(i=0; i<N; i++) {
            for(j=0; j<=M; j++) {
                for(k=0; k<4; k++) dp[i][j][k] = NA;
            }
        }
        for(i=0; i<4; i++) dp[0][0][i] = seq[0] == (i + 1) ? 1 : 0; //dp[0] 채우기
        //dp 채우기
        for(i=1; i<N; i++) {
            for(j=0; j<=M; j++) {
                for(k=0; k<4; k++) {
                    for(l=0; l<4; l++) {
                        //숫자 변경 안 함
                        if(k == l) {
                            if(dp[i - 1][j][k] != NA) dp[i][j][k] = Math.max(dp[i][j][k], dp[i - 1][j][k] + (seq[i] == (k + 1) ? 1 : 0));
                        }
                        //숫자 변경(변경 횟수 1 증가)
                        else {
                            if(j > 0 && dp[i - 1][j - 1][k] != NA) dp[i][j][l] = Math.max(dp[i][j][l], dp[i - 1][j - 1][k] + (seq[i] == (l + 1) ? 1 : 0));
                        }
                    }
                }
            }
        }
        int output = 0; //유사도: 같은 위치에 같은 원소가 나온 횟수
        //seq[0] ~ seq[N - 1]에서의 최대 유사도(숫자 변경 M회 이하)
        for(j=0; j<=M; j++) {
            for(k=0; k<4; k++) output = Math.max(output, dp[N - 1][j][k]);
        }
        System.out.println(output);
    }
}