import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long n = sc.nextLong(); //2차원 배열 크기(1 ~ 100_000)
        long k = sc.nextLong(); //정렬 시 k번째로 오는 수를 구해야 함
        long left = 1L;
        long right = Math.min(n * n, 1_000_000_000L); //k의 범위
        long i;
        //답을 mid로 가정하고 계산
        while(left <= right) {
            long mid = (left + right) / 2L;
            long total = 0L; //mid보다 작은 수의 개수
            for(i=1; i<=n; i++) {
                if(mid < i) break;
                total += Math.min(mid / i, n);
            }
            long count = 0L; //값이 mid인 수의 개수
            for(i=1; i<=n; i++) {
                if(mid < i) break;
                if(mid % i == 0 && mid / i <= n) count++;
            }
            //조건에 맞는 수를 찾았다면 출력 후 종료
            if(total - count < k && k <= total) {
                System.out.println(mid);
                break;
            }
            //찾지 못했다면 다른 답을 선택
            else if(total - count >= k) right = mid - 1L;
            else left = mid + 1L;
        }
    }
}