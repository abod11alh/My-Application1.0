package com.myhexaville.login;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.myhexaville.UI.Account.signup_fragment_tow;
import com.myhexaville.UI.Chat.MainFragment.MainChat.main_chat_fragment;
import com.myhexaville.UI.Chat.MainFragment.RoomChat.room_chat;
import com.myhexaville.UI.Chat.MainFragment.main_fragment;
import com.myhexaville.UI.Notification.notification_fragment;

public class SecondActivity extends AppCompatActivity implements main_fragment.OnFragmentInteractionListener,
        main_chat_fragment.OnFragmentInteractionListener,
        notification_fragment.OnFragmentInteractionListener,
        room_chat.OnFragmentInteractionListener,
signup_fragment_tow.OnFragmentInteractionListener{

    public static FragmentManager fragmentManager;
    private static FragmentTransaction fragmentTransaction;
    public static FragmentActivity fragmentActivity;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        fragmentActivity = this;
        context = this;
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        if (findViewById(R.id.container_main_second) != null) {
            if (savedInstanceState != null) return;
            fragmentTransaction = fragmentManager.beginTransaction();
            if(getIntent().getExtras().getString("fragment").equals("main_fragment")){
                main_fragment main_fragment = new main_fragment();
            /*Bundle bundle = new Bundle();
            bundle.putString("data", finalMy_json.toString());
            main_fragment.setArguments(bundle);
            main_fragment.setArguments(bundle);*/
                fragmentTransaction.replace(R.id.container_main_second, main_fragment).commit();
            }else if(getIntent().getExtras().getString("fragment").equals("signup_fragment_tow")){
                signup_fragment_tow signup_fragment_tow = new signup_fragment_tow();
            /*Bundle bundle = new Bundle();
            bundle.putString("data", finalMy_json.toString());
            main_fragment.setArguments(bundle);
            main_fragment.setArguments(bundle);*/
                fragmentTransaction.replace(R.id.container_main_second, signup_fragment_tow).commit();
            }


        }

    }

    @Override
    public void onFragmentInteraction(String json, String id) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
