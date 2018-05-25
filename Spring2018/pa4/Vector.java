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
		float xValue = (float) (magnitude * Math.cos(angle));
		float yValue = (float) (magnitude * Math.sin(angle));
		return new Vector(xValue, yValue);
	}

	@Override
	public float getX() {
		return this.X;
	}

	@Override
	public float getY() {
		return this.Y;
	}

	@Override
	public float getAngle() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getMagnitude() {
		return (float) Math.sqrt(this.X * this.X + this.Y  * this.Y);
	}

	@Override
	public Vector add(Vector other) {
		float sumX = (this.getX() + other.getX());
		float sumY = (this.getY() + other.getY());
		return new Vector(sumX, sumY);
	}

	@Override
	public Vector subtract(Vector other) {
		float diffX = (this.getX() - other.getX());
		float diffY = (this.getY() - other.getY());
		return new Vector(diffX, diffY);
	}

	@Override
	public float dotProduct(Vector other) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * Returns the cross product of this instance and {@code passedVector}.
	 * 
	 * @param passedVector - The {@link Vector} to perform the cross product with
	 * @return The cross product of {@code this} and {@code passedVector}.
	 */
	public Vector crossProduct(Vector passedVector) {
		// TODO
		return null;
	}

	@Override
	public Vector scalarMultiply(float scalar) {
		float productX = (this.getX() * scalar);
		float productY = (this.getY() * scalar);
		return new Vector(productX, productY);
	}

	@Override
	public Vector normalize() {
		// TODO Auto-generated method stub
		return null;
	}

}
