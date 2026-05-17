import java.util.Scanner;

public class Main {
    public static final int NA = -99999;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //1 ~ 100
        int m = sc.nextInt(); //1 ~ 10000
        int[] weight = new int[n]; //weight[i]: i번 보석의 무게
        int[] value = new int[n]; //value[i]: i번 보석의 가치
        int i, j;
        for(i=0; i<n; i++) {
            weight[i] = sc.nextInt(); //1 ~ 10000
            value[i] = sc.nextInt(); //1 ~ 10000
        }
        int[] dp = new int[m + 1]; //dp[i]: 무게의 합이 i일 때 최대 가치
        for(i=0; i<=m; i++) dp[i] = NA; //dp 초기화
        dp[0] = 0;
        //dp 채우기
        for(i=0; i<=m; i++) {
            for(j=0; j<n; j++) {
                //j번 보석을 추가(여러 번 가능)할 수 있을 경우 추가해본 뒤 dp 갱신
                if(i >= weight[j]) dp[i] = Math.max(dp[i], dp[i - weight[j]] + value[j]);
            }
        }
        int output = 0;
        //무게의 합이 m 이하일 때 최대 가치
        for(i=1; i<=m; i++) output = Math.max(output, dp[i]);
        System.out.println(output);
    }
}