package gtf.puzzle.engine;

import java.util.Collection;
import java.util.SortedMap;


/**
 * Adds functionality to let a graph be used in a
 * backtracking algorithm.
 * 
 * Care must be taken if the underlying graph is mutable.
 * Changing the colours of any nodes of the underlying
 * graph may break the backtracker. In this case
 * you should create a new backtracker for the changed
 * graph, or else use a mutable-aware implementation.
 * 
 * @author gtf
 *
 * @param <T> The type used for the node addresses
 * @param <V> The type used for the node values
 */
interface Backtrackable<T, V> {

  /**
   * Gets all of the uncoloured nodes, sorted in order
   * of the fewest to most possible values.
   * 
   * @return a sorted map in which the keys, in order,
   * are the node addresses of the unvalued nodes; and
   * the values are the set of possible values for each
   * node.
   */
  SortedMap<T, Collection<V>> getUnvaluedNodesSorted();
}