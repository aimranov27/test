package serverbackend.data;

public class NewsArticle {

    private String title;
    private String source;
    private String author;
    private String description;
    private String imgURL;

    public NewsArticle(String title, String source, String author, String description,
                       String imgURL) {
        this.title = title;
        this.source = source;
        this.author = author;
        this.description = description;
        this.imgURL = imgURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
