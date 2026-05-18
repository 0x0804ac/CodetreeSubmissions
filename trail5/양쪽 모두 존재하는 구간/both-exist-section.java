import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

public class Main {
	public static final String SPACE = " ";

    public static void main(String[] args) throws IOException, NumberFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] line = br.readLine().split(SPACE);
        int n = Integer.parseInt(line[0]); //1 ~ 100_000
        int m = Integer.parseInt(line[1]); //1 ~ m
        int[] arr = new int[n + 1];
		int[] count = new int[m + 1]; //count[i]: i(1 ~ m)의 개수
		int[] section = new int[m + 1]; //section[i]: 현재 구간 내에서 i(1 ~ m)의 개수
        int left = 0; //구간의 왼쪽 끝
		int right = 0; //구간의 오른쪽 끝
		int output = -1; //안과 밖 모두 (1 ~ m)가 하나씩 존재하는 구간 중 가장 짧은 구간의 길이
        int i, check;
		line = br.readLine().split(SPACE);
		//n개의 숫자
        for(i=0; i<n; i++) {
            arr[i] = Integer.parseInt(line[i]); //1 ~ m
            count[arr[i]]++; //전체 개수 기록
        }
		//전체 기록 확인
        for(i=1; i<=m; i++) {
			//구간 안과 밖에 모두 존재하려면 숫자가 2개 이상 있어야 함
            if(count[i] < 2) {
                System.out.println(output);
                System.exit(0);
            }
        }
        output = Integer.MAX_VALUE;
        section[arr[0]]++; //arr[0]부터 시작
		//한 방향으로 진행하면서 구간 정해보기
        while(left < n && right < n) {
            check = 0; //현재 구간의 상태
			//구간 상태 확인
            for(i=1; i<=m; i++) {
                int value = section[i];
				//구간에 없는 숫자가 있다면 +값으로 체크 후 종료
                if(value == 0) {
                    check = i;
                    break;
                }
				//구간 밖에 없는 숫자가 있다면 -값으로 체크 후 종료
                else if(value == count[i]) {
                    check = -i;
                    break;
                }
            }
			//구간이 조건을 만족
            if(check == 0) {
                output = Math.min(output, right - left + 1); //최솟값 갱
                section[arr[left++]]--; //왼쪽 끝 이동
            }
			//구간에 숫자를 추가해야 함
            else if(check > 0) {
				//해당 숫자가 나올 때까지 오른쪽 끝 이동
                do {
                    if(++right < n) section[arr[right]]++;
                    else break;
                } while(arr[right] != check);
            }
			//구간에서 숫자를 제거해야 함
            else {
				//해당 숫자가 나올 때까지 왼쪽 끝 이동
                do {
                    if(left < right) section[arr[left++]]--;
                    else break;
                } while(arr[left - 1] != -check);
            }
        }
        System.out.println(output == Integer.MAX_VALUE ? -1 : output); //구간의 최소 길이(구간이 없다면 -1)
    }
}