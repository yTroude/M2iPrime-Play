

  //Vérification de toutes les entrées
  let elms = document.querySelectorAll("form.carre > input"), i;
  for (i = 0; i < elms.length; ++i) {
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

  document.querySelector("#inputEmails").onchange = function (event) {
      let em = document.getElementById("inputEmail").value;
      let ems = document.getElementById("inputEmails").value;
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

window.onload=function() {



    bouton.onclick = function (event) {

        if (!checkEmail()) {
            document.getElementById("used").style.visibility = 'visible';
            return false;
        } else if (checkMemeEmail()) {
            alert("pas le même email");
            return false;
        } else if (checkMemeMdp()) {
            alert("pas le même mot de passe");
            return false;
        } else if (!checkAge()) {
            document.getElementById("seizeans").style.visibility = 'visible';
            return false;
        }


    }

    function checkEmail() {
        let emtest = 'mat.menn@mca-benelux.org';
        let em = document.getElementById("inputEmail").value;
        return (em == emtest) ? false : true;

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
        return (age < 16) ? false : true;
    }
    function checkMemeEmail() {
        let em = document.getElementById("inputEmail").value;
        let ems = document.getElementById("inputEmails").value;
        return (em == ems) ? false : true;

    }
    function checkMemeMdp() {
        let mdp = document.getElementById("inputPassword").value;
        let mdps = document.getElementById("inputPasswords").value;
        return (mdp == mdps) ? false : true;

    }


}

