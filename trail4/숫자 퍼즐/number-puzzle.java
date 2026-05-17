import java.util.Scanner;

public class Main {
    public static void printArray(int[] arr) {
        for(int i=0; i<arr.length; i++) System.out.print(arr[i] + " ");
        System.out.println();
    }

    public static void printArray(int[][] arr) {
        for(int[] line : arr) printArray(line);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //1 ~ 10
        int m = sc.nextInt(); //1 ~ 200
        int k = sc.nextInt(); //1 ~ 1_000_000_000
        int index = 0, sum = 0;
        int i, j, l;
        int[][] dp = new int[n + 1][m + 1]; //dp[i][j]: i개의 숫자로 j 만드는 경우의 수
        //dp 초기화
        for(i=0; i<=n; i++) {
            dp[i][0] = 1;
            dp[i][i] = 1; //i개의 1 사용
        }
        for(i=0; i<=m; i++) dp[1][i] = 1; //자기 자신을 1개 사용
        //dp 채우기
        for(i=2; i<=n; i++) {
            for(j=i+1; j<=m; j++) {
                l = 1;
                while(j >= i * l) {
                    //(i - 1)개의 숫자와 l 사용
                    if(i - 1 <= (j - 1) - i * (l - 1)) dp[i][j] += dp[i - 1][(j - 1) - i * (l - 1)];
                    l++;
                }
            }
        }
        //printArray(dp);
        int[] output = new int[n]; //찾아야 하는 수열(오름차순 정렬, n개의 원소의 합이 m)
        //output[0] 채우기
        for(i=1; i*n<=m; i++) {
            l = dp[n - 1][m - 1 - n * (i - 1)]; //(n - 1)개의 숫자와 i 사용
            if(index + l >= k) {
                //output 채우기
                output[0] = i;
                sum += i;
                for(j=1; j<n; j++) {
                    output[j] = i - 1;
                    sum += (i - 1);
                }
                break;
            }
            else index += l;
        }
        //printArray(output);
        //output[i] 채우기
        for(i=1; i<n-1; i++) {
            for(j=1; n-i<m-sum-(j-1)*(n-i); j++) {
                l = dp[n - i - 1][m - sum - (j - 1) * (n - i) - 1]; //(n - i - 1)개의 숫자와 j 사용
                if(index + l >= k) {
                    //output[i] ~ output[n - 2] 채우기
                    output[i] += j;
                    sum += j;
                    for(l=i+1; l<n; l++) {
                        output[l] += (j - 1);
                        sum += (j - 1);
                    }
                    break;
                }
                else index += l;
            }
            //printArray(output);
            //System.out.println(index);
        }
        output[n - 1] += (m - sum); //output[n - 1] 채우기
        printArray(output);
    }
}