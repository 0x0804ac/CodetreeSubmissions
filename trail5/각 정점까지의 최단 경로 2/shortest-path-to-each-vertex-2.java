import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
    public static final int MAX = 1_000_000_000;
    public static final String SPACE = " ";

    public static void main(String[] args) throws IOException, NumberFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(SPACE);
        int n = Integer.parseInt(line[0]); //정점 수(1 ~ 100)
        int m = Integer.parseInt(line[1]); //간선 수(1 ~ 100_000)
        int[][] edges = new int[n + 1][n + 1]; //인접 행렬로 나타낸 간선
        int[][] distance = new int[n + 1][n + 1]; //distance[i][j]: 정점 i에서 정점 j까지의 최단 거리
        int i, j, k, u, v, w;
        //edges 초기화
        for(i=1; i<=n; i++) {
            for(j=1; j<=n; j++) edges[i][j] = -1;
        }
        //m개의 간선
        for(i=0; i<m; i++) {
            line = br.readLine().split(SPACE);
            u = Integer.parseInt(line[0]); //시작 정점
            v = Integer.parseInt(line[1]); //끝 정점
            w = Integer.parseInt(line[2]); //간선 길이(1 ~ 100_000)
            if(edges[u][v] == -1) edges[u][v] = w; //새 간선 등록
            else edges[u][v] = Math.min(edges[u][v], w); //같은 경로의 더 긴 간선은 사용되지 않음
        }
        //distance 초기화
        for(i=1; i<=n; i++) {
            for(j=1; j<=n; j++) {
                if(i == j) distance[i][j] = 0;
                else {
                    k = edges[i][j];
                    if(k > 0) distance[i][j] = k;
                    else distance[i][j] = MAX;
                }
            }
        }
        //최단 경로 갱신
        for(k=1; k<=n; k++) {
            for(i=1; i<=n; i++) {
                for(j=1; j<=n; j++) {
                    //i -> j보다 i -> k -> j가 더 짧은지 확인
                    distance[i][j] = Math.min(distance[i][j], distance[i][k] + distance[k][j]);
                }
            }
        }
        //최단 거리(도달 불가능하면 -1) 출력
        for(i=1; i<=n; i++) {
            for(j=1; j<=n; j++) {
                k = (distance[i][j] == MAX) ? -1 : distance[i][j];
                System.out.print(k + " ");
            }
            System.out.println();
        }
    }
}