import java.util.LinkedList;
import java.util.Scanner;

class Vertex {
    private int id;
    private LinkedList<Vertex> adjacentList; //인접 리스트

    public Vertex(int id) {
        this.id = id;
        adjacentList = new LinkedList<Vertex>();
    }

    public int getId() { return id; }
    public LinkedList<Vertex> getAdjacentVertices() { return adjacentList; }

    public void addEdge(Vertex to) {
        if(!adjacentList.contains(to)) adjacentList.add(to);
        if(!to.adjacentList.contains(this)) to.adjacentList.add(this); //양방향 그래프이므로 반대 방향 간선 추가
    }

    public boolean hasEdge(Vertex to) {
        return adjacentList.contains(to);
    }
}

class Graph {
    private int count;
    private Vertex[] vertices;
    private boolean[] visited;

    public Graph(int size) {
        count = size;
        vertices = new Vertex[count + 1];
        visited = new boolean[count + 1];
    }

    //index가 (1 ~ count) 범위에 있는지 검사
    public boolean isValidIndex(int index) {
        if(index <= 0) return false;
        return index <= count;
    }

    public Vertex getVertex(int index) {
        if(isValidIndex(index)) return vertices[index];
        else return null;
    }

    public void setVertex(int index, Vertex value) {
        if(isValidIndex(index)) vertices[index] = value;
    }

    //startPos번 정점에서 도달할 수 있는 서로 다른 정점의 개수 출력
    public void search(int startPos) {
        if(!isValidIndex(startPos)) return;
        dfs(startPos);
        int count = -1; //자기 자신은 결과에서 제외
        for(boolean value : visited) {
            if(value) count++;
        }
        System.out.println(count);
        //for(int i=1; i<=len; i++) visited[i] = false;
    }

    //DFS 진행(현재 index번 정점 탐색 중)
    private void dfs(int index) {
        visited[index] = true; //정점 방문
        //연결된 모든 정점에 대하여 아직 방문되지 않았을 경우 방문
        for(Vertex v : vertices[index].getAdjacentVertices()) {
            int i = v.getId();
            if(!visited[i]) dfs(i);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //정점의 수(1 ~ 1000)
        int m = sc.nextInt(); //간선의 수(0 ~ min(10000, n(n-1)/2))
        Graph g = new Graph(n);
        int i;
        for(i=1; i<=n; i++) g.setVertex(i, new Vertex(i)); //i번 정점 추가
        for(i=0; i<m; i++) {
            Vertex u = g.getVertex(sc.nextInt());
            Vertex v = g.getVertex(sc.nextInt());
            u.addEdge(v); //간선 추가
        }
        g.search(1); //1번 정점에 대하여 탐색
    }
}