# Sudoku
Sudoku solver

## build

`ant`

## Setup

Create a data file in the `boards/` directory named `myboard.gb`

For example:

3  
0 9 2 5 6 0 0 0 0  
0 0 3 0 2 7 0 6 0  
6 0 0 0 0 0 2 7 9  
0 0 8 0 0 0 4 2 3  
0 4 0 0 3 2 0 5 0  
0 3 6 0 5 8 0 0 0  
3 2 0 8 0 0 0 0 6  
1 0 0 2 0 0 9 0 5  
4 0 0 6 0 9 3 0 0

The first line is normally 3, the number of values across a cell.

The rest of the lines specify the given values, with 0 for an unknown value.

## Run

`ant run -Dboard=myboard.gb`

The program will find every possible solution (typically unique,
in a correctly-designed puzzle.)
