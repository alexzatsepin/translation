package lingualeo.mydictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

import lingualeo.mydictionary.domain.Word;

/**
 * Created by Zatsepin on 13.08.2014.
 */
public class WordsDao {
    private final static String[] ALL_COLUMNS =
            {TranslationSqliteHelper.COLUMN_ID,
                    TranslationSqliteHelper.COLUMN_TEXT,
                    TranslationSqliteHelper.COLUMN_TRANSLATION};
    private SQLiteDatabase mDatabase;
    private final TranslationSqliteHelper mSqlHelper;

    public WordsDao(Context context) {
        this.mSqlHelper = new TranslationSqliteHelper(context);
    }

    public void open(){
        mDatabase = mSqlHelper.getWritableDatabase();
    }

    public void close(){
        mSqlHelper.close();
    }

    public void saveWord(Word word){
        ContentValues values = new ContentValues();
        values.put(TranslationSqliteHelper.COLUMN_TEXT, word.getText());
        values.put(TranslationSqliteHelper.COLUMN_TRANSLATION, word.getTranslation());
        mDatabase.insert(TranslationSqliteHelper.TABLE_WORDS, null, values);
    }

    public List<Word> getAllWords(){
        List<Word> words = new ArrayList<Word>();
        Cursor cursor = mDatabase.query(TranslationSqliteHelper.TABLE_WORDS, ALL_COLUMNS,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Word word = cursorToWord(cursor);
            words.add(word);
            cursor.moveToNext();
        }
        cursor.close();
        return words;
    }

    private static Word cursorToWord(Cursor cursor){
        Word word = new Word();
        word.setText(cursor.getString(1));
        word.setTranslation(cursor.getString(2));
        return word;
    };
}
