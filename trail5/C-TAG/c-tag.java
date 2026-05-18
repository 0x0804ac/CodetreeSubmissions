import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static int map(char ch) {
        switch(ch) {
            case 'A':
            return 1;
            case 'T':
            return 2;
            case 'G':
            return 3;
            case 'C':
            return 4;
            default:
            return 0;
        }
    }

    public static int map(char c1, char c2, char c3) {
        return map(c1) * 100 + map(c2) * 10 + map(c3);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //1 ~ 500
        int m = sc.nextInt(); //3 ~ 50
        int output = 0;
        String[] A = new String[n]; //그룹 A
        String[] B = new String[n]; //그룹 B
        HashSet<Integer> set = new HashSet<Integer>(n * 3 / 2);
        int i, j, k;
        for(i=0; i<n; i++) A[i] = sc.next(); //'A', 'T', 'G', 'C'로 구성된 길이 m의 문자열
        for(i=0; i<n; i++) B[i] = sc.next(); //'A', 'T', 'G', 'C'로 구성된 길이 m의 문자열
        //i, j, k번째 문자 조합으로 분류 시도
        for(i=0; i<m; i++) {
            for(j=i+1; j<m; j++) {
                for(k=j+1; k<m; k++) {
                    boolean match = true; //이 조합만으로 그룹 A와 B를 구별할 수 있는지 여부
                    //그룹 A의 각 문자열에 대해 조합이 나타내는 부분 문자열을 set에 추가
                    for(String tag : A) {
                        set.add(map(tag.charAt(i), tag.charAt(j), tag.charAt(k)));
                    }
                    //그룹 B의 각 문자열에 대해 조합이 나타내는 부분 문자열이 set에 포함되는지 검사
                    for(String tag : B) {
                        //하나라도 포함되면 구별할 수 없다는 뜻이므로 종료
                        if(set.contains(map(tag.charAt(i), tag.charAt(j), tag.charAt(k)))) {
                            match = false;
                            break;
                        }
                    }
                    if(match) output++;
                    set.clear(); //다음 검사를 위해 set 초기화
                }
            }
        }
        System.out.println(output); //구분할 수 있는 조합의 개수 출력
    }
}