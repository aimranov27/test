// this file is the start point of our server for our news app
var express = require('express');
var app = express();
const bodyParser = require("body-parser");

// Parses post requests
app.use(
    bodyParser.urlencoded({
        extended: true
    })
);
app.use(bodyParser.json());

// 'news' will be another file that exports itself and concerns itself with gathering and 
// sorting the news
var newsProcessor = require('./backend/NewsProcessor.js');

// api routes for user sign up / log in.
app.use('/users', require('./backend/UserController'));

//FOR TESTING PURPOSES SINCE WE HAVE LIMITED API CALLS WE WILL HAVE PAUSE ENDPOINT
let intervalID;
app.use('/pause', (req, res) => {
    clearInterval(intervalID);
    res.send("aight this has been paused");
});


// endpoint for when trying to get an array containing all category names and their image
app.use('/getAllCategories', (req, res) => {
    res.json(newsProcessor.getAllCategories());
});

// endpoint for when trying to get an array containing all category names and their image
app.use('/getNewsFromCategory', (req, res) => {
    if (!req || !req.query || !req.query.category || req.query.category == '') {
        console.log('bad query');
        res.json([]);
        return;
    }
    if (!req.query.page || req.query.page < 0) {
        req.query.page = 0;
    }
    res.json(newsProcessor.getCategory(req.query.category, req.query.page));
});

// This just sends back a message for any URL path not covered above
app.use('/', (req, res) => {
    let message = `
        Currently supported endpoints:
        /getAllCategories - this will return an array of objects of categories
        /getNewsFromCategory - with query param category=, this will return array of news articles
    `;
    res.send(message);
});

// This starts the web server on port 3000. 
app.listen(3000, () => {
    console.log('Listening on port 3000');
    newsProcessor.updateNews()
    intervalID = setInterval(newsProcessor.updateNews, 1800000);
});