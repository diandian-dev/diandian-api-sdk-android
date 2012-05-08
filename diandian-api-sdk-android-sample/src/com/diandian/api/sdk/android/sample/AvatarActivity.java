/**
 * 
 */
package com.diandian.api.sdk.android.sample;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.diandian.api.sdk.android.configuration.DDAPIConstants;
import com.diandian.api.sdk.android.util.AndroidUtil;
import com.diandian.api.sdk.android.util.PrintLog;
import com.diandian.api.sdk.exception.DDAPIException;

/**
 * @author zhangdong zhangdong@diandian.com
 * 
 *         2012-4-24 下午2:17:11
 */
public class AvatarActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avatar);
        final Button button = (Button) findViewById(R.id.button1);
        final EditText editText = (EditText) findViewById(R.id.editText1);
        final ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
        final ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
        final ImageView imageView3 = (ImageView) findViewById(R.id.imageView3);
        final ImageView imageView4 = (ImageView) findViewById(R.id.imageView4);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    String blogCName = editText.getText().toString();
                    String avatarPre = DDAPIConstants.HOST + "v1/blog/" + blogCName + "/avatar/";
                    PrintLog.d("test", avatarPre + "57");
                    Bitmap bitmap1 = AndroidUtil.getHttpBitmap(avatarPre + "57");
                    imageView1.setImageBitmap(bitmap1);
                    Bitmap bitmap2 = AndroidUtil.getHttpBitmap(avatarPre + "72");
                    imageView2.setImageBitmap(bitmap2);
                    Bitmap bitmap3 = AndroidUtil.getHttpBitmap(avatarPre + "114");
                    imageView3.setImageBitmap(bitmap3);
                    Bitmap bitmap4 = AndroidUtil.getHttpBitmap(avatarPre + "144");
                    imageView4.setImageBitmap(bitmap4);

                } catch (DDAPIException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

    }
}
