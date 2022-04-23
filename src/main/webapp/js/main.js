var firebaseConfig = {
    apiKey: "AIzaSyAdqMjhNu83InirQelkIBMCz4bKGFXvKzc",
    authDomain: "haytruyen-da8c2.firebaseapp.com",
    projectId: "haytruyen-da8c2",
    storageBucket: "haytruyen-da8c2.appspot.com",
    messagingSenderId: "655186480380",
    appId: "1:655186480380:web:a3e61760ea2314d6abdb79",
    measurementId: "G-E8S1WG5FCQ"
};

var uiConfig = {
    signInFlow: 'popup',
    signInOptions: [
        firebase.auth.EmailAuthProvider.PROVIDER_ID,
        firebase.auth.PhoneAuthProvider.PROVIDER_ID,
        firebase.auth.GoogleAuthProvider.PROVIDER_ID
    ],
    callbacks: {
        signInSuccessWithAuthResult: function (authResult) {
            if (authResult.user) {
                handleSignedInUser(authResult.user);
            }
            return false;
        },
        signInFailure: function (error) {
        }
    },

    autoUpgradeAnonymousUsers: true
};
var ui
$(function () {
    firebase.initializeApp(firebaseConfig);
    ui = new firebaseui.auth.AuthUI(firebase.auth());
    ui.start('#firebaseui-auth-container', uiConfig);
    firebase.auth().onAuthStateChanged(function (user) {
        user ? handleSignedInUser(user) : handleSignedOutUser();
        $("#login-spinner").addClass("d-none")
    });
});

function handleSignedInUser(user) {
    $(".user").removeClass("d-none")
    $(".guest").addClass("d-none")
    $("#name").text(user.displayName);
    $("#email").text(user.email);
    $("#phone").text(user.phoneNumber);
    if (user.photoURL) {
        $(".avatar").attr("src",user.photoURL);
    } else {
        $(".avatar").attr("src","/images/user.svg");
    }
    $('#modal-login').modal('hide');
}
function handleSignedOutUser() {
    ui.start("#firebaseui-auth-container", uiConfig);
    $(".user").addClass("d-none")
    $(".guest").removeClass("d-none")
}

//fix loi collapse menu
function collapseMenu() {
    if (document.getElementById("myNavbar").className==="navbar-collapse collapse show")
        document.getElementById("myNavbar").className = "navbar-collapse collapse";
    else document.getElementById("myNavbar").className = "navbar-collapse collapse show";
}