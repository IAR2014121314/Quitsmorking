package com.jsnk77.quitsmoking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by jsnk77 on 14/12/13.
 */
public class HomeActivity extends Activity {


    private Intent intent;
    private String fbId;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toast.makeText(HomeActivity.this,"ホームへようこそ",Toast.LENGTH_LONG).show();

        intent = getIntent();
        fbId = intent.getStringExtra("fbId");

//        Intent i = new Intent(this,ShareActivity.class);
//        startActivity(i);




    }
}
