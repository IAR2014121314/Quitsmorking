package com.jsnk77.quitsmoking;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by jsnk77 on 14/12/13.
 */
public class FriendActivity extends Activity {

    @InjectView(R.id.friend_image)
    ImageView mFriendImage;
    @InjectView(R.id.friend_birthday)
    TextView mFriendBirthday;
    @InjectView(R.id.friend_name)
    TextView mFriendName;
    @InjectView(R.id.friend_total_count)
    TextView mFriendTotalCount;
    @InjectView(R.id.friend_gender)
    TextView mFriendGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        ButterKnife.inject(this);


    }
}
