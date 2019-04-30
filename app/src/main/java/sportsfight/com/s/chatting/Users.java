package sportsfight.com.s.chatting;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import sportsfight.com.s.R;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.interfaces.WebApiResponseCallback;
import sportsfight.com.s.util.Util;

public class Users extends AppCompatActivity implements WebApiResponseCallback {
    ListView usersList;
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;
    AppController controller;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        controller=(AppController)getApplicationContext();
        usersList = (ListView) findViewById(R.id.usersList);
        noUsersText = (TextView) findViewById(R.id.noUsersText);
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        pd = new ProgressDialog(Users.this);
        pd.setMessage("Loading...");
        pd.show();


//       for(int i=0;i<10;i++) {
//           writeNewUser(Integer.toString(i), "Ashish1", "Ashish1@gmail.com");
//       }
        String url = "https://sportsfight-5cacd.firebaseio.com/users.json";
         controller.getApiCall().getData(url,"",this);
        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDetails.chatWith = al.get(position);
                UserDetails.username="7-Ashish1";
                startActivity(new Intent(Users.this, Chat.class));
            }
        });
    }

    private void writeNewUser(String userId, String name, String email) {
        UserModel details= new UserModel(userId, name,  email, Util.getCurrentDate());
        mDatabase.child(userId).setValue(details);
    }
    public void doOnSuccess(String s) {
        try {
            JSONArray jsonArray=new JSONArray(s);
            for(int i=1;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                al.add(jsonObject.getString("userId") +"-"+jsonObject.getString("username"));
                totalUsers++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (totalUsers <= 1) {
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        } else {
            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
        }
        pd.dismiss();
    }

    @Override
    public void onSucess(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                doOnSuccess(value);
                pd.cancel();
            }
        });

    }

    @Override
    public void onError(String value) {
       runOnUiThread(new Runnable() {
    @Override
    public void run() {
        pd.cancel();
    }
});
    }
}
