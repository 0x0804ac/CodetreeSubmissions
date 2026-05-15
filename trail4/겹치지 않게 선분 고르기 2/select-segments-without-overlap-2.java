import java.util.Arrays;
import java.util.Scanner;

class Segment {
    private int startPoint; //시작점
    private int endPoint; //끝점

    public Segment(int start, int end) {
        startPoint = Math.min(start, end);
        endPoint = Math.max(start, end);
    }

    public int getStart() { return startPoint; }
    public int getEnd() { return endPoint; }

    @Override
    public String toString() {
        return "[" + startPoint + " - " + endPoint + "]";
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //1 ~ 1000
        Segment[] segments = new Segment[n];
        int[] dp = new int[n]; //dp[i]: segments[i]를 마지막으로 선택했을 때 서로 겹치지 않는 선분의 최대 개수
        int output = 1;
        int i, j, start, end;
        for(i=0; i<n; i++) segments[i] = new Segment(sc.nextInt(), sc.nextInt()); //1 ~ 1000
        Arrays.sort(segments, (a, b) -> a.getStart() - b.getStart()); //선분을 시작점 기준으로 정렬
        //dp 채우기
        for(i=0; i<n; i++) {
            dp[i] = 1;
            start = segments[i].getStart();
            //이전 선분들과 비교(자신의 시작점이 이전 선분의 끝점보다 앞에 있으면 겹치지 않음)
            for(j=0; j<n; j++) {
                end = segments[j].getEnd();
                if(end < start && dp[j] >= dp[i]) dp[i] = dp[j] + 1;
            }
            if(output < dp[i]) output = dp[i]; //최댓값 갱신
        }
        System.out.println(output); //최댓값 출력
    }
}