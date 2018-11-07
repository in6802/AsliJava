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
    SimpleDateFormat dateTime = new SimpleDateFormat("yyMMddHHmmss");
    SimpleDateFormat today = new SimpleDateFormat("yyMMdd");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbOpenHelper = MyDBOpenHelper.getInstance(this,"aslijava.db",null,1);

        ((Button)findViewById(R.id.buttonNewOrder)).setOnClickListener(this);
        ((TextView)findViewById(R.id.textViewToday)).setText(date.format(new Date()));

        String countDay = today.format(new Date());
        int orderCount = dbOpenHelper.countOrderDay(countDay);
        ((TextView)findViewById(R.id.textViewTodayOrder)).setText("오늘 주문 수 : " + orderCount);
        String order_text = dbOpenHelper.readOrder();
        ((TextView)findViewById(R.id.textViewResult)).setText(order_text);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        Bundle bundle = new Bundle();

        switch(v.getId()) {
            case R.id.buttonNewOrder:
                String orderID = dateTime.format(new Date());
                String menu_ID = "01";
                String orderPkID = orderID + menu_ID;
                dbOpenHelper.insertOrder(orderPkID, orderID, menu_ID,"아메리카노", 1,2000,0);
                menu_ID = "02";
                orderPkID = orderID + menu_ID;
                dbOpenHelper.insertOrder(orderPkID, orderID, menu_ID,"까페라떼", 1,3500,0);

                String order_text = dbOpenHelper.readOrderToday(today.format(new Date()));
                ((TextView)findViewById(R.id.textViewResult)).setText(order_text);

                String countDay = today.format(new Date());
                int orderCount = dbOpenHelper.countOrderDay(countDay);
                ((TextView)findViewById(R.id.textViewTodayOrder)).setText("오늘 주문 수 : " + orderCount);

//                bundle.putString("orderID",dateTime.format(new Date()));
//                intent = new Intent(this, OrderActivity.class);
//                startActivityForResult(intent, 100);
                break;
            case R.id.textViewTodayOrder:
//                intent = new Intent(this, TodayOrderActivity.class);
//                startActivityForResult(intent, 200);
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
