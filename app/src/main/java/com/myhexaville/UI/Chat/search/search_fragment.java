package com.myhexaville.UI.Chat.search;

import android.app.SearchManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import com.myhexaville.Logic.Client.$_Client;
import com.myhexaville.Logic.Friend.Friendship_sender;
import com.myhexaville.Logic.JSONData.$_JSONAttributes;
import com.myhexaville.Logic.JSONData.$_JSON_Search_User_Successful;
import com.myhexaville.login.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link search_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class search_fragment extends Fragment implements    SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SearchView searchView;
    private SearchManager searchManager;
    private ArrayAdapter mAdapter;
    ListView mListView ;
    private OnFragmentInteractionListener mListener;
    private    ArrayList<User_Info_Search> user_info_searches;

    public search_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment search_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static search_fragment newInstance(String param1, String param2) {
        search_fragment fragment = new search_fragment();
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
        setHasOptionsMenu(true);

        searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchItem = null;
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
    
    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {

        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
      //  FragmentTransaction fragmentTransaction = MainActivity.fragmentManager.beginTransaction();
     //   fragmentTransaction.replace(R.id.continer_main,MainActivity.fragment_main).commit();
        return true;
    }
});
        searchItem.expandActionView();
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search");
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.requestFocus();




        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_search_fragment, container, false);
        mListener = (OnFragmentInteractionListener) getActivity();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        System.out.println(query);
        send_to_search(query);

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        System.out.println("d="+newText);
        if(!newText.equals(""))
            send_to_search(newText);
        else
            set_list_show(null);
        return false;
    }
    public  void send_to_search(String query)
    {
        JSONObject jsonObject = new JSONObject();

        try {
            JSONObject jsonObject1= $_Client.getSharedPreferences().getObject("data_signup");
            jsonObject.put($_JSONAttributes.Type.toString(), "Search_Of_User");
            jsonObject.put($_JSONAttributes.Id.toString(), $_Client.getEmail());
            jsonObject.put($_JSONAttributes.Search_User.toString(),query);
            $_Client.getDataOutputStreamOnline().writeUTF(jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("error send data sign up");

        }
    }

    public  void set_list_show(final $_JSON_Search_User_Successful list_show)
    {
        System.out.println("hello");
       user_info_searches=new ArrayList<>();
        if(list_show!=null) {
            for (int i = 0; i < list_show.getIds().size(); i++) {
                user_info_searches.add(new User_Info_Search(R.drawable.user, list_show.getUsers_Name().get(i), "I need Pizza",list_show.getIds().get(i),list_show.getState_users().get(i)));

            }
        }
        mAdapter=new User_Adapter(getActivity(),user_info_searches);
        mListView = (ListView)getActivity(). findViewById(R.id.list);
        mListView.setAdapter(mAdapter);
        TextView mEmptyView = (TextView) getActivity().findViewById(R.id.emptyView);
        mListView.setEmptyView(mEmptyView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }
public void Edit_button(String id,String text)
{
    for (int i = 0; i <user_info_searches.size() ; i++) {
        if (user_info_searches.get(i).getId().equals(id))
        {
            user_info_searches.get(i).setButton_text(text);
            mAdapter.notifyDataSetInvalidated();
        }
    }
}
    public void send_add(String id)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject jsonObject1=$_Client.getSharedPreferences().getObject("data_signup");
            jsonObject.put($_JSONAttributes.Type.toString(), $_JSONAttributes.Add_friend.toString());
            jsonObject.put($_JSONAttributes.Id.toString(), jsonObject1.getString($_JSONAttributes.Id.toString()));
            jsonObject.put($_JSONAttributes.Add_Friend_Id.toString(),id);
            jsonObject.put($_JSONAttributes.User_Name.toString(),"abod");

            $_Client.getDataOutputStreamOnline().writeUTF(jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("error send data sign up");

        }

    }
    public  void  send_refusal(String id){

        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject jsonObject1=$_Client.getSharedPreferences().getObject("data_signup");
            jsonObject.put($_JSONAttributes.Type.toString(), $_JSONAttributes.Add_friend.toString());
            jsonObject.put($_JSONAttributes.Id.toString(), jsonObject1.getString($_JSONAttributes.Id.toString()));
            jsonObject.put($_JSONAttributes.Add_Friend_Id.toString(),id);
            jsonObject.put($_JSONAttributes.User_Name.toString(),"abod");

            $_Client.getDataOutputStreamOnline().writeUTF(jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("error send data sign up");

        }

    }
    public  void set_action(String Type,String id)
    {
        switch (Type)
        {
            case "Refusal":
            {
                new Friendship_sender().send_To($_Client.getSharedPreferences().getObject("data_signup"),id, $_JSONAttributes.Refusal_friend.toString(),$_JSONAttributes.Refusal_Friend_Id.toString());
                break;
            }
            case "Add":
            {
                new Friendship_sender().send_To($_Client.getSharedPreferences().getObject("data_signup"),id, $_JSONAttributes.Add_friend.toString(),$_JSONAttributes.Add_Friend_Id.toString());
                break;
            }
            case "Remove":
            {
                new Friendship_sender().send_To($_Client.getSharedPreferences().getObject("data_signup"),id, $_JSONAttributes.Remove_Friend.toString(),$_JSONAttributes.Remove_Friend_Id.toString());

                break;
            }
            case  "Remove Request":
            {
                new Friendship_sender().send_To($_Client.getSharedPreferences().getObject("data_signup"),id, $_JSONAttributes.Remove_Request.toString(),$_JSONAttributes.Remove_Request_Id.toString());

                break;
        }
            case  "Accept":
            {
                new Friendship_sender().send_To($_Client.getSharedPreferences().getObject("data_signup"),id, $_JSONAttributes.Accept_Friend.toString(),$_JSONAttributes.Accept_Friend_Id.toString());

                break;
            }
        }
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
        void onFragmentInteraction(Uri uri);
    }
}
