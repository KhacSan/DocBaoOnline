package vn.edu.hust.soict.khacsan.docbaoonline.Model.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import vn.edu.hust.soict.khacsan.docbaoonline.Model.TinTuc;
import vn.edu.hust.soict.khacsan.docbaoonline.R;

/**
 * Created by San on 12/11/2017.
 */

public class TinTucAdapter extends BaseItemDraggableAdapter<TinTuc,BaseViewHolder> {

    private Context mContext;
    public TinTucAdapter(Context context,int layoutResId, List<TinTuc> data) {
        super(layoutResId, data);
        mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, TinTuc item) {
        helper.setText(R.id.text_item_title_tintuc,item.getTitle())
                .setText(R.id.text_item_date,item.getDate());
        Picasso.with(mContext)
                .load(item.getImage())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.imageerror)
                .fit()
                .into((ImageView) helper.getView(R.id.image_item_tintuc));
    }
}

