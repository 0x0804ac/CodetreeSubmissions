import java.util.Scanner;

public class Main {
    public static final int MOD = 1_000_000_007;
    public static final String DEBUG_MID = " / ";

    public static void debug(int[][][] arr) {
        int i, j, k;
        for(i=0; i<arr.length; i++) {
            for(j=1; j<arr[i].length; j++) {
                System.out.print(j);
                for(k=0; k<i; k++) System.out.print(0);
                System.out.print(DEBUG_MID);
                for(k=0; k<arr[i][j].length; k++) System.out.print(arr[i][j][k] + DEBUG_MID);
                System.out.println();
            }
        }
    }

    public static int numAt(String num, int index) {
        return (int) (num.charAt(index) - '0');
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String n = sc.next();
        //dp[i][j][k]: 게임을 10^i * j까지 진행하면서 박수 친 횟수
        //k는 (10^i * j)를 3으로 나눈 나머지(단, 숫자에 3/6/9가 포함되어 있으면 3을 더함)
        int[][][] dp = new int[n.length()][10][6];
        int i, j, k;
        //dp[0] 초기화
        for(i=1; i<10; i++) {
            for(j=i; j<10; j++) dp[0][j][i % 3 == 0 ? 3 : i % 3]++;
        }
        //dp 채우기(N은 3/6/9가 아닌 수)
        for(i=1; i<n.length(); i++) {
            for(k=0; k<6; k++) dp[i][0][k] = dp[i - 1][9][k]; //09X -> 10X
            //(1~9)X
            for(j=1; j<10; j++) {
                for(k=0; k<6; k++) dp[i][j][k] = dp[i][j - 1][k]; //1X -> 2X, 09X -> 10X
                //09X -> 10X
                if(j == 1) {
                    if(i > 1) {
                        for(k=3; k<6; k++) {
                            dp[i][j][k] = (dp[i][j][k] + dp[i - 1][1][0]) % MOD; //09(3/6/9) -> 10X
                            dp[i][j][k] = (dp[i][j][k] + dp[i - 1][1][3]) % MOD; //09N -> 10X
                        }
                    }
                    for(k=0; k<6; k++) dp[i][0][k] = dp[i][1][k];
                    dp[i][1][1] = (dp[i][1][1] + 1) % MOD;
                }
                //3X -> 40, 6X -> 70
                else if(j == 4 || j == 7) {
                    for(k=3; k<6; k++) {
                        dp[i][j][k] = (dp[i][j][k] + dp[i][1][0]) % MOD; //(3/6)(3/6/9)X -> (4/7)0X
                        dp[i][j][k] = (dp[i][j][k] + dp[i][1][3]) % MOD; //(3/6)NX -> (4/7)0X
                    }
                    dp[i][j][1] = (dp[i][j][1] + 1) % MOD;
                }
                //NX -> (N+1)X
                else {
                    for(k=0; k<3; k++) dp[i][j][k] = (dp[i][j][k] + dp[i][0][(k - j + 10) % 3]) % MOD; //NN
                    for(k=3; k<6; k++) dp[i][j][k] = (dp[i][j][k] + dp[i][0][(k - j + 7) % 3 + 3]) % MOD; //N(3/6/9)
                    if(j % 3 == 0) dp[i][j][3] = (dp[i][j][3] + 1) % MOD;
                    else dp[i][j][j % 3] = (dp[i][j][j % 3] + 1) % MOD;
                }
            }
        }
        //debug(dp);
        i = numAt(n, 0);
        int output = dp[n.length() - 1][i][0]; //입력이 ABCD이면 A000까지 진행
        int mod3 = i % 3;
        boolean flag = mod3 == 0;
        for(i=3; i<6; i++) output = (output + dp[n.length() - 1][numAt(n, 0)][i]) % MOD;
        //입력이 ABCD이면 A001부터 ABCD까지 진행
        for(i=1; i<n.length(); i++) {
            j = numAt(n, i);
            if(j == 0) continue;
            for(k=0; k<3; k++) {
                if(flag || (k + mod3) % 3 == 0) output = (output + dp[n.length() - i - 1][j][k]) % MOD;
            }
            for(k=3; k<6; k++) output = (output + dp[n.length() - i - 1][j][k]) % MOD;
            mod3 = (mod3 + j) % 3;
            if(j % 3 == 0) flag = true;
        }
        System.out.println(output);
    }
}