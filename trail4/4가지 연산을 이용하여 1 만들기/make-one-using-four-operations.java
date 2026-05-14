import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    //연산 종류: -1, +1, /2, /3
    public static final int[] DIVIDE_BY = {1, 1, 2, 3}; //(이 수로 나누어 떨어지면) 나눌 수
    public static final int[] ADD = {-1, 1, 0, 0}; //더할 수

    //from을 1로 만들기 위해 필요한 최소 연산 횟수
    public static int makeOne(int from) {
        LinkedList<int[]> queue = new LinkedList<int[]>(); //BFS 큐
        int[] count = new int[from * 2]; //arr[i]: from을 i로 만드는 데 필요한 최소 연산 횟수(0 < i < from * 2)
        int i;
        for(i=1; i<from*2; i++) count[i] = Integer.MAX_VALUE;
        //from에서 시작(연산 횟수: 0)
        count[from] = 0;
        queue.add(arr(from, 0));
        //BFS 탐색
        while(true) {
            int[] arr = queue.poll();
            if(arr[0] == 1) return arr[1]; //연산 결과가 1이면 종료
            for(i=0; i<4; i++) {
                if(arr[0] % DIVIDE_BY[i] == 0) {
                    int next = arr[0] / DIVIDE_BY[i] + ADD[i];
                    //최소 연산 횟수 기록
                    if(count[next] == Integer.MAX_VALUE) {
                        count[next] = arr[1] + 1;
                        queue.add(arr(next, arr[1] + 1));
                    }
                }
            }
        }
    }

    private static int[] arr(int n1, int n2) {
        return new int[] {n1, n2};
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //1 ~ 1_000_000
        System.out.println(makeOne(n));
    }
}