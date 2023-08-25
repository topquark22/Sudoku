package gtf.math.graph;

import java.util.Collection;


/**
 * The constraint that expresses that a node may not
 * have the same colour as any of its neighbours.
 * 
 * For performance reasons, it is advised to implement
 * caching in the getNeighbourColours() method of the
 * underlying graph. The backtracker makes intensive
 * use of the constraint check in an inner loop, and an
 * inefficient implementation will impair performance.
 * 
 * @author gtf
 *
 * @param <T> The type used for the node addresses
 * @param <V> The type used for the node values
 */
public final class UnalikeNeighboursColourConstraint<T, V>
    implements ColourConstraint<T, V> {

  /*
   * (non-Javadoc)
   * @see gtf.math.graph.ColourConstraint#check(gtf.math.graph.ColourableGraph, java.lang.Object, java.lang.Object)
   */
  public boolean check(ColourableGraph<T, V> graph, T node, V colour) {
    if (colour == null) {
      return true;
    }
    Collection<V> neighbourColours = graph.getNeighbourColours(node);
    for (V neighbourColour : neighbourColours) {
      if (colour.equals(neighbourColour)) {
        return false;
      }
    }
    return true;
  }
}
