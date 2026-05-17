import java.util.Scanner;

public class Main {
    public static final int NA = Integer.MAX_VALUE;

    //from에서 to로 바꾸기 위한 최소한의 회전 수
    public static int[] convert(String from, String to) {
        int[] output = new int[from.length()];
        for(int i=0; i<from.length(); i++) {
            output[i] = (10 + (int)(from.charAt(i) - '0') - (int)(to.charAt(i) - '0')) % 10;
        }
        return output;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //3 ~ 10000
        String current = sc.next(); //현재 상태
        String target = sc.next(); //목표 상태
        int[] rotate = convert(current, target); //각 마법진을 회전해야 하는 횟수(반시계 회전 시 뒤에 있는 모든 마법진이 회전)
        int[][] dp = new int[n][10]; //dp[i][j]: 첫 i개의 마법진을 목표 상태로 바꾸는 데 필요한 최소 회전 수(반시계 회전 j회)
        int i, j, k;
        //dp 초기화
        for(i=1; i<n; i++) {
            for(j=0; j<10; j++) dp[i][j] = NA;
        }
        //dp[0] 채우기
        for(i=0; i<10; i++) dp[0][i] = i + (rotate[0] + i) % 10;
        //dp 채우기
        for(i=1; i<n; i++) {
            for(j=0; j<10; j++) {
                //반시계 회전을 k회 더 한 뒤 dp 갱신
                for(k=0; k<10; k++) dp[i][j] = Math.min(dp[i][j], dp[i - 1][k] + (10 + j - k) % 10 + (rotate[i] + j) % 10);
            }
        }
        int output = dp[n - 1][0]; //n개의 마법진을 목표 상태로 바꾸는 데 필요한 최소 회전 수
        for(i=0; i<9; i++) output = Math.min(output, dp[n - 1][i + 1]);
        System.out.println(output);
    }
}