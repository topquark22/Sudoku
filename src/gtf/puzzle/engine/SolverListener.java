package gtf.puzzle.engine;

import java.util.EventListener;


/**
 * Represents something that is interested in receiving
 * events from the backtracking solver.
 * 
 * @author gtf
 *
 * @param T the type used for the node addresses
 * @param V the type used for the node values
 */
public interface SolverListener<T, V> extends EventListener {

  void solverInvoked(SolverEvent<T, V> event);

  void solverTriedCell(SolverEvent<T, V> event);

  void solverFoundSolution(SolverEvent<T, V> event);

  void solverNoSolution(SolverEvent<T, V> event);
}

