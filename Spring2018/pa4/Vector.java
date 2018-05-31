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
	
	/* Required Methods */

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
		return (float) Math.atan(this.Y / this.X);
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
		float angle = this.angleBetween(other);
		return (float) (this.getMagnitude() * other.getMagnitude() * Math.cos(angle));
	}

	@Override
	public Vector scalarMultiply(float scalar) {
		float productX = (this.getX() * scalar);
		float productY = (this.getY() * scalar);
		return new Vector(productX, productY);
	}

	@Override
	public Vector normalize() {
		float magnitude = this.getMagnitude();
		float normalizedX = (this.getX() / magnitude);
		float nomralizedY = (this.getY() / magnitude);
		return new Vector(normalizedX, nomralizedY);
	}
	
	/* New Methods */
	
	/**
	 * Returns the "slope" of this {@link Vector}, as if it were a line segment originating at (0,0).
	 * <p>
	 * If {@link #getX()} would return 0, returns {@link Float#POSITIVE_INFINITY}.
	 * 
	 * @return
	 */
	public float slope() {
		return (this.getX() == 0)? Float.POSITIVE_INFINITY : (this.getY() / this.getX());
	}

	/**
	 * Returns the cross product of this instance and {@code passedVector}.
	 * 
	 * @param passedVector - The {@link Vector} to perform the cross product with
	 * @return The cross product of {@code this} and {@code passedVector}.
	 */
	public Vector crossProduct(Vector passedVector) {
		float angle = this.angleBetween(passedVector);
		float scalar = (float) (this.getMagnitude() * passedVector.getMagnitude() * Math.sin(angle));
		return this.normalize().scalarMultiply(scalar);
	}
	
	/**
	 * Returns the angle between this instance and the passed instance.
	 * 
	 * @param passedVector - The vector to compare against.
	 * @return The angle, in radians, between {@code this} and {@code passedVector}
	 */
	public float angleBetween(Vector passedVector) {
		float dotProduct = this.dotProduct(passedVector);
		float thisMagnitude = this.getMagnitude();
		float otherMagnitude = passedVector.getMagnitude();
		return (float) (Math.acos(dotProduct / (thisMagnitude * otherMagnitude)));
	}

}
