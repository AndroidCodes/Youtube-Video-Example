package in.gajerait.liveena.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Transformers.BaseTransformer;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.gajerait.liveena.R;
import in.gajerait.liveena.adapter.SponsorRecycleAdapter;
import in.gajerait.liveena.bean.BeanGallery;
import in.gajerait.liveena.progressbar.KProgressHUD;
import in.gajerait.liveena.utils.CommonUtility;
import in.gajerait.liveena.utils.NetworkStatus;
import in.gajerait.liveena.utils.StaticDataUtility;

public class SponsorCategoryActivity extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener{

    ArrayList<BeanGallery> AvailVoucher;
    private Activity activity;
    private RecyclerView video_recycleView;
    private TextView noData, txtTitle, tvHeader;
    private KProgressHUD pDialog;
    private SponsorRecycleAdapter adapter;
    private Toolbar toolbar;
    private String ID, NAME;
    private SliderLayout BottomAdd, TopAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videosubpart);

        activity = SponsorCategoryActivity.this;

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            ID = extras.getString("ID");
            NAME = extras.getString("NAME");
        }


        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ico_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
                overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity);
            }
        });

        setTitle("");
        txtTitle = (TextView) toolbar.findViewById(R.id.txtTitle);
        tvHeader = (TextView) findViewById(R.id.tvHeader);

        tvHeader.setText(R.string.sponsor);

        TopAdd = (SliderLayout) findViewById(R.id.TopAdd);
        BottomAdd = (SliderLayout) findViewById(R.id.BottomAdd);
        setHeaderViewpager();
        setFooterViewpager();
        video_recycleView = (RecyclerView) findViewById(R.id.video_recycleView);
        video_recycleView.setLayoutManager(new GridLayoutManager(activity, 2));
//        video_recycleView.setLayoutManager(new LinearLayoutManager(activity));

        noData = (TextView) findViewById(R.id.noData);

        if (NetworkStatus.getConnectivityStatus(activity)) {

            SponsorCategoryActivity();

        } else {

            CommonUtility.showAlertDialog(activity, getResources().getString(R.string.No_Internet), getResources().getString(R.string.app_name),true);

        }

    }
    private void setHeaderViewpager() {

        HashMap<String, String> top_file_maps = new HashMap<String, String>();
        for (int i = 0; i < StaticDataUtility.ad1.size(); i++) {
            top_file_maps.put("top" + i, StaticDataUtility.ad1.get(i));
            System.out.println("Add1>>"+ StaticDataUtility.ad1.get(i));
        }

        for (String name : top_file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .image(top_file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            TopAdd.addSlider(textSliderView);
        }

        TopAdd.setPresetTransformer(SliderLayout.Transformer.Tablet);
        TopAdd.setCustomAnimation(new DescriptionAnimation());
        TopAdd.setDuration(7000);
        TopAdd.addOnPageChangeListener(this);


        if (top_file_maps.size() < 2) {
            TopAdd.stopAutoCycle();
            TopAdd.setPagerTransformer(false, new BaseTransformer() {
                @Override
                protected void onTransform(View view, float v) {
                }
            });
            //TODO: disable indicator
        }

    }
    private void setFooterViewpager() {

        HashMap<String, String> bottom_file_maps = new HashMap<String, String>();

        for (int i = 0; i < StaticDataUtility.ad2.size(); i++) {
            bottom_file_maps.put("footer" + i, StaticDataUtility.ad2.get(i));
            System.out.println(" footer >>"+ StaticDataUtility.ad2.get(i));
        }

        for (String name : bottom_file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .image(bottom_file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            BottomAdd.addSlider(textSliderView);
        }
        BottomAdd.setPresetTransformer(SliderLayout.Transformer.Tablet);
        BottomAdd.setCustomAnimation(new DescriptionAnimation());
        BottomAdd.setDuration(7000);
        BottomAdd.addOnPageChangeListener(this);

        if (bottom_file_maps.size() < 2) {
            BottomAdd.stopAutoCycle();
            BottomAdd.setPagerTransformer(false, new BaseTransformer() {
                @Override
                protected void onTransform(View view, float v) {
                }
            });
            //TODO: disable indicator
        }

    }

    public void SponsorCategoryActivity() {

        pDialog = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f);
        pDialog.setCancellable(false);
        pDialog.show();

        AvailVoucher = new ArrayList<>();

        RequestQueue rQ = Volley.newRequestQueue(activity);

        StringRequest sReq = new StringRequest(Request.Method.POST, " http://liveena.com/sponsors_api.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject joResp = new JSONObject(response);

                            System.out.println("sponsors_api.." + response);
                            int success = joResp.getInt("success");

                            if (success == 1) {

                                JSONArray jaDetail = joResp.getJSONArray("result");

                                Log.e("jaDetail", jaDetail.toString());
                                if (jaDetail.length() > 0) {

                                    for (int i = 0; i < jaDetail.length(); i++) {
                                        JSONObject joDetail = jaDetail.getJSONObject(i);

                                        AvailVoucher.add(new BeanGallery(joDetail.optString("id"), joDetail.optString("category"),
                                                joDetail.optString("image"), joDetail.optString("sidebar_image"), joDetail.optString("sort_order")
                                                , joDetail.optString("name"), joDetail.optString("link")));
                                    }
                                    tvHeader.setText(NAME + " (" + jaDetail.length() + " Sponsor)");
                                    noData.setVisibility(View.GONE);
                                    video_recycleView.setVisibility(View.VISIBLE);

                                    adapter = new SponsorRecycleAdapter(activity, AvailVoucher, "avail");
                                    video_recycleView.setAdapter(adapter);
                                } else {
                                    CommonUtility.showAlertDialog(activity, getResources().getString(R.string.No_Internet), getResources().getString(R.string.app_name),true);


                                    noData.setVisibility(View.VISIBLE);
                                    video_recycleView.setVisibility(View.GONE);
                                }

                            } else {

                                CommonUtility.showAlertDialog(activity, getResources().getString(R.string.No_Internet), getResources().getString(R.string.app_name),true);

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
                CommonUtility.showAlertDialog(activity, getResources().getString(R.string.No_Internet), getResources().getString(R.string.app_name),true);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();

                params.put("id", ID);

                Log.e("params", params.toString());

                return params;


            }
        };

        sReq.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        rQ.add(sReq);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
