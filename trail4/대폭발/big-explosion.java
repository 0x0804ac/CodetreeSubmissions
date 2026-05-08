import java.util.Scanner;

public class Main {
    public static final int[] DX = new int[] { 1, 0, -1, 0 };
    public static final int[] DY = new int[] { 0, 1, 0, -1 };
    public static final int EMPTY = -1;

    //(x, y)가 board 내부인지 확인
    public static boolean isValid(int[][] board, int x, int y) {
        if(x < 0 || y < 0) return false;
        if(x < board.length && y < board[0].length) return true;
        return false;
    }

    //board에서 time초가 지났을 때의 폭발
    public static void detonate(int[][] board, int time) {
        int len = board.length;
        int distance = (int) Math.pow(2, time - 1);
        int i, j, x, y, dir;
        //모든 칸에 대하여 실행
        for(i=0; i<len; i++) {
            for(j=0; j<len; j++) {
                if(board[i][j] == EMPTY || board[i][j] == time) continue; //폭탄이 없거나 지금 생성된 경우 터지지 않음
                //4방향으로 폭탄 생성
                for(dir=0; dir<4; dir++) {
                    x = i + distance * DX[dir];
                    y = j + distance * DY[dir];
                    if(isValid(board, x, y) && board[x][y] == EMPTY) board[x][y] = time; //빈 칸에 폭탄 생성
                }
            }
        }
    }

    //board에 있는 폭탄의 개수 출력
    public static void print(int[][] board) {
        int count = 0;
        for(int[] line : board) {
            for(int bomb : line) {
                if(bomb != EMPTY) count++;
            }
        }
        System.out.println(count);
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //board 크기
        int m = sc.nextInt(); //진행 시간
        int r = sc.nextInt(); //첫 폭탄의 행(1 ~ n)
        int c = sc.nextInt(); //첫 폭탄의 열(1 ~ n)
        int[][] board = new int[n][n];
        int i, j;
        for(i=0; i<n; i++) {
            for(j=0; j<n; j++) board[i][j] = EMPTY;
        }
        board[r - 1][c - 1] = 0;
        for(i=1; i<=m; i++) detonate(board, i);
        print(board);
    }
}