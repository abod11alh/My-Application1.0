package com.myhexaville.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.myhexaville.Logic.Client.$_Client;
import com.myhexaville.Logic.JSONData.$_JSON;
import com.myhexaville.Logic.JSONData.$_JSONAttributes;
import com.myhexaville.Logic.JSONData.$_JSON_Accept_Friend_Respons;
import com.myhexaville.Logic.JSONData.$_JSON_Add_Friend_Response;
import com.myhexaville.Logic.JSONData.$_JSON_Friend_Accept_Response;
import com.myhexaville.Logic.JSONData.$_JSON_Friend_Refusal_Request;
import com.myhexaville.Logic.JSONData.$_JSON_Friend_Remove;
import com.myhexaville.Logic.JSONData.$_JSON_Friend_Request;
import com.myhexaville.Logic.JSONData.$_JSON_Message_Image;
import com.myhexaville.Logic.JSONData.$_JSON_Message_Text;
import com.myhexaville.Logic.JSONData.$_JSON_Refusal_Friend_Response;
import com.myhexaville.Logic.JSONData.$_JSON_Remove_Friend_Response;
import com.myhexaville.Logic.JSONData.$_JSON_Remove_Request_Response;
import com.myhexaville.Logic.JSONData.$_JSON_Search_User_Successful;
import com.myhexaville.Logic.JSONData.$_JSON_SignUp_Successful;
import com.myhexaville.Logic.JSONData.$_JSON_SignUp_Tow_Successful;
import com.myhexaville.Logic.ServerManagment.$_CheckOnline;
import com.myhexaville.Logic.ServerManagment.$_CheckReciveData;
import com.myhexaville.Logic.Tools.$_SharedPreferences;
import com.myhexaville.UI.$_Static_Class;
import com.myhexaville.UI.Account.signin_fragment;
import com.myhexaville.UI.Account.signup_fragment;
import com.myhexaville.UI.Account.signup_fragment_tow;
import com.myhexaville.UI.Adapter.AdapterRoomChat.Message.$_Message;
import com.myhexaville.UI.Adapter.AdapterRoomChat.Message.MessageImage.$_Message_Image;
import com.myhexaville.UI.Adapter.AdapterRoomChat.Message.MessageText.$_Message_Text;
import com.myhexaville.UI.Chat.MainFragment.MainChat.main_chat_fragment;
import com.myhexaville.UI.Chat.MainFragment.RoomChat.room_chat;
import com.myhexaville.UI.Chat.MainFragment.main_fragment;
import com.myhexaville.UI.Chat.search.search_fragment;
import com.myhexaville.UI.Notification.notification_fragment;
import com.myhexaville.login.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.myhexaville.login.FlexibleFrameLayout.ORDER_LOGIN_STATE;
import static com.myhexaville.login.FlexibleFrameLayout.ORDER_SIGN_UP_STATE;


public class MainActivity extends AppCompatActivity implements signup_fragment.OnFragmentInteractionListener,
        signin_fragment.OnFragmentInteractionListener,
        signup_fragment_tow.OnFragmentInteractionListener,
        main_fragment.OnFragmentInteractionListener,
        main_chat_fragment.OnFragmentInteractionListener,
        room_chat.OnFragmentInteractionListener,
        search_fragment.OnFragmentInteractionListener,
        notification_fragment.OnFragmentInteractionListener{


    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private boolean isLogin = true;


    public static FragmentManager fragmentManager;
    private static FragmentTransaction fragmentTransaction;
    public static FragmentActivity fragmentActivity;
    public static Context context;
    public static Fragment fragment;
    public static List<$_Message> messages;


    public static search_fragment fragment_search;
    public static notification_fragment fragment_notifiction;
    public static main_fragment fragment_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        context = this;

        signin_fragment topLoginFragment = new signin_fragment();
        signup_fragment topSignUpFragment = new signup_fragment();



        binding.loginFragment.setRotation(-90);

        binding.button.setOnSignUpListener(topSignUpFragment);
        binding.button.setOnLoginListener(topLoginFragment);

        binding.button.setOnButtonSwitched(isLogin -> {
            binding.getRoot()
                    .setBackgroundResource( R.drawable.loginbackground);
        });

        binding.loginFragment.setVisibility(INVISIBLE);


        fragmentActivity = this;
        context = this;
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        $_Client.setSharedPreferences(new $_SharedPreferences("RememberMe"));
        $_Client.getSharedPreferences().removeObject("data_signup");
        messages = new ArrayList<>();

        if (findViewById(R.id.container_main) != null) {
            if (savedInstanceState != null) return;
            if ($_Client.getSharedPreferences().isExist("data_signup") == "") {
              //  fragmentTransaction.add(R.id.continer_main, new signin_fragment(), null).addToBackStack(null).commit();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.login_fragment, topLoginFragment)
                        .replace(R.id.sign_up_fragment, topSignUpFragment)
                        .commit();
            } else {
                fragmentTransaction.add(R.id.container_main, new main_fragment(), null).addToBackStack(null).commit();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //  if (validateEmail() && validatePassword() && validateConfirmPassword()) {
                            $_Client client = new $_Client(context);
                            send_Sign_In();
                            $_Client.setCheckOnline(new $_CheckOnline($_Client.getEmail(), "Check", "online"));
                            get_Recive_Data_And_Apply();
                            //  }
                        } catch (IOException e) {
                            System.err.println("error connect to internet");
                        }
                    }
                });
                thread.start();
            }
        }
    }



    public static void get_Recive_Data_And_Apply() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    final $_CheckReciveData checkReciveData = new $_CheckReciveData();
                    checkReciveData.excute();
                    if (checkReciveData.getResult() != null) {
                        Decode_JSON(checkReciveData);
                    }
                }
            }
        }).start();
    }


    public static void Decode_JSON($_CheckReciveData checkReciveData) {
        JSONObject jsonObject = null;
        $_JSON my_json = null;
        try {
            if(checkReciveData.getResult() instanceof  String)
                jsonObject = new JSONObject((String) checkReciveData.getResult());
            else {
                byte[] bytes = new byte[100];
                $_Client.getDataInputStreamMessage().read(bytes);
                System.out.println("BITMAP = " +  bytes);

            }
            switch (jsonObject.getString("Type")) {
                case "Login_User_Successful": {
                    my_json = new $_JSON_SignUp_Successful("Login_User_Successful", jsonObject.getString($_JSONAttributes.IdRecive.toString()), jsonObject.getBoolean($_JSONAttributes.Done.toString()));
                    final $_JSON finalMy_json = my_json;
                    fragmentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ((($_JSON_SignUp_Successful) finalMy_json).isDone()) {
                                Toast.makeText(context, "Login_User_Successful", Toast.LENGTH_SHORT).show();
                              /*  fragmentTransaction = fragmentManager.beginTransaction();
                                main_fragment main_fragment = new main_fragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("data", finalMy_json.toString());
                                main_fragment.setArguments(bundle);
                                main_fragment.setArguments(bundle);
                                fragmentTransaction.replace(R.id.continer_main, main_fragment).commit();*/
                              Bundle bundle = new Bundle();
                              bundle.putString("fragment", "main_fragment");
                              Intent intent = new Intent(MainActivity.context,SecondActivity.class);
                              intent.putExtras(bundle);
                              MainActivity.fragmentActivity.startActivity(intent);
                            } else {
                                Toast.makeText(context, "Sign Up UN Successfully", Toast.LENGTH_SHORT).show();
                                $_Client.getSharedPreferences().removeObject("data_signup");
                                if ($_Client.getSharedPreferences().isExist("data_signup").equals(""))
                                    $_Client.getSharedPreferences().removeObject("username");
                                $_Client.setEmail("Email");
                                $_Client.setUserName("Username");
                            }
                        }
                    });
                    break;
                }
                case "Sign_Up_Successful": {
                    my_json = new $_JSON_SignUp_Successful("Sign_Up_Successful", jsonObject.getString($_JSONAttributes.IdRecive.toString()), jsonObject.getBoolean($_JSONAttributes.Done.toString()));
                    final $_JSON finalMy_json = my_json;
                    final JSONObject finalJsonObject = new JSONObject();
                    fragmentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ((($_JSON_SignUp_Successful) finalMy_json).isDone()) {
                                Toast.makeText(context, "Sign Up Successfully", Toast.LENGTH_SHORT).show();
                               /* Bundle bundle = new Bundle();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                signup_fragment_tow signup_fragment_tow = new signup_fragment_tow();
                                final JSONObject jsonObject1 = new JSONObject();
                                try {
                                    finalJsonObject.put($_JSONAttributes.Id.toString(), (($_JSON_SignUp_Successful) finalMy_json).getIdReceived());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                bundle.putString("data", finalJsonObject.toString());
                                signup_fragment_tow.setArguments(bundle);
                                fragmentTransaction.replace(R.id.container_main, signup_fragment_tow).commit();*/
                               Intent intent = new Intent(MainActivity.context, SecondActivity.class);
                               Bundle bundle = new Bundle();
                               bundle.putString("fragment","signup_fragment_tow");
                               intent.putExtras(bundle);
                               MainActivity.fragmentActivity.startActivity(intent);
                            } else {
                                Toast.makeText(context, "Sign Up UN Successfully", Toast.LENGTH_SHORT).show();
                                $_Client.getSharedPreferences().removeObject("data_signup");
                                $_Client.getSharedPreferences().removeObject("username");
                            }
                        }
                    });
                    break;
                }
                case "Sign_Up_Tow_Successful": {
                    my_json = new $_JSON_SignUp_Tow_Successful("Sign_Up_Tow_Successful", jsonObject.getString($_JSONAttributes.IdRecive.toString()), jsonObject.getBoolean($_JSONAttributes.Done.toString()), jsonObject.getString("Password"), jsonObject.getString("Image"));
                    final $_JSON finalMy_json = my_json;
                    final JSONObject finalJsonObject1 = jsonObject;
                    fragmentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ((($_JSON_SignUp_Tow_Successful) finalMy_json).isDone()) {
                                Toast.makeText(context, "Sign Up AAAAAL Successfully", Toast.LENGTH_SHORT).show();
                             /*   JSONObject jsonObject1 = new JSONObject();
                                try {
                                    jsonObject1.put("Id", (($_JSON_SignUp_Tow_Successful) finalMy_json).getIdReceived());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Bundle bundle = new Bundle();
                                fragmentTransaction = SecondActivity.fragmentManager.beginTransaction();
                                JSONObject jsonObject2 = new JSONObject();
                                try {
                                    finalJsonObject1.put($_JSONAttributes.Id.toString(), $_Client.getEmail());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                bundle.putString("data", finalJsonObject1.toString());
                                main_fragment.setArguments(bundle);*/
                                main_fragment main_fragment = new main_fragment();
                                SecondActivity.fragmentManager.beginTransaction().replace(R.id.container_main_second, main_fragment).commit();
                            } else {
                                Toast.makeText(context, "Sign Up AAAAAL UN Successfully", Toast.LENGTH_SHORT).show();
                                $_Client.getSharedPreferences().removeObject("data_signup");
                            }
                        }
                    });
                    break;
                }
                case "Message_Text": {
                    my_json = new $_JSON_Message_Text("Message_Text", jsonObject.getString($_JSONAttributes.Id.toString()), jsonObject.getString($_JSONAttributes.IdRecive.toString()), jsonObject.getString("Message"), jsonObject.getString("Time"), jsonObject.getString($_JSONAttributes.User_Name.toString()));
                    if (jsonObject.getString($_JSONAttributes.Id.toString()).equals(jsonObject.getString($_JSONAttributes.IdRecive.toString()))) {

                    } else {
                        $_Message message = new $_Message_Text((($_JSON_Message_Text) my_json).getIdFrom(), (($_JSON_Message_Text) my_json).getUsername(), "3", (($_JSON_Message_Text) my_json).getTime(), jsonObject.getString("Message"));
                        //     if (main_chat_fragment.rooms == null) {
                        // messages.add(message);
                        //   } else {
                        //  if (main_chat_fragment.rooms.size() == 0) {
                        //      messages.add(message);
                        //   } else {
                        //      int size = messages.size();
                        ///      if (size > 0) {
                        //            for (int i = 0; i < size; i++) {
                        //              System.out.println("EEEEEEEE = "  + messages.get(i));
                        //              main_chat_fragment.rooms.get(0).second.addMessage(messages.get(i));
                        //               messages.remove(i);
                        //           }
                        //       }
                  /*      while (main_chat_fragment.rooms == null){

                        }

                        while (main_chat_fragment.rooms.size() == 0){

                        }*/
                        if (main_chat_fragment.rooms == null) {
                            messages.add(message);
                        } else {
                            if (main_chat_fragment.rooms.size() == 0) {
                                messages.add(message);

                            } else {
                                int size = messages.size();
                                System.out.println("SIZE = " + size);
                                for (int i = 0; i < size; i++) {
                                    main_chat_fragment.rooms.get(0).second.addMessage(messages.get(0));
                                    messages.remove(0);
                                }
                                main_chat_fragment.rooms.get(0).second.addMessage(message);

                            }
                        }


                        //          }//
                        //     }
                    }
                    break;
                }

                case "Message_Image": {
                    my_json = new $_JSON_Message_Image("Message_Image", jsonObject.getString($_JSONAttributes.Id.toString()), jsonObject.getString($_JSONAttributes.IdRecive.toString()), jsonObject.getString("Message"), $_Static_Class.getCurrentDate(), jsonObject.getString($_JSONAttributes.User_Name.toString()));
                    if (jsonObject.getString($_JSONAttributes.Id.toString()).equals(jsonObject.getString($_JSONAttributes.IdRecive.toString()))) {
                    } else {
                        System.out.println("B!!!!!!!!! = " + jsonObject);

                        if(jsonObject.get("MType").equals("R")) {
                            System.out.println("A!!!!!!!!!");
                            DataInputStream dataInputStream = new DataInputStream($_Client.getSocketMessage().getInputStream());
                            byte[] bytes = new byte[Integer.parseInt((($_JSON_Message_Image) my_json).getBytes().toLowerCase())];
                            dataInputStream.readFully(bytes);
                            System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ = " + bytes);
                            $_Message message = new $_Message_Image((($_JSON_Message_Image) my_json).getIdFrom(), (($_JSON_Message_Image) my_json).getUsername(), "4", $_Static_Class.getCurrentDate(), bytes);
                            if (main_chat_fragment.rooms == null) {
                                messages.add(message);
                            } else {
                                if (main_chat_fragment.rooms.size() == 0) {
                                    messages.add(message);
                                } else {
                                    int size = messages.size();
                                    System.out.println("SIZE = " + size);
                                    for (int i = 0; i < size; i++) {
                                        main_chat_fragment.rooms.get(0).second.addMessage(messages.get(0));
                                        messages.remove(0);
                                    }
                                    main_chat_fragment.rooms.get(0).second.addMessage(message);
                                }
                            }
                        }

                        //          }//
                        //     }
                    }
                    break;
                }

                case "Search_User_Successful": {
                    JSONArray jsonArray_Ids = jsonObject.getJSONArray("Ids");
                    JSONArray jsonArray_Users_name = jsonObject.getJSONArray("Users_Name");
                    JSONArray jsonArray_State_Users = jsonObject.getJSONArray("State_Users");
                    ArrayList<String> Ids = new ArrayList<>();
                    ArrayList<String> Users_name = new ArrayList<>();
                    ArrayList<String> State_Users = new ArrayList<>();

                    for (int i = 0; i < jsonArray_Ids.length(); i++) {
                        Ids.add(jsonArray_Ids.getString(i));
                        Users_name.add(jsonArray_Users_name.getString(i));
                        State_Users.add(jsonArray_State_Users.getString(i));

                    }
                    my_json = new $_JSON_Search_User_Successful("Search_User_Successful", jsonObject.getString($_JSONAttributes.IdRecive.toString()), jsonObject.getBoolean($_JSONAttributes.Done.toString()), Ids, Users_name, State_Users);
                    final $_JSON_Search_User_Successful finalMy_json = ($_JSON_Search_User_Successful) my_json;
                    final JSONObject finalJsonObject = jsonObject;
                    MainActivity.fragmentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((search_fragment) MainActivity.fragment_search).set_list_show(finalMy_json);
                        }
                    });

                    break;
                }
                case "Friend_Request": {
                    System.out.println("ggg");
                    System.out.println(jsonObject.toString());
                    my_json = new $_JSON_Friend_Request("Friend_Request", jsonObject.getString($_JSONAttributes.IdRecive.toString()), jsonObject.getString($_JSONAttributes.Id_Friend_Request.toString()), jsonObject.getString($_JSONAttributes.user_friend_request.toString()));
                    final $_JSON_Friend_Request finalMy_json1 = ($_JSON_Friend_Request) my_json;
                    MainActivity.fragmentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MainActivity.fragment_notifiction.prepare_friend_request_notifiction(finalMy_json1);
                            Toast.makeText(context, "Friend_Request", Toast.LENGTH_SHORT).show();

                        }
                    });


                    break;
                }
                case "Add_Friend_Response": {
                    my_json = new $_JSON_Add_Friend_Response("Add_Friend_Response", jsonObject.getString($_JSONAttributes.IdRecive.toString()), jsonObject.getBoolean($_JSONAttributes.Done.toString()), jsonObject.getString($_JSONAttributes.Add_Friend_Id.toString()));
                    final $_JSON finalMy_json1 = my_json;
                    MainActivity.fragmentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            MainActivity.fragment_search.Edit_button((($_JSON_Add_Friend_Response) finalMy_json1).getId_user(), "Remove Request");
                        }
                    });
                    break;
                }
                case "Friend_Refusal_Request": {
                    my_json = new $_JSON_Friend_Refusal_Request("Friend_Refusal_Request", jsonObject.getString($_JSONAttributes.IdRecive.toString()), jsonObject.getString($_JSONAttributes.Id_Friend_Refusal_Request.toString()), jsonObject.getString($_JSONAttributes.user_friend_request.toString()));
                    MainActivity.fragmentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                    break;
                }
                case "Refusal_Friend_Response": {
                    my_json = new $_JSON_Refusal_Friend_Response("Refusal_Friend_Response", jsonObject.getString($_JSONAttributes.IdRecive.toString()), jsonObject.getBoolean($_JSONAttributes.Done.toString()), jsonObject.getString($_JSONAttributes.Refusal_Friend_Id.toString()));
                    MainActivity.fragmentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Test", Toast.LENGTH_SHORT).show();

                        }
                    });
                    break;
                }
                case "Accept_Friend_Response": {
                    my_json = new $_JSON_Accept_Friend_Respons("Accept_Friend_Response", jsonObject.getString($_JSONAttributes.IdRecive.toString()), jsonObject.getBoolean($_JSONAttributes.Done.toString()), jsonObject.getString($_JSONAttributes.Accept_Friend_Id.toString()));
                    MainActivity.fragmentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Test", Toast.LENGTH_SHORT).show();

                        }
                    });

                    break;
                }
                case "Friend_Accept_Response": {
                    my_json = new $_JSON_Friend_Accept_Response("Friend_Accept_Response", jsonObject.getString($_JSONAttributes.IdRecive.toString()), jsonObject.getString($_JSONAttributes.Id_Friend_Accept_Response.toString()), jsonObject.getString($_JSONAttributes.user_friend_request.toString()));

                    MainActivity.fragmentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                    break;
                }
                case "Remove_Friend_Response": {
                    my_json = new $_JSON_Remove_Friend_Response("Remove_Friend_Response", jsonObject.getString($_JSONAttributes.IdRecive.toString()), jsonObject.getBoolean($_JSONAttributes.Done.toString()), jsonObject.getString($_JSONAttributes.Remove_Friend_Id.toString()));
                    MainActivity.fragmentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Test", Toast.LENGTH_SHORT).show();

                        }
                    });
                    break;
                }
                case "Friend_Remove_Response": {
                    my_json = new $_JSON_Friend_Remove("Friend_Remove_Response", jsonObject.getString($_JSONAttributes.IdRecive.toString()), jsonObject.getString($_JSONAttributes.Id_Friend_Remove_Response.toString()), jsonObject.getString($_JSONAttributes.user_friend_request.toString()));
                    MainActivity.fragmentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                    break;
                }
                case "Remove_Request_Response": {
                    my_json = new $_JSON_Remove_Request_Response("Remove_Request_Response", jsonObject.getString($_JSONAttributes.IdRecive.toString()), jsonObject.getBoolean($_JSONAttributes.Done.toString()), jsonObject.getString($_JSONAttributes.Remove_Request_Id.toString()));
                    MainActivity.fragmentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Test", Toast.LENGTH_SHORT).show();

                        }
                    });
                    break;
                }
                case "Friend_Remove_Request": {
                    my_json = new $_JSON_Friend_Remove("Friend_Remove_Request", jsonObject.getString($_JSONAttributes.IdRecive.toString()), jsonObject.getString($_JSONAttributes.Id_Request_Remove_Response.toString()), jsonObject.getString($_JSONAttributes.user_friend_request.toString()));
                    MainActivity.fragmentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });

                    break;
                }

                default:
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void send_Sign_In() {
        JSONObject res = $_Client.getSharedPreferences().getObject("data_signup");
        JSONObject jsonObject = new JSONObject();
        if (res != null) {
            try {
                jsonObject.put($_JSONAttributes.Type.toString(), "Login_User");
                jsonObject.put($_JSONAttributes.Id.toString(), res.getString($_JSONAttributes.Id.toString()));
                System.out.println("id = " + res.getString($_JSONAttributes.Id.toString()));
                jsonObject.put($_JSONAttributes.Password.toString(), res.getString($_JSONAttributes.Password.toString()));
                $_Client.getDataOutputStreamOnline().writeUTF(jsonObject.toString());
                $_Client.setEmail(res.getString($_JSONAttributes.Id.toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println("error send data sign up");
            }

        } else {

        }

    }




    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        binding.loginFragment.setPivotX(binding.loginFragment.getWidth() / 2);
        binding.loginFragment.setPivotY(binding.loginFragment.getHeight());
        binding.signUpFragment.setPivotX(binding.signUpFragment.getWidth() / 2);
        binding.signUpFragment.setPivotY(binding.signUpFragment.getHeight());
    }

    public void switchFragment(View v) {
        if (isLogin) {
            binding.loginFragment.setVisibility(VISIBLE);
            binding.loginFragment.animate().rotation(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    binding.signUpFragment.setVisibility(INVISIBLE);
                    binding.signUpFragment.setRotation(90);
                    binding.wrapper.setDrawOrder(ORDER_LOGIN_STATE);
                }
            });
        } else {
            binding.signUpFragment.setVisibility(VISIBLE);
            binding.signUpFragment.animate().rotation(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    binding.loginFragment.setVisibility(INVISIBLE);
                    binding.loginFragment.setRotation(-90);
                    binding.wrapper.setDrawOrder(ORDER_SIGN_UP_STATE);
                }
            });
        }

        isLogin = !isLogin;
        binding.button.startAnimation();
    }

    @Override
    public void onFragmentInteraction(String json, String id) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}