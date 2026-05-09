import java.util.Scanner;

public class Main {
    public static final char[] ch = new char[] { '4', '5', '6' };
    public static StringBuilder s = new StringBuilder();

    //string에 두 인접한 연속 부분 수열이 동일한지 검사(예시: 4_5_5, 66_45_45_5, 65_654_654_44)
    public static boolean check(StringBuilder string) {
        int i, first, mid, last;
        //모든 길이의 부분 수열에 대하여 검사
        for(i=2; i<=string.length(); i+=2) {
            last = string.length();
            first = last - i;
            mid = (first + last) / 2;
            //왼쪽 절반과 오른쪽 절반이 일치하면 조건 불만족: 종료
            if(string.substring(first, mid).equals(string.substring(mid, last))) return false;
        }
        //일치하는 쌍이 없으면 조건 만족: 종료
        return true;
    }

    //string에 addLength글자를 추가해야 함
    public static void func(StringBuilder string, int addLength) {
        //모두 추가했다면 조건을 만족하는지 검사
        if(addLength <= 0) {
            //조건을 만족하는 최초의 수열을 찾았다면 출력 후 프로그램 종료
            if(check(string)) {
                s = string;
                System.out.println(s.toString());
                System.exit(0);
            }
            return;
        }
        //'4', '5', '6'을 순서대로 하나씩 추가해봄
        for(char c : ch) {
            string.append(c);
            if(check(string)) {
                func(string, addLength - 1);
                if(s.length() == string.length() + addLength) return;
            }
            string.deleteCharAt(string.length() - 1);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //수열의 길이(1 ~ 80)
        func(new StringBuilder(), n);
    }
}