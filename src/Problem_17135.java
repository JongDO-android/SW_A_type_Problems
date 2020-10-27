import java.util.ArrayList;
import java.util.Scanner;

public class Problem_17135 {
    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int D = scanner.nextInt();

        boolean[][] castle = new boolean[N][M];

        for(int i = 0 ; i < N; i ++){
            for(int j = 0 ; j < M; j ++){
                int index = scanner.nextInt();
                if(index == 1){
                    castle[i][j] = true;
                }
                else{
                    castle[i][j] = false;
                }
            }
        }

        Point ptemp;
        int[] indexlist = new int[3];
        for(int i = 1; i <= 3 ; i ++){
            int temp = 0;
            for(int j = 1; j <= M ; j ++){
                Point p = new Point(N+1, j);
                Archer ac = new Archer(castle, D);
                ac.AttackEnemy(0, p);
                if(temp < ac.getMaxKill()){
                    ptemp = p;
                }
            }

        }

        Archer ac = new Archer(castle, D);
        Point p = new Point(N+1,3);
        ac.AttackEnemy(0, p);


    }
    public static class Archer{
        private int aRange, N, M, Anum;
        private boolean[][] castle;
        private int kill;


        public Archer(boolean[][] b, int D){
            castle = b;
            aRange = D;
            Anum = 0;
            kill = 0;
            N = b.length;
            M = b[0].length;
        }
        public int getMaxKill(){
            return kill;
        }
        public int getDistance(Point p1, Point p2){
            int x1 = p1.getX();
            int y1 = p1.getY();
            int x2 = p2.getX();
            int y2 = p2.getY();

            return Math.abs(x1 - x2) + Math.abs(y1 - y2);
        }

        public void AttackEnemy(int count, Point p1){
            ArrayList<Point> pList = new ArrayList<>();
            if(count == N) return;
            if(Anum > 2) return;
            int start;
            int end;
            if(N - aRange - count< 0){
                start = 0;
            }
            else{
                start = N-aRange-count;
            }
            end = N - count;
            int cnt = 0;
            for(int i = start; i < end; i ++){
                for(int j = 0 ; j < M; j ++){
                    if(castle[i][j]){
                      if(getDistance(p1, new Point(i+1, j+1)) <= aRange){
                          pList.add(new Point(i+1, j+1));
                          cnt++;
                      }
                    }
                }
            }
            if(cnt >= 2){
                Point p = pList.get(0);
                int D = getDistance(p1, pList.get(0));

                for(int i = 1 ; i < pList.size(); i++){
                    if(D > getDistance(p1, pList.get(i))){
                        p = pList.get(i);
                    }
                    else if(D == getDistance(p1, pList.get(i))){
                        if(p.getY() > pList.get(i).getY()){
                            p = pList.get(i);
                        }
                    }
                }
                castle[p.getX()-1][p.getY()-1] = false;
                kill++;
            }
            else if (cnt == 1){
                castle[pList.get(0).getX()-1][pList.get(0).getY()-1] = false;
                kill++;
            }

            p1.setX(p1.getX()-1);
            AttackEnemy(count+1, p1);
        }
    }
    public static class Point{
        private int x;
        private int y;

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }

        public void setX(int x){
            this.x = x;
        }
        public void setY(int y){
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
