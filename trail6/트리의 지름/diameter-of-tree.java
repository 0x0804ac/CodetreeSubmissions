import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.LinkedList;

class Edge {
    private int d; //도착 정점
    private int l; //간선 길이

    public Edge(int vertex, int length) {
        d = vertex;
        l = length;
    }

    public int getVertex() { return d; }
    public int getLength() { return l; }
}

class Tree {
    public static final int MAX = 1_000_000_000;
    private int v; //정점 수
    private LinkedList<Edge>[] e; //인접 리스트
    
    public Tree(int size) {
        v = size;
        e = new LinkedList[v + 1];
        for(int i=1; i<=v; i++) e[i] = new LinkedList<Edge>();
    }

    //vertex가 (1 ~ v) 범위 내에 있는지 검사
    public boolean isValid(int vertex) {
        if(vertex <= 0) return false;
        return vertex <= v;
    }

    public void addEdge(int vertex, Edge edge) {
        if(isValid(vertex)) e[vertex].add(edge);
    }

    //from에서 가장 먼 정점과 거리 정보를 Edge 객체에 담아서 반환
    public Edge getFurthestVertex(int from) {
        if(!isValid(from)) return null;
        int[] distance = new int[v + 1]; //from번 정점으로부터의 거리
        int i;
        //distance 초기화
        for(i=1; i<=v; i++) distance[i] = MAX;
        distance[from] = 0;
        dfs(distance, from); //distance 채우기 위해 DFS 실행
        int output = from; //최장 거리
        for(i=1; i<=v; i++) {
            if(distance[i] > distance[output]) output = i;
        }
        return new Edge(output, distance[output]);
    }

    //vertex번 정점에서 DFS 진행
    private void dfs(int[] arr, int vertex) {
        //연결된 모든 간선에 대하여 실행
        for(Edge edge : e[vertex]) {
            int v = edge.getVertex();
            //방문하지 않은 정점에 대하여 거리 기록
            if(arr[v] == 0) continue;
            if(arr[v] == MAX) {
                arr[v] = arr[vertex] + edge.getLength();
                dfs(arr, v); //경로를 따라 탐색
            }
        }
    }
}

public class Main {
    public static void main(String[] args) throws IOException, NumberFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine()); //2 ~ 100_000
        Tree t = new Tree(n); //n개의 정점
        //(n - 1)개의 간선
        for(int i=1; i<n; i++) {
            String[] line = br.readLine().split(" ");
            int v1 = Integer.parseInt(line[0]); //정점 1
            int v2 = Integer.parseInt(line[1]); //정점 2
            int w = Integer.parseInt(line[2]); //간선 길이(1 ~ 10_000)
            //양방향 간선 추가
            t.addEdge(v1, new Edge(v2, w));
            t.addEdge(v2, new Edge(v1, w));
        }
        Edge end = t.getFurthestVertex(1); //아무 정점에서 시작해서 가장 먼 정점으로 이동
        //이동한 곳에서 다시 가장 먼 곳으로 이동 후 그 때의 이동 거리 출력
        System.out.println(t.getFurthestVertex(end.getVertex()).getLength());
    }
}