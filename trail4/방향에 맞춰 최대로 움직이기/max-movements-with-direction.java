import java.util.Scanner;

class Tile {
    public static final int[] DX = new int[] { -1, -1, 0, 1, 1, 1, 0, -1 };
    public static final int[] DY = new int[] { 0, 1, 1, 1, 0, -1, -1, -1 };

    private int v;
    private int d;

    public Tile(int number, int direction) {
        v = number;
        d = direction;
    }

    public int getValue() { return v; }

    public void setDirection(int direction) {
        if(direction > 0 && direction <= DX.length) d = direction;
    }

    public int getDx() { return DX[d - 1]; }
    public int getDy() { return DY[d - 1]; }
}

class Board {
    private Tile[][] grid;
    private int len;
    private int moves;

    public Board(int size) {
        len = size;
        grid = new Tile[len][len];
    }

    //(x, y)가 격자 내부에 있는지 검사
    public boolean isValidTile(int x, int y) {
        if(x < 0 || y < 0) return false;
        return x < len && y < len;
    }

    public Tile getTile(int x, int y) {
        return isValidTile(x, y) ? grid[x][y] : null;
    }

    public void setTile(int x, int y, Tile tile) {
        if(isValidTile(x, y)) grid[x][y] = tile;
    }

    //(x, y)에서 시작해서 최대로 움직일 수 있는 횟수
    public int move(int x, int y) {
        moves = 0;
        if(!isValidTile(x, y)) return -1;
        moves = func(x, y);
        return moves;
    }

    private int func(int x, int y) {
        int output = 0;
        Tile tile = grid[x][y];
        int value = tile.getValue();
        int nextX = x + tile.getDx();
        int nextY = y + tile.getDy();
        //가리키는 방향으로 계속 전진
        while(isValidTile(nextX, nextY)) {
            Tile nextTile = getTile(nextX, nextY);
            //현위치의 값이 더 크면 이 타일을 사용한 경로 탐색
            if(value < nextTile.getValue()) output = Math.max(output, func(nextX, nextY) + 1);
            nextX += tile.getDx();
            nextY += tile.getDy();
        }
        return output;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //격자 크기(1 ~ 4)
        Board board = new Board(n);
        int i, j, num, dir;
        for(i=0; i<n; i++) {
            for(j=0; j<n; j++) {
                num = sc.nextInt(); //칸에 쓰인 값(1 ~ n*n, 중복 없음)
                board.setTile(i, j, new Tile(num, 1));
            }
        }
        for(i=0; i<n; i++) {
            for(j=0; j<n; j++) {
                dir = sc.nextInt(); //칸에 쓰인 방향(1 ~ 8)
                board.getTile(i, j).setDirection(dir);
            }
        }
        int r = sc.nextInt(); //시작 행(1 ~ n)
        int c = sc.nextInt(); //시작 열(1 ~ n)
        System.out.println(board.move(r - 1, c - 1));
    }
}