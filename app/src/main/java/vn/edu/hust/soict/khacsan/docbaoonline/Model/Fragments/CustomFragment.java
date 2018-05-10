package vn.edu.hust.soict.khacsan.docbaoonline.Model.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;

import java.util.ArrayList;

import vn.edu.hust.soict.khacsan.docbaoonline.Model.Adapter.TinTucAdapter;
import vn.edu.hust.soict.khacsan.docbaoonline.Model.CheckConnection;
import vn.edu.hust.soict.khacsan.docbaoonline.Model.ReadRSS;
import vn.edu.hust.soict.khacsan.docbaoonline.Model.TinTuc;
import vn.edu.hust.soict.khacsan.docbaoonline.R;
import vn.edu.hust.soict.khacsan.docbaoonline.View.MainActivity;
import vn.edu.hust.soict.khacsan.docbaoonline.View.WebViewActivity;

import static com.chad.library.adapter.base.BaseQuickAdapter.SLIDEIN_LEFT;


/**
 * Created by San on 11/18/2017.
 */

public class CustomFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private String title,link;
    private TinTucAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private LinearLayout placeError;

    public CustomFragment() {
    }

    public static CustomFragment newInstance(String title,String link) {
        Bundle args = new Bundle();
        args.putString("LINK",link);
        args.putString("TITLE",title);
        CustomFragment fragment = new CustomFragment();
        fragment.setTitle(title);
        fragment.setArguments(args);
        return fragment;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle != null){
            link = bundle.getString("LINK");
            title = bundle.getString("TITLE");
        }

        RecyclerView listTinTuc = view.findViewById(R.id.list_view_tintuc);
        refreshLayout = view.findViewById(R.id.swipeRefresh);
        refreshLayout.setOnRefreshListener(this);

        placeError = view.findViewById(R.id.network_error);

        listTinTuc.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        listTinTuc.setHasFixedSize(true);
        adapter = new TinTucAdapter(getContext(), R.layout.custom_listview_tintuc, null);
        listTinTuc.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("URL",((TinTuc) adapter.getItem(position)).getLink());
                startActivity(intent);
            }
        });

        adapter.openLoadAnimation(SLIDEIN_LEFT);
        adapter.isFirstOnly(false);

        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
        itemTouchHelper.attachToRecyclerView(listTinTuc);

// open drag
        adapter.enableDragItem(itemTouchHelper);
//        apdapter.setOnItemDragListener(onItemDragListener);
//        open slide to delete
//        apdapter.enableSwipeItem();
//        apdapter.setOnItemSwipeListener(onItemSwipeListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        getNews();
    }

    @Override
    public void onRefresh() {
        adapter.setNewData(null);
        getNews();
    }

    public void getNews(){
        placeError.setVisibility(View.GONE);
        refreshLayout.setRefreshing(true);
        new ReadRSS(new ReadRSS.getNewsCallback() {
            @Override
            public void execute(ArrayList<TinTuc> tinTucs) {
                refreshLayout.setRefreshing(false);
                if(CheckConnection.internetConnectionCheck(getContext()))
                    adapter.setNewData(tinTucs);
                else placeError.setVisibility(View.VISIBLE);
            }
        }).execute(link);
    }


}