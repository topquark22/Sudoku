package gtf.puzzle.sudoku.io;

import gtf.puzzle.sudoku.graph.SudokuBoard;

import java.io.OutputStream;
import java.io.PrintWriter;


public class GameBoardWriter {

  private PrintWriter out;

  public GameBoardWriter(OutputStream out) {
    this.out = new PrintWriter(out);
  }

  public void write(SudokuBoard board) {
    int hsize = board.getTopology().getZoneHeight();
    int wsize = board.getTopology().getZoneWidth();
    int width = board.getTopology().getWidth();
    try {
      out.println(hsize + " " + wsize);
      for (int row = 0; row < width; row++) {
        for (int col = 0; col < width; col++) {
          out.print(getPrintableValue(board, row, col));
          if (col < width - 1) {
            out.print(" ");
          }
        }
        out.println();
      }
    } finally {
      out.close();
    }
  }

  private String getPrintableValue(SudokuBoard board, int row, int col) {
    Integer value = board.getValue(row, col);
    if (value == null) {
      return "0";
    }
    return String.valueOf(value.intValue());
  }
}
