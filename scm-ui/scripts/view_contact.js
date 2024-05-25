document.addEventListener("DOMContentLoaded", async () => {
  const params = new URLSearchParams(window.location.search);
  let id = params.get("id");
  let contact = await fetch(BASE_URL + "/api/contacts/" + id, {
    method: "get",
    headers: {
      Authorization: "Bearer " + localStorage.getItem(SCM_TOKEN_NAME),
    },
  })
    .then((res) => {
      return res.json();
    })
    .catch((error) => {
      console.error("Error saving contact:", error);
      alert(
        "An error occurred while saving the contact. Please try again later."
      );
    });
  updatePage(contact);
});

function updatePage(contact) {
  console.log("data", contact);
  if (contact.image) {
    document.getElementById("contact-image").src =
      "data:image/png;base64," + contact.image;
  } else {
    document.getElementById("contact-image").src = PIC_NOT_AVAILABLE;
  }

  document.getElementById("contact-name").innerText = contact.name;
  document.getElementById("contact-email").innerText = contact.email;
  document.getElementById("contact-phoneNumber").innerText =
    contact.phoneNumber;
  document.getElementById("contact-address").innerText = contact.address;
  document.getElementById("contact-description").innerText =
    contact.description;
  if (contact.favourite) {
    document.getElementById("contact-favourite").classList = "fas fa-star";
    ("one of your Favourite");
  } else {
    document.getElementById("contact-favourite").classList = "fas fa-xmark";
  }
}
