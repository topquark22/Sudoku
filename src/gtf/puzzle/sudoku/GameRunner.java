package gtf.puzzle.sudoku;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import gtf.math.graph.ColourableGraph;

import gtf.puzzle.engine.Solver;

import gtf.puzzle.sudoku.graph.SudokuBoard;
import gtf.puzzle.sudoku.graph.SudokuCellAddress;
import gtf.puzzle.sudoku.io.GameBoardReader;
import gtf.puzzle.sudoku.io.GameBoardReaderException;
import gtf.puzzle.sudoku.io.SudokuFormatter;


public class GameRunner {

  private final Args arguments;

  private static class Args {
    private final File inputFile;
    private final boolean verbose;
    private Args(File inputFile, boolean verbose) {
      this.inputFile = inputFile;
      this.verbose = verbose;
    }
    private File getInputFile() {
      return inputFile;
    }
    private boolean isVerbose() {
      return verbose;
    }
  }

  private final static String OPT_VERBOSE = "v";

  private static Args parseOpts(String[] args) throws ParseException {
    Options opts = new Options();
    opts.addOption(new Option(OPT_VERBOSE, false, "give verbose output"));
    CommandLineParser parser = new GnuParser();
    CommandLine cl = null;
    cl = parser.parse(opts, args, false);
    boolean verbose = cl.hasOption(OPT_VERBOSE);
    String[] args2 = cl.getArgs();
    if (args2.length != 1) {
      throw new ParseException("Must provide a filename");
    }
    File inFile = new File(args2[0]);
    return new Args(inFile, verbose);
  }

  public static void main(String[] args) throws ParseException {
    Args a = parseOpts(args);
    File file = a.getInputFile();
    try {
      new GameRunner(a).execute();
    } catch (IOException e) {
      System.err.println("Could not open board data file " + file);
      System.exit(1);
    } catch (GameBoardReaderException e) {
      System.err.println("Could not read board data file.");
      System.err.println(e.getMessage());
      System.exit(1);
    }
  }

  public GameRunner(Args arguments) {
    this.arguments = arguments;
  }

  private void execute() throws GameBoardReaderException, IOException {
    GameBoardReader reader = new GameBoardReader();
    InputStream in = new FileInputStream(arguments.getInputFile());
    SudokuBoard board = reader.read(in);
    SudokuFormatter formatter = new SudokuFormatter(board.getTopology());
    System.out.println("Game board:");
    System.out.println(board.toString());
    System.out.println();
    Solver<SudokuCellAddress, Integer> solver =
        new Solver<SudokuCellAddress, Integer>();
    if (arguments.isVerbose()) {
      solver.addListener(new EventReporter(formatter));
    }
    Set<ColourableGraph<SudokuCellAddress, Integer>> results = solver.solve(board.getGraph());
    System.out.println(Integer.toString(results.size()) + " solutions");
    System.out.println("(solved in " + solver.getSteps() + " steps)");
    for (ColourableGraph<SudokuCellAddress, Integer> result : results) {
      System.out.println();
      System.out.println(formatter.asString(result));
    }
  }
}
