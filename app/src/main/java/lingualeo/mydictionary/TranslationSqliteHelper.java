package lingualeo.mydictionary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Zatsepin on 13.08.2014.
 */
public class TranslationSqliteHelper extends SQLiteOpenHelper {
    static final String TABLE_WORDS = "words";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_TEXT = "text";
    static final String COLUMN_TRANSLATION = "translation";
    private static final String DATABASE_NAME = "dictionary.db";
    private static final int DB_VERSION = 1;
    private static final String CREATE_DB_SQL = "create table "
            + TABLE_WORDS + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TEXT + " text not null, "
            + COLUMN_TRANSLATION + " text not null);";

    public TranslationSqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //empty
    }
}
