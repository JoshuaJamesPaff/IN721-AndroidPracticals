package nz.ac.op.paffjj1student.languagequiz;

/**
 * Created by Mr Nobody on 29/03/2016.
 */
public class Question {
    private int imgID;
    private String noun;
    private String answer;

    public Question(int imgID, String noun, String answer){

        this.setImgID(imgID);
        this.setNoun(noun);
        this.setAnswer(answer);
    }

    public int getImgID() {
        return imgID;
    }

    public void setImgID(int imgID) {
        this.imgID = imgID;
    }

    public String getNoun() {
        return noun;
    }

    public void setNoun(String noun) {
        this.noun = noun;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
