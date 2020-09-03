var valid = {
    checkParams: function(refobj, evalueobj) {
    if (Object.keys(refobj).sort().toString()==Object.keys(evalueobj).sort().toString()){
        return true;
    }
    return false;
    },
    checkPassword: function (password) {
        var exp = /(?=.*[A-Z])(?=.*[a-z])(?=.*\d)[a-zA-Z\d]{8,}/g       
        if (password.match(exp)==null){
            return false;
        }
        return true;

    },
    checkEmail: function(email) {
        var expresion = /\w{1,}@(gmail.com|hotmail.com)/g //solo se admiten correos gmail y hotmail
        if (email.match(expresion)==null){
            return false;
        }
        return true;
    }

    };
    module.exports = valid;