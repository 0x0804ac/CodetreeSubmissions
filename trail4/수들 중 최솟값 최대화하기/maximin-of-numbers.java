import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static int[][] grid;
    public static boolean[] checked; //열이 색칠되었는지 여부
    public static int output = 0;

    //grid[row]에서 한 칸 칠하기, 지금까지의 최솟값: midMin
    public static void func(int row, int midMin) {
        int len = checked.length;
        //다 칠했으면 합 계산
        if(row == len) {
            output = Math.max(output, midMin);
            return;
        }
        for(int i=0; i<len; i++) {
            //아직 확인하지 않은 칸만 탐색
            if(!checked[i]) {
                checked[i] = true;
                func(row + 1, Math.min(midMin, grid[row][i]));
                checked[i] = false;
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        grid = new int[n][n];
        checked = new boolean[n];
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) grid[i][j] = sc.nextInt(); //칸에 쓰인 값(1 ~ 10000)
        }
        func(0, 11111); //grid[0]부터 채우기
        System.out.println(output); //색칠된 칸의 최대 합 출력
    }
}
