// this is the equivalent of abstract class
// implementation will set all variables in articleData
// I'm mainly doing this such that if we decided to turn from NewsAPI we don't have to change much code here
// this will just contain the functions that does processing
class Article {
    // our Article class is meant asan extension of the NewsAPI object
    constructor(articleData) {
        this.articleData = articleData;
        this.stats = {
            likes: 0,
            dislikes: 0,
            views: 0,
            // further stats will be needed, perhaps conservativeLikes, liberalDislikes, etc
        };
    }
}

module.exports = Article;