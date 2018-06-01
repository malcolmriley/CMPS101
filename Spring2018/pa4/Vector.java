public class Vector {
	
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

	/**
	 * Returns the X component of this {@link Vector} instance.
	 * 
	 * @return This {@link Vector}'s X component.
	 */
	public float getX() {
		return this.X;
	}

	/**
	 * Returns the Y component of this {@link Vector} instance.
	 * 
	 * @return This {@link Vector}'s Y component.
	 */
	public float getY() {
		return this.Y;
	}

	/**
	 * Returns the angle of the {@link Vector}.
	 * 
	 * @return This {@link Vector}'s angle.
	 */
	public float getAngle() {
		return (float) Math.atan(this.Y / this.X);
	}

	/**
	 * Returns the magnitude of this {@link Vector}.
	 * 
	 * @return The magnitude of this {@link Vector}.
	 */
	public float getMagnitude() {
		return (float) Math.sqrt(this.X * this.X + this.Y  * this.Y);
	}

	/**
	 * Performs addition of this {@link Vector} and the passed {@link Vector}, returning the result as a new {@link Vector} instance.
	 * 
	 * @param other - The {@link Vector} to add to {@code this}
	 * @return The result of adding {@code this} with {@code other}, returned as a new {@link Vector} instance.
	 */
	public Vector add(Vector other) {
		float sumX = (this.getX() + other.getX());
		float sumY = (this.getY() + other.getY());
		return new Vector(sumX, sumY);
	}
	
	/**
	 * Performs subtraction of this {@link Vector} and the passed {@link Vector}, returning the result as a new {@link Vector} instance.
	 * 
	 * @param other - The {@link Vector} to subtract from {@code this}
	 * @return The result of subtracting {@code other} from {@code this}, returned as a new {@link Vector} instance.
	 */
	public Vector subtract(Vector other) {
		float diffX = (this.getX() - other.getX());
		float diffY = (this.getY() - other.getY());
		return new Vector(diffX, diffY);
	}

	/**
	 * Performs the dot product operation on this {@link Vector} instance and the passed {@link Vector} instance, returning the result as a new {@link Vector} instance.
	 * 
	 * @param other - The {@link Vector} to perform the dot product operation with
	 * @return The result of the dot product between {@code this} and {@code other}, returned as a new {@link Vector} instance.
	 */
	public float dotProduct(Vector other) {
		return (this.getX() * this.getY()) + (other.getX() * other.getY());
	}

	/**
	 * Performs scalar multiplication on this {@link Vector} instance, returning the result as a new {@link Vector} instance.
	 * 
	 * @param scalar - The scalar value to multiply by
	 * @return The result of scalar multiplication of {@code this} and {@code scalar}, returned as a new {@link Vector} instance.
	 */
	public Vector scalarMultiply(float scalar) {
		float productX = (this.getX() * scalar);
		float productY = (this.getY() * scalar);
		return new Vector(productX, productY);
	}

	/**
	 * Returns the normalized unit-vector version of this {@link Vector}, as a new instance.
	 * 
	 * @return The unit vector of {@code this}, returned as a new {@link Vector} instance.
	 */
	public Vector normalize() {
		float magnitude = this.getMagnitude();
		float normalizedX = (this.getX() / magnitude);
		float nomralizedY = (this.getY() / magnitude);
		return new Vector(normalizedX, nomralizedY);
	}
	
	/* New Methods */
	
	/**
	 * Converts this {@link Vector} into a readable {@link String} in the format "(X, Y)".
	 */
	@Override
	public String toString() {
		return String.format("(%s, %s)", String.valueOf(this.getX()), String.valueOf(this.getY()));
	}
	
	/**
	 * Returns a new copy of this {@link Vector} instance, with the same X and Y values.
	 * 
	 * @return A copy of this {@link Vector} instance.
	 */
	public Vector copy() {
		return new Vector(this.getX(), this.getY());
	}
	
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
	 * Performs the cross product of this instance and {@code passedVector}, returning the result as a new {@link Vector}.
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
	
	/**
	 * Performs a rotation of this {@link Vector} about the origin, returning the result as a new {@link Vector}.
	 * 
	 * @param passedAngle - The angle to rotate by
	 * @return A new {@link Vector} instance, the result of rotating {@code this} about the origin {@code (0, 0)}.
	 */
	public Vector rotate(float passedAngle) {
		float sin = (float) Math.sin(passedAngle);
		float cos = (float) Math.cos(passedAngle);
		float xValue = (cos * this.getX()) - (sin * this.getY());
		float yValue = (cos * this.getY()) + (sin * this.getX());
		return new Vector(xValue, yValue);
	}
	
	/**
	 * Performs a rotation of this {@link Vector} about an arbitrary point, interpreted from the passed {@link Vector}, returning the result as a new {@link Vector}.
	 * 
	 * @param passedVector - The point to rotate around
	 * @param passedAngle - The angle to rotate by
	 * @return A new {@link Vector} instance, the result of rotating {@code this} about {@code passedVector} by the angle {@code passedAngle}.
	 */
	public Vector rotateAround(Vector passedVector, float passedAngle) {
		return this.subtract(passedVector).rotate(passedAngle).add(passedVector);
	}

}
