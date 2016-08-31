package in.gajerait.liveena.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import in.gajerait.liveena.R;
import in.gajerait.liveena.bean.BeanGallery;
import in.gajerait.liveena.utils.TouchImageView;

/**
 * Created by HP-PC : Hardik/Sagar on 01/19/2016.
 */
public class FullScreenImageAdapter extends PagerAdapter {

    private Activity _activity;
    private ArrayList<BeanGallery> imagePaths;
    private LayoutInflater inflater;
    // constructor
    public FullScreenImageAdapter(Activity activity,
                                  ArrayList<BeanGallery> imagePaths) {
        this._activity = activity;
        this.imagePaths = imagePaths;
        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return this.imagePaths.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        TouchImageView imgDisplay;

        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                false);

        imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);
        Glide.with(_activity).load(imagePaths.get(position).getVoucher_image()).placeholder(R.drawable.lodar).into(imgDisplay);
        container.addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);

    }

}