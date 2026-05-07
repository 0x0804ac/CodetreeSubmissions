import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static int[] temp;

    public static void debug(int[][] board) {
        for(int[] line : board) System.out.println(Arrays.toString(line));
        System.out.println("= = = = DEBUG = = = =");
    }

    //board에 남은 폭탄의 개수
    public static int getBombs(int[][] board) {
        int output = 0;
        for(int[] line : board) {
            for(int bomb : line) {
                if(bomb > 0) output++;
            }
        }
        return output;
    }

    //중력 작용
    public static void gravity(int[][] board) {
        int len = temp.length;
        int i, j, index;
        //i열에 대하여 처리
        for(i=0; i<len; i++) {
            for(j=0; j<len; j++) temp[j] = 0;
            index = 0;
            for(j=0; j<len; j++) {
                if(board[j][i] > 0) temp[index++] = board[j][i];
            }
            for(j=0; j<len; j++) board[j][i] = temp[j];
        }
    }

    //한 줄의 폭탄을 폭발시킴
    public static void detonateLine(int[] bombs, int reqCombo) {
        if(bombs[0] == 0) return; //폭탄이 없다면 종료
        int len = bombs.length, value = bombs[0], combo = 1, index = 0;
        int i, j;
        //폭탄을 하나씩 순회
        for(i=1; i<len; i++) {
            if(bombs[i] == 0) break; //남은 폭탄이 없으면 종료
            if(bombs[i] == value) combo++; //연속
            else {
                //터지지 않은 폭탄은 임시 배열에 저장 후 연속 기록 초기화
                if(combo < reqCombo) {
                    for(j=0; j<combo; j++) temp[index++] = value;
                }
                value = bombs[i];
                combo = 1;
            }
        }
        //마지막 폭탄에 대하여 처리
        if(combo < reqCombo) {
            for(j=0; j<combo; j++) temp[index++] = value;
        }
        //임시 배열에서 불러옴
        for(i=0; i<len; i++) bombs[i] = 0;
        System.arraycopy(temp, 0, bombs, len - index, index);
    }

    //board의 모든 열에 대하여 연속으로 combo 개 있는 폭탄을 폭발시킴
    public static void detonate(int[][] board, int combo) {
        int len = board.length, count = getBombs(board);
        int[] temp = new int[len];
        int i, j, k;
        while(true) {
            for(i=0; i<len; i++) {
                for(j=0; j<len; j++) temp[j] = board[j][i];
                detonateLine(temp, combo);
                for(j=0; j<len; j++) board[j][i] = temp[j];
            }
            k = getBombs(board);
            if(k == count) break; //터진 폭탄이 없다면 종료
            count = k;
            gravity(board);
        }
        //debug(board);
    }

    //board를 시계 방향으로 90도 회전
    public static void rotate(int[][] board) {
        int len = board.length;
        int[][] copy = new int[len][len];
        int i, j;
        for(i=0; i<len; i++) System.arraycopy(board[i], 0, copy[i], 0, len); //2차원 배열 복사
        for(i=0; i<len; i++) {
            for(j=0; j<len; j++) board[i][j] = copy[len - 1 - j][i]; //회전시켜서 재배치
        }
        gravity(board);
        //debug(board);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int K = sc.nextInt();
        int[][] grid = new int[N][N];
        temp = new int[N];
        int i, j;
        for(i=0; i<N; i++) {
            for(j=0; j<N; j++) grid[i][j] = sc.nextInt();
        }
        for(i=0; i<K; i++) {
            detonate(grid, M);
            rotate(grid);
            //debug(grid);
        }
        detonate(grid, M);
        System.out.println(getBombs(grid));
    }
}