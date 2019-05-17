package com.myhexaville.UI.Adapter.AdapterMainChat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.myhexaville.UI.Chat.MainFragment.RoomChat.room_chat;
import com.myhexaville.login.MainActivity;
import com.myhexaville.login.R;

import java.util.List;

public class $_Recycle_View_Main_Chat_Adapter extends RecyclerView.Adapter<$_Recycle_View_Main_Chat_Adapter.myHolder> {

    List<Pair<$_Value_Item_Main_Chat, room_chat>> rooms;

    public $_Recycle_View_Main_Chat_Adapter(List list) {
        this.rooms = list;
        this.rooms.toArray();
    }

    //Overrided Method
    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(MainActivity.context).inflate(R.layout.item_recycle_view_main_chat, viewGroup, false);
        myHolder myHolder = new myHolder(view);
        return myHolder;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull myHolder myHolder, int i) {
        $_Value_Item_Main_Chat value_item_main_chat;
        value_item_main_chat = rooms.get(i).first;
        myHolder.item_main_chat.getRecycle_view_main_chat_message().setText(value_item_main_chat.getMessage());
        myHolder.item_main_chat.getRecycle_view_main_chat_name().setText(value_item_main_chat.getName());
        myHolder.item_main_chat.getRecycle_view_main_chat_email().setText(value_item_main_chat.getEmail());
        myHolder.item_main_chat.getRecycle_view_main_chat_image().setImageResource(value_item_main_chat.getId_image());
        myHolder.item_main_chat.getRecycle_view_main_chat_image().setBackgroundColor(Color.RED);
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }




    public class myHolder extends RecyclerView.ViewHolder {

        $_Item_Main_Chat item_main_chat;

        public myHolder(@NonNull View itemView) {
            super(itemView);
            item_main_chat = new $_Item_Main_Chat(itemView);

        }
    }
}
