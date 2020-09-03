var express = require('express');
//const USERS = require ('../database/users');
const Nes = require('../../../database/neighborhood');
var Img = require('../../../database/img');

const NES = Nes.model;
const NESCHEMA = Nes.schema; 
//var valid = require('../utils/valid');
var router = express.Router();

//get vecindario
router.get("/ne", (req, res) => {
    var params = req.query;
    console.log(params);
   
 
    
    NES.find({}).sort({_id:-1}).exec((err, docs) => {
    res.status(200).json(docs);
    });
    });



  //post de un vecindario
  router.post("/ne", (req, res) => {
      
      var params = req.query;
      
      var neData = new NES(params);
    
      neData.save().then( (rr) => {
        //content-type
        res.status(200).json({
          "nombre" : rr.nombre,
          "lat" : rr.coordenadas,
          //"lgn" : rr.lgn,
          "msn" : "vecindario registrado con exito "
        });
      });
    });
    module.exports = router;