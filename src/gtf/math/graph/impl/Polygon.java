package gtf.math.graph.impl;

import gtf.math.algebra.ModularArithmetic;
import gtf.math.graph.GraphTopology;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * Sample graph topology representing an n-sided polygon.
 * 
 * @author gtf
 */
public class Polygon implements GraphTopology<Integer> {

  private final int size;
  
  private final List<Integer> nodes;
  
  /**
   * Constructs a polygon with a given number of vertices.
   * 
   * @param size the number of vertices.
   */
  public Polygon(int size) {
    if (size < 0) {
      throw new IllegalArgumentException("bad size " + size);
    }
    this.size = size;
    nodes = new ArrayList<Integer>(size);
    for (int i = 0; i < size; i++) {
      nodes.set(i, new Integer(i));
    }
  }
  
  /*
   * (non-Javadoc)
   * @see gtf.math.graph.GraphTopology#getAllNodes()
   */
  public Collection<Integer> getAllNodes() {
    return Collections.unmodifiableList(nodes);
  }

  /**
   * Get neighbours of a node. All calculations done mod n.
   */
  public Collection<Integer> getNeighbours(Integer nodeAddr) {
    int i = ModularArithmetic.remainder(nodeAddr.intValue() - 1, size);
    int j = ModularArithmetic.remainder(nodeAddr.intValue() + 1, size);
    Collection<Integer> result = new ArrayList<Integer>(2);
    result.add(new Integer(i));
    result.add(new Integer(j));
    return result;
  }

  /*
   * (non-Javadoc)
   * @see gtf.math.graph.GraphTopology#getNodeCount()
   */
  public int getNodeCount() {
    return size;
  }
}
