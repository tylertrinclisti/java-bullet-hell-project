package bullethell.character;

import java.awt.Point;
import bullethell.game.Game;
import bullethell.enemies.FairyBulletPattern;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jollepoker
 *
 */

public class CharReimuTest {

    public CharReimuTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    //Testar om man kan sätta lives till 5
    @Test
    public void testSetLives1() {
        CharReimu reimu = new CharReimu(new Point((Game.GAME_WIDTH / 2), (Game.GAME_HEIGHT - (Game.GAME_HEIGHT / 10))));
        reimu.setLives(5);
        assertEquals(5, reimu.getLives());
    }

    //Testar om metoden korrekt gör om liv till 8 om liv blir över 8 och att den också lägger till en bomb korrekt när detta händer.
    @Test
    public void testSetLives2() {
        CharReimu reimu = new CharReimu(new Point((Game.GAME_WIDTH / 2), (Game.GAME_HEIGHT - (Game.GAME_HEIGHT / 10))));
        int o_Bombcount = reimu.getBombs();
        reimu.setLives(1231512);
        assertEquals(8, reimu.getLives());
        assertEquals(o_Bombcount + 1, reimu.getBombs());
    }

    //Testar om metoden korrekt för om liv till 0 om liv är under 0
    @Test
    public void testSetLives3() {
        CharReimu reimu = new CharReimu(new Point((Game.GAME_WIDTH / 2), (Game.GAME_HEIGHT - (Game.GAME_HEIGHT / 10))));
        reimu.setLives(-5);
        assertEquals(0, reimu.getLives());
    }

    //Testar om metoden korrekt kan lägga till 2 liv.
    @Test
    public void testAddLives1() {
        CharReimu reimu = new CharReimu(new Point((Game.GAME_WIDTH / 2), (Game.GAME_HEIGHT - (Game.GAME_HEIGHT / 10))));
        int o_Lifecount = reimu.getLives();
        reimu.addLives(2);
        assertEquals(o_Lifecount + 2, reimu.getLives());
    }

    //Testar om metoden korrekt inte gör någonting om man försöker lägga till negativt antal liv
    @Test
    public void testAddLives2() {
        CharReimu reimu = new CharReimu(new Point((Game.GAME_WIDTH / 2), (Game.GAME_HEIGHT - (Game.GAME_HEIGHT / 10))));
        int o_Lifecount = reimu.getLives();
        reimu.addLives(-2);
        assertEquals(o_Lifecount, reimu.getLives());
    }

    //Testar om metoden korrekt kan göra om bombantalet till 5
    @Test
    public void testSetBombs1() {
        CharReimu reimu = new CharReimu(new Point((Game.GAME_WIDTH / 2), (Game.GAME_HEIGHT - (Game.GAME_HEIGHT / 10))));
        reimu.setBombs(5);
        assertEquals(5, reimu.getBombs());
    }

    //Testar om metoden korrekt gör om bombantalet till 8 om bombantalet blir över 8
    @Test
    public void testSetBombs2() {
        CharReimu reimu = new CharReimu(new Point((Game.GAME_WIDTH / 2), (Game.GAME_HEIGHT - (Game.GAME_HEIGHT / 10))));
        reimu.setBombs(123151);
        assertEquals(8, reimu.getBombs());
    }

    //Testar om metoden korrekt gör om bombantalet till 0 om bombantalet blir mindre än 0
    @Test
    public void testSetBombs3() {
        CharReimu reimu = new CharReimu(new Point((Game.GAME_WIDTH / 2), (Game.GAME_HEIGHT - (Game.GAME_HEIGHT / 10))));
        reimu.setBombs(-5);
        assertEquals(0, reimu.getBombs());
    }

    //Testar om metoden korrekt kan lägga till 2 bomber till bombantalet
    @Test
    public void testAddBombs1() {
        CharReimu reimu = new CharReimu(new Point((Game.GAME_WIDTH / 2), (Game.GAME_HEIGHT - (Game.GAME_HEIGHT / 10))));
        int o_Bombcount = reimu.getBombs();
        reimu.addBombs(2);
        assertEquals(o_Bombcount + 2, reimu.getBombs());
    }

    //Testar om metoden korrekt inte gör någonting alls när man försöker lägga till negativt antal bomber till bombantalet
    @Test
    public void testAddBombs2() {
        CharReimu reimu = new CharReimu(new Point((Game.GAME_WIDTH / 2), (Game.GAME_HEIGHT - (Game.GAME_HEIGHT / 10))));
        int o_Bombcount = reimu.getBombs();
        reimu.addBombs(-2);
        assertEquals(o_Bombcount, reimu.getBombs());
    }

    //Testar om metoden korrekt gör om powerantalet till 225
    @Test
    public void testSetPower1() {
        CharReimu reimu = new CharReimu(new Point((Game.GAME_WIDTH / 2), (Game.GAME_HEIGHT - (Game.GAME_HEIGHT / 10))));
        reimu.setPower(225);
        assertEquals(225, reimu.getPower());
    }

    //Testar om metoden korrekt gör om powerantalet till 400 om powerantalet blir över 400
    @Test
    public void testSetPower2() {
        CharReimu reimu = new CharReimu(new Point((Game.GAME_WIDTH / 2), (Game.GAME_HEIGHT - (Game.GAME_HEIGHT / 10))));
        reimu.setPower(123125151);
        assertEquals(400, reimu.getPower());
    }

    //Testar om metoden korrekt gör om powerantalet till 0 om powerantalet blir under 0
    @Test
    public void testSetPower3() {
        CharReimu reimu = new CharReimu(new Point((Game.GAME_WIDTH / 2), (Game.GAME_HEIGHT - (Game.GAME_HEIGHT / 10))));
        reimu.setPower(-5123);
        assertEquals(0, reimu.getPower());
    }

    //Testar om metoden korrekt lägger till 200 till powerantalet
    @Test
    public void testAddPower1() {
        CharReimu reimu = new CharReimu(new Point((Game.GAME_WIDTH / 2), (Game.GAME_HEIGHT - (Game.GAME_HEIGHT / 10))));
        int o_Powercount = reimu.getPower();
        reimu.addPower(200);
        assertEquals(o_Powercount + 200, reimu.getPower());
    }

    //Testar om metoden korrekt inte gör någonting om man försöker lägga till negativt antal power till powerantalet
    @Test
    public void testAddPower2() {
        CharReimu reimu = new CharReimu(new Point((Game.GAME_WIDTH / 2), (Game.GAME_HEIGHT - (Game.GAME_HEIGHT / 10))));
        int o_Powercount = reimu.getPower();
        reimu.addPower(-200);
        assertEquals(o_Powercount, reimu.getPower());
    }

    //Testar om metoden korrekt kan göra om graze antalet till 352235
    @Test
    public void testSetGraze1() {
        CharReimu reimu = new CharReimu(new Point((Game.GAME_WIDTH / 2), (Game.GAME_HEIGHT - (Game.GAME_HEIGHT / 10))));
        reimu.setGraze(352235);
        assertEquals(352235, reimu.getGraze());
    }

    //Testar om metoden korrekt gör om graze antalet till 0 om det blir mindre än 0
    @Test
    public void testSetGraze2() {
        CharReimu reimu = new CharReimu(new Point((Game.GAME_WIDTH / 2), (Game.GAME_HEIGHT - (Game.GAME_HEIGHT / 10))));
        reimu.setGraze(-352235);
        assertEquals(0, reimu.getGraze());
    }

    //Testar om metoden korrekt kan lägga till 200 till graze antalet
    @Test
    public void testAddGraze1() {
        CharReimu reimu = new CharReimu(new Point((Game.GAME_WIDTH / 2), (Game.GAME_HEIGHT - (Game.GAME_HEIGHT / 10))));
        int o_Grazecount = reimu.getGraze();
        reimu.addGraze(200);
        assertEquals(o_Grazecount + 200, reimu.getGraze());
    }

    //Testar om metoden korrekt inte gör någonting om man försöker lägga till negativt antal graze till graze antalet.
    @Test
    public void testAddGraze2() {
        CharReimu reimu = new CharReimu(new Point((Game.GAME_WIDTH / 2), (Game.GAME_HEIGHT - (Game.GAME_HEIGHT / 10))));
        int o_Grazecount = reimu.getGraze();
        reimu.addGraze(-200);
        assertEquals(o_Grazecount, reimu.getGraze());
    }

}