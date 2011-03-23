/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bullethell.highscore;

/**
 *
 * @author 08KarlinF
 */
public class Score {
    private String namn;
    private int poäng = 0;
    private int level = 0;

            public void addScore(int reward){
                if(poäng>=0){
                setPoäng(getPoäng() + reward);
                }
    }
            public void editName(String newName){



            }

    /**
     * @return the namn
     */
    public String getNamn() {
        return namn;
    }

    /**
     * @param namn the namn to set
     */
    public void setNamn(String namn) {
        this.namn = namn;
    }

    /**
     * @return the poäng
     */
    public int getPoäng() {
        return poäng;
    }

    /**
     * @param poäng the poäng to set
     */
    public void setPoäng(int poäng) {
        this.poäng = poäng;
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }
}
