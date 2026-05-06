import java.util.Scanner;

public class Program {
    //lower bound: target 이상의 값이 최초로 나오는 index
    public static int lowerBound(int[] arr, int target) {
        int len = arr.length, left = 0, right = len - 1, index = len;
        int mid;
        while(left <= right) {
            mid = (left + right) / 2;
            if(arr[mid] >= target) { //target 이상
                right = mid - 1;
                if(index > mid) index = mid;
            }
            else left = mid + 1;
        }
        return index;
    }

    //upper bound: target 초과의 값이 최초로 나오는 index
    public static int upperBound(int[] arr, int target) {
        int len = arr.length, left = 0, right = len - 1, index = len;
        int mid;
        while(left <= right) {
            mid = (left + right) / 2;
            if(arr[mid] > target) { //target 초과
                right = mid - 1;
                if(index > mid) index = mid;
            }
            else left = mid + 1;
        }
        return index;
    }

    //custom bound: target 이하의 값이 마지막으로 나오는 index
    public static int customBound(int[] arr, int target) {
        int len = arr.length, left = 0, right = len - 1, index = -1;
        int mid;
        while(left <= right) {
            mid = (left + right) / 2;
            if(arr[mid] <= target) { //target 이하
                left = mid + 1;
                if(index < mid) index = mid;
            }
            else right = mid - 1;
        }
        return index;
    }

    public static void main(String[] args) {
        /*
        = = 입력 조건 = =
        1. 1 <= N <= 100
        2. 1 <= (각 원소) <= 100
        3. 입력되는 원소들은 오름차순 정렬됨
        */
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
		int[] arr = new int[N];
        for(int i=0; i<N; i++) arr[i] = sc.nextInt();
        int query = sc.nextInt();

        int lower = lowerBound(arr, query);
        int upper = upperBound(arr, query);
        int custom = customBound(arr, query);
        System.out.println(query + " 검색 결과");
        System.out.println("Lower Bound: " + arr[lower]);
        System.out.println("Upper Bound: " + arr[upper]);
        System.out.println("Custom Bound: " + arr[custom]);
        System.out.println((upper - lower) + "개 발견됨");
	}
}
