package gtf.math.types;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * A colour space populated by the integers 1..range.
 * 
 * @author gtf
 */
public final class PaintByNumber implements ColourSpace<Integer> {
  
  private final int range;
  
  private final List<Integer> values;
  
  public PaintByNumber(int range) {
    if (range < 1) {
      throw new IllegalArgumentException("bad range " + range);
    }
    this.range = range;
    values = new ArrayList<Integer>(range);
    for (int i = 1; i <= range; i++) {
      values.add(new Integer(i));
    }
  }
  
  /*
   * (non-Javadoc)
   * @see gtf.math.graph.ColourSpace#getNumberOfColours()
   */
  public int getNumberOfColours() {
    return range;
  }
  
  /*
   * (non-Javadoc)
   * @see gtf.math.graph.ColourSpace#getColours()
   */
  public Collection<Integer> getColours() {
    return Collections.unmodifiableList(values);
  }
  
}
