package compinner.cji.edu.aslijava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListViewBtnAdapter extends ArrayAdapter implements View.OnClickListener {

    // 버튼 클릭 이벤트를 위한 리스너 인터페이스 정의
    public interface ListBtnClickListener{
        void onListBtnClick(int position);
    }

    int resourceId;
    private ListBtnClickListener listBtnClickListener;

    ListViewBtnAdapter(Context context, int resource, ArrayList<ListViewItem> list, ListBtnClickListener clickListener){
        super(context, resource, list);

        this.resourceId = resource;
        this.listBtnClickListener = clickListener;
    }

    //@androidx.annotation.NonNull
    @Override
    //public View getView(int position, @androidx.annotation.Nullable View convertView, @androidx.annotation.NonNull ViewGroup parent) {public View getView(int position, @androidx.annotation.Nullable View convertView, @androidx.annotation.NonNull ViewGroup parent) {
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.order_list_item, parent, false);
        }

        TextView textViewMenu = convertView.findViewById(R.id.textViewMenu);
        TextView textViewPrice = convertView.findViewById(R.id.textViewPrice);

        //Button buttonIncrese = convertView.findViewById(R.id.buttonIncrese);

        //데이터 셋
        //ListViewItem

        return convertView;
        //return super.getView(position, convertView, parent);
    }

    @Override
    public void onClick(View v) {

    }
}
