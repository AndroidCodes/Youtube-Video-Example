package in.gajerait.liveena.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import java.util.List;

import in.gajerait.liveena.R;
import in.gajerait.liveena.adapter.MoviesAdapter;
import in.gajerait.liveena.bean.Movie;
import in.gajerait.liveena.progressbar.KProgressHUD;
import in.gajerait.liveena.utils.StaticDataUtility;


public class MainActivity extends FragmentActivity implements ViewPagerEx.OnPageChangeListener {
    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;

    private Activity activity;
    private RecyclerView recycler_view;
    private SliderLayout TopAdd, BottomAdd, SliderAdd;

    private boolean backPressedToExitOnce = false;
    public static int addsMaintain = 0;
    private KProgressHUD pDialog;

    @Override
    public void onCreate(Bundle inState) {
        super.onCreate(inState);
        setContentView(R.layout.activity_main);

        activity = MainActivity.this;

//        mPager = (ViewPager) findViewById(R.id.pager);
//        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);

        TopAdd = (SliderLayout) findViewById(R.id.TopAdd);
        SliderAdd = (SliderLayout) findViewById(R.id.SliderAdd);
        BottomAdd = (SliderLayout) findViewById(R.id.BottomAdd);



        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new MoviesAdapter(activity, movieList);
        recycler_view.setLayoutManager(new GridLayoutManager(activity, 3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareMovieData();
//        setGridView();
    }
    private void prepareMovieData() {
        Movie movie = new Movie("", R.drawable.live);
        movieList.add(movie);

        movie = new Movie("", R.drawable.photo);
        movieList.add(movie);

        movie = new Movie("", R.drawable.video);
        movieList.add(movie);

        movie = new Movie("Celebrity", R.drawable.selebrity);
        movieList.add(movie);

        movie = new Movie("Sponsors", R.drawable.sponsar);
        movieList.add(movie);

        movie = new Movie("Booking", R.drawable.booking);
        movieList.add(movie);

        movie = new Movie("Facebook", R.drawable.facebook);
        movieList.add(movie);

        movie = new Movie("Rewards", R.drawable.rewords);
        movieList.add(movie);

        movie = new Movie("Food Stall", R.drawable.food_stol);
        movieList.add(movie);

        movie = new Movie("Classis", R.drawable.classis);
        movieList.add(movie);

        movie = new Movie("You tube", R.drawable.youtub);
        movieList.add(movie);

        movie = new Movie("Contact us", R.drawable.contact);
        movieList.add(movie);

        mAdapter.notifyDataSetChanged();
        loadAds();
    }

//    private void setGridView() {
//
//        TestFragmentAdapter mAdapter = new TestFragmentAdapter(
//                getSupportFragmentManager());
//
//        mPager.setAdapter(mAdapter);
////        mIndicator.setViewPager(mPager);
////        mIndicator.setCurrentItem(0);
//    }


    @Override
    protected void onRestart() {
        super.onRestart();
        setHeaderViewpager();
        setSliderViewpager();
        setFooterViewpager();
    }

    @Override
    public void onBackPressed() {

        if (backPressedToExitOnce) {
            super.onBackPressed();
        } else {
            this.backPressedToExitOnce = true;
            Toast.makeText(activity, "Press again to exit", Toast.LENGTH_SHORT)
                    .show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    backPressedToExitOnce = false;
                }
            }, 2000);
        }

        // super.onBackPressed();
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

        TopAdd.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
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


    private void setSliderViewpager() {

        HashMap<String, String> slider_file_maps = new HashMap<String, String>();

        for (int i = 0; i < StaticDataUtility.slider.size(); i++) {
            slider_file_maps.put("footer" + i, StaticDataUtility.slider.get(i));
            System.out.println(" Slider >>" + StaticDataUtility.slider.get(i));
        }

        for (String name : slider_file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .image(slider_file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            SliderAdd.addSlider(textSliderView);
        }

        SliderAdd.setPresetTransformer(SliderLayout.Transformer.Accordion);
                SliderAdd.setCustomAnimation(new DescriptionAnimation());
        SliderAdd.setDuration(7000);
        SliderAdd.addOnPageChangeListener(this);

        if (slider_file_maps.size() < 2) {
            SliderAdd.stopAutoCycle();
            SliderAdd.setPagerTransformer(false, new BaseTransformer() {
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


                                addsMaintain = 1;
                                setHeaderViewpager();
                                setSliderViewpager();
                                setFooterViewpager();
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
//                CommonUtility.showAlertDialog(activity, getResources().getString(R.string.No_Internet), getResources().getString(R.string.app_name));
            }
        }) {
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
