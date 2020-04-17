const NewsAPI = require('newsapi');
const newsapi = new NewsAPI('f86db005ae744e96ba3357cb992e9d4a');
class NewsGetter {
    constructor() {
        
    }
    
    getPromise(category) {
        let d = new Date();
//        console.log(d.getDay());
        let date = `${d.getFullYear()}-${d.getMonth() + 1}-${d.getDate() - 1}`;
        console.log(date);
        return newsapi.v2.topHeadlines({
//            sources: 'bbc-news,the-verge',
            category: category,
//            from: date,
            language: 'en',
            country: 'us',
            sortBy: 'publishedAt',
            pageSize: 100,
            page: 1
        });
    }
    
    async getNews(category) {
        let articles = [];
        let promiseResults = await this.getPromise(category);
        articles = [...promiseResults.articles];  

        return articles;
    }
}

module.exports = NewsGetter;
