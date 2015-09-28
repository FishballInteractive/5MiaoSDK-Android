package com.wumiao.sdk.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class ShareActivity extends AppCompatActivity {

    public static final String EXTRA_SHARE_CONTENT = "extra.5miao.share_content";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_share);

        Bundle shareInfo = getIntent().getBundleExtra(EXTRA_SHARE_CONTENT);
        StringBuilder sb = new StringBuilder();
        for (String key : shareInfo.keySet()) {
            sb.append(key);
            sb.append(": ");
            sb.append(shareInfo.get(key));
            sb.append("\n");
        }

        ((EditText) findViewById(R.id.share_content)).setText(sb.toString());
    }
}