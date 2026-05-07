import java.util.Scanner;

public class Main {
    public static final int[] DX = new int[] { 0, 1, 0, -1 };
    public static final int[] DY = new int[] { 1, 0, -1, 0 };
    public static final char[] DIRECTION = new char[] { '0', '1', '2', '3' };
    public static final int IMPOSSIBLE = -1;
    
    public static void debug(char[][] maze, int x, int y, int t) {
        for(int i=0; i<maze.length; i++) {
            for(int j=0; j<maze[i].length; j++) {
                if(i == x && j == y) System.out.print('O');
                else System.out.print(maze[i][j]);
            }
            System.out.println();
        }
        System.out.printf("= = = = = DEBUG %d = = = = =\n", t);
    }

    //격자 안인지 밖인지 검사
    public static boolean isValid(char[][] arr, int x, int y) {
        if(x < 0 || y < 0) return false;
        return x < arr.length && y < arr[0].length;
    }

    //maze[startX][startY]에서 시작하여 미로를 탈출하는 데 걸리는 시간
    public static int escape(char[][] maze, int startX, int startY) {
        int index = 0, posX = startX, posY = startY, time = 0;
        int i, j, nextX, nextY;
        while(true) {
            nextX = posX + DX[index];
            nextY = posY + DY[index];
            //한 칸 이동에 최대 4회 소요
            for(i=0; i<4; i++) {
                if(!isValid(maze, nextX, nextY)) return time + 1; //탈출에 성공하여 종료
                //앞에 벽이 있으면 좌회전
                if(maze[nextX][nextY] == '#') {
                    index = (index + 3) % 4;
                    nextX = posX + DX[index];
                    nextY = posY + DY[index];
                }
                else if(maze[nextX][nextY] == DIRECTION[index]) return IMPOSSIBLE; //루프 구조여서 탈출 불가능: 종료
                else break;
            }
            if(i == 4) return IMPOSSIBLE; //사방이 벽으로 막혀서 탈출 불가능: 종료
            posX = nextX;
            posY = nextY;
            maze[posX][posY] = DIRECTION[index]; //이동 후 진행 방향을 현위치에 기록
            time++;
            nextX = posX + DX[(index + 1) % 4];
            nextY = posY + DY[(index + 1) % 4];
            //오른쪽에 벽이 없으면 우회전
            if(isValid(maze, nextX, nextY) && maze[nextX][nextY] != '#') index = (index + 1) % 4;
            //debug(maze, posX, posY, time);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int x = sc.nextInt();
        int y = sc.nextInt();
        char[][] maze = new char[n][n];
        int i, j;
        for(i=0; i<n; i++) {
            String line = sc.next();
            for(j=0; j<n; j++) maze[i][j] = line.charAt(j);
        }
        System.out.println(escape(maze, x - 1, y - 1));
    }
}