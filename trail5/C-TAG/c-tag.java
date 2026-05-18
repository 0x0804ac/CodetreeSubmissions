import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static int map(char ch) {
        switch(ch) {
            case 'A':
            return 1;
            case 'T':
            return 2;
            case 'G':
            return 3;
            case 'C':
            return 4;
            default:
            return 0;
        }
    }

    public static int map(char c1, char c2, char c3) {
        return map(c1) * 100 + map(c2) * 10 + map(c3);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //pairs
        int m = sc.nextInt(); //length
        int output = 0;
        String[] A = new String[n];
        String[] B = new String[n];
        HashSet<Integer> set = new HashSet<Integer>(n * 3 / 2);
        int i, j, k;
        for(i=0; i<n; i++) A[i] = sc.next();
        for(i=0; i<n; i++) B[i] = sc.next();
        for(i=0; i<m; i++) {
            for(j=i+1; j<m; j++) {
                for(k=j+1; k<m; k++) {
                    boolean match = true;
                    for(String tag : A) {
                        set.add(map(tag.charAt(i), tag.charAt(j), tag.charAt(k)));
                    }
                    for(String tag : B) {
                        if(set.contains(map(tag.charAt(i), tag.charAt(j), tag.charAt(k)))) {
                            match = false;
                            break;
                        }
                    }
                    if(match) output++;
                    set.clear();
                }
            }
        }
        System.out.println(output);
    }
}