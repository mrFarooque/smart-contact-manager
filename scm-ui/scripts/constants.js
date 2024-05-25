// CONSTANTS
const BASE_URL = "http://localhost:8080";
const LOGIN_PAGE = "./login.html";
const VIEW_CONTACTS_PAGE = "./view_contacts.html";
const HOME_PAGE = "./index.html";
const SCM_TOKEN_NAME = "scm-access-token";
const SCM_THEME = "scm-theme";
const PIC_NOT_AVAILABLE = "./images/profile-image-unavailable.svg";

// LOGOUT functionality
function logout() {
  window.localStorage.removeItem(SCM_TOKEN_NAME);
  window.localStorage.removeItem(SCM_THEME);
  window.location.href = LOGIN_PAGE;
}

// redirect to home page
document
  .getElementById("sidebar-dashboard-text")
  .addEventListener("click", () => (window.location.href = "./index.html"));
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
