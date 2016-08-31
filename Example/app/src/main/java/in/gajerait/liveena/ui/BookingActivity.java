package in.gajerait.liveena.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import in.gajerait.liveena.R;
import in.gajerait.liveena.adapter.BookingCategoryAdapter;
import in.gajerait.liveena.bean.BeanPhotoCategory;
import in.gajerait.liveena.progressbar.KProgressHUD;
import in.gajerait.liveena.utils.CommonUtility;
import in.gajerait.liveena.utils.NetworkStatus;
import in.gajerait.liveena.utils.StaticDataUtility;

public class BookingActivity extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener {
    ArrayList<BeanPhotoCategory> AvailVoucher;
    private Activity activity;
    private RecyclerView BookingCategory_recyclerView;
    private TextView noData, txtTitle;
    private KProgressHUD pDialog;
    private BookingCategoryAdapter adapter;
    private Toolbar toolbar;
    private SliderLayout BottomAdd, TopAdd;
public String URL, Header;
    private TextView tvHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        activity = BookingActivity.this;

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            URL = extras.getString("URL");
            Header = extras.getString("Header");
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
        txtTitle.setText(R.string.photocategory);

        tvHeader = (TextView) findViewById(R.id.tvHeader);
        tvHeader.setText(""+Header);

        System.out.println(""+URL);
        System.out.println(""+Header);

        BookingCategory_recyclerView = (RecyclerView) findViewById(R.id.BookingCategory_recyclerView);
        BookingCategory_recyclerView.setLayoutManager(new GridLayoutManager(activity, 1));
//        BookingCategory_recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        noData = (TextView) findViewById(R.id.noData);

        TopAdd = (SliderLayout) findViewById(R.id.TopAdd);
        BottomAdd = (SliderLayout) findViewById(R.id.BottomAdd);
        setHeaderViewpager();
        setFooterViewpager();
        if (NetworkStatus.getConnectivityStatus(activity)) {

            if (MainActivity.addsMaintain == 0) {

                loadAds();
            } else {

                PhotoCategory();
            }


        } else {

            CommonUtility.showAlertDialog(activity, getResources().getString(R.string.No_Internet), getResources().getString(R.string.app_name), true);

        }

    }

    private void setHeaderViewpager() {

        HashMap<String, String> top_file_maps = new HashMap<String, String>();
        for (int i = 0; i < StaticDataUtility.ad1.size(); i++) {
            top_file_maps.put("top" + i, StaticDataUtility.ad1.get(i));
            System.out.println("Add1>>" + StaticDataUtility.ad1.get(i));
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
            System.out.println(" footer >>" + StaticDataUtility.ad2.get(i));
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

    private void PhotoCategory() {

        pDialog = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f);
        pDialog.setCancellable(false);
        pDialog.show();
        AvailVoucher = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "\n" +
                ""+URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            System.out.println("liveena-->> " + response);

                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                JSONArray jaDetail = jsonObject.getJSONArray("result");

                                System.out.println("jaDetail ..>>" + jaDetail.toString());

                                if (jaDetail.length() > 0) {

                                    for (int i = 0; i < jaDetail.length(); i++) {

                                        JSONObject joDetail = jaDetail.getJSONObject(i);
                                        System.out.println("joDetail ..>>" + joDetail.toString());

                                        AvailVoucher.add(new BeanPhotoCategory(joDetail.optString("id"), joDetail.optString("name"),
                                                joDetail.optString("description"), joDetail.optString("image")));

                                    }

                                    txtTitle.setText(getString(R.string.photocategory) + " (" + jaDetail.length() + " categories)");

                                    noData.setVisibility(View.GONE);
                                    BookingCategory_recyclerView.setVisibility(View.VISIBLE);
                                    adapter = new BookingCategoryAdapter(activity, AvailVoucher, "avail");
                                    BookingCategory_recyclerView.setAdapter(adapter);
                                    pDialog.dismiss();
                                } else {
                                    pDialog.dismiss();
                                    CommonUtility.showAlertDialog(activity, getResources().getString(R.string.No_Internet), getResources().getString(R.string.app_name), true);
                                }
                                pDialog.dismiss();
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

    public void loadAds() {

        pDialog = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f);
        pDialog.setCancellable(false);
        pDialog.show();

        RequestQueue rQ = Volley.newRequestQueue(activity);

        StringRequest sReq = new StringRequest(Request.Method.POST, "http://liveena.com/adds_all_api.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject joResp = new JSONObject(response);

                            System.out.println("response.." + response);
                            int success = joResp.getInt("success1");

                            if (success == 1) {

                                JSONArray jaDetail = joResp.getJSONArray("result1");

                                Log.e("jaDetail", jaDetail.toString());
//                               [{"logo":"http:\/\/support.peacocktech.in:8081\/lid\/siteuploads\/company_logo_image\/1466657972_download.jpg","company_id":"84","terms_condition":"Lorem Ipsum is
                                if (jaDetail.length() > 0) {

                                    for (int i = 0; i < jaDetail.length(); i++) {
                                        JSONObject joDetail = jaDetail.getJSONObject(i);

                                        StaticDataUtility.ad1.add(joDetail.optString("image"));


                                    }

                                }

                                JSONArray jaDetailADD2 = joResp.getJSONArray("result2");

                                if (jaDetailADD2.length() > 0) {

                                    for (int i = 0; i < jaDetailADD2.length(); i++) {
                                        JSONObject joDetailADD2 = jaDetailADD2.getJSONObject(i);

                                        StaticDataUtility.ad2.add(joDetailADD2.optString("image"));
                                    }

                                }

                                JSONArray jaDetailslider = joResp.getJSONArray("result3");

                                if (jaDetailslider.length() > 0) {

                                    for (int i = 0; i < jaDetailslider.length(); i++) {
                                        JSONObject joDetailslider = jaDetailslider.getJSONObject(i);

                                        StaticDataUtility.slider.add(joDetailslider.optString("image"));
                                    }

                                }

                                pDialog.dismiss();
                                setHeaderViewpager();

                                setFooterViewpager();
                                PhotoCategory();


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
                CommonUtility.showAlertDialog(activity, getResources().getString(R.string.No_Internet), getResources().getString(R.string.app_name), true);
            }
        }) {
        };

        sReq.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        rQ.add(sReq);
    }
}
