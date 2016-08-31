package in.gajerait.liveena.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.gajerait.liveena.R;
import in.gajerait.liveena.progressbar.KProgressHUD;
import in.gajerait.liveena.utils.CommonUtility;
import in.gajerait.liveena.utils.NetworkStatus;
import in.gajerait.liveena.utils.StaticDataUtility;

//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;

public class Contactus extends AppCompatActivity implements View.OnClickListener, ViewPagerEx.OnPageChangeListener , OnMapReadyCallback {

    private Toolbar toolbar;
    private SliderLayout BottomAdd, TopAdd;

    ImageView img_set_default;

    LatLng latLng;
    private TextView tvHeader;
    private GoogleMap mMap;
    private EditText edtQuickName,  edtQuickEmail, edtQuickComments;
    private Button btnSubmit;
    private String strName,  strEmail, strComment;
    private Activity activity;
    private KProgressHUD pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);

        activity = Contactus.this;
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ico_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity);
            }
        });

        setTitle("");
        TextView txtTitle = (TextView) toolbar.findViewById(R.id.txtTitle);
        txtTitle.setText(R.string.contact_us);
        tvHeader = (TextView) findViewById(R.id.tvHeader);
        tvHeader.setText(R.string.contact_us);

        edtQuickComments = (EditText) findViewById(R.id.edtQuickComments);
        edtQuickName = (EditText) findViewById(R.id.edtQuickName);
        edtQuickEmail = (EditText) findViewById(R.id.edtQuickEmail);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        TopAdd = (SliderLayout) findViewById(R.id.TopAdd);
        BottomAdd = (SliderLayout) findViewById(R.id.BottomAdd);
        setHeaderViewpager();
        setFooterViewpager();

        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_google_map)).getMapAsync(this);

        latLng = new LatLng(21.099823,73.036710);
        img_set_default = (ImageView) findViewById(R.id.img_set_default);


    }

    private void reSet() {

        edtQuickComments.getText().clear();
        edtQuickName.getText().clear();
        edtQuickEmail.getText().clear();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSubmit:

                strName = edtQuickName.getText().toString().trim();
                strEmail = edtQuickEmail.getText().toString().trim();
                strComment = edtQuickComments.getText().toString().trim();

                if (strName.length() <= 0
                        || strName.equalsIgnoreCase("")) {

                    edtQuickName.setError(activity.getString(R.string.validation_name));

                } else if (strEmail.length() <= 0 || strEmail.equalsIgnoreCase("") || !CommonUtility.isValidEmail(strEmail)) {

                    edtQuickEmail.setError(activity.getString(R.string.validation_email));

                } else if (strComment.length() <= 0 || strComment.equalsIgnoreCase("")) {

                    edtQuickComments.setError(activity.getString(R.string.validation_comment));

                } else {

                    if (NetworkStatus.getConnectivityStatus(activity)) {

                        quickInquiry();

                    } else {

                        CommonUtility.showAlertDialog(activity, getResources().getString(R.string.No_Internet),
                                getResources().getString(R.string.app_name), true);

                    }
                }
                break;
        }
    }
    public void quickInquiry() {

        pDialog = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f);
        pDialog.setCancellable(false);
        pDialog.show();


        RequestQueue rQ = Volley.newRequestQueue(activity);

        StringRequest sReq = new StringRequest(Request.Method.POST, "http://liveena.com/inquire.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("quickInquiry Response.." + response.toString());

                        try {
                            JSONObject joResp = new JSONObject(response);


                            int success = joResp.getInt("success");

                            if (success == 1) {


                                Log.e("success..", ""+success);

                                CommonUtility.showAlertDialog(activity, getResources().getString(R.string.Yourmessagewassentsuccessfully), getResources().getString(R.string.app_name), false);
                                reSet();

                            } else {

                                CommonUtility.showAlertDialog(activity, getResources().getString(R.string.No_Internet), getResources().getString(R.string.app_name), true);

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
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();

                params.put("name", strName);
                params.put("email", strEmail);
                params.put("message", strComment);

                Log.e("quickInquiry params", params.toString());

                return params;

            }
        };

        sReq.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        rQ.add(sReq);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setUpMap();
    }


    public void setUpMap(){
        mMap.getUiSettings().setMyLocationButtonEnabled(false);


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        mMap.setPadding(0, 150, 0, 0);


        mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    //name
                    .title("Ena Gaam Navaratri 2016")
                    //Address
                    .snippet("Ena village, Surat, India.")
//                    .icon(BitmapDescriptorFactory
//                            .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location)));

        img_set_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

                // Zoom in, animating the camera.
                mMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
