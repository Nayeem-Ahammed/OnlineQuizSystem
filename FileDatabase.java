
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
 * 
 */
public class FileDatabase {
    private static final String USERS_FILE = "users.txt";
    private static final String QUIZZES_FILE = "quizzes.txt";

    // save user to file
    public void saveUser(User user) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))){
            writer.write(user.getUsername() + "," + user.getPassword() + "," + user.getRole());
            writer.newLine();
        } 
    }

    // Load all users from file
    public List<User> loadUsers() throws IOException {
        List<User> users = new ArrayList<>();
        File file = new File(USERS_FILE);

        if(!file.exists()) {
            return users;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts.length == 3) {
                    users.add(new User(parts[0], parts[1], parts[2]));
                }
            }
        }

        return users;
    }

    // Save quiz to file 
    public void saveQuiz(Quiz quiz) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(QUIZZES_FILE, true))) {
            // format : quizId|title|question1_text;option1;option2;correctIndex|question2_text;...
            StringBuilder sb = new StringBuilder();
            sb.append(quiz.getQuizId()).append("|").append(quiz.getTitle()).append("|");

            for(Question q : quiz.getQuestion()) {
                sb.append(q.getQuestionText()).append(";");
                sb.append(String.join(",", q.getOptions())).append(";");
                sb.append(q.getCorrectAnswerIndex()).append("|");
            }

            writer.write(sb.toString());
            writer.newLine();
        }
    }


    // Load all quizzes 
    public List <Quiz> loadQuizs() throws IOException {
        List <Quiz> quizzes = new ArrayList<>();
        File file = new File(QUIZZES_FILE);

        if(!file.exists()) {
            return quizzes;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(QUIZZES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if(parts.length >= 3) {
                    String quizId = parts[0];
                    String title = parts[1];
                    List<Question> questions = new ArrayList<>();

                    for(int i = 2; i < parts.length; i++) {
                        String[] qParts = parts[i].split(";");
                        if(qParts.length == 3) {
                            String qText = qParts[0];
                            List <String> options = List.of(qParts[1].split(","));
                            int correctIndex = Integer.parseInt(qParts[2]);
                            questions.add(new Question(qText, options, correctIndex));
                        }
                    }

                    quizzes.add(new Quiz(quizId, title, questions));
                }
            }
        }

        return quizzes;
    }


}
