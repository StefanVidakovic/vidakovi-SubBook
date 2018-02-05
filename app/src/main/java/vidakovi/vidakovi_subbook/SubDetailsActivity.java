package vidakovi.vidakovi_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubDetailsActivity extends AppCompatActivity {
    public Subscription sub;
    public boolean addFlag = false;
//    private ArrayList<TextView> textViews = new ArrayList<TextView>();
    public static final String NEW_SUB = "new sub";
    public static final String OLD_SUB = "old sub";
    public static final String DELETE_SUB = "delete sub";
    public static final String ADD_SUB = "add";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_details);

        Intent intent = getIntent();
        ViewSwitcher vs = (ViewSwitcher) findViewById(R.id.viewSwitcher1);


        if(!intent.hasExtra(MainActivity.ADD_MESSAGE)) {
            Subscription sub = (Subscription)intent.getSerializableExtra(MainActivity.SUB_MESSAGE);
            this.sub = sub;
//            sub.setComment("Netflix is the best");
            TextView name = (TextView) findViewById(R.id.Name);
            name.setText(sub.getName());
            TextView charge = (TextView) findViewById(R.id.Charge);
            charge.setText("$" + sub.getMonthlyCharge() + "/month");
            TextView date = (TextView) findViewById(R.id.Date);
            date.setText(sub.getStartDate());
            TextView comment = (TextView) findViewById(R.id.Comment);
            comment.setText(sub.getComment());
        }
        Button edit = (Button) findViewById(R.id.editButton);
        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                editSub(v);
            }
        });
        Button save = (Button) findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSub(v);
            }
        });
        Button cancel = (Button) findViewById(R.id.cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCancel(v);
            }
        });
        Button delete = (Button) findViewById(R.id.deleteButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSub(v);
            }
        });
        if(intent.hasExtra(MainActivity.ADD_MESSAGE)){
            editSub(edit);
            this.addFlag = true;
            this.sub = new Subscription("name","0000-00-00","0.0");
        }
    }

    public void editSub(View v){
        Button editButton = (Button) findViewById(R.id.editButton);
        editButton.setEnabled(false);
        ViewSwitcher vs1 = (ViewSwitcher) findViewById(R.id.viewSwitcher1);
        vs1.showNext();
        ViewSwitcher vs2 = (ViewSwitcher) findViewById(R.id.viewSwitcher2);
        vs2.showNext();
        ViewSwitcher vs3 = (ViewSwitcher) findViewById(R.id.viewSwitcher3);
        vs3.showNext();
        ViewSwitcher vs4 = (ViewSwitcher) findViewById(R.id.viewSwitcher4);
        vs4.showNext();
    }

    public void saveSub(View v){
        boolean flag1 = true;
        boolean flag2 = true;
        boolean flag3 = true;
        boolean flag4 = true;
        Button editButton  = (Button) findViewById(R.id.editButton);
        editButton.setEnabled(false);
        EditText editName = (EditText) findViewById(R.id.editName);
        String oldSubName = null;
        if(!addFlag) {
            oldSubName = this.sub.getName();
        }
        if(!editName.getText().toString().matches("")
                &&this.sub.checkName(editName.getText().toString())) {
            this.sub.setName(editName.getText().toString());
            TextView name = (TextView) findViewById(R.id.Name);
            name.setText(editName.getText());
            editName.getText().clear();

        }

        else{
            flag1 = false;
        }


        EditText editCharge = (EditText) findViewById(R.id.editCharge);
        if(!editCharge.getText().toString().matches("")
                &&this.sub.checkMonthlyCharge(editCharge.getText().toString())) {
            this.sub.setMonthlyCharge(editCharge.getText().toString());
            TextView charge = (TextView) findViewById(R.id.Charge);
            charge.setText(editCharge.getText());
            editCharge.getText().clear();

        }

        else{
            flag2 = false;
        }

        EditText editDate = (EditText) findViewById(R.id.editDate);
        if(!editDate.getText().toString().matches("")
                &&this.sub.checkStartDate(editDate.getText().toString())){
            this.sub.setStartDate(editDate.getText().toString());
            TextView date = (TextView) findViewById(R.id.Date);
            date.setText(editDate.getText());
            editDate.getText().clear();

        }

        else{
            flag3 = false;
        }

        EditText editComment = (EditText) findViewById(R.id.editComment);
        if(!editComment.getText().toString().matches("")){
            this.sub.setComment(editComment.getText().toString());
            TextView comment = (TextView) findViewById(R.id.Comment);
            comment.setText(editComment.getText());
            editComment.getText().clear();

        }
        else if(editComment.getText().toString().matches("")){
            flag4 = true;
        }
        else{
            flag4 = false;
        }
        if(flag1&&flag2&&flag3&&flag4&&!addFlag) {
            editButton.setEnabled(true);
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra(OLD_SUB,oldSubName);
            intent.putExtra(NEW_SUB,this.sub);
            startActivity(intent);
        }
        if(flag1&&flag2&&flag3&&flag4&&addFlag){
            this.addFlag = false;
            editButton.setEnabled(true);
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra(NEW_SUB,this.sub);
            intent.putExtra(ADD_SUB,"add");
            startActivity(intent);
        }
    }

    public void onClickCancel(View v){
        Button editButton = (Button) findViewById(R.id.editButton);
        if(editButton.isEnabled()){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        else {
            EditText editName = (EditText) findViewById(R.id.editName);
            editName.getText().clear();
            ViewSwitcher vs1 = (ViewSwitcher) findViewById(R.id.viewSwitcher1);
            vs1.showPrevious();
            EditText editCharge = (EditText) findViewById(R.id.editCharge);
            editCharge.getText().clear();
            ViewSwitcher vs2 = (ViewSwitcher) findViewById(R.id.viewSwitcher2);
            vs2.showPrevious();
            EditText editDate = (EditText) findViewById(R.id.editDate);
            editDate.getText().clear();
            ViewSwitcher vs3 = (ViewSwitcher) findViewById(R.id.viewSwitcher3);
            vs3.showPrevious();
            EditText editComment = (EditText) findViewById(R.id.editComment);
            editComment.getText().clear();
            ViewSwitcher vs4 = (ViewSwitcher) findViewById(R.id.viewSwitcher4);
            vs4.showPrevious();
            editButton.setEnabled(true);
        }
    }

    public void deleteSub(View v){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(DELETE_SUB,this.sub.getName());
        startActivity(intent);
    }

}
