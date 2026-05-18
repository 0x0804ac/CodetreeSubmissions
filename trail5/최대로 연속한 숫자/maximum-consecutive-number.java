import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.TreeSet;

class Range implements Comparable<Range> {
    private int s;
    private int e;
    private int l;

    public Range(int from, int to) {
        s = from;
        e = to;
        l = e - s + 1;
    }

    public int getStart() { return s; }
    public int getEnd() { return e; }
    public int length() { return l; }

    @Override
    public int compareTo(Range other) {
        if(l != other.l) return other.l - l;
        else if(s != other.s) return s - other.s;
        else return e - other.e;
    }

    @Override
    public String toString() {
        return "[" + s + ", " + e + "]";
    }
}

public class Main {
    public static final String SPACE = " ";

    public static void main(String[] args) throws IOException, NumberFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(SPACE);
        int n = Integer.parseInt(line[0]); //1 ~ 1_000_000_000
        int m = Integer.parseInt(line[1]); //제거할 수의 개수(1 ~ min(n-1, 100_000))
        TreeSet<Integer> removed = new TreeSet<Integer>(); //제거된 수 집합
        TreeSet<Range> sequence = new TreeSet<Range>(); //연속된 수 수열 집합
        //양쪽 끝 계산을 위해 추가
        removed.add(-1);
        removed.add(n + 1);
        sequence.add(new Range(0, n)); //최초 상태: 0부터 n까지의 숫자가 하나씩 있음
        line = br.readLine().split(SPACE);
        int i, l, r, input;
        for(i=0; i<m; i++) {
            input = Integer.parseInt(line[i]); //제거할 수(1 ~ n-1, 중복 없음)
            l = removed.floor(input) + 1; //제거할 수가 있는 수열의 왼쪽 끝
            r = removed.ceiling(input) - 1; //제거할 수가 있는 수열의 오른쪽 끝
            removed.add(input);
            //수열이 2개로 분리됨
            sequence.remove(new Range(l, r));
            if(l < input) sequence.add(new Range(l, input - 1));
            if(input < r) sequence.add(new Range(input + 1, r));
            System.out.println(sequence.first().length()); //현재 상태에서 최장 수열의 길이 출력
        }
    }
}