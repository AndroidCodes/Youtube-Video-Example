package in.gajerait.liveena.bean;

public class Movie {
    private String title;
    private int image;

    public Movie() {
    }

    public Movie(String title, int image) {
        this.title = title;
        this.image = image;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}