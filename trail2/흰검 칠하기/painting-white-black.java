import java.util.Scanner;

class Tile {
    public static final char WHITE = 'L'; //왼쪽: 흰색
    public static final char BLACK = 'R'; //오른쪽: 검은색
    public static final char GRAY = 'G';

    private StringBuilder paints;
    private boolean isGray;

    public Tile() {
        paints = new StringBuilder();
        isGray = false;
    }

    public char getColor() {
        int len = paints.length();
        if(len == 0) return 'E'; //색칠되지 않음
        if(isGray) return GRAY; //회색(빠른 처리)
        int black = 0, white = 0;
        for(int i=0; i<len; i++) {
            switch(paints.charAt(i)) {
                case 'L':
                white++;
                break;
                case 'R':
                black++;
                break;
            }
        }
        if(black >= 2 && white >= 2) {
            isGray = true; //다음에는 빠른 처리가 됨
            return GRAY; //회색
        }
        return paints.charAt(len - 1); //마지막으로 칠해진 색
    }

    public void paint(char color) {
        if(!isGray) paints.append(color);
    }
}

public class Main {
    public static final String SPACE = " ";
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        Tile[] line = new Tile[N * 198 + 1]; //index(0 ~ N*198) -> pos(-N*99 ~ N*99)
        int pos = N * 99; //index(N*99) -> pos(0)
        int i, j, x;
        char dir;
        for(i=0; i<line.length; i++) line[i] = new Tile();

        for(i=0; i<N; i++) {
            x = sc.nextInt();
            dir = sc.next().charAt(0);
            line[pos].paint(dir); //현위치에 색칠
            if(dir == Tile.WHITE) {
                for(j=1; j<x; j++) line[--pos].paint(dir); //왼쪽으로 색칠
            }
            else if(dir == Tile.BLACK) {
                for(j=1; j<x; j++) line[++pos].paint(dir); //오른쪽으로 색칠
            }
        }

        int[] count = new int[] { 0, 0, 0 }; //흰색, 검은색, 회색
        for(i=0; i<line.length; i++) {
            switch(line[i].getColor()) {
                case Tile.WHITE:
                count[0]++;
                break;
                case Tile.BLACK:
                count[1]++;
                break;
                case Tile.GRAY:
                count[2]++;
                break;
                default:
                break;
            }
        }
        System.out.println(count[0] + " " + count[1] + " " + count[2]);
    }
}