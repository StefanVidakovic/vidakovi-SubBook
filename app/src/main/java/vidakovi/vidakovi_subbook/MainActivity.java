package vidakovi.vidakovi_subbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SubscriptionList subs = new SubscriptionList();
        Subscription netflix = new Subscription("Netflix","2016-06-18","8.00");
        subs.subList.add(netflix);
        Double totalCharges = netflix.getMonthlyCharge();
        TextView tCharge = findViewById(R.id.totalCharges);
        tCharge.setText("Total Monthly Charges: "+"$8.00");
    }
}
