var mongo = require("./database/connect");
var Schema = mongo.Schema;
var thingSchema = new Schema({}, {strict: false});
var listings = mongo.model("listing", thingSchema);
var request = require("request");
request.get("http://45.79.108.188/api/v1/listing?type=H&broker=&geo=&price_min=&price_max=&baths=0&beds=0&sqft_min=&sqft_max=&northEastLat=30.369126930154234&northEastLng=-97.68598841082633&southWestLat=30.275626734938765&southWestLng=-97.78778360735953&sortBy=D", async function (header, request, body) {
    var json = JSON.parse(body);
    console.log(json.listings.length);
    for (var i = 1; i < json.listings.length; i++) {
        var data = await getdata(json.listings[i].mlsId);
        
        var jsondata = JSON.parse(data);
        var aux = jsondata[0];
        delete aux._id;
        delete aux.geo_ids;
        console.log(aux);
        var homes = new listings(aux);
        await homes.save();
        console.log("Guardado " + i);
    }
});
function getdata(id) {
    return new Promise((resolve, reject) => {
        request.get("http://45.79.108.188/api/v1/listing/" + id, (header, request, body) => {
            resolve(body);
        });
    });
    
}