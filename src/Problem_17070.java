import java.util.Scanner;

public class Problem_17070 {
    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        boolean[][] room = new boolean[N][N];


        for(int i = 0 ; i < N ; i ++){
            for(int j = 0 ; j < N; j ++){
                int n = scanner.nextInt();
                if(n == 1){
                    room[i][j] = true;
                }
                else{
                    room[i][j] = false;
                }
            }
        }
        MovePipe mp = new MovePipe(N, room);
        int cnt = mp.Count();

        System.out.println(cnt);
    }

    public static class MovePipe{
        private boolean[][] room;
        private int N;
        private boolean[] blist = new boolean[3];

        private int count;

        public MovePipe(int N, boolean[][] b){
            room = b;
            this.N = N;

            blist[0] = true;
            blist[1] = false;
            blist[2] = false;
            count = 0;
        }

        public boolean MoveHor(Point prePoint){
            int x2 = prePoint.getX();
            int y2 = prePoint.getY();

            if(blist[1])
                return false;
            else{
                if(x2 != N && y2 + 1 >= N) return false;
                if(!room[x2-1][y2]) {
                    Point nPoint = new Point(x2, y2 + 1);
                    if(x2 == N && y2+1 == N) {
                        count++;
                        return true;
                    }
                    setHor();
                    MoveHor(nPoint);
                    setHor();
                    MoveDiag(nPoint);
                }
                return false;
            }
        }
        public boolean MoveVer(Point prePoint){
            int x2 = prePoint.getX();
            int y2 = prePoint.getY();

            if(blist[0]){
                return false;
            }
            else{
                if(y2 != N && x2 + 1 >= N) return false;
                if(!room[x2][y2-1]) {
                    Point nPoint = new Point(x2 + 1, y2);

                    if(x2+1 == N && y2 == N) {
                        count++;
                        return true;
                    }
                    setVer();
                    MoveDiag(nPoint);
                    setVer();
                    MoveVer(nPoint);
                }
                return false;
            }
        }
        public boolean MoveDiag(Point prePoint){
            int x2 = prePoint.getX();
            int y2 = prePoint.getY();


            if(x2 == N || y2 == N) return false;
            if(!room[x2-1][y2] && !room[x2][y2] && !room[x2][y2-1]){
                Point nPoint = new Point(x2 + 1, y2 + 1);

                if(x2+1 == N && y2+1 == N) {
                    count++;
                    return true;
                }
                setDiag();

                MoveHor(nPoint);
                setDiag();
                MoveDiag(nPoint);
                setDiag();
                MoveVer(nPoint);

            }
            return false;
        }
        public int Count(){
            Point startPoint = new Point(1,2);
            MoveHor(startPoint);
            MoveDiag(startPoint);
            return count;
        }
        public void setHor(){
            blist[0] = true;
            blist[1] = false;
            blist[2] = false;
        }
        public void setVer(){
            blist[0] = false;
            blist[1] = true;
            blist[2] = false;
        }
        public void setDiag(){
            blist[0] = false;
            blist[1] = false;
            blist[2] = true;
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
