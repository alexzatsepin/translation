package lingualeo.mydictionary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class NewWordActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        final EditText wordEditText = (EditText)findViewById(R.id.word);
        Button doneBtn = (Button)findViewById(R.id.done);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = wordEditText.getText().toString().trim();
                if (TextUtils.isEmpty(word)){
                    Toast.makeText(NewWordActivity.this, "Type the word, please!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(NewWordActivity.this, TranslationService.class);
                intent.putExtra(TranslationService.EXTRA_TEXT, word);
                startService(intent);
            }
        });
        Button showAllBtn = (Button)findViewById(R.id.show_all);
        showAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewWordActivity.this, AllWordsActivity.class);
                startActivity(intent);
            }
        });
    }

}
