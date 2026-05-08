import java.util.Scanner;

public class Main {
    public static final int TOP = 0;
    public static final int UP = 1;
    public static final int LEFT = 2;
    public static final int DOWN = 3;
    public static final int RIGHT = 4;
    public static final int BOTTOM = 5;
    public static final int X = 6;
    public static final int Y = 7;
    public static final int[] DX = new int[] { 0, -1, 0, 1 };
    public static final int[] DY = new int[] { 1, 0, -1, 0 };

    public static int[] temp = new int[6];

    //(x, y)가 board 내부인지 검사
    public static boolean isValid(int[][] board, int x, int y) {
        if(x < 0 || y < 0) return false;
        if(x < board.length && y < board[0].length) return true;
        return false;
    }

    //주사위 굴리기
    public static void roll(int[] dice, int direction) {
        System.arraycopy(dice, 0, temp, 0, 6);
        switch(direction) {
            case RIGHT % 4:
            temp[TOP] = dice[LEFT]; temp[UP] = dice[UP]; temp[LEFT] = dice[BOTTOM];
            temp[DOWN] = dice[DOWN]; temp[RIGHT] = dice[TOP]; temp[BOTTOM] = dice[RIGHT];
            break;
            case DOWN:
            temp[TOP] = dice[UP]; temp[UP] = dice[BOTTOM]; temp[LEFT] = dice[LEFT];
            temp[DOWN] = dice[TOP]; temp[RIGHT] = dice[RIGHT]; temp[BOTTOM] = dice[DOWN];
            break;
            case LEFT:
            temp[TOP] = dice[RIGHT]; temp[UP] = dice[UP]; temp[LEFT] = dice[TOP];
            temp[DOWN] = dice[DOWN]; temp[RIGHT] = dice[BOTTOM]; temp[BOTTOM] = dice[LEFT];
            break;
            case UP:
            temp[TOP] = dice[DOWN]; temp[UP] = dice[TOP]; temp[LEFT] = dice[LEFT];
            temp[DOWN] = dice[BOTTOM]; temp[RIGHT] = dice[RIGHT]; temp[BOTTOM] = dice[UP];
            break;
            default:
            return;
        }
        System.arraycopy(temp, 0, dice, 0, 6);
    }

    //주사위의 밑면에 쓰인 숫자를 바닥에 마킹
    public static void mark(int[][] board, int[] dice) {
        board[dice[X]][dice[Y]] = dice[BOTTOM];
    }

    //board 위의 주사위를 주어진 방향으로 굴리기
    public static void move(int[][] board, int[] dice, String direction) {
        int index = -1;
        switch(direction) {
            case "L":
            index = LEFT;
            break;
            case "R":
            index = RIGHT % 4;
            break;
            case "U":
            index = UP;
            break;
            case "D":
            index = DOWN;
            break;
            default:
            return;
        }
        int nextX = dice[X] + DX[index], nextY = dice[Y] + DY[index];
        if(!isValid(board, nextX, nextY)) return; //주사위를 굴리면 board 밖으로 나갈 경우 굴리지 않음
        dice[X] = nextX;
        dice[Y] = nextY;
        roll(dice, index);
        mark(board, dice);
    }

    //board에 적힌 숫자의 합 출력
    public static void print(int[][] board) {
        int sum = 0;
        for(int[] line : board) {
            for(int tile : line) sum += tile;
        }
        System.out.println(sum);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //board 크기
        int m = sc.nextInt(); //주사위 굴리는 횟수
        int r = sc.nextInt(); //주사위 시작 행(1 ~ n)
        int c = sc.nextInt(); //주사위 시작 열(1 ~ n)
        int[][] board = new int[n][n];
        int[] dice = new int[] { 1, 5, 4, 2, 3, 6, r - 1, c - 1 };
        mark(board, dice);
        for(int i=0; i<m; i++) move(board, dice, sc.next());
        print(board);
    }
}