import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.TreeSet;

public class Main {
    public static final String SPACE = " ";

    public static void main(String[] args) throws IOException, NumberFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(SPACE);
        TreeSet<Long> set = new TreeSet<Long>();
        int n = Integer.parseInt(line[0]); //사람 수(1 ~ 100_000)
        int t = Integer.parseInt(line[1]); //달리기 진행 시간(1 ~ 1_000_000_000)
        int i;
        for(i=0; i<n; i++) {
            line = br.readLine().split(SPACE);
            int startPos = Integer.parseInt(line[0]); //이 사람의 출발점(0 ~ 1_000_000_000, 출발점이 앞선 사람의 정보가 먼저 주어짐)
            int speed = Integer.parseInt(line[1]); //이 사람의 속력(1 ~ 1_000_000_000)
            long endPos = 1L * speed * t + startPos; //이 사람의 달리기 종료 시점 위치
            Long next = set.ceiling(endPos); //이 사람 뒤에서 출발했는데 앞에 있다면 중간에 만났을 것
            while(next != null) {
                set.remove(next); //같은 그룹으로 묶기
                next = set.ceiling(endPos); //만난 사람이 더 있는지 확인
            }
            set.add(endPos); //그룹 기록
        }
        System.out.println(set.size()); //최종적으로 몇 개의 그룹이 생겼는지 출력
    }
}