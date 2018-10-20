package assignment;

import java.util.ArrayList;
import assignment.*;

import com.jogamp.opengl.GL2;

/**
 * 
 * @author Alvin Tang 15909204
 *
 */
public class ParticleSystem implements Toggleable {

	public ArrayList<Particle> particles;
	private boolean start;

	private float minAge;
	private float maxAge;

	private float minX;
	private float maxX;
	private float minY;
	private float maxY;

	private int currentPopulation;
	private int maxPopulation = 100;

	private double spawnDelay = 0.05f;
	private double currentTime;
	
	private boolean on = false;

	public ParticleSystem(float minX, float maxX, float minY, float maxY, int maxPopulation, float spawnDelay) {
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		start = false;
		this.maxPopulation = maxPopulation;
		currentPopulation = 0;
		this.spawnDelay = spawnDelay;
		particles = new ArrayList<Particle>();
	}

	public void addParticle(Particle newParticle) {
		particles.add(newParticle);
		currentPopulation++;
	}

	public void init() {
		for (Particle p : particles) {
			p.setAge(RandomRange.randomRange(minAge, maxAge));
		}
		currentTime = 0f;
	}
	
	protected Particle createParticle() {
		Point position = this.randomizePoint();
		float gravityScale = RandomRange.randomRange(-0.02f, -0.015f);
		Particle snow = new Snow(position, gravityScale, true);

		return snow;
	}
	

	public void update(double deltaTime) {
		// Spawn Particle
		currentPopulation = particles.size();
//		System.out.println(currentPopulation);
		if (currentPopulation < maxPopulation && on) {
			currentTime += deltaTime;
			if (currentTime >= spawnDelay) {
				Particle particle = createParticle();
				addParticle(particle);
				currentTime = 0f;
			}
		}

		// Update the position
		for (Particle p : particles) {
			p.update(deltaTime);
			if (p.getY() < -1.25f) {
				p.setDead(true);
			}
			if (p.isDead && on) {
				revive(p);
				p.resetVelocity();
			}
		}
	}

	private void revive(Particle particle) {
		particle.setPosition(randomizePoint());
		particle.setDead(false);
	}

	protected Point randomizePoint() {
		float x = RandomRange.randomRange(minX, maxX);
		float y = RandomRange.randomRange(minY, maxY);

		Point position = new Point(x, y);
		return position;
	}

	public void draw(GL2 gl) {
		for (Particle p : particles) {
			p.draw(gl);
		}
	}

	@Override
	public void turnOn() {
		on = true;
		
	}

	@Override
	public void turnOff() {
		on = false;
		
	}
}
