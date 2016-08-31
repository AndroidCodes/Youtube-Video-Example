package in.gajerait.liveena.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Transformers.BaseTransformer;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import in.gajerait.liveena.R;
import in.gajerait.liveena.adapter.FullScreenImageAdapter;
import in.gajerait.liveena.bean.BeanGallery;
import in.gajerait.liveena.utils.CommonUtility;
import in.gajerait.liveena.utils.StaticDataUtility;

public class FullScreenViewActivity extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener {


    private FullScreenImageAdapter adapter;
    private ViewPager viewPager;
    private ArrayList<BeanGallery> galleryMasters;
    private TextView txtTitle;
    ImageView tvshare_saved;
    private Toolbar toolbar;
    private SliderLayout TopAdd, BottomAdd;
    private Activity activity;
    String what;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_view);

        activity =FullScreenViewActivity.this;
        viewPager = (ViewPager) findViewById(R.id.pager);

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        txtTitle = (TextView) toolbar.findViewById(R.id.txtTitle);
        txtTitle.setText("Photo Gallery");

        tvshare_saved = (ImageView) findViewById(R.id.tvshare_saved);
        tvshare_saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Imagepath = galleryMasters.get(viewPager.getCurrentItem()).getVoucher_image();
                String Id = galleryMasters.get(viewPager.getCurrentItem()).getVoucher_id();
                what = "share";
                new DownloadImage("share", Id).execute(Imagepath);

            }
        });

        TopAdd = (SliderLayout) findViewById(R.id.TopAdd);
        BottomAdd = (SliderLayout) findViewById(R.id.BottomAdd);
        setHeaderViewpager();
        setFooterViewpager();
        int position = getIntent().getIntExtra("position", 0);

        galleryMasters = (ArrayList<BeanGallery>) getIntent().getSerializableExtra("gallery");

        adapter = new FullScreenImageAdapter(FullScreenViewActivity.this,
                galleryMasters);

        viewPager.setAdapter(adapter);

        // displaying selected image first
        viewPager.setCurrentItem(position);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

//                txtTitle.setText(""+galleryMasters.get(viewPager.getCurrentItem()).getVoucher_company_id());

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    File file1;
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        ProgressDialog pDialog;
        String what, Id;

        public DownloadImage(String what, String Id) {
            this.what = what;
            this.Id = Id;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon;
        }

        @Override
        protected void onPreExecute() {
            Log.i("", "onPreExecute");

            pDialog = new ProgressDialog(activity);
            if (!what.equals("share"))
                pDialog.setMessage(activity.getString(R.string.Saving_Image) + "...");
            else
                pDialog.setMessage(activity.getString(R.string.Sharing_Image) + "...");
            pDialog.setCancelable(false);
            // if(!what.equals("share"))
            pDialog.show();
        }

        protected void onPostExecute(Bitmap result) {
            if (pDialog.isShowing())
                pDialog.dismiss();

            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/" + activity.getString(R.string.app_name));
            myDir.mkdirs();
            Random generator = new Random();
            int n = 10000;
            n = generator.nextInt(n);
            String fname = activity.getString(R.string.Diamond_Image) + " "
                    + Id + ".jpg";
            file1 = new File(myDir, fname);
            if (file1.exists())
                file1.delete();
            try {
                FileOutputStream out = new FileOutputStream(file1);
                result.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
                if (!what.equals("share"))
                    CommonUtility.showAlertDialog(activity,
                            activity.getResources().getString(R.string.Image_Saved_Successfully),
                            activity.getResources().getString(R.string.app_name), false);


            } catch (Exception e) {
                e.printStackTrace();
            }

            if (what.equals("share")) {
                Intent sharingIntent = new Intent(
                        android.content.Intent.ACTION_SEND);
                sharingIntent.setType("image/*,text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                        activity.getString(R.string.app_name) + ""
                                + Id);
                sharingIntent.putExtra(android.content.Intent.EXTRA_STREAM,
                        Uri.fromFile(file1));
                Log.d("", file1 + "");
                activity.startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }
        }
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