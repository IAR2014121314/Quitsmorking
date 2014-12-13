package com.jsnk77.quitsmoking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by jsnk77 on 14/12/13.
 */
public class HomeActivity extends Activity {


    @InjectView(R.id.usericon)
    ImageView mUsericon;
    @InjectView(R.id.username)
    TextView mUsername;
    @InjectView(R.id.Birthday)
    TextView mBirthday;
    @InjectView(R.id.gender)
    TextView mGender;
    @InjectView(R.id.totalcount)
    TextView mTotalcount;
    @InjectView(R.id.imageView)
    ImageView mImageView;
    @InjectView(R.id.friendlist)
    ListView mFriendlist;
    @InjectView(R.id.option)
    ImageButton mOption;
    @InjectView(R.id.imageButton)
    ImageButton mImageButton;
    private Intent intent;
    private String fbId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);

        Toast.makeText(HomeActivity.this, "ホームへようこそ", Toast.LENGTH_LONG).show();



        intent = getIntent();
        fbId = intent.getStringExtra("fbId");


        ImageLoader imageLoader = ApplicationControler.getInstance().getImageLoader();
        ImageLoader.ImageListener imageListener = imageLoader.getImageListener(mUsericon, R.drawable.ic_launcher, R.drawable.ic_launcher);
        imageLoader.get("https://graph.facebook.com/"+fbId+"/picture",imageListener);



//        Intent i = new Intent(this,ShareActivity.class);
//        startActivity(i);


    }
}
