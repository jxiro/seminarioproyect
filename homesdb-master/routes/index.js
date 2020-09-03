var express = require('express');
var router = express.Router();
var LISTING = require("../database/homesshema");





/* GET home page. */
router.get('/listhomesa', function(req, res) {
  res.write('bienvenido');
  res.end();

});


router.get('/listhomes', function(req, res, next) {

  var skip = 0;
  var limit = 20;
  var params = req.query;
  var filter = {};
  if (params.skip != null) {
    skip = Number(params.skip);
  }
  if (params.limit != null) {
    limit = Number(params.limit);
  }
  if (params.search != null) {
    filter["directions"] = new RegExp(params.search, "g");
  }
  if (params.streetnumber != null) {
    filter["streetNumber"] = new RegExp(params.streetNumber, "g");
  }
  if (params.min != null && params.max != null) {
    filter["listPrice"] = {"$gt": Number(params.min), "$lt": Number(params.max)}
  }
  if (params.mlsId != null) {
    filter["mlsId"] = params.mlsId;
  }
  var   cad = "description directions disclaimer lat lng primary_photo listPrice";
  if (params.detail != null) {
    console.log(params.detail);
    if (params.detail == "true") {
      cad = "";
    } else if(params.detail == "false"){
      cad = "description directions disclaimer lat lng primary_photo listPrice";
    }
  }
  if (cad == "") {
    LISTING.find(filter).skip(skip).limit(limit).exec((err, docs) => {
      console.log("ENTER HERE")
      if (err) {
        res.status(300).json({
          msn:"Error en la base de datos"
        });
        return;
      }
      res.status(200).json(docs);
      return;
    });
  } else {
    LISTING.find({}).exec((err, docs) => {
      console.log("ENTER HERE")
      if (err) {
        res.status(300).json({
          msn:"Error en la base de datos"
        });
        return;
      }
      res.status(200).json(docs);
      return;
    });
  }
  
});

module.exports = router;
