package in.gajerait.liveena.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import in.gajerait.liveena.R;


@SuppressLint("InflateParams")
public class GridViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> titleArrayList;
    private ArrayList<Integer> iconArrayList;
    private LayoutInflater inflater;

    public GridViewAdapter(Context context, ArrayList<String> titleArrayList, ArrayList<Integer> iconArrayList) {

        this.context = context;
        this.titleArrayList = titleArrayList;
        this.iconArrayList = iconArrayList;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return titleArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return titleArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return titleArrayList.size();
    }

    static class ViewHolder {

        public TextView textViewName;
        public ImageView imageViewIcon;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_view_item, null);
            viewHolder = new ViewHolder();

            viewHolder.imageViewIcon = (ImageView) convertView
                    .findViewById(R.id.grid_item_image);
            viewHolder.textViewName = (TextView) convertView
                    .findViewById(R.id.grid_item_title);

            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }
        if (position == 0 || position == 1 || position == 2) {
            viewHolder.textViewName.setVisibility(View.INVISIBLE);
            viewHolder.textViewName.setTextSize(1);

        }  else {
            viewHolder.textViewName.setVisibility(View.VISIBLE);
        }

        viewHolder.textViewName.setText(titleArrayList.get(position));

        Glide.with(context).load(iconArrayList.get(position)).into(viewHolder.imageViewIcon);

        return convertView;
    }
}
