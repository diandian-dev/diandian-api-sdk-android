/**
 * 
 */
package com.diandian.api.sdk.android.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.diandian.api.sdk.android.client.DDClientInvoker;
import com.diandian.api.sdk.exception.DDAPIException;
import com.diandian.api.sdk.model.BlogDetailInfo;

/**
 * @author zhangdong zhangdong@diandian.com
 * 
 *         2012-4-24 下午2:17:11
 */
public class BlogInfoActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_info);
        final Button button = (Button) findViewById(R.id.button1);
        final EditText editText = (EditText) findViewById(R.id.editText1);
        final EditText editText2 = (EditText) findViewById(R.id.editText2);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    String blogCName = editText.getText().toString();
                    BlogDetailInfo blogInfo = DDClientInvoker.getInstance().getBlogInfo(blogCName);
                    editText2.setText(blogInfo.toString());
                } catch (DDAPIException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

    }

}
