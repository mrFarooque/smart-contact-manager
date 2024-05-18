// CONSTANTS
const BASE_URL = "http://localhost:8080";

document
  .getElementById("sidebar-dashboard-text")
  .addEventListener("click", () => (window.location.href = "./index.html"));

async function showDashboard() {
  async function getTotalNumberOfContacts() {
    let response = await fetch(BASE_URL + "/api/user/dashboard", {
      method: "get",
      headers: {
        accept: "application/json",
      },
      credentials: "include",
    }).then((res) => {
      if (res.status != 200) {
        console.log("not logged in");
        window.location.href = "./login.html";
      } else {
        return res.json();
      }
    });
    return response;
  }
  let dashboard = await getTotalNumberOfContacts();
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

async function logout() {
  let response = await fetch(BASE_URL + "/api/user/logout", {
    method: "get",
    credentials: "include",
  });
  window.location.href = "./index.html";
}

// redirect to home page
document.getElementById("");
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
