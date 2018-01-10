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

import com.squareup.picasso.Picasso;

import java.util.List;

import vn.edu.hust.soict.khacsan.docbaoonline.Model.TinTuc;
import vn.edu.hust.soict.khacsan.docbaoonline.R;

/**
 * Created by San on 12/11/2017.
 */

public class TinTucAdapter extends ArrayAdapter<TinTuc> {

    public TinTucAdapter(@NonNull Context context, int resource, List<TinTuc> list) {
        super(context, resource,list);
    }

    class viewHolder{
        ImageView imageView;
        TextView textViewTitle,textViewDate,textViewDescription;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        viewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_listview_tintuc,null);
            holder = new viewHolder();
            holder.imageView = convertView.findViewById(R.id.imageTinTuc);
            holder.textViewTitle = convertView.findViewById(R.id.text_title_tintuc);
            holder.textViewDate = convertView.findViewById(R.id.text_date_ngaydang);
            holder.textViewDescription = convertView.findViewById(R.id.text_description);
            convertView.setTag(holder);
        }else {
            holder = (viewHolder) convertView.getTag();
        }
        TinTuc tinTuc = getItem(position);
        if(tinTuc != null){
            try {
                Picasso.with(getContext()).load(tinTuc.getImage())
                        .placeholder(R.drawable.noimage)
                        .error(R.drawable.imageerror)
                        .into(holder.imageView);
                holder.textViewTitle.setText(tinTuc.getTitle());
                holder.textViewDate.setText(tinTuc.getDate());
                holder.textViewDescription.setText(tinTuc.getDescription());
            }catch(IllegalArgumentException e){
                Log.d("Error",e.getMessage());
            }
        }
        return convertView;
    }
}

