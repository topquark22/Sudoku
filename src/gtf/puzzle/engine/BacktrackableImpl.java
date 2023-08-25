package gtf.puzzle.engine;

import gtf.math.graph.ColourableGraph;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * A decoration of a colourable graph that enables it
 * to be used in a backtracking algorithm.
 * 
 * For performance reasons, it is advised to implement
 * caching in the getPossibleColoursForNode() method
 * of the underlying graph. The backtracker makes intensive
 * use of that method in an inner loop, and an inefficient
 * implementation will impair performance.
 * 
 * @author gtf
 *
 * @param <T> The type used for the node addresses
 * @param <V> The type used for the node values
 */
class BacktrackableImpl<T, V> implements Backtrackable<T, V> {
  
  protected final ColourableGraph<T, V> graph;
  
  /**
   * An arbitrary ordering imposed on the nodes. It is
   * needed to maintain the consistency of the TreeMap.
   */
  private final Map<T, Integer> fakeOrdering;
  
  public BacktrackableImpl(ColourableGraph<T, V> graph) {
    this.graph = graph;
    int nodeCount = graph.getTopology().getNodeCount();
    fakeOrdering = new HashMap<T, Integer>(nodeCount);
    _computeFakeOrdering();
  }

  /**
   * Compute the fake ordering. Called from constructor.
   * 
   * TODO find a way to avoid recomputing the fake
   * ordering for every new instance. It only depends on
   * the collection of underlying nodes, which doesn't
   * change.
   */
  private void _computeFakeOrdering() {
    int i = 0;
    for (T node : graph.getTopology().getAllNodes()) {
      fakeOrdering.put(node, i++);
    }
  }

  /*
   * (non-Javadoc)
   * @see gtf.puzzle.engine.Backtrackable#getUncolouredNodesSorted()
   */
  public SortedMap<T, Collection<V>> getUnvaluedNodesSorted() {
    SortedMap<T, Collection<V>> result = new TreeMap<T, Collection<V>>(
        new Comparator<T>() {
          public int compare(T x, T y) {
            // Compare first on number of possibilities. If they
            // have the same number, we can't return 0 because the
            // TreeMap will consider them equal. Otherwise we
            // use the fake ordering.
            int xPossibilities = graph.getPossibleColoursForNode(x).size();
            int yPossibilities = graph.getPossibleColoursForNode(y).size();
            if (xPossibilities != yPossibilities) {
              return xPossibilities - yPossibilities;
            }
            return fakeOrdering.get(x) - fakeOrdering.get(y);
          }
        }
    );
    for (T node : graph.getTopology().getAllNodes()) {
      if (null == graph.getColour(node)) {
        Collection<V> possibleColours = graph.getPossibleColoursForNode(node);
        if (!(possibleColours.isEmpty())) {
          result.put(node, possibleColours);
        }
      }
    }
    return result;
  }
}
