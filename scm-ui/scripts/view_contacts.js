// redirections
document
  .getElementById("sidebar-dashboard-text")
  .addEventListener("click", showDashboard);

function showDashboard() {
  window.location.href = "./index.html";
}
