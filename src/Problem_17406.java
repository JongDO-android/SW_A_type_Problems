import java.util.ArrayList;
import java.util.Scanner;

public class Problem_17406 {
    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int K = scanner.nextInt();

        int[][] a = new int[N][M];
        for(int i = 0 ; i < N ; i ++){
            for(int j = 0 ; j < M; j ++){
                a[i][j] = scanner.nextInt();
            }
        }
        int[][] list = new int[K][3];
        for(int i = 0; i < K ; i++){
            list[i][0] = scanner.nextInt();
            list[i][1] = scanner.nextInt();
            list[i][2] = scanner.nextInt();
        }
        ArrayList al = new ArrayList();
        permutation(list, a,0, K, al);
        int min = (int) al.get(0);
        for(int i = 1 ; i < al.size(); i ++){
            if(min > (int) al.get(i)){
                min = (int) al.get(i);
            }
        }
        System.out.println(min);

    }

    public static class Rotate{
        private int[][] cube;
        private int min;

        public Rotate(int[][] a){
            min = 0;
            cube = a;
        }
        public void setMin() {
            for(int i = 0 ; i < cube[0].length; i ++){
                min += cube[0][i];
            }

            for(int i = 1 ; i < cube.length; i ++){
                int temp = 0;
                for(int j = 0 ; j < cube[0].length; j ++){
                    temp += cube[i][j];
                }
                if(min > temp) min = temp;
            }
        }
        public int getMin(){
            return min;
        }
        public void rotateList(int n, int m, int k){
            if(k == 0 ) return;

            int temp = cube[n-k-1][m-k-1];
            for(int i = m-k ; i < m+k; i++){
                int temp2 = cube[n-k-1][i];
                cube[n-k-1][i] = temp;
                temp = temp2;
            }
            for(int i = n-k; i < n+k; i ++){
                int temp2 = cube[i][m+k-1];
                cube[i][m+k-1] = temp;
                temp = temp2;
            }
            for(int i = m+k-2; i > m-k-2; i --){
                int temp2 = cube[n+k-1][i];
                cube[n+k-1][i] = temp;
                temp = temp2;
            }
            for(int i = n+k-2; i > n-k-2; i --){
                int temp2 = cube[i][m-k-1];
                cube[i][m-k-1] = temp;
                temp = temp2;
            }
            rotateList(n, m,k-1);
        }

    }
    public static void permutation(int[][] rlist, int[][] a, int depth, int k, ArrayList al){
        if(depth == k){
            int[][] templist = new int[a.length][a[0].length];
            for(int i = 0; i < templist.length; i++){
                for(int j = 0 ; j < templist[0].length; j ++){
                    templist[i][j] = a[i][j];
                }
            }

            Rotate r = new Rotate(templist);
            for(int i = 0 ; i < rlist.length; i++){
                r.rotateList(rlist[i][0], rlist[i][1], rlist[i][2]);
            }
            r.setMin();
            al.add(r.getMin());
            return;
        }

        for(int i = depth; i < k; i++){
            swap(rlist, depth, i);
            permutation(rlist, a,depth+1, k, al);
            swap(rlist, depth, i);
        }
    }
    public static void swap(int[][] rlist, int depth, int i){
        int[] temp = {rlist[depth][0], rlist[depth][1], rlist[depth][2]};

        rlist[depth] = rlist[i];
        rlist[i] =  temp;

    }
}
