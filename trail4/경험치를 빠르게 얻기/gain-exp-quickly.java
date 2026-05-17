import java.util.Scanner;

public class Main {
    public static final int NA = -1;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //1 ~ 100
        int m = sc.nextInt(); //1 ~ 1_000_000
        int[] exp = new int[n];
        int[] time = new int[n];
        int sum = 0, len = 1;
        int i, j;
        for(i=0; i<n; i++) {
            exp[i] = sc.nextInt(); //1 ~ 1_000_000
            time[i] = sc.nextInt(); //1 ~ 100
            sum += exp[i];
            len += time[i];
        }
        if(sum < m) System.out.println(NA); //모든 퀘스트의 경험치 합이 m 미만일 경우 불가능
        else if(sum == m) System.out.println(sum); //모든 퀘스트의 경험치 합이 m일 경우 모든 퀘스트를 수행해야 함
        else {
            int[] dp = new int[len]; //dp[i]: 총 i의 시간 동안 얻을 수 있는 최대 경험치
            int output = Integer.MAX_VALUE;
            //dp 초기화
            dp[0] = 0;
            for(i=1; i<len; i++) dp[i] = NA;
            //dp 채우기
            for(i=0; i<n; i++) {
                for(j=len-1; j>0; j--) {
                    //i번 퀘스트를 수행할 시간 여유가 있는 경우 dp 갱신
                    if(j - time[i] >= 0 && dp[j - time[i]] != NA) dp[j] = Math.max(dp[j], dp[j - time[i]] + exp[i]);
                    //경험치 합이 m 이상인 경우 output 갱신
                    if(dp[j] >= m) output = Math.min(output, j);
                }
            }
            System.out.println(output); //퀘스트를 통해 경험치를 m 이상 얻기 위해 필요한 최소 시간
        }
    }
}