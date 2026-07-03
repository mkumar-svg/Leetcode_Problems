package leetcode.dp;

/**
 *===============================================================
 * 5. Longest Palindromic Substring
 * https://leetcode.com/problems/longest-palindromic-substring/
 * Difficulty: Medium
 *=============================================================== 
 *
 * Given a string s, return the longest palindromic substring in s.
 *
 * Example 1:
 * Input: s = "babad"
 * Output: "bab"
 * Explanation: "aba" is also a valid answer.
 *
 * Example 2:
 * Input: s = "cbbd"
 * Output: "bb"
 *
 * Constraints:
 * 1 <= s.length <= 1000
 * s consist of only digits and English letters.
 *
 *============================================================================
 * Approach: Expand Around Center
 *============================================================================
 *
 * Idea: Every palindrome has a center. Expanding outward from that center,
 * as long as the characters on the left and right match, the palindrome
 * keeps growing. As soon as there's a mismatch or we hit a boundary, the
 * expansion stops.
 *
 * There are two possible types of centers:
 *   1) Odd length palindrome (e.g. "aba") -> center = a single character i
 *   2) Even length palindrome (e.g. "bb") -> center = the gap between i, i+1
 *
 * Steps:
 *   1. For every index i, treat it as an odd-length center and call
 *      expandAroundCenter(i, i).
 *   2. For every index i, treat it as an even-length center and call
 *      expandAroundCenter(i, i+1) (the gap between i and i+1 is the center).
 *   3. Compare whichever length comes out longer (maxLen) against the
 *      current best (end - start + 1).
 *   4. If maxLen is bigger, update start/end to the new palindrome's bounds:
 *        start = i - (maxLen - 1) / 2
 *        end   = i + maxLen / 2
 *      (this formula works for both the odd and even case)
 *   5. After processing all indices, return s.substring(start, end + 1) —
 *      that's the longest palindromic substring.
 *
 * expandAroundCenter(left, right) helper:
 *   - Move left/right outward as long as s.charAt(left) == s.charAt(right),
 *     or until a boundary is hit (left < 0 / right >= n).
 *   - After the loop ends, left/right have each moved one extra step past
 *     the actual palindrome, so the true length is right - left - 1.
 *
 * Total centers = 2n - 1 (n odd-centers + (n-1) even-centers), and each
 * center can expand up to O(n) in the worst case.
 *
 * Time Complexity:  O(n^2)  -> 2n-1 centers, each expanding up to O(n)
 * Space Complexity: O(1)    -> only a few pointers/variables, no extra space
 * ============================================================================
 * 
 */

public class LongestPalindromicSubstring {
	public static String longestPalindrome(String s) {
		if(s == null || s.length() < 1) {
			return "";
		}
		
		int start = 0, end = 0; // bounds of the best palindrome found (inclusive)
		for(int i = 0; i < s.length(); i++) {
			// Case 1: odd length palindrome -> center = single char at i
			int len1 = expandAroundCenter(s, i, i);
			
			// Case 2: even length palindrome -> center = between i and i+1
			int len2 = expandAroundCenter(s, i, i+1);
			
			int maxLen = Math.max(len1, len2);
			
			// If this palindrome is longer than the current best, update start/end
			if(maxLen > end - start + 1) {
				// Deriving start/end from maxLen:
                // a palindrome of length maxLen centered at i -> left = i - (maxLen-1)/2
				start = i - (maxLen - 1)/2;
				end = i + maxLen/2;
			}			
		}
		return s.substring(start, end + 1);
	}
	
	// Expands left/right outward as long as the characters match,
    // and returns the resulting palindrome length
	private static int expandAroundCenter(String s, int left, int right) {
		while(left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
			left--;
			right++;
		}
		
		// once the loop ends, left/right have each moved one step too far,
        // so the actual length is right - left - 1
		return right - left - 1;
	}
	
	public static void main(String[] args) {
        // Example 1
        String s1 = "babad";
        System.out.println("Input: " + s1);
        System.out.println("Output: " + longestPalindrome(s1));
        System.out.println("(\"aba\" is also a valid answer)");
        System.out.println();
 
        // Example 2
        String s2 = "cbbd";
        System.out.println("Input: " + s2);
        System.out.println("Output: " + longestPalindrome(s2));
        System.out.println();
 
        // A few extra test cases
        String[] tests = {"a", "ac", "racecar", "forgeeksskeegfor", "aaaa"};
        for (String t : tests) {
            System.out.println("Input: " + t + "  ->  Output: " + longestPalindrome(t));
        }
    }
}
