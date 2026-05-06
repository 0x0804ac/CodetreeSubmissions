import java.util.Scanner;

public class Main {
    public static void insertionSort(int[] arr) {
        int i, j, value;
        //arr[i]를 왼쪽에 삽입
        for(i=1; i<arr.length; i++) {
            j = i - 1;
            value = arr[i];
            //더 큰 원소를 오른쪽으로 밀기
            while(j >= 0 && arr[j] > value) {
                arr[j + 1] = arr[j];
                j--;
            }
            //삽입 실행
            arr[j + 1] = value;
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        int i;
        for(i=0; i<n; i++) arr[i] = sc.nextInt();
        insertionSort(arr);
        for(i=0; i<n; i++) System.out.print(arr[i] + " ");
    }
}
