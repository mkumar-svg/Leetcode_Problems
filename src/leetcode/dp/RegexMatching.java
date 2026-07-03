package leetcode.dp;

/**
 * 
 * ============================================================================
 * LeetCode 10. Regular Expression Matching
 * Difficulty: Hard
 * Link: https://leetcode.com/problems/regular-expression-matching/
 * ============================================================================
 *
 * Problem:
 * Given an input string `s` and a pattern `p`, implement regular expression
 * matching with support for '.' and '*' where:
 *   - '.' Matches any single character.
 *   - '*' Matches zero or more of the preceding element.
 * Return a boolean indicating whether the matching covers the entire input
 * string (not partial).
 *
 * Example 1:
 *   Input: s = "aa", p = "a"
 *   Output: false
 *   Explanation: "a" does not match the entire string "aa".
 *
 * Example 2:
 *   Input: s = "aa", p = "a*"
 *   Output: true
 *   Explanation: '*' means zero or more of the preceding element, 'a'.
 *   Therefore, by repeating 'a' once, it becomes "aa".
 *
 * Example 3:
 *   Input: s = "ab", p = ".*"
 *   Output: true
 *   Explanation: ".*" means "zero or more (*) of any character (.)".
 *
 * Constraints:
 *   1 <= s.length <= 20
 *   1 <= p.length <= 20
 *   s contains only lowercase English letters.
 *   p contains only lowercase English letters, '.', and '*'.
 *   It is guaranteed for each appearance of '*', there is a previous
 *   valid character to match.
 *
 * ============================================================================
 * Approach: Bottom-Up Dynamic Programming (2D Table)
 * ============================================================================
 * Define dp[i][j] = true if s[0..i) (first i chars of s) matches
 *                    p[0..j) (first j chars of p).
 *
 * Base case:
 *   dp[0][0] = true  -> empty string matches empty pattern.
 *
 * Row 0 (empty s vs non-empty p):
 *   Only patterns like "a*", "a*b*", "a*b*c*" can match an empty string,
 *   because '*' allows zero occurrences of the preceding character.
 *   So: dp[0][j] = dp[0][j-2]  when p[j-1] == '*'  (skip the "x*" pair)
 *
 * General transition, for s[i-1] and p[j-1]:
 *   1) p[j-1] is '.' or p[j-1] == s[i-1]  -> direct character match
 *        dp[i][j] = dp[i-1][j-1]
 *
 *   2) p[j-1] == '*'  -> two sub-choices, combined with OR:
 *        a) Zero occurrence: treat "p[j-2]*" as contributing nothing.
 *             dp[i][j] |= dp[i][j-2]
 *        b) One or more occurrence: only valid if the character before
 *           '*' (p[j-2]) actually matches s[i-1] (or is '.').
 *           If it matches, we "use up" one character of s and stay on
 *           the same pattern position (since '*' can repeat):
 *             dp[i][j] |= dp[i-1][j]
 *
 *   3) Otherwise -> characters don't match and it's not '*' -> dp[i][j] = false
 *
 * Final answer: dp[n][m], where n = s.length(), m = p.length().
 *
 * Time complexity:  O(n * m)  -- we fill an (n+1) x (m+1) table once.
 * Space complexity: O(n * m)  -- for the dp table.
 * ============================================================================
 */

public class RegexMatching {
	public static boolean isMatch(String s, String p) {
		int n = s.length();
		int m = p.length();
		
		// dp[i][j] = true if s[0..i) matches p[0..j)
		boolean[][] dp = new boolean[n+1][m+1];
		dp[0][0] = true; // empty string matches empty pattern
		
		// Handle patterns like "a*", "a*b*", "a*b*c*" matching empty string
		for(int j = 1; j <= m; j++) {
			if(p.charAt(j-1) == '*') {
				dp[0][j] = dp[0][j-2]; // zero occurrence of preceding char
			}
		}
		
		for(int i = 1; i <= n; i++) {
			for(int j = 1; j <= m; j++) {
				char sc = s.charAt(i-1);
				char pc = p.charAt(j-1);
				
				if(pc == '*') {
					char prevPc = p.charAt(j-2);
					
					// Option 1: treat "prevPc*" as zero occurrences
					boolean zeroOccurrence = dp[i][j-2];
					
					// Option 2: match one more occurrence of prevPc, if it fits sc
                    boolean oneOrMore = false;
                    if(prevPc == '.' || prevPc == sc) {
                    	oneOrMore = dp[i-1][j];
                    }
                    
                    dp[i][j] = zeroOccurrence || oneOrMore;
				} else if(pc == '.' || pc == sc) {
					dp[i][j] = dp[i-1][j-1];
				} else {
					dp[i][j] = false;
				}
			}
		}
		
		return dp[n][m];
	}
	
	public static void main(String[] args) {
        // Example 1
        System.out.println(isMatch("aa", "a"));    // false

        // Example 2
        System.out.println(isMatch("aa", "a*"));   // true

        // Example 3
        System.out.println(isMatch("ab", ".*"));   // true

        // Extra test cases
        System.out.println(isMatch("mississippi", "mis*is*p*.")); // false
        System.out.println(isMatch("aab", "c*a*b")); // true
        System.out.println(isMatch("", "a*"));       // true
        System.out.println(isMatch("", ".*"));       // true
        System.out.println(isMatch("a", ""));        // false
    }
}
