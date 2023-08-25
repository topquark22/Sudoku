package gtf.puzzle.sudoku;

import gtf.puzzle.engine.SolverEvent;
import gtf.puzzle.engine.SolverListener;
import gtf.puzzle.sudoku.graph.SudokuCellAddress;
import gtf.puzzle.sudoku.io.SudokuFormatter;


class EventReporter implements SolverListener<SudokuCellAddress, Integer> {

  private final SudokuFormatter formatter;
  
  public EventReporter(SudokuFormatter formatter) {
    this.formatter = formatter;
  }
  
  public void solverInvoked(SolverEvent<SudokuCellAddress, Integer> event) {
    System.out.println("depth = " + Integer.toString(event.getRecursionDepth()));
  }

  public void solverTriedCell(SolverEvent<SudokuCellAddress, Integer> event) {
    System.out.println("TRIED CELL "+  event.getNode() + " -> " + (event.getValue()));
    System.out.println(formatter.asString(event.getPuzzle()));
  }

  public void solverFoundSolution(SolverEvent<SudokuCellAddress, Integer> event) {
    System.out.println("FOUND SOLUTION");
    System.out.println(formatter.asString(event.getPuzzle()));
  }

  public void solverNoSolution(SolverEvent<SudokuCellAddress, Integer> event) {
  }
}
