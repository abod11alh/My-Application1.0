package com.myhexaville.UI.Chat.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.myhexaville.login.MainActivity;
import com.myhexaville.login.R;

import java.util.ArrayList;

public class User_Adapter extends ArrayAdapter<User_Info_Search> implements Filterable {

    public User_Adapter(Context context, ArrayList<User_Info_Search> users) {
        super(context, R.layout.item_view, users);
        this.user_info_searches=users;
        this.user_info_searches_conest=users;

    }
    private ArrayList<User_Info_Search> user_info_searches;
    private ArrayList<User_Info_Search> user_info_searches_conest;


    public ArrayList<User_Info_Search> getUser_info_searches_conest() {
        return user_info_searches_conest;
    }

    public void setUser_info_searches_conest(ArrayList<User_Info_Search> user_info_searches_conest) {
        this.user_info_searches_conest = user_info_searches_conest;
    }

    public ArrayList<User_Info_Search> getUser_info_searches() {
        return user_info_searches;
    }

    public void setUser_info_searches(ArrayList<User_Info_Search> user_info_searches) {
        this.user_info_searches = user_info_searches;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final User_Info_Search user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_view, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
        ImageView imageView=(ImageView)convertView.findViewById(R.id.imUser);
        Button Add_button=(Button) convertView.findViewById(R.id.Add_friend) ;
        Button Add_button2=(Button) convertView.findViewById(R.id.Add_friend2) ;

        // Populate the data into the template view using the data object
        tvName.setText( user.getmTitle());
        tvHome.setText(user.getmDescription());
        imageView.setImageResource(user.getmImageUrl());
        Add_button.setText(user.getButton_text());
        if (user.getButton_text().equals("Refusal"))
        {

            Add_button2.setText("Accept");
            Add_button2.setVisibility(View.VISIBLE);
        }
        Add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragment_search.set_action(user.getButton_text(),user.getId());
            }
        });
        Add_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragment_search.set_action("Accept",user.getId());

            }
        });
        // Return the completed view to render on screen
        return convertView;
    }

}
