import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static final String SPACE = " ";
    public static final int[] TARGET = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };

    //사다리 타기 결과 모두 제자리로 돌아갔는지 검사
    public static boolean check(int[] arr) {
        for(int i=0; i<arr.length; i++) {
            if(arr[i] != TARGET[i]) return false;
        }
        return true;
    }

    //index번 세로줄과 (index + 1)번 세로줄을 잇는 가로줄의 숫자 위치 교환
    public static void swap(int[] arr, int index) {
        if(index >= 0 && index < arr.length - 1) {
            int temp = arr[index];
            arr[index] = arr[index + 1];
            arr[index + 1] = temp;
        }
    }

    //현재 상태에서 모두 제자리로 돌아가기 위해 필요한 최소 가로줄 개수
    public static int calc(int[] arr) {
        for(int i=0; i<arr.length-1; i++) {
            if(arr[i] > arr[i + 1]) {
                swap(arr, i);
                return calc(arr) + 1;
            }
        }
        return 0;
    }

    public static void main(String[] args) throws IOException, NumberFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] l = br.readLine().split(SPACE);
        int n = Integer.parseInt(l[0]); //세로줄의 수(2 ~ 11)
        int m = Integer.parseInt(l[1]); //가로줄의 수(1 ~ 15)
        int[][] lines = new int[m][2]; //가로줄
        int[] ladder = new int[n]; //세로줄
        int i;
        for(i=0; i<m; i++) {
            l = br.readLine().split(SPACE);
            lines[i][0] = Integer.parseInt(l[0]); //lines[i][0]번 세로줄과 (lines[i][0] + 1)번 세로줄을 연결
            lines[i][1] = Integer.parseInt(l[1]); //위에서부터 lines[i][1]번째에 있는 가로줄
        }
        for(i=0; i<n; i++) ladder[i] = TARGET[i];
        Arrays.sort(lines, (a, b) -> (a[1] - b[1])); //가로줄을 높이 기준으로 정렬
        for(int[] line : lines) swap(ladder, line[0] - 1); //사다리 타기 결과 저장
        System.out.println(calc(ladder));
    }
}