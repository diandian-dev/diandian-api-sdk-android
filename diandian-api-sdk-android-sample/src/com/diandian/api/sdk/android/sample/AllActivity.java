package com.diandian.api.sdk.android.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AllActivity extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button init = (Button) this.findViewById(R.id.button1);
        Button home = (Button) this.findViewById(R.id.button2);
        Button blogInfo = (Button) findViewById(R.id.button3);
        Button avatar = (Button) findViewById(R.id.button4);
        Button follow = (Button) findViewById(R.id.button5);
        Button like = (Button) findViewById(R.id.button6);
        Button posts = (Button) findViewById(R.id.button10);
        Button reblog = (Button) findViewById(R.id.button7);
        Button delete = (Button) findViewById(R.id.button8);
        Button userInfo = (Button) findViewById(R.id.button9);
        Button post = (Button) findViewById(R.id.button11);
        Button edit = (Button) findViewById(R.id.button12);
        Button draftqueueandsubmission = (Button) findViewById(R.id.button13);
        init.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllActivity.this, InitActivity.class);
                startActivity(intent);
            }

        });

        home.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllActivity.this, HomeActivity.class);
                startActivity(intent);
            }

        });

        blogInfo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllActivity.this, BlogInfoActivity.class);
                startActivity(intent);
            }

        });

        avatar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllActivity.this, AvatarActivity.class);
                startActivity(intent);
            }

        });
        follow.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllActivity.this, FollowActivity.class);
                startActivity(intent);
            }

        });
        like.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllActivity.this, LikeActivity.class);
                startActivity(intent);
            }

        });
        posts.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllActivity.this, PostsActivity.class);
                startActivity(intent);
            }

        });
        reblog.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllActivity.this, ReblogActivity.class);
                startActivity(intent);
            }

        });
        delete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllActivity.this, DeleteActivity.class);
                startActivity(intent);
            }

        });
        userInfo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllActivity.this, UserInfoActivity.class);
                startActivity(intent);
            }

        });
        post.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllActivity.this, PostActivity.class);
                startActivity(intent);
            }

        });
        edit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllActivity.this, EditActivity.class);
                startActivity(intent);
            }

        });

        draftqueueandsubmission.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllActivity.this, DaftQueueAndSubmissionActivity.class);
                startActivity(intent);
            }

        });
    }
}
