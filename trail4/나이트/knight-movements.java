import java.util.LinkedList;
import java.util.Scanner;

class Board {
    public static final int[] DX = {2, 1, -1, -2, -2, -1, 1, 2};
    public static final int[] DY = {1, 2, 2, 1, -1, -2, -2, -1};

    private int len;
    private int[][] bd;
    private LinkedList<int[]> q;

    public Board(int size) {
        len = size;
        bd = new int[len][len];
        for(int i=0; i<len; i++) {
            for(int j=0; j<len; j++) bd[i][j] = Integer.MAX_VALUE;
        }
        q = new LinkedList<int[]>();
    }

    //index가 (0 ~ len - 1) 범위 내에 있는지 검사
    public boolean isValidIndex(int index) {
        if(index < 0) return false;
        return index < len;
    }

    //나이트가 (fromRow, fromColumn)에서 (toRow, toColumn)으로 가기 위한 최소 이동 횟수
    public int move(int fromRow, int fromColumn, int toRow, int toColumn) {
        bfs(fromRow, fromColumn);
        return bd[toRow][toColumn];
    }

    private int[] arr(int n1, int n2) {
        return new int[] {n1, n2};
    }

    //(row, column)을 시작점으로 하는 BFS 실행
    private void bfs(int row, int column) {
        bd[row][column] = 0;
        q.add(arr(row, column));
        //나이트가 이동할 수 있는 8방향으로 탐색
        while(!q.isEmpty()) {
            int[] a = q.poll();
            int d = bd[a[0]][a[1]];
            for(int i=0; i<8; i++) {
                int x = a[0] + DX[i];
                int y = a[1] + DY[i];
                if(isValidIndex(x) && isValidIndex(y)) {
                    if(d + 1 < bd[x][y]) {
                        bd[x][y] = d + 1;
                        q.add(arr(x, y));
                    }
                }
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //격자 크기(1 ~ 100)
        Board b = new Board(n);
        int r1 = sc.nextInt() - 1; //시작 행(1 ~ n)
        int c1 = sc.nextInt() - 1; //시작 열(1 ~ n)
        int r2 = sc.nextInt() - 1; //끝 행(1 ~ n)
        int c2 = sc.nextInt() - 1; //끝 열(1 ~ n)
        int output = b.move(r1, c1, r2, c2);
        System.out.println(output == Integer.MAX_VALUE ? -1 : output);
    }
}