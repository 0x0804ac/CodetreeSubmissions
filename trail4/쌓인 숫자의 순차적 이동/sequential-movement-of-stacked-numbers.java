import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    //x와 y가 (0 ~ len-1) 범위 내에 있는 확인
    public static boolean isValid(int len, int x, int y) {
        if(x < 0 || y < 0) return false;
        return x < len && y < len;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //격자 크기
        int m = sc.nextInt(); //움직임 수
        ArrayDeque<Integer>[] grid = new ArrayDeque[n * n];
        ArrayDeque<Integer> stack = new ArrayDeque<Integer>(n * n);
        int i, j, k, x, y, z, target, index, max, dest;
        for(i=0; i<n*n; i++) {
            grid[i] = new ArrayDeque<Integer>(n * n);
            grid[i].addFirst(sc.nextInt());
        }
        //한 번씩 움직여보기
        for(i=0; i<m; i++) {
            target = sc.nextInt();
            //target 찾기
            for(j=0; j<n*n; j++) {
                if(grid[j].contains(target)) break;
            }
            index = j;
            max = 0;
            dest = j;
            x = index / n;
            y = index % n;
            //인접한 8방향에서 가장 큰 값을 탐색
            for(j=x-1; j<=x+1; j++) {
                for(k=y-1; k<=y+1; k++) {
                    if(j == x && k == y) continue;
                    z = j * n + k;
                    if(isValid(n, j, k) && !grid[z].isEmpty()) {
                        for(int e : grid[z]) {
                            if(max < e) {
                                max = e;
                                dest = z;
                            }
                        }
                    }
                }
            }
            //가장 큰 값이 발견됨
            if(index != dest) {
                //숫자들을 임시 stack에 저장
                do {
                    j = grid[index].pollFirst();
                    stack.addFirst(j);
                } while(j != target);
                //임시 stack에서 꺼내서 목적지에 넣음
                while(!stack.isEmpty()) grid[dest].addFirst(stack.pollFirst());
            }
        }
        //한 칸씩 출력
        for(ArrayDeque<Integer> e : grid) {
            if(e.isEmpty()) System.out.println("None");
            else {
                for(int v : e) System.out.print(v + " ");
                System.out.println();
            }
        }
    }
}