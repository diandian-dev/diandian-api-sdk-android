/**
 * 
 */
package com.diandian.api.sdk.android.sample;

import java.io.File;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.Toast;

import com.diandian.api.sdk.android.client.BaseDDListener;
import com.diandian.api.sdk.android.client.DDClientInvoker;
import com.diandian.api.sdk.android.configuration.DDAPIConstants;
import com.diandian.api.sdk.android.util.PrintLog;
import com.diandian.api.sdk.exception.DDAPIException;

/**
 * @author zhangdong zhangdong@diandian.com
 * 
 *         2012-4-25 下午2:10:16
 */
public class EditActivity extends Activity {

    private static String[] states = { "published", "draft", "queue" };

    private static final String[] types = { "text", "photo", "link", "audio", "video" };

    private static final String LOG_TAG = "PostActivity";

    TableRow titleRow;

    TableRow bodyRow;

    TableRow urlRow;

    TableRow descRow;

    TableRow captionRow;

    TableRow dataRow;

    TableRow musicNameRow;

    TableRow musicSingerRow;

    EditText dataEditText;

    Spinner typeSpinner;

    Spinner stateSpinner;

    Button button;

    Handler handler = new Handler();

    EditText resultText;

    BaseDDListener postListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        ArrayAdapter<String> typeAdapter;
        ArrayAdapter<String> stateAdapter;
        button = (Button) findViewById(R.id.button1);
        typeSpinner = (Spinner) findViewById(R.id.spinner2);
        stateSpinner = (Spinner) findViewById(R.id.spinner1);
        titleRow = (TableRow) findViewById(R.id.tableRow5);
        bodyRow = (TableRow) findViewById(R.id.tableRow6);
        urlRow = (TableRow) findViewById(R.id.tableRow7);
        descRow = (TableRow) findViewById(R.id.tableRow8);
        captionRow = (TableRow) findViewById(R.id.tableRow9);
        dataRow = (TableRow) findViewById(R.id.tableRow10);
        musicNameRow = (TableRow) findViewById(R.id.tableRow11);
        musicSingerRow = (TableRow) findViewById(R.id.tableRow12);
        dataEditText = (EditText) findViewById(R.id.editText12);
        resultText = (EditText) findViewById(R.id.editText1);

        typeAdapter = new ArrayAdapter<String>(EditActivity.this,
                android.R.layout.simple_spinner_item, types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int index, long arg3) {
                arg0.setVisibility(View.VISIBLE);
                cleanAll();
                if (index == 0) {
                    titleRow.setVisibility(View.VISIBLE);
                    bodyRow.setVisibility(View.VISIBLE);
                } else if (index == 1) {
                    captionRow.setVisibility(View.VISIBLE);
                    dataRow.setVisibility(View.VISIBLE);
                } else if (index == 2) {
                    titleRow.setVisibility(View.VISIBLE);
                    urlRow.setVisibility(View.VISIBLE);
                    descRow.setVisibility(View.VISIBLE);
                } else if (index == 3) {
                    captionRow.setVisibility(View.VISIBLE);
                    dataRow.setVisibility(View.VISIBLE);
                    musicNameRow.setVisibility(View.VISIBLE);
                    musicSingerRow.setVisibility(View.VISIBLE);
                } else if (index == 4) {
                    captionRow.setVisibility(View.VISIBLE);
                    urlRow.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        stateAdapter = new ArrayAdapter<String>(EditActivity.this,
                android.R.layout.simple_spinner_item, states);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter添加到spinner中
        stateSpinner.setAdapter(stateAdapter);
        stateSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                arg0.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        dataEditText.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                if ("photo".equalsIgnoreCase(typeSpinner.getSelectedItem().toString())) {
                    intent.setType("image/*");
                } else {
                    intent.setType("audio/*");
                }
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);

            }

        });

        postListener = new BaseDDListener() {

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
        };

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String type = getType();
                String blogCName = getBlogCName();
                String slug = getSlug();
                String tag = getTag();
                String state = getState();
                String postId = getPostId();
                try {
                    if (TextUtils.isEmpty(blogCName)) {
                        throw new DDAPIException(DDAPIConstants.DEFAULT_ERR_CODE,
                                "blogCName can not be null", null);
                    }
                    if ("text".equalsIgnoreCase(type)) {
                        DDClientInvoker.getInstance().editText(blogCName, state, tag, slug,
                                getPostTitle(), getBody(), postId, postListener);

                    } else if ("photo".equalsIgnoreCase(type)) {
                        PrintLog.d(LOG_TAG, "caption:" + getCaption() + "\t" + getFilePath());
                        DDClientInvoker.getInstance().editPhoto(blogCName, state, tag, slug,
                                getCaption(), getFilePath(), postId, postListener);
                    } else if ("link".equalsIgnoreCase(type)) {

                        DDClientInvoker.getInstance().editLink(blogCName, state, tag, slug,
                                getPostTitle(), getUrl(), getDesc(), postId, postListener);
                    } else if ("audio".equalsIgnoreCase(type)) {
                        PrintLog.d(LOG_TAG, "caption:" + getCaption() + "\t" + getFilePath());
                        DDClientInvoker.getInstance().editAudio(blogCName, state, tag, slug,
                                getCaption(), getFilePath(), getMusicName(), getMusicSinger(),
                                postId, postListener);

                    } else if ("video".equalsIgnoreCase(type)) {
                        PrintLog.d(LOG_TAG, "caption:" + getCaption() + "\t" + getFilePath());
                        DDClientInvoker.getInstance().editVideo(blogCName, state, tag, slug,
                                getCaption(), getUrl(), postId, postListener);
                    } else {
                        throw new DDAPIException(DDAPIConstants.DEFAULT_ERR_CODE, "Invalid type",
                                null);
                    }

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG)
                            .show();
                }

            }

        });

    }

    private void cleanAll() {
        titleRow.setVisibility(View.GONE);
        bodyRow.setVisibility(View.GONE);
        descRow.setVisibility(View.GONE);
        urlRow.setVisibility(View.GONE);
        captionRow.setVisibility(View.GONE);
        dataRow.setVisibility(View.GONE);
        musicNameRow.setVisibility(View.GONE);
        musicSingerRow.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    Uri uri = data.getData();
                    ContentResolver cr = getContentResolver();
                    Cursor mCur = cr.query(uri, null, null, null, null);
                    /** 得到本地文件的 id、路径、大小、文件名 */
                    if (!mCur.moveToFirst()) {
                        return;
                    }
                    try {
                        String filePath = mCur.getString(mCur.getColumnIndex("_data"));
                        PrintLog.d(LOG_TAG, "filePath:" + filePath);
                        File file = new File(filePath);
                        if (!file.exists()) {
                            PrintLog.d(LOG_TAG, "file not exist");
                            return;
                        }
                        dataEditText.setText(filePath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    public String getBlogCName() {
        EditText blogCNameText = (EditText) findViewById(R.id.editText11);
        return blogCNameText.getText().toString();
    }

    public String getState() {
        return stateSpinner.getSelectedItem().toString();
    }

    public String getType() {
        return typeSpinner.getSelectedItem().toString();
    }

    public String getTag() {
        return ((EditText) findViewById(R.id.editText2)).getText().toString();
    }

    public String getSlug() {
        return ((EditText) findViewById(R.id.editText3)).getText().toString();
    }

    public String getFilePath() {
        return dataEditText.getText().toString();
    }

    public String getCaption() {
        return ((EditText) findViewById(R.id.editText8)).getText().toString();
    }

    public String getUrl() {
        return ((EditText) findViewById(R.id.editText6)).getText().toString();
    }

    public String getDesc() {
        return ((EditText) findViewById(R.id.editText7)).getText().toString();
    }

    public String getMusicName() {
        return ((EditText) findViewById(R.id.editText9)).getText().toString();
    }

    public String getMusicSinger() {
        return ((EditText) findViewById(R.id.editText10)).getText().toString();
    }

    /**
     * 因为父类名重合，所以修改
     * 
     * @return
     */
    public String getPostTitle() {
        return ((EditText) findViewById(R.id.editText4)).getText().toString();
    }

    public String getBody() {
        return ((EditText) findViewById(R.id.editText5)).getText().toString();
    }

    public String getPostId() {
        return ((EditText) findViewById(R.id.editText13)).getText().toString();
    }
}
