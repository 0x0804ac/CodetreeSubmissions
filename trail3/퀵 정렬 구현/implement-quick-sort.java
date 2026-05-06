import java.util.Scanner;

public class Main {
    //arr[index1], arr[index2], arr[index3] 3개의 값에 대한 중앙값의 index
    public static int median(int[] arr, int index1, int index2, int index3) {
        int a = arr[index1], b = arr[index2], c = arr[index3];
        if(b <= a && a <= c || c <= a && a <= b) return index1;
        else if(a <= b && b <= c || c <= b && b <= a) return index2;
        else return index3;
    }

    //arr[index1]과 arr[index2] 교환
    public static void swap(int[] arr, int index1, int index2) {
        if(index1 == index2) return;
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    //arr[from] ~ arr[to] 구간에 대하여 pivot을 정하고 대소관계에 따라 2개의 구간으로 분할 (pivot의 index 반환)
    public static int partition(int[] arr, int from, int to) {
        int len = to - from + 1; //구간의 길이
        int i, j;
        if(len > 3) swap(arr, median(arr, from, to, (from + to) / 2), to); //pivot을 오른쪽 끝 원소와 교환
        j = from - 1;
        for(i=from; i<to; i++) {
            if(arr[i] < arr[to]) swap(arr, ++j, i); //더 큰 원소를 오른쪽으로 이동
        }
        swap(arr, ++j, to); //pivot을 두 구간 사이로 이동
        return j;
    }

    //arr[from] ~ arr[to] 구간에 대하여 정렬
    public static void quickSort(int[] arr, int from, int to) {
        if(from >= to) return;
        int mid = partition(arr, from, to);
        quickSort(arr, from, mid - 1);
        quickSort(arr, mid + 1, to);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        int i;
        for(i=0; i<n; i++) arr[i] = sc.nextInt();
        quickSort(arr, 0, n - 1);
        for(i=0; i<n; i++) System.out.print(arr[i] + " ");
    }
}