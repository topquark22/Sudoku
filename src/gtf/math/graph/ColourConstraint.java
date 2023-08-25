package gtf.math.graph;


/**
 * Expresses a constraint on the colour assignments on a
 * colourable graph.
 * 
 * @author gtf
 * 
 * @param <T> the type used for the node addresses
 * @param <V> the type used for the node values
 */
public interface ColourConstraint<T, V> {
  
  /**
   * Check if the constraint would be satisfied for a given node
   * and colour.
   * 
   * @param graph the graph
   * @param node the node address
   * @param colour the value to assign (may be null)
   * @return true if the constraint would allow the colour to be
   * assigned to the node; false otherwise.
   */
  boolean check(ColourableGraph<T, V> graph, T node, V colour);
}
