import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
    public static final String SPACE = " ";

    public static void main(String[] args) throws IOException, NumberFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(SPACE);
        int n = Integer.parseInt(line[0]); //1 ~ 100_000
        int m = Integer.parseInt(line[1]); //1 ~ 300_000_000
        int[] arr = new int[n]; //수열
        int sum = 0, output = 0;
        int i;
        line = br.readLine().split(SPACE);
        //n개의 원소
        for(i=0; i<n; i++) {
            arr[i] = Integer.parseInt(line[i]); //1 ~ 30_000
        }
        int from = 0, to = -1; //sum: arr[from] ~ arr[to] 범위 내 모든 원소의 합
        //한 방향으로 진행하면서 합이 m이 되는 경우 찾기
        while(from < n && to < n) {
            //합이 m보다 크면 구간 길이 감소(시작점 이동)
            if(sum > m) {
                sum -= arr[from++];
            }
            //합이 m보다 작으면 구간 길이 증가(끝점 이동)
            else if(sum < m) {
                to++;
                if(to < n) sum += arr[to];
            }
            //합이 m이면 기록 후 시작점과 끝점 이동
            else {
                output++;
                to++;
                sum -= arr[from++];
                if(to < n) sum += arr[to];
            }
        }
        System.out.println(output); //연속된 원소들의 합이 m이 되는 경우의 수
    }
}