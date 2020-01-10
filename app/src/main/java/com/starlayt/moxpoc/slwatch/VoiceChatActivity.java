package com.starlayt.moxpoc.slwatch;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.starlayt.moxpoc.slwatch.ModelAPI.Message;
import com.starlayt.moxpoc.slwatch.Tools.MessageAdapter;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class VoiceChatActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Message> messageList = new ArrayList();
    PreferencesLoad load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_chat);

        Toolbar profileToolbar = findViewById(R.id.profileToolbar);
        profileToolbar.setTitle("");
        setSupportActionBar(profileToolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_message_list);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MessageAdapter(getBaseContext(), messageList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        fillWithNonsenseText();


        load = new PreferencesLoad(getApplicationContext());

        //Событие кнопки назад(настройки)
        profileToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VoiceChatActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomMenu = findViewById(R.id.bottomNavigationView);
        bottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.statistics_item:
                        Intent intent = new Intent(VoiceChatActivity.this, StatisticsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.call_item:
                        String phoneNo = load.getWatch().getDeviceMobileNo();
                        if(!TextUtils.isEmpty(phoneNo)) {
                            String dial = "tel:" + phoneNo;
                            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                        }else {
                            Toast.makeText(VoiceChatActivity.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.myProfile_item:
                        Intent intentT = new Intent(VoiceChatActivity.this, MainActivity.class);
                        startActivity(intentT);
                        break;
                }
                return true;
            }
        });
        
        
    }

    public void fillWithNonsenseText() {
        messageList.add(new Message(1,"Hello", "Device"));
        messageList.add(new Message(2,"Hello", "JavaCodeGeeks"));
        messageList.add(new Message(3,"This is an example about RecyclerView", "Device"));
        messageList.add(new Message(4,"Great news!", "JavaCodeGeeks"));
        messageList.add(new Message(5,"Enjoy reading!", "Device"));
        messageList.add(new Message(6,"You too", "JavaCodeGeeks"));
        /*messageList.add(new Message(7,"Hello", "Device"));
        messageList.add(new Message(8,"Hello", "JavaCodeGeeks"));
        messageList.add(new Message(9,"This is an example about RecyclerView", "Device"));
        messageList.add(new Message(10,"Great news!", "JavaCodeGeeks"));
        messageList.add(new Message(11,"Enjoy reading!", "Device"));
        messageList.add(new Message(12,"You too", "JavaCodeGeeks"));
        messageList.add(new Message(13,"Hello", "Device"));
        messageList.add(new Message(14,"Hello", "JavaCodeGeeks"));
        messageList.add(new Message(15,"This is an example about RecyclerView", "Device"));
        messageList.add(new Message(16,"Great news!", "JavaCodeGeeks"));
        messageList.add(new Message(17,"Enjoy reading!", "Device"));
        messageList.add(new Message(18,"You too", "JavaCodeGeeks"));

        messageList.add(new Message(19,"Hello", "Device"));
        messageList.add(new Message(20,"Hello", "JavaCodeGeeks"));
        messageList.add(new Message(21,"This is an example about RecyclerView", "Device"));
        messageList.add(new Message(22,"Great news!", "JavaCodeGeeks"));
        messageList.add(new Message(23,"Enjoy reading!", "Device"));
        messageList.add(new Message(24,"You too", "JavaCodeGeeks"));
        messageList.add(new Message(25,"Hello", "Device"));
        messageList.add(new Message(26,"Hello", "JavaCodeGeeks"));
        messageList.add(new Message(27,"This is an example about RecyclerView", "Device"));
        messageList.add(new Message(28,"Great news!", "JavaCodeGeeks"));
        messageList.add(new Message(29,"Enjoy reading!", "Device"));
        messageList.add(new Message(30,"You too", "JavaCodeGeeks"));
        messageList.add(new Message(31,"Hello", "Device"));
        messageList.add(new Message(32,"Hello", "JavaCodeGeeks"));
        messageList.add(new Message(33,"This is an example about RecyclerView", "Device"));
        messageList.add(new Message(34,"Great news!", "JavaCodeGeeks"));
        messageList.add(new Message(35,"Enjoy reading!", "Device"));
        messageList.add(new Message(36,"You too", "JavaCodeGeeks"));*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        final MenuItem item = menu.findItem(R.id.watchChargeItem);
        return true;
    }
}
