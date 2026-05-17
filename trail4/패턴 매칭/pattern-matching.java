import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static final int MAX_LENGTH = 20; //문자열과 패턴의 최대 길이
    private static ArrayList<String> patterns = new ArrayList<String>(MAX_LENGTH);
    private static String input = null;
    private static int guaranteedLength = 0;

    //input이 expression 표현식을 만족하는지 검사 후 출력
    public static void match(String expression) {
        int len = input.length();
        if(len != expression.length()) return;
        for(int i=0; i<len; i++) {
            if(expression.charAt(i) == '.') continue;
            if(input.charAt(i) != expression.charAt(i)) return; //한 글자라도 만족하지 않으면 종료
        }
        System.out.println(true);
        System.exit(0);
    }

    //
    public static void match(int[] lengths) {
        StringBuilder expression = new StringBuilder();
        int i, j;
        for(i=0; i<lengths.length; i++) {
            String pattern = patterns.get(i);
            //'*'이 포함됨(0개 이상의 이전 문자와 일치)
            if(pattern.length() == 2) {
                for(j=0; j<lengths[i]; j++) expression.append(pattern.charAt(0));
            }
            //일반 문자 또는 '.'(임의의 단일 문자와 일치)
            else expression.append(pattern.charAt(0));
        }
        match(expression.toString()); //완성된 표현식으로 매칭 시도
    }

    //lengths[index] 채우기
    public static void build(int[] lengths, int index) {
        //다 채웠으면 매칭 시도
        if(index == lengths.length) {
            match(lengths);
            return;
        }
        String pattern = patterns.get(index);
        int i;
        //'*'이 포함됨
        if(pattern.length() == 2) {
            for(i=0; i<=MAX_LENGTH-guaranteedLength; i++) {
                lengths[index] = i;
                build(lengths, index + 1);
            }
        }
        //일반 문자 또는 '.'
        else {
            guaranteedLength++;
            lengths[index] = 1;
            build(lengths, index + 1);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        input = sc.next(); //입력 문자열
        String p = sc.next(); //입력 패턴
        //'*'이 포함됨
        if(p.contains("*")) {
            int i, j;
            for(i=0; i<p.length(); i++) {
                if(i < p.length() - 1 && p.charAt(i + 1) == '*') {
                    patterns.add(p.substring(i, i + 2));
                    i++;
                }
                else patterns.add(p.substring(i, i + 1));
            }
            int[] parts = new int[patterns.size()];
            build(parts, 0);
        }
        //'*'이 포함되지 않음
        else match(p);
        System.out.println(false);
    }
}