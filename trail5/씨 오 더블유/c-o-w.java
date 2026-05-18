import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //1 ~ 100_000
        String str = sc.next(); //'C', 'O', 'W'만으로 이루어진 길이가 n인 문자열
        int[] c = new int[n]; //c[i]: str[i] 왼쪽에 있는 'C'의 수
        int[] w = new int[n]; //w[i]: str[i] 오른쪽에 있는 'W'의 수
        long output = 0L; //('C' + 'O' + 'W') 조합의 수
        int i;
        for(i=1; i<n; i++) c[i] = c[i - 1] + (str.charAt(i - 1) == 'C' ? 1 : 0); //c 채우기
        for(i=n-2; i>0; i--) w[i] = w[i + 1] + (str.charAt(i + 1) == 'W' ? 1 : 0); //w 채우기
        //c와 w를 이용하여 모든 'O'에 대한 조합 수 계산
        for(i=1; i<n-1; i++) {
            if(str.charAt(i) == 'O') output += (1L * c[i] * w[i]);
        }
        System.out.println(output);
    }
}