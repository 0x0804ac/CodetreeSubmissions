import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.TreeSet;

class Segment implements Comparable<Segment> {
    private int begin; //시작점 (begin, 0)
    private int end; //끝점 (end, 1)

    public Segment(int x1, int x2) {
        begin = x1;
        end = x2;
    }

    public int getBegin() { return begin; }
    public int getEnd() { return end; }

    //시작점 우선 정렬
    @Override
    public int compareTo(Segment other) {
        if(begin == other.begin) return end - other.end;
        else return begin - other.begin;
    }

    @Override
    public String toString() {
        return "(" + begin + ", " + end + ")";
    }

    //s1와 s2가 겹치는지 검사
    public static boolean isMeeting(Segment s1, Segment s2) {
        int down = s1.begin - s2.begin;
        int up = s1.end - s2.end;
        return (up > 0 && down < 0) || (up < 0 && down > 0);
    }
}

public class Main {
    public static final String SPACE = " ";

    public static void main(String[] args) throws IOException, NumberFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        TreeSet<Segment> set = new TreeSet<>();
        Segment s;
        int n = Integer.parseInt(br.readLine()); //1 ~ 100_000
        int i, e;
        //n개의 선분
        for(i=0; i<n; i++) {
            String[] line = br.readLine().split(SPACE);
            s = new Segment(Integer.parseInt(line[0]), Integer.parseInt(line[1])); //-1_000_000 ~ 1_000_000
            set.add(s);
        }
        Segment[] arr = new Segment[n];
        i = 0;
        for(Segment segment : set) arr[i++] = segment;
        int[] left = new int[n + 1]; //left[i]: arr[0] ~ arr[i - 1] 범위의 선분에 대하여 최대 end 값
        int[] right = new int[n + 1]; //right[i]: arr[i] ~ arr[n - 1] 범위의 선분에 대하여 최대 end 값
        left[0] = Integer.MIN_VALUE; //left 초기화
        right[n] = Integer.MAX_VALUE; //right 초기화
        for(i=1; i<=n; i++) left[i] = Math.max(left[i - 1], arr[i - 1].getEnd()); //left 채우기
        for(i=n-1; i>=0; i--) right[i] = Math.min(right[i + 1], arr[i].getEnd()); //right 채우기
        int output = 0; //다른 어떤 선분과도 겹치지 않는 선분의 수
        //모든 선분에 대하여 left와 right를 이용하여 다른 선분과 겹치는지 검사
        for(i=0; i<n; i++) {
            s = arr[i];
            e = s.getEnd(); //이 선분의 끝점
            //시작점의 대소관계가 정해져 있으므로(정렬됨) 끝점의 대소관계로 겹치는지 확인 가능
            if(left[i] < e && e < right[i + 1]) output++;
        }
        System.out.println(output);
    }
}