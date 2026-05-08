import java.util.Scanner;

public class Main {
    public static final int[] DX = new int[] { -1, 1, 0, 0 };
    public static final int[] DY = new int[] { 0, 0, -1, 1 };
    public static final char UP = 'U';
    public static final char DOWN = 'D';
    public static final char LEFT = 'L';
    public static final char RIGHT = 'R';
    public static final char EMPTY = 'E';
    public static final char COLLIDED = 'C';

    //(x, y)가 board 내부에 있는지 검사
    public static boolean isValid(char[][] board, int x, int y) {
        if(x < 0 || y < 0) return false;
        return x < board.length && y < board[0].length;
    }

    //dir의 반대 방향 출력
    public static char reverse(char dir) {
        switch(dir) {
            case UP:
            return DOWN;
            case DOWN:
            return UP;
            case RIGHT:
            return LEFT;
            case LEFT:
            return RIGHT;
            default:
            return dir;
        }
    }

    //board에서 1초 동안 이동 실행
    public static void move(char[][] board) {
        int len = board.length;
        char[][] temp = new char[len][len];
        int i, j, x, y, index;
        //초기화
        for(i=0; i<len; i++) {
            for(j=0; j<len; j++) temp[i][j] = EMPTY;
        }
        //한 칸씩 구슬 이동
        for(i=0; i<len; i++) {
            for(j=0; j<len; j++) {
                index = -1;
                if(board[i][j] == UP) index = 0;
                else if(board[i][j] == DOWN) index = 1;
                else if(board[i][j] == LEFT) index = 2;
                else if(board[i][j] == RIGHT) index = 3;
                else continue;
                x = i + DX[index];
                y = j + DY[index];
                //벽이 아니라면 전진
                if(isValid(board, x, y)) {
                    if(temp[x][y] == EMPTY) temp[x][y] = board[i][j]; //빈 칸이면 그냥 배치
                    else temp[x][y] = COLLIDED; //구슬이 있다면 충돌
                }
                //벽에 부딪히면 방향 전환
                else {
                    if(temp[i][j] == EMPTY) temp[i][j] = reverse(board[i][j]); //빈 칸이면 그냥 배치
                    else temp[i][j] = COLLIDED; //구슬이 있다면 충돌
                }
            }
        }
        //구슬 이동 결과 저장
        for(i=0; i<len; i++) {
            for(j=0; j<len; j++) {
                board[i][j] = temp[i][j];
                //if(board[i][j] == COLLIDED) board[i][j] = EMPTY;
            }
        }
    }

    //board에 남아있는 구슬의 개수 출력
    public static void print(char[][] board) {
        int count = 0;
        for(char[] line : board) {
            for(char marble : line) {
                if(marble == UP || marble == DOWN || marble == LEFT || marble == RIGHT) count++;
            }
        }
        System.out.println(count);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt(); //테스트 케이스 수
        int i;
        while(T-- > 0) {
            int N = sc.nextInt();
            char[][] grid = new char[N][N];
            int M = sc.nextInt();
            for(i=0; i<M; i++) grid[sc.nextInt() - 1][sc.nextInt() - 1] = sc.next().charAt(0); //구슬 입력
            for(i=0; i<2*N; i++) move(grid); //(2 * N)초 동안 구슬 이동
            print(grid);
        }
    }
}
