package gtf.puzzle.sudoku.graph;

import gtf.math.graph.ColourableGraph;
import gtf.math.graph.UnalikeNeighboursColourConstraint;
import gtf.math.graph.impl.ColourableGraphImpl;
import gtf.math.types.PaintByNumber;
import gtf.puzzle.sudoku.io.SudokuFormatter;


public class SudokuBoard {
  
  private final ColourableGraph<SudokuCellAddress, Integer> graph;
  
  private final SudokuFormatter formatter;
  
  /**
   * Constructor should only be used for the root board.
   * During solving, the copyOf() method is used to avoid
   * duplicating the graph topology setup.
   * 
   * @param size
   */
  public SudokuBoard(int size) {
    this(size, size);
  }

  /**
   * Constructor should only be used for the root board.
   * During solving, the copyOf() method is used to avoid
   * duplicating the graph topology setup.
   * 
   * @param size
   */
  public SudokuBoard(int hsize, int wsize) {
    SudokuTopology topology = new SudokuTopology(hsize, wsize);
    graph = new ColourableGraphImpl<SudokuCellAddress, Integer>(
        topology,
        new PaintByNumber(hsize * wsize),
        new UnalikeNeighboursColourConstraint<SudokuCellAddress, Integer>(),
        false);
    formatter = new SudokuFormatter(topology);
  }
  
  public String toString() {
    return formatter.asString(graph);
  }

  public SudokuTopology getTopology() {
    return (SudokuTopology) graph.getTopology();
  }
  
  /**
   * Sets the value of a cell by its row and column.
   * 
   * @param row
   * @param col
   * @param value
   */
  public void setCell(int row, int col, Integer value) {
    graph.setColour(new SudokuCellAddress(getTopology().getWidth(), row, col), value);   
  }

  /**
   * Gets the value of a cell by its row and column.
   * 
   * @param row
   * @param col
   * @return value
   */
  public Integer getValue(int row, int col) {
    return graph.getColour(new SudokuCellAddress(getTopology().getWidth(), row, col));   
  }

  public ColourableGraph<SudokuCellAddress, Integer> getGraph() {
    return graph;
  }
}
