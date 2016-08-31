package in.gajerait.liveena.bean;

public class Bean {

    public String name;
    public String image_icon;

    public Bean(String name, String image_icon) {
        this.name = name;
        this.image_icon = image_icon;
    }

    public String getName() {
        return this.name;
    }

    public String getImg_Icon() {
        return this.image_icon;
    }

}
