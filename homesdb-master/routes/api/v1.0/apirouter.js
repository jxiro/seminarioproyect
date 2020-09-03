var express = require('express');
//const USERS = require ('../database/users');
const user = require('../../../database/users');

const USERS = user.model;
const USERSCHEMA = user.schema; 
var valid = require('../../../utils/valid');
var router = express.Router();
var jwt = require("jsonwebtoken");
const key = "jairoesungriton666";

/*function generateToken(users) {
    var u = {
     name: user.email,
     id: user.id
    }
    return token = jwt.sign(u, key,(err, {
 expiresIn: 60 * 60 * 24 // expires in 24 hours
})
   
  }*/

function verifyToken (req, res, next) {
    const header = req.headers["authorization"];
    if (header==null){
        res.status(300).json({
            "msn": "NO TIENES EL TOKEN"
        });
        return;
    }
    req.token=header;
    jwt.verify(req.token, key, (err, authData)=>{
        if (err){
            res.status(403).json({
                "msn": "bad token"
            });
            return;
        }
        res.status(403).json(authData);
    }); 
}


//post user
router.post('/listhomes/user1', async(req, res) => {
var params = req.body;

    

params["registerdate"] = new Date();
params["rols"]=["user"];



//params valid
if (!valid.checkParams(USERSCHEMA, params)){
    res.status(300).json({mns:"order input error"});
    return;
}
//pass valid
if (!valid.checkPassword(params.password)){
    res.status(300).json({mns:"bad pass"});
    return;
}
//email valid
if (!valid.checkEmail(params.email)){
    res.status(300).json({mns:"bad email"});
    return;
}

var users = new USERS(params);
var result = await users.save();
res.status(200).json(result);

});

//post login

//for customer users
router.post('/listhomes/login',(req,res) =>{
    console.log(req.body);
    var useremail = req.body.email;
    var password = req.body.password;
    var customerUser = USERS.findOne({email:useremail,password:password}).exec((err,doc)=>{
        if(err)
        {
            res.status(500).json({
                msn:"Error en la base de datos"
            });
            return;
        }
        if(doc) 
        {
            
            jwt.sign({useremail:doc.email,password:doc.password},key,(err,token)=>{
                res.status(200).json({
                    msn :"Inicio de sesion exitosa",
                    token:token,
                    'id':doc._id
                    
                });
            })
        }
        else{
            res.status(400).json({
                'msn' : "El username o password son incorrectos o no existen en la base de datos"
            });
        }
    }) 
});


//get

router.get("/listhomes/user",verifyToken, (req, res) => {
    var params = req.query;
    console.log(params);
    var limit = 100;
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
    USERS.find({})/*.limit(limit).sort({_id: order}).skip(skip)*/.exec((err, docs) => {
    res.status(200).json(docs);
    });
    });
//get id
router.get("/listhomes/user/id",(req, res) => {
    var params = req.body;

    idd = params.email
  USERS.find({email:idd},{"_id":1}).exec((err, docs) => {
    res.status(200).json(docs);
    });
    });
    
 






    //get patch

    router.patch("/user", (req, res) => {
        if (req.query.id == null) {
        res.status(300).json({
        msn: "Error no existe id"
        });
        return;
        }
        var id = req.query.id;
        var params = req.body;
        USERS.findOneAndUpdate({_id: id}, params, (err, docs) => {
        res.status(200).json(docs);
        });
        });


        //delete

        router.delete("/user", async(req, res) => {
            if (req.query.id == null) {
            res.status(300).json({
            msn: "Error no existe id"
            });return;
            }
            var r = await USERS.remove({_id: req.query.id});
            res.status(300).json(r);
            });



            module.exports = router;
