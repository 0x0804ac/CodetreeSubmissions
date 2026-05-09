import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ArrayList<Integer> numList = new ArrayList<Integer>(20);
    public static int sum = 0;
    public static int output = Integer.MAX_VALUE;

    //numList, sum 초기화
    public static void init(int[] nums) {
        for(int num : nums) {
            numList.add(num);
            sum += num;
        }
    }

    //그룹 만들기(index: 현위치, count: 지금까지 사용한 원소의 수, midSum: 지금까지 사용된 원소의 합)
    public static void func(int index, int count, int midSum) {
        int len = numList.size();
        if(index == len) return; //수열 밖으로 벗어나면 종료
        //그룹이 완성됐다면 비교
        if(count == len / 2) {
            output = Math.min(output, Math.abs(midSum - sum + midSum));
            return;
        }
        func(index + 1, count + 1, midSum + numList.get(index)); //현재 원소를 사용
        func(index + 1, count, midSum); //현재 원소를 사용하지 않음
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //1 ~ 10
        int[] arr = new int[2 * n];
        for(int i=0; i<2*n; i++) arr[i] = sc.nextInt(); //1 ~ 1000
        init(arr);
        func(0, 0, 0); //첫 원소부터 보면서 그룹 만들기
        System.out.println(output);
    }
}