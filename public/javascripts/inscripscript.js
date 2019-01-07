
  /*let em = document.getElementById("emaile").value;
   let ems = document.getElementById("emails").value;
   let mdp = document.getElementById("motdepasse").value;
   let mdps = document.getElementById("motdepasses").value;
   let bd = document.getElementById("birthdate").value;*/


  //Vérification de toutes les entrées
  let elms = document.querySelectorAll("form.carre > input"), i;
  for (i = 0; i < elms.length; ++i) {
      //elms[i].style.color = "green";

      elms[i].onkeyup = function (event) {
          let em = document.getElementById("inputEmail").value;
          let ems = document.getElementById("inputEmails").value;
          let mdp = document.getElementById("inputPassword").value;
          let mdps = document.getElementById("inputPasswords").value;
          let bd = document.getElementById("inputDateNaissance").value;
          if (em != "" && ems != "" && mdp != "" && mdps != "" && bd != "") {
              document.getElementById("bouton").value = "OK!";

          } else {
              document.getElementById("bouton").value = "not OK!";
          }
      };
  }


window.onload=function() {
    //let emtest = "mat.menn@mca-benelux.org";


    bouton.onclick = function (event) {
        checkEmail();
        checkAge();
        //Geson();
    }

    function checkEmail() {
        let emtest = 'mat.menn@mca-benelux.org';
        let em = document.getElementById("inputEmail").value;
        /*let ems = document.getElementById("emails").value;
        if (em !== ems) {
            alert("pas le même email");
        }*/
        if (em === emtest) {
            alert("déjà utilisé");
            document.getElementById("used").style.visibility = "visible";

        }
        else {
            document.getElementById("used").style.visibility = "hidden";

        }
    }

    function checkAge(dateString) {
        let bd = document.getElementById("inputDateNaissance").value;
        let today = new Date();
        let birthDate = new Date(bd);
        let age = today.getFullYear() - birthDate.getFullYear();
        let m = today.getMonth() - birthDate.getMonth();
        if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
            age--;
        }
        if (age < 16) {
            alert("tu es trop jeune");
            document.getElementById("seizeans").style.visibility = "visible";

        } else {
            document.getElementById("seizeans").style.visibility = "hidden";

            document.getElementById("myForm").submit();

        }
    }

//}

    document.querySelector("#inputEmails").onchange = function (event) {
        let em = document.getElementById("emaile").value;
        let ems = document.getElementById("emails").value;
        let mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
        if (document.getElementById("inputEmail").value.match(mailformat) && document.getElementById("inputEmails").value.match(mailformat) && em !== ems) {
            alert("pas le même email");
        }
    }

    document.querySelector("#inputPasswords").onchange = function (event) {
        let mdp = document.getElementById("inputPassword").value;
        let mdps = document.getElementById("inputPasswords").value;
        if (mdp !== mdps) {
            alert("pas le même mot de passe");
        }
    }



}
//document.getElementById("bouton").disabled = false;
// document.querySelectorAll().onchange= function (event){
// };
