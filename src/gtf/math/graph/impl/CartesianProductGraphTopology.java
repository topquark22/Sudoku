package gtf.math.graph.impl;

import gtf.math.graph.GraphTopology;
import gtf.math.types.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * A graph topology that is the cartesian product of two existing graph topologies.
 * 
 * @author gtf
 *
 * @param <T> The type used for the node addresses of graph 1
 * @param <U> The type used for the node addresses of graph 2
 */
public class CartesianProductGraphTopology<T, U> implements GraphTopology<Pair<T, U>> {

  private final GraphTopology<T> x;
  
  private final GraphTopology<U> y;
  
  private final int nodeCount;
  
  private final Map<Pair<T, U>, Collection<Pair<T, U>>> neighbours;
  
  public CartesianProductGraphTopology(GraphTopology<T> x, GraphTopology<U> y) {
    this.x = x;
    this.y = y;
    this.nodeCount = x.getNodeCount() * y.getNodeCount();
    neighbours = new HashMap<Pair<T, U>, Collection<Pair<T, U>>>(nodeCount);
    _populateNeighbours();
  }
  
  /**
   * Populate the neighbours map. Called from constructor.
   */
  private void _populateNeighbours() {
    for (T t : x.getAllNodes()) {
      Collection<T> tNeighbours = x.getNeighbours(t);
      for (U u : y.getAllNodes()) {
        Collection<U> uNeighbours = y.getNeighbours(u);
        Collection<Pair<T, U>> thisNodeNeighbours = new ArrayList<Pair<T, U>>(
            tNeighbours.size() + uNeighbours.size());
        for (T tn: tNeighbours) {
          thisNodeNeighbours.add(new Pair<T, U>(tn, u));
        }
        for (U un: uNeighbours) {
          thisNodeNeighbours.add(new Pair<T, U>(t, un));
        }
        Pair<T, U> thisNode = new Pair<T, U>(t, u);
        neighbours.put(thisNode, thisNodeNeighbours);
      }
    }
    
  }

  public Collection<Pair<T, U>> getAllNodes() {
    return neighbours.keySet();
  }

  public Collection<Pair<T, U>> getNeighbours(Pair<T, U> nodeAddr) {
    return Collections.unmodifiableCollection(neighbours.get(nodeAddr));
  }

  public int getNodeCount() {
    return nodeCount;
  }
}
