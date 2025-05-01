
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        UserManager userManager = new UserManager();
        QuizManager quizManager = new QuizManager();

        while (true) { 
            System.out.println("\n=== Online Quiz System ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = input.nextInt();
            input.nextLine(); // newline

            switch (choice) {
                case 1:
                    registerUser(userManager, input);
                    break;
                case 2:
                    loginUser(userManager, quizManager, input);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // Register User
    private static void registerUser (UserManager userManager, Scanner input) {
        System.out.println("Enter username: ");
        String username = input.nextLine();
        System.out.println("Enter password: ");
        String password = input.nextLine();
        System.out.println("Enter role (admin/teacher/student): ");
        String role = input.nextLine();

        if(userManager.register(new User(username, password, role))) {
            System.out.println("Registration successful!");
        }
        else {
            System.out.println("Registration failed.");
        }
    }

    // Login User
    private static void loginUser (UserManager userManager, QuizManager QuizManager, Scanner input) {
        System.out.println("Username: ");
        String username = input.nextLine();
        System.out.println("Password: ");
        String password = input.nextLine();

        User user = userManager.login(username, password);
        if(user == null) {
            System.out.println("Invalid Username or Password");
            return;
        }
        
        System.out.println("\nWelcome, " + user.getUsername() + " (" + user.getRole() + ")");
        if(user.getRole().equals("admin")) {
            adminMenu(QuizManager, input);
        }
        else if(user.getRole().equals("student")) {
            studentMenu(QuizManager, input);
        }
        else if(user.getRole().equals("teacher")) {
            // not now build
            System.out.println("Updating...\nTry \"admin\".");
            return;
        }
    }


    // #################### Admin Menu #########################
    private static void adminMenu (QuizManager quizManager, Scanner input) {
        while (true) { 
            System.out.println("\n --- Admin Menu ---");
            System.out.println("1. Create Quiz");
            System.out.println("2. View Quizzes");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    CreateQuiz(quizManager, input);
                    break;
                case 2:
                    viewQuizzes(quizManager);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // Create Quiz
    private static void CreateQuiz (QuizManager quizManager, Scanner input) {
        System.out.print("Enter Quiz ID: ");
        String quizId = input.nextLine();
        System.out.print("Enter Quiz Title: ");
        String title = input.nextLine();

        List <Question> question = new ArrayList<>();

        while (true) { 
            System.out.print("Add a question (or type 'done' to finish): ");
            String questionText = input.nextLine();

            if(questionText.equalsIgnoreCase("done")) break;

            List <String> option = new ArrayList<>();
            for(int i = 1; i <= 4; i++) {
                System.out.print("Option " + i + ": ");
                option.add(input.nextLine());
            }

            System.out.print("Correct option (1-4): ");
            int correctIndex = input.nextInt() - 1;
            input.nextLine(); // consume newline

            question.add(new Question(questionText, option, correctIndex));
        }

        quizManager.CreateQuiz(new Quiz(quizId, title, question));
        System.out.println("Quiz created successfully!");

    }


    // view quiz
    private static void viewQuizzes (QuizManager quizManager) {
        List <Quiz> quizzes = quizManager.getQuezzes();
        if(quizzes.isEmpty()) {
            System.out.println("No quezzes available.");
            return;
        }

        System.out.println("\nAvailable Quizzes: ");
        for(Quiz quiz : quizzes) {
            System.out.println("-> " + quiz.getQuizId() + ": " + quiz.getTitle());
        }
    }




    //############## Student Menu #############################
    private static void studentMenu (QuizManager quizManager, Scanner input){
        while (true) { 
            System.out.println("\n --- Student Menu ---");
            System.out.println("1. Take Quiz");
            System.out.println("2. Logout");
            System.out.print("Choose an option: ");
            
            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    takeQuiz(quizManager, input);
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    private static void takeQuiz (QuizManager quizManager, Scanner input) {
        viewQuizzes(quizManager);
        System.out.print("Enter Quiz ID to Take: ");
        String quizId = input.nextLine();

        int score = quizManager.takeQuiz(quizId, input);
        if(score != -1) {
            System.out.println("\nYOur score: " + score + "/" + 
                quizManager.getQuezzes().stream()
                    .filter(q -> q.getQuizId().equals(quizId))
                    .findFirst()
                    .get()
                    .getQuestion()
                    .size());
        }
    }
    // Updating....
    private static void teacherMenu (QuizManager quizManager, Scanner input) {
        /*
         *  --- Teacher Menu ---
         * 1. Create Quiz
         * 2. View My Quizzes
         * 3. Delete Quiz
         * 4. Logout
         */
    }
}
