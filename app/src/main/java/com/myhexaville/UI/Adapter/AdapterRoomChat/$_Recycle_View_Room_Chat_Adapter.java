package com.myhexaville.UI.Adapter.AdapterRoomChat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.myhexaville.Logic.Client.$_Client;
import com.myhexaville.UI.Adapter.AdapterRoomChat.Message.$_Message;
import com.myhexaville.UI.Adapter.AdapterRoomChat.Message.$_Message_Type;
import com.myhexaville.UI.Adapter.AdapterRoomChat.Message.MessageImage.$_Message_Image;
import com.myhexaville.UI.Adapter.AdapterRoomChat.Message.MessageText.$_Message_Text;
import com.myhexaville.UI.Adapter.AdapterRoomChat.RecivevMessage.ReciveMessageImage.$_Recive_Message_Holders_Image;
import com.myhexaville.UI.Adapter.AdapterRoomChat.RecivevMessage.ReciveMessageImage.$_Value_Recive_Item_Room_Chat_Message_Image;
import com.myhexaville.UI.Adapter.AdapterRoomChat.RecivevMessage.ReciveMessageText.$_Recive_Message_Holders_Text;
import com.myhexaville.UI.Adapter.AdapterRoomChat.RecivevMessage.ReciveMessageText.$_Value_Recive_Item_Room_Chat_Text;
import com.myhexaville.UI.Adapter.AdapterRoomChat.SendMessage.SendMessageImage.$_Send_Message_Holders_Image;
import com.myhexaville.UI.Adapter.AdapterRoomChat.SendMessage.SendMessageImage.$_Value_Send_Item_Room_Chat_Message_Image;
import com.myhexaville.UI.Adapter.AdapterRoomChat.SendMessage.SendMessgaeText.$_Send_Message_Holders_Text;
import com.myhexaville.UI.Adapter.AdapterRoomChat.SendMessage.SendMessgaeText.$_Value_Send_Item_Room_Chat_Message_Text;
import com.myhexaville.login.MainActivity;
import com.myhexaville.login.R;
import com.myhexaville.login.SecondActivity;

import java.util.List;


public class $_Recycle_View_Room_Chat_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<$_Message> list;
    private Context context;

    public $_Recycle_View_Room_Chat_Adapter(List list, Context context) {
        this.list = list;
        this.list.toArray();
        this.context = context;
    }


    //Overrided Method
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        System.out.println(" i = " + i);
        if (i == $_Message_Type.SENT_TEXT) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_send_recycle_view_room_chat_text, viewGroup, false);
            $_Send_Message_Holders_Text send_message_holders_text = new $_Send_Message_Holders_Text(view);
            return send_message_holders_text;
        } else if (i == $_Message_Type.SENT_IMAGE) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_send_recycle_view_room_chat_image, viewGroup, false);
            $_Send_Message_Holders_Image send_message_holders_image = new $_Send_Message_Holders_Image(view);
            return send_message_holders_image;
        } else if (i == $_Message_Type.RECIVE_TEXT) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recive_recycle_view_room_chat_text, viewGroup, false);
            $_Recive_Message_Holders_Text recive_message_holders_text = new $_Recive_Message_Holders_Text(view);
            return recive_message_holders_text;

        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recive_recycle_view_room_chat_image, viewGroup, false);
            $_Recive_Message_Holders_Image recive_message_holders_image = new $_Recive_Message_Holders_Image(view);
            return recive_message_holders_image;
        }
        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder myHolder, int i) {
        int type = getItemViewType(i);
        $_Message message = list.get(i);
        if (type == $_Message_Type.SENT_TEXT) {
            $_Message_Text message_text = ($_Message_Text) message;
            $_Value_Send_Item_Room_Chat_Message_Text value_send_item_room_chat_message_text = new $_Value_Send_Item_Room_Chat_Message_Text(message_text.getMessage_text(), message_text.getTime());
            $_Send_Message_Holders_Text send_message_holders_text = ($_Send_Message_Holders_Text) myHolder;
            send_message_holders_text.getItem_room_chat_text_send().getMessage_send().setText(value_send_item_room_chat_message_text.getMessage());
            send_message_holders_text.getItem_room_chat_text_send().getDate_send().setText(value_send_item_room_chat_message_text.getDate());
        } else if (type == $_Message_Type.SENT_IMAGE) {
            $_Message_Image message_image = ($_Message_Image) message;
            $_Value_Send_Item_Room_Chat_Message_Image value_send_item_room_chat_message_image = new $_Value_Send_Item_Room_Chat_Message_Image(message_image.getBytes(), message_image.getTime());
            $_Send_Message_Holders_Image send_message_holders_image = ($_Send_Message_Holders_Image) myHolder;
            //byte[] bytes = Base64.decode(message_image.getBytes(), Base64.DEFAULT);
            // down sizing image as it throws OutOfMemory Exception for larger
            // images
            //   BufferedImage bufferedImage = null;
            Glide.with(SecondActivity.fragmentActivity)
                    .load(message_image.getBytes())
                    .asBitmap()
                    .into(
                            send_message_holders_image.getItem_send_room_chat_message_image().getMessage_send_image()
                    );
            /*byte [] encodeByte=Base64.decode(message_image.getBytes(),Base64.DEFAULT);
            InputStream inputStream  = new ByteArrayInputStream(encodeByte);
            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
            send_message_holders_image.getItem_send_room_chat_message_image().getMessage_send_image().setImageBitmap(bitmap);*/
            send_message_holders_image.getItem_send_room_chat_message_image().getDate_send().setText(value_send_item_room_chat_message_image.getDate());
        } else if (type == $_Message_Type.RECIVE_TEXT) {
            $_Message_Text message_text = ($_Message_Text) message;
            $_Recive_Message_Holders_Text reciveTextHolder = ($_Recive_Message_Holders_Text) myHolder;
            $_Value_Recive_Item_Room_Chat_Text value_recive_item_room_chat_text = new $_Value_Recive_Item_Room_Chat_Text(message_text.getName(), message_text.getMessage_text(), message_text.getTime());
            reciveTextHolder.getItem_recive_room_chat_text().getName_recive().setText(value_recive_item_room_chat_text.getName());
            reciveTextHolder.getItem_recive_room_chat_text().getMessage_recive().setText(value_recive_item_room_chat_text.getContent());
            reciveTextHolder.getItem_recive_room_chat_text().getDate_recive().setText(value_recive_item_room_chat_text.getDate());

        } else {
            $_Message_Image message_image = ($_Message_Image) message;
            $_Value_Recive_Item_Room_Chat_Message_Image value_recive_item_room_chat_message_image = new $_Value_Recive_Item_Room_Chat_Message_Image(message_image.getBytes(), message_image.getName(), message_image.getTime());
            $_Recive_Message_Holders_Image recive_message_holders_image = ($_Recive_Message_Holders_Image) myHolder;
            recive_message_holders_image.getItem_send_room_chat_message_image().getName_recive().setText(value_recive_item_room_chat_message_image.getName());
            // down sizing image as it throws OutOfMemory Exception for larger
            // images

            //byte[] bytes = Base64.decode(message_image.getBytes(), Base64.DEFAULT);
            // down sizing image as it throws OutOfMemory Exception for larger
            // images

           Glide.with(SecondActivity.fragmentActivity)
                    .load(message_image.getBytes())
                    .asBitmap()
                    .into(recive_message_holders_image.getItem_send_room_chat_message_image().getMessage_send_image());
           /* byte [] encodeByte=Base64.decode(message_image.getBytes(),Base64.DEFAULT);
            InputStream inputStream  = new ByteArrayInputStream(encodeByte);
            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
            recive_message_holders_image.getItem_send_room_chat_message_image().getMessage_send_image().setImageBitmap(bitmap);
            recive_message_holders_image.getItem_send_room_chat_message_image().getDate_recive().setText(value_recive_item_room_chat_message_image.getDate());*/

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        $_Message message = list.get(position);
        if ($_Client.getEmail().equals(message.getId())) {
            if (message.getType().equals("1")) {
                return $_Message_Type.SENT_TEXT;
            } else if (message.getType().equals("2")) {
                return $_Message_Type.SENT_IMAGE;
            }
        } else {
            if (message.getType().equals("3")) {
                return $_Message_Type.RECIVE_TEXT;
            } else if (message.getType().equals("4")) {
                return $_Message_Type.RECIVE_IMAGE;
            }
        }
        return super.getItemViewType(position);
    }


}
