package vn.edu.hust.soict.khacsan.docbaoonline.Model;

/**
 * Created by San on 11/17/2017.
 */

public class TinTuc {
    private String title,image,date,description,link;

    public TinTuc(String title, String image, String date, String description, String link) {
        this.title = title;
        this.image = image;
        this.date = date;
        this.description = description;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
