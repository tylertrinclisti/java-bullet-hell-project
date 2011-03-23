/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bullethell.highscore;
import java.io.*;
import java.util.*;
/**
 *
 * @author 08KarlinF
 */
public class ScoreList {
    private LinkedList<Score> scores = new LinkedList<Score>();
    private String filnamn="highscore.txt";
    private int t = 0;
    private int poäng;
    private int level;
    private String namn;

    public ScoreList(String filnamn){
        this.filnamn = filnamn;
        readFile();
     }

    private void readFile() {
        try{
            File file = new File(filnamn);
            Scanner input = null;                     
            input = new Scanner(new FileReader(file));
            while (input.hasNext()){
                Score a = new Score();
                a.setNamn(input.nextLine());
                a.setPoäng(Integer.parseInt(input.nextLine()));
                a.setLevel(Integer.parseInt(input.nextLine()));
                getScores().add(a);
            }
            
            input.close();
        }catch(Exception e){
            System.out.println("Error:"+e);
        }
        //sort();
    }
     public void writeFile(){
        try{
        PrintWriter utström = new PrintWriter (new BufferedWriter(new FileWriter(filnamn)));
            for(int i = 0; i<getScores().size(); i++){
            //utström.println(getScores().get(i).getNamn());
            utström.println(poäng);
            //utström.println(getScores().get(i).getLevel());
            utström.close();
            }
        }
        catch(IOException ioe){
        }
        
    }
    public void add(Score herp){
            poäng=herp.getPoäng();
            //herp.setLevel(level);
            namn=herp.getNamn();

            getScores().add(herp);
            //sort();
    }
    ArrayList<Score> getScores(String player){
        ArrayList<Score> highList = new ArrayList();
        for(int i = 0 ; i<getScores().size(); i++){
            if (getScores().get(i).getNamn().equals(player)){
                highList.add(getScores().get(i));
            }
        }
        return highList;
    }
   public ArrayList<Score> getTopScores(int antal){
        ArrayList<Score> highList = new ArrayList();
        for(int i = 0 ; i<antal; i++){
                highList.add(getScores().get(i));
        }
        return highList;
   }

    public void sort(){
        boolean run = (true);
        while (run) {
            run = (false);
            System.out.println(getScores());
            try{
                for(int i = 0; getScores().get(i+1) != null; i++){
                    System.out.println(i);
                    if(getScores().get(i).getPoäng()<getScores().get(i + 1).getPoäng()){
                        System.out.println("Sorting "+i+" ("+(getScores().get(i).getPoäng())+") < "+(i+1)+" ("+(getScores().get(i + 1).getPoäng())+")");
                        Score temp = getScores().get(i+1);
                        getScores().set(i+1,getScores().get(i));
                        getScores().set(i, temp);
                        run = (true);
                    }
                }
            }catch(IndexOutOfBoundsException ioobe){
                System.out.println("KLAR!!!");
            }
        }
    }
    public void print() throws IOException{
        PrintWriter utström = new PrintWriter (new BufferedWriter(new FileWriter(filnamn)));
        for(int i = 0; i<getScores().size(); i++){
            System.out.println(i+" "+getScores().get(i).getPoäng()+" "+getScores().get(i).getLevel()+" "+getScores().get(i).getNamn());
            utström.println(getScores().get(i).getNamn());
            utström.println(getScores().get(i).getPoäng());
            utström.println(getScores().get(i).getLevel());
        }
        utström.close();
    }
    public void print(List<Score> list){
        for(int i = 0; i<list.size(); i++){
            System.out.println("Namn: " +list.get(i).getNamn());
            System.out.println("Level: " +list.get(i).getLevel());
            System.out.println("Poäng: " +list.get(i).getPoäng());

        }
    }
    public void print(Score score){
        System.out.println("Namn: " +score.getNamn());
        System.out.println("Level: " +score.getLevel());
        System.out.println("Poäng: " +score.getPoäng());
        
    }

    /**
     * @return the scores
     */
    public LinkedList<Score> getScores() {
        return scores;
    }

}