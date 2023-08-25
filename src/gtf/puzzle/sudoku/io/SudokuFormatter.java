package gtf.puzzle.sudoku.io;

import java.io.Reader;
import java.io.StringReader;

import gtf.math.graph.ColourableGraph;
import gtf.math.graph.io.ColourableGraphFormatter;

import gtf.puzzle.sudoku.graph.SudokuCellAddress;
import gtf.puzzle.sudoku.graph.SudokuTopology;


public class SudokuFormatter implements ColourableGraphFormatter<SudokuCellAddress, Integer> {

  private final SudokuTopology topology;
  
  private final String horizontalBar;

  public SudokuFormatter(SudokuTopology topology) {
    this.topology = topology;
    horizontalBar = createHorizontalBar(topology.getZoneWidth(), topology.getZoneHeight());
  }
  
  private String createHorizontalBar(int zoneWidth, int numberOfZones) {
    return "-----+-----+-----\n"; // FIXME
  }
  
  /*
   * (non-Javadoc)
   * @see gtf.math.graph.io.ColourableGraphFormatter#getReader(gtf.math.graph.ColourableGraph)
   */
  public Reader getReader(ColourableGraph<SudokuCellAddress, Integer> graph) {
    return new StringReader(asString(graph));
  }
  
  public String asString(ColourableGraph<SudokuCellAddress, Integer> graph) {
    final int width = topology.getWidth();
    final int hsize = topology.getZoneHeight();
    final int wsize = topology.getZoneWidth();
    StringBuffer sb = new StringBuffer();
    for (int row = 0; row < width; row++) {
      for (int col = 0; col < width; col++) {
        Integer value = graph.getColour(new SudokuCellAddress(width, row, col));
        sb.append(value == null ? " " : Integer.toString(value));
        if (col < width - 1) {
          sb.append((((col + 1) % wsize) == 0) ? "|" : " ");
        }
      }
      if (row < width - 1) {
        sb.append("\n");
        if ((row + 1) % hsize == 0) {
          sb.append(horizontalBar);
        }
      }
    }
    return sb.toString();
  }
}
