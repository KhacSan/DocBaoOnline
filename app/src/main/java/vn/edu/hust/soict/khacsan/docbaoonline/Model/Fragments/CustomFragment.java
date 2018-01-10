package vn.edu.hust.soict.khacsan.docbaoonline.Model.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import vn.edu.hust.soict.khacsan.docbaoonline.Model.Adapter.TinTucAdapter;
import vn.edu.hust.soict.khacsan.docbaoonline.Model.CheckConnection;
import vn.edu.hust.soict.khacsan.docbaoonline.Model.TinTuc;
import vn.edu.hust.soict.khacsan.docbaoonline.R;
import vn.edu.hust.soict.khacsan.docbaoonline.View.MainActivity;
import vn.edu.hust.soict.khacsan.docbaoonline.View.WebViewActivity;


/**
 * Created by San on 11/18/2017.
 */

@SuppressLint("ValidFragment")
public class CustomFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private ArrayList<TinTuc> tinTucs = null;
    private String title;
    private TinTucAdapter apdapter;
    SwipeRefreshLayout refreshLayout;
    public CustomFragment(String title) {
        this.tinTucs = new ArrayList<>();
        this.title = title;
    }

    public ArrayList<TinTuc> getTinTucs() {
        return tinTucs;
    }

    public void setTinTucs(ArrayList<TinTuc> tinTucs) {
        for(int i = 0; i < tinTucs.size(); i ++) {
            this.tinTucs.add(tinTucs.get(i));
            apdapter.notifyDataSetChanged();
        }
    }

    public String getTitle() {
        return title;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_fragment,container,false);
        ListView listView = view.findViewById(R.id.list_view_tintuc);
        refreshLayout = view.findViewById(R.id.swipeRefresh);
        refreshLayout.setOnRefreshListener(this);

        apdapter =  new TinTucAdapter(getContext(),R.layout.custom_listview_tintuc, this.tinTucs);
        listView.setAdapter(apdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!CheckConnection.internetConnectionCheck(getContext())){
                    Toast.makeText(getContext(),"Lỗi kết nối! vui lòng kiểm tra lại internet...",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getContext(), WebViewActivity.class);
                    intent.putExtra("URL", tinTucs.get(position).getLink());
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    @Override
    public void onRefresh() {
        tinTucs.clear();
        apdapter.notifyDataSetChanged();
        MainActivity.Refresh();
        refreshLayout.setRefreshing(false);
    }


}