package bullethell.game;import java.awt.Canvas;import java.awt.Color;import java.awt.Dimension;import java.awt.Graphics2D;import java.awt.Point;import java.awt.Rectangle;import java.awt.event.KeyAdapter;import java.awt.event.KeyEvent;import java.awt.event.WindowAdapter;import java.awt.event.WindowEvent;import java.awt.image.BufferStrategy;import java.util.ArrayList;import java.util.Arrays;import java.util.Collections;import java.util.List;import javax.swing.JFrame;import javax.swing.JPanel;import bullethell.character.CharReimu;import bullethell.game.internalgui.ConsoleGui;import bullethell.game.internalgui.LifeGui;import bullethell.game.internalgui.MessageHandle;import bullethell.game.internalgui.WaitForKeyPress;import bullethell.highscore.Score;import bullethell.highscore.ScoreEntity;import bullethell.highscore.ScoreList;import bullethell.highscore.ScoreTicker;import bullethell.karta.Karta;/** * The main hook of our game. This class with both act as a manager * for the display and central mediator for the game logic.  *  * Display management will consist of a loop that cycles round all * entities in the game asking them to move and then drawing them * in the appropriate place. With the help of an inner class it * will also allow the player to control the main ship. *  * As a mediator it will be informed when entities within our game * detect events (e.g. alient killed, played died) and will take * appropriate game actions. *  * @author Swedman, Axel and the guy who made space invaders. */public class Game extends Canvas {	/** Used by getInstance. Gets set in the constructor. The custructor will fail if this isn't null */	private static Game instance;	/**	 * Gets the one and only instance of the game.	 * @return the instance	 */	public static Game getInstance() {		return instance;	}		/** The stragey that allows us to use accelerate page flipping */	private BufferStrategy strategy;	/** True if the game is currently "running", i.e. the game loop is looping */	private boolean gameRunning = true;	/** The list of all the entities that exist in our game */	private List<Entity> entities = Collections.synchronizedList(new ArrayList<Entity>());	/** The list of entities that need to be removed from the game this loop */	private List<Entity> removeList = Collections.synchronizedList(new ArrayList<Entity>());	/** The entity representing the player */	/** The speed at which the player's ship should move (pixels/sec) */	private double moveSpeed = 300.0;	/** True if the left cursor key is currently pressed */	private boolean leftPressed = false;	/** True if the right cursor key is currently pressed */	private boolean rightPressed = false;        /** True if the up cursor key is currently pressed */        private boolean upPressed = false;        /** True if the down cursor key is currently pressed */        private boolean downPressed = false;	/** True if we are firing */	private boolean zPressed = false;        /** True if we are going slow */        private boolean shiftPressed = false;        /** True if we are bombing */        private boolean xPressed = false;	/** The player himself */	private CharReimu ply;	/** How long should we sleep. Initially added to allow slowmotion but all it does it create fake lag. Don't change! */	int sleeptime = 10; // Default 10.	/** The speed the game will move in. Used for slow motion */	private float speed = 1.0f;	// Requested by the HighScore group.	private Score score;	private ScoreEntity scoreGui;	private ScoreList scoreList = new ScoreList("highscore.txt");	/** Used for drawing UI elements */	private List<GuiItem> guiitems = Collections.synchronizedList(new ArrayList<GuiItem>());	/** GUI elements to be removed */	private List<GuiItem> guiremove = Collections.synchronizedList(new ArrayList<GuiItem>());	/** The waiting for key press message GuiItem. We need to store it so we can remove it when relevant.  */	private WaitForKeyPress waitForKeyPress = null;	/** The console text field GuiItem. We need to store it so we can remove it when relevant. */	private ConsoleGui consoleInputField;	/** The current map */	private Karta map;	/** The time the game started. Requested by the score group */	private long startTime;	/** The message handle that displayes the messages */	private MessageHandle msgHandle;	/** Game width. Set from constructor (these used to be final and now people are dependent on them) */	public static int GAME_WIDTH = -1;	/** Game height. Set from constructor (these used to be final and now people are dependent on them) */	public static int GAME_HEIGHT = -1;	/** The number of in-game ms the game has been running */	private long gameTime = 0L;	/** Godmode :) */	private boolean godmode = false;		/**	 * Construct our game and set it running.	 *	 * @param width Game width	 * @param height Game height	 * @throws Exception If anything goes wrong it ain't our fault!	 */	public Game(int width, int height) throws Exception	{		// So we can allow people to use Game.getInstance() and not being forced to pass down a reference to this instance everywhere we only allow one.		// And there is no point having two games running at the same time.		if (Game.getInstance() != null)		{			throw new Exception("Can only exist one Game");		}		GAME_WIDTH = width;		GAME_HEIGHT = height;		Game.instance = this;		// create a frame to contain our game		JFrame container = new JFrame("Bullet Hell");				// get hold the content of the frame and set up the resolution of the game		JPanel panel = (JPanel) container.getContentPane();		panel.setPreferredSize(new Dimension(GAME_WIDTH,GAME_HEIGHT));		panel.setLayout(null);				// setup our canvas size and put it into the content of the frame		setBounds(0,0,GAME_WIDTH,GAME_HEIGHT);		panel.add(this);				// Tell AWT not to bother repainting our canvas since we're		// going to do that our self in accelerated mode		setIgnoreRepaint(true);				// finally make the window visible 		container.pack();		container.setResizable(false);		container.setVisible(true);				// add a listener to respond to the user closing the window. If they		// do we'd like to exit the game		container.addWindowListener(new WindowAdapter() {			@Override			public void windowClosing(WindowEvent e) {				System.exit(0);			}		});		// Put the game in the middle of the screen.		container.setLocationRelativeTo(null);				// add a key input system (defined below) to our canvas		// so we can respond to key pressed		addKeyListener(new KeyInputHandler());				// request the focus so key events come to us		requestFocus();		// create the buffering strategy which will allow AWT		// to manage our accelerated graphics		createBufferStrategy(2);		strategy = getBufferStrategy();				// initialise the entities in our game so there's something		// to see at startup		startGame();	}	/**	 * Showes the message to the text area. The message is displayed for around 6 seconds (after 5.5 it starts fading out, after 6.5 it is completly gone)	 * @param msg The text to display. New line is supported.	 */	public void showMessage(String msg)	{		String[] add = msg.split("\n");		for (String s : add)			msgHandle.addMessage(s);	}		/**	 * Start a fresh game, this should clear out any old data and	 * create a new set.	 */	private void startGame()	{		// clear out any existing entities and intialise a new set		entities.clear();		guiitems.clear();		// Needed to be able to know for how long the game has been running.		startTime = System.currentTimeMillis();		// Create the map first, add all entities and finally add the GUI.		initEntities();		initGui();		// Show welcome message                showMessage("Welcome to BulletHelltest \n\n*** NEW GAME HAS STARTED! ***");		// blank out any keyboard settings we might currently have		leftPressed = false;		rightPressed = false;		upPressed = false;                downPressed = false;                shiftPressed = false;                zPressed = false;                xPressed = false;		// Restart gametime.		gameTime = 0L;	}	/**	 * Gets the number of milliseconds since the game started. This is reset each round so it will be number of seconds this round	 * @return The time in ms.	 * @deprecated This isn't used as far as I know and it does not give any respect to slow motion, lag or other things that should be counted differently.	 */	public long getTimeSinceStart()	{		return System.currentTimeMillis() - startTime;	}	/**	 * Returns the number of milliseconds since this round started but actually calculates with slow motion and all other things you should respect.	 * 20 ms here might be 40 real ms if the game is in 50% slow motion for example.	 * @return	 */	public long getGameTime()	{		return gameTime;	}	/**	 * Gets the current visible view. Currently the game is only within the screen thus we are returning the screen sice.	 * @return	 */	public Rectangle getView()	{		return new Rectangle(GAME_WIDTH, GAME_HEIGHT);	}	/**	 * If this is called the game will wait for a key press and is then reset. The <code>message</code> is displayed.	 */	private void waitForKeyPress(String message)	{		waitForKeyPress = new WaitForKeyPress(message);		addGui(waitForKeyPress);	}	/**	 * If this is called we are no longer waiting for a key press.	 */	private void waitForKeyPressEnd()	{		guiremove.add(waitForKeyPress);		waitForKeyPress = null;	}	/**	 * Initalise all GUI items that will be displayed on the screen.	 */	private void initGui()	{		// Add the ScoreEntity which is actually a GuiItem so we can see our score		scoreGui = new ScoreEntity(getScore());		addGui(scoreGui);		// Add the message handle which will display messages. Used mostly by the console.		msgHandle = new MessageHandle();		addGui(msgHandle);		// Shows the healthbar		addGui(new LifeGui());		addGui(new GuiItem()		{			@Override			public void draw(Graphics2D g)			{				g.setColor(Color.ORANGE);				g.drawString("Time: " + (getGameTime() / 1000), GAME_WIDTH - 100, 50);			}		});	}		/**	 * Initialise the starting state of the entities (ship and aliens). Each	 * entitiy will be added to the overall list of entities in the game.	 */	private void initEntities()	{		// Create our character and add it to the game.		ply = new CharReimu(new Point(200, 350));		ply.setMaximalSpeed((int) moveSpeed);		addEntity(ply);	}	/**	 * Check whether the game is waiting for a key to be pressed or not.	 * @return	 */	public boolean isWaitingForKeyPress()	{		// We are only waiting for a key press if we have the waitForKeyPress GuiItem.		return waitForKeyPress != null;	}		/**	 * Remove an entity from the game. The entity removed will	 * no longer move or be drawn.	 * 	 * @param entity The entity that should be removed	 */	public void removeEntity(Entity entity)	{		// Add to the remove list for later removal.		removeList.add(entity);	}	/**	 * Adds a entity to the game.	 * @param entity The entity to add	 */	public void addEntity(Entity entity)	{		entities.add(entity);	}	/**	 * Removed the GuiItem from the game.	 * @param item The item to remove	 */	public void removeGui(GuiItem item)	{		guiremove.add(item);	}	/**	 * Adds the GuiItem to the GUI.	 * @param item The item to add	 */	public void addGui(GuiItem item)	{		guiitems.add(item);	}		/**	 * Notification that the player has died. This will immedetly end the game.	 * If you just want to damage the character you use a fitting method on the character instance.	 * game.getCharacter() to get the character.	 */	public void notifyDeath()	{		if (godmode)			return;				scoreList.add(getScore());		scoreList.writeFile();		waitForKeyPress("You lost the game. Want to try again? Score: " + getScore().getPoäng());	}		/**	 * Notification that the player has won the game.	 */	public void notifyWin()	{		// Add the score to the list and save it. We also give a bonus of 50% for winning.		getScore().addScore(getScore().getPoäng() / 2);		scoreList.add(getScore());		scoreList.writeFile();		waitForKeyPress("Congratulations! You win the game! Score: " + getScore().getPoäng());	}		/**	 * Notification that an enemy has been killed	 * Makes sure all entities (that needs to know) gets to know	 *	 * @deprecated Use notifyEnemyKilled(this) instead	 */	public void notifyEnemyKilled()	{		for (int i=0;i<entities.size();i++) {			Entity entity = entities.get(i);						if (entity instanceof ExtendedEntity)			{				((ExtendedEntity) entity).notifyEnemyKilled(null);			}		}	}	public void notifyEnemyKilled(Entity killed)	{		// If this enemy died it shouldn't be in game anymore.		removeEntity(killed);		// We tell all other entities that this entity died so that they can act accordingly.		for (int i=0;i<entities.size();i++)		{			Entity entity = entities.get(i);			if (entity instanceof ExtendedEntity)			{				((ExtendedEntity) entity).notifyEnemyKilled(killed);			}		}	}	/**	 * The main game loop. This loop is running during all game	 * play as is responsible for the following activities:	 * <p>	 * - Working out the speed of the game loop to update moves	 * - Moving the game entities	 * - Drawing the screen contents (entities, text)	 * - Updating game events	 * - Checking Input	 * <p>	 */	public void gameLoop() {		// This is used later in the loop. If it was 0 the game would think we had a enormiuos lag spike and thus we set this to the current time.		long lastLoopTime = System.currentTimeMillis();				// keep looping round til the game ends		while (gameRunning) {			// work out how long its been since the last update, this			// will be used to calculate how far the entities should			// move this loop			long delta = (long) ((System.currentTimeMillis() - lastLoopTime) * getSpeed());			lastLoopTime = System.currentTimeMillis();			// Add to the game time unless the game has already ended.			if (!isWaitingForKeyPress())				gameTime += delta;			// brute force collisions, compare every entity against			// every other entity. If any of them collide notify			// both entities that the collision has occured			for (int p=0;p<entities.size();p++) {				for (int s=p+1;s<entities.size();s++) {					Entity me = entities.get(p);					Entity him = entities.get(s);										// Sometimes the lists change while running despite my effort to stop it. Especially when the game just started.					// Therefore I added this to prevent crashes.					if (me == null || him == null)						continue;					if (me.collidesWith(him)) {						me.collidedWith(him);						him.collidedWith(me);					}				}			}			// Get hold of a graphics context for the accelerated 			// surface and blank it out			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();			g.setColor(Color.black);			g.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);						// cycle round asking each entity to move itself			if (!isWaitingForKeyPress()) {				for (int i=0;i<entities.size();i++) {					Entity entity = entities.get(i);					// This list is also changed in another thread sometimes. This is a crash protection.					if (entity == null)						continue;					// Move the entity.					entity.move(delta);					// This is called for legacy purposes.					entity.doLogic();				}			}			// resolve the movement of the player. First assume the ship			// isn't moving. If either cursor key is pressed then			// update the movement appropraitely			getCharacter().setHorizontalMovement(0);                        getCharacter().setVerticalMovement(0);			if ((leftPressed) && (!rightPressed)) {				getCharacter().setHorizontalMovement(-ply.getMaximalSpeed());			} else if ((rightPressed) && (!leftPressed)) {				getCharacter().setHorizontalMovement(ply.getMaximalSpeed());			}                        if ((upPressed) && (!downPressed)) {                                getCharacter().setVerticalMovement(-ply.getMaximalSpeed());                        } else if ((downPressed) && (!upPressed)) {                                getCharacter().setVerticalMovement(ply.getMaximalSpeed());                        }                        if (shiftPressed) {                                getCharacter().setVerticalMovement(ply.getVerticalMovement() / 2);                                getCharacter().setHorizontalMovement(ply.getHorizontalMovement() / 2);                        }                        if (zPressed) {                            /**                             * Add shooting projectile code here                             */                        }                        if (xPressed) {                            /**                             * Add shooting bomb code here                             */                        }						// cycle round drawing all the entities we have in the game			for (int i=0;i<entities.size();i++) {				Entity entity = entities.get(i);				// Another crash protection.				if (entity == null)					continue;				entity.draw(g);			}			for (int i = 0; i < guiitems.size(); i++)			{				// GuiItems are always drawn on the screen directly, the entities might move with the camera in the future.				guiitems.get(i).draw(g);			}						// remove any entity that has been marked for clear up			entities.removeAll(removeList);			removeList.clear();			guiitems.removeAll(guiremove);			guiremove.clear();						// finally, we've completed drawing so clear up the graphics			// and flip the buffer over			g.dispose();			strategy.show();			// finally pause for a bit. Note: this should run us at about			// 100 fps but on windows this might vary each loop due to			// a bad implementation of timer			try { Thread.sleep(sleeptime); } catch (Exception e) {}		}	}	/**	 * @return the speed	 */	public float getSpeed() {		return speed;	}	/**	 * Sets the game speed. 1.0 is default. 0.5 is 50%. 2.0 is 200%	 * @param speed the speed to set	 */	public void setSpeed(float speed) {		this.speed = speed;	}	/**	 * @return the ply	 */	public CharReimu getCharacter() {		return ply;	}	/**	 * @return the score	 */	public Score getScore() {		return score;	}	/**	 * A class to handle keyboard input from the user. The class	 * handles both dynamic input during game play, i.e. left/right 	 * and shoot, and more static type input (i.e. press any key to	 * continue)	 * 	 * This has been implemented as an inner class more through 	 * habbit then anything else. Its perfectly normal to implement	 * this as seperate class if slight less convienient.	 * 	 * @author Kevin Glass	 */	private class KeyInputHandler extends KeyAdapter {		/** The number of key presses we've had while waiting for an "any key" press */		private int pressCount = 1;				/**		 * Notification from AWT that a key has been pressed. Note that		 * a key being pressed is equal to being pushed down but *NOT*		 * released. Thats where keyTyped() comes in.		 *		 * @param e The details of the key that was pressed 		 */		@Override		public void keyPressed(KeyEvent e)		{			// If the key to open / close the console is pressed act accordingly. Enter can only be used to close the console, not to open it.			if (e.getKeyCode() == KeyEvent.VK_F12 || e.getKeyCode() == KeyEvent.VK_ENTER)			{				if (consoleInputField != null && consoleInputField.getCmd() != null)				{					// A command has been entered, run it and remove the input field.					handleCommand(consoleInputField.getCmd());					removeGui(consoleInputField);					consoleInputField = null;				}				else if (e.getKeyCode() != KeyEvent.VK_ENTER)				{					// Show the console input field.					consoleInputField = new ConsoleGui("");					addGui(consoleInputField);				}			}			// if we're waiting for an "any key" typed then we don't			// want to do anything with just a "press"			if (isWaitingForKeyPress() || consoleInputField != null) {				return;			}						// Movement			if (e.getKeyCode() == KeyEvent.VK_LEFT) {				leftPressed = true;			}			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {				rightPressed = true;			}			if (e.getKeyCode() == KeyEvent.VK_UP) {				upPressed = true;			}                        if (e.getKeyCode() == KeyEvent.VK_DOWN) {                                downPressed = true;                        }                        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {                                shiftPressed = true;                        }                        if (e.getKeyCode() == KeyEvent.VK_Z) {                                zPressed = true;                        }                        if (e.getKeyCode() == KeyEvent.VK_X) {                                xPressed = true;                        }		} 				/**		 * Notification from AWT that a key has been released.		 *		 * @param e The details of the key that was released 		 */		@Override		public void keyReleased(KeyEvent e) {			// if we're waiting for an "any key" typed then we don't 			// want to do anything with just a "released"			if (isWaitingForKeyPress() || consoleInputField != null) {				return;			}			// Movement.			if (e.getKeyCode() == KeyEvent.VK_LEFT) {				leftPressed = false;			}			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {				rightPressed = false;			}			if (e.getKeyCode() == KeyEvent.VK_UP) {				upPressed = false;			}                        if (e.getKeyCode() == KeyEvent.VK_DOWN) {                                downPressed = false;                        }                        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {                                shiftPressed = false;                        }                        if (e.getKeyCode() == KeyEvent.VK_Z) {                                zPressed = false;                        }                        if (e.getKeyCode() == KeyEvent.VK_X) {                                xPressed = false;                        }		}		/**		 * Notification from AWT that a key has been typed. Note that		 * typing a key means to both press and then release it.		 *		 * @param e The details of the key that was typed. 		 */		@Override		public void keyTyped(KeyEvent e)		{			// If the console is open this isn't a "any key", it is input to the console.			if (consoleInputField != null)			{				// Allow backspace.				if (e.getKeyChar() == (char) 8 && consoleInputField.getCmd().length() > 0)				{					String nev = consoleInputField.getCmd();					nev = nev.substring(0, nev.length() - 1);					consoleInputField.setCmd(nev);				}				else					consoleInputField.appendCmd("" + e.getKeyChar());			}			// if we're waiting for a "any key" type then			// check if we've recieved any recently. We may			// have had a keyType() event from the user releasing			// the shoot or move keys, hence the use of the "pressCount"			// counter.			else if (isWaitingForKeyPress()) {				if (pressCount == 1) {					// since we've now recieved our key typed					// event we can mark it as such and start					// our new game					waitForKeyPressEnd();					startGame();					pressCount = 0;				} else {					pressCount++;				}			}						// if we hit escape, then quit the game unless the console is open.			if (e.getKeyChar() == 27)			{				if (consoleInputField != null)				{					removeGui(consoleInputField);					consoleInputField = null;				}				else					System.exit(0);			}		}		/**		 * Splits the string and checks if the first word is a valid command.		 * @param instr		 */		private void handleCommand(String instr)		{			// There is no empty command.			if (instr.equals(""))				return;			// Remove any new lines and split it into words.			String[] cmd = instr.replaceAll("\n", "").split(" ");			if (cmd[0].equalsIgnoreCase("reset"))			{				// The wait for key press resets the game after you click something.				if (!isWaitingForKeyPress())					waitForKeyPress("Game reset by console");				else					showMessage("Game is already reset!");			}			else if (cmd[0].equalsIgnoreCase("lag"))			{				try				{					sleeptime = Integer.parseInt(cmd[1]);				}				catch (Exception ex)				{					showMessage("Formating error in command.");				}			}			else if (cmd[0].equalsIgnoreCase("speed"))			{				try				{					setSpeed(Float.parseFloat(cmd[1]));				}				catch (Exception ex)				{					showMessage("Formating error in command.");				}			}			else if (cmd[0].equalsIgnoreCase("setlives"))			{				try				{					ply.setLives(Integer.parseInt(cmd[1]));				}				catch (Exception ex)				{					showMessage("Formating error in command.");				}			}                        else if (cmd[0].equalsIgnoreCase("setbombs"))                        {                                try                                {                                    ply.setBombs(Integer.parseInt(cmd[1]));                                }                                catch (Exception ex)                                {                                        showMessage("Formating error in command.");                                }                        }			else if (cmd[0].equalsIgnoreCase("god"))			{				godmode = !godmode;				showMessage("Godmode is now: " + godmode);			}			else if (cmd[0].equalsIgnoreCase("help") || cmd[0].equalsIgnoreCase("list"))			{				showMessage("reset - Resets the game\n" +						"lag <int> - Sets the sleep time. Default 10.\n" +						"speed <float> - Sets the game speed. 1.0 is default." +						"help or list - Shows this help.\n" +						"setlives - Sets your life\n" +                                                "setbombs - Sets your bombs\n" +						"god - Toggles godmode");			}			else			{				showMessage("Unknown command: " + cmd[0]);			}				for (byte b : cmd[0].getBytes())					System.out.print(b + ", ");		}	}		/**	 * The entry point into the game. We'll simply create an	 * instance of class which will start the display and game	 * loop.	 * 	 * @param argv The arguments that are passed into our game	 */	public static void main(String argv[]) {		try		{			// Create the game.			Game g = new Game(700, 950);			// Start the main game loop, note: this method will not			// return until the game has finished running. Hence we are			// using the actual main thread to run the game.			g.gameLoop();		}		catch (Exception ex)		{			// Since we used the space invaders base which throws exceptions we must catch them here.			System.err.println("*** THE CRASH ***");			ex.printStackTrace();		}	}}