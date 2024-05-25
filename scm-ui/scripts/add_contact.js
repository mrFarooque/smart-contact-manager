document.getElementById("form").addEventListener("submit", async (event) => {
  event.preventDefault();
  let formData = new FormData(event.target);
  await fetch(BASE_URL + "/api/contacts", {
    method: "post",
    headers: {
      Accept: "*/*",
      Authorization: "Bearer " + localStorage.getItem(SCM_TOKEN_NAME),
    },
    body: formData,
  })
    .then((res) => {
      console.log("contact saved with status", res.status);
      if (res.status == 201) {
        event.target.reset();
        clearImagePreview();
        showSuccessAlert("Contact saved successfully!");
      }
    })
    .catch((error) => {
      console.error("Error saving contact:", error);
      alert(
        "An error occurred while saving the contact. Please try again later."
      );
    });
});

// Preview image
document.getElementById("image").addEventListener("change", function (event) {
  const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = function (e) {
      const preview = document.getElementById("image-preview");
      preview.src = e.target.result;
      preview.classList.remove("d-none");
      preview.style.display = "block";
    };
    reader.readAsDataURL(file);
  }
});

// clear image preview
function clearImagePreview() {
  const preview = document.getElementById("image-preview");
  preview.src = "";
  preview.classList.add("d-none");
}

function showSuccessAlert(message) {
  const alertDiv = document.createElement("div");
  alertDiv.className = "alert alert-success";
  alertDiv.setAttribute("role", "alert");
  alertDiv.textContent = message;
  document.getElementById("show-message").appendChild(alertDiv);
  setTimeout(() => {
    alertDiv.remove();
  }, 3000);
}
