import java.util.ArrayDeque;
import java.util.Scanner;

class DP implements Comparable<DP> {
    public static final int NA = 1_000_000_000;
    public static final int FOLLOW_A = 1;
    public static final int FOLLOW_B = 2;
    public static final int SELECT = 3;

    private int l; //최장 공통 부분 수열의 길이
    private int m; //마지막 원소의 최솟값
    private int p; //이전 원소의 위치

    public DP() {
        l = 0;
        m = NA;
        p = 0;
    }

    public DP(int length, int previousValue, int previousPosition) {
        l = length;
        m = previousValue;
        p = previousPosition;
    }

    public int getLength() { return l; }
    public int getValue() { return m; }
    public int getPosition() { return p; }

    public void setLength(int value) { l = value; }
    public void setValue(int value) { m = value; }
    public void setPosition(int value) { p = value; }

    @Override
    public int compareTo(DP other) {
        if(l != other.l) return l - other.l;
        else return other.m - m;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //1 ~ 1000
        int m = sc.nextInt(); //1 ~ 1000
        int[] a = new int[n + 1]; //수열 A(a[1] ~ a[n])
        int[] b = new int[m + 1]; //수열 B(b[1] ~ b[n])
        DP[][] dp = new DP[n + 1][m + 1];
        int i, j;
        //dp 초기화
        for(i=0; i<=n; i++) {
            for(j=0; j<=m; j++) dp[i][j] = new DP();
        }
        dp[0][0].setValue(0);
        //입력받은 수열을 뒤집어서 저장
        for(i=n; i>0; i--) a[i] = sc.nextInt();
        for(i=m; i>0; i--) b[i] = sc.nextInt();
        //dp[i][j] 채우기
        for(i=1; i<=n; i++) {
            for(j=1; j<=m; j++) {
                //(i - 1, j) -> (i, j)
                if(dp[i - 1][j].compareTo(dp[i][j]) > 0) {
                    dp[i][j].setLength(dp[i - 1][j].getLength());
                    dp[i][j].setValue(dp[i - 1][j].getValue());
                    dp[i][j].setPosition(DP.FOLLOW_A);
                }
                //(i, j - 1) -> (i, j)
                if(dp[i][j - 1].compareTo(dp[i][j]) > 0) {
                    dp[i][j].setLength(dp[i][j - 1].getLength());
                    dp[i][j].setValue(dp[i][j - 1].getValue());
                    dp[i][j].setPosition(DP.FOLLOW_B);
                }
                //(i - 1, j - 1) -> (i, j)
                if(a[i] == b[j]) {
                    int l = dp[i - 1][j - 1].getLength() + 1; //수열 길이 +1
                    if(l > dp[i][j].getLength() || (l == dp[i][j].getLength() && a[i] < dp[i][j].getValue())) {
                        dp[i][j].setLength(l);
                        dp[i][j].setValue(a[i]);
                        dp[i][j].setPosition(DP.SELECT);
                    }
                }
            }
        }
        int[] output = new int[dp[n][m].getLength()]; //사전 순으로 가장 앞선 최장 공통 부분 수열
        int index = 0;
        i = n;
        j = m;
        //output 채우기
        while(index < output.length) {
            if(a[i] == b[j] && dp[i][j].getPosition() == DP.SELECT) {
                output[index++] = a[i];
                i--;
                j--;
            }
            else {
                switch(dp[i][j].getPosition()) {
                    case DP.FOLLOW_A:
                    i--;
                    break;
                    case DP.FOLLOW_B:
                    j--;
                    break;
                    case DP.SELECT:
                    i--;
                    j--;
                    break;
                }
            }
        }
        for(i=0; i<output.length; i++) System.out.print(output[i] + " ");
    }
}
