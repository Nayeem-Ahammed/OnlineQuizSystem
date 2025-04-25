
import java.io.IOException;
import java.util.*;

public class QuizManager {
    private FileDatabase database;
    private List <Quiz> quizzes;

    public QuizManager() {
        this.database = new FileDatabase();
        try {
            this.quizzes = database.loadQuizs();
        } catch (IOException e) {
            System.out.println("Error loading quizzes: " + e.getMessage());
        }
    }

    // Create a new quiz
    public void CreateQuiz(Quiz quiz) {
        quizzes.add(quiz);
        try {
            database.saveQuiz(quiz);
        } catch (IOException e) {
            System.out.println("Error saving quiz: " + e.getMessage());
        }
    }


    // Take a quiz (with return score)
    public int takeQuiz(String quizId, Scanner input) {
        Quiz quiz = quizzes.stream()
            .filter((q -> q.getQuizId().equals(quizId)))
            .findFirst()
            .orElse(null);

        if(quiz == null) {
            System.out.println("Quiz not found!");
            return -1;
        }

        System.out.println("\n--- Quiz: " + quiz.getTitle() + " ---");

        List <Integer> userAnswer = new ArrayList<>();

        for(Question question : quiz.getQuestion()) {
            System.out.println("\nQ: " + question.getQuestionText());
            List<String> options = question.getOptions();

            for(int i = 0; i < options.size(); i++) {
                System.out.println((i+1) + ". " + options.get(i));
            }

            System.out.println("Your answer (1-" + options.size() + "): ");
            int answer = input.nextInt();
            userAnswer.add(answer-1);
        }

        return calculateScore(quiz, userAnswer);
    }

    // Calculate Score
    private int calculateScore(Quiz quiz, List<Integer> userAnswer) {
        int score = 0;
        List <Question> questions = quiz.getQuestion();

        for(int i = 0; i < questions.size(); i++) {
            if(userAnswer.get(i) == questions.get(i).getCorrectAnswerIndex()) {
                score++;
            }
        }

        return score;
    }

    // Get all quizzes
    public List <Quiz> getQuezzes() {
        return quizzes;
    }

}
