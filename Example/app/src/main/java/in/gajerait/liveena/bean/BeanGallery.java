package in.gajerait.liveena.bean;

import java.io.Serializable;


public class BeanGallery implements Serializable {
    public String id;
    public String category;
    public String image;
    public String sort_order;
    public String sidebar_image;
    public String you;
    public String link;
    public String name;

    public BeanGallery(String id, String category, String image, String sidebar_image, String sort_order) {
        this.id = id;
        this.category = category;
        this.image = image;
        this.sidebar_image = sidebar_image;
        this.sort_order = sort_order;


    }

    public BeanGallery(String id, String category, String image, String sidebar_image, String sort_order, String you) {
        this.id = id;
        this.category = category;
        this.image = image;
        this.sidebar_image = sidebar_image;
        this.sort_order = sort_order;
        this.you = you;
    }

    public BeanGallery(String id, String category, String image, String sidebar_image, String sort_order, String name, String link) {
        this.id = id;
        this.category = category;
        this.image = image;
        this.sidebar_image = sidebar_image;
        this.sort_order = sort_order;
        this.name = name;
        this.link = link;

    }

    public String getVoucher_id() {
        return id;
    }

    public void setVoucher_id(String id) {
        this.id = id;
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

    public void setyou(String you) {
        this.you = you;
    }

    public String getyou() {
        return you;
    }

    public void setlink(String link) {
        this.link = link;
    }

    public String getlink() {
        return link;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getname() {
        return name;
    }


}
