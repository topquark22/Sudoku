package gtf.puzzle.sudoku.io;

public class GameBoardReaderException extends Exception {

  private static final int VALUE_NOT_INITIALIZED = -1;

  private int row = VALUE_NOT_INITIALIZED;
  private int col = VALUE_NOT_INITIALIZED;
  private String token;

  public GameBoardReaderException(String message) {
    super(message);
  }

  public GameBoardReaderException(String message, int row) {
    this(message);
    this.row = row;
  }

  public GameBoardReaderException(String message, int row, int col) {
    this(message, row);
    this.col = col;
  }

  public GameBoardReaderException(String message, int row, int col, String token) {
    this(message, row, col);
    this.token = token;
  }

  public String getMessage() {
    class Appender {
      StringBuffer buf = new StringBuffer();

      void append(String message) {
        buf.append(message);
      }

      void append(String varName, Object varValue) {
        append("[");
        append(varName);
        append("=");
        append(varValue == null ? null : varValue.toString());
        append("] ");
      }

      public String toString() {
        return buf.toString();
      }
    };
    Appender appender = new Appender();
    appender.append(super.getMessage());
    appender.append(": ");
    if (row != VALUE_NOT_INITIALIZED) {
      appender.append("row", row);
    }
    if (col != VALUE_NOT_INITIALIZED) {
      appender.append("col", col);
    }
    if (token != null) {
      appender.append("token", token);
    }
    return appender.toString();
  }
}
