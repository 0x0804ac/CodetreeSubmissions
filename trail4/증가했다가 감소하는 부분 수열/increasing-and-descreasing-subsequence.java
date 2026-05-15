import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //1 ~ 1000
        int[] arr = new int[n];
        int[] asc = new int[n]; //asc[i]: (arr[0] ~ arr[i]) 범위 내의 최장 증가 부분 수열의 길이
        int[] desc = new int[n]; //desc[i]: (arr[i] ~ arr[n - 1]) 범위 내의 최장 감소 부분 수열의 길이
        int i, j;
        for(i=0; i<n; i++) arr[i] = sc.nextInt(); //1 ~ 10000
        //desc 채우기
        desc[n - 1] = 1;
        for(i=n-2; i>=0; i--) {
            desc[i] = 1;
            //다음 원소들에 대하여 감소하는 순서일 경우 자신을 수열에 추가
            for(j=n-1; j>i; j--) {
                if(arr[i] > arr[j] && desc[i] <= desc[j]) desc[i] = desc[j] + 1;
            }
        }
        //asc 채우기
        asc[0] = 1;
        for(i=1; i<n; i++) {
            asc[i] = 1;
            //이전 원소들에 대하여 증가하는 순서일 경우 자신을 수열에 추가
            for(j=0; j<i; j++) {
                if(arr[j] < arr[i] && asc[i] <= asc[j]) asc[i] = asc[j] + 1;
            }
        }
        int output = 1;
        //자기 자신이 2개의 수열에 포함되었으므로 결과 계산 시 1을 뺌
        for(i=0; i<n; i++) output = Math.max(output, asc[i] + desc[i] - 1);
        System.out.println(output);
    }
}
