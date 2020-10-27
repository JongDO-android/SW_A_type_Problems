import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Problem_SW_1767 {
    public static void main(String args[]){
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        try {
            int T = Integer.parseInt(bf.readLine());
            for(int i = 1; i <= T ; i ++){
                int N = Integer.parseInt(bf.readLine());
                boolean[][] b = new boolean[N][N];
                ArrayList<Point> clist = new ArrayList();

                for(int j = 0 ; j < N; j ++){
                    String s = bf.readLine();
                    StringTokenizer st = new StringTokenizer(s);
                    for(int k = 0 ; k < N; k ++){
                        if(Integer.parseInt(st.nextToken()) == 1){
                            b[j][k] = true;
                            clist.add(new Point(j, k));
                        }
                        else b[j][k] = false;
                    }
                }

                Mexinos mn = new Mexinos(b, clist);
                mn.setBlocklist();
                mn.setBcube(3);
                mn.print();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static class Mexinos{
        private boolean[][] b;
        private ArrayList<Point> plist;

        private int[] blocklist;
        private int length;

        public Mexinos(boolean[][] b, ArrayList<Point> list){
            this.b = b;
            plist = list;
            length = 0;
            blocklist = new int[list.size()];
            for(int i = 0 ; i < blocklist.length; i ++)
                blocklist[i] = 0;
        }
        public void print(){
            for(int i = 0; i < b.length; i ++){
                for(int j = 0 ; j < b.length ; j ++)
                    System.out.print(b[i][j] + " ");

                System.out.println();
            }
        }
        public int getLength(){
            return length;
        }
        public void setBlocklist(){
            for(int i = 0 ; i < plist.size(); i ++){
                Point p = plist.get(i);
                blocklist[i] = p.getBlocknum(b);
            }
        }
        public void setBcube(int N){
            if(N < 0) return;
            setBlocklist();
            for(int i = 0 ; i < blocklist.length; i ++){
                if(plist.get(i).getX() == 0 || plist.get(i).getX() == b.length-1 ||
                        plist.get(i).getY() == 0 || plist.get(i).getY() == b.length-1) continue;
                if(blocklist[i] == N){
                    Point p = plist.get(i);
                    boolean[] dir = p.getDirect();

                    if(dir[0]){
                        for(int j = p.getY() + 1 ; j < b.length; j ++){
                            b[p.getX()][j] = true;
                        }
                        length += p.getMinlen();
                    }
                    else if(dir[1]){
                        for(int j = p.getY() - 1; j >= 0 ; j--){
                            b[p.getX()][j] = true;
                        }
                        length += p.getMinlen();
                    }
                    else if(dir[2]){
                        for(int j = p.getX() + 1; j < b.length; j ++){
                            b[j][p.getY()] = true;
                        }
                        length += p.getMinlen();
                    }
                    else{
                        for(int j = p.getX() - 1; j >= 0 ; j --){
                            b[j][p.getY()] = true;
                        }
                        length += p.getMinlen();
                    }

                    blocklist[i] = -1;
                }
            }
            setBcube(N-1);
        }
    }
    public static class Point{
        private int x;
        private int y;
        private int blocknum;
        private int minlen;

        private boolean[] d;
        public Point(int x, int y){
            this.x = x;
            this.y = y;
            d = new boolean[4];

            blocknum = 0;
            for(int i = 0 ; i < d.length; i ++)
                d[i] = false;
        }
        public boolean[] getDirect(){
            return d;
        }
        public int getMinlen(){
            return minlen;
        }
        public boolean checkEast(boolean[][] b){
            for(int i = y+1; i < b.length; i ++){
                if(b[x][i]){
                    return false;
                }
            }
            return true;
        }
        public boolean checkWest(boolean[][] b){
            for(int i = y-1; i >= 0; i --){
                if(b[x][i]){
                    return false;
                }
            }
            return true;
        }
        public boolean checkNorth(boolean[][] b){
            for(int i = x-1; i >= 0; i --){
                if(b[i][y]){
                    return false;
                }
            }
            return true;
        }
        public boolean checkSouth(boolean[][] b){
            for(int i = x+1; i < b.length; i ++){
                if(b[i][y]){
                    return false;
                }
            }
            return true;
        }

        public int getBlocknum(boolean[][] b){
            blocknum = 0;
            minlen = b.length;
            if(!checkEast(b)) {
                blocknum++;
            }
            else {
                minlen = b.length - y - 1;
            }

            if(!checkWest(b)) blocknum++;
            else {
                if (minlen > y) minlen = y;
            }
            if(!checkNorth(b)) blocknum++;
            else{
                if(minlen > x) minlen = x;
            }
            if(!checkSouth(b)) blocknum++;
            else{
                if(minlen > b.length - x - 1) minlen = b.length - x - 1;
            }

            return blocknum;
        }

        public int getX(){
            return x;
        }
        public int getY(){
            return y;
        }
    }
}
