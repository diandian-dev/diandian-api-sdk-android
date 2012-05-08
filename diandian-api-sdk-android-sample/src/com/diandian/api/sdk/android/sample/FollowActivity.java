/**
 * 
 */
package com.diandian.api.sdk.android.sample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.diandian.api.sdk.android.client.BaseDDListener;
import com.diandian.api.sdk.android.client.DDClientInvoker;
import com.diandian.api.sdk.android.configuration.DDAPIConstants;
import com.diandian.api.sdk.exception.DDAPIException;
import com.diandian.api.sdk.view.FollowersView;
import com.diandian.api.sdk.view.FollowingView;

/**
 * @author zhangdong zhangdong@diandian.com
 * 
 *         2012-4-24 下午2:17:11
 */
public class FollowActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follow);
        final Button followerButton = (Button) findViewById(R.id.button1);
        final Button followingButton = (Button) findViewById(R.id.button2);
        final Button followButton = (Button) findViewById(R.id.button3);
        final Button unfollowButton = (Button) findViewById(R.id.button4);
        final EditText limitText = (EditText) findViewById(R.id.editText1);
        final EditText offsetText = (EditText) findViewById(R.id.editText2);
        final EditText blogCNameText = (EditText) findViewById(R.id.editText3);
        final EditText resultText = (EditText) findViewById(R.id.editText4);
        final Handler handler = new Handler();
        followerButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String blogCName = blogCNameText.getText().toString();
                int limit = DDAPIConstants.DEFAULT_NUMBER_PERPAGE;
                int offset = 0;
                String limitStr = limitText.getText().toString();
                String offsetStr = offsetText.getText().toString();
                try {
                    if (TextUtils.isEmpty(blogCName)) {
                        throw new DDAPIException(DDAPIConstants.DEFAULT_ERR_CODE,
                                "blogCName can not be empty", null);
                    }
                    if (!TextUtils.isEmpty(limitStr)) {
                        limit = Integer.valueOf(limitStr);
                    }
                    if (!TextUtils.isEmpty(offsetStr)) {
                        offset = Integer.valueOf(offsetStr);
                    }

                    FollowersView f = DDClientInvoker.getInstance().getFollowers(blogCName, limit,
                            offset);
                    resultText.setText(f.toString());

                } catch (DDAPIException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG)
                            .show();
                }

            }

        });

        followingButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                int limit = DDAPIConstants.DEFAULT_NUMBER_PERPAGE;
                int offset = 0;
                String limitStr = limitText.getText().toString();
                String offsetStr = offsetText.getText().toString();
                if (!TextUtils.isEmpty(limitStr)) {
                    limit = Integer.valueOf(limitStr);
                }
                if (!TextUtils.isEmpty(offsetStr)) {
                    offset = Integer.valueOf(offsetStr);
                }

                try {
                    FollowingView f = DDClientInvoker.getInstance().getFollowing(limit, offset);
                    resultText.setText(f.toString());
                } catch (DDAPIException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG)
                            .show();
                }

            }
        });

        followButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String blogCName = blogCNameText.getText().toString();
                try {
                    if (TextUtils.isEmpty(blogCName)) {
                        throw new DDAPIException(DDAPIConstants.DEFAULT_ERR_CODE,
                                "blogCName can not be empty", null);
                    }

                    DDClientInvoker.getInstance().follow(blogCName, new BaseDDListener() {

                        @Override
                        public void onComplete(Bundle values) {
                            final String resultInfo = values.getString("result");
                            handler.post(new Runnable() {

                                @Override
                                public void run() {
                                    resultText.setText(resultInfo);
                                }
                            });
                        }
                    });

                } catch (DDAPIException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG)
                            .show();
                }

            }

        });
        unfollowButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String blogCName = blogCNameText.getText().toString();
                try {
                    if (TextUtils.isEmpty(blogCName)) {
                        throw new DDAPIException(DDAPIConstants.DEFAULT_ERR_CODE,
                                "blogCName can not be empty", null);
                    }

                    DDClientInvoker.getInstance().unFollow(blogCName, new BaseDDListener() {

                        @Override
                        public void onComplete(Bundle values) {
                            final String resultInfo = values.getString("result");
                            handler.post(new Runnable() {

                                @Override
                                public void run() {
                                    resultText.setText(resultInfo);
                                }
                            });
                        }
                    });

                } catch (DDAPIException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG)
                            .show();
                }

            }

        });
    }
}
