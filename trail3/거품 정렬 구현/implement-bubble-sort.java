import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        int i, temp;
        boolean isSorted;
        for(i=0; i<n; i++) arr[i] = sc.nextInt();
        do {
            isSorted = true;
            for(i=0; i<n-1; i++) {
                if(arr[i] > arr[i + 1]) {
                    isSorted = false;
                    temp = arr[i + 1];
                    arr[i + 1] = arr[i];
                    arr[i] = temp;
                }
            }
        } while(!isSorted);
        for(i=0; i<n; i++) System.out.print(arr[i] + " ");
    }
}
