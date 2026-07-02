package leetcode.array;

import java.util.Arrays;

/**
 * ============================================================
 * LeetCode #36 - Valid Sudoku
 * Difficulty: Medium
 * Link: https://leetcode.com/problems/valid-sudoku/
 * ============================================================
 *
 * PROBLEM STATEMENT:
 * Determine if a 9 x 9 Sudoku board is valid. Only the filled
 * cells need to be validated according to the following rules:
 *
 *   1. Each row must contain the digits 1-9 without repetition.
 *   2. Each column must contain the digits 1-9 without repetition.
 *   3. Each of the nine 3 x 3 sub-boxes of the grid must contain
 *      the digits 1-9 without repetition.
 *
 * NOTE:
 *   - A Sudoku board (partially filled) could be valid but is
 *     not necessarily solvable.
 *   - Only the filled cells need to be validated according to
 *     the mentioned rules. Empty cells are represented by '.'
 *
 * CONSTRAINTS:
 *   - board.length == 9
 *   - board[i].length == 9
 *   - board[i][j] is a digit 1-9 or '.'
 *
 * EXAMPLE 1:
 * Input:
 * [["5","3",".",".","7",".",".",".","."]
 * ,["6",".",".","1","9","5",".",".","."]
 * ,[".","9","8",".",".",".",".","6","."]
 * ,["8",".",".",".","6",".",".",".","3"]
 * ,["4",".",".","8",".","3",".",".","1"]
 * ,["7",".",".",".","2",".",".",".","6"]
 * ,[".","6",".",".",".",".","2","8","."]
 * ,[".",".",".","4","1","9",".",".","5"]
 * ,[".",".",".",".","8",".",".","7","9"]]
 * Output: true
 *
 * EXAMPLE 2:
 * Input: Same as Example 1, except top-left '5' changed to '8'.
 * Output: false
 * Explanation: There are two 8's in the top-left 3x3 sub-box,
 * making it invalid.
 *
 * ============================================================
 * APPROACH: Single-pass validation using boolean tracking arrays
 * ============================================================
 * We traverse the board once. For every filled cell we check
 * whether the digit has already been seen in the same row,
 * same column, or same 3x3 box using three boolean[9][9]
 * arrays (rows, cols, boxes). If yes -> invalid board.
 * Otherwise we mark the digit as "seen" and continue.
 *
 * Box index formula: boxIndex = (r / 3) * 3 + (c / 3)
 * This maps each cell to one of the 9 sub-boxes (0 to 8).
 *
 * Time Complexity : O(1) -> effectively O(N^2) for fixed N = 9
 *                    (81 cells visited exactly once, constant work per cell)
 * Space Complexity: O(1) -> fixed size 9x9x3 boolean arrays,
 *                    independent of input content
 * ============================================================
 */

public class ValidSudoku {
	
	/**
     * Approach: Single pass over the 9x9 board.
     * For each filled cell, track whether the digit has already
     * appeared in its row, column, or 3x3 box using boolean arrays.
     *
     * Time Complexity : O(1) -> effectively O(N^2) for fixed N = 9
     * Space Complexity: O(1) -> fixed size 9x9 boolean arrays
     */
	
	public boolean isValidSudoku(char[][] board) {
		boolean[][] rows = new boolean[9][9];	// rows[r][digit-1]
		boolean[][] cols = new boolean[9][9];	// cols[c][digit-1]
		boolean[][] boxes = new boolean[9][9];	// boxes[boxIndex][digit-1]
		
		for(int r = 0; r < 9; r++) {
			for(int c = 0; c < 9; c++) {
				char cell = board[r][c];
				
				if(cell == '.') {
					continue;	// skip empty cells('.')
				}
				
				int digit = cell - '1';		// 0-indexed digit (0 to 8)
				int boxIndex = (r/3)*3 + (c/3);
				
				if(rows[r][digit] || cols[c][digit] || boxes[boxIndex][digit]) {
					return false; // duplicate found
				}
				
				rows[r][digit] = true;
				cols[c][digit] = true;
				boxes[boxIndex][digit] = true;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		ValidSudoku solver = new ValidSudoku();
		 
        // Example 1: Valid board -> expected true
        char[][] board1 = {
            {'5','3','.','.','7','.','.','.','.'},
            {'6','.','.','1','9','5','.','.','.'},
            {'.','9','8','.','.','.','.','6','.'},
            {'8','.','.','.','6','.','.','.','3'},
            {'4','.','.','8','.','3','.','.','1'},
            {'7','.','.','.','2','.','.','.','6'},
            {'.','6','.','.','.','.','2','8','.'},
            {'.','.','.','4','1','9','.','.','5'},
            {'.','.','.','.','8','.','.','7','9'}
        };
        System.out.println("Example 1 (expected true) : " + solver.isValidSudoku(board1));
 
        // Example 2: Invalid board (duplicate 8 in top-left box) -> expected false
        char[][] board2 = {
            {'8','3','.','.','7','.','.','.','.'},
            {'6','.','.','1','9','5','.','.','.'},
            {'.','9','8','.','.','.','.','6','.'},
            {'8','.','.','.','6','.','.','.','3'},
            {'4','.','.','8','.','3','.','.','1'},
            {'7','.','.','.','2','.','.','.','6'},
            {'.','6','.','.','.','.','2','8','.'},
            {'.','.','.','4','1','9','.','.','5'},
            {'.','.','.','.','8','.','.','7','9'}
        };
        System.out.println("Example 2 (expected false): " + solver.isValidSudoku(board2));
 
        // Extra test: duplicate in a row -> expected false
        char[][] board3 = {
            {'5','5','.','.','7','.','.','.','.'},
            {'6','.','.','1','9','5','.','.','.'},
            {'.','9','8','.','.','.','.','6','.'},
            {'8','.','.','.','6','.','.','.','3'},
            {'4','.','.','8','.','3','.','.','1'},
            {'7','.','.','.','2','.','.','.','6'},
            {'.','6','.','.','.','.','2','8','.'},
            {'.','.','.','4','1','9','.','.','5'},
            {'.','.','.','.','8','.','.','7','9'}
        };
        System.out.println("Example 3 (row dup, expected false): " + solver.isValidSudoku(board3));
 
        // Extra test: fully empty board -> expected true
        char[][] board4 = new char[9][9];
        for (char[] row : board4) Arrays.fill(row, '.');
        System.out.println("Example 4 (empty board, expected true): " + solver.isValidSudoku(board4));
	}

}
