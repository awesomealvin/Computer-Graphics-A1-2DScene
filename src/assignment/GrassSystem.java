package assignment;

import com.jogamp.opengl.GL2;

/**
 * 
 * @author Alvin Tang 15909204
 *
 */
public class GrassSystem implements Drawable{
	
	private final int minAmount = 150;
	private final int maxAmount = 170;
	private int amount;
	
	private Grass[] grass;
	
	private float maxSpawnHeight;
	
	private float minGrassHeight = 0.04f;
	private float maxGrassHeight = 0.12f;
	private float minGrassWidth = 0.02f;
	private float maxGrassWidth = 0.04f;
	
	private Time time;
	
	private boolean nightTime;
	
	public GrassSystem(float maxSpawnHeight, Time time) {
		this.maxSpawnHeight = maxSpawnHeight;
		this.time = time;
		
		amount = RandomRange.randomRange(minAmount, maxAmount);
		
		init();
	}
	
	private void init() {
		grass = new Grass[amount];
		
		for (int i = 0; i < grass.length; i++) {
			float x = RandomRange.randomRange(-1f, 1f);
			float y = RandomRange.randomRange(-1f, maxSpawnHeight);
			Point p = new Point(x, y);
			float width = RandomRange.randomRange(minGrassWidth, maxGrassWidth);
			float height = RandomRange.randomRange(minGrassHeight, maxGrassHeight);
			
			grass[i] = new Grass(width, height, new Point(x, y));
		}
	}

	@Override
	public void update(double deltaTime) {
		this.nightTime = isNightTime();
		//System.out.println(nightTime);
		for (Grass g : grass) {
			g.canPulse = (nightTime)? true : false;
			
			g.update(deltaTime);
		}
	}
	
	private boolean isNightTime() {
		boolean nightTime = false;
		if (time.currentTime >= 0 && time.currentTime < time.DAWN_TIME) {
			nightTime = true;
		}
		
		if (time.currentTime >= time.DUSK_TIME && time.currentTime <= time.MAX_TIME) {
//			System.out.println(time.currentTime + " | " + time.DUSK_TIME);
			nightTime = true;
		}
		
		return nightTime;
	}

	@Override
	public void draw(GL2 gl) {
		for (Grass g : grass) {
			g.draw(gl);
		}
		
	}

}
