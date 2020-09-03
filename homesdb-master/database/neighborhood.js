const mongoose = require("./connect");
var NESCHEMA = {
          
            departamento: String,
            nombre: String,
            zoom:Number,
            latcenter: Number,
            longcenter: Number,
            coordenadas: Array
            }
        
const NES = mongoose.model("neighborhood", NESCHEMA);
module.exports = {model: NES, schema: NESCHEMA};