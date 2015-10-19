package com.wumiao.sdk.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wumiao.sdk.WM;

public class LoginActivity extends AppCompatActivity {

    private ProfileData mProfileData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mProfileData = new ProfileData(this);
        refreshLoginState();

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.login_result).setVisibility(View.VISIBLE);
                findViewById(R.id.login).setVisibility(View.GONE);
            }
        });
        findViewById(R.id.login_success).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.login_result).setVisibility(View.GONE);
                findViewById(R.id.login).setVisibility(View.VISIBLE);

                ProfileData.Profile profile = mProfileData.newProfile();
                refreshLoginState();
                Toast.makeText(LoginActivity.this, profile.name + getString(R.string.welcome), Toast.LENGTH_SHORT).show();

                WM.getInstance().notifyLoginResult(WM.LoginResult.SUCCESS, "success");
                finish();
            }
        });
        findViewById(R.id.login_failure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.login_result).setVisibility(View.GONE);
                findViewById(R.id.login).setVisibility(View.VISIBLE);
                Toast.makeText(LoginActivity.this, getString(R.string.login_failure), Toast.LENGTH_SHORT).show();

                WM.getInstance().notifyLoginResult(WM.LoginResult.FAILURE, "failure");
                finish();
            }
        });
        findViewById(R.id.login_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.login_result).setVisibility(View.GONE);
                findViewById(R.id.login).setVisibility(View.VISIBLE);
                Toast.makeText(LoginActivity.this, getString(R.string.login_cancel), Toast.LENGTH_SHORT).show();

                WM.getInstance().notifyLoginResult(WM.LoginResult.CANCELLED, "cancel");
                finish();
            }
        });

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProfileData.clearProfile();
                refreshLoginState();
                Toast.makeText(LoginActivity.this, getString(R.string.bye), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshLoginState() {
        View loginView = findViewById(R.id.login);
        View logoutView = findViewById(R.id.logout);
        View profileView = findViewById(R.id.profile);
        ProfileData.Profile profile = mProfileData.getProfile();

        if (TextUtils.isEmpty(profile.uid)) {
            loginView.setEnabled(true);
            logoutView.setEnabled(false);
            profileView.setVisibility(View.GONE);
        } else {
            loginView.setEnabled(false);
            logoutView.setEnabled(true);

            TextView uidTxt = (TextView) findViewById(R.id.uid);
            TextView nameTxt = (TextView) findViewById(R.id.name);
            TextView avatorText = (TextView) findViewById(R.id.avator);

            uidTxt.setText(getString(R.string.uid) + profile.uid);
            nameTxt.setText(getString(R.string.name) + profile.name);
            avatorText.setText(getString(R.string.avator) + profile.avator);

            profileView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            WM.getInstance().notifyLoginResult(WM.LoginResult.CANCELLED, "cancel");
        }
        return super.onKeyUp(keyCode, event);
    }
}
