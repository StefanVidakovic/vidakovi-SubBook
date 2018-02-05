package vidakovi.vidakovi_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubDetailsActivity extends AppCompatActivity {
    public Subscription sub;
    private ArrayList<TextView> textViews = new ArrayList<TextView>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_details);

        Intent intent = getIntent();
        String subName = intent.getStringExtra(MainActivity.SUB_MESSAGE);
        Map<String,Subscription> subMap = (HashMap<String,Subscription>) intent.getSerializableExtra(MainActivity.MAP_MESSAGE);
        Subscription sub = subMap.get(subName);
        this.sub = sub;
        TextView name = (TextView) findViewById(R.id.Name);
        name.setText(sub.getName());
        ViewSwitcher vs = (ViewSwitcher) findViewById(R.id.viewSwitcher1);
        vs.showNext();
    }
}
