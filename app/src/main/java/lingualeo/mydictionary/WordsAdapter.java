package lingualeo.mydictionary;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lingualeo.mydictionary.domain.Word;

/**
 * Created by Zatsepin on 14.08.2014.
 */
public class WordsAdapter extends ArrayAdapter<Word> {

    private final Context mContext;
    private final int mLayoutResource;
    private final List<Word> mInitialWords = new ArrayList<Word>();
    private final List<Word> mDataSet;
    private final List<Word> mSuggestions = new ArrayList<Word>();

    private final Filter mFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (!TextUtils.isEmpty(constraint)){
                mSuggestions.clear();
                for(Word word: mDataSet){
                    String text = word.getText().toLowerCase();
                    String translation = word.getTranslation().toLowerCase();
                    String constr = constraint.toString().toLowerCase();
                    if (text.startsWith(constr) || translation.startsWith(constr)){
                        mSuggestions.add(word);
                    }
                }
                FilterResults results = new FilterResults();
                results.values = mSuggestions;
                results.count = mSuggestions.size();
                return results;
            }else{
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<Word> suggestions = (List<Word>)results.values;
            if (results != null && results.count > 0){
                clear();
                mDataSet.addAll(suggestions);
            }else{
                mDataSet.addAll(mInitialWords);
            }
            notifyDataSetChanged();
        }
    };

    public WordsAdapter(Context context, int resource, List<Word> words) {
        super(context, resource, words);
        mContext = context;
        mLayoutResource = resource;
        mDataSet = words;
        mInitialWords.addAll(words);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WordViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(mLayoutResource, parent, false);

            holder = new WordViewHolder();
            holder.mText = (TextView) convertView.findViewById(R.id.text);
            holder.mTranslate = (TextView) convertView.findViewById(R.id.translation);
            convertView.setTag(holder);
        } else {
            holder = (WordViewHolder)convertView.getTag();
        }

        Word word = getItem(position);
        holder.mText.setText(word.getText());
        holder.mTranslate.setText(word.getTranslation());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    static class WordViewHolder {
        TextView mText;
        TextView mTranslate;
    }
}
