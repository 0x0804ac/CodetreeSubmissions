import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //2 ~ 100
        int[] arr = new int[n];
        int len = 1;
        int i, j;
        for(i=0; i<n; i++) {
            arr[i] = sc.nextInt(); //1 ~ 1000
            len += arr[i];
        }
        boolean[] sum = new boolean[len]; //sum[i]: 합이 i인 그룹을 만들 수 있는지 여부
        sum[0] = true;
        len--;
        //sum 채우기
        for(int num : arr) {
            //num을 사용하여 합을 i로 만들 수 있는지 확인
            for(i=len; i>0; i--) {
                if(sum[i]) continue;
                j = i - num;
                if(j >= 0 && sum[j]) sum[i] = true;
            }
        }
        len++;
        //2개의 그룹으로 나누었을 때 두 그룹의 숫자 합(i, j)의 차이의 최솟값
        for(i=len/2; i>0; i--) {
            if(sum[i]) {
                j = len - i - 1;
                System.out.println(Math.abs(i - j));
                break;
            }
        }
    }
}