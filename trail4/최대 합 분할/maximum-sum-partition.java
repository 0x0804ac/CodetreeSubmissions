import java.util.Scanner;

public class Main {
    public static final int NA = Integer.MIN_VALUE;

    private static int sum = 0;

    public static boolean isValid(int value) {
        return -sum <= value && value <= sum;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //1 ~ 100
        int[] arr = new int[n];
        int i, j, v1, v2;
        for(i=0; i<n; i++) {
            arr[i] = sc.nextInt(); //1 ~ 1000
            sum += arr[i];
        }
        //dp[i][j]: arr[0] ~ arr[i]의 수들을 그룹(빈 그룹 가능) A, B, C로 나눈다면
        //(A의 숫자 합) - (B의 숫자 합) = (j - sum)일 때 (A의 숫자 합)의 최댓값
        int[][] dp = new int[n][2 * sum + 1];
        //dp 초기화
        for(i=0; i<n; i++) {
            for(j=0; j<dp[i].length; j++) dp[i][j] = NA;
        }
        //dp[0] 초기화
        if(isValid(arr[0])) dp[0][sum + arr[0]] = arr[0]; //arr[0]을 그룹 A에 추가
        if(isValid(-arr[0])) dp[0][sum - arr[0]] = 0; //arr[0]을 그룹 B에 추가
        dp[0][sum] = 0; //arr[0]을 그룹 C에 추가
        //dp 채우기
        for(i=1; i<n; i++) {
            for(j=-sum; j<=sum; j++) {
                v1 = j + arr[i]; //arr[i]를 그룹 A에 추가
                v2 = dp[i - 1][sum + j];
                if(isValid(v1) && v2 != NA) {
                    if(dp[i][sum + v1] < v2 + arr[i]) dp[i][sum + v1] = v2 + arr[i];
                }
                v1 = j - arr[i]; //arr[i]를 그룹 B에 추가
                if(isValid(v1) && v2 != NA) {
                    if(dp[i][sum + v1] < v2) dp[i][sum + v1] = v2;
                }
                //arr[i]를 그룹 C에 추가
                if(v2 != NA) {
                    if(dp[i][sum + j] < v2) dp[i][sum + j] = v2;
                }
            }
        }
        //arr[0] ~ arr[n - 1]의 수들을 그룹 A, B, C로 나눈다면
        //(A의 숫자 합) == (B의 숫자 합)일 때 (A의 숫자 합)의 최댓값
        System.out.println(dp[n - 1][sum]);
    }
}