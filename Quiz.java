/*
 * 
 */
import java.util.List;

public class Quiz {
    private String quizId, title;
    private List<Question> questions;

    // setters
    public Quiz(String quizId, String title, List<Question> questions) {
        this.quizId = quizId;
        this.title = title;
        this.questions = questions;
    }

    // getters
    public String getQuizId() {
        return quizId;
    }
    public String getTitle() {
        return title;
    }
    public List<Question> getQuestion() {
        return questions;
    }
    
}
