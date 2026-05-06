import java.util.Scanner;

public class Main {
    public static final int MAX_LENGTH = 100_000;
    public static int[] mergeArr = new int[MAX_LENGTH];

    //구간 arr[begin] ~ arr[mid]와 arr[mid + 1] ~ arr[end]를 병합
    public static void merge(int[] arr, int begin, int mid, int end) {
        int i = begin, j = mid + 1, k = begin;
        while(i <= mid && j <= end) {
            //arr[i]와 arr[j] 중 더 작은 원소를 먼저 선택
            if(arr[i] <= arr[j]) mergeArr[k++] = arr[i++];
            else mergeArr[k++] = arr[j++];
        }
        //아직 선택되지 않은 원소에 대하여 처리
        while(i <= mid) mergeArr[k++] = arr[i++];
        while(j <= end) mergeArr[k++] = arr[j++];
        //원래 배열에 정렬 결과 적용
        System.arraycopy(mergeArr, begin, arr, begin, end - begin + 1);
    }

    //구간 arr[from] ~ arr[to]를 정렬
    public static void mergeSort(int[] arr, int from, int to) {
        if(from >= to) return;
        int mid = (from + to) / 2;
        mergeSort(arr, from, mid);
        mergeSort(arr, mid + 1, to);
        merge(arr, from, mid, to);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        int i;
        for(i=0; i<n; i++) arr[i] = sc.nextInt();
        mergeSort(arr, 0, n - 1);
        for(i=0; i<n; i++) System.out.print(arr[i] + " ");
    }
}