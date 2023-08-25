package gtf.puzzle.sudoku.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

import gtf.puzzle.sudoku.graph.SudokuBoard;


public class GameBoardReader {

  public SudokuBoard read(InputStream in) throws GameBoardReaderException, IOException {
    SudokuBoard board;
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    try {
      String line = reader.readLine();
      board = createBoardByDimensionSpec(line);
      int width = board.getTopology().getWidth();
      int row = 0;
      line = reader.readLine();
      while (row < width && line != null) {
        StringTokenizer tokenizer = new StringTokenizer(line);
        int col = 0;
        while (col < width && tokenizer.hasMoreTokens()) {
          String token = tokenizer.nextToken();
          try {
            int value = Integer.parseInt(token);
            if (value > 0) {
              board.setCell(row, col, value);
            }
          } catch (NumberFormatException e) {
            throw new GameBoardReaderException("Bad token", row, col, token);
          } catch (IllegalArgumentException e) {
            throw new GameBoardReaderException(e.getMessage(), row, col, token);
          }
          col++;
        }
        if (col < width) {
          throw new GameBoardReaderException("Not enough columns in row", row);
        }
        row++;
        line = reader.readLine();
      }
      if (row < width) {
        throw new GameBoardReaderException("Not enough rows", row);
      }
    } finally {
      reader.close();
    }
    return board;
  }

  private SudokuBoard createBoardByDimensionSpec(String line)
      throws GameBoardReaderException {
    if (line == null) {
      throw new GameBoardReaderException("Couldn't read side");
    }
    SudokuBoard board;
    StringTokenizer tokenizer = new StringTokenizer(line.trim());
    try {
      int countTokens = tokenizer.countTokens();
      if (countTokens == 1) {
        int size = Integer.parseInt(tokenizer.nextToken());
        board = new SudokuBoard(size);
      } else if (countTokens == 2) {
        int hsize = Integer.parseInt(tokenizer.nextToken());
        int wsize = Integer.parseInt(tokenizer.nextToken());
        board = new SudokuBoard(hsize, wsize);
      } else {
        throw new GameBoardReaderException("Illegal number of values in dimension spec");
      }
    } catch (NumberFormatException e) {
      throw new GameBoardReaderException("Bad number in dimension spec");
    } catch (IllegalArgumentException e) {
      // dimension out of range (thrown by SudokuGraph)
      throw new GameBoardReaderException(e.getMessage());
    }
    return board;
  }
}