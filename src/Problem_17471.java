import java.util.ArrayList;
import java.util.Scanner;

public class Problem_17471 {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int min = 0;
        int[] plist = new int[N];
        Vertex[] ver = new Vertex[N];

        for(int i = 0; i < N ; i ++){
            plist[i] = scanner.nextInt();
            min += plist[i];
        }
        for(int i = 0 ; i < N; i ++){
            int adnum = scanner.nextInt();
            int[] adlist = new int[adnum];

            for(int j = 0; j < adnum; j ++){
                adlist[j] = scanner.nextInt() - 1;
            }

            ver[i] = new Vertex(plist[i], adlist, i);
        }
        scanner.close();
        Graph graph = new Graph(ver, N);
        if(graph.Check()) System.out.println(-1);
        else{
            for(int i = 0 ; i < N; i ++){
                graph.Search(i);
                graph.clear();
            }
            System.out.print(graph.getMin());
        }
    }

    public static class Graph{
        private ArrayList<Vertex> ward;
        private ArrayList<Vertex> rward;

        private boolean[] vlist;

        private int N;
        private int dis;
        private int min;

        private int wpn;
        private int rwpn;

        private Graph(Vertex[] ver, int N){
            this.N = N;

            vlist = new boolean[N];

            ward = new ArrayList<>();
            rward = new ArrayList<>();

            dis = 0;
            wpn = 0;
            for(int i = 0 ; i < ver.length; i ++){
                rward.add(ver[i]);
                rwpn += ver[i].getPopulation();
            }
            min = rwpn;
        }
        private void clear(){
            for(int i = 0 ; i < vlist.length ; i ++){
                vlist[i] = false;
            }
            for(int i = 0 ; i < ward.size(); i ++){
                Remove(ward.get(i));
            }
        }

        private boolean Check(){
            int cnt = 0;
            for(int i = 0 ; i < rward.size(); i++){
                if(rward.get(i).getAdlist().length == 0) cnt++;
            }
            return cnt > 1;
        }
        private void Add(Vertex vertex){
            ward.add(vertex);
            rward.remove(vertex);

            wpn += vertex.getPopulation();
            rwpn -= vertex.getPopulation();

            dis = Math.abs(wpn-rwpn);
        }
        private void Remove(Vertex vertex){
            ward.remove(vertex);
            rward.add(vertex);

            wpn -= vertex.getPopulation();
            rwpn += vertex.getPopulation();

            dis = Math.abs(wpn-rwpn);
        }
        private int getMin(){
            return min;
        }
        private boolean IsConnected(Vertex vertex){
            int cnt = 0;

            for(int i = 0 ; i < rward.size(); i ++){
                int[] adlist = rward.get(i).getAdlist();
                if(vertex.getNumber() == rward.get(i).getNumber()) continue;
                if(adlist.length == 1 && adlist[0] == vertex.getNumber()) {
                    cnt++;
                }
            }
            return cnt < 2;
        }
        private boolean IsCanDivide(){
            if(rward.size() == 1) return true;

            boolean[] vNlist = new boolean[N];
            for(int i = 0 ; i < rward.size(); i ++){
                vNlist[rward.get(i).getNumber()] = true;
            }
            for(int i = 0 ; i < rward.size(); i ++){
                int[] adlist = rward.get(i).getAdlist();

                for(int j = 0 ; j < adlist.length; j ++){
                    if(vNlist[adlist[j]]) vNlist[adlist[j]] = false;
                }
            }

            for(int i = 0; i < vNlist.length; i ++){
                if(vNlist[i]) return false;
            }
            return true;
        }
        private int FindIndex(int vN){
            for(int i = 0 ; i < rward.size(); i ++){
                if(rward.get(i).getNumber() == vN)
                    return i;
            }
            return -1;
        }
        private void Search(int vNum){
            if(rward.size() == 1) return;
            vlist[vNum] = true;

            int vIndex = FindIndex(vNum);
            if(vIndex != -1){
                Vertex v = rward.get(vIndex);

                if(IsConnected(v)) {
                    Add(v);
                    if(IsCanDivide()){
                        if(min > dis) min = dis;
                    }
                    int[] adlist = v.getAdlist();
                    for(int i = 0 ; i < adlist.length; i ++){
                        if(!vlist[adlist[i]]){
                            Search(adlist[i]);
                        }
                    }
                    Remove(v);
                }
            }
        }
    }

    static class Vertex{
        private int population;
        private int number;
        private int[] adlist;

        private Vertex(int population, int[] adlist, int number){
            this.number = number;
            this.population = population;
            this.adlist = adlist;
        }
        private int getNumber(){
            return number;
        }
        private int getPopulation(){
            return population;
        }
        private int[] getAdlist(){
            return adlist;
        }
    }
}
