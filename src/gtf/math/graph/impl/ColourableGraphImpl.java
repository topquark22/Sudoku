package gtf.math.graph.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import gtf.math.graph.ColourConstraint;
import gtf.math.graph.ColourConstraintViolation;
import gtf.math.graph.ColourableGraph;
import gtf.math.graph.GraphTopology;
import gtf.math.graph.VacuousColourConstraint;
import gtf.math.types.ColourSpace;
import gtf.math.types.Copyable;


/**
 * A graph whose nodes may be coloured with different values
 * from a colour space. They may also be constrained by a
 * specified constraint.
 * 
 * @author gtf
 *
 * @param <T> The type used for the node addresses
 * @param <V> The type used for the node values
 */
public class ColourableGraphImpl<T, V>
    implements ColourableGraph<T, V>, Copyable<ColourableGraph<T, V>> {
  
  private final GraphTopology<T> topology;
  
  private final Map<T, V> colours;
  
  private final ColourSpace<V> colourSpace;
  
  private final Collection<V> allColours;
  
  private final ColourConstraint<T, V> constraint;
  
  private final Map<T, Collection<V>> possibleColoursCache;
  
  private final Map<T, Collection<V>> neighbourColoursCache;
  
  private final boolean checkConstraintOnSetColour;
  
  /**
   * Constructor for a colourable graph with no constraint.
   * 
   * @param topology topology the topology of the underlying graph
   * @param colourSpace the colour space that defines the possible values for each node
   */
  public ColourableGraphImpl(GraphTopology<T> topology, ColourSpace<V> colourSpace) {
    this(topology, colourSpace, new VacuousColourConstraint<T, V>(), false);
  }
  
  /**
   * Constructor for a colourable graph with a constraint.
   * 
   * @param topology the topology of the underlying graph
   * @param colourSpace the colour space that defines the possible values for each node
   * @param constraint the constraint on the graph colouring. It will be enforced when calling setColour().
   */
  public ColourableGraphImpl(GraphTopology<T> topology, ColourSpace<V> colourSpace, ColourConstraint<T, V> constraint) {
    this(topology, colourSpace, constraint, true);
  }
  
  /**
   * Constructor for a colourable graph.
   * 
   * @param topology the topology of the underlying graph
   * @param colourSpace the colour space that defines the possible values for each node
   * @param constraint the constraint on the graph colouring
   * @param checkConstraintOnSetColour whether or not to enforce the constraint when calling setColour().
   * If false, it is up to the caller to ensure that the colour is acceptable beforehand.
   */
  public ColourableGraphImpl(GraphTopology<T> topology, ColourSpace<V> colourSpace, ColourConstraint<T, V> constraint, boolean checkConstraintOnSetColour) {
    this.topology = topology;
    this.colourSpace = colourSpace;
    this.constraint = constraint;
    this.checkConstraintOnSetColour = checkConstraintOnSetColour;
    colours = new HashMap<T, V>(topology.getNodeCount());
    allColours = colourSpace.getColours();
    possibleColoursCache = new HashMap<T, Collection<V>>(topology.getNodeCount());
    neighbourColoursCache = new HashMap<T, Collection<V>>(topology.getNodeCount());
  }
  
  /**
   * Sets the colour of a node of the graph.
   * 
   * Checking the constraint here may be disabled for performance
   * reasons. If checkConstraintOnSetColour is false, it is up to the
   * caller to ensure that the value falls within the possible values
   * of this node. Otherwise the instance state may be inconsistent
   * with its constraint.
   * 
   * @param nodeAddress
   * @param colour
   * @throws ColourConstraintViolation
   */
  public void setColour(T nodeAddress, V colour) {
    boolean ok = !checkConstraintOnSetColour || constraint.check(this, nodeAddress, colour);
    if (ok) {
      colours.put(nodeAddress, colour);
      possibleColoursCache.clear();
      neighbourColoursCache.clear();
    } else {
      throw new ColourConstraintViolation(this, nodeAddress, colour);
    }
  }
  
  /**
   * Returns the set of distinct colours of the neighbours of this
   * node. Null (uncoloured) is not included as a value.
   * 
   * @param nodeAddress
   * @return
   */
  public Collection<V> getNeighbourColours(T nodeAddress) {
    Collection<V> result = neighbourColoursCache.get(nodeAddress);
    if (result == null) {
      result = new HashSet<V>(colourSpace.getNumberOfColours());
      for (T neighbour : topology.getNeighbours(nodeAddress)) {
        V colour = getColour(neighbour);
        if (colour != null) {
          result.add(colour);
        }
      }
      neighbourColoursCache.put(nodeAddress, result);
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * @see gtf.math.graph.ColourableGraph#getColour(java.lang.Object)
   */
  public V getColour(T nodeAddress) {
    return colours.get(nodeAddress);
  }
  
  /*
   * (non-Javadoc)
   * @see gtf.math.graph.ColourableGraph#isComplete()
   */
  public boolean isComplete() {
    return colours.size() == topology.getNodeCount();
  }
  
  /*
   * (non-Javadoc)
   * @see gtf.math.graph.ColourableGraph#getPossibleColoursForNode(java.lang.Object)
   */
  public Collection<V> getPossibleColoursForNode(T node) {
    Collection<V> result = possibleColoursCache.get(node);
    if (result == null) {
      result = new ArrayList<V>(colours.size());
      for (V colour : allColours) {
        if (constraint.check(this, node, colour)) {
          result.add(colour);
        }
      }
      possibleColoursCache.put(node, result);
    }
    return result;
  }
  /*
   * (non-Javadoc)
   * @see gtf.math.types.Copyable#copyOf()
   */
  public ColourableGraph<T, V> copyOf() {
    ColourableGraphImpl<T, V> copy =
          new ColourableGraphImpl<T, V>(topology, colourSpace, constraint, checkConstraintOnSetColour);
    for (T node : topology.getAllNodes()) {
      V colour = getColour(node);
      if (colour != null) {
        copy.colours.put(node, colour);
      }
    }
    return copy;
  }
  
  /*
   * (non-Javadoc)
   * @see gtf.math.graph.ColourableGraph#getTopology()
   */
  public GraphTopology<T> getTopology() {
    return topology;
  }
}
