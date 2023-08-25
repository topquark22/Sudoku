package gtf.math.algebra;


/**
 * Utility class for operations on integers mod n.
 * @author gtf
 *
 */
public final class ModularArithmetic {

  private ModularArithmetic() {
  }
  
  /**
   * Return the remainder of integer division. The result will
   * have the same sign as the divisor b.
   * 
   * Necessitated by the sign conventions of the Java
   * operator %, which doesn't do the right thing for negative
   * numbers.
   * 
   * @param a The dividend
   * @param b The divisor
   * @return the remainder, in the range 0..a. The result will
   * have the same sign as the divisor b.
   * @throws ArithmeticException if b == 0
   */
  public static int remainder(int a, int b) {
    int result = a % b;
    if ((result < 0 && b > 0) || (result > 0 && b < 0)) {
      result += b;
    }
    return result;
  }
}
