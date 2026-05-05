import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] numbers = new int[N];
        int i;
        for(i=0; i<N; i++) numbers[i] = sc.nextInt();
        int output = 0, odds = 0, evens = 0;
        for(i=0; i<N; i++) {
            if(numbers[i] % 2 == 0) evens++;
            else odds++;
        }
        //짝수 합 -> 홀수 합 -> ... 순서대로 묶음 만들기
        while(true) {
            //짝수 합 만들기
            if(output % 2 == 0) {
                //짝수 1개
                if(evens > 0) evens--;
                //홀수 2개
                else if(odds > 1) odds -= 2;
                else {
                    //홀수 1개만 남아서 불가능: 종료
                    if(odds == 1) {
                        //바로 전 홀수 묶음과 같이 그 전 짝수 묶음과 합침
                        if(output > 0) output--;
                    }
                    break;
                }
            }
            //홀수 합 만들기
            else {
                //홀수 1개
                if(odds > 0) odds--;
                //홀수가 없어서 불가능: 종료
                else break;
            }
            output++;
        }
        System.out.println(output);
    }
}