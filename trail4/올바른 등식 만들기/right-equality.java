import java.util.Scanner;

public class Main {
    public static final long NA = 0L;

    public static int toIndex(int value) { return value + 20; }
    public static int toValue(int index) { return index - 20; }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(); //1 ~ 100
        int M = sc.nextInt(); //-20 ~ 20
        int[] numbers = new int[N];
        long[][] dp = new long[N + 1][41]; //dp[i][j]: 앞 i개의 수를 사용한 연산 결과가 (j - 20)인 경우의 수
        int i, j, k, index;
        for(i=0; i<N; i++) {
            numbers[i] = sc.nextInt(); //0 ~ 9
            for(j=0; j<41; j++) dp[i + 1][j] = NA; //dp 초기화
        }
        for(i=0; i<41; i++) dp[0][i] = i == toIndex(0) ? 1L : NA; //dp[0] 채우기
        //dp 채우기
        for(i=0; i<N; i++) {
            for(j=-20; j<=20; j++) {
                k = toIndex(j);
                if(dp[i][k] != NA) {
                    //numbers[i]를 더한 결과가 20 이하
                    if(j + numbers[i] <= 20) {
                        index = toIndex(j + numbers[i]);
                        dp[i + 1][index] += dp[i][k];
                    }
                    //numbers[i]를 뺀 결과가 -20 이상
                    if(j - numbers[i] >= -20) {
                        index = toIndex(j - numbers[i]);
                        dp[i + 1][index] += dp[i][k];
                    }
                }
            }
        }
        System.out.println(dp[N][toIndex(M)]); //N개의 수를 사용한 연산(+ 또는 -) 결과가 M인 경우의 수
    }
}