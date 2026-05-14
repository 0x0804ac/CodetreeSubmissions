import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //1 ~ 19
        int[] bst = new int[n + 1]; //bst[i]: i(1 ~ n)개의 노드로 만들 수 있는 서로 다른 이진 탐색 트리의 개수
        int count, i, j;
        for(i=0; i<=n; i++) {
            if(i <= 1) bst[i] = 1;
            else {
                count = 0;
                for(j=0; j<i; j++) count += bst[j] * bst[i - j - 1]; //왼쪽에 j개의 노드, 오른쪽에 (i - j - 1)개의 노드
                bst[i] = count;
            }
        }
        System.out.println(bst[n]);
    }
}