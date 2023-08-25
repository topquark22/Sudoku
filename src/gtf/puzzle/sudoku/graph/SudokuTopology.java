package gtf.puzzle.sudoku.graph;

import gtf.math.graph.GraphTopology;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Represents the topology of the Sudoku board. Has no knowledge of
 * node values. One instance of the topology can be shared by all
 * SudokuBoard instances.
 * 
 * @author falkgeof
 */
public class SudokuTopology implements GraphTopology<SudokuCellAddress> {
  
  /**
   * The "size" of the zones, which defines the size of the board.
   * hsize = height of a zone, wsize = width of a zone.
   * Can be any whole number >= 1.
   * A standard puzzle has hsize 3, wsize 3.
   * The playing area is hsize * wsize on a side. There are
   * hsize^2 * wsize^2 cells in the entire board.
   */
  private final int hsize, wsize;
  
  private final SudokuCellAddress[][] cells;
  
  private final Collection<SudokuCellAddress> allNodes;
  
  private final Map<SudokuCellAddress, Collection<SudokuCellAddress>> neighbours;
  
  /**
   * Constructor for square zones (standard).
   * 
   * @param size the size of a zone
   */
  public SudokuTopology(int size) {
    this(size, size);
  }
  
  /**
   * Constructor for square or rectangular zones.
   * 
   * @param hsize the height of a zone
   * @param wsize the width of a zone
   */
  public SudokuTopology(int hsize, int wsize) {
    if (wsize <= 0) {
      throw new IllegalArgumentException("bad zone width " + wsize);
    }
    if (hsize <= 0) {
      throw new IllegalArgumentException("bad zone height " + hsize);
    }
    this.hsize = hsize;
    this.wsize = wsize;
    cells = new SudokuCellAddress[getWidth()][getWidth()];
    allNodes = new ArrayList<SudokuCellAddress>(getNodeCount());
    neighbours = new HashMap<SudokuCellAddress, Collection<SudokuCellAddress>>(getNodeCount());
    for (int row = 0; row < getWidth(); row++) {
      for (int col = 0; col < getWidth(); col++) {
        SudokuCellAddress addr = new SudokuCellAddress(getWidth(), row, col);
        cells[row][col] = addr;
        allNodes.add(addr);
        neighbours.put(addr, new ArrayList<SudokuCellAddress>(getNodeCount()));
      }
    }
    for (int row = 0; row < getWidth(); row++) {
      for (int col = 0; col < getWidth(); col++) {
        SudokuCellAddress node = cells[row][col];
        // add this cell as a neighbour to others in its column
        for (int i = 0; i < getWidth(); i++) {
          if (i != row) {
            neighbours.get(cells[i][col]).add(node);
          }
        }
        // add this cell as a neighbour to others in its row
        for (int j = 0; j < getWidth(); j++) {
          if (j != col) {
            neighbours.get(cells[row][j]).add(node);
          }
        }
        // add this cell as a neighbour to others in its zone
        // (except those in the same row or column were already added)
        for (int i = (row / hsize) * hsize; i < (1 + row / hsize) * hsize; i++) {
          if (i != row) {
            for (int j = (col / wsize) * wsize; j < (1 + col / wsize) * wsize; j++) {
              if (j != col) {
                neighbours.get(cells[i][j]).add(node);
              }
            }
          }
        }
      }
    }
  }
  
  public int getZoneHeight() {
    return hsize;
  }
  
  public int getZoneWidth() {
    return wsize;
  }
  
  public int getWidth() {
    return hsize * wsize;
  }
  
  public int getNodeCount() {
    return hsize * hsize * wsize * wsize;
  }
  
  public Collection<SudokuCellAddress> getAllNodes() {
    return Collections.unmodifiableCollection(allNodes);
  } 
  
  public Collection<SudokuCellAddress> getNeighbours(SudokuCellAddress nodeAddr) {
    return Collections.unmodifiableCollection(neighbours.get(nodeAddr));
  }
}

