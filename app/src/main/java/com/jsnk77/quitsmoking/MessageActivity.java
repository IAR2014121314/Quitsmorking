package com.jsnk77.quitsmoking;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by yuki on 2014/12/13.
 */
public class MessageActivity extends Activity {
    @InjectView(R.id.textView)
    TextView mTextView;
    @InjectView(R.id.listView)
    ListView mListView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.inject(this);

        

    }
}
