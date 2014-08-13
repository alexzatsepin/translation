package lingualeo.mydictionary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class NewWordActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        final EditText wordEditText = (EditText)findViewById(R.id.word);
        final Button doneBtn = (Button)findViewById(R.id.done);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewWordActivity.this, TranslationService.class);
                String word = wordEditText.getText().toString();
                intent.putExtra(TranslationService.EXTRA_TEXT, word);
                startService(intent);
            }
        });
    }

}
