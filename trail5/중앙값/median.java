import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.PriorityQueue;

public class Main {
    public static final String SPACE = " ";

    public static void main(String[] args) throws IOException, NumberFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //중앙값보다 낮은 숫자(내림차순)
        PriorityQueue<Integer> lower = new PriorityQueue<Integer>((a, b) -> b.compareTo(a));
        //중앙값보다 높은 숫자(오름차순)
        PriorityQueue<Integer> upper = new PriorityQueue<Integer>();
        int t = Integer.parseInt(br.readLine()); //테스트 케이스 수(1 ~ 5)
        int i, input, m, mid, temp;
        while(t-- > 0) {
            m = Integer.parseInt(br.readLine()); //수열의 크기(1 ~ 100_000, 홀수)
            String[] line = br.readLine().split(SPACE);
            mid = Integer.parseInt(line[0]); //지금까지 입력받은 원소들의 중앙값
            System.out.print(mid + " "); //원소가 1개면 그 원소가 중앙값임
            if(m > 1) {
                input = Integer.parseInt(line[1]); //두번째 원소
                upper.add(Math.max(input, mid));
                mid = Math.min(input, mid);
            }
            for(i=2; i<m; i++) {
                input = Integer.parseInt(line[i]); //(i + 1)번째 원소
                //짝수번째 원소(upper에 하나 추가)
                if(i % 2 != 0) {
                    upper.add(Math.max(input, mid));
                    mid = Math.min(input, mid);
                    temp = lower.poll();
                    lower.add(Math.min(temp, mid));
                    mid = Math.max(temp, mid);
                }
                //홀수번째 원소(lower에 하나 추가)
                else {
                    lower.add(Math.min(input, mid));
                    mid = Math.max(input, mid);
                    temp = upper.poll();
                    upper.add(Math.max(temp, mid));
                    mid = Math.min(temp, mid);
                    System.out.print(mid + " "); //홀수번째 수를 읽을 때마다 지금까지 입력받은 수들의 중앙값 출력
                }
            }
            //다음 테스트 케이스를 위해 초기화
            lower.clear();
            upper.clear();
            System.out.println();
        }
    }
}