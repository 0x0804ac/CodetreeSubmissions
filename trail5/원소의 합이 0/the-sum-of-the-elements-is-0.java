import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class Main {
    public static boolean zero(int n1, int n2) {
        return 0L + n1 + n2 == 0L;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //1 ~ 5000
        int[] A = new int[n]; //수열 A
        int[] B = new int[n]; //수열 B
        int[] C = new int[n]; //수열 C
        int[] D = new int[n]; //수열 D
        //수열 A와 B에서 하나씩 골랐을 때 두 원소의 합이 key가 되는 경우의 수: value
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>(n * n * 3 / 2);
        long output = 0L;
        int i, j, sum;
        for(i=0; i<n; i++) A[i] = sc.nextInt();
        for(i=0; i<n; i++) B[i] = sc.nextInt();
        for(i=0; i<n; i++) C[i] = sc.nextInt();
        for(i=0; i<n; i++) D[i] = sc.nextInt();
        //수열 A과 B에서 하나씩 고르기
        for(i=0; i<n; i++) {
            for(j=0; j<n; j++) {
                sum = A[i] + B[j];
                map.put(sum, map.getOrDefault(sum, 0) + 1);
            }
        }
        //수열 C와 D에서 하나씩 고르기
        for(i=0; i<n; i++) {
            for(j=0; j<n; j++) {
                sum = C[i] + D[j];
                if(map.containsKey(-sum)) output = output + map.get(-sum);
            }
        }
        System.out.println(output); //4개의 수열에서 하나씩 골랐을 때 합이 0이 되는 경우의 수
    }
}