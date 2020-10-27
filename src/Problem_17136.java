import java.util.ArrayList;
import java.util.Scanner;

public class Problem_17136 {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        boolean[][] paper = new boolean[10][10];
        ArrayList<Point> plist = new ArrayList<>();

        for(int i = 0 ; i < 10 ; i ++){
            for(int j = 0 ; j < 10 ; j ++){
                if(scanner.nextInt() == 1) {
                    paper[i][j] = true;
                    plist.add(new Point(i, j));
                }
            }
        }
        ColorPaper cp = new ColorPaper(paper, plist);
        cp.setClist(5, 0);

        while(cp.getMax() > 0) {
            cp.setMin();
        }
        cp.print();
        System.out.print(cp.getMin());
    }
    static class ColorPaper{
        private boolean[][] paper;
        private ArrayList<Point> plist;
        private int[] clist;
        private int[] cpaper = new int[5];

        private int min = 0;

        public ColorPaper(boolean[][] paper, ArrayList<Point> plist){
            this.paper = paper;
            this.plist = plist;

            clist = new int[plist.size()];
            for(int i = 0 ; i < 5; i ++) cpaper[i] = 5;
        }
        public int getMin(){
            return min;
        }
        public int getMax(){
            if(clist.length == 0) return 0;
            else return clist[FindMaxIndex()];
        }
        public void setMin(){
            if(cover(FindMaxIndex())){
                min++;
            }
            else{
                if(getMax() == 0) min = 0;
                else min = -1;
            }
        }
        public int FindMaxIndex(){
            int index = 0;
            int max = -1;
            for(int i = 0 ; i < clist.length; i ++){
                if(max < clist[i]) {
                    index = i;
                    max = clist[i];
                }
            }
            return index;
        }
        public boolean CanUsePaper(int index){
            if(clist[index] > 1){
                return cpaper[clist[index]-1] > 0;
            }
            return true;
        }
        public boolean cover(int index){
            while(!CanUsePaper(index)){
                clist[index]--;
            }

            int psize = clist[index];

            int x = plist.get(index).getX();
            int y = plist.get(index).getY();

            for(int i = x ; i < x + psize ; i ++){
                for(int j = y; j < y + psize ; j ++){
                    paper[i][j] = false;
                }
            }
            for(int i = index; i < plist.size(); i ++){
                int tx = plist.get(i).getX();
                int ty = plist.get(i).getY();
                if(!paper[tx][ty]) clist[i] = -1;
            }
            cpaper[psize-1]--;
            if(cpaper[psize-1] < 0) {
                cpaper[psize-1]++;
                return false;
            }
            setClist(clist[0], 0);
            return true;
        }
        public void setClist(int n, int index){
            if(index >= clist.length) return;

            int x = plist.get(index).getX();
            int y = plist.get(index).getY();

            if(clist[index] == -1) setClist(5, index+1);
            else if(x + n > 10 || y + n > 10) setClist(n-1, index);
            else{
                int col = 0;
                int row = 0;
                for(int tempY = y; tempY < y + n; tempY++){
                    if(paper[x][tempY]) col ++;
                }
                for(int tempZ = x; tempZ < x + n; tempZ++){
                    if(paper[tempZ][y]) row ++;
                }

                if(row < col) setClist(row, index);
                else if(row > col) setClist(col, index);
                else{
                    int count = 0;
                    for(int tempX = x; tempX < x + col; tempX++){
                        for(int tempY = y; tempY < y + row; tempY++){
                            if(paper[tempX][tempY]) count++;
                        }
                    }
                    if(count == col * row){
                        clist[index] = col;
                        setClist(5, index + 1);
                    }
                    else{
                        setClist(col-1, index);
                    }
                }
            }
        }
        public void print(){
            for(int i = 0 ; i < cpaper.length; i++)
                System.out.print(cpaper[i] + " ");

            System.out.println();
        }
    }
    static class Point{
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
