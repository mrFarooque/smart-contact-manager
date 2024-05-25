// set theme
function setTheme() {
  let currentTheme = localStorage.getItem(SCM_THEME);
  if (currentTheme == null) localStorage.setItem(SCM_THEME, "light");
}
setTheme();

function toggle_theme() {
  console.log("dark mode");
  // get current theme
  const currentTheme = localStorage.getItem(SCM_THEME)
  // toggle
  if (currentTheme === 'light') {

  } else {
    
  }
}

document.getElementById("dark").addEventListener("click", toggle_theme);
