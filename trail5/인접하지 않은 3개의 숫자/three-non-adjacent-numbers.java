import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //5 ~ 100_000
        int output = 0; //배열 내에서 인접하지 않은 3개의 숫자 합의 최댓값
        int[] arr = new int[n]; //배열
        int[] left = new int[n]; //left[i]: arr[0] ~ arr[i - 1] 범위 내의 최댓값
        int[] right = new int[n]; //right[i]: arr[i + 1] ~ arr[n - 1] 범위 내의 최댓값
        int i;
        for(i=0; i<n; i++) arr[i] = sc.nextInt(); //1 ~ 100_000
        for(i=1; i<n; i++) left[i] = Math.max(left[i - 1], arr[i - 1]); //left 채우기
        for(i=n-1; i>0; i--) right[i - 1] = Math.max(right[i], arr[i]); //right 채우기
        //left와 right를 이용하여 최댓값 계산
        for(i=2; i<n-2; i++) output = Math.max(output, left[i - 1] + right[i + 1] + arr[i]);
        System.out.println(output);
    }
}