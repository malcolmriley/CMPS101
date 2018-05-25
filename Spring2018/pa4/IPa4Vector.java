// Vector.java
// A class that implements the Vector ADT.
//
// For this assignment, you must complete this code skeleton.
// You may not change the function prototypes.
// You are expected to fill in the functions to make them work
// as expected, and you can add as much as you need or want.
// We recommend implementing the Vector ADT using x and y coordinates.

// Notes:
// Angles are always in radians, not degrees.

public interface IPa4Vector {

  /* Accessors */

  /** getX
   *  Returns the x coordinate of the Vector.
   */
  public float getX();

  /** getY
   *  Returns the y coordinate of the Vector.
   */
  public float getY();

  /** getAngle
   *  Returns the angle of the Vector.
   */
  public float getAngle();

  /** getMagnitude
   *  Returns the magnitude of the Vector.
   */
  public float getMagnitude();

  /** add
   *  Returns the sum of this Vector with the given Vector.
   */
  public Vector add(Vector other);

  /** subtract
   *  Returns the difference between this Vector and the given Vector.
   */
  public Vector subtract(Vector other);

  /** dotProduct
   *  Returns the dot product of this Vector and the given Vector.
   */
  public float dotProduct(Vector other);

  /** scalarMultiply
   *  Returns this Vector scaled by the given scalar.
   */
  public Vector scalarMultiply(float scalar);

  /** normalize
   *  Returns the normalized version of this Vector, a Vector with the same
   *    angle with magnitude 1.
   */
  public Vector normalize();
}
