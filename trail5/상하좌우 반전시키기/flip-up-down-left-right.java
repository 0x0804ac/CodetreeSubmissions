import java.util.Scanner;

public class Main {
    public static final int[] DX = new int[] { 0, 1, 0, -1 };
    public static final int[] DY = new int[] { 1, 0, -1, 0 };

    //(row, column)이 격자 내부에 있는지 검사
    public static boolean isValidIndex(boolean[][] arr, int row, int column) {
        if(row < 0 || column < 0) return false;
        return row < arr.length && column < arr[0].length;
    }

    //arr[row][column] 상태 반전
    public static void toggle(boolean[][] arr, int row, int column) {
        arr[row][column] = !arr[row][column];
    }

    //arr[row][collumn]을 누르면 해당 칸 그리고 상하좌우로 인접한 칸의 상태 반전
    public static void flip(boolean[][] arr, int row, int column) {
        toggle(arr, row, column);
        for(int i=0; i<4; i++) {
            int r = row + DX[i];
            int c = column + DY[i];
            if(isValidIndex(arr, r, c)) toggle(arr, r, c);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //1 ~ 100
        int output = 0; //격자의 모든 숫자를 1(true)로 만들기 위해 눌러야 하는 최소 횟수
        boolean[][] arr = new boolean[n][n];
        int i, j;
        for(i=0; i<n; i++) {
            for(j=0; j<n; j++) arr[i][j] = sc.nextInt() == 1; //0 또는 1
        }
        //첫 행은 누를 수 없음
        for(i=1; i<n; i++) {
            for(j=0; j<n; j++) {
                //첫 행의 상태를 반전시키려면 오른쪽에 있는 칸을 눌러야 함
                if(!arr[i - 1][j]) {
                    output++;
                    flip(arr, i, j);
                }
            }
        }
        for(i=0; i<n; i++) {
            //모두 1로 만드는 것이 불가능하다면 -1 출력
            if(!arr[n - 1][i]) {
                System.out.println(-1);
                break;
            }
        }
        if(i == n) System.out.println(output); //최소 횟수 출력
    }
}