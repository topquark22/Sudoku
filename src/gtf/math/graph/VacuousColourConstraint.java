package gtf.math.graph;


/**
 * The constraint that expresses no constraints.
 * 
 * @author gtf
 *
 * @param <T> The type used for the node addresses
 * @param <V> The type used for the node values
 */
public final class VacuousColourConstraint<T, V>
  implements ColourConstraint<T, V> {

  /*
   * (non-Javadoc)
   * @see gtf.math.graph.ColourConstraint#check(gtf.math.graph.ColourableGraph, java.lang.Object, java.lang.Object)
   */
  public boolean check(ColourableGraph<T, V> graph, T node, V colour) {
    return true;
  }
}
