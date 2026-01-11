/**
 * ============================================================================
 * Question.java - Model class representing a single MCQ question
 * ============================================================================
 * 
 * PURPOSE:
 * This class represents a single Multiple Choice Question (MCQ) in the exam.
 * It encapsulates the question text, answer options, and the correct answer index.
 * 
 * DATA STRUCTURE USED: None (Simple POJO - Plain Old Java Object)
 * 
 * WHY THIS DESIGN:
 * - Simple immutable data holder following Single Responsibility Principle
 * - All fields are private with public getters (encapsulation)
 * - No setters as questions should not be modified after creation
 * 
 * TIME COMPLEXITY:
 * - All operations are O(1) as we're just accessing/returning stored values
 * 
 * INTERVIEW TIP:
 * This is a classic example of a model/entity class. In interviews, always
 * mention encapsulation, immutability (when applicable), and SRP.
 * 
 * @author Online Exam System
 * @version 1.0
 */
public class Question {
    
    // ========================================================================
    // INSTANCE VARIABLES (Private for encapsulation)
    // ========================================================================
    
    /**
     * The question text that will be displayed to the student.
     * Example: "What is the time complexity of binary search?"
     */
    private String questionText;
    
    /**
     * Array of 4 answer options for this MCQ.
     * 
     * WHY ARRAY (not ArrayList)?
     * - Fixed size (always 4 options for MCQ)
     * - O(1) access by index
     * - Memory efficient for fixed-size collections
     * - No dynamic resizing needed
     * 
     * Time Complexity for access: O(1)
     */
    private String[] options;
    
    /**
     * The index (0-3) of the correct answer in the options array.
     * Example: If correctOption = 2, then options[2] is the correct answer.
     */
    private int correctOption;
    
    // ========================================================================
    // CONSTRUCTOR
    // ========================================================================
    
    /**
     * Parameterized constructor to create a Question object.
     * 
     * Time Complexity: O(1)
     * Reason: Simple assignment operations, no loops or recursion.
     * 
     * @param questionText  The question to be asked
     * @param options       Array of 4 answer choices
     * @param correctOption Index of the correct answer (0-3)
     */
    public Question(String questionText, String[] options, int correctOption) {
        // Direct assignment - O(1) operation
        this.questionText = questionText;
        this.options = options;
        this.correctOption = correctOption;
    }
    
    // ========================================================================
    // GETTER METHODS (No setters - maintaining immutability)
    // ========================================================================
    
    /**
     * Returns the question text.
     * 
     * Time Complexity: O(1)
     * Reason: Simply returns a reference to the stored string.
     * 
     * @return The question text as a String
     */
    public String getQuestionText() {
        return questionText;
    }
    
    /**
     * Returns the array of answer options.
     * 
     * Time Complexity: O(1)
     * Reason: Returns reference to the options array, no copying.
     * 
     * NOTE FOR INTERVIEWS:
     * In production code, we might want to return a copy of the array
     * to prevent external modification (defensive copying).
     * For this project, we keep it simple for clarity.
     * 
     * @return Array of 4 answer options
     */
    public String[] getOptions() {
        return options;
    }
    
    /**
     * Returns the index of the correct answer.
     * 
     * Time Complexity: O(1)
     * Reason: Returns a primitive int value.
     * 
     * @return Index (0-3) of the correct option
     */
    public int getCorrectOption() {
        return correctOption;
    }
    
    /**
     * Checks if the provided answer index is correct.
     * 
     * Time Complexity: O(1)
     * Reason: Simple integer comparison.
     * 
     * WHY THIS METHOD?
     * - Encapsulates the comparison logic inside the Question class
     * - Follows "Tell, Don't Ask" principle
     * - Caller doesn't need to know internal implementation
     * 
     * @param selectedOption The index of the option selected by the student
     * @return true if the answer is correct, false otherwise
     */
    public boolean isCorrect(int selectedOption) {
        // Simple comparison - O(1)
        return selectedOption == correctOption;
    }
    
    /**
     * Displays the question and all options to the console.
     * 
     * Time Complexity: O(1)
     * Reason: Always prints exactly 4 options (fixed iterations).
     *         Technically O(n) where n = number of options, but since
     *         n is always 4 (constant), it's effectively O(1).
     * 
     * WHY THIS METHOD IN QUESTION CLASS?
     * - Keeps display logic related to Question in the Question class
     * - Alternative: Create a separate QuestionDisplay utility class
     * - For this project scope, keeping it here follows simplicity principle
     */
    public void displayQuestion() {
        // Print the question text
        System.out.println("\n" + questionText);
        
        // Print all options with numbering (1-4 for user-friendly display)
        // Loop runs exactly 4 times - O(1) since it's a constant
        for (int i = 0; i < options.length; i++) {
            // i+1 for 1-based numbering (more intuitive for users)
            System.out.println((i + 1) + ". " + options[i]);
        }
    }
}
