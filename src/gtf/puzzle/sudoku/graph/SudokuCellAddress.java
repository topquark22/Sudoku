package gtf.puzzle.sudoku.graph;


/**
 * Expresses the location of a cell within the Sudoku grid.
 * 
 * @author gtf
 */
public class SudokuCellAddress {

  private final int width;
  private final int row;
  private final int col;

  /**
   * 
   * @param width width of the game board (= wsize * hsize)
   * @param row the row number
   * @param col the column number
   */
  public SudokuCellAddress(int width, int row, int col) {
    if (width <= 0 || row < 0 || col < 0 || row >= width || col >= width) {
      throw new IllegalArgumentException("[width=" + width + ",row=" + row + ",col=" + col + "]");
    }
    this.width=width;
    this.row = row;
    this.col = col;
  }

  int getRow() {
    return row;
  }

  int getColumn() {
    return col;
  }

  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append("[row=");
    buf.append(Integer.toString(row));
    buf.append(", col=");
    buf.append(Integer.toString(col));
    buf.append("]");
    return buf.toString();
  }

  public boolean equals(Object o) {
    if (!(o instanceof SudokuCellAddress)) {
      return false;
    }
    SudokuCellAddress s = (SudokuCellAddress) o;
    return (width == s.width
        && row == s.row && col == s.col);
  }

  public int hashCode() {
    return row * width + col;
  }
}
