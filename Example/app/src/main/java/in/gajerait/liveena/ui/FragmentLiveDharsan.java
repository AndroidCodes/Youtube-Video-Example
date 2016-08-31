package in.gajerait.liveena.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.gajerait.liveena.R;
import in.gajerait.liveena.utils.ImageLoader;
import in.gajerait.liveena.utils.JsonUtils;



public class FragmentLiveDharsan extends Fragment {
    static Dialog progressDialog;
    ArrayList<String> ArrayImageUrl;
    ArrayList<String> ArrayVideo;
    ArrayList<String> ArrayVideoCatId;
    ArrayList<String> ArrayVideoCatName;
    ArrayList<String> ArrayVideoDesc;
    ArrayList<String> ArrayVideoDuration;
    ArrayList<String> ArrayVideoId;
    ArrayList<String> ArrayVideoName;
    ArrayList<String> ArrayVideoUrl;
    String[] StringImageUrl;
    String[] StringVideo;
    String[] StringVideoCatId;
    String[] StringVideoCatName;
    String[] StringVideoDesc;
    String[] StringVideoDuration;
    String[] StringVideoId;
    String[] StringVideoName;
    String[] StringVideourl;
    AdapterLive adapter;
    private int columnWidth;
    public ImageLoader imageLoader;
    List<ItemDailyKatha> listItem;
    ListView listView;
    private ItemDailyKatha object;
    private ItemDailyKatha objectforzero;
    int textlength;
    JsonUtils util;

    /* renamed from: com.bhujmandir.fragments.FragmentLiveDharsan.1 */
    class C00641 implements OnItemClickListener {
        C00641() {
        }

        public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            if (FragmentLiveDharsan.this.listItem.size() >= 1) {
                String VideoPlayId = FragmentLiveDharsan.this.StringVideo[position];
                String VideoUrl = FragmentLiveDharsan.this.StringVideourl[position];
                String VideoPlayName = FragmentLiveDharsan.this.StringVideoName[position];
                if (VideoPlayId.equals("000q1w2")) {
//                    FragmentLiveDharsan.this.startActivity(new Intent(null, Uri.parse("file://" + VideoUrl),
//                            FragmentLiveDharsan.this.getActivity(), VideoPlayerActivity.class));
                    System.out.println("VideoPlayerActivity......."+VideoUrl);
                    return;
                }
                Toast.makeText(getActivity(), ""+VideoUrl, Toast.LENGTH_LONG).show();
                System.out.println("VideoUrl..."+VideoUrl);
                System.out.println("VideoPlayId..."+VideoPlayId);
                Intent i = new Intent(FragmentLiveDharsan.this.getActivity(), YoutubePlay.class);
                i.putExtra("ID", VideoPlayId);
                i.putExtra("name", VideoPlayName);
                FragmentLiveDharsan.this.startActivity(i);
                return;
            }
            Toast.makeText(FragmentLiveDharsan.this.getActivity(), "There is no any live katha streaming at now!", Toast.LENGTH_LONG).show();
        }
    }
    public static Dialog createProgressDialog(Context mContext) {
        Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setContentView(R.layout.progressdialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }
    private class MyTask extends AsyncTask<String, Void, String> {
        private MyTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            FragmentLiveDharsan.progressDialog = createProgressDialog(FragmentLiveDharsan.this.getActivity());
            FragmentLiveDharsan.progressDialog.show();
        }

        protected String doInBackground(String... params) {
            return JsonUtils.getJSONString(params[0]);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (FragmentLiveDharsan.progressDialog != null && FragmentLiveDharsan.progressDialog.isShowing()) {
                FragmentLiveDharsan.progressDialog.dismiss();
            }
            if (result == null || result.length() == 0) {
                Toast.makeText(FragmentLiveDharsan.this.getActivity(), "Failed Connect to Network!!", Toast.LENGTH_LONG).show();
                return;
            }
            try {
                JSONArray jsonArray = new JSONObject(result).getJSONArray("YourVideosChannel");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject objJson = jsonArray.getJSONObject(i);
                    ItemDailyKatha objItem = new ItemDailyKatha();
                    objItem.setId(objJson.getInt("id"));
                    objItem.setVideoUrl(objJson.getString("video_url"));
                    objItem.setVideoId(objJson.getString("video_id"));
                    objItem.setVideoName(objJson.getString("video_title"));
                    objItem.setDuration(objJson.getString("video_duration"));
                    objItem.setDescription(objJson.getString("video_description"));
                    objItem.setImageUrl(objJson.getString("video_thumbnail"));
                    FragmentLiveDharsan.this.listItem.add(objItem);

                    
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int j = 0; j < FragmentLiveDharsan.this.listItem.size(); j++) {
                FragmentLiveDharsan.this.object = (ItemDailyKatha) FragmentLiveDharsan.this.listItem.get(j);
                FragmentLiveDharsan.this.ArrayVideo.add(FragmentLiveDharsan.this.object.getVideoId());
                FragmentLiveDharsan.this.StringVideo = (String[]) FragmentLiveDharsan.this.ArrayVideo.toArray(FragmentLiveDharsan.this.StringVideo);
                FragmentLiveDharsan.this.ArrayVideoId.add(String.valueOf(FragmentLiveDharsan.this.object.getId()));
                FragmentLiveDharsan.this.StringVideoId = (String[]) FragmentLiveDharsan.this.ArrayVideoId.toArray(FragmentLiveDharsan.this.StringVideoId);
                FragmentLiveDharsan.this.ArrayVideoUrl.add(String.valueOf(FragmentLiveDharsan.this.object.getVideoUrl()));
                FragmentLiveDharsan.this.StringVideourl = (String[]) FragmentLiveDharsan.this.ArrayVideoUrl.toArray(FragmentLiveDharsan.this.StringVideourl);
                FragmentLiveDharsan.this.ArrayVideoName.add(String.valueOf(FragmentLiveDharsan.this.object.getVideoName()));
                FragmentLiveDharsan.this.StringVideoName = (String[]) FragmentLiveDharsan.this.ArrayVideoName.toArray(FragmentLiveDharsan.this.StringVideoName);
                FragmentLiveDharsan.this.ArrayVideoDuration.add(String.valueOf(FragmentLiveDharsan.this.object.getDuration()));
                FragmentLiveDharsan.this.StringVideoDuration = (String[]) FragmentLiveDharsan.this.ArrayVideoDuration.toArray(FragmentLiveDharsan.this.StringVideoDuration);
                FragmentLiveDharsan.this.ArrayVideoDesc.add(FragmentLiveDharsan.this.object.getDescription());
                FragmentLiveDharsan.this.StringVideoDesc = (String[]) FragmentLiveDharsan.this.ArrayVideoDesc.toArray(FragmentLiveDharsan.this.StringVideoDesc);
                FragmentLiveDharsan.this.ArrayImageUrl.add(FragmentLiveDharsan.this.object.getImageUrl());
                FragmentLiveDharsan.this.StringImageUrl = (String[]) FragmentLiveDharsan.this.ArrayImageUrl.toArray(FragmentLiveDharsan.this.StringImageUrl);
            }
            FragmentLiveDharsan.this.setAdapterToListview();
        }
    }

    public FragmentLiveDharsan() {
        this.textlength = 0;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_live_katha, container, false);
        this.imageLoader = new ImageLoader(getActivity());
        setHasOptionsMenu(true);
        this.listView = (ListView) rootView.findViewById(R.id.lsv_latest);
        this.listItem = new ArrayList();
        this.ArrayVideo = new ArrayList();
        this.ArrayVideoCatName = new ArrayList();
        this.ArrayVideoCatId = new ArrayList();
        this.ArrayVideoId = new ArrayList();
        this.ArrayVideoName = new ArrayList();
        this.ArrayVideoUrl = new ArrayList();
        this.ArrayVideoDuration = new ArrayList();
        this.ArrayVideoDesc = new ArrayList();
        this.ArrayImageUrl = new ArrayList();
        this.StringVideo = new String[this.ArrayVideo.size()];
        this.StringVideoCatName = new String[this.ArrayVideoCatName.size()];
        this.StringVideoId = new String[this.ArrayVideoId.size()];
        this.StringVideoCatId = new String[this.ArrayVideoCatId.size()];
        this.StringVideourl = new String[this.ArrayVideoUrl.size()];
        this.StringVideoName = new String[this.ArrayVideoName.size()];
        this.StringVideoDuration = new String[this.ArrayVideoDuration.size()];
        this.StringVideoDesc = new String[this.ArrayVideoDesc.size()];
        this.StringImageUrl = new String[this.ArrayImageUrl.size()];
        this.util = new JsonUtils(getActivity());
        if (JsonUtils.isNetworkAvailable(getActivity())) {
            new MyTask().execute(new String[]{"http://apps.bhujmandir.org/SSMB/darshan-api.php?latest=20"});
        } else {
            Toast.makeText(getActivity(), "Failed Connect to Network!!", Toast.LENGTH_LONG).show();
        }
        this.listView.setOnItemClickListener(new C00641());
        return rootView;
    }

    public void setAdapterToListview() {
        this.adapter = new AdapterLive(getActivity(), R.layout.lsv_item_model_list_live_video, this.listItem, this.columnWidth);
        this.listView.setAdapter(this.adapter);
    }

    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }
}
