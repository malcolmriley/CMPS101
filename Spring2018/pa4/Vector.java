public class Vector implements IPa4Vector {
	
	// Local Fields
	private final float X;
	private final float Y;

	/**
	 * The default constructor should create a new Vector with no magnitude.
	 */
	public Vector() {
		this(0F, 0F);
	}

	/**
	 * This constructor takes an x and a y coordinate for the Vector.
	 * 
	 * @param passedX - The X coordinate
	 * @param passedY - The Y coordinate
	 */
	public Vector(float passedX, float passedY) {
		this.X = passedX;
		this.Y = passedY;
	}

	/**
	 * This "constructor" takes an angle and a magnitude for the Vector.
	 * 
	 * @param angle - The angle, in radians, from (0,0)
	 * @param magnitude - The magnitude of the vector
	 * @return A suitably-instantiated vector
	 */
	public static Vector polarVector(float angle, float magnitude) {
		// TODO
		return null;
	}

	@Override
	public float getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getAngle() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getMagnitude() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Vector add(Vector other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector subtract(Vector other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float dotProduct(Vector other) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Vector scalarMultiply(float scalar) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector normalize() {
		// TODO Auto-generated method stub
		return null;
	}

}
