import java.util.Scanner;

class Person {
    private int[][] cheese; //[치즈, 시각]
    private int ache; //아픈 것으로 기록된 시각
    private int count; //치즈 기록 개수

    public Person(int outputRecords) {
        cheese = new int[outputRecords][2];
        ache = 0;
        count = 0;
    }

    public int getAcheTime() { return ache; }
    public int getRecordCount() { return count; }

    public void writeCheeseRecord(int number, int time) {
        if(count == cheese.length) return;
        cheese[count][0] = number;
        cheese[count++][1] = time;
    }

    public int[] getCheeseRecord(int index) {
        return cheese[index];
    }
    
    public void writeAcheRecord(int time) {
        if(ache == 0) ache = time;
    }

    public boolean checkBadCheese(int number) {
        boolean output = false;
        for(int i=0; i<count; i++) {
            if(cheese[i][0] == number && cheese[i][1] < ache) return true; //아프기 전에 치즈를 먹음
        }
        return ache == 0; //아프지 않고 치즈를 먹지 않음
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(); //사람 수
        int M = sc.nextInt(); //치즈 수
        int D = sc.nextInt(); //치즈 기록 개수
        int S = sc.nextInt(); //아픈 기록 개수
        Person[] people = new Person[N];
        int output = 0;
        int i, j, count;
        boolean valid;
        for(i=0; i<N; i++) people[i] = new Person(D);
        for(i=0; i<D; i++) people[sc.nextInt() - 1].writeCheeseRecord(sc.nextInt(), sc.nextInt()); //먹은 치즈 기록
        for(i=0; i<S; i++) people[sc.nextInt() - 1].writeAcheRecord(sc.nextInt()); //아픈 사람 기록
        //i번 치즈에 대하여 검사
        for(i=1; i<=M; i++) {
            valid = true;
            for(Person p : people) {
                if(!p.checkBadCheese(i)) {
                    valid = false;
                    break;
                }
            }
            if(valid) {
                count = 0; //아픈 사람 수
                for(Person p : people) {
                    if(p.getAcheTime() > 0) count++;
                    else {
                        //j번째 기록에 대하여 검사
                        for(j=0; j<p.getRecordCount(); j++) {
                            //아픈 기록이 없지만 치즈를 먹음
                            if(p.getCheeseRecord(j)[0] == i) {
                                count++;
                                break;
                            }
                        }
                    }
                    output = Math.max(output, count);
                }
            }
        }
        System.out.println(output);
    }
}