package gtf.puzzle.engine;

import gtf.math.graph.ColourableGraph;

import java.util.EventObject;


/**
 * Encapsulates all available data about any event that occurred
 * in the Solver.
 * 
 * @author gtf
 *
 * @param <T>
 * @param <V>
 */
public class SolverEvent<T, V> extends EventObject {

  private final ColourableGraph<T, V> puzzle;
  private final int recursionDepth;
  private final int steps;
  private final T node;
  private final V value;
  

  SolverEvent(Object solver, ColourableGraph<T, V> puzzle, int recursionDepth, int steps) {
    this(solver, puzzle, recursionDepth, steps, null, null);
  }
  
  SolverEvent(Object solver, ColourableGraph<T, V> puzzle, int recursionDepth,
      int steps, T node, V value) {
    super(solver);
    this.puzzle = puzzle;
    this.recursionDepth = recursionDepth;
    this.steps = steps;
    this.node = node;
    this.value = value;
  }

  public ColourableGraph<T, V> getPuzzle() {
    return puzzle;
  }

  public int getRecursionDepth() {
    return recursionDepth;
  }
  
  public int getSteps() {
    return steps;
  }

  public T getNode() {
    return node;
  }
  
  public V getValue() {
    return value;
  }
  
  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append("[SolverEvent::depth=");
    buf.append(Integer.toString(getRecursionDepth()));
    buf.append(",puzzle=\n");
    buf.append(puzzle.toString());
    if (node != null) {
      buf.append(",node=\n");
      buf.append(node.toString());
    }
    if (value != null) {
      buf.append(",value=\n");
      buf.append(value.toString());
    }
    buf.append("]");
    return buf.toString();
  }
}

