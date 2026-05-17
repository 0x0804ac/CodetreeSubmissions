import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //1 ~ 100
        int[] profit = new int[n]; //profit[i]: 길이가 (i + 1)인 막대기를 팔았을 때 수익
        int[] dp = new int[n]; //dp[i]: 길이의 합이 (i + 1)인 막대기들을 팔았을 때 최대 수익
        int i, j;
        for(i=0; i<n; i++) profit[i] = sc.nextInt(); //1 ~ 10000
        dp[0] = profit[0];
        //dp 채우기
        for(i=1; i<n; i++) {
            dp[i] = profit[i]; //막대기를 쪼개지 않고 그대로 판매
            for(j=0; j<i; j++) dp[i] = Math.max(dp[i], dp[j] + dp[i - j - 1]); //막대기를 쪼개서 판매
        }
        System.out.println(dp[n - 1]); //길이가 n인 막대기를 (쪼개서) 팔았을 때 최대 수익 
    }
}