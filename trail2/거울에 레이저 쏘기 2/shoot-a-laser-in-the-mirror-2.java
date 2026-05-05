import java.util.Scanner;

public class Main {
    //아래쪽, 오른쪽, 위쪽, 왼쪽
    public static final int DOWN = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int LEFT = 3;

    public static final int[] DX = new int[] { 1, 0, -1, 0 };
    public static final int[] DY = new int[] { 0, 1, 0, -1 };
    
    public static int mirror(char direction, int from) {
        if(direction == '/') return 3 - from; //0 <-> 3, 1 <-> 2
        else if(direction == '\\') return from ^ 1; //0 <-> 1, 2 <-> 3
        else return -1;
    }
    
    public static boolean isOutOfBounds(char[][] grid, int i, int j) {
        int len = grid[0].length;
        if(i <= 0 || j <= 0) return true;
        return i >= len - 1 || j >= len - 1;
    }

    public static int fire(char[][] grid, int x, int y, int dir) {
        int result = 0;
        while(true) {
            x += DX[dir];
            y += DY[dir];
            if(isOutOfBounds(grid, x, y)) break; //격자를 벗어나면 종료
            result++; //튕기기
            dir = mirror(grid[x][y], dir);
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        char[][] grid = new char[n + 2][n + 2]; //격자 내부 index: 1 ~ n
        int i, j;
        for(i=0; i<grid.length; i++) {
            for(j=0; j<grid[i].length; j++) grid[i][j] = '0';
        }
        for(i=1; i<=n; i++) {
            String s = sc.next();
            for(j=1; j<=n; j++) grid[i][j] = s.charAt(j - 1);
        }
        int startPos = sc.nextInt();
        int output = -1;
        if(startPos > 0 && startPos <= n) output = fire(grid, 0, startPos, DOWN);
        else if(startPos <= n * 2) output = fire(grid, startPos - n, n + 1, LEFT);
        else if(startPos <= n * 3) output = fire(grid, n + 1, n * 3 - startPos + 1, UP);
        else if(startPos <= n * 4) output = fire(grid, n * 4 - startPos + 1, 0, RIGHT);
        System.out.println(output);
    }
}