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
import com.diandian.api.sdk.view.LikesPostView;

/**
 * @author zhangdong zhangdong@diandian.com
 * 
 *         2012-4-24 下午2:17:11
 */
public class LikeActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.like);
        final Button likesButton = (Button) findViewById(R.id.button1);
        final Button likeButton = (Button) findViewById(R.id.button2);
        final Button unlikeButton = (Button) findViewById(R.id.button3);
        final EditText limitText = (EditText) findViewById(R.id.editText1);
        final EditText offsetText = (EditText) findViewById(R.id.editText2);
        final EditText postIdText = (EditText) findViewById(R.id.editText3);
        final EditText resultText = (EditText) findViewById(R.id.editText4);
        final Handler handler = new Handler();
        likesButton.setOnClickListener(new OnClickListener() {

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
                    LikesPostView l = DDClientInvoker.getInstance().getLikes(limit, offset);
                    resultText.setText(l.toString());

                } catch (DDAPIException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG)
                            .show();
                }

            }

        });

        likeButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String postId = postIdText.getText().toString();
                if (TextUtils.isEmpty(postId)) {
                    throw new DDAPIException(DDAPIConstants.DEFAULT_ERR_CODE,
                            "postId can not be empty", null);
                }

                try {
                    DDClientInvoker.getInstance().like(postId, new BaseDDListener() {

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

        unlikeButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String postId = postIdText.getText().toString();
                if (TextUtils.isEmpty(postId)) {
                    throw new DDAPIException(DDAPIConstants.DEFAULT_ERR_CODE,
                            "postId can not be empty", null);
                }

                try {
                    DDClientInvoker.getInstance().unLike(postId, new BaseDDListener() {

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
