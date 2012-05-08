/**
 * 
 */
package com.diandian.api.sdk.android.sample;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.diandian.api.sdk.DDJSONParser;
import com.diandian.api.sdk.DefaultHttpTools;
import com.diandian.api.sdk.DefaultJsonParser;
import com.diandian.api.sdk.android.R;
import com.diandian.api.sdk.android.client.DDClient;
import com.diandian.api.sdk.android.client.DDClientInvoker;
import com.diandian.api.sdk.android.client.DDError;
import com.diandian.api.sdk.android.client.DDListener;
import com.diandian.api.sdk.android.configuration.DDAPIConstants;
import com.diandian.api.sdk.android.util.PrintLog;
import com.diandian.api.sdk.exception.DDAPIException;

/**
 * @author zhangdong zhangdong@diandian.com
 * 
 *         2012-4-23 下午2:58:41
 */
public class InitActivity extends Activity {

    DDClient client;

    DDJSONParser parser;

    public final static String LOG_TAG = "InitActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init);
        //设定appKey,appSerret和redirectUri
        client = new DDClient(DDAPIConstants.APP_KEY, DDAPIConstants.APP_SECRET,
                DDAPIConstants.REDIRECT_URI);
        client.setDdHttpTools(new DefaultHttpTools());
        //设定parser,可用自己的，只需要实现DDJAONParser的所以接口就行。
        parser = new DefaultJsonParser();
        DDClientInvoker.init(client, parser);
        Button oauthCode = (Button) this.findViewById(R.id.button1);
        Button oauthPassword = (Button) this.findViewById(R.id.button2);
        Button oauthRefresh = (Button) this.findViewById(R.id.button3);
        Button back = (Button) this.findViewById(R.id.button4);

        oauthCode.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //code的认证方法。    //code的认证方法。。
                client.authorize(InitActivity.this, new AuthDialogListener());
            }

        });

        oauthPassword.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(InitActivity.this);
                dialog.setTitle("请输入用户名及密码");
                dialog.setContentView(R.layout.oauth_password);
                Button ok = (Button) dialog.findViewById(R.id.button1);
                Button cancel = (Button) dialog.findViewById(R.id.button2);
                final AuthDialogListener authDialogListener = new AuthDialogListener();
                ok.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String account = ((EditText) dialog.findViewById(R.id.editText1)).getText()
                                .toString();
                        String password = ((EditText) dialog.findViewById(R.id.editText2))
                                .getText().toString();
                        PrintLog.d(LOG_TAG, "init by password account:" + account + "\t password:"
                                + password);
                        client.initAccessTokenByPassword(account, password, authDialogListener);
                        dialog.dismiss();

                    }

                });
                cancel.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        authDialogListener.onCancel();
                        dialog.dismiss();
                    }

                });
                dialog.show();
            }

        });

        back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }

        });

        oauthRefresh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final AuthDialogListener authDialogListener = new AuthDialogListener();
                try {
                    client.refreshToken(authDialogListener);
                } catch (DDAPIException e) {
                    authDialogListener.onDDAPIException(e);
                }
            }

        });

    }

    class AuthDialogListener implements DDListener {

        @Override
        public void onComplete(Bundle values) {
            DDClientInvoker.init(client, parser);
            Toast.makeText(getApplicationContext(), "Auth succ", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(DDError e) {
            Toast.makeText(getApplicationContext(), "Auth error : " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(), "Auth cancel", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onDDAPIException(DDAPIException e) {
            Toast.makeText(getApplicationContext(), "Auth exception : " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

}
