package vidakovi.vidakovi_subbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.reflect.TypeToken;


//Main activity which lists subscriptions
public class MainActivity extends AppCompatActivity {
    public Map<Button,Subscription> buttonToSub = new HashMap<Button, Subscription>();
    public Map<String,Subscription> subMap = new HashMap<String,Subscription>();
    public SubscriptionList subs;
    public static final String SUB_MESSAGE = "subName";
    public static final String ADD_MESSAGE = "adding new sub flag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();


        LinearLayout linLayout = findViewById(R.id.LinearLayout);
        LinearLayout.LayoutParams lpview2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpview2.gravity = Gravity.CENTER_HORIZONTAL;

        if(retrieveList().nullIdentifier!=0) {
            this.subs = new SubscriptionList();
        }
        else{
            this.subMap = retrieveMap();
            this.subs = retrieveList();
        }

        if(intent.hasExtra(SubDetailsActivity.ADD_SUB)){
            Subscription newSub = (Subscription) intent.getSerializableExtra(SubDetailsActivity.NEW_SUB);
            this.subs.add(newSub);
            this.subMap.put(newSub.getName(),newSub);
            write();
        }
        if(intent.hasExtra(SubDetailsActivity.DELETE_SUB)&&!intent.hasExtra(SubDetailsActivity.ADD_SUB)){
            String removedSub = intent.getStringExtra(SubDetailsActivity.DELETE_SUB);
            this.subs.subList.remove(this.subMap.get(removedSub));
            this.subMap.remove(removedSub);
            write();
        }
        if(intent.hasExtra(SubDetailsActivity.OLD_SUB)&&intent.hasExtra(SubDetailsActivity.NEW_SUB)&&!intent.hasExtra(SubDetailsActivity.ADD_SUB)) {
            Subscription newSub = (Subscription) intent.getSerializableExtra(SubDetailsActivity.NEW_SUB);
            String oldSubName = intent.getStringExtra(SubDetailsActivity.OLD_SUB);
            this.subs.subList.remove(this.subMap.get(oldSubName));
            this.subMap.remove(oldSubName);
            this.subs.add(newSub);
            this.subMap.put(newSub.getName(), newSub);
            write();
        }
        Double totalCharges = subs.getTotalMonthlyCharges();

        TextView tCharge = (TextView) findViewById(R.id.totalCharges);
        tCharge.setText("Total Monthly Charges: $"+totalCharges.toString()+"/month");

        int id = 0;
        for(Subscription sub : this.subs.subList){
            Button button = new Button(this);
            button.setText(sub.getName()+": $"+sub.getMonthlyCharge().toString()+"/month"+"\n\t"
                +sub.getStartDate());
            button.setId(id);
            id++;
            buttonToSub.put(button,sub);
//            subMap.put(sub.getName(),sub);
            button.setLayoutParams(lpview2);
            button.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    goToDetails(v);
                }
            });

            linLayout.addView(button);
        }
        Button addButton = (Button)  findViewById(R.id.addButton);
        addButton.setText("Add");
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSub(v);
            }
        });
//        if(retrieveList()==null){
//            addButton.setText("TEST");
//        }

    }


    public void goToDetails(View v){
        write();
        int id = v.getId();
        Button button = findViewById(id);
        Subscription sub = this.buttonToSub.get(button);
        Intent intent = new Intent(this,SubDetailsActivity.class);
        intent.putExtra(SUB_MESSAGE,sub);
        startActivity(intent);
    }

    public void addSub(View v){
        write();
        Intent intent = new Intent(this,SubDetailsActivity.class);
        intent.putExtra(ADD_MESSAGE,"add");
        startActivity(intent);
    }

    public void write(){
        try {
            InternalStorage.writeObject(this, "SubList", this.subs);
            InternalStorage.writeObject(this,"SubMap",this.subMap);
        }
        catch(IOException e){
            return;
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        write();
    }
    @Override
    protected void onStop(){
        super.onStop();
       write();
    }
    public SubscriptionList retrieveList(){
        try{
            SubscriptionList subList = (SubscriptionList) InternalStorage.readObject(this,"SubList");
            return subList;
        }
        catch(ClassNotFoundException e){
            SubscriptionList nullSubList = new SubscriptionList();
            nullSubList.nullIdentifier = 0;
            return nullSubList;
        }
        catch(IOException e){
            SubscriptionList nullSubList = new SubscriptionList();
            nullSubList.nullIdentifier = 0;
            return nullSubList;
        }
    }
    public HashMap<String,Subscription> retrieveMap(){
        try{
            HashMap<String,Subscription> subMap = (HashMap<String,Subscription>) InternalStorage.readObject(this,"SubMap");
            return subMap;
        }
        catch(ClassNotFoundException e){
            return new HashMap<String, Subscription>();
        }
        catch(IOException e){
            return new HashMap<String, Subscription>();
        }
    }
}
