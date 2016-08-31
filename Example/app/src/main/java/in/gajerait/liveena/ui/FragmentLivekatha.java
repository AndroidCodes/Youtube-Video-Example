package in.gajerait.liveena.ui;

import android.app.Dialog;
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


public class FragmentLivekatha extends Fragment {
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

    /* renamed from: com.bhujmandir.fragments.FragmentLivekatha.1 */
    class C00651 implements OnItemClickListener {
        C00651() {
        }

        public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            if (FragmentLivekatha.this.listItem.size() >= 1) {
                String VideoPlayId = FragmentLivekatha.this.StringVideo[position];
                String VideoUrl = FragmentLivekatha.this.StringVideourl[position];
                String VideoPlayName = FragmentLivekatha.this.StringVideoName[position];
                if (VideoPlayId.equals("000q1w2")) {

                    Toast.makeText(getActivity(), "VideoPlayerActivity"+VideoUrl, Toast.LENGTH_SHORT).show();
//                    FragmentLivekatha.this.startActivity(new Intent(null, Uri.parse("file://" + VideoUrl),
//                            FragmentLivekatha.this.getActivity(), VideoPlayerActivity.class));
                    return;
                }
//                Intent i = new Intent(FragmentLivekatha.this.getActivity(), YoutubePlayHide.class);
//                i.putExtra(JsonConstant.LATEST_ID, VideoPlayId);
//                i.putExtra("name", VideoPlayName);
//                FragmentLivekatha.this.startActivity(i);
                return;
            }
            Toast.makeText(FragmentLivekatha.this.getActivity(), "There is no any live katha streaming at now!", Toast.LENGTH_LONG).show();
        }
    }

    private class MyTask extends AsyncTask<String, Void, String> {
        private MyTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
//            FragmentLivekatha.progressDialog = Utility.createProgressDialog(FragmentLivekatha.this.getActivity());
//            FragmentLivekatha.progressDialog.show();
        }

        protected String doInBackground(String... params) {
            return JsonUtils.getJSONString(params[0]);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (FragmentLivekatha.progressDialog != null && FragmentLivekatha.progressDialog.isShowing()) {
                FragmentLivekatha.progressDialog.dismiss();
            }
            if (result == null || result.length() == 0) {
                Toast.makeText(FragmentLivekatha.this.getActivity(), "Failed Connect to Network!!", Toast.LENGTH_LONG).show();
                return;
            }
            try {
                System.out.println("result...."+result);

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
                    FragmentLivekatha.this.listItem.add(objItem);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int j = 0; j < FragmentLivekatha.this.listItem.size(); j++) {
                FragmentLivekatha.this.object = (ItemDailyKatha) FragmentLivekatha.this.listItem.get(j);
                FragmentLivekatha.this.ArrayVideo.add(FragmentLivekatha.this.object.getVideoId());
                FragmentLivekatha.this.StringVideo = (String[]) FragmentLivekatha.this.ArrayVideo.toArray(FragmentLivekatha.this.StringVideo);
                FragmentLivekatha.this.ArrayVideoId.add(String.valueOf(FragmentLivekatha.this.object.getId()));
                FragmentLivekatha.this.StringVideoId = (String[]) FragmentLivekatha.this.ArrayVideoId.toArray(FragmentLivekatha.this.StringVideoId);
                FragmentLivekatha.this.ArrayVideoUrl.add(String.valueOf(FragmentLivekatha.this.object.getVideoUrl()));
                FragmentLivekatha.this.StringVideourl = (String[]) FragmentLivekatha.this.ArrayVideoUrl.toArray(FragmentLivekatha.this.StringVideourl);
                FragmentLivekatha.this.ArrayVideoName.add(String.valueOf(FragmentLivekatha.this.object.getVideoName()));
                FragmentLivekatha.this.StringVideoName = (String[]) FragmentLivekatha.this.ArrayVideoName.toArray(FragmentLivekatha.this.StringVideoName);
                FragmentLivekatha.this.ArrayVideoDuration.add(String.valueOf(FragmentLivekatha.this.object.getDuration()));
                FragmentLivekatha.this.StringVideoDuration = (String[]) FragmentLivekatha.this.ArrayVideoDuration.toArray(FragmentLivekatha.this.StringVideoDuration);
                FragmentLivekatha.this.ArrayVideoDesc.add(FragmentLivekatha.this.object.getDescription());
                FragmentLivekatha.this.StringVideoDesc = (String[]) FragmentLivekatha.this.ArrayVideoDesc.toArray(FragmentLivekatha.this.StringVideoDesc);
                FragmentLivekatha.this.ArrayImageUrl.add(FragmentLivekatha.this.object.getImageUrl());
                FragmentLivekatha.this.StringImageUrl = (String[]) FragmentLivekatha.this.ArrayImageUrl.toArray(FragmentLivekatha.this.StringImageUrl);
            }
            FragmentLivekatha.this.setAdapterToListview();
        }
    }

    public FragmentLivekatha() {
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
            new MyTask().execute(new String[]{"http://apps.bhujmandir.org/SSMB/live-api.php?latest=20"});
        } else {
            Toast.makeText(getActivity(), "Failed Connect to Network!!", Toast.LENGTH_LONG).show();
        }
        this.listView.setOnItemClickListener(new C00651());
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
