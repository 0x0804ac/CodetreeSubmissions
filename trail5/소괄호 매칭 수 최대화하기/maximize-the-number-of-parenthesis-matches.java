import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    //s 안에 있는 '('와 ')' 조합의 개수
    public static long match(String s) {
        long output = 0L;
        int len = s.length();
        int[] C = new int[len]; //지금까지 나온 '('의 개수
        int i;
        C[0] = s.charAt(0) == '(' ? 1 : 0;
        for(i=1; i<len; i++) C[i] = C[i - 1] + (s.charAt(i) == '(' ? 1 : 0); //C 채우기
        for(i=0; i<len; i++) {
            //모든 ')'에 대하여 앞에 있는 '('와 매칭
            if(s.charAt(i) == ')') output += C[i];
        }
        return output;
    }

    public static void main(String[] args) throws IOException, NumberFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine()); //1 ~ 100_000
        int len = 0;
        String[] arr = new String[n];
        int i;
        //n개의 문자열
        for(i=0; i<n; i++) {
            arr[i] = br.readLine(); //'(' 또는 ')'로만 구성됨
            len += arr[i].length(); //길이의 총합은 500_000 이하
        }
        //조합을 더 많이 얻을 수 있는 순서대로 정렬
        Arrays.sort(arr, (a, b) -> Long.compare(match(b + a), match(a + b)));
        StringBuilder str = new StringBuilder(len);
        for(String e : arr) str.append(e); //정렬된 최종 문자열
        System.out.println(match(str.toString())); //최종 문자열에서 얻을 수 있는 조합의 개수
    }
}