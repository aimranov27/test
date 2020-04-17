package serverbackend.data;

public class NewsCategory {
    private String name;
    private String imgURL;

    public NewsCategory(String name, String imgURL) {
        this.name = name;
        this.imgURL = imgURL;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "{ name=" + name + ", imgURL=" + imgURL + " }";
    }
}
