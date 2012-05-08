/**
 * 
 */
package com.diandian.api.sdk.android.sample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.diandian.api.sdk.android.client.DDClientInvoker;
import com.diandian.api.sdk.exception.DDAPIException;
import com.diandian.api.sdk.model.UserDetailInfo;

/**
 * @author zhangdong zhangdong@diandian.com
 * 
 *         2012-4-24 下午2:17:11
 */
public class UserInfoActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);
        final EditText resultText = (EditText) findViewById(R.id.editText4);
        try {
            UserDetailInfo u = DDClientInvoker.getInstance().getUserInfo();
            resultText.setText(u.toString());
        } catch (DDAPIException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}
