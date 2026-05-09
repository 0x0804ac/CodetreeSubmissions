import java.util.Scanner;

public class Main {
    //weight만큼 더 가져갈 수 있는 상황에서 arr[index] 가져갈지 판단
    public static int calc(int[] arr, int index, int weight) {
        if(index == arr.length) return 0; //가져갈 물건이 없어서 종료
        if(weight == 0) return 0; //더 가져갈 여유가 안 돼서 종료
        int item = arr[index];
        int value1 = calc(arr, index + 1, weight); //arr[index]를 가져가지 않고 arr[index + 1]에 대해 판단
        //여유가 되면 arr[index]를 가져가고 arr[index + 1]에 대해 판단
        if(weight >= item) {
            int value2 = item * item + calc(arr, index + 1, weight - item);
            return Math.max(value1, value2);
        }
        return value1;
    }

    //board[x][y ~ (y + itemRange - 1)] 범위에서 최대 weightLimit만큼 가져갈 수 있을 때 가능한 최대 가치
    public static int getValue(int[][] board, int x, int y, int itemRange, int weightLimit) {
        int[] items = new int[itemRange];
        System.arraycopy(board[x], y, items, 0, itemRange);
        return calc(items, 0, weightLimit);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //방 길이(3 ~ 10)
        int m = sc.nextInt(); //한 명이 가질 수 있는 개수(1 ~ 5)
        int c = sc.nextInt(); //한 명이 가질 수 있는 무게(1 ~ 30)
        int[][] grid = new int[n][n]; //n * n 크기의 방
        int[][] values = new int[n][n - m + 1];
        int output = 0;
        int i, j, k, l, value;
        for(i=0; i<n; i++) {
            for(j=0; j<n; j++) grid[i][j] = sc.nextInt();
        }
        //한 명이 values[i][j]에서 시작했을 때 최대로 가져갈 수 있는 가치를 미리 계산
        for(i=0; i<n; i++) {
            for(j=0; j<values[i].length; j++) values[i][j] = getValue(grid, i, j, m, c);
        }
        //범위가 겹치지 않는 2명이 가질 수 있는 최대 가치 계산
        for(i=0; i<n; i++) {
            for(j=0; j<=n-m; j++) {
                for(k=0; k<n; k++) {
                    for(l=0; l<=n-m; l++) {
                        if(i == k && Math.abs(j - l) < m) continue; //같은 행에 있을 경우 범위가 겹치면 안 됨
                        value = values[i][j] + values[k][l];
                        output = Math.max(output, value);
                    }
                }
            }
        }
        System.out.println(output);
    }
}