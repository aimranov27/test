const NewsGetter = require('./NewsGetter.js');
const newsGetter = new NewsGetter();
const Article = require('./Article.js');
// This file will take care of processing the news, and have a dependency on the Article.js class
// This is a Node module, so at the end of the file we export an instance of this class

function categoryTemplate(name, auto) {
    this.name = name;
    this.img = '';
    this.auto = auto;
    this.cachedArticles = []; // still deciding whether to use
}

class NewsProcessor {
    constructor() {
        this.categories = {
            general: new categoryTemplate('general', true),
            entertainment: new categoryTemplate('entertainment', true),
            health: new categoryTemplate('health', true),
            science: new categoryTemplate('science', true),
            sports: new categoryTemplate('sports', true),
            business: new categoryTemplate('business', true),
            politics: new categoryTemplate('politics', true),
            technology: new categoryTemplate('technology', true),
//            recent: new categoryTemplate('recent', false) implement non-automatic get later
        }
    }

    // returns an array consisting of objects with just category names and img urls
    getAllCategories() {
        let allCats = [];
        Object.keys(this.categories).forEach((cat) => {
            allCats.push({
                name: this.categories[cat].name,
                img: this.categories[cat].img
            });
        });
        return allCats;
    }

    // returns an array consisting of all Articles for a given category
    getCategory(cat, page) {
        console.log(`Get category called with category: ${cat} and page: ${page}`);
        console.log(this.categories[cat]);
        if (cat && this.categories[cat]) {
            // really should be trying to get data from database?
            let startIndex = Math.min(this.categories[cat].cachedArticles.length - 1, page * 30);
            let endIndex = Math.min(this.categories[cat].cachedArticles.length - 1, (page * 30) + 30);
            console.log(`Returning elements ${startIndex} to ${endIndex} from ${cat}`);
            return this.categories[cat].cachedArticles.slice(startIndex, endIndex);
        } else {
            return [];
        }
    }
    
    
    
    updateDatabase(newsObject) {
        // to be implemented later
        // will change our MongoDB database
    }
    
    // this function will query NewsAPI and get the results for each category
    async updateNews() {
        let tempNews = {};
        console.log('UPDATING NEWS');
        console.log('--------------------------------------------------------------------');
        for (let category in this.categories) {
            
            if (!this.categories[category].auto) continue; // don't try to automatically get

            console.log(' ');
            console.log(`GETTING ${category}`);
            let rawArticleData = await newsGetter.getNews(category);
            tempNews[category] = [];
            rawArticleData.forEach((article) => {
                
                // update category image to most recent article image
                this.categories[category].img = article.urlToImage;
                
                // next step is converting the raw data into th Article class for storage
                tempNews[category].push(new Article(article));
                
            });
            // replace this in new builds, this basically adds the non-new articles to the
                // cached articles. Problem is that this will erase any stats data, also no size
                // check
            this.categories[category].cachedArticles = [...tempNews[category], 
                                                        ...this.categories[category].cachedArticles];
            console.log(`categories length is ${this.categories[category].cachedArticles.length}
                        , tempNews is ${tempNews[category].length}`);
        }
        
        // now we will send the tempNews data to the database manager
        this.updateDatabase(tempNews);
    }
}

// we are exporting an instance of NewsProcessor that way there is only one NewsProcessor running
// at once
module.exports = new NewsProcessor();
