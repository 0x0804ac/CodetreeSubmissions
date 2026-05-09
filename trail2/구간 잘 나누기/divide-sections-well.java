import java.util.Scanner;

public class Main {
    //배열의 최댓값
    public static int maxValue(int[] arr) {
        int output = arr[0];
        for(int num : arr) output = Math.max(output, num);
        return output;
    }
    
    //배열의 원소 합
    public static int sum(int[] arr) {
        int output = 0;
        for(int num : arr) output += num;
        return output;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //수 개수
        int m = sc.nextInt(); //구간 개수
        int[] nums = new int[n];
        int i, j, count, midSum, maxSum;
        for(i=0; i<n; i++) nums[i] = sc.nextInt();
        //System.out.printf("(%d groups, sum)\n", m);
        int arrMax = maxValue(nums), arrSum = sum(nums);
        //최대 구간 합을 i로 가정
        for(i=arrMax; i<=arrSum; i++) {
            count = 1;
            midSum = 0;
            maxSum = 0;
            for(j=0; j<n; j++) {
                if(midSum + nums[j] <= i) {
                    //System.out.print(nums[j] + " ");
                    midSum += nums[j];
                }
                else {
                    //System.out.print("| " + nums[j] + " ");
                    count++; //새로운 구간 시작
                    maxSum = Math.max(maxSum, midSum);
                    midSum = nums[j];
                }
            }
            maxSum = Math.max(maxSum, midSum); //마지막 구간 처리
            //System.out.printf("(%d groups, %d)\n", count, maxSum);
            if(count <= m && maxSum == i) {
                System.out.println(i);
                break; //최대 합이 i인 m개의 구간이 성공적으로 만들어졌다면 종료
            }
        }
    }
}