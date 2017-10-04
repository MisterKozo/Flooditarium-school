package xyz.misterkozo.flooditarium;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adhoms on 10/3/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int    DATABASE_VERSION = 1;
    private static final String DATABASE_NAME    = "scoresManager";

    private static final String TABLE_SCORES = "scores";
    private static final String KEY_ID       = "id";
    private static final String KEY_DATE     = "date";
    private static final String KEY_SCORE    = "score";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SCORES_TABLE = "CREATE TABLE " + TABLE_SCORES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT," + KEY_SCORE + " INTEGER" + ")";
        db.execSQL(CREATE_SCORES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
        onCreate(db);
    }

    public void addScore(Score score) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, score.getDate());
        values.put(KEY_SCORE, score.getScore());

        db.insert(TABLE_SCORES, null, values);
        db.close();
    }

    public List<Score> getAllScores() {
        List<Score> scoreList = new ArrayList<Score>();
        String selectQuery = "SELECT * FROM " + TABLE_SCORES + " ORDER BY " + KEY_SCORE + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Score score = new Score();
                score.setId(Integer.parseInt(cursor.getString(0)));
                score.setDate(cursor.getString(1));
                score.setScore(Integer.parseInt(cursor.getString(2)));
                scoreList.add(score);
            } while (cursor.moveToNext());
        }

        return scoreList;
    }

    public void deleteScore(Score score) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SCORES, KEY_ID + " = ?", new String[] { String.valueOf(score.getId()) });
        db.close();
    }

    public void deleteAllScores() {
        List<Score> scores = getAllScores();
        //int[] ids = new int[scores.size()];

        for (int i = 0; i < scores.size(); i++) {
            deleteScore(scores.get(i));
        }
    }
}
