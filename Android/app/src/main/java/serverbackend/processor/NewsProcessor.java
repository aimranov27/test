package serverbackend.processor;

import java.util.List;

import serverbackend.data.NewsCategory;
import serverbackend.datamanagement.DataSource;

public class NewsProcessor {

    private DataSource ds;

    public NewsProcessor() {
        ds = new DataSource();
    }

    public List<NewsCategory> getAllCategories() throws Exception {
        return ds.getAllCategories();
    }
}
