BASE_URL = "http://localhost:8080";

async function login() {
  event.preventDefault();
  let email = loginForm.email.value;
  let password = loginForm.password.value;
  if (email === "") {
    window.alert("email cannot be empty.");
  } else if (password === "") {
    window.alert("password cannot be empty.");
  } else {
    const credentials = email + ":" + password;
    const encodedCredentials = btoa(credentials);
    const basicAuthHeader = "Basic " + encodedCredentials;

    let accessToken = await fetch(BASE_URL + "/api/login", {
      method: "post",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        Authorization: basicAuthHeader,
      },
    })
      .then((res) => {
        if (res.status == 201) {
          loginForm.reset();
          return res.json();
        }
        throw new Error("email or password invalid");
      })
      .catch((err) => window.alert("email or password invalid"));
    console.log("access token", accessToken);
    window.localStorage.setItem("scm-access-token", accessToken["accessToken"]);
    window.location.assign("./index.html")
  }
}

async function googleAuthRedirect() {
  // console.log("google");
  // let response = await fetch(BASE_URL + "/oauth2/authorization/google", {
  //   method: "get",
  // }).then((res) => console.log(res));
  window.location.assign("http://localhost:8080/oauth2/authorization/google");
}

// EVENT LISTENER
document.getElementById("log-in-btn").addEventListener("click", login);
document
  .getElementById("google-btn")
  .addEventListener("click", googleAuthRedirect);