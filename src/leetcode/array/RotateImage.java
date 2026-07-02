package leetcode.array;

import java.util.Arrays;

/**
 * ============================================================
 * LeetCode #48 - Rotate Image
 * Difficulty: Medium
 * Link: https://leetcode.com/problems/rotate-image/
 * ============================================================
 *
 * PROBLEM STATEMENT:
 * You are given an n x n 2D matrix representing an image, rotate
 * the image by 90 degrees (clockwise).
 *
 * You have to rotate the image in-place, which means you have to
 * modify the input 2D matrix directly. DO NOT allocate another
 * 2D matrix and do the rotation.
 *
 * CONSTRAINTS:
 *   - n == matrix.length == matrix[i].length
 *   - 1 <= n <= 20
 *   - -1000 <= matrix[i][j] <= 1000
 *
 * EXAMPLE 1:
 * Input:  matrix = [[1,2,3],[4,5,6],[7,8,9]]
 * Output: [[7,4,1],[8,5,2],[9,6,3]]
 *
 * EXAMPLE 2:
 * Input:  matrix = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]
 * Output: [[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]
 *
 * ============================================================
 * APPROACH: Transpose + Reverse each row
 * ============================================================
 * Step 1 - Transpose the matrix: swap matrix[i][j] with matrix[j][i]
 *          for all i < j (upper triangle only, so each pair swaps
 *          exactly once). This flips rows into columns.
 *
 * Step 2 - Reverse each row in place using two pointers
 *          (left = 0, right = n-1). This fixes the ordering so the
 *          net effect equals a 90-degree clockwise rotation.
 *
 * Why it works: in a 90-degree clockwise rotation, the last row of
 * the original matrix becomes the first column of the result.
 * Transposing turns rows into columns (correct positions, wrong
 * order), and reversing each row corrects that order.
 *
 * Time Complexity : O(n^2) -> every cell is touched a constant
 *                    number of times (once in transpose, once in reverse)
 * Space Complexity: O(1)   -> no extra matrix allocated, pure in-place swaps
 * ============================================================
 */

public class RotateImage {
	public static void rotate(int[][] matrix) {
		int n = matrix.length;
		
		// Step 1: Transpose the matrix (swap matrix[i][j] with matrix[j][i])
		for(int i = 0; i < n; i++) {
			for(int j = i+1; j < n; j++) {
				int temp = matrix[i][j];
				matrix[i][j] = matrix[j][i];
				matrix[j][i] = temp;
			}
		}
		
		// Step 2: Reverse each row
		for(int i = 0; i < n; i++) {
			int left = 0, right = n-1;
			while(left < right) {
				int temp = matrix[i][left];
				matrix[i][left] = matrix[i][right];
				matrix[i][right] = temp;
				left++;
				right--;
			}
		}
	}
	
	public static void printMatrix(int[][] matrix) {
		for(int[] row : matrix) {
			System.out.println(Arrays.toString(row));
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
        int[][] matrix1 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        System.out.println("Example 1 - Before rotation:");
        printMatrix(matrix1);
        rotate(matrix1);
        System.out.println("Example 1 - After rotation:");
        printMatrix(matrix1);
        // Expected: [7,4,1] [8,5,2] [9,6,3]

        int[][] matrix2 = {
            {5, 1, 9, 11},
            {2, 4, 8, 10},
            {13, 3, 6, 7},
            {15, 14, 12, 16}
        };
        System.out.println("Example 2 - Before rotation:");
        printMatrix(matrix2);
        rotate(matrix2);
        System.out.println("Example 2 - After rotation:");
        printMatrix(matrix2);
        // Expected: [15,13,2,5] [14,3,4,1] [12,6,8,9] [16,7,10,11]

        // Edge case: n = 1
        int[][] matrix3 = {{42}};
        rotate(matrix3);
        System.out.println("Edge case (1x1): " + Arrays.toString(matrix3[0]));
    }
}
