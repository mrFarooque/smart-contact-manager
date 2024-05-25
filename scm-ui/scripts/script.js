async function showDashboard() {
  async function getTotalNumberOfContacts(authToken) {
    let response = await fetch(BASE_URL + "/api/user/dashboard", {
      method: "get",
      headers: {
        Accept: "application/json",
        Authorization: "Bearer " + authToken,
      },
    }).then((res) => {
      if (res.status != 200) {
        console.log("not logged in");
        window.location.assign(LOGIN_PAGE);
      } else {
        return res.json();
      }
    });
    return response;
  }
  // access token might come with query param
  let authToken = localStorage.getItem(SCM_TOKEN_NAME);
  if (authToken == null) {
    const currentUrl = window.location.href;
    const urlObject = new URL(currentUrl);
    authToken = urlObject.searchParams.get("token");
    console.log("auth token", authToken);
    if (authToken == null) window.location.assign(LOGIN_PAGE);
    if (authToken != null)
      window.localStorage.setItem(SCM_TOKEN_NAME, authToken);
  }
  let dashboard = await getTotalNumberOfContacts(authToken);
  document.getElementById("total-contact-number").textContent =
    dashboard["totalContacts"];
  document.getElementById("total-favourite-contact-number").textContent =
    dashboard["totalFavourites"];
}
showDashboard();

// toggle dark/light themes
// document.getElementById("dark").addEventListener("click", toggleDarkButton);
// document
//   .getElementById("toggleButton")
//   .addEventListener("click", toggleDarkButton);

// function toggleDarkButton() {
//   console.log("toggle button");
//   const htmlElement = document.querySelector("html");
//   const currentTheme = htmlElement.getAttribute("data-bs-theme");
//   if (currentTheme === "dark") {
//     htmlElement.setAttribute("data-bs-theme", "light");
//   } else {
//     htmlElement.setAttribute("data-bs-theme", "dark");
//   }
// }
