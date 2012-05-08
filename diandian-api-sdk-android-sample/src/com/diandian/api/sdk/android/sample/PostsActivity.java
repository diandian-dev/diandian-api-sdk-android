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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.diandian.api.sdk.android.client.BaseDDListener;
import com.diandian.api.sdk.android.client.DDClientInvoker;
import com.diandian.api.sdk.android.configuration.DDAPIConstants;
import com.diandian.api.sdk.exception.DDAPIException;
import com.diandian.api.sdk.model.AudioPostInfo;
import com.diandian.api.sdk.model.LinkPostInfo;
import com.diandian.api.sdk.model.PhotoPostInfo;
import com.diandian.api.sdk.model.PostBaseInfo;
import com.diandian.api.sdk.model.TextPostInfo;
import com.diandian.api.sdk.model.VideoPostInfo;
import com.diandian.api.sdk.view.PostView;

/**
 * @author zhangdong zhangdong@diandian.com
 * 
 *         2012-4-23 下午5:25:22
 */
public class PostsActivity extends Activity {

    private static final String[] title = { "all", "text", "photo", "link", "audio", "video" };

    private final Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posts);
        ArrayAdapter<String> adapter;
        final Spinner typeSpinner = (Spinner) findViewById(R.id.spinner1);
        adapter = new ArrayAdapter<String>(PostsActivity.this,
                android.R.layout.simple_spinner_item, title);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter添加到spinner中
        typeSpinner.setAdapter(adapter);
        typeSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                arg0.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        Button button = (Button) findViewById(R.id.button1);
        final EditText limitEdit = (EditText) findViewById(R.id.editText1);
        final EditText offsetEdit = (EditText) findViewById(R.id.editText2);
        final EditText blogCNameEdit = (EditText) findViewById(R.id.editText3);
        final RadioGroup reblogInfoRadio = (RadioGroup) findViewById(R.id.radioGroup1);
        final RadioGroup notesInfoRadio = (RadioGroup) findViewById(R.id.radioGroup2);
        final RadioGroup syncInfoRadio = (RadioGroup) findViewById(R.id.radioGroup3);
        final TextView textView = (TextView) findViewById(R.id.editText4);
        final TextView tagEdit = (TextView) findViewById(R.id.editText5);
        final TextView postIdEdit = (TextView) findViewById(R.id.editText6);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    int limit = DDAPIConstants.DEFAULT_NUMBER_PERPAGE;
                    int offset = 0;
                    String limitStr = limitEdit.getText().toString();
                    String offsetStr = offsetEdit.getText().toString();
                    String blogCName = blogCNameEdit.getText().toString();
                    String tag = tagEdit.getText().toString();
                    String postId = postIdEdit.getText().toString();
                    if (TextUtils.isEmpty(blogCName)) {
                        throw new DDAPIException(DDAPIConstants.DEFAULT_ERR_CODE,
                                "blogCName can not by null", null);
                    }
                    if (!TextUtils.isEmpty(limitStr)) {
                        limit = Integer.valueOf(limitStr);
                    }
                    if (!TextUtils.isEmpty(offsetStr)) {
                        offset = Integer.valueOf(offsetStr);
                    }

                    RadioButton reblogInfo = (RadioButton) findViewById(reblogInfoRadio
                            .getCheckedRadioButtonId());
                    RadioButton notesInfo = (RadioButton) findViewById(notesInfoRadio
                            .getCheckedRadioButtonId());
                    RadioButton syncInfo = (RadioButton) findViewById(syncInfoRadio
                            .getCheckedRadioButtonId());
                    String type = typeSpinner.getSelectedItem().toString();
                    if ("all".equalsIgnoreCase(type)) {
                        type = null;
                    }
                    //如果是异步获取。
                    if (Boolean.valueOf(syncInfo.getText().toString())) {
                        DDClientInvoker.getInstance().getPosts(blogCName, type, limit, offset, tag,
                                Boolean.valueOf(reblogInfo.getText().toString()),
                                Boolean.valueOf(notesInfo.getText().toString()), postId,
                                new BaseDDListener() {

                                    @Override
                                    public void onComplete(Bundle values) {
                                        final String result = values.getString("result");
                                        //这里返回的是json串。需要自己转换为DashBoardView
                                        //因为android的ui并非线程安全，所以需要handler来更新ui.
                                        handler.post(new Runnable() {

                                            @Override
                                            public void run() {
                                                textView.setText(result);
                                            }

                                        });

                                    }
                                });
                        return;
                    }
                    PostView info = DDClientInvoker.getInstance().getPosts(blogCName, type, limit,
                            offset, tag, Boolean.valueOf(reblogInfo.getText().toString()),
                            Boolean.valueOf(notesInfo.getText().toString()), postId);
                    String result = "";
                    for (PostBaseInfo postBase : info.getPosts()) {
                        //这里postBase并非实际的post类型。用的时候需要根据type强转为相应的类型
                        if (DDAPIConstants.POST_TEXT.equalsIgnoreCase(postBase.getType())) {
                            result += ((TextPostInfo) postBase).toString();
                        } else if (DDAPIConstants.POST_LINK.equalsIgnoreCase(postBase.getType())) {
                            result += ((LinkPostInfo) postBase).toString();
                        } else if (DDAPIConstants.POST_PHOTO.equalsIgnoreCase(postBase.getType())) {
                            result += ((PhotoPostInfo) postBase).toString();
                        } else if (DDAPIConstants.POST_AUDIO.equalsIgnoreCase(postBase.getType())) {
                            result += ((AudioPostInfo) postBase).toString();
                        } else if (DDAPIConstants.POST_VIDEO.equalsIgnoreCase(postBase.getType())) {
                            result += ((VideoPostInfo) postBase).toString();
                        }
                    }
                    textView.setText(result);
                } catch (DDAPIException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG)
                            .show();
                }

            }

        });
    }
}
