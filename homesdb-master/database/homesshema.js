var mongo = require("./connect");

var Schema = mongo.Schema;
var thingSchema = new Schema({}, {strict: false});
var LISTING = mongo.model("casas", thingSchema);

module.exports = LISTING;