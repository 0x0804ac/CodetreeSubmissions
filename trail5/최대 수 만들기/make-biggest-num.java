import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static final String EMPTY = "";

    public static void main(String[] args) throws IOException, NumberFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine()); //1 ~ 50_000
        Integer[] arr = new Integer[n]; //n개의 정수
        for(int i=0; i<n; i++) arr[i] = Integer.valueOf(br.readLine()); //1 ~ 1_000_000_000
        //수를 문자열로 생각하고 + 연산 했을 때, 앞에 놓으면 결과값이 더 큰 것이 먼저 오도록 정렬
        Arrays.sort(arr, (a, b) -> (EMPTY + b + a).compareTo(EMPTY + a + b));
        for(int e : arr) System.out.print(e);
    }
}
