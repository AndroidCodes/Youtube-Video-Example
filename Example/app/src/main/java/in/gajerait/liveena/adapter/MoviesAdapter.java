package in.gajerait.liveena.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import in.gajerait.liveena.R;
import in.gajerait.liveena.bean.Movie;
import in.gajerait.liveena.progressbar.KProgressHUD;
import in.gajerait.liveena.ui.BookingActivity;
import in.gajerait.liveena.ui.CelebrityVideo;
import in.gajerait.liveena.ui.Contactus;
import in.gajerait.liveena.ui.GalleryActivity;
import in.gajerait.liveena.ui.PhotoCategory;
import in.gajerait.liveena.ui.SponsorActivity;
import in.gajerait.liveena.ui.VideoCategory;
import in.gajerait.liveena.ui.WebviewActivity;
import in.gajerait.liveena.ui.YoutubePlay;
import in.gajerait.liveena.utils.CommonUtility;
import in.gajerait.liveena.utils.NetworkStatus;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    public static String FACEBOOK_URL = "https://www.facebook.com/NavratriEna";
    public static String FACEBOOK_PAGE_ID = "370233523110329";
    public Context context;
    Activity activity = (Activity) context;
    private List<Movie> moviesList;
    private KProgressHUD pDialog;

    public MoviesAdapter(Context context, List<Movie> moviesList) {
        this.moviesList = moviesList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_view_itemaa, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Movie movie = moviesList.get(position);
        if (position == 0 || position == 1 || position == 2) {
            holder.grid_item_title.setVisibility(View.INVISIBLE);
            holder.grid_item_title.setTextSize(1);

        } else {
            holder.grid_item_title.setVisibility(View.VISIBLE);
        }


        holder.grid_item_title.setText(movie.getTitle());
        Glide.with(context).load(movie.getImage()).placeholder(R.drawable.lodar).into(holder.grid_item_image);
        holder.lliocnitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();

                IconClick(position);

            }
        });
    }

    private void IconClick(int position) {

        switch (position) {

            case 0:
                if (NetworkStatus.getConnectivityStatus(context)) {


                    LiveStreamActivity();
                } else {

                    CommonUtility.showAlertDialog((Activity) context, context.getResources().getString(R.string.No_Internet),
                            context.getResources().getString(R.string.app_name), false);
                }

                break;
            case 1:

                if (NetworkStatus.getConnectivityStatus(context)) {

                    context.startActivity(new Intent(context, PhotoCategory.class));
//                   activity.overridePendingTransition(R.anim.slide_in_left_activity, R.anim.slide_out_right_activity);

                } else {

                    CommonUtility.showAlertDialog((Activity) context, context.getResources().getString(R.string.No_Internet),
                            context.getResources().getString(R.string.app_name), false);
                }

                break;
            case 2:
                if (NetworkStatus.getConnectivityStatus(context)) {

                    context.startActivity(new Intent(context, VideoCategory.class));
//                    context.overridePendingTransition(R.anim.slide_in_left_activity, R.anim.slide_out_right_activity);

                } else {

                    CommonUtility.showAlertDialog((Activity) context, context.getResources().getString(R.string.No_Internet),
                            context.getResources().getString(R.string.app_name), false);
                }

                break;
            case 3:
                if (NetworkStatus.getConnectivityStatus(context)) {
                    context.startActivity(new Intent(context, CelebrityVideo.class));
//                    context.overridePendingTransition(R.anim.slide_in_left_activity, R.anim.slide_out_right_activity);
                } else {

                    CommonUtility.showAlertDialog((Activity) context, context.getResources().getString(R.string.No_Internet),
                            context.getResources().getString(R.string.app_name), false);
                }
                break;
            case 4:
                if (NetworkStatus.getConnectivityStatus(context)) {

                    context.startActivity(new Intent(context, SponsorActivity.class));
//                    context.overridePendingTransition(R.anim.slide_in_left_activity, R.anim.slide_out_right_activity);
                } else {

                    CommonUtility.showAlertDialog((Activity) context, context.getResources().getString(R.string.No_Internet),
                            context.getResources().getString(R.string.app_name), false);
                }
                break;
            case 5:
                if (NetworkStatus.getConnectivityStatus(context)) {

                    Intent intent = new Intent(context, BookingActivity.class);
                    intent.putExtra("URL", "http://liveena.com/booking_api.php");
                    intent.putExtra("Header", "Booking");
                    context.startActivity(intent);


//                   activity.overridePendingTransition(R.anim.slide_in_left_activity, R.anim.slide_out_right_activity);

                } else {

                    CommonUtility.showAlertDialog((Activity) context, context.getResources().getString(R.string.No_Internet),
                            context.getResources().getString(R.string.app_name), false);
                }

                break;
            case 6:
                if (NetworkStatus.getConnectivityStatus(context)) {
                    Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                    String facebookUrl = getFacebookPageURL(context);
                    facebookIntent.setData(Uri.parse(facebookUrl));
                    context.startActivity(facebookIntent);

                } else {

                    CommonUtility.showAlertDialog((Activity) context, context.getResources().getString(R.string.No_Internet),
                            context.getResources().getString(R.string.app_name), false);
                }
//                        try {
//                            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
//                            new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/370233523110329"));
//                        } catch (Exception e) {
//                            new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/NavratriEna?fref=ts"));
//                        }

                break;
            case 7:
                Intent intent = new Intent(context, BookingActivity.class);
                intent.putExtra("URL", "http://liveena.com/rewards_api.php");
                intent.putExtra("Header", "Rewards");
                context.startActivity(intent);

                break;
            case 8:
                 intent = new Intent(context, BookingActivity.class);
                intent.putExtra("URL", "http://liveena.com/food_api.php");
                intent.putExtra("Header", "Food Stall");
                context.startActivity(intent);
                break;
            case 9:
                intent = new Intent(context, BookingActivity.class);
                intent.putExtra("URL", "http://liveena.com/classis_api.php");
                intent.putExtra("Header", "Classis");
                context.startActivity(intent);
                break;
            case 10:
                if (NetworkStatus.getConnectivityStatus(context)) {
                    try {

                        if (isAppInstalled("com.google.android.youtube")) {
                             intent = new Intent(Intent.ACTION_SEARCH);
                            intent.setPackage("com.google.android.youtube");
                            intent.putExtra("query", "ena+navratri");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        } else {
                            Toast.makeText(context, "Youtube is not install in your device!! ", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "Youtube is not install in your device!! ", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    CommonUtility.showAlertDialog((Activity) context, context.getResources().getString(R.string.No_Internet),
                            context.getResources().getString(R.string.app_name), false);
                }
                break;
            case 11:
                if (NetworkStatus.getConnectivityStatus(context)) {
                    context.startActivity(new Intent(context, Contactus.class));
//                    context.overridePendingTransition(R.anim.slide_in_left_activity, R.anim.slide_out_right_activity);
                } else {

                    CommonUtility.showAlertDialog((Activity) context, context.getResources().getString(R.string.No_Internet),
                            context.getResources().getString(R.string.app_name), false);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    //method to get the right URL to use in the intent
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

    protected boolean isAppInstalled(String packageName) {
        Intent mIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (mIntent != null) {
            return true;
        } else {
            return false;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView grid_item_title;
        public LinearLayout lliocnitem;
        ImageView grid_item_image;

        public MyViewHolder(View view) {
            super(view);
            grid_item_title = (TextView) view.findViewById(R.id.grid_item_title);
            grid_item_image = (ImageView) view.findViewById(R.id.grid_item_image);
            lliocnitem = (LinearLayout) view.findViewById(R.id.lliocnitem);

        }
    }

    public void LiveStreamActivity() {

        pDialog = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f);
        pDialog.setCancellable(false);
        pDialog.show();

        RequestQueue rQ = Volley.newRequestQueue(context);

        StringRequest sReq = new StringRequest(Request.Method.GET, "http://liveena.com/live_stream_api.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

//                        rtsp://vm3.cdbyte.in:1935/ivb7/d64ddaa65be5eb8d01b99a4c5e903933/playlist.m3u8

                        //{"result":{"mobile":"rtsp:\/\/vm3.gloriatech.in:1935\/ivb7\/d64ddaa65be5eb8d01b99a4c5e903933?3551250","type":"2"},"msg":"success","success":"1","error_code":"1"}
                        System.out.println("LiveStreamActivity.." + response);
                        try {
                            JSONObject joResp = new JSONObject(response);

                            System.out.println("response.." + response);
                            int success = joResp.getInt("success");

                            if (success == 1) {

                                JSONObject result = joResp.optJSONObject("result");
                                String mobile = result.optString("mobile");

                                System.out.println(" result --> " + joResp.optJSONObject("result"));

                                String[] temp = mobile.split("\\/");
                                System.out.println(" mobile --> " + temp[temp.length - 1]);


                                String Type = result.optString("type");

                                System.out.println(" 1mobile --> " + mobile);
                                System.out.println(" Type --> " + Type);


                                if (Type.equals("1")) {

                                    System.out.println(" mobile 1. --> " + temp[temp.length - 1]);

                                    Intent intent = new Intent(context, YoutubePlay.class);
                                    intent.putExtra("ID", temp[temp.length - 1]);
                                    intent.putExtra("name", "sagar");
                                    context.startActivity(intent);

                                } else if (Type.equals("2")) {

                                    System.out.println(" mobile 2. --> " + mobile);

                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mobile));
                                    context.startActivity(intent);


                                } else {

                                    System.out.println(" mobile 3. --> " + mobile);

                                    Intent intent = new Intent(context, WebviewActivity.class);
                                    intent.putExtra("url", "" + mobile);
                                    context.startActivity(intent);

                                }


                            } else {

                                CommonUtility.showAlertDialog((Activity) context, context.getResources().getString(R.string.No_Video),
                                        context.getResources().getString(R.string.app_name), true);

                            }

                            pDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            pDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                pDialog.dismiss();
                CommonUtility.showAlertDialog((Activity) context, context.getResources().getString(R.string.No_Video),
                        context.getResources().getString(R.string.app_name), true);

            }
        }) {
        };

        sReq.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        rQ.add(sReq);
    }
}
