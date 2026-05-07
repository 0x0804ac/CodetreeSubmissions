import java.util.Scanner;

public class Main {
    public static final int[] LINE = new int[4];
    public static final char UP = 'U';
    public static final char DOWN = 'D';
    public static final char LEFT = 'L';
    public static final char RIGHT = 'R';

    //한 줄의 숫자 합치기
    public static void mergeLine(int n1, int n2, int n3, int n4) {
        if(n1 == n2 && n1 > 0) {
            n1 += n2;
            n2 = 0;
        }
        if(n2 == n3 && n2 > 0) {
            n2 += n3;
            n3 = 0;
        }
        if(n3 == n4 && n3 > 0) {
            n3 += n4;
            n4 = 0;
        }
        LINE[0] = n1; LINE[1] = n2; LINE[2] = n3; LINE[3] = n4;
    }

    //direction 방향으로 숫자 합치기
    public static void merge(int[][] board, char direction) {
        int i, j;
        switch(direction) {
            case UP:
            for(i=0; i<4; i++) {
                mergeLine(board[0][i], board[1][i], board[2][i], board[3][i]);
                for(j=0; j<4; j++) board[j][i] = LINE[j];
            }
            break;
            case DOWN:
            for(i=0; i<4; i++) {
                mergeLine(board[3][i], board[2][i], board[1][i], board[0][i]);
                for(j=0; j<4; j++) board[3 - j][i] = LINE[j];
            }
            break;
            case LEFT:
            for(i=0; i<4; i++) {
                mergeLine(board[i][0], board[i][1], board[i][2], board[i][3]);
                for(j=0; j<4; j++) board[i][j] = LINE[j];
            }
            break;
            case RIGHT:
            for(i=0; i<4; i++) {
                mergeLine(board[i][3], board[i][2], board[i][1], board[i][0]);
                for(j=0; j<4; j++) board[i][3 - j] = LINE[j];
            }
            break;
        }
    }

    //한 줄의 숫자를 왼쪽으로 밀기
    public static void pushLine(int n1, int n2, int n3, int n4) {
        int i;
        for(i=4; i>0; i--) LINE[i - 1] = 0;
        if(n1 > 0) LINE[i++] = n1;
        if(n2 > 0) LINE[i++] = n2;
        if(n3 > 0) LINE[i++] = n3;
        if(n4 > 0) LINE[i] = n4;
    }

    //board를 direction 방향으로 밀기
    public static void push(int[][] board, char direction) {
        int i, j;
        switch(direction) {
            case UP:
            for(i=0; i<4; i++) {
                pushLine(board[0][i], board[1][i], board[2][i], board[3][i]);
                for(j=0; j<4; j++) board[j][i] = LINE[j];
            }
            break;
            case DOWN:
            for(i=0; i<4; i++) {
                pushLine(board[3][i], board[2][i], board[1][i], board[0][i]);
                for(j=0; j<4; j++) board[3 - j][i] = LINE[j];
            }
            break;
            case LEFT:
            for(i=0; i<4; i++) {
                pushLine(board[i][0], board[i][1], board[i][2], board[i][3]);
                for(j=0; j<4; j++) board[i][j] = LINE[j];
            }
            break;
            case RIGHT:
            for(i=0; i<4; i++) {
                pushLine(board[i][3], board[i][2], board[i][1], board[i][0]);
                for(j=0; j<4; j++) board[i][3 - j] = LINE[j];
            }
            break;
        }
    }

    public static void move(int[][] board, char direction) {
        push(board, direction);
        merge(board, direction);
        push(board, direction);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[][] grid = new int[4][4];
        int i, j;
        for(i=0; i<4; i++) {
            for(j=0; j<4; j++) grid[i][j] = sc.nextInt(); //2의 거듭제곱
        }
        char dir = sc.next().charAt(0);
        move(grid, dir);
        for(i=0; i<4; i++) {
            for(j=0; j<4; j++) System.out.print(grid[i][j] + " ");
            System.out.println();
        }
    }
}
