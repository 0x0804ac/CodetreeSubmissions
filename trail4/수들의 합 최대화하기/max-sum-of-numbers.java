import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ArrayList<Integer> columnList = new ArrayList<Integer>(10); //행마다 색칠할 열의 리스트
    public static int[][] grid;
    public static boolean[] checked;
    public static int output = 0;

    //grid에서 색칠된 칸의 합
    public static void getSum() {
        int sum = 0;
        int i, j;
        //한 칸씩 더하기
        for(i=0; i<grid.length; i++) {
            j = columnList.get(i);
            sum += grid[i][j];
        }
        output = Math.max(output, sum);
    }

    //grid[row]에서 한 칸 칠하기
    public static void func(int row) {
        int len = checked.length;
        //다 칠했으면 합 계산
        if(row == len) {
            getSum();
            return;
        }
        for(int i=0; i<len; i++) {
            //아직 확인하지 않은 칸만 탐색
            if(!checked[i]) {
                columnList.add(i);
                checked[i] = true;
                func(row + 1);
                columnList.remove(columnList.size() - 1);
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
            for(int j=0; j<n; j++) grid[i][j] = sc.nextInt();
        }
        func(0); //grid[0]부터 채우기
        System.out.println(output); //색칠된 칸의 최대 합 출력
    }
}
