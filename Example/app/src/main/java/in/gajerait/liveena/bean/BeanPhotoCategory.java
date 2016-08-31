package in.gajerait.liveena.bean;

import java.io.Serializable;
import java.util.List;


public class BeanPhotoCategory implements Serializable {


    public String id;
    public String category;
    public String image;
    public String sort_order;
    public String sidebar_image;
    public String name;
    public String description;

    public BeanPhotoCategory(String id, String category, String image, String sidebar_image, String sort_order) {
        this.id = id;
        this.category = category;
        this.image = image;
        this.sidebar_image = sidebar_image;
        this.sort_order = sort_order;


    }
    public BeanPhotoCategory(String id, String name, String description, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;

    }

    public BeanPhotoCategory( String category, String image) {

        this.category = category;
        this.image = image;


    }

    public BeanPhotoCategory(List<BeanPhotoCategory> movieList) {
    }

    public String getVoucher_id() {
        return id;
    }

    public void setVoucher_id(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVoucher_image() {
        return image;
    }

    public void setVoucher_image(String image) {
        this.image = image;
    }

    public void setsidebar_image(String sidebar_image) {
        this.sidebar_image = sidebar_image;
    }

    public String getsidebar_image() {
        return sidebar_image;
    }

    public void setsort_order(String sort_order) {
        this.sort_order = sort_order;
    }

    public String getsort_order() {
        return sort_order;
    }


}
