import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.PriorityQueue;

class Person {
    private int id; //id번 사람
    private int stay; //머무르는 시간
    private int from; //도착하는 시각
    private int to; //나오는 시각

    public Person(int number, int arrival, int duration) {
        id = number;
        stay = duration;
        from = arrival;
        to = -1;
    }

    public int getId() { return id; }
    public int getStayTime() { return stay; }
    public int getArriveTime() { return from; }
    public int getExitTime() { return to; }
    
    //들어가는 시각이 주어졌을 때 나오는 시각 기록 후 도착해서 들어가기 전까지 기다린 시간을 반환
    public int enter(int enterTime) {
        to = enterTime + stay;
        return enterTime - from;
    }
}

public class Main {
    public static final String SPACE = " ";

    public static void main(String[] args) throws IOException, NumberFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //사람들을 도착 시간 기준 정렬
        PriorityQueue<Person> departQueue = new PriorityQueue<Person>((a, b) -> a.getArriveTime() - b.getArriveTime());
        //기다리는 사람이 여러 명이라면 번호가 작은 사람부터 입장
        PriorityQueue<Person> enterQueue = new PriorityQueue<Person>((a, b) -> a.getId() - b.getId());
        //어떤 일이 발생한 시각 기록
        PriorityQueue<Integer> timeQueue = new PriorityQueue<Integer>();
        Person garden = null; //정원 안에 있는 사람
        int n = Integer.parseInt(br.readLine()); //사람 수(1 ~ 100_000)
        int output = 0; //대기 시간의 최댓값
        int i;
        for(i=1; i<=n; i++) {
            String[] line = br.readLine().split(SPACE);
            int arrive = Integer.parseInt(line[0]); //도착한 시각(1 ~ 1_000_000_000)
            int stay = Integer.parseInt(line[1]); //머무르는 시간(1 ~ 10_000)
            Person p = new Person(i, arrive, stay);
            departQueue.add(p);
            timeQueue.add(arrive); //도착 시각 기록
            timeQueue.add(stay); //머무르는 시간 기록
        }
        //기록을 시간순으로 하나씩 꺼내서 분석
        while(!timeQueue.isEmpty()) {
            int time = timeQueue.poll();
            //도착 시각 기록일 경우 입장 큐에 추가
            if(!departQueue.isEmpty() && time == departQueue.peek().getArriveTime()) {
                enterQueue.add(departQueue.poll());
            }
            //나가는 시각 기록일 경우 정원 안에 있는 사람을 내보냄
            if(garden != null && time == garden.getExitTime()) garden = null;
            //정원 안에 사람이 없다면 입장 큐에 있는 사람을 입장시킴
            if(garden == null && !enterQueue.isEmpty()) {
                garden = enterQueue.poll();
                output = Math.max(output, garden.enter(time)); //최대 대기 시간 갱신
                timeQueue.add(garden.getExitTime()); //나가는 시각 기록
            }
        }
        System.out.println(output);
    }
}