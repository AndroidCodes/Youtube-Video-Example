package in.gajerait.liveena.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.MediaController;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.gajerait.liveena.R;
import in.gajerait.liveena.adapter.GridViewAdapter;
import in.gajerait.liveena.progressbar.KProgressHUD;
import in.gajerait.liveena.utils.CommonUtility;
import in.gajerait.liveena.utils.NetworkStatus;
import in.gajerait.liveena.utils.StaticDataUtility;

@SuppressLint("InflateParams")
public final class TestFragment extends Fragment {
    public static View rootView;
    public static String FACEBOOK_URL = "https://www.facebook.com/NavratriEna";
    public static String FACEBOOK_PAGE_ID = "370233523110329";
    private GridViewAdapter adapter;
    private ArrayList<Integer> iconArrayList;
    private ArrayList<String> titleArrayList;
    private ArrayList<Integer> idArrayList;
    private int start, end;
    private GridView gridView;
    private int[] icon = {R.drawable.live, R.drawable.photo, R.drawable.video,
            R.drawable.selebrity, R.drawable.sponsar, R.drawable.booking, R.drawable.facebook,
            R.drawable.rewords, R.drawable.food_stol, R.drawable.classis, R.drawable.youtub, R.drawable.contact};
    private String[] title = {"", "", "", "Celebrity", "Sponsors", "Booking", "Facebook", "Rewards",
            "Food Stall", "Classis", "You tube", "Contact us"};
    private int[] id = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    private KProgressHUD pDialog;

    public TestFragment() {

    }

    // newInstance constructor for creating fragment with arguments
    public static TestFragment newInstance(int i) {
        TestFragment fragmentFirst = new TestFragment();
        Bundle args = new Bundle();
        args.putInt("start", i);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        start = getArguments().getInt("start");

        System.out.println(" start --> " + start);
        System.out.println(" end --> " + end);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_gridview, null);

        gridView = (GridView) rootView.findViewById(R.id.gridView);

        iconArrayList = new ArrayList<Integer>();
        idArrayList = new ArrayList<Integer>();
        titleArrayList = new ArrayList<String>();

        for (int j = 0; j < start; j++) {

            titleArrayList.add(title[j]);
            iconArrayList.add(icon[j]);
            idArrayList.add(id[j]);
        }

        adapter = new GridViewAdapter(getContext(), titleArrayList, iconArrayList);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {

                switch (idArrayList.get(position)) {

                    case 1:
                        if (NetworkStatus.getConnectivityStatus(getActivity())) {


                            LiveStreamActivity();
                        } else {

                            CommonUtility.showAlertDialog(getActivity(), getResources().getString(R.string.No_Internet), getResources().getString(R.string.app_name), false);
                        }

                        break;
                    case 2:

                        if (NetworkStatus.getConnectivityStatus(getActivity())) {

                            startActivity(new Intent(getActivity(), PhotoCategory.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_left_activity, R.anim.slide_out_right_activity);

                        } else {

                            CommonUtility.showAlertDialog(getActivity(), getResources().getString(R.string.No_Internet), getResources().getString(R.string.app_name), false);
                        }

                        break;
                    case 3:
                        if (NetworkStatus.getConnectivityStatus(getActivity())) {

                            startActivity(new Intent(getActivity(), VideoCategory.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_left_activity, R.anim.slide_out_right_activity);

                        } else {

                            CommonUtility.showAlertDialog(getActivity(), getResources().getString(R.string.No_Internet), getResources().getString(R.string.app_name), false);
                        }

                        break;
                    case 4:
                        if (NetworkStatus.getConnectivityStatus(getActivity())) {
                            startActivity(new Intent(getActivity(), CelebrityVideo.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_left_activity, R.anim.slide_out_right_activity);
                        } else {

                            CommonUtility.showAlertDialog(getActivity(), getResources().getString(R.string.No_Internet), getResources().getString(R.string.app_name), false);
                        }
                        break;
                    case 5:
                        if (NetworkStatus.getConnectivityStatus(getActivity())) {

                            startActivity(new Intent(getActivity(), SponsorActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_left_activity, R.anim.slide_out_right_activity);
                        } else {

                            CommonUtility.showAlertDialog(getActivity(), getResources().getString(R.string.No_Internet), getResources().getString(R.string.app_name), false);
                        }
                        break;
                    case 6:

                        break;
                    case 7:
                        if (NetworkStatus.getConnectivityStatus(getActivity())) {
                            Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                            String facebookUrl = getFacebookPageURL(getActivity());
                            facebookIntent.setData(Uri.parse(facebookUrl));
                            startActivity(facebookIntent);

                        } else {

                            CommonUtility.showAlertDialog(getActivity(), getResources().getString(R.string.No_Internet), getResources().getString(R.string.app_name), false);
                        }
//                        try {
//                            getActivity().getPackageManager().getPackageInfo("com.facebook.katana", 0);
//                            new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/370233523110329"));
//                        } catch (Exception e) {
//                            new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/NavratriEna?fref=ts"));
//                        }

                        break;
                    case 8:

                        break;
                    case 9:

                        break;
                    case 10:

                        break;
                    case 11:
                        if (NetworkStatus.getConnectivityStatus(getActivity())) {
                            try {

                                if (isAppInstalled("com.google.android.youtube")) {
                                    Intent intent = new Intent(Intent.ACTION_SEARCH);
                                    intent.setPackage("com.google.android.youtube");
                                    intent.putExtra("query", "ena+navratri");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getActivity(), "Youtube is not install in your device!! ", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(getActivity(), "Youtube is not install in your device!! ", Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            CommonUtility.showAlertDialog(getActivity(), getResources().getString(R.string.No_Internet), getResources().getString(R.string.app_name), false);
                        }
                        break;
                    case 12:
                        if (NetworkStatus.getConnectivityStatus(getActivity())) {
                            startActivity(new Intent(getActivity(), Contactus.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_left_activity, R.anim.slide_out_right_activity);
                        } else {

                            CommonUtility.showAlertDialog(getActivity(), getResources().getString(R.string.No_Internet), getResources().getString(R.string.app_name), false);
                        }
                        break;
                }

            }
        });
        return rootView;
    }

    public void loadAds() {

        pDialog = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f);
        pDialog.setCancellable(false);
        pDialog.show();

        RequestQueue rQ = Volley.newRequestQueue(getActivity());

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


//                                setHeaderViewpager();
//                                setSliderViewpager();
//                                setFooterViewpager();
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

    public void LiveStreamActivity() {

        pDialog = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f);
        pDialog.setCancellable(false);
        pDialog.show();

        RequestQueue rQ = Volley.newRequestQueue(getActivity());

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

                                    Intent intent = new Intent(getActivity(), YoutubePlay.class);
                                    intent.putExtra("ID", temp[temp.length - 1]);
                                    intent.putExtra("name", "sagar");
                                    getActivity().startActivity(intent);

                                } else if (Type.equals("2")) {

                                    System.out.println(" mobile 2. --> " + mobile);

                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mobile));
                                    startActivity(intent);


                                } else {

                                    System.out.println(" mobile 3. --> " + mobile);

                                    Intent intent = new Intent(getActivity(), WebviewActivity.class);
                                    intent.putExtra("url", "" + mobile);
                                    getActivity().startActivity(intent);

                                }


                            } else {

                                CommonUtility.showAlertDialog(getActivity(), getResources().getString(R.string.No_Video),
                                        getResources().getString(R.string.app_name), true);

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
                CommonUtility.showAlertDialog(getActivity(), getResources().getString(R.string.No_Video),
                        getResources().getString(R.string.app_name), true);

            }
        }) {
        };

        sReq.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        rQ.add(sReq);
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
        Intent mIntent = getActivity().getPackageManager().getLaunchIntentForPackage(packageName);
        if (mIntent != null) {
            return true;
        } else {
            return false;
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
