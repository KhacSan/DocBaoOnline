package vn.edu.hust.soict.khacsan.docbaoonline.Model.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.edu.hust.soict.khacsan.docbaoonline.Model.LoaiBao;
import vn.edu.hust.soict.khacsan.docbaoonline.R;

/**
 * Created by San on 12/11/2017.
 */

public class LoaiBaoAdapter extends BaseAdapter {
    ArrayList<LoaiBao> baos;
    Context context;

    public LoaiBaoAdapter(ArrayList<LoaiBao> baos, Context context) {
        this.baos = baos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return baos.size();
    }

    @Override
    public Object getItem(int position) {
        return baos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder holder = null;
        if(convertView == null){
            holder = new viewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_listview_loaibao,null);
            holder.imageViewIcon = convertView.findViewById(R.id.imageLoaiBao);
            holder.textViewTenBao = convertView.findViewById(R.id.textViewTenBao);
            convertView.setTag(holder);
        }else {
            holder = (viewHolder) convertView.getTag();
        }
        LoaiBao loaiBao = (LoaiBao) getItem(position);
        Picasso.with(context).load(loaiBao.getIcon())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.imageerror)
                .into(holder.imageViewIcon);
        holder.textViewTenBao.setText(loaiBao.getTenBao());
        if(loaiBao.isSelected()){
            convertView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        }else  convertView.setBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
        return convertView;
    }

    public void setSelect(int pos){
        for(int i = 0; i < baos.size() ; i++){
            if(i == pos) baos.get(i).setSelected(true);
            else baos.get(i).setSelected(false);
            notifyDataSetChanged();
        }
    }

    private class viewHolder{
        ImageView imageViewIcon;
        TextView textViewTenBao;
    }
}
