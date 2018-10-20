package assignment;

import java.util.ArrayList;

/**
 * 
 * @author Alvin Tang 15909204
 *
 */
public class AsteroidParticleSystem extends ParticleSystem{

	public AsteroidParticleSystem(float minX, float maxX, float minY, float maxY, int maxPopulation, float spawnDelay) {
		super(minX, maxX, minY, maxY, maxPopulation, spawnDelay);
		
//		this.particles = new ArrayList<Asteroid>();
	}

	@Override
	protected Particle createParticle() {
		Point position = this.randomizePoint();
		float gravityScale = -0.005f;
		int smoothness = RandomRange.randomRange(8, 12);
		Color color = new Color(90, 90, 90);
		Particle asteroid = new Asteroid(position, gravityScale, true, 0.07f, 0.05f, smoothness, color);

		return asteroid;
	}

}
