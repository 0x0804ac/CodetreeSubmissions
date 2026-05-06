import java.util.Scanner;

public class Main {
    //arr[index1]과 arr[index2] 교환
    public static void swap(int[] arr, int index1, int index2) {
        if(index1 == index2) return;
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    //heap[1] ~ heap[size] 구간의 힙에 대하여 heap[node] 재배치
    public static void heapify(int[] heap, int node, int size) {
        if(size < 1) return;
        int left = node * 2; //왼쪽 자식
        if(left > size) return; //자식 노드가 없을 경우 종료
        int right = left + 1; //오른쪽 자식
        int max = node;
        //오른쪽 자식이 있음
        if(right <= size) {
            //heap[node], heap[left], heap[right]의 최댓값 index
            if(heap[right] > heap[node] && heap[right] > heap[left]) max = right;
            else if(heap[left] > heap[node] && heap[left] > heap[node]) max = left;
        }
        //왼쪽 자식만 있음
        else if(heap[left] > heap[node]) max = left;
        //교환이 필요한 경우 진행
        if(max != node) {
            swap(heap, node, max);
            heapify(heap, max, size);
        }
    }

    //heap[1] ~ heap[len - 1] 정렬
    public static void heapSort(int[] heap) {
        int len = heap.length;
        int i;
        for(i=len/2; i>0; i--) heapify(heap, i, len - 1); //최대 힙으로 초기화
        for(i=len-1; i>0; i--) {
            swap(heap, 1, i); //최댓값 재배치
            heapify(heap, 1, i - 1); //최대 힙으로 재구성
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n + 1];
        int i;
        for(i=1; i<=n; i++) arr[i] = sc.nextInt();
        heapSort(arr);
        for(i=1; i<=n; i++) System.out.print(arr[i] + " ");
    }
}