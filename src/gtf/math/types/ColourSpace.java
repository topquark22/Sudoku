package gtf.math.types;

import java.util.Collection;


/**
 * 
 * @author gtf
 *
 * @param <V> the type used for the node values
 */
public interface ColourSpace<V> {
  
  Collection<V> getColours();
  
  int getNumberOfColours();
}
