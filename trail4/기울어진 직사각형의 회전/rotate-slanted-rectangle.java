import java.util.Scanner;

public class Main {
    public static final int[] DX = new int[] { -1, -1, 1, 1 };
    public static final int[] DY = new int[] { 1, -1, -1, 1 };

    //rect[row][column]에서 시작하는 기울어진 직사각형을 따라서 회전
    public static void shift(int[][] rect, int row, int column, int moves1, int moves2, int direction) {
        int[] numbers = new int[(moves1 + moves2) * 2];
        int r = row, c = column, dir = 0;
        int i, j, temp;
        //임시 배열에 숫자 저장
        for(i=0; i<numbers.length; i++) {
            numbers[i] = rect[r][c];
            if(i == moves1 || i == moves1 + moves2 || i == moves1 + moves2 + moves1) dir++;
            r += DX[dir];
            c += DY[dir];
        }
        //반시계 방향 회전
        if(direction == 0) {
            temp = numbers[i - 1];
            for(j=i-1; j>0; j--) numbers[j] = numbers[j - 1];
            numbers[0] = temp;
        }
        //시계 방향 회전
        else {
            temp = numbers[0];
            for(j=0; j<i-1; j++) numbers[j] = numbers[j + 1];
            numbers[i - 1] = temp;
        }
        //격자에 숫자 재배치
        r = row; c = column; dir = 0;
        for(i=0; i<numbers.length; i++) {
            rect[r][c] = numbers[i];
            if(i == moves1 || i == moves1 + moves2 || i == moves1 + moves2 + moves1) dir++;
            r += DX[dir];
            c += DY[dir];
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] grid = new int[n][n];
        int i, j;
        for(i=0; i<n; i++) {
            for(j=0; j<n; j++) grid[i][j] = sc.nextInt();
        }
        int r = sc.nextInt();
        int c = sc.nextInt();
        int m1 = sc.nextInt();
        int m2 = sc.nextInt();
        int m3 = sc.nextInt(); //m1 == m3
        int m4 = sc.nextInt(); //m2 == m4
        int dir = sc.nextInt(); //0: 반시계, 1: 시계
        shift(grid, r - 1, c - 1, m1, m2, dir);
        for(i=0; i<n; i++) {
            for(j=0; j<n; j++) System.out.print(grid[i][j] + " ");
            System.out.println();
        }
    }
}
