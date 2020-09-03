var express = require('express');
var multer = require('multer');
//const USERS = require ('../database/users');
const Home = require('../../../database/homes');
var Img = require('../../../database/img');
var valid = require('../../../utils/valid');

const HOMES = Home.model;
const HOMESCHEMA = Home.schema;
//var valid = require('../utils/valid');
var router = express.Router();
//inicio multer
var storage = multer.diskStorage({
  destination: "./public/homesImg",
  filename: function (req, file, cb) {
    console.log("-------------------------");
    console.log(file);
    cb(null, "IMG_" + Date.now() + ".jpg");
  }
});
var upload = multer({
  storage: storage
}).single("img");;
//fin multer


//get buscar casas
router.get("/listhomes", function (req, res) {
  var params = req.query;
  console.log(params);
  var filter =  {};
  
  // filtro de precio
  if (params.minp != null && params.maxp != null) {
    filter["precio"] = {"$gt": Number(params.minp), "$lt": Number(params.maxp)}
  }
  // filtro de superficie
  if (params.mins != null) {
    filter["supTerreno"] = {"$gt": Number(params.mins)}
  }
  //filtro de año de construccion
  if (params.miny != null ) {
    filter["yearCOns"] = {"$gt": Number(params.miny)}
  }
  //filtro de habitaciones
  if (params.minh != null ) {
    filter["numDormitorios"] = {"$gt": Number(params.minh) }
  }
  // filtro de baños
  if (params.minb != null ) {
    filter["numBanos"] = {"$gt": Number(params.minb)}
  }
  //filtro de oferta: de casa Venta o Alquiler 
  if (params.searcho != null) {
    filter["oferta"] = new RegExp(params.searcho, "g");
  }
  //filtro de tipo: Casa, Departamento, Terreno
  if (params.searcht != null) {
    filter["tipo"] = new RegExp(params.searcht, "g");
  }
  //filtro de Zona
  if (params.searchz != null) {
    filter["zona"] = new RegExp(params.searchz, "g");
  }
  //------------------

  /*if (params.limit != null) {
  limit = parseInt(params.limit);
  }
  var order = -1;
  if (params.order != null) {
  if (params.order == "desc") {
  order = -1;
  } else if (params.order == "asc") {
  order = 1;
  }
  }
  var skip = 10;
  if (params.skip != null) {
  skip = parseInt(params.skip);
  }

  if (params.search != null) {
    //filter["direccion"] = new RegExp(params.search, "g");
    filter["ciudad"] = new RegExp(params.search, "g");

  }
  if  (params.search2 != null ) {

    if ( params.search2 == "*"){
      filter["ciudad"] = new RegExp(params.search, "g");
    }else {filter["zona"] = new RegExp(params.search2, "g");}

    //filter["ciudad"] = new RegExp(params.search, "g");
    //filter3["zona"] = new RegExp(params.search, "g");
  }
*/
  HOMES.find(filter).exec((err, docs) => {
  res.status(200).json(docs);
  });
  
  });

//get casas

router.get("/listhomes1", function (req, res) {
  var params = req.query;
  console.log(params);
  var limit = 100;
  var filter =  {};
  var filter2 = {};
  var filter3 = {};
  if (params.min != null && params.max != null) {
    filter["precio"] = {"$gt": Number(params.min), "$lt": Number(params.max)}
  }
  if (params.limit != null) {
  limit = parseInt(params.limit);
  }
  var order = -1;
  if (params.order != null) {
  if (params.order == "desc") {
  order = -1;
  } else if (params.order == "asc") {
  order = 1;
  }
  }
  var skip = 10;
  if (params.skip != null) {
  skip = parseInt(params.skip);
  }

  if (params.search != null) {
    //filter["direccion"] = new RegExp(params.search, "g");
    filter["ciudad"] = new RegExp(params.search, "g");

  }
  if  (params.search2 != null ) {

    if ( params.search2 == "*"){
      filter["ciudad"] = new RegExp(params.search, "g");
    }else {filter["zona"] = new RegExp(params.search2, "g");}

    //filter["ciudad"] = new RegExp(params.search, "g");
    //filter3["zona"] = new RegExp(params.search, "g");
  }

  HOMES.find(filter)/*.limit(limit).sort({_id: order}).skip(skip)*/.exec((err, docs) => {
  res.status(200).json(docs);
  });
  //HOMES.find(filter3);
  });


//post de una casa
router.post("/listhomes1", (req, res) => {
    var params = req.body
    params["registerdate"] = new Date();
    params["verif"]=["si"];
    params["img"] = "";
    //Ejemplo de validacion


    /*/if (!valid.checkParams(HOMESCHEMA, params)){
      res.status(300).json({mns:"order input error"});
      return;*/

    //fin validacion
    /*var home = {
            idVendedor: req.body.idVendedor,
            tipo: req.body.tipo ,
            oferta   :  req.body.oferta,
            estado   :  req.body.estado ,
            ciudad   : req.body.ciudad,
            zona   :    req.body.zona   ,
            idZona  : req.body.idZona,
            direccion   :    req.body.direccion   ,
            precio   :    req.body.precio   ,
            descripcion   :   req.body.descripcion,
            supTerreno   :   req.body.supTerreno  ,
            servicios   :    req.body.servicios  ,
            numDormitorios   :    req.body.numDormitorios   ,
            numBanos   :    req.body.numBanos  ,
            numPisos   :    req.body.numPisos ,
            piscina   :    req.body.piscina  ,
            capGaraje :    req.body.capGaraje ,
            amoblado   :    req.body.amoblado ,
            yearCOns:req.body.yearCOns,
            fec_publicacion   :  new Date(),
            latitud   :    req.body.latitud  ,
            longitud   :    req.body.longitud  ,
            img:""
    };*/
    //var homeData = new HOMES(home);
    var homeData = new HOMES(params);
    homeData.save().then( (rr) => {
      //content-type
      res.status(200).json({
        "id" : rr._id,
        "msn" : "casa registrada con exito "
      });
    });
  });


//post imagen
//id prueba: 5df2f3237f90980173ce3088
router.post("/homeimg" ,(req, res) => {
  var params = req.query;
  var id = params.id;
  var SUPERES = res;
  HOMES.findOne({_id: id},{idVendedor: id} ).exec((err, docs) => {
    if (err) {
      res.status(501).json({
        "msn" : "Problemas con la base de datos"
      });
      return;
    }
    if (docs != undefined) {
      upload(req, res, (err) => {
        if (err) {
          res.status(500).json({
            "msn" : "Error al subir la imagen"
          });
          return;
        }
        var url = req.file.path.replace(/public/g, "");

        HOMES.update({_id: id}, {$set:{img:url}}, (err, docs) => {
          if (err) {
            res.status(200).json({
              "msn" : err
            });
            return;
          }
          res.status(200).json(docs);
        });
      });
    }
  });
});
module.exports = router;
