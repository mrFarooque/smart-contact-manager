async function updateSidebar() {
  console.log("update sidebar");
  let user = await fetch(BASE_URL + "/api/user/", {
    method: "get",
    headers: {
      authorization: "Bearer " + localStorage.getItem(SCM_TOKEN_NAME),
    },
  }).then((res) => res.json());
  console.log(user);
  let username = user["name"];
  document.getElementById("user-name").innerText = username;
  let userimageUrl = user["profilePic"];
  if (userimageUrl) {
    document.getElementById("user-profile-pic").src = userimageUrl;
  }
}

updateSidebar();
