package nz.ac.op.paffjj1student.languagequiz;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mr Nobody on 29/03/2016.
 */
public class QuestionManager {

    private ArrayList<Question> questions;

    private int currentQuestion;
    private int score;

    //sets score, questions and then shuffles the questions
    public QuestionManager(){
        score = 0;
        currentQuestion = 1;
        questions = new ArrayList<>();
        setQuestions();
        shuffleQuestions();

    }

    //sets data for questions
    public void setQuestions(){
        questions.add(new Question(R.drawable.das_auto, "Car", "das"));
        questions.add(new Question(R.drawable.das_haus, "House", "das"));
        questions.add(new Question(R.drawable.das_schaf, "Sheep", "das"));
        questions.add(new Question(R.drawable.der_apfel, "Apple", "der"));
        questions.add(new Question(R.drawable.der_baum, "Tree", "der"));
        questions.add(new Question(R.drawable.der_stuhl, "Stool", "der"));
        questions.add(new Question(R.drawable.die_ente, "Duck", "die"));
        questions.add(new Question(R.drawable.die_hexe, "Witch", "die"));
        questions.add(new Question(R.drawable.die_kuh, "Cow", "die"));
        questions.add(new Question(R.drawable.die_milch, "Milk", "die"));
        questions.add(new Question(R.drawable.die_strasse, "Street", "die"));
    }

    //gets current question object
    public Question getQuestion(){
        //minus one because the question count starts at 1 while the index starts at 0
        return questions.get(currentQuestion-1);
    }

    //swaps 2 random questions 100 times shuffling the questions
    public void shuffleQuestions(){

        int length = questions.size();
        Random randGenerator = new Random();
        int swapCount = 0;
        Question temp;

        while(swapCount < 100){
            int q1 = randGenerator.nextInt(length);
            int q2 = randGenerator.nextInt(length);
            temp = questions.get(q1);
            questions.set(q1, questions.get(q2));
            questions.set(q2, temp);
        }
    }

    public boolean checkQuestion(String guess){
        Question temp = getQuestion();

        //if questions is right increment score and question number
        if (temp.getAnswer().equals(guess)){
            currentQuestion += 1;
            score += 1;
            return true;

        }else{
            currentQuestion += 1;
            return false;
        }
    }

    //getter setter
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }


}
