package com.jsnk77.quitsmoking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnTouch;


/**
 * Created by jsnk77 on 14/12/13.
 */
public class HomeActivity extends Activity {

    private MobileServiceClient mClient;

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
    @InjectView(R.id.shareOnFacebook)
    ImageButton mShareOnFacebook;
    @InjectView(R.id.imageButton)
    ImageButton mImageButton;
    @InjectView(R.id.imageButtonTabacco)
    ImageButton mImageButtonTabacco;
    @InjectView(R.id.tabaccoToday)
    TextView mTabaccoToday;

    private Intent intent;
    private String fbId;
    private String fbName;

    private int targetLocalX;
    private int targetLocalY;
    private int screenX;
    private int screenY;
    private int defLeft;
    private int defTop;

    int incrementNum = 0;

    int goalTabaccoFromProfile;

    ArrayList<FriendList> users;

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
        imageLoader.get("https://graph.facebook.com/" + fbId + "/picture", imageListener);

        mUsername.setText(fbName);

        try {
            mClient = new MobileServiceClient(
                    "https://quitsmokingavatar.azure-mobile.net/",
                    "oBosiqBqMTabSBiLzeCmUlAucgoiOX80",
                    this
            );
        } catch (Throwable e) {
            e.printStackTrace();
        }

        intent = getIntent();
        goalTabaccoFromProfile = intent.getIntExtra("GoalTabacco", 0);

//        Intent i = new Intent(this,ShareActivity.class);
//        startActivity(i);

        //ListView setup
        users = new ArrayList<FriendList>();

        User u1 = new User();

        u1.friendname = "YUJI";
        u1.cigarCount = "20";
        users.add(u1);
        User u2 = new User();
        u2.name = "Haru";
        u2.cigarCount = "21";
        users.add(u2);

        //set ListAdapter
        FriendListAdapter testListAdapter = new FriendListAdapter(this, R.layout.activity_friendlistitem, users);
        mFriendlist.setAdapter(testListAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @OnClick(R.id.shareOnFacebook)
    public void shareOnFacebook() {
        Intent intent = new Intent(HomeActivity.this, ShareActivity.class);
        startActivity(intent);
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
        } else*/
        if (id == R.id.action_home) {
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

    @OnTouch(R.id.imageButtonTabacco)
    public boolean touchTabacco(View v, MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                targetLocalX = defLeft = mImageButtonTabacco.getLeft();
                targetLocalY = defTop = mImageButtonTabacco.getTop();
                screenX = x;
                screenY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int diffX = screenX - x;
                int diffY = screenY - y;
                targetLocalX -= diffX;
                targetLocalY -= diffY;
                mImageButtonTabacco.layout(targetLocalX, targetLocalY, targetLocalX + mImageButtonTabacco.getWidth(), targetLocalY + mImageButtonTabacco.getHeight());
                screenX = x;
                screenY = y;
                break;
            case MotionEvent.ACTION_UP:
                mImageButtonTabacco.setVisibility(View.INVISIBLE);

                CountDownTimer cdt = new CountDownTimer(5000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        mImageButtonTabacco.layout(defLeft, defTop, defLeft + mImageButtonTabacco.getWidth(), defTop + mImageButtonTabacco.getHeight());
                        mImageButtonTabacco.setVisibility(View.VISIBLE);
                    }
                }.start();

                Tabacco tabacco = new Tabacco();
                tabacco.FbId = fbId;
                tabacco.Latitude = "";
                tabacco.Longitude = "";
                incrementNum++;
                tabacco.SmokeCount = incrementNum;

                mClient.getTable(Tabacco.class).insert(tabacco, new TableOperationCallback<Tabacco>() {
                    public void onCompleted(Tabacco entity, Exception exception, ServiceFilterResponse response) {
                        if (exception == null) {
                            // Insert succeeded
                            Toast.makeText(HomeActivity.this, "Succeeded!", Toast.LENGTH_LONG).show();
                            getData();
                        } else {
                            // Insert failed
                            Toast.makeText(HomeActivity.this, "Failed!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
        }
        return true;
    }

    public void getData() {
        MobileServiceTable<Tabacco> tabacco = mClient.getTable(Tabacco.class);
        MobileServiceTable<Profile> profile = mClient.getTable(Profile.class);

        tabacco.where().field("FbId").eq(fbId).select("SmokeCount").execute(new TableQueryCallback<Tabacco>() {
            @Override
            public void onCompleted(List<Tabacco> result, int count, Exception exception, ServiceFilterResponse response) {
                int total = 0;
                for (Tabacco i : result) {

                    total += i.SmokeCount;
                }
                final int finalTotal = total;
                HomeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mTotalcount.setText("今までに吸ったタバコの総本数 : " + Integer.toString(finalTotal) + " 本");
                    }
                });
            }
        });

        tabacco.where().day("_createdAt").eq(13).select("SmokeCount").execute(new TableQueryCallback<Tabacco>() {
            @Override
            public void onCompleted(List<Tabacco> result, int count, Exception exception, ServiceFilterResponse response) {
                int totalToday = 0;
                for (Tabacco i : result) {
                    totalToday += i.SmokeCount;
                }
                final int finalTotalToday = totalToday;
                HomeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTabaccoToday.setText(finalTotalToday + " / " + goalTabaccoFromProfile + " 本");
                    }
                });
            }
        });
    }
    //Friends list onclick event.
    @OnItemClick(R.id.friendlist)
    public void friendClick(int position) {
        User user = (User) mFriendlist.getItemAtPosition(position);
        Intent intent = new Intent(this, FriendActivity.class);
        startActivity(intent);
        Toast.makeText(this, user.name, Toast.LENGTH_SHORT).show();

    }

    public static class FriendListAdapter extends ArrayAdapter<User> {

        Context mContext;
        int mResource;

        public FriendListAdapter(Context context, int resource, List<User> objects) {
            super(context, resource, objects);
            this.mContext = context;
            this.mResource = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView != null) {
                holder = (ViewHolder) convertView.getTag();
            } else {
                convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }

            User item = this.getItem(position);
//            holder.mIcon.setImageDrawable(item.icon);
            holder.mFriendName.setText(item.friend_name);
            holder.mFriendTotalCount.setText(item.friend_tatal_count);

            // etc...

            return convertView;
        }

        static class ViewHolder {
            @InjectView(R.id.friend_image)
            ImageView mFriendImage;
            @InjectView(R.id.friend_birthday)
            TextView mFriendBirthday;
            @InjectView(R.id.friend_name)
            TextView mFriendName;
            @InjectView(R.id.friend_total_count)
            TextView mFriendTotalCount;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }
}
