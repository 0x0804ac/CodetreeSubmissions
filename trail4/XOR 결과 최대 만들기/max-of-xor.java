import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static int output = 0;

    //arr: 사용할 배열, currentIndex: 현재 index, currentValue: 현재 값, chooseCount: 더 뽑아야 할 숫자 개수
    public static void calc(int[] arr, int currentIndex, int currentValue, int chooseCount) {
        //다 뽑았으면 최댓값 저장
        if(chooseCount == 0) {
            output = Math.max(output, currentValue);
            return;
        }
        int len = arr.length;
        if(currentIndex == len) return; //다 뽑기 전에 배열 끝까지 도달했다면 종료
        //arr[currentIndex]를 선택하지 않고 다음 원소로 진행
        calc(arr, currentIndex + 1, currentValue, chooseCount);
        //arr[currentIndex]를 선택, XOR 연산을 적용하고 다음 원소로 진행
        calc(arr, currentIndex + 1, currentValue ^ arr[currentIndex], chooseCount - 1);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //0 ~ 1_000_000 범위의 정수의 개수(1 ~ 20)
        int m = sc.nextInt(); //뽑을 숫자의 개수(1 ~ n)
        int[] A = new int[n];
        for(int i=0; i<n; i++) A[i] = sc.nextInt();
        calc(A, 0, 0, m);
        System.out.println(output);
    }
}
