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

/**
 * @author zhangdong zhangdong@diandian.com
 * 
 *         2012-4-24 下午2:17:11
 */
public class DeleteActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete);
        final Handler handler = new Handler();
        final Button button = (Button) findViewById(R.id.button1);
        final EditText idText = (EditText) findViewById(R.id.editText1);
        final EditText blogCNameText = (EditText) findViewById(R.id.editText3);
        final EditText resultText = (EditText) findViewById(R.id.editText4);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    String blogCName = blogCNameText.getText().toString();
                    String id = idText.getText().toString();
                    if (TextUtils.isEmpty(id)) {
                        throw new DDAPIException(DDAPIConstants.DEFAULT_ERR_CODE,
                                "id can not be null", null);
                    }
                    if (TextUtils.isEmpty(blogCName)) {
                        throw new DDAPIException(DDAPIConstants.DEFAULT_ERR_CODE,
                                "blogCName can not be null", null);
                    }
                    DDClientInvoker.getInstance().deletePost(blogCName, id, new BaseDDListener() {

                        @Override
                        public void onComplete(Bundle values) {
                            final String result = values.getString("result");
                            handler.post(new Runnable() {

                                @Override
                                public void run() {
                                    resultText.setText(result);
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
