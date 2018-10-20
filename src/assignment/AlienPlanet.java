package assignment;

import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.LinkedBlockingQueue;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

import missileLauncher.MissileLauncher;
/**
 * 
 * @author Alvin Tang 15909204
 *
 */
public class AlienPlanet implements GLEventListener, MouseListener {
	private LinkedBlockingQueue<MouseEvent> pendingEvents = new LinkedBlockingQueue<MouseEvent>();

	Surface surfaces[];
	AsteroidParticleSystem asteroidPSys;
	ButtonPanel buttonPanel;
	Atmosphere atmosphere;
	Time time;
	Stars stars;
	ParticleSystem sys;
	Spaceship spaceship;
	GrassSystem grassSystem;
	MissileLauncher missileLauncher;

	private static int windowWidth = 640;
	private static int windowHeight = 640;

	private double prevTime;
	
	
	@Override
	public void display(GLAutoDrawable gld) {
		while (this.handleNextMouseEvent());

		// Calculate DeltaTime
		double currentTime = (System.currentTimeMillis() / 1000.0);
		double deltaTime = currentTime - prevTime;
		// Update the objects
		if (prevTime > 0f) {
			asteroidPSys.update(deltaTime);
			//sys.update(deltaTime);
			time.update(deltaTime);
			spaceship.update(deltaTime);
			
			grassSystem.update(deltaTime);
			missileLauncher.update(deltaTime);
		}
		prevTime = currentTime;

		// Draw the objects
		GL2 gl = gld.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		stars.draw(gl);
		atmosphere.draw(gl);
		time.draw(gl);
		asteroidPSys.draw(gl);
		for (Surface s : surfaces) {
			s.draw(gl);
		}
		spaceship.draw(gl);
		
		//test
		
		grassSystem.draw(gl);
		
		buttonPanel.draw(gl);
		missileLauncher.draw(gl);
		//sys.draw(gl);
		gl.glFlush();
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		//sys = new ParticleSystem(-1f, 1f, 0.9f, 0.95f,3000000, 0.00f);
		initializeSurfaces(gl);
		initializeStars(gl);
		initializeAsteroidParticleSystem();
		initializeAtmosphere();
		initializeTime();
		initializeGrassSystem();
		initializeSpaceship();
		initializeMissileLauncher();
		initializeButtons();
	}
	
	private void initializeMissileLauncher() {
		missileLauncher = new MissileLauncher(asteroidPSys.particles);
	}
	
	private void initializeGrassSystem() {
		grassSystem = new GrassSystem(surfaces[2].minHeight, time);
	}

	private void initializeSpaceship() {
		float minSpawnX = -1.5f; // sadfafsdaf
		float maxSpawnX = -2f;
		float minSpawnY = 0.3f;
		float maxSpawnY = 0.8f;
		spaceship = new Spaceship(minSpawnX, maxSpawnX, minSpawnY, maxSpawnY);
	}
	
	
	private void initializeButtons() {
		Button[] buttons = new Button[5];
		//sdfadf sd
		buttons[0] = new Button("Atmosphere", atmosphere);
		buttons[1] = new Button("Time", time);
		buttons[2] = new Button("Asteroid", asteroidPSys);
		buttons[3] = new Button("Spaceship", spaceship);
		buttons[4] = new Button("Missile", missileLauncher);
		
		Color panelColor = new Color(150, 150, 150);
		
		buttonPanel = new ButtonPanel(0.15f, panelColor, buttons);
	}
	
	
	private void initializeAtmosphere() {
		Color[] atmosphereColor = new Color[2];
		atmosphereColor[0] = new Color(142, 255, 0, 0.5f);
		atmosphereColor[1] = new Color(0, 0, 0, 0f);
		
		atmosphere = new Atmosphere(atmosphereColor);
	}
	
	private void initializeTime() {
		Color[] dawnColor = new Color[2];
		dawnColor[0] = new Color(255, 140, 0, 0.99f);
		dawnColor[1] = new Color(38, 92, 255, 0.99f);
		
		Color[] dayColor = new Color[2];
		dayColor[0] = new Color(0, 255, 233, 1f);
		dayColor[1] = new Color(45, 86, 252, 1f);
		
		Color[] duskColor = new Color[2];
		duskColor[0] = new Color(255, 140, 0, 0.99f);
		duskColor[1] = new Color(38, 92, 255, 0.99f);
		
		time = new Time(dawnColor, dayColor, duskColor);
	}

	
	private void initializeAsteroidParticleSystem() {
		float minX = -1f;
		float maxX = 1f;
		float minY = 1.5f;
		float maxY = 2.5f;
		int maxPopulation = 6;
		float spawnDelay = 1f;
		
		asteroidPSys = new AsteroidParticleSystem(minX, maxX, minY, maxY, maxPopulation, spawnDelay);
	}
	
	private void initializeStars(GL2 gl) {
		stars = new Stars(-1f, (1f - 0.15f));
		stars.buildDisplayList(gl);
	}

	private void initializeSurfaces(GL2 gl) {
		// Initialize Surfaces
		// [2] = front
		// [1] = inner
		// [0] = background
		surfaces = new Surface[3];
		float r = 142f / 255f;
		float g = 255f / 255f;
		float b = 0f / 255f;
		Color frontSurfaceColor = new Color(r, g, b);
		Color innerSurfaceColor = new Color(r * 0.75f, g * 0.75f, b * 0.75f);
		Color backgroundSurfaceColor = new Color(r * 0.25f, g * 0.25f, b * 0.25f);

		surfaces[2] = new Surface(-0.65f, 0.02f, 70, 0.08f, frontSurfaceColor);
		surfaces[1] = new Surface(-0.5f, 0.07f, 40, 0.05f, innerSurfaceColor);
		surfaces[0] = new Surface(-0.3f, 0.15f, 25, 0.04f, backgroundSurfaceColor);

		for (Surface s : surfaces) {
			s.buildDisplayList(gl);
		}
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		height = (height == 0) ? 1 : height; // prevent divide by zero
		windowWidth = width;
		windowHeight = height;
	}


	public static void main(String[] args) {
		Frame frame = new Frame("Simple JOGL");
		GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);
		GLCanvas canvas = new GLCanvas(capabilities);

		AlienPlanet alientPlanet = new AlienPlanet();
		canvas.addGLEventListener(alientPlanet);
		canvas.addMouseListener(alientPlanet);
		frame.add(canvas);
		frame.setSize(windowWidth, windowHeight);

		final Animator animator = new Animator(canvas);
		animator.start();

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// Run this on another thread than the AWT event queue to
				// make sure the call to Animator.stop() completes before
				// exiting
				new Thread(new Runnable() {

					public void run() {
						animator.stop();
						System.exit(0);
					}
				}).start();
			}
		});

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		canvas.requestFocusInWindow();
	}
	
	
	private boolean handleNextMouseEvent() {
		boolean eventsToHandle = false;
		
		if (this.pendingEvents.size() > 0) {
			MouseEvent nextEvent;
			try {
				nextEvent = this.pendingEvents.take();
				this.handleMouseEvent(nextEvent);
			} catch (InterruptedException e) {
				//pew
			}
			eventsToHandle = true;
		}
		
		return eventsToHandle;
	}
	
	private void handleMouseEvent(MouseEvent e) {
		float mouseX = e.getX();
		float mouseY = windowHeight - e.getY();
		
		float x = 2.0f * (mouseX / windowWidth) - 1.0f;
		float y = 2.0f * (mouseY / windowHeight) - 1.0f;
		
		Point p = new Point(x, y);
		
		buttonPanel.handleClick(p);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		try {
			this.pendingEvents.put(arg0);
		} catch (InterruptedException e) {
			
		}
				
	}
}
