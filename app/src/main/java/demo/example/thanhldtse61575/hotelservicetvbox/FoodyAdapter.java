package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.CartItem;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Service;

/**
 * Created by ThanhLDTSE61575 on 3/10/2017.
 */

public class FoodyAdapter extends BaseAdapter{
    private List<Service> list;
    private Context context;
    private LayoutInflater inflater;

    FoodyAdapter(Context context, List<Service> list){
        this.context=context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        View gridView = convertView;
        if(convertView==null){
            inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.layout_gridviewitem,null);
            holder = new ViewHolder();
            holder.image = (ImageView) gridView.findViewById(R.id.imageViewGrid);
            holder.name = (TextView) gridView.findViewById(R.id.textViewGrid);
            holder.price = (TextView) gridView.findViewById(R.id.txtUnitPrice);
            gridView.setTag(holder);
        } else
            holder = (ViewHolder) gridView.getTag();

        String url = list.get(position).getImage();
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.loading)
                .fit()
                .centerCrop().into(holder.image);
        holder.name.setText(list.get(position).getServiceName());
        holder.price.setText(list.get(position).getUnitPrice()+"");
        return gridView;
    }

    class ViewHolder {
        ImageView image;
        TextView name;
        TextView price;
    }
}
