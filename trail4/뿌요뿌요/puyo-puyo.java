import java.util.Scanner;

class Grid {
    public static final int[] DX = new int[] { 1, 0, -1, 0 };
    public static final int[] DY = new int[] { 0, 1, 0, -1 };
    public static final int INVALID = -1;
    public static final int LARGE_SIZE = 4; //4칸 이상으로 이루어진 블록은 폭발

    private int len;
    private int[][] blocks;
    private boolean[][] visited;
    private int block; //블록 크기
    private int count; //폭발하는 블록 개수
    private int output; //가장 큰 블록의 크기

    public Grid(int size) {
        len = size;
        blocks = new int[len][len];
        visited = new boolean[len][len];
        block = 0;
        count = 0;
        output = 0;
    }

    //index가 (0 ~ len - 1) 범위 안에 있는지 검사
    public boolean isValidIndex(int index) {
        if(index < 0) return false;
        return index < len;
    }

    public int getBlock(int row, int column) {
        if(isValidIndex(row) && isValidIndex(column)) return blocks[row][column];
        else return INVALID;
    }

    public void setBlock(int row, int column, int value) {
        if(isValidIndex(row) && isValidIndex(column) && value > 0) blocks[row][column] = value;
    }

    //모든 칸에 대하여 DFS 탐색
    public void search() {
        int i, j;
        for(i=0; i<len; i++) {
            for(j=0; j<len; j++) {
                if(!visited[i][j]) {
                    visited[i][j] = true;
                    block = 1;
                    dfs(i, j, blocks[i][j]);
                    output = Math.max(output, block);
                    if(block >= LARGE_SIZE) count++;
                }
            }
        }
        System.out.println(count + " " + output);
    }

    //현재 (row, column) 칸에서 숫자가 number인 블록을 DFS 탐색
    private void dfs(int row, int column, int number) {
        for(int i=0; i<4; i++) {
            int x = row + DX[i];
            int y = column + DY[i];
            if(isValidIndex(x) && isValidIndex(y) && blocks[x][y] == number && !visited[x][y]) {
                visited[x][y] = true;
                block++;
                dfs(x, y, number);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //격자 크기(1 ~ 100)
        Grid g = new Grid(n);
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) g.setBlock(i, j, sc.nextInt()); //블록에 쓰인 숫자(1 ~ 100)
        }
        g.search();
    }
}