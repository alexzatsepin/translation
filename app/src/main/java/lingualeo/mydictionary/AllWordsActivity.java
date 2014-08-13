package lingualeo.mydictionary;

import android.app.Activity;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import lingualeo.mydictionary.R;
import lingualeo.mydictionary.domain.Word;

public class AllWordsActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_words);
        final EditText searchView = (EditText)findViewById(R.id.search);
        Button goSearchBtn = (Button)findViewById(R.id.go);
        goSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchString = searchView.getText().toString().trim();
                ((ArrayAdapter<Word>)getListAdapter()).getFilter().filter(searchString);
            }
        });
        new LoadWordsTask(this).execute();
    }

    private void onLoadWords(List<Word> words){
        setListAdapter(new WordsAdapter(this, R.layout.listview_item, words));
    }


    private static class LoadWordsTask extends AsyncTask<Void, Void, List<Word>>{
        private final WeakReference<AllWordsActivity> mActivityRef;

        private LoadWordsTask(AllWordsActivity activity) {
            this.mActivityRef = new WeakReference<AllWordsActivity>(activity);
        }

        @Override
        protected List<Word> doInBackground(Void... params) {
            List<Word> result = new ArrayList<Word>();
            if(mActivityRef.get() != null){
                Activity activity = mActivityRef.get();
                WordsDataSource dataSource = new WordsDataSource(activity.getApplicationContext());
                dataSource.open();
                result.addAll(dataSource.getAllWords());
                dataSource.close();
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<Word> words) {
            if (mActivityRef.get() != null){
                mActivityRef.get().onLoadWords(words);
            }
        }
    }
}
