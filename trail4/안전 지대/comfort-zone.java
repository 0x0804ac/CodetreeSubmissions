import java.util.Scanner;

class Village {
    public static final int INVALID = -1;
    public static final int[] DX = new int[] { 0, 1, 0, -1 };
    public static final int[] DY = new int[] { 1, 0, -1, 0 };

    private int r; //2차원 배열의 행 수
    private int c; //2차원 배열의 열 수
    private int[][] graph;
    private boolean[][] visited;

    public Village(int row, int column) {
        r = row;
        c = column;
        graph = new int[r][c];
        visited = new boolean[r][c];
    }

    //(row, column)이 마을 내부에 있는지 검사
    public boolean isInside(int row, int column) {
        if(row < 0 || column < 0) return false;
        return row < r && column < c;
    }

    public int getHeight(int row, int column) {
        if(isInside(row, column)) return graph[row][column];
        else return INVALID;
    }

    public void setHeight(int row, int column, int value) {
        if(isInside(row, column) && value > 0) graph[row][column] = value;
    }

    //강수량이 rain일 때 생기는 안전 영역의 개수
    public int getSafeZones(int rain) {
        int count = 0;
        int i, j;
        //모든 집에 대하여 탐색
        for(i=0; i<r; i++) {
            for(j=0; j<c; j++) {
                //잠기지 않은 집이 영역으로 지정되지 않았다면 새로운 영역 생성
                if(graph[i][j] > rain && !visited[i][j]) {
                    count++;
                    visited[i][j] = true;
                    dfs(i, j, rain); //영역 확장
                }
            }
        }
        //다음 탐색을 위해 영역 초기화
        for(i=0; i<r; i++) {
            for(j=0; j<c; j++) visited[i][j] = false;
        }
        return count;
    }

    //현재 (row, column) 집에서 DFS 진행 중, 강수량: height
    private void dfs(int row, int column, int height) {
        //네 방향으로 영역 확장 가능
        for(int i=0; i<4; i++) {
            int x = row + DX[i];
            int y = column + DY[i];
            if(isInside(x, y) && graph[x][y] > height && !visited[x][y]) {
                visited[x][y] = true;
                dfs(x, y, height);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //행(1 ~ 50)
        int m = sc.nextInt(); //열(1 ~ 50)
        Village v = new Village(n, m);
        int k = 1, output = 0;
        int i, j;
        for(i=0; i<n; i++) {
            for(j=0; j<m; j++) {
                int input = sc.nextInt(); //집의 높이(1 ~ 100)
                v.setHeight(i, j, input);
                k = Math.max(k, input); //최댓값 저장
            }
        }
        j = 1;
        //k의 최댓값은 (집 높이의 최댓값 - 1)
        for(i=1; i<k; i++) {
            int zones = v.getSafeZones(i);
            if(zones > output) {
                output = zones;
                j = i;
            }
        }
        System.out.println(j + " " + output); //j: k, output: 안전 영역의 수
    }
}