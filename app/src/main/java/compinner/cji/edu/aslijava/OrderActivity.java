package compinner.cji.edu.aslijava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {

    TextView textViewOrderID, textViewMenu, textViewPrice, textViewCount, textViewTotal;
    Button buttonIncrease, buttonDecrease, buttonClear, buttonComplete, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //인텐드 받아오기


        //디자인과 연결
        // - 오더 번호
        textViewOrderID = findViewById(R.id.textViewOrderID);
        // - 리스트 아이템
        textViewMenu = findViewById(R.id.textViewMenu);
        textViewPrice = findViewById(R.id.textViewPrice);
        buttonIncrease = findViewById(R.id.buttonIncrease);
        buttonDecrease = findViewById(R.id.buttonDecrease);
        textViewCount = findViewById(R.id.textViewCount);
        buttonClear = findViewById(R.id.buttonClear);
        // - 합계
        textViewTotal = findViewById(R.id.textViewTotal);
        // - 버튼
        buttonComplete = findViewById(R.id.buttonComplete);
        buttonCancel = findViewById(R.id.buttonCancel);


        //오더 번호
        textViewOrderID.setText(2);

        //메뉴 리스트 채우기
        addListItem();
    }

    public void addListItem(){
        String[] MENU = new String[]{"아메리카노", "카페라떼", "바닐라라떼"};
        Integer[] PRICE = new Integer[]{1000, 2000, 2500};

        ListView menuListView = findViewById(R.id.ListViewMenu);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.order_list_item, MENU);
        menuListView.setAdapter(arrayAdapter);
    }

    @Override
    public void finish() {
        super.finish();
    }
}
