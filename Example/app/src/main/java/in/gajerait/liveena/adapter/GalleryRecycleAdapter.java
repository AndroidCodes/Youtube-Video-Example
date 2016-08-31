package in.gajerait.liveena.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import in.gajerait.liveena.R;
import in.gajerait.liveena.bean.BeanGallery;
import in.gajerait.liveena.ui.FullScreenViewActivity;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;


public class GalleryRecycleAdapter extends RecyclerView.Adapter<GalleryRecycleAdapter.AvailViewHolder> {

    Context context;
    ArrayList<BeanGallery> vouchers;
    String type;
    View v;

    public GalleryRecycleAdapter(Context context, ArrayList<BeanGallery> vouchers, String type) {
        this.context = context;
        this.vouchers = vouchers;
        this.type = type;
    }

    @Override
    public AvailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);

        return new AvailViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AvailViewHolder holder, final int position) {

        Glide.with(context).load(vouchers.get(position).getVoucher_image()).placeholder(R.drawable.lodar).into(holder.imgGallery);

        holder.progressBar.setVisibility(View.VISIBLE);
        holder.llGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, FullScreenViewActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("gallery", vouchers);
                context.startActivity(intent);
//                Toast.makeText(context, "" + vouchers.get(position).getCategory(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return vouchers.size();
    }

    public class AvailViewHolder extends RecyclerView.ViewHolder {

        ImageView imgGallery;
        LinearLayout llGallery;
        MaterialProgressBar progressBar;

        public AvailViewHolder(View itemView) {
            super(itemView);
            imgGallery = (ImageView) itemView.findViewById(R.id.imgGallery);

            llGallery = (LinearLayout) itemView.findViewById(R.id.llGallery);
            progressBar = (MaterialProgressBar) itemView.findViewById(R.id.progressBar);

        }
    }

}
