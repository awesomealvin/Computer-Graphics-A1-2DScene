package assignment;

import com.jogamp.opengl.GL2;

/**
 * 
 * @author Alvin Tang 15909204
 *
 */
public class Time implements Drawable, Toggleable {

	private Color[] dawnColor;
	private final float[] DEFAULT_DAWN_ALPHA = new float[2];

	private Color[] dayColor;
	private final float[] DEFAULT_DAY_ALPHA = new float[2];

	private Color[] duskColor;
	private final float[] DEFAULT_DUSK_ALPHA = new float[2];

	private Color[] nightColor = new Color[2];
	
	private Color[] currentColor = new Color[2];
	
	public double currentTime = 0f;
	private final float durationMultiplier = 2f;
	public final double MAX_TIME = 24.0 * durationMultiplier;
	public final double NIGHT_TIME = 0.0 * durationMultiplier;
	public final double DAWN_TIME = 7.0 * durationMultiplier;
	public final double DAY_TIME = 12.0 * durationMultiplier;
	public final double DUSK_TIME = 19.0 * durationMultiplier;

	public boolean on = false;

	public Time(Color[] dawnColor, Color[] dayColor, Color[] duskColor) {

		this.dawnColor = dawnColor;
		DEFAULT_DAWN_ALPHA[0] = dawnColor[0].getA();
		DEFAULT_DAWN_ALPHA[1] = dawnColor[1].getA();

		this.dayColor = dayColor;
		DEFAULT_DAY_ALPHA[0] = dayColor[0].getA();
		DEFAULT_DAY_ALPHA[1] = dayColor[1].getA();

		this.duskColor = duskColor;
		DEFAULT_DUSK_ALPHA[0] = duskColor[0].getA();
		DEFAULT_DUSK_ALPHA[1] = duskColor[1].getA();

		this.nightColor[0] = new Color(0f, 0f, 0f, 0f);
		this.nightColor[1] = new Color(0f, 0f, 0f, 0f);
		
		this.currentColor[0] = new Color(0f, 0f, 0f, 0f);
		this.currentColor[1] = new Color(0f, 0f, 0f, 0f);
	}
	
	@Override
	public void update(double deltaTime) {
		if (!on) {
			return;
		}
		
		currentTime += deltaTime;
		currentTime = (currentTime > MAX_TIME) ? 0f : currentTime;
		
		calculateCurrentColor(currentTime, NIGHT_TIME, nightColor, DAWN_TIME, dawnColor);
		calculateCurrentColor(currentTime, DAWN_TIME, duskColor, DAY_TIME, dayColor);
		calculateCurrentColor(currentTime, DAY_TIME, dayColor, DUSK_TIME, duskColor);
		calculateCurrentColor(currentTime, DUSK_TIME, duskColor, MAX_TIME, nightColor);
	}
	
	private void calculateCurrentColor(double currentTime, double previousTime, Color[] previousColor, double nextTime, Color[] nextColor) {
		if (currentTime >= previousTime && currentTime < nextTime) {
			double maxDuration = nextTime - previousTime;
			float currentPercentage = (float) ((currentTime - previousTime )/maxDuration);
			
			currentColor[0] = Color.lerp(previousColor[0], nextColor[0], currentPercentage);
			currentColor[1] = Color.lerp(previousColor[1], nextColor[1], currentPercentage);
		}
	}

	/*
	@Override
	public void update(double deltaTime) {
		if (on) {
			currentTime += deltaTime;
			currentTime = (currentTime > MAX_TIME) ? 0f : currentTime;

			// Update dawn color
			float dawnAlphaPercentage = calculateTimePercentage(currentTime, DAWN_TIME, DAY_TIME, NIGHT_TIME);
			updateColor(dawnColor, DEFAULT_DAWN_ALPHA, dawnAlphaPercentage);
			// updateDawnColors(dawnAlphaPercentage);
			// System.out.println("Current Time = " + currentTime + " | " + "Dawn % = " +
			// dawnAlphaPercentage);

			float dayAlphaPercentage = calculateTimePercentage(currentTime, DAY_TIME, DUSK_TIME, DAWN_TIME);
			updateColor(dayColor, DEFAULT_DAY_ALPHA, dayAlphaPercentage);
			// updateDayColors(dayAlphaPercentage);
			// System.out.println("Current Time = " + currentTime + " | " + "Day % = " +
			// dayAlphaPercentage);

			float duskAlphaPercentage = calculateTimePercentage(currentTime, DUSK_TIME, NIGHT_TIME, DAY_TIME);
			updateColor(duskColor, DEFAULT_DUSK_ALPHA, duskAlphaPercentage);
			// System.out.println("Current Time = " + currentTime + " | " + "Dusk % = " +
			// duskAlphaPercentage);

		}
	}
	*/
	
	/*
	private void updateColor(Color[] colors, float defaultAlpha[], float alphaPercentage) {
		for (int i = 0; i < colors.length; i++) {
			colors[i].a = defaultAlpha[i] * alphaPercentage;
			colors[i].a = defaultAlpha[i] * alphaPercentage;
		}
	}
	*/

	private void drawColor(Color[] colors, GL2 gl) {
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

		gl.glBegin(GL2.GL_QUADS);
		gl.glColor4f(colors[0].getR(), colors[0].getG(), colors[0].getB(), colors[0].getA());
		gl.glVertex2f(1f, -1f);
		gl.glVertex2f(-1f, -1f);
		gl.glColor4f(colors[1].getR(), colors[1].getG(), colors[1].getB(), colors[1].getA());
		gl.glVertex2f(-1f, 1f);
		gl.glVertex2f(1f, 1f);
		gl.glEnd();
		gl.glDisable(GL2.GL_BLEND);
	}

	/*
	private float calculateTimePercentage(double currentTime, double timeGoal, double nextTimeGoal,
			double previousTimeGoal) {

		double length;
		double currentLength;
		float percentage;

		if (currentTime >= timeGoal) {
			if (timeGoal > nextTimeGoal) {
				length = (MAX_TIME - timeGoal) + nextTimeGoal;
				currentLength = MAX_TIME - currentTime;
				percentage = (float) (currentLength / length);

			} else {
				length = nextTimeGoal - timeGoal;
				currentLength = currentTime - timeGoal;
				percentage = (float) (1.0 - (currentLength / length));
			}

		} else {
			if (previousTimeGoal > timeGoal) {
				length = (MAX_TIME - previousTimeGoal) + timeGoal;
				currentLength = currentTime;
				// System.out.println("Current Length = " + currentLength +" | Length = " +
				// length);
			} else {
				length = timeGoal - previousTimeGoal;
				currentLength = currentTime - previousTimeGoal;
				// System.out.println("Current Length = " + currentLength +" | Length = " +
				// length);
			}
			percentage = (float) (currentLength / length);
		}

		percentage = (percentage < 0f) ? 0f : percentage;

		return percentage;
	}
	*/

	@Override
	public void draw(GL2 gl) {
		drawColor(currentColor, gl);
//		drawColor(dawnColor, gl);
//		drawColor(dayColor, gl);
//		drawColor(duskColor, gl);
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
