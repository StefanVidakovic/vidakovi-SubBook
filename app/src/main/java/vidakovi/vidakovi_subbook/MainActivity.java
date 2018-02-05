package vidakovi.vidakovi_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public Map<Button,Subscription> buttonToSub = new HashMap<Button, Subscription>();
    public Map<String,Subscription> subMap = new HashMap<String,Subscription>();
    public static final String MAP_MESSAGE = "subMap";
    public static final String SUB_MESSAGE = "subName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linLayout = findViewById(R.id.LinearLayout);
        LinearLayout.LayoutParams lpview1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpview1.gravity = Gravity.CENTER_HORIZONTAL;
        LinearLayout.LayoutParams lpview2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpview2.gravity = Gravity.CENTER_HORIZONTAL;

        SubscriptionList subs = new SubscriptionList();
        Subscription netflixSub = new Subscription("Netflix","2016-06-18","8.00");
        subs.add(netflixSub);
        Subscription huluSub = new Subscription("Hulu","2016-06-18","9.00");
        subs.add(huluSub);

        Double totalCharges = subs.getTotalMonthlyCharges();

        TextView tCharge = new TextView(this);
        tCharge.setText("Total Monthly Charge: $"+totalCharges.toString()+"/month");
        tCharge.setLayoutParams(lpview1);
        linLayout.addView(tCharge);


        int id = 0;
        for(Subscription sub : subs.subList){
            Button button = new Button(this);
            button.setText(sub.getName()+": $"+sub.getMonthlyCharge().toString()+"/month");
            button.setId(id);
            id++;
            buttonToSub.put(button,sub);
            subMap.put(sub.getName(),sub);
            button.setLayoutParams(lpview2);
            button.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    goToDetails(v);
                }
            });

            linLayout.addView(button);
        }


    }

    public void goToDetails(View v){
        int id = v.getId();
        Button button = findViewById(id);
        Subscription sub = this.buttonToSub.get(button);
        Intent intent = new Intent(this,SubDetailsActivity.class);
        intent.putExtra(SUB_MESSAGE,sub.getName());
        intent.putExtra(MAP_MESSAGE, (Serializable) this.subMap);
        startActivity(intent);
    }
}
