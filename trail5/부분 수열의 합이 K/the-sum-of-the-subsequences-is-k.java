import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //3 ~ 1000
        int k = sc.nextInt(); //1 ~ 1_000_000
        int output = 0;
        int[] arr = new int[n]; //수열
        int[] sum = new int[n + 1]; //sum[i]: 수열의 앞 i개 원소의 합
        int i, j;
        sum[0] = 0;
        for(i=0; i<n; i++) {
            arr[i] = sc.nextInt(); //1 ~ 1_000_000
            sum[i + 1] = sum[i] + arr[i]; //sum 채우기
        }
        //모든 구간에 대하여 탐색
        for(i=1; i<=n; i++) {
            for(j=0; j<i; j++) {
                if(sum[i] - sum[j] == k) output++; //누적합을 이용하여 구간의 합을 계산
            }
        }
        System.out.println(output); //모든 원소의 합이 k인 구간의 개수
    }
}