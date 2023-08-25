package gtf.puzzle.engine;

import gtf.math.graph.ColourableGraph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;


/**
 * Implementation of a backtracking algorithm to solve
 * graph-colouring and related problems.
 * 
 * Algorithm:
 * <ol>
 * <li>Check to see if the board is complete. If it is,
 * return it as the single unique solution.</li>
 * 
 * <li>Check to see if the board has any unsatisfiable
 * nodes. If so, return an empty set (there is
 * no solution).</li>
 *
 * <li>Find an empty square on the board with the
 * fewest number of allowed values.</li>
 *
 * <li>Get the set of allowed values for that node.</li>
 *
 * <li>For each allowed value:
 *    <ol>
 *    <li>Create a separate copy of the board<li>
 *    
 *    <li>Fill the allowed value into the blank square
 *    as a guess</li>
 *
 *    <li>Recursively invoke solve() on the new board to
 *   determine if the guess leads to solution(s).</li>
 *
 *   <li>Add any returned solutions to the result set.</li>
 *   </ol>
 *   </li>
 *   
 * <li>Return the result set. Note: If there were no
 * allowed values in step 4, then the result
 * set on this branch is empty (no solution).</li>
 * </ol>
 * 
 * @param T the type used for the node addresses
 * @param V the type used for the node values
 */
public class Solver<T, V> {

  private int steps = 0;

  private final SolverEventHub<T, V> eventHub;

  public Solver() {
    eventHub = new SolverEventHub<T, V>(this);
  }

  public void addListener(SolverListener<T, V> listener) {
    eventHub.addListener(listener);
  }
  
  public void removeListener(SolverListener<T, V> listener) {
    eventHub.removeListener(listener);
  }
  
  /**
   * Solve a colourable graph.
   * 
   * For better performance, the graph instance should not check its
   * constraint when its setColour method is called. The solver will
   * always check the constraint first before setting the colour.
   * 
   * @param puzzle the puzzle to solve
   * @return set of solutions
   */
  public Set<ColourableGraph<T, V>> solve(final ColourableGraph<T, V> puzzle) {
    return solve(puzzle, 0);
  }

  private Set<ColourableGraph<T, V>>
      solve(final ColourableGraph<T, V> puzzle, final int recursionDepth) {
    eventHub.fireInvoked(puzzle, recursionDepth, steps);
    Set<ColourableGraph<T, V>> result = new HashSet<ColourableGraph<T, V>>();
    if (puzzle.isComplete()) {
      eventHub.fireFoundSolution(puzzle, recursionDepth, steps);
      result.add(puzzle);
      return result;
    }
    Backtrackable<T, V> backtrackable = new BacktrackableImpl<T, V>(puzzle);
    SortedMap<T, Collection<V>> squares = backtrackable.getUnvaluedNodesSorted();
    if (squares.isEmpty()) {
      eventHub.fireNoSolution(puzzle, recursionDepth, steps);
      return result; // empty set
    }
    T bestSquare = squares.firstKey();
    Collection<V> allowedValues = squares.get(bestSquare);
    for (V value : allowedValues) {
      ColourableGraph<T, V> next = puzzle.copyOf();
      next.setColour(bestSquare, value);
      eventHub.fireTriedCell(next, recursionDepth, steps, bestSquare, value);
      Set<ColourableGraph<T, V>> nextResult = solve(next, recursionDepth + 1);
      result.addAll(nextResult);
    }
    steps++;
    return result;
  }

  /**
   * @return the total number of steps taken to solve the puzzle.
   */
  public int getSteps() {
    return steps;
  }
}
