import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

class Graph {
    private int vertices; //정점 수
    private int[][] edges; //인접 행렬로 나타낸 간선
    private int[] distance; //특정 정점에서 다른 정점까지의 거리
    private int[] previous; //경로 탐색 중 이전 정점 저장
    private boolean[] visited; //경로 탐색 중 정점 방문 여부

    public Graph(int size) {
        vertices = size;
        edges = new int[vertices + 1][vertices + 1];
        distance = new int[vertices + 1];
        previous = new int[vertices + 1];
        visited = new boolean[vertices + 1];
    }

    public boolean isValid(int vertex) {
        if(vertex <= 0) return false;
        return vertex <= vertices;
    }

    public void addEdge(int from, int to, int weight) {
        if(isValid(from) && isValid(to) && weight > 0) edges[from][to] = weight;
    }

    public void printShortestPath(int from, int to) {
        if(isValid(from) && isValid(to)) {
            int i, j;
            //경로 탐색 정보 초기화
            for(i=1; i<=vertices; i++) {
                distance[i] = Integer.MAX_VALUE;
                previous[i] = Integer.MAX_VALUE;
                visited[i] = false;
            }
            //경로를 뒤집어서(양방향 그래프라서 간선을 뒤집지는 않음) 도착 정점에서 시작
            distance[to] = 0;
            for(i=1; i<=vertices; i++) {
                int next = 0, value = Integer.MAX_VALUE;
                //방문되지 않은 가장 가까운 정점 next 찾기
                for(j=1; j<=vertices; j++) {
                    if(visited[j]) continue;
                    if(distance[j] < value) {
                        next = j;
                        value = distance[j];
                    }
                }
                if(isValid(next)) {
                    visited[next] = true;
                    //정점 next와 연결된 모든 정점에 대하여 distance와 previous 갱신
                    for(j=1; j<=vertices; j++) {
                        int edge = edges[next][j];
                        if(edge > 0 && distance[j] > distance[next] + edge) {
                            distance[j] = distance[next] + edge;
                            previous[j] = next;
                        }
                    }
                }
            }
            System.out.println(distance[from]);
            int v = from;
            System.out.print(v + " ");
            while(v != to) {
                for(i=1; i<=vertices; i++) {
                    //i번 정점을 경유하는 것이 좋다면 바로 선택
                    if(edges[i][v] > 0 && distance[i] + edges[i][v] == distance[v]) {
                        v = i;
                        break;
                    }
                }
                System.out.print(v + " ");
            }
        }
    }
}

public class Main {
    public static final String SPACE = " ";

    public static void main(String[] args) throws IOException, NumberFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(SPACE);
        int n = Integer.parseInt(line[0]); //정점 수(1 ~ 1_000)
        int m = Integer.parseInt(line[1]); //간선 수(1 ~ 100_000)
        Graph g = new Graph(n);
        //m개의 간선(동일한 간선이 여러 번 주어지지 않음)
        for(int i=0; i<m; i++) {
            line = br.readLine().split(SPACE);
            int x = Integer.parseInt(line[0]); //정점 1
            int y = Integer.parseInt(line[1]); //정점 2
            int z = Integer.parseInt(line[2]); //길이(1 ~ 100_000)
            //양방향 간선 추가
            g.addEdge(x, y, z);
            g.addEdge(y, x, z);
        }
        line = br.readLine().split(SPACE);
        int a = Integer.parseInt(line[0]); //출발 정점
        int b = Integer.parseInt(line[1]); //도착 정점
        g.printShortestPath(a, b); //정점 a에서 정점 b까지의 사전순으로 가장 앞선 최단 경로 출력
    }
}