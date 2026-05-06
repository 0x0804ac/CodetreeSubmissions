import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static final int BASE = 10;
    
    public static int getDigit(int num, int digit) {
        return (num / (int) Math.pow(10, digit)) % 10;
    }

    public static int getDigits(int num) {
        return Integer.toString(num).length();
    }

    public static void radixSort(int[] arr) {
        //자릿값 0 ~ 9
        ArrayList<Integer>[] digits = new ArrayList[10];
		int i, j;
        for(i=0; i<10; i++) digits[i] = new ArrayList<Integer>();
        //가장 큰 수의 자릿수
        int maxDigits = 0;
        for(int num : arr) maxDigits = Math.max(maxDigits, num);
        maxDigits = getDigits(maxDigits);
        //모든 자리에 대하여 일의 자리부터 정렬
        for(i=0; i<maxDigits; i++) {
            //자릿값에 따라 분류
            for(int num : arr) digits[getDigit(num, i)].add(num);
            //순서를 유지하면서 배열에 재배치
            j = 0;
            for(ArrayList<Integer> digit : digits) {
                for(int num : digit) arr[j++] = num;
            }
            for(ArrayList<Integer> digit : digits) digit.clear();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        int i;
        for(i=0; i<n; i++) arr[i] = sc.nextInt();
        radixSort(arr);
        for(i=0; i<n; i++) System.out.print(arr[i] + " ");
    }
}