package in.gajerait.liveena.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import in.gajerait.liveena.R;
import in.gajerait.liveena.utils.ImageLoader;



public class AdapterLive extends ArrayAdapter<ItemDailyKatha> {
    private Activity activity;
    public ImageLoader imageLoader;
    private List<ItemDailyKatha> item;
    ItemDailyKatha object;
    private int row;

    public class ViewHolder {
        public TextView category;
        public ImageView image;
        public TextView name;
        public TextView time;
    }

    public AdapterLive(Activity act, int resource, List<ItemDailyKatha> arrayList, int columnWidth) {
        super(act, resource, arrayList);
        this.activity = act;
        this.row = resource;
        this.item = arrayList;
        this.imageLoader = new ImageLoader(this.activity);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view = convertView;
        if (view == null) {
            view = ((LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(this.row, null);
            holder = new ViewHolder();
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (this.item != null && position + 1 <= this.item.size()) {
            this.object = (ItemDailyKatha) this.item.get(position);
            holder.image = (ImageView) view.findViewById(R.id.picture);
            holder.name = (TextView) view.findViewById(R.id.text);
            if (this.object.getVideoId().equals("000q1w2")) {
                this.imageLoader.DisplayImage("http://apps.bhujmandir.org/SSMB/upload/thumbs/" + this.object.getImageUrl(), holder.image);
            } else {
                this.imageLoader.DisplayImage(new StringBuilder("http://img.youtube.com/vi/").
                        append(this.object.getVideoId()).append("/default.jpg").toString(), holder.image);
            }
            holder.name.setText(this.object.getVideoName());
        }
        return view;
    }
}
