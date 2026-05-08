import java.util.ArrayDeque;
import java.util.Scanner;

public class Main {
    public static final int RIGHT = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int UP = 3;
    public static final int[] DX = new int[] { 0, 1, 0, -1 };
    public static final int[] DY = new int[] { 1, 0, -1, 0 };
    public static final int APPLE = 8;
    public static ArrayDeque<String> snake = new ArrayDeque<String>();
    public static int time = 0;

    //(x, y)가 board 내부인지 검사
    public static boolean isValid(int[][] board, int x, int y) {
        if(x < 0 || y < 0) return false;
        return x < board.length && y < board[0].length;
    }

    //정수 x와 y를 "x,y" 형식의 문자열로 변환
    public static String coordsToString(int x, int y) {
        return "" + x + "," + y;
    }

    //board 내에서 뱀을 주어진 방향으로 움직임
    public static boolean move(int[][] board, char direction) {
        int index = -1;
        switch(direction) {
            case 'U':
            index = UP;
            break;
            case 'D':
            index = DOWN;
            break;
            case 'R':
            index = RIGHT;
            break;
            case 'L':
            index = LEFT;
            break;
            default:
            return false; //입력이 올바르다면 사용되지 않음
        }
        String[] head = snake.peekFirst().split(",");
        int x = Integer.parseInt(head[0]), y = Integer.parseInt(head[1]);
        int nextX = x + DX[index], nextY = y + DY[index];
        String next = coordsToString(nextX, nextY);
        time++;
        if(!isValid(board, nextX, nextY)) return false; //board를 벗어나면 종료
        if(board[nextX][nextY] == APPLE) board[nextX][nextY] = 0; //사과 먹기(길이 증가)
        else snake.pollLast(); //이동하기 위해 꼬리 제거
        if(snake.contains(next)) return false; //몸에 충돌하면 종료
        snake.offerFirst(next); //머리를 추가하여 이동 성공
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //board 크기
        int m = sc.nextInt(); //사과 수
        int k = sc.nextInt(); //움직임 수
        int[][] board = new int[n][n];
        int i, j, moves;
        char direction;
        for(i=0; i<m; i++) board[sc.nextInt() - 1][sc.nextInt() - 1] = APPLE;
        snake.offerFirst(coordsToString(0, 0)); //뱀의 초기 상태: 길이 1, 위치 (0, 0)
        for(i=0; i<k; i++) {
            direction = sc.next().charAt(0);
            moves = sc.nextInt();
            for(j=0; j<moves; j++) {
                //조기 종료
                if(!move(board, direction)) {
                    System.out.println(time);
                    return;
                }
            }
        }
        System.out.println(time); //정상 종료
    }
}