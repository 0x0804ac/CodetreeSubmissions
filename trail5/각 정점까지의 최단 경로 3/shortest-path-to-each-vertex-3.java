import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

class Graph {
    public static final int MAX_DISTANCE = 1_000_000_000;

    private int vertices; //정점의 수
    private int[][] adjacentMatrix; //인접 행렬
    private int[] distance; //정점까지의 거리
    private boolean[] visited; //정점 방문 여부

    public Graph(int count) {
        vertices = count;
        adjacentMatrix = new int[vertices + 1][vertices + 1];
        distance = new int[vertices + 1];
        visited = new boolean[vertices + 1];
    }

    public int getVertexCount() { return vertices; }
    
    public void addEdge(int from, int to, int weight) {
        if(isValidIndex(from) && isValidIndex(to) && weight > 0) adjacentMatrix[from][to] = weight;
    }

    public void removeEdge(int from, int to) {
        if(isValidIndex(from) && isValidIndex(to) && adjacentMatrix[from][to] > 0) adjacentMatrix[from][to] = 0;
    }

    public boolean hasEdge(int from, int to) {
        return isValidIndex(from) && isValidIndex(to) && adjacentMatrix[from][to] > 0;
    }

    public int getEdge(int from, int to) {
        if(hasEdge(from, to)) return adjacentMatrix[from][to];
        else return -1;
    }

    public boolean isVisited(int vertex) {
        return isValidIndex(vertex) && visited[vertex];
    }

    public void setVisited(int vertex, boolean value) {
        if(isValidIndex(vertex)) visited[vertex] = value;
    }

    public int getDistance(int vertex) {
        if(isValidIndex(vertex)) return distance[vertex];
        else return -1;
    }

    public void setDistance(int vertex, int value) {
        if(isValidIndex(vertex)) distance[vertex] = value;
    }

    //아직 방문하지 않은 정점 중에서 저장된 거리 값이 가장 작은 정점
    public int getNearestUnvisited() {
        int output = 0, min = MAX_DISTANCE;
        for(int i=1; i<=vertices; i++) {
            if(!visited[i] && distance[i] < min) {
                output = i;
                min = distance[i];
            }
        }
        return output;
    }

    //index가 (1 ~ vertices) 범위 내에 있는지 검사
    public boolean isValidIndex(int index) {
        if(index <= 0) return false;
        return index <= vertices;
    }
}

public class Main {
    public static final String SPACE = " ";

    public static void main(String[] args) throws IOException, NumberFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(SPACE);
        int n = Integer.parseInt(line[0]); //정점 수(1 ~ 100)
        Graph g = new Graph(n);
        int i, j;
        int m = Integer.parseInt(line[1]); //간선 수(1 ~ 1000)
        for(i=0; i<m; i++) {
            line = br.readLine().split(SPACE);
            int u = Integer.parseInt(line[0]); //시작 정점
            int v = Integer.parseInt(line[1]); //끝 정점
            int w = Integer.parseInt(line[2]); //가중치(1 ~ 10)
            g.addEdge(u, v, w); //단방향 간선 추가(동일한 간선이 여러 번 주어지지 않음)
        }
        for(i=2; i<=n; i++) g.setDistance(i, Graph.MAX_DISTANCE); //distance 초기화(1번 정점만 0으로 설정)
        //n개의 정점에 대하여 탐색
        for(j=0; j<n; j++) {
            int next = g.getNearestUnvisited();
            if(g.isValidIndex(next)) {
                //next번 정점을 방문하면서 distance 갱신
                g.setVisited(next, true);
                for(i=1; i<=n; i++) {
                    if(g.hasEdge(next, i)) {
                        g.setDistance(i, Math.min(g.getDistance(i), g.getDistance(next) + g.getEdge(next, i)));
                    }
                }
            }
        }
        //1번 정점부터 (2 ~ n)번 정점까지의 최단 거리 출력
        for(i=2; i<=n; i++) {
            int output = g.getDistance(i);
            if(output == Graph.MAX_DISTANCE) System.out.println(-1); //도달 불가능할 경우 -1 출력
            else System.out.println(output);
        }
    }
}