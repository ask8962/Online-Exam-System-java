/**
 * ============================================================================
 * RankTree.java - Binary Search Tree implementation for the ranking system
 * ============================================================================
 * 
 * PURPOSE:
 * Implements a Binary Search Tree (BST) to maintain student rankings based
 * on their exam scores. Provides efficient insertion and sorted retrieval.
 * 
 * DATA STRUCTURE: Binary Search Tree (BST)
 * 
 * WHY BST FOR RANKINGS?
 * ─────────────────────
 * 1. O(log n) average insertion - better than sorted array O(n)
 * 2. O(n) in-order traversal gives sorted rankings automatically
 * 3. Natural hierarchical structure for ranking data
 * 4. No need to re-sort after each insertion
 * 
 * TIME COMPLEXITY SUMMARY:
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ Method │ Average Case │ Worst Case │ When Worst Occurs │
 * ├─────────────────────────────────────────────────────────────────────────┤
 * │ insert() │ O(log n) │ O(n) │ Skewed tree │
 * │ displayRankings() │ O(n) │ O(n) │ Always │
 * │ reverseInOrder() │ O(n) │ O(n) │ Always │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * SPACE COMPLEXITY: O(n) for storing n students
 * 
 * INTERVIEW TIP:
 * The worst case O(n) for insert happens when elements are inserted in
 * sorted order, creating a "skewed tree" (essentially a linked list).
 * Solution: Use self-balancing BST (AVL/Red-Black tree) or Java's TreeMap.
 * 
 * @author Online Exam System
 * @version 1.0
 */
public class RankTree {

    // ========================================================================
    // INSTANCE VARIABLES
    // ========================================================================

    /**
     * Root node of the BST.
     * If root is null, the tree is empty.
     * 
     * All tree operations start from the root and traverse down.
     */
    private RankNode root;

    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================

    /**
     * Creates an empty RankTree.
     * 
     * Time Complexity: O(1)
     * Reason: Just initializing root to null.
     */
    public RankTree() {
        // Initially, tree is empty
        this.root = null;
    }

    // ========================================================================
    // PUBLIC METHODS
    // ========================================================================

    /**
     * Inserts a new student score into the BST.
     * 
     * Time Complexity:
     * - Average Case: O(log n) - tree is reasonably balanced
     * - Worst Case: O(n) - tree is completely skewed
     * 
     * Reason: We traverse from root to a leaf position.
     * In balanced tree: height = log n
     * In skewed tree: height = n
     * 
     * HOW BST INSERT WORKS:
     * 1. Start at root
     * 2. If new score < current node score → go left
     * 3. If new score >= current node score → go right
     * 4. Repeat until we find a null position
     * 5. Insert new node at that position
     * 
     * NOTE: We handle equal scores by going right (they'll appear together).
     * 
     * @param score       The student's score
     * @param studentName The student's name
     */
    public void insert(int score, String studentName) {
        // Create the new node to insert
        RankNode newNode = new RankNode(score, studentName);

        // Special case: empty tree
        // Simply make the new node the root
        if (root == null) {
            root = newNode;
            return;
        }

        // Call recursive helper to find insert position
        insertRecursive(root, newNode);
    }

    /**
     * Displays all rankings in descending order (highest score first).
     * Uses reverse in-order traversal (Right → Root → Left).
     * 
     * Time Complexity: O(n)
     * Reason: Must visit every node exactly once to display.
     * 
     * Space Complexity: O(h) where h = height of tree (recursion stack)
     * - Best case (balanced): O(log n)
     * - Worst case (skewed): O(n)
     * 
     * WHY REVERSE IN-ORDER?
     * - Normal in-order (L→Root→R) gives ascending order
     * - Reverse in-order (R→Root→L) gives descending order
     * - For rankings, we want highest score first (descending)
     */
    public void displayRankings() {
        // Check if tree is empty
        if (root == null) {
            System.out.println("No rankings available yet. Take an exam first!");
            return;
        }

        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║               STUDENT RANKING LIST                    ║");
        System.out.println("║         (Binary Search Tree - In-Order)               ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");

        // Use an array to track rank number (passed by reference)
        // This is a common technique when we need mutable state in recursion
        int[] rank = { 1 };

        // Start reverse in-order traversal from root
        reverseInOrderWithRank(root, rank);

        System.out.println("╚══════════════════════════════════════════════════════╝");
    }

    /**
     * Checks if the tree is empty.
     * 
     * Time Complexity: O(1)
     * Reason: Simple null check.
     * 
     * @return true if no students in ranking, false otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }

    // ========================================================================
    // PRIVATE HELPER METHODS
    // ========================================================================

    /**
     * Recursive helper method to insert a new node in the BST.
     * 
     * Time Complexity: O(log n) average, O(n) worst case
     * Reason: Each recursive call goes one level down.
     * 
     * ALGORITHM (BST Property):
     * - If newNode.score < current.score → go LEFT
     * - If newNode.score >= current.score → go RIGHT
     * - Insert at first null position found
     * 
     * WHY RECURSIVE?
     * - Cleaner code than iterative version
     * - Naturally matches tree structure
     * - Each call handles one level of the tree
     * 
     * ALTERNATIVE: Iterative approach with while loop
     * - More efficient (no stack overhead)
     * - Slightly more complex code
     * 
     * @param current The current node being examined
     * @param newNode The node to insert
     */
    private void insertRecursive(RankNode current, RankNode newNode) {
        // BST Insert Logic:
        // Smaller scores go LEFT, larger/equal scores go RIGHT

        if (newNode.score < current.score) {
            // ─────────────────────────────────────────────────────
            // CASE 1: New score is SMALLER - go LEFT
            // ─────────────────────────────────────────────────────

            if (current.left == null) {
                // Found empty left spot - insert here!
                current.left = newNode;
            } else {
                // Left spot taken - continue searching in left subtree
                insertRecursive(current.left, newNode);
            }

        } else {
            // ─────────────────────────────────────────────────────
            // CASE 2: New score is LARGER or EQUAL - go RIGHT
            // ─────────────────────────────────────────────────────

            if (current.right == null) {
                // Found empty right spot - insert here!
                current.right = newNode;
            } else {
                // Right spot taken - continue searching in right subtree
                insertRecursive(current.right, newNode);
            }
        }
    }

    /**
     * Performs reverse in-order traversal with rank numbering.
     * 
     * TRAVERSAL ORDER: Right → Node → Left
     * This gives us DESCENDING order (highest score first).
     * 
     * Time Complexity: O(n)
     * Reason: Visits every node exactly once.
     * 
     * Space Complexity: O(h) for recursion stack
     * where h = height of tree.
     * 
     * WHY ARRAY FOR RANK?
     * - Java passes primitives by value (copy)
     * - Array is passed by reference
     * - This allows us to maintain rank counter across recursive calls
     * - Alternative: Use instance variable, but array is more functional
     * 
     * @param node The current node
     * @param rank Array containing current rank number
     */
    private void reverseInOrderWithRank(RankNode node, int[] rank) {
        // Base case: reached null node (leaf's child)
        if (node == null) {
            return;
        }

        // ─────────────────────────────────────────────────────
        // STEP 1: Visit RIGHT subtree first (higher scores)
        // ─────────────────────────────────────────────────────
        reverseInOrderWithRank(node.right, rank);

        // ─────────────────────────────────────────────────────
        // STEP 2: Process current node (print with rank)
        // ─────────────────────────────────────────────────────
        System.out.printf("║  Rank #%-3d │ %-20s │ %3d marks  ║%n",
                rank[0], node.studentName, node.score);
        rank[0]++; // Increment rank for next student

        // ─────────────────────────────────────────────────────
        // STEP 3: Visit LEFT subtree (lower scores)
        // ─────────────────────────────────────────────────────
        reverseInOrderWithRank(node.left, rank);
    }

    /**
     * Standard in-order traversal (for debugging).
     * Gives scores in ASCENDING order (lowest to highest).
     * 
     * TRAVERSAL ORDER: Left → Node → Right
     * 
     * Time Complexity: O(n)
     * Reason: Visits every node exactly once.
     * 
     * @param node The starting node
     */
    @SuppressWarnings("unused")
    private void inOrder(RankNode node) {
        // Base case
        if (node == null) {
            return;
        }

        // Left → Node → Right (standard in-order)
        inOrder(node.left);
        System.out.println(node.studentName + ": " + node.score);
        inOrder(node.right);
    }

    // ========================================================================
    // VISUAL EXPLANATION
    // ========================================================================

    /**
     * EXAMPLE OF BST RANKING:
     * 
     * Insertions: (80, "Alice"), (60, "Bob"), (90, "Charlie"), (70, "David")
     * 
     * Tree Structure:
     * 80 (Alice)
     * / \
     * 60 (Bob) 90 (Charlie)
     * \
     * 70 (David)
     * 
     * Reverse In-Order Traversal (R → Root → L):
     * 1. Go right: Charlie (90)
     * 2. Visit root: Alice (80)
     * 3. Go left, then right in left subtree: David (70)
     * 4. Visit Bob: (60)
     * 
     * OUTPUT:
     * Rank 1: Charlie - 90 marks
     * Rank 2: Alice - 80 marks
     * Rank 3: David - 70 marks
     * Rank 4: Bob - 60 marks
     * 
     * ✓ Sorted in descending order automatically!
     */
}
