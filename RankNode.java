/**
 * ============================================================================
 * RankNode.java - Node class for Binary Search Tree (BST) ranking system
 * ============================================================================
 * 
 * PURPOSE:
 * This class represents a single node in the Binary Search Tree used for
 * the ranking system. Each node contains a student's score and name.
 * 
 * DATA STRUCTURE: Binary Search Tree Node
 * 
 * WHY BST FOR RANKING?
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ Operation │ BST (Avg) │ Array │ Why BST Wins │
 * ├─────────────────────────────────────────────────────────────────────────┤
 * │ Insert │ O(log n) │ O(n) │ No shifting needed │
 * │ Get sorted list │ O(n) │ O(n log n) │ In-order gives sorted │
 * │ Find min/max │ O(log n) │ O(n) or O(1)│ BST: leftmost/rightmost│
 * │ Search by score │ O(log n) │ O(n) │ Tree structure │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * BST PROPERTY:
 * - Left subtree contains nodes with scores LESS than current node
 * - Right subtree contains nodes with scores GREATER than current node
 * - In-order traversal gives scores in ascending order (for rankings)
 * 
 * INTERVIEW TIP:
 * When discussing BST, always mention:
 * 1. Average case: O(log n) for insert/search
 * 2. Worst case: O(n) when tree is skewed (like a linked list)
 * 3. Solution for worst case: Self-balancing trees (AVL, Red-Black)
 * 
 * For this project, we use simple BST for clarity. In production,
 * consider using TreeMap (Red-Black tree internally) for guaranteed O(log n).
 * 
 * @author Online Exam System
 * @version 1.0
 */
public class RankNode {

    // ========================================================================
    // INSTANCE VARIABLES
    // ========================================================================

    /**
     * The score of the student (used as the key for BST ordering).
     * 
     * WHY SCORE AS KEY?
     * - Ranking is based on scores
     * - In-order traversal will give scores in ascending order
     * - For descending order (highest first), we do reverse in-order
     */
    int score;

    /**
     * Name of the student for display purposes.
     * Multiple students can have the same score (handled in insert logic).
     */
    String studentName;

    /**
     * Reference to the left child node.
     * Contains nodes with SMALLER scores.
     * 
     * BST Property: left.score < this.score
     */
    RankNode left;

    /**
     * Reference to the right child node.
     * Contains nodes with LARGER scores.
     * 
     * BST Property: right.score > this.score
     */
    RankNode right;

    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================

    /**
     * Creates a new RankNode with the given score and student name.
     * Left and right children are initialized to null (leaf node).
     * 
     * Time Complexity: O(1)
     * Reason: Simple assignment operations.
     * 
     * Space Complexity: O(1)
     * Reason: Fixed number of fields regardless of input.
     * 
     * @param score       The student's exam score
     * @param studentName The student's name
     */
    public RankNode(int score, String studentName) {
        // Store score - used for BST ordering
        this.score = score;

        // Store student name - for display
        this.studentName = studentName;

        // Initialize children as null (new node is a leaf)
        // These will be set by RankTree.insert() when children are added
        this.left = null;
        this.right = null;
    }

    // ========================================================================
    // VISUAL REPRESENTATION OF BST
    // ========================================================================

    /**
     * EXAMPLE BST STRUCTURE:
     * 
     * Suppose we insert scores: 70, 50, 90, 40, 60, 80, 100
     * 
     * 70 (root)
     * / \
     * 50 90
     * / \ / \
     * 40 60 80 100
     * 
     * IN-ORDER TRAVERSAL (Left → Root → Right):
     * 40 → 50 → 60 → 70 → 80 → 90 → 100
     * This gives us scores in ASCENDING order!
     * 
     * REVERSE IN-ORDER (Right → Root → Left):
     * 100 → 90 → 80 → 70 → 60 → 50 → 40
     * This gives us scores in DESCENDING order (for ranking!)
     * 
     * WHY THIS IS EFFICIENT:
     * - No need to sort after insertion
     * - O(n) to get sorted list (just traverse)
     * - O(log n) average to insert new score
     */

    // ========================================================================
    // UTILITY METHODS
    // ========================================================================

    /**
     * Returns a string representation of this node.
     * Useful for debugging and display.
     * 
     * Time Complexity: O(1)
     * Reason: Simple string formatting.
     * 
     * @return Formatted string with student name and score
     */
    @Override
    public String toString() {
        return studentName + " - " + score + " marks";
    }

    /**
     * Checks if this node is a leaf node (no children).
     * 
     * Time Complexity: O(1)
     * Reason: Simple null checks.
     * 
     * INTERVIEW TIP:
     * Leaf nodes are important in BST operations:
     * - Insertion always happens at a leaf position
     * - Deletion of leaf node is simplest case
     * 
     * @return true if node has no children, false otherwise
     */
    public boolean isLeaf() {
        return left == null && right == null;
    }

    /**
     * Checks if this node has only one child.
     * 
     * Time Complexity: O(1)
     * Reason: Simple boolean operations.
     * 
     * Useful for BST deletion (node with one child case).
     * 
     * @return true if node has exactly one child
     */
    public boolean hasOneChild() {
        // XOR logic: exactly one of left/right is non-null
        return (left == null) != (right == null);
    }

    /**
     * Checks if this node has two children.
     * 
     * Time Complexity: O(1)
     * Reason: Simple null checks.
     * 
     * @return true if node has both left and right children
     */
    public boolean hasTwoChildren() {
        return left != null && right != null;
    }
}
