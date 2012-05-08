/**
 * 
 */
package com.diandian.api.sdk.android.sample;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.diandian.api.sdk.android.client.DDClientInvoker;
import com.diandian.api.sdk.android.configuration.DDAPIConstants;
import com.diandian.api.sdk.exception.DDAPIException;
import com.diandian.api.sdk.view.PostView;

/**
 * @author zhangdong zhangdong@diandian.com
 * 
 *         2012-4-24 下午2:17:11
 */
public class DaftQueueAndSubmissionActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.queue_draft_submission);
        final Button draftButton = (Button) findViewById(R.id.button1);
        final Button queueButton = (Button) findViewById(R.id.button2);
        final Button submissionButton = (Button) findViewById(R.id.button3);
        final EditText blogCNameText = (EditText) findViewById(R.id.editText1);
        final EditText resultText = (EditText) findViewById(R.id.editText2);

        draftButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    String blogCName = blogCNameText.getText().toString();
                    if (TextUtils.isEmpty(blogCName)) {
                        throw new DDAPIException(DDAPIConstants.DEFAULT_ERR_CODE,
                                "blogCName can not be null", null);
                    }
                    PostView postView = DDClientInvoker.getInstance().getDraft(blogCName);
                    resultText.setText(postView.toString());
                } catch (DDAPIException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        queueButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    String blogCName = blogCNameText.getText().toString();
                    if (TextUtils.isEmpty(blogCName)) {
                        throw new DDAPIException(DDAPIConstants.DEFAULT_ERR_CODE,
                                "blogCName can not be null", null);
                    }
                    PostView postView = DDClientInvoker.getInstance().getQueue(blogCName);
                    resultText.setText(postView.toString());
                } catch (DDAPIException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        submissionButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    String blogCName = blogCNameText.getText().toString();
                    if (TextUtils.isEmpty(blogCName)) {
                        throw new DDAPIException(DDAPIConstants.DEFAULT_ERR_CODE,
                                "blogCName can not be null", null);
                    }
                    PostView postView = DDClientInvoker.getInstance().getSubmission(blogCName);
                    resultText.setText(postView.toString());
                } catch (DDAPIException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

    }
}
