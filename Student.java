import java.util.LinkedList;

/**
 * ============================================================================
 * Student.java - Model class representing a student in the exam system
 * ============================================================================
 * 
 * PURPOSE:
 * This class represents a student who can take exams. It stores student
 * information and maintains a history of all exam attempts.
 * 
 * DATA STRUCTURE USED: LinkedList<Integer> for attempt history
 * 
 * WHY LinkedList (not ArrayList)?
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ Operation │ LinkedList │ ArrayList │ Winner for our use case │
 * ├─────────────────────────────────────────────────────────────────────────┤
 * │ Add at end │ O(1) │ O(1)* │ Both good │
 * │ Add at beginning │ O(1) │ O(n) │ LinkedList │
 * │ Memory overhead │ Higher │ Lower │ ArrayList │
 * │ Sequential reads │ O(n) │ O(n) │ Both same │
 * └─────────────────────────────────────────────────────────────────────────┘
 * * ArrayList is O(1) amortized, but O(n) when resizing
 * 
 * For attempt history, we chose LinkedList because:
 * 1. We always add new attempts (sequential insertion)
 * 2. We display entire history (sequential traversal)
 * 3. No random access needed
 * 4. Demonstrates LinkedList usage as per requirements
 * 
 * INTERVIEW TIP:
 * When asked "Why LinkedList?", explain the access pattern:
 * - If mostly sequential access/insertion → LinkedList
 * - If random access needed → ArrayList
 * 
 * @author Online Exam System
 * @version 1.0
 */
public class Student implements Comparable<Student> {

    // ========================================================================
    // INSTANCE VARIABLES
    // ========================================================================

    /**
     * Unique identifier for the student.
     * Used as key in HashMap for O(1) lookup.
     */
    private int id;

    /**
     * Name of the student for display purposes.
     */
    private String name;

    /**
     * Current/Latest score of the student.
     * Updated after each exam attempt.
     */
    private int score;

    /**
     * History of all exam attempt scores.
     * 
     * WHY LinkedList<Integer>?
     * ─────────────────────────
     * 1. Maintains insertion order (chronological history)
     * 2. O(1) insertion at end using addLast()
     * 3. O(n) traversal for displaying history (acceptable)
     * 4. No need for random access (we always show full history)
     * 
     * Space Complexity: O(k) where k = number of attempts
     * 
     * INTERVIEW NOTE:
     * LinkedList in Java is a doubly-linked list, so:
     * - addFirst() and addLast() are both O(1)
     * - get(index) is O(n) - avoid if possible
     */
    private LinkedList<Integer> attemptHistory;

    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================

    /**
     * Creates a new Student with the given ID and name.
     * Initializes score to 0 and creates empty attempt history.
     * 
     * Time Complexity: O(1)
     * Reason: Simple assignments and one LinkedList instantiation.
     * 
     * @param id   Unique student identifier
     * @param name Student's name
     */
    public Student(int id, String name) {
        this.id = id; // O(1)
        this.name = name; // O(1)
        this.score = 0; // O(1)
        this.attemptHistory = new LinkedList<>(); // O(1)
    }

    // ========================================================================
    // GETTER METHODS
    // ========================================================================

    /**
     * Returns the student's unique ID.
     * 
     * Time Complexity: O(1)
     * Reason: Returns primitive int value.
     * 
     * @return Student ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the student's name.
     * 
     * Time Complexity: O(1)
     * Reason: Returns String reference.
     * 
     * @return Student name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the student's current/latest score.
     * 
     * Time Complexity: O(1)
     * Reason: Returns primitive int value.
     * 
     * @return Current score
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the complete attempt history.
     * 
     * Time Complexity: O(1)
     * Reason: Returns reference to LinkedList (not a copy).
     * 
     * @return LinkedList containing all attempt scores
     */
    public LinkedList<Integer> getAttemptHistory() {
        return attemptHistory;
    }

    // ========================================================================
    // SETTER / MUTATOR METHODS
    // ========================================================================

    /**
     * Updates the student's score after an exam attempt.
     * 
     * Time Complexity: O(1)
     * Reason: Simple assignment operation.
     * 
     * @param score New score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    // ========================================================================
    // BUSINESS LOGIC METHODS
    // ========================================================================

    /**
     * Adds a new attempt score to the student's history.
     * 
     * Time Complexity: O(1)
     * Reason: LinkedList.addLast() is O(1) because LinkedList maintains
     * a reference to the tail node (doubly-linked list).
     * 
     * WHY addLast() and not add()?
     * - add() in LinkedList also adds at the end (O(1))
     * - addLast() is more explicit about intention
     * - Both have same time complexity
     * 
     * INTERVIEW TIP:
     * This is a perfect example of why LinkedList is chosen:
     * We're only adding at the end, never in the middle.
     * 
     * @param attemptScore Score from the exam attempt
     */
    public void addAttempt(int attemptScore) {
        // Using addLast() - O(1) operation
        // LinkedList maintains tail reference, so no traversal needed
        attemptHistory.addLast(attemptScore);
    }

    /**
     * Displays the complete attempt history of the student.
     * 
     * Time Complexity: O(n) where n = number of attempts
     * Reason: Must traverse entire LinkedList to print all scores.
     * 
     * Space Complexity: O(1)
     * Reason: Only using a counter variable, no extra data structures.
     * 
     * WHY FOR-EACH LOOP?
     * - LinkedList implements Iterable, so for-each works
     * - Cleaner syntax than using Iterator explicitly
     * - Same O(n) performance as manual iteration
     */
    public void displayAttemptHistory() {
        // Check if history is empty
        if (attemptHistory.isEmpty()) {
            System.out.println("No attempts yet.");
            return;
        }

        System.out.println("\n===== Attempt History for " + name + " (ID: " + id + ") =====");

        // Counter for attempt numbering
        int attemptNumber = 1;

        // Enhanced for loop - iterates through LinkedList
        // Time Complexity: O(n) where n = attemptHistory.size()
        for (Integer score : attemptHistory) {
            System.out.println("Attempt " + attemptNumber + ": " + score + " marks");
            attemptNumber++;
        }

        System.out.println("Total attempts: " + attemptHistory.size());
        System.out.println("================================================");
    }

    /**
     * Returns the number of exam attempts made by this student.
     * 
     * Time Complexity: O(1)
     * Reason: LinkedList maintains size internally.
     * 
     * @return Number of attempts
     */
    public int getAttemptCount() {
        // LinkedList.size() is O(1) - size is cached
        return attemptHistory.size();
    }

    /**
     * Calculates and returns the average score across all attempts.
     * 
     * Time Complexity: O(n) where n = number of attempts
     * Reason: Must sum all scores, requiring full traversal.
     * 
     * @return Average score as double, or 0.0 if no attempts
     */
    public double getAverageScore() {
        // Handle edge case - no attempts
        if (attemptHistory.isEmpty()) {
            return 0.0;
        }

        // Sum all scores - O(n) traversal
        int sum = 0;
        for (Integer score : attemptHistory) {
            sum += score;
        }

        // Calculate and return average
        return (double) sum / attemptHistory.size();
    }

    // ========================================================================
    // COMPARABLE IMPLEMENTATION
    // ========================================================================

    /**
     * Compares this student with another based on score.
     * Used for sorting students by score.
     * 
     * Time Complexity: O(1)
     * Reason: Simple integer comparison.
     * 
     * WHY IMPLEMENT COMPARABLE?
     * - Enables natural ordering of Student objects
     * - Required for Collections.sort() and sorted collections
     * - Used for Binary Search (requires sorted array)
     * 
     * @param other The student to compare with
     * @return negative if this < other, 0 if equal, positive if this > other
     */
    @Override
    public int compareTo(Student other) {
        // Compare by ID for sorting purposes
        // This enables binary search by ID
        return Integer.compare(this.id, other.id);
    }

    // ========================================================================
    // OBJECT OVERRIDES
    // ========================================================================

    /**
     * Returns a string representation of the student.
     * 
     * Time Complexity: O(1)
     * Reason: String concatenation of fixed number of fields.
     * 
     * @return String representation
     */
    @Override
    public String toString() {
        return "Student [ID=" + id + ", Name=" + name + ", Score=" + score + "]";
    }
}
