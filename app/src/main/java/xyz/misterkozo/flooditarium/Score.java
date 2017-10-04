package xyz.misterkozo.flooditarium;

/**
 * Created by adhoms on 10/3/17.
 */

public class Score {
    private int id;
    private String date;
    private int score;

    public Score() {
        this.id    = 0;
        this.date  = "";
        this.score = 0;
    }

    public Score(int id, String date, int score) {
        this.id    = id;
        this.date  = date;
        this.score = score;
    }

    public Score(String date, int score) {
        this.date  = date;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
