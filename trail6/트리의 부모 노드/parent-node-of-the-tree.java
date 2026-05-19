import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.LinkedList;

class Tree {
    private int vertices; //정점 수
    private int root; //루트 노드
    private LinkedList<Integer>[] edges; //인접 리스트
    private int[] parents; //부모 노드

    public Tree(int size) {
        vertices = size;
        root = 1;
        edges = new LinkedList[vertices + 1];
        parents = new int[vertices + 1];
        for(int i=1; i<=vertices; i++) edges[i] = new LinkedList<Integer>();
    }

    //vertex가 (1 ~ vertices) 내에 있는지 검사
    public boolean isValid(int vertex) {
        if(vertex <= 0) return false;
        return vertex <= vertices;
    }

    public void addEdge(int from, int to) {
        if(isValid(from) && isValid(to)) edges[from].add(to);
    }

    public void printParents() {
        dfs(root); //루트 노드에서 DFS 실행
        for(int i=2; i<=vertices; i++) System.out.println(parents[i]); //부모 노드 출력
    }

    private void dfs(int vertex) {
        if(isValid(vertex)) {
            for(int v : edges[vertex]) {
                if(v != root && parents[v] == 0) {
                    parents[v] = vertex;
                    dfs(v);
                }
            }
        }
    }
}

public class Main {
    public static void main(String[] args) throws IOException, NumberFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine()); //1 ~ 100_000
        Tree t = new Tree(n); //n개의 정점
        //(n - 1)개의 간선
        for(int i=1; i<n; i++) {
            String[] line = br.readLine().split(" ");
            int x = Integer.parseInt(line[0]);
            int y = Integer.parseInt(line[1]);
            t.addEdge(x, y);
            t.addEdge(y, x);
        }
        t.printParents();
    }
}