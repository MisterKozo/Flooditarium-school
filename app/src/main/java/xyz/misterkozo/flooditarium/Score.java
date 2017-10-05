package xyz.misterkozo.flooditarium;

/**
 * Created by adhoms on 10/3/17.
 */

public class Score {
    private int id;
    private String date;
    private int score;
    private String player;
    private String seed;

    public Score() {
        this.id     = 0;
        this.date   = "";
        this.score  = 0;
        this.player = "";
        this.seed   = "";
    }

    public Score(int id, String date, int score, String player, String seed) {
        this.id     = id;
        this.date   = date;
        this.score  = score;
        this.player = player;
        this.seed   = seed;
    }

    public Score(String date, int score, String player, String seed) {
        this.date  = date;
        this.score = score;
        this.player = player;
        this.seed   = seed;
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

    public String getPlayer() { return this.player; }

    public void setPlayer(String player) { this.player = player; }

    public String getSeed() { return this.seed; }

    public void setSeed(String seed) { this.seed = seed; }
}
