import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, NumberFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine()); //4 ~ 100_000
        ArrayList<Integer> center = new ArrayList<>(n); //중간 지점(두번째 구간 끝)으로 가능한 곳
        long[] arr = new long[n]; //수열의 누적합
        long[] left = new long[n]; //왼쪽의 모든 원소들로 두 구간이 정확히 만들어지는 경우의 수
        long[] right = new long[n]; //오른쪽의 모든 원소들로 두 구간이 정확히 만들어지는 경우의 수
        long sum = 0L; //총합
        long a, b;
        int i;
        String[] line = br.readLine().split(" ");
        for(i=0; i<n; i++) {
            arr[i] = Integer.parseInt(line[i]); //-2_000_000_000 ~ 2_000_000_000
            sum += arr[i];
        }
        //총합이 4로 나누어 떨어지지 않을 경우 불가능: 종료
        if(sum % 4 != 0) {
            System.out.println(0);
            System.exit(0);
        }
        a = 0L;
        b = 0L;
        //왼쪽부터 탐색
        for(i=0; i<n; i++) {
            a += arr[i]; //중간합
            if(a * 4 == sum) b++; //왼쪽 두 구간의 중간 지점으로 사용 가능
            if(a * 2 == sum && i > 0 && i < n - 2) center.add(i); //두번째 구간 끝
            left[i] = b; //가능한 지점의 수
        }
        a = 0L;
        b = 0L;
        //오른쪽부터 탐색
        for(i=n-1; i>=0; i--) {
            a += arr[i]; //중간합
            if(a * 4 == sum) b++; //오른쪽 두 구간의 중간 지점으로 사용 가능
            right[i] = b; //가능한 지점의 수
        }
        long output = 0L; //4개의 구간을 정확히 만들 수 있는 경우의 수
        //가능한 모든 중간 지점에 대하여 left와 right을 이용하여 경우의 수 계산
        for(int index : center) {
            output += (1L * left[index - 1] * right[index + 2]);
        }
        System.out.println(output);
    }
}