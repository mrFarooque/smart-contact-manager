// CONSTANTS
const BASE_URL = "http://localhost:8080";
const LOGIN_PAGE = "./login.html";
const HOME_PAGE = "./index.html";
const SCM_TOKEN_NAME = "scm-access-token";

document
  .getElementById("sidebar-dashboard-text")
  .addEventListener("click", () => (window.location.href = "./index.html"));

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
    if (authToken == null) window.location.assign(LOGIN_PAGE);
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
document.getElementById("dark").addEventListener("click", toggleDarkButton);
document
  .getElementById("toggleButton")
  .addEventListener("click", toggleDarkButton);

function toggleDarkButton() {
  console.log("toggle button");
  const htmlElement = document.querySelector("html");
  const currentTheme = htmlElement.getAttribute("data-bs-theme");
  if (currentTheme === "dark") {
    htmlElement.setAttribute("data-bs-theme", "light");
  } else {
    htmlElement.setAttribute("data-bs-theme", "dark");
  }
}

// LOGOUT functionality
function logout() {
  window.localStorage.removeItem(SCM_TOKEN_NAME);
  window.location.href = LOGIN_PAGE;
}

// redirect to home page
// redirect to add contact page
document
  .getElementById("add-contact")
  .addEventListener(
    "click",
    () => (window.location.href = "./add_contact.html")
  );
// redirect to view contact page
document
  .getElementById("view-contacts")
  .addEventListener(
    "click",
    () => (window.location.href = "./view_contacts.html")
  );
// redirect to favourites page
document
  .getElementById("favourite")
  .addEventListener(
    "click",
    () => (window.location.href = "./view_favourites.html")
  );
// logout btn
document.getElementById("logout-btn").addEventListener("click", logout);
