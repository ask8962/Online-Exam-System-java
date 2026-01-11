import java.util.Scanner;

/**
 * ============================================================================
 * Main.java - Entry point and menu-driven console UI
 * ============================================================================
 * 
 * PURPOSE:
 * This class serves as the entry point for the Online Exam System.
 * It provides a menu-driven console interface for user interaction.
 * 
 * DESIGN PRINCIPLE: Separation of Concerns
 * ─────────────────────────────────────────
 * Main class handles ONLY:
 * - Displaying menu options
 * - Getting user input
 * - Delegating to appropriate Exam methods
 * 
 * Main class does NOT contain:
 * - Business logic (handled by Exam)
 * - Data structures manipulation
 * - Score calculation
 * 
 * WHY THIS DESIGN?
 * 1. Single Responsibility Principle (SRP)
 * 2. Easy to test (Exam logic can be unit tested without UI)
 * 3. Easy to replace UI (could switch to GUI without changing logic)
 * 4. Clean, readable code
 * 
 * INTERVIEW TIP:
 * Interviewers love when you separate UI from business logic.
 * It shows you understand software design principles.
 * 
 * @author Online Exam System
 * @version 1.0
 */
public class Main {

    // ========================================================================
    // STATIC VARIABLES
    // ========================================================================

    /**
     * Scanner for reading user input from console.
     * Static because we need only one instance for entire application.
     * 
     * WHY STATIC?
     * - Created once when class loads
     * - Avoids creating multiple Scanner instances
     * - Resource efficient
     */
    private static Scanner scanner = new Scanner(System.in);

    /**
     * The current logged-in student.
     * null when no student is logged in.
     */
    private static Student currentStudent = null;

    /**
     * The exam system instance handling all business logic.
     */
    private static Exam exam = new Exam();

    // ========================================================================
    // MAIN METHOD - Entry Point
    // ========================================================================

    /**
     * Entry point of the application.
     * Displays welcome message and starts the main menu loop.
     * 
     * Time Complexity: O(1) for each iteration
     * Reason: Menu display and input handling are constant time.
     * Overall complexity depends on user actions.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Display welcome banner
        displayWelcomeBanner();

        // Main application loop
        // Runs until user chooses to exit
        boolean running = true;

        while (running) {
            // Display menu and get user choice
            displayMainMenu();
            int choice = getMenuChoice();

            // Process user choice
            // Using switch for O(1) jump table
            switch (choice) {
                case 1:
                    // Login / Register
                    handleLogin();
                    break;

                case 2:
                    // Take Exam
                    handleTakeExam();
                    break;

                case 3:
                    // View Attempt History
                    handleViewHistory();
                    break;

                case 4:
                    // View Rankings
                    handleViewRankings();
                    break;

                case 5:
                    // Search Student by ID (Binary Search)
                    handleSearchStudent();
                    break;

                case 6:
                    // View All Students
                    handleViewAllStudents();
                    break;

                case 7:
                    // Exit
                    running = false;
                    displayExitMessage();
                    break;

                default:
                    System.out.println("\n⚠ Invalid choice! Please try again.");
            }
        }

        // Close scanner to prevent resource leak
        scanner.close();
    }

    // ========================================================================
    // DISPLAY METHODS
    // ========================================================================

    /**
     * Displays the welcome banner with system information.
     * 
     * Time Complexity: O(1)
     * Reason: Fixed number of print statements.
     */
    private static void displayWelcomeBanner() {
        System.out.println();
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                               ║");
        System.out.println("║           ╔═╗┌┐┌┬  ┬┌┐┌┌─┐  ╔═╗─┐ ┬┌─┐┌┬┐                     ║");
        System.out.println("║           ║ ║││││  ││││├┤   ║╣ ┌┴┬┘├─┤│││                     ║");
        System.out.println("║           ╚═╝┘└┘┴─┘┴┘└┘└─┘  ╚═╝┴ └─┴ ┴┴ ┴                     ║");
        System.out.println("║                                                               ║");
        System.out.println("║        Data Structures & Algorithms Examination System        ║");
        System.out.println("║                                                               ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════╣");
        System.out.println("║                                                               ║");
        System.out.println("║  DATA STRUCTURES IMPLEMENTED:                                 ║");
        System.out.println("║  ┌─────────────────────────────────────────────────────────┐  ║");
        System.out.println("║  │ • Array           → Fixed exam questions                │  ║");
        System.out.println("║  │ • ArrayList       → Dynamic student storage             │  ║");
        System.out.println("║  │ • HashMap         → O(1) student lookup                 │  ║");
        System.out.println("║  │ • LinkedList      → Attempt history tracking            │  ║");
        System.out.println("║  │ • Binary Search Tree → Ranking system                   │  ║");
        System.out.println("║  │ • Binary Search   → Search student by ID                │  ║");
        System.out.println("║  └─────────────────────────────────────────────────────────┘  ║");
        System.out.println("║                                                               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
    }

    /**
     * Displays the main menu options.
     * Shows login status and available actions.
     * 
     * Time Complexity: O(1)
     * Reason: Fixed number of print statements.
     */
    private static void displayMainMenu() {
        System.out.println();
        System.out.println("┌───────────────────────────────────────────────────────────────┐");
        System.out.println("│                       MAIN MENU                               │");
        System.out.println("├───────────────────────────────────────────────────────────────┤");

        // Show current login status
        if (currentStudent != null) {
            System.out.println("│  Logged in as: " +
                    String.format("%-46s", currentStudent.getName()) + "│");
            System.out.println("│  Student ID: " +
                    String.format("%-48d", currentStudent.getId()) + "│");
        } else {
            System.out.println("│  Status: Not logged in                                       │");
        }

        System.out.println("├───────────────────────────────────────────────────────────────┤");
        System.out.println("│  1. Login / Register Student                                  │");
        System.out.println("│  2. Take DSA Exam                                            │");
        System.out.println("│  3. View My Attempt History                                  │");
        System.out.println("│  4. View Ranking List (BST)                                  │");
        System.out.println("│  5. Search Student by ID (Binary Search)                     │");
        System.out.println("│  6. View All Registered Students                             │");
        System.out.println("│  7. Exit                                                      │");
        System.out.println("└───────────────────────────────────────────────────────────────┘");
        System.out.print("\nEnter your choice (1-7): ");
    }

    /**
     * Displays exit message with data structure summary.
     * 
     * Time Complexity: O(1)
     */
    private static void displayExitMessage() {
        System.out.println();
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                   Thank you for using                         ║");
        System.out.println("║            Online DSA Examination System!                     ║");
        System.out.println("║                                                               ║");
        System.out.println("║  TIME COMPLEXITY SUMMARY:                                     ║");
        System.out.println("║  ┌─────────────────────────────────────────────────────────┐  ║");
        System.out.println("║  │ HashMap Lookup     : O(1) average                       │  ║");
        System.out.println("║  │ Binary Search      : O(log n)                           │  ║");
        System.out.println("║  │ BST Insert         : O(log n) average                   │  ║");
        System.out.println("║  │ BST Traversal      : O(n)                               │  ║");
        System.out.println("║  │ LinkedList Add     : O(1)                               │  ║");
        System.out.println("║  │ ArrayList Add      : O(1) amortized                     │  ║");
        System.out.println("║  └─────────────────────────────────────────────────────────┘  ║");
        System.out.println("║                                                               ║");
        System.out.println("║                    Goodbye! 👋                                ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
    }

    // ========================================================================
    // INPUT HANDLING METHODS
    // ========================================================================

    /**
     * Gets a valid menu choice (1-7) from the user.
     * 
     * Time Complexity: O(k) where k = number of invalid inputs
     * Best case O(1) if first input is valid.
     * 
     * @return Valid integer between 1 and 7
     */
    private static int getMenuChoice() {
        int choice = -1;

        try {
            String input = scanner.nextLine().trim();
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            // Invalid input, will return -1
        }

        return choice;
    }

    // ========================================================================
    // HANDLER METHODS (Delegate to Exam class)
    // ========================================================================

    /**
     * Handles student login/registration.
     * Gets student ID and name, creates or retrieves student.
     * 
     * Time Complexity: O(1) average
     * Reason: Uses HashMap for O(1) lookup.
     * 
     * WORKFLOW:
     * 1. Prompt for student ID
     * 2. Prompt for student name
     * 3. Call exam.registerOrLoginStudent()
     * 4. Set current student
     */
    private static void handleLogin() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                  STUDENT LOGIN / REGISTER                     ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");

        // Get student ID
        System.out.print("Enter Student ID (numeric, e.g., 101): ");
        int id = getValidId();

        // Get student name
        System.out.print("Enter your name: ");
        String name = scanner.nextLine().trim();

        // Validate name
        if (name.isEmpty()) {
            name = "Student" + id;
            System.out.println("(Using default name: " + name + ")");
        }

        // Register or login - O(1) using HashMap
        currentStudent = exam.registerOrLoginStudent(id, name);

        // Display attempt count from LinkedList
        System.out.println("Total exam attempts: " + currentStudent.getAttemptCount());
    }

    /**
     * Handles taking the exam.
     * Requires student to be logged in first.
     * 
     * Time Complexity: O(q) where q = number of questions
     * Plus O(log n) for BST insert at the end.
     */
    private static void handleTakeExam() {
        // Check if logged in
        if (currentStudent == null) {
            System.out.println("\n⚠ Please login first! (Option 1)");
            return;
        }

        // Conduct exam - O(q) questions + O(log n) BST insert
        exam.conductExam(currentStudent, scanner);
    }

    /**
     * Handles viewing attempt history.
     * Uses LinkedList traversal.
     * 
     * Time Complexity: O(k) where k = number of attempts
     * Reason: LinkedList traversal for displaying history.
     */
    private static void handleViewHistory() {
        // Check if logged in
        if (currentStudent == null) {
            System.out.println("\n⚠ Please login first! (Option 1)");
            return;
        }

        // Display history - O(k) LinkedList traversal
        exam.displayAttemptHistory(currentStudent);

        // Also show average
        double avg = currentStudent.getAverageScore();
        System.out.printf("Average Score: %.2f marks%n", avg);
    }

    /**
     * Handles displaying rankings using BST.
     * 
     * Time Complexity: O(n)
     * Reason: In-order BST traversal visits all nodes.
     * 
     * WHY BST FOR THIS?
     * - Reverse in-order gives descending order automatically
     * - No need to sort after each insertion
     */
    private static void handleViewRankings() {
        System.out.println("\n╭───────────────────────────────────────────────────────────────╮");
        System.out.println("│  RANKING SYSTEM (Binary Search Tree - Reverse In-Order)       │");
        System.out.println("│  Time Complexity: O(n) for traversal                          │");
        System.out.println("╰───────────────────────────────────────────────────────────────╯");

        // Display rankings - O(n) BST traversal
        exam.displayRankings();
    }

    /**
     * Handles searching for a student by ID using Binary Search.
     * First sorts the ArrayList, then performs binary search.
     * 
     * Time Complexity: O(n log n) for sorting + O(log n) for search
     * 
     * WHY BINARY SEARCH?
     * - Demonstrates the algorithm (project requirement)
     * - O(log n) faster than O(n) linear search for large datasets
     * 
     * INTERVIEW DISCUSSION:
     * In practice, HashMap lookup (O(1)) is faster.
     * Binary Search is useful when:
     * - Data is already sorted
     * - Memory is limited (no HashMap)
     * - Range queries needed
     */
    private static void handleSearchStudent() {
        System.out.println("\n╭───────────────────────────────────────────────────────────────╮");
        System.out.println("│  BINARY SEARCH - Search Student by ID                         │");
        System.out.println("│  Time Complexity: O(n log n) sort + O(log n) search           │");
        System.out.println("╰───────────────────────────────────────────────────────────────╯");

        // Check if any students exist
        if (exam.hasNoStudents()) {
            System.out.println("\n⚠ No students registered yet!");
            return;
        }

        // Get ID to search
        System.out.print("\nEnter Student ID to search: ");
        int searchId = getValidId();

        // Perform binary search - O(n log n) + O(log n)
        System.out.println("\n[Binary Search Algorithm]");
        System.out.println("Step 1: Sorting ArrayList by ID... O(n log n)");
        System.out.println("Step 2: Performing Binary Search... O(log n)");

        Student found = exam.binarySearchStudentById(searchId);

        // Display result
        if (found != null) {
            System.out.println("\n✓ Student Found!");
            System.out.println("────────────────────────────────────────");
            System.out.println("  ID    : " + found.getId());
            System.out.println("  Name  : " + found.getName());
            System.out.println("  Score : " + found.getScore());
            System.out.println("  Attempts: " + found.getAttemptCount());
            System.out.println("────────────────────────────────────────");
        } else {
            System.out.println("\n✗ Student with ID " + searchId + " not found!");
        }

        // Compare with HashMap lookup
        System.out.println("\n[Comparison with HashMap Lookup - O(1)]");
        Student hashResult = exam.findStudentById(searchId);
        System.out.println("HashMap lookup result: " +
                (hashResult != null ? "Found - " + hashResult.getName() : "Not found"));
    }

    /**
     * Handles displaying all registered students.
     * 
     * Time Complexity: O(n)
     * Reason: ArrayList traversal.
     */
    private static void handleViewAllStudents() {
        // O(n) ArrayList traversal
        exam.displayAllStudents();
    }

    /**
     * Gets a valid positive integer ID from user input.
     * 
     * Time Complexity: O(k) where k = number of invalid inputs
     * 
     * @return Valid positive integer ID
     */
    private static int getValidId() {
        int id = -1;

        while (id <= 0) {
            try {
                String input = scanner.nextLine().trim();
                id = Integer.parseInt(input);
                if (id <= 0) {
                    System.out.print("Please enter a positive number: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input! Please enter a number: ");
            }
        }

        return id;
    }
}
