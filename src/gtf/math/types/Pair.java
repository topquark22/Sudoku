package gtf.math.types;


/**
 * An ordered pair.
 */
public class Pair<T, U> {

  private final T x;
  private final U y;
  
  public Pair(T x, U y) {
    this.x = x;
    this.y = y;
  }
  
  public T getX() {
    return x;
  }
  
  public U getY() {
    return y;
  }
  
  public int hashCode() {
    return x.hashCode() ^ y.hashCode();
  }
  
  public boolean equals(Object o) {
    if (o == null || !(o instanceof Pair)) {
      return false;
    }
    Pair p = (Pair) o;
    return (x == p.x && y == p.y);
  }
}
