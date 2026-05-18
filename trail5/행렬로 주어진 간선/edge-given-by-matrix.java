import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, NumberFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine()); //정점 수(1 ~ 100)
        int[][] edges = new int[n + 1][n + 1]; //인접 행렬로 나타낸 간선
        boolean[][] path = new boolean[n + 1][n + 1]; //path[i][j]: 정점 i에서 정점 j까지의 경로 존재 여부
        int i, j, k;
        //edges 입력
        for(i=1; i<=n; i++) {
            String[] line = br.readLine().split(" ");
            for(j=1; j<=n; j++) edges[i][j] = Integer.parseInt(line[j - 1]); //0: 간선 없음, 1: 간선 있음
        }
        //path 초기화
        for(i=1; i<=n; i++) {
            for(j=1; j<=n; j++) {
                if(i == j) path[i][j] = true;
                else path[i][j] = edges[i][j] == 1;
            }
        }
        //path 채우기
        for(k=1; k<=n; k++) {
            for(i=1; i<=n; i++) {
                for(j=1; j<=n; j++) {
                    //i -> k -> j 경로가 존재하면 i -> j 경로가 존재함
                    if(path[i][k] && path[k][j]) path[i][j] = true;
                }
            }
        }
        //경로 유무 출력
        for(i=1; i<=n; i++) {
            for(j=1; j<=n; j++) {
                k = path[i][j] ? 1 : 0;
                System.out.print(k + " "); //0: 경로 없음, 1: 경로 있음
            }
            System.out.println();
        }
    }
}