const mongoose = require("mongoose");
mongoose.connect("mongodb://172.23.0.2:27017/homesdb", {useNewUrlParser: true});
//mongoose.connect("mongodb://192.168.43.126:27017/homesdb", {useNewUrlParser: true});

if (mongoose) {
    console.log("Conectado");
}
module.exports = mongoose;
