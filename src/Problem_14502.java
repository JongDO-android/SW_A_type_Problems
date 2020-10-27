
import java.util.ArrayList;
import java.util.Scanner;

public class Problem_14502 {
    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt();
        int M = scanner.nextInt();

        int[][] labotory = new int[N][M];
        int sfp = 0;

        ArrayList<Point> vlist = new ArrayList<>();
        ArrayList<Point> wlist = new ArrayList<>();

        for(int i = 0 ; i < N; i ++){
            for(int j = 0 ; j < M; j++){
                labotory[i][j] = scanner.nextInt();
                if(labotory[i][j] == 0 ) sfp++;
                else if(labotory[i][j] == 2) vlist.add(new Point(i, j));
                else wlist.add(new Point(i, j));
            }
        }
    }
    public static class Labotory{
        private ArrayList<Point> vlist = new ArrayList<>();
        private ArrayList<Point> wlist = new ArrayList<>();

        private int safe;
        private int N;
        private int M;

        public Labotory(ArrayList<Point> vlist, ArrayList<Point> wlist, int N, int M){
            this.vlist = vlist;
            this.wlist = wlist;

            this.N = N;
            this.M = M;
            safe = 0;
        }
        public boolean CanInfect(Point p){
            int x = p.getX();
            int y = p.getY();

            if(x == 0){
                if(y == 0){

                }
                else if (y == N-1){

                }
            }
            return true;
        }
    }
    public static class Point{
        private int x;
        private int y;
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
        public int getX(){
            return x;
        }
        public int getY(){
            return y;
        }
    }
}
