package in.gajerait.liveena.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import in.gajerait.liveena.R;
import in.gajerait.liveena.bean.BeanGallery;
import in.gajerait.liveena.ui.WebviewActivity;


public class SponsorRecycleAdapter extends RecyclerView.Adapter<SponsorRecycleAdapter.AvailViewHolder> {

    Context context;
    ArrayList<BeanGallery> vouchers;
    String type;
    View v;

    public SponsorRecycleAdapter(Context context, ArrayList<BeanGallery> vouchers, String type) {
        this.context = context;
        this.vouchers = vouchers;
        this.type = type;
    }

    @Override
    public AvailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sponsor, parent, false);

        return new AvailViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AvailViewHolder holder, final int position) {

        Glide.with(context).load(vouchers.get(position).getVoucher_image()).placeholder(R.drawable.lodar
        ).into(holder.img_background);

        holder.company_name.setText(vouchers.get(position).getname());

        holder.llPCitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String Link = vouchers.get(position).getlink();
                System.out.println("Link"+Link);

                if (!Link.equals("") && Link != null){

                    Intent intent = new Intent(context, WebviewActivity.class);
                    intent.putExtra("url", ""+Link);
                    context.startActivity(intent);

                }else {
                    Toast.makeText(context, "Website is not available!!", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return vouchers.size();
    }

    public class AvailViewHolder extends RecyclerView.ViewHolder {

        ImageView img_background;
        TextView company_name;
        LinearLayout llPCitem;

        public AvailViewHolder(View itemView) {
            super(itemView);
            img_background = (ImageView) itemView.findViewById(R.id.img_background);
            company_name = (TextView) itemView.findViewById(R.id.company_name);

            llPCitem = (LinearLayout) itemView.findViewById(R.id.llPCitem);

        }
    }

}
