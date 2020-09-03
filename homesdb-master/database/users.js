const mongoose = require("./connect");
var USERSCHEMA = {
//id: Number,
email: String,
password : String,
name: String,
surname : String,
registerdate: Date,
address : String,
phone : String,
rols:Array
}
const USERS = mongoose.model("usuarios1", USERSCHEMA);
module.exports = {model: USERS, schema: USERSCHEMA};