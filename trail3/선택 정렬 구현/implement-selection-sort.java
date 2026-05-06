import java.util.Scanner;

public class Main {
    public static void selectionSort(int[] arr) {
        int len = arr.length;
        int min, temp;
        for(int i=0; i<len-1; i++) {
            //가장 작은 원소 index(범위: i ~ len-1) 찾기
            min = i;
            for(int j=i+1; j<len; j++) {
                if(arr[j] < arr[min]) min = j;
            }
            //arr[i]와 자리 바꾸기
            if(min != i) {
                temp = arr[i];
                arr[i] = arr[min];
                arr[min] = temp;
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        int i;
        for(i=0; i<n; i++) arr[i] = sc.nextInt();
        selectionSort(arr);
        for(i=0; i<n; i++) System.out.print(arr[i] + " ");
    }
}
