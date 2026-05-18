import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

class Point {
    int x;
    int y;
    
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Main {
    public static final String SPACE = " ";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(SPACE);
        int N = Integer.parseInt(input[0]); //점 수(1 ~ 2500)
        int Q = Integer.parseInt(input[1]); //질의 수(1 ~ 300_000)
        HashMap<Point, Integer> mapX = new HashMap<>(N * 4 / 3 + 2); //X좌표 압축 map
        HashMap<Point, Integer> mapY = new HashMap<>(N * 4 / 3 + 2); //Y좌표 압축 map
        TreeSet<Point> setX = new TreeSet<>((a, b) -> (a.x == b.x ? a.y - b.y : a.x - b.x)); //X좌표 우선 정렬
        TreeSet<Point> setY = new TreeSet<>((a, b) -> (a.y == b.y ? a.x - b.x : a.y - b.y)); //Y좌표 우선 정렬
        ArrayList<Point> arrX, arrY;
        Point p1, p2, r;
        int[][] arr = new int[N + 1][N + 1]; //2차원 누적합 배열
        int i, j, k, x, y;
        //점 입력
        for(i=0; i<N; i++) {
            input = br.readLine().split(SPACE);
            x = Integer.parseInt(input[0]); //-1_000_000_000 ~ 1_000_000_000
            y = Integer.parseInt(input[1]); //-1_000_000_000 ~ 1_000_000_000
            p1 = new Point(x, y);
            setX.add(p1);
            setY.add(p1);
        }
        i = 0;
        arrX = new ArrayList<>(setX);
        for(Point pt : arrX) mapX.put(pt, i++); //X좌표 압축
        i = 0;
        arrY = new ArrayList<>(setY);
        for(Point pt : arrY) mapY.put(pt, i++); //Y좌표 압축
        //압축된 좌표로 누적합 배열 채우기
        for(i=1; i<=N; i++) {
            p1 = arrX.get(i - 1);
            for(j=1; j<=N; j++) {
                p2 = arrY.get(j - 1);
                arr[i][j] = arr[i - 1][j] + arr[i][j - 1] - arr[i - 1][j - 1] + (p1 == p2 ? 1 : 0);
            }
        }
        //질의
        for(i=0; i<Q; i++) {
            input = br.readLine().split(SPACE);
            p1 = new Point(Integer.parseInt(input[0]), Integer.parseInt(input[1])); //-1_000_000_000 ~ 1_000_000_000
            p2 = new Point(Integer.parseInt(input[2]), Integer.parseInt(input[3])); //-1_000_000_000 ~ 1_000_000_000
            Point px = setX.ceiling(p1); //최소 X
            Point py = setY.ceiling(p1); //최소 Y
            Point qx = setX.floor(p2); //최대 X
            Point qy = setY.floor(p2); //최대 Y
            //범위 내에 점이 없을 경우
            if(px == null || py == null || qx == null || qy == null) {
                System.out.println(0);
                continue;
            }
            int fromX = mapX.get(px); //압축된 최소 X
            int fromY = mapY.get(py); //압축된 최소 Y
            int toX = mapX.get(qx) + 1; //압축된 최대 X
            int toY = mapY.get(qy) + 1; //압축된 최대 Y
            k = arr[toX][toY] - arr[fromX][toY] - arr[toX][fromY] + arr[fromX][fromY]; //누적합 배열로 점의 개수 계산
            System.out.println(k);
        }
    }
}
