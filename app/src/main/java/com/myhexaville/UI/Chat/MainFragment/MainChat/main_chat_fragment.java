package com.myhexaville.UI.Chat.MainFragment.MainChat;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.myhexaville.UI.Adapter.AdapterMainChat.$_Recycle_View_Main_Chat_Adapter;
import com.myhexaville.UI.Adapter.AdapterMainChat.$_Value_Item_Main_Chat;
import com.myhexaville.UI.Chat.MainFragment.RoomChat.RecyclerItemClickListener;
import com.myhexaville.UI.Chat.MainFragment.RoomChat.room_chat;
import com.myhexaville.login.MainActivity;
import com.myhexaville.login.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link main_chat_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link main_chat_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class main_chat_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    //Attribute
    RecyclerView recycle_view_main_chat;
    $_Recycle_View_Main_Chat_Adapter recycleAdapter;
    public static List<Pair<$_Value_Item_Main_Chat, room_chat>> rooms;

    public main_chat_fragment() {
        // Required empty public constructor
        rooms = new ArrayList<>();

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment main_chat_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static main_chat_fragment newInstance(String param1, String param2) {
        main_chat_fragment fragment = new main_chat_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_main_chat_fragment, container, false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mListener = (OnFragmentInteractionListener) getActivity();
        $_Value_Item_Main_Chat value_item_main_chat = new $_Value_Item_Main_Chat("Hello", "Yamen", "y@y.y", R.drawable.add);
        room_chat room_chat = new room_chat();


        rooms.add(new Pair<$_Value_Item_Main_Chat, room_chat>(value_item_main_chat, room_chat));
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container_main_second, room_chat).addToBackStack(null).hide(room_chat).commit();

        int size = MainActivity.messages.size();
        for (int i = 0; i < size; i++) {
            main_chat_fragment.rooms.get(0).second.addMessage(MainActivity.messages.get(0));
            MainActivity.messages.remove(0);
        }
        recycleAdapter = new $_Recycle_View_Main_Chat_Adapter(rooms);
        recycle_view_main_chat = view.findViewById(R.id.recycle_view_main_chat);
        recycle_view_main_chat.setAdapter(recycleAdapter);
        recycle_view_main_chat.setLayoutManager(layoutManager);
        action_recycle_view();
         return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String json, String id) {
        if (mListener != null) {
            mListener.onFragmentInteraction(json, id);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String json, String id);
    }


    public void action_recycle_view() {
        recycle_view_main_chat.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recycle_view_main_chat, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.show(rooms.get(position).second).addToBackStack(null).commit();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }
}
