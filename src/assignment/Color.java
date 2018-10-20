package assignment;


/**
 * 
 * @author Alvin Tang 15909204
 *
 */
public class Color {
	public float r;
	public float g;
	public float b;
	
	public float a;
	
	public float getR() {
		return r;
	}

	public void setR(float r) {
		this.r = r;
	}

	public float getG() {
		return g;
	}

	public void setG(float g) {
		this.g = g;
	}

	public float getB() {
		return b;
	}

	public void setB(float b) {
		this.b = b;
	}

	public Color(float r, float g, float b) {
		a = 1f;
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	/**
	 * Takes actual RBG values (0-255) then converts to
	 * the float (0.0-1.0) counterpart
	 * @param r
	 * @param g
	 * @param b
	 */
	public Color(int r, int g, int b) {
		a = 1f;
		this.r = (float)r/255;
		this.g = (float)g/255;
		this.b = (float)b/255;
	}
	
	public Color(int r, int g, int b, float a ) {
		this(r, g, b);
		this.a = a;
	}
	
	public Color(float r, float g, float b, float a ) {
		this(r, g, b);
		this.a = a;
	}
	
	
	public Color() {
		this.r = 1.0f;
		this.g = 0.0f;
		this.b = 0.0f;
	}

	public float getA() {
		return a;
	}
	
	public static Color lerp(Color start, Color end, float interp) {
		Color newColor = new Color();
		newColor.r = interp(start.r, end.r, interp);
		newColor.g = interp(start.g, end.g, interp);
		newColor.b = interp(start.b, end.b, interp);
		newColor.a = interp(start.a, end.a, interp);
		
		return newColor;
	}
	
	private static float interp(float start, float end, float interp) {
		float length = end - start;
		return (length * interp) + start;
	}
}
