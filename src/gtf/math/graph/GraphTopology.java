package gtf.math.graph;

import java.util.Collection;

/**
 * Defines the topology of a graph (directed or undirected).
 * 
 * It is parametrized by a type T, used as the type for a
 * node address. Objects of type T are "keys" that refer
 * to the location of a particular node within the graph.
 * Any class of objects may be used for this.
 * 
 * @author falkgeof
 *
 * @param <T> the type used for the node addresses.
 */
public interface GraphTopology<T> {

  Collection<T> getAllNodes();

  Collection<T> getNeighbours(T nodeAddr);

  int getNodeCount();
  
}
