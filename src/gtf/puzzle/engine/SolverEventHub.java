package gtf.puzzle.engine;

import gtf.math.graph.ColourableGraph;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Delegate for managing SolverListeners.
 * 
 * @author gtf
 *
 * @param T the type used for the node addresses
 * @param V the type used for the node values
 */
class SolverEventHub<T, V> {

  private final Solver<T, V> eventSource;
  
  private final Collection<SolverListener<T, V>> listeners;

  SolverEventHub(Solver<T, V> solver) {
    eventSource = solver;
    listeners = new ArrayList<SolverListener<T, V>>();
  }
  
  void addListener(SolverListener<T, V> listener) {
    listeners.add(listener);
  }
  
  void removeListener(SolverListener<T, V> listener) {
    listeners.remove(listener);
  }
  
  void fireInvoked(ColourableGraph<T, V> puzzle, int recursionDepth, int steps) {
    for (SolverListener<T, V> listener : listeners) {
      listener.solverInvoked(new SolverEvent<T, V>(eventSource, puzzle, recursionDepth, steps));
    }
  }

  void fireFoundSolution(ColourableGraph<T, V> puzzle, int recursionDepth, int steps) {
    for (SolverListener<T, V> listener : listeners) {
      listener.solverFoundSolution(new SolverEvent<T, V>(eventSource, puzzle, recursionDepth, steps));
    }
  }
  
  void fireNoSolution(ColourableGraph<T, V> puzzle, int recursionDepth, int steps) {
    for (SolverListener<T, V> listener : listeners) {
      listener.solverNoSolution(new SolverEvent<T, V>(eventSource, puzzle, recursionDepth, steps));
    }
  }

  void fireTriedCell(ColourableGraph<T, V> puzzle, int recursionDepth, int steps,
      T node, V value) {
    for (SolverListener<T, V> listener : listeners) {
      listener.solverTriedCell(new SolverEvent<T, V>(eventSource, puzzle, recursionDepth, steps, node, value));
    }
  }
}
