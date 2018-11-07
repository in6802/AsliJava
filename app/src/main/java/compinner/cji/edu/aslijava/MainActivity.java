package compinner.cji.edu.aslijava;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    MyDBOpenHelper dbOpenHelper;
    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd E");
    SimpleDateFormat dateMs = new SimpleDateFormat("yyMMddHHss");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbOpenHelper = MyDBOpenHelper.getInstance(this,"aslijava.db",null,2);

        ((Button)findViewById(R.id.buttonNewOrder)).setOnClickListener(this);
        ((TextView)findViewById(R.id.textViewToday)).setText(date.format(new Date()));
        ((TextView)findViewById(R.id.textViewTodayOrder)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        Bundle bundle = new Bundle();

        switch(v.getId()) {
            case R.id.buttonNewOrder:
                intent = new Intent(this, OrderActivity.class);
                bundle.putString("orderID",dateMs.format(new Date()));
                startActivityForResult(intent, 100);
                break;
            case R.id.textViewTodayOrder:
                intent = new Intent(this, TodayOrderActivity.class);
                startActivityForResult(intent, 200);
                String order_text = dbOpenHelper.readOrder();
                ((TextView)findViewById(R.id.textViewResult)).setText(order_text);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 100:
                    break;
                case 200:
                    break;
            }
        }


    }
}
