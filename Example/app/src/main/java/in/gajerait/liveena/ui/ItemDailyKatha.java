package in.gajerait.liveena.ui;

public class ItemDailyKatha {
    private String Description;
    private String Duration;
    private String ImageUrl;
    private String VType;
    private String VideoId;
    private String VideoName;
    private String VideoUrl;
    private int id;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVType() {
        return this.VType;
    }

    public void setVType(String VType) {
        this.VType = VType;
    }

    public String getVideoUrl() {
        return this.VideoUrl;
    }

    public void setVideoUrl(String videourl) {
        this.VideoUrl = videourl;
    }

    public String getVideoId() {
        return this.VideoId;
    }

    public void setVideoId(String videoid) {
        this.VideoId = videoid;
    }

    public String getVideoName() {
        return this.VideoName;
    }

    public void setVideoName(String videoname) {
        this.VideoName = videoname;
    }

    public String getDuration() {
        return this.Duration;
    }

    public void setDuration(String duration) {
        this.Duration = duration;
    }

    public String getDescription() {
        return this.Description;
    }

    public void setDescription(String desc) {
        this.Description = desc;
    }

    public String getImageUrl() {
        return this.ImageUrl;
    }

    public void setImageUrl(String imageurl) {
        this.ImageUrl = imageurl;
    }
}
