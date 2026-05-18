import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Main {
    public static final String SPACE = " ";

    public static void main(String[] args) throws IOException, NumberFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(SPACE);
        //두 수열에서 원소를 하나씩 뽑았을 때 두 원소의 합을 내림차순으로 저장
        PriorityQueue<Integer> list = new PriorityQueue<Integer>((a, b) -> b.compareTo(a));
        int n = Integer.parseInt(line[0]); //1 ~ 100_000
        int m = Integer.parseInt(line[1]); //1 ~ 100_000
        int k = Integer.parseInt(line[2]); //1 ~ min(n*m, 100_000)
        int[] arr1 = new int[n];
        int[] arr2 = new int[m];
        int i, j;
        line = br.readLine().split(SPACE);
        for(i=0; i<n; i++) arr1[i] = Integer.parseInt(line[i]); //1 ~ 1_000_000_000
        Arrays.sort(arr1);
        line = br.readLine().split(SPACE);
        for(i=0; i<m; i++) arr2[i] = Integer.parseInt(line[i]); //1 ~ 1_000_000_000
        Arrays.sort(arr2);
        //arr1[i]과 arr2[j]를 뽑음
        for(i=0; i<n; i++) {
            for(j=0; j<m; j++) {
                int sum = arr1[i] + arr2[j];
                if(list.size() < k) list.add(sum);
                else {
                    int top = list.peek();
                    //k번째로 작은 합 갱신
                    if(top > sum) {
                        list.poll();
                        list.add(sum);
                    }
                    //수열이 정렬되었기 때문에 합이 계속 커질 것을 예상할 수 있으므로 조기 종료
                    else break;
                }
            }
        }
        System.out.println(list.peek()); //arr1과 arr2에서 하나씩 뽑았을 때 k번째로 작은 합 출력
    }
}