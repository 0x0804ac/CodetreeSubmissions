import java.util.Scanner;

public class Main {
    //2차원 배열 출력
    public static void print(int[][] arr) {
        for(int i=0; i<arr.length; i++) {
            for(int j=0; j<arr[i].length; j++) System.out.print(arr[i][j] + " ");
            System.out.println();
        }
    }

    //중력이 작용하여 폭탄이 아래로 떨어짐
    public static void applyGravity(int[][] bombs, int col) {
        int len = bombs.length, index = len - 1;
        int[] temp = new int[len];
        int i;
        //i번째 행 처리
        for(i=index; i>=0; i--) {
            if(bombs[i][col] > 0) temp[index--] = bombs[i][col];
        }
        for(i=0; i<len; i++) bombs[i][col] = temp[i];
    }

    //폭탄 bombs[row][col] 폭발
    public static void detonate(int[][] bombs, int row, int col) {
        int range = bombs[row][col] - 1;
        int i;
        //가로로 폭발
        for(i=col-range; i<=col+range; i++) {
            if(i >= 0 && i < bombs[row].length) bombs[row][i] = 0;
        }
        //세로로 폭발
        for(i=row-range; i<=row+range; i++) {
            if(i >= 0 && i < bombs.length) bombs[i][col] = 0;
        }
        //폭발 후 중력 작용
        for(i=0; i<bombs[0].length; i++) applyGravity(bombs, i);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] grid = new int[n][n];
        int i, j, c;
        for(i=0; i<n; i++) {
            for(j=0; j<n; j++) grid[i][j] = sc.nextInt();
        }
        for(i=0; i<m; i++) {
            c = sc.nextInt();
            //해당 열에 폭탄이 있으면 가장 위에 있는 것 폭발
            for(j=0; j<n; j++) {
                if(grid[j][c - 1] > 0) {
                    detonate(grid, j, c - 1);
                    break;
                }
            }
        }
        print(grid);
    }
}
