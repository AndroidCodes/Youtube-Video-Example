package in.gajerait.liveena.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.gajerait.liveena.R;
import in.gajerait.liveena.adapter.SponsorAdapter;
import in.gajerait.liveena.bean.BeanPhotoCategory;
import in.gajerait.liveena.progressbar.KProgressHUD;
import in.gajerait.liveena.utils.CommonUtility;
import in.gajerait.liveena.utils.NetworkStatus;
import in.gajerait.liveena.utils.StaticDataUtility;

public class SponsorActivity extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener {
    ArrayList<BeanPhotoCategory> AvailVoucher;
    private Activity activity;
    private RecyclerView sponsor_recyclerView;
    private TextView noData, txtTitle, tvHeader;
    private KProgressHUD pDialog;
    private SponsorAdapter adapter;
    private Toolbar toolbar;
    private SliderLayout BottomAdd, TopAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor);

        activity = SponsorActivity.this;

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
        sponsor_recyclerView = (RecyclerView) findViewById(R.id.sponsor_recyclerView);
        sponsor_recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        noData = (TextView) findViewById(R.id.noData);

        if (NetworkStatus.getConnectivityStatus(activity)) {

            VideoCategory();

        } else {

            CommonUtility.showAlertDialog(activity, getResources().getString(R.string.No_Internet), getResources().getString(R.string.app_name), true);

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
    private void VideoCategory() {

        pDialog = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f);
        pDialog.setCancellable(false);
        pDialog.show();
        AvailVoucher = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://liveena.com/sponsors_category_api.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            System.out.println("SponsorAdapter-->> " + response.toString());

                            JSONObject jsonObject = new JSONObject(response.toString());
                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                JSONArray jaDetail = jsonObject.getJSONArray("result");

                                System.out.println("jaDetail ..>>" + jaDetail.toString());

                                if (jaDetail.length() > 0) {

                                    for (int i = 0; i < jaDetail.length(); i++) {

                                        JSONObject joDetail = jaDetail.getJSONObject(i);
                                        System.out.println("joDetail ..>>" + joDetail.toString());

                                        AvailVoucher.add(new BeanPhotoCategory(joDetail.optString("id"), joDetail.optString("category"),
                                                joDetail.optString("image"), joDetail.optString("sidebar_image"), joDetail.optString("sort_order")));

                                    }

                                    noData.setVisibility(View.GONE);
                                    sponsor_recyclerView.setVisibility(View.VISIBLE);
                                    adapter = new SponsorAdapter(activity, AvailVoucher, "avail");
                                    sponsor_recyclerView.setAdapter(adapter);
                                } else {

                                    CommonUtility.showAlertDialog(activity, getResources().getString(R.string.No_Internet), getResources().getString(R.string.app_name), true);
                                }

                            } else {

                                CommonUtility.showAlertDialog(activity, getResources().getString(R.string.No_Internet), getResources().getString(R.string.app_name), true);

                            }

                            pDialog.dismiss();
                        } catch (Exception e) {

                            System.out.println(" error --> " + e.toString());

                            CommonUtility.showAlertDialog(activity, getResources().getString(R.string.No_Internet), getResources().getString(R.string.app_name), true);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        CommonUtility.showAlertDialog(activity, getResources().getString(R.string.No_Internet), getResources().getString(R.string.app_name), true);
                    }
                }) {
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Adding request to request queue
        Volley.newRequestQueue(activity).add(stringRequest);
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