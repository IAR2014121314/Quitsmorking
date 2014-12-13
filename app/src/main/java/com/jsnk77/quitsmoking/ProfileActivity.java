package com.jsnk77.quitsmoking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by jsnk77 on 14/12/13.
 */
public class ProfileActivity extends Activity {

    @InjectView(R.id.question1)
    TextView mQuestion1;
    @InjectView(R.id.editText)
    EditText mEditText;
    @InjectView(R.id.question2)
    TextView mQuestion2;
    @InjectView(R.id.editText2)
    EditText mEditText2;
    @InjectView(R.id.question3)
    TextView mQuestion3;
    @InjectView(R.id.editText3)
    EditText mEditText3;
    @InjectView(R.id.button)
    Button mButton;
    @InjectView(R.id.textView)
    TextView mTextView;
    @InjectView(R.id.textView2)
    TextView mTextView2;

    private MobileServiceClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.inject(this);
        ButterKnife.inject(this);
        ButterKnife.inject(this);

        try {
            mClient = new MobileServiceClient(
                    "https://quitsmokingavatar.azure-mobile.net/",
                    "oBosiqBqMTabSBiLzeCmUlAucgoiOX80",
                    this
            );
        } catch (Throwable e) {
            e.printStackTrace();
        }
        getData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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
            intent.setClassName("com.jsnk77.quitsmokin", "com.jsnk77.quitsmokin.HomeActivity");
            //intent.putExtra();
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.action_profile) {
            //Toast.makeText(this, "profile selected", Toast.LENGTH_LONG).show();
            intent.setClassName("com.jsnk77.quitsmokin", "com.jsnk77.quitsmokin.ProfileActivity");
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.action_friend) {
            //Toast.makeText(this, "ranking selected", Toast.LENGTH_LONG).show();
            intent.setClassName("com.jsnk77.quitsmokin", "com.jsnk77.quitsmokin.FriendActivity");
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.action_message) {
            //Toast.makeText(this, "facebook selected", Toast.LENGTH_LONG).show();
            intent.setClassName("com.jsnk77.quitsmokin", "com.jsnk77.quitsmokin.MessageActivity");
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.button)
    public void submit(View view) {
        Profile profile = new Profile();
        profile.AverageTabacco = Integer.parseInt(mEditText.getText().toString());
        profile.GoalTabacco = Integer.parseInt(mEditText2.getText().toString());
        profile.PeriodTabacco = Integer.parseInt(mEditText3.getText().toString());
        mClient.getTable(Profile.class).insert(profile, new TableOperationCallback<Profile>() {
            public void onCompleted(Profile entity, Exception exception, ServiceFilterResponse response) {
                if (exception == null) {
                    // Insert succeeded
                    Toast.makeText(ProfileActivity.this, "登録完了しました。", Toast.LENGTH_LONG).show();
                } else {
                    // Insert failed
                    Toast.makeText(ProfileActivity.this, "登録に失敗しました。", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void getData() {
        MobileServiceTable<Tabacco> tabacco = mClient.getTable(Tabacco.class);

        tabacco.where().field("Text").eq("すばらしいアイテム").select("SmokeCount").execute(new TableQueryCallback<Tabacco>() {
            @Override
            public void onCompleted(List<Tabacco> result, int count, Exception exception, ServiceFilterResponse response) {
                int total = 0;
                for (Tabacco i : result) {
                    total += i.SmokeCount;
                }
                final int finalTotal = total;
                ProfileActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText("Total Steps : " + Integer.toString(finalTotal) + " 歩\n" + "Total Cost : " + (finalTotal * 21) + "円");
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
                final int finalTotal = totalToday;
                ProfileActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextView2.setText("今日の歩数 : " + Integer.toString(finalTotal) + " 歩");
                    }
                });
            }
        });
    }
}
