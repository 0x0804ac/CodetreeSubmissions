import java.util.ArrayList;
import java.util.Scanner;

class Graph {
    public static final int UNREACHABLE = 0;
    public static final int INVALID = -1;

    private int len;
    private int[][] edges;
    private boolean[] visited;
    private int output;
    private int start;

    public Graph(int size) {
        len = size;
        edges = new int[len][len];
        visited = new boolean[len];
        output = Integer.MAX_VALUE;
        start = INVALID;
    }

    public int size() { return len; }

    //index가 올바른 지점인지 검사(0 ~ len - 1)
    public boolean isValidIndex(int index) {
        if(index < 0) return false;
        return index < len;
    }
    
    public int getEdge(int from, int to) {
        if(isValidIndex(from) && isValidIndex(to)) return edges[from][to];
        else return INVALID;
    }

    public void setEdge(int from, int to, int value) {
        if(isValidIndex(from) && isValidIndex(to) && value >= 0) edges[from][to] = value;
    }

    public boolean isVisited(int vertex) {
        return isValidIndex(vertex) && visited[vertex];
    }

    public void setVisited(int vertex, boolean value) {
        if(isValidIndex(vertex)) visited[vertex] = value;
    }

    //지점 startPos에서 다른 모든 지점을 한 번씩 방문하고 돌아오기 위한 최소 비용
    public int traverse(int startPos) {
        if(!isValidIndex(startPos)) return INVALID;
        visited[startPos] = true;
        output = Integer.MAX_VALUE;
        start = startPos;
        func(0, len - 1, 0);
        visited[startPos] = false;
        return output;
    }

    //현재 지점 latestVisited, vertices개 더 방문해야 함, 지금까지의 비용 합: midSum
    private void func(int latestVisited, int vertices, int midSum) {
        if(!isValidIndex(latestVisited)) return;
        int i;
        //모두 방문했다면 시작 지점으로 돌아갈 수 있을 경우 최소 비용 갱신
        if(vertices == 0) {
            if(edges[latestVisited][start] != UNREACHABLE) {
                output = Math.min(output, midSum + edges[latestVisited][start]);
            }
            return;
        }
        //방문되지 않은 다른 모든 정점에 대하여 이동 가능할 경우 경로 탐색
        for(i=0; i<len; i++) {
            if(!visited[i] && edges[latestVisited][i] != UNREACHABLE) {
                visited[i] = true;
                func(i, vertices - 1, midSum + edges[latestVisited][i]);
                visited[i] = false;
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //지점의 개수(2 ~ 10)
        Graph g = new Graph(n);
        for(int i=0; i<n; i++) {
            //(i + 1)번 지점 -> (j + 1)번 지점 이동 비용(0 ~ 10000, 0이면 이동 불가)
            for(int j=0; j<n; j++) g.setEdge(i, j, sc.nextInt());
        }
        System.out.println(g.traverse(0)); //1번 지점에서 출발했을 때의 최소 비용
    }
}