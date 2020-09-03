const mongoose = require("./connect");
var HOMESCHEMA = {
    
            idVendedor: String,
            tipo: String ,
            oferta   :  String,
            estado   :  String ,
            ciudad   : String,
            zona   :    String   ,
            direccion   :    String   ,
            precio   :    Number   ,
            descripcion   :   String,             
            supTerreno   :   Number  ,
            servicios   :    String  ,
            numDormitorios   :    Number   ,
            numBanos   :    Number  ,
            numPisos   :    Number ,
            piscina   :    String  ,
            capGaraje :    Number ,
            amoblado   :    String ,
            yearCOns:       Number,
            fec_publicacion   :    Date,
            latitud   :    String  ,
            longitud   :    String   ,
            img         : String,
            verif:Array
            
            }
        
const HOMES = mongoose.model("homes1", HOMESCHEMA);
module.exports = {model: HOMES, schema: HOMESCHEMA};