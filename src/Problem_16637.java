
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Problem_16637 {
    public static void main(String args[]){
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        try {
            int N = Integer.parseInt(bf.readLine());
            String s = bf.readLine();

            Calculator ctr = new Calculator(s);
            System.out.println(ctr.FindMax());

            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Calculator{
        private int[] nlist;
        private char[] olist;
        private boolean[] flag;

        private int max;


        public Calculator(String s){
            nlist = new int[s.length()/2 + 1];
            flag = new boolean[s.length()/2];
            olist = new char[s.length()/2];
            max = 0;

            char[] c = s.toCharArray();
            int index1 = 0;
            int index2 = 0;
            for(int i = 0 ; i < s.length(); i ++){
                if(i%2 == 0){
                    nlist[index1++] = c[i] - '0';
                }
                else{
                    olist[index2++] = c[i];
                }
            }
        }
        public int FindMax(){
            for(int i = 0 ; i < olist.length; i ++){
                if(i != 0 && olist[i] == '+'){
                    if(olist[i-1] == '*')
                        flag[i] = true;
                }
                else if(i != 0 && olist[i] == '-'){
                    if(olist[i-1] == '-' && nlist[i] - nlist[i+1] < 0)
                        flag[i] = true;
                }
            }

            for(int i = 0 ; i < olist.length ; i++){
                if(flag[i]){
                    switch (olist[i]){
                        case '+':
                            nlist[i] = nlist[i] + nlist[i+1];
                            nlist[i+1] = 0;
                            i++;
                            break;
                        case '-':
                            nlist[i] = nlist[i+1] - nlist[i];
                            nlist[i+1] = 0;
                            olist[i-1] = '+';
                            i++;
                            break;

                    }
                }
            }
            max = nlist[0];
            for(int i = 0 ; i < olist.length; i ++){
                switch (olist[i]){
                    case '*':
                        max *= nlist[i+1];
                        break;
                    case '+':
                        max += nlist[i+1];
                        break;
                    case '-':
                        max -= nlist[i+1];
                        break;
                }
            }
            return max;
        }
    }

}
