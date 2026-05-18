import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;

class Grid {
    public static final int[] DX = { 0, 1, 0, -1 };
    public static final int[] DY = { 1, 0, -1, 0 };

    private LinkedList<Tile> bfs;
    private Tile[][] g;
    private int r;
    private int c;

    public Grid(int row, int column) {
        bfs = new LinkedList<Tile>();
        r = row;
        c = column;
        g = new Tile[r][c];
    }

    public int getRows() { return r; }
    public int getColumns() { return c; }

    //(index1, index2)가 격자 내부에 있는지 검사
    public boolean isValidIndex(int index1, int index2) {
        if(index1 < 0 || index2 < 0) return false;
        return index1 < r && index2 < c;
    }
    
    public Tile getTile(int row, int column) {
        if(isValidIndex(row, column)) return g[row][column];
        else return null;
    }

    public void setTile(int row, int column, Tile tile) {
        if(isValidIndex(row, column)) g[row][column] = tile;
    }

    //시작 지점 (0, 0)
    public Tile getStartTile() {
        return g[0][0];
    }

    //도착 지점 (r - 1, c - 1)
    public Tile getEndTile() {
        return g[r - 1][c - 1];
    }

    //(row, column)을 BFS 큐에 추가
    public boolean enqueue(int row, int column) {
        if(isValidIndex(row, column)) {
            Tile tile = g[row][column];
            if(tile.isVisited()) return false;
            else {
                tile.setVisited(true);
                bfs.add(tile);
                return true;
            }
        }
        else return false;
    }

    //BFS 큐에서 꺼냄
    public Tile dequeue() {
        return bfs.poll();
    }

    //다음 BFS를 위해 방문 기록 초기화
    public void resetVisited() {
        for(Tile[] line : g) {
            for(Tile t : line) t.setVisited(false);
        }
    }
}

class Tile {
    private int r;
    private int c;
    private int h;
    private boolean visited;
    private int[] bfs;

    public Tile(int row, int column, int height) {
        r = row;
        c = column;
        h = height;
        visited = false;
        bfs = new int[2];
    }

    public int getRow() { return r; }
    public int getColumn() { return c; }
    public int getHeight() { return h; }
    public boolean isVisited() { return visited; }
    public int getMin() { return bfs[0]; }
    public int getMax() { return bfs[1]; }

    public void setVisited(boolean value) { visited = value; }
    public void setMin(int value) { bfs[0] = value; }
    public void setMax(int value) { bfs[1] = value; }
}

public class Main {
    public static final String SPACE = " ";

    public static void main(String[] args) throws IOException, NumberFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(SPACE);
        int n = Integer.parseInt(line[0]); //1 ~ 100
        int m = Integer.parseInt(line[1]); //1 ~ 100
        Grid grid = new Grid(n, m);
        int[] bfs = new int[2];
        int low = 500, high = 1;
        int i, j;
        for(i=0; i<n; i++) {
            line = br.readLine().split(SPACE);
            for(j=0; j<m; j++) {
                int input = Integer.parseInt(line[j]); //1 ~ 500
                grid.setTile(i, j, new Tile(i, j, input));
                low = Math.min(low, input); //최솟값 저장
                high = Math.max(high, input); //최댓값 저장
            }
        }
        int s = grid.getStartTile().getHeight(), e = grid.getEndTile().getHeight(), se = Math.min(s, e);
        int left = Math.abs(s - e); //가능한 답의 최솟값(출발지와 도착지만 고려)
        int right = high - low; //가능한 답의 최댓값(모든 칸에 대해 고려)
        int output = right; //(최대 높이) - (최소 높이)의 최솟값
        //답을 mid로 가정하고 계산
        while(left <= right) {
            int mid = (left + right) / 2;
            Tile tile = grid.getStartTile();
            //최솟값을 i, 최댓값을 i + mid로 가정
            for(i=low; i<=se; i++) {
                bfs[0] = i;
                bfs[1] = i + mid;
                if(bfs[1] > high) break;
                //시작 지점(0, 0)을 큐에 추가
                tile.setMin(s);
                tile.setMax(s);
                grid.enqueue(0, 0);
                //BFS
                while(true) {
                    tile = grid.dequeue();
                    //더 이상 진행할 수 없으면 종료
                    if(tile == null) break;
                    //4방향으로 탐색
                    for(j=0; j<4; j++) {
                        int x = tile.getRow() + Grid.DX[j];
                        int y = tile.getColumn() + Grid.DY[j];
                        Tile next = grid.getTile(x, y);
                        if(next != null && !next.isVisited()) {
                            int h = next.getHeight(); //현재 높이
                            int nextMin = Math.min(tile.getMin(), h); //높이의 최솟값 갱신
                            int nextMax = Math.max(tile.getMax(), h); //높이의 최댓값 갱신
                            //미리 정해놓은 (최솟값, 최댓값) 범위 안에 있다면 이동 가능
                            if(bfs[0] <= nextMin && nextMax <= bfs[1]) {
                                next.setMin(nextMin);
                                next.setMax(nextMax);
                                grid.enqueue(x, y);
                            }
                        }
                    }
                }
                tile = grid.getEndTile();
                //도착 지점으로 이동 성공했다면 답 갱신
                if(tile.isVisited()) {
                    output = Math.min(output, tile.getMax() - tile.getMin());
                    right = output - 1; //더 적은 높이 차를 답으로 가정 후 재시도
                    break;
                }
                else grid.resetVisited();
            }
            if(tile.isVisited()) grid.resetVisited();
            else left = mid + 1; //이동하지 못했다면 더 큰 높이 차를 답으로 가정 후 재시도
        }
        System.out.println(output);
    }
}
