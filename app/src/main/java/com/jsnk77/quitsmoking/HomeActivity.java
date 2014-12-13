package com.jsnk77.quitsmoking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
    private String fbName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);

        Toast.makeText(HomeActivity.this, "ホームへようこそ", Toast.LENGTH_LONG).show();



        intent = getIntent();
        fbId = intent.getStringExtra("fbId");
        fbName = intent.getStringExtra("fbName");


        ImageLoader imageLoader = ApplicationControler.getInstance().getImageLoader();
        ImageLoader.ImageListener imageListener = imageLoader.getImageListener(mUsericon, R.drawable.ic_launcher, R.drawable.ic_launcher);
        imageLoader.get("https://graph.facebook.com/"+fbId+"/picture",imageListener);

        mUsername.setText(fbName);



//        Intent i = new Intent(this,ShareActivity.class);
//        startActivity(i);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent = new Intent();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        } else*/ if (id == R.id.action_home) {
            //Toast.makeText(this, "Main Page selected", Toast.LENGTH_LONG).show();
            intent.setClassName("com.jsnk77.quitsmoking", "com.jsnk77.quitsmoking.HomeActivity");
            //intent.putExtra("Goal", "");
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.action_profile) {
            //Toast.makeText(this, "profile selected", Toast.LENGTH_LONG).show();
            intent.setClassName("com.jsnk77.quitsmoking", "com.jsnk77.quitsmoking.ProfileActivity");
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.action_friend) {
            //Toast.makeText(this, "ranking selected", Toast.LENGTH_LONG).show();
            intent.setClassName("com.jsnk77.quitsmoking", "com.jsnk77.quitsmoking.FriendActivity");
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.action_message) {
            //Toast.makeText(this, "facebook selected", Toast.LENGTH_LONG).show();
            intent.setClassName("com.jsnk77.quitsmoking", "com.jsnk77.quitsmoking.MessageActivity");
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
