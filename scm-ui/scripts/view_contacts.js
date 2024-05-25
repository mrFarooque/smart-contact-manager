async function display_contacts() {
  let contacts = await fetch(BASE_URL + "/api/contacts", {
    method: "get",
    headers: {
      authorization: "Bearer " + localStorage.getItem(SCM_TOKEN_NAME),
    },
  }).then((res) => res.json());
  console.log(contacts);
  if (contacts) {
    const table = document.getElementById("tbody");
    table.innerHTML = "";
    contacts.contacts.forEach((ele, index) => {
      const newRow = table.insertRow();

      const profilePic = newRow.insertCell(0);
      const name = newRow.insertCell(1);
      const email = newRow.insertCell(2);
      const phone = newRow.insertCell(3);
      const actions = newRow.insertCell(4);

      const img = document.createElement("img");
      if (ele.image) {
        img.src = "data:image/png;base64," + ele.image;
      } else {
        img.src = PIC_NOT_AVAILABLE;
      }
      img.alt = name;
      img.width = 60;
      profilePic.appendChild(img);
      name.innerHTML = ele.name;
      name.className = "lead";
      email.innerHTML = ele.email;
      email.className = "lead";
      phone.innerHTML = ele.phoneNumber;
      phone.className = "lead";

      // Add action buttons
      const favouriteBtn = document.createElement("button");
      favouriteBtn.type = "button";
      favouriteBtn.className = "btn";
      if (ele.favourite) {
        favouriteBtn.innerHTML = '<i class="fas fa-star"></i>';
      } else {
        favouriteBtn.innerHTML = '<i class="far fa-star"></i>';
      }

      const editButton = document.createElement("button");
      editButton.type = "button";
      editButton.className = "btn";
      editButton.innerHTML = '<i class="far fa-edit"></i>';

      const deleteButton = document.createElement("button");
      deleteButton.type = "button";
      deleteButton.className = "btn";
      deleteButton.innerHTML = '<i class="far fa-trash-alt"></i>';

      actions.appendChild(favouriteBtn);
      actions.appendChild(editButton);
      actions.appendChild(deleteButton);

      favouriteBtn.addEventListener("click", async () => {
        const url = BASE_URL + "/api/contacts/" + ele.id + "/favourite";
        await fetch(url, {
          method: "put",
          headers: {
            Authorization: "Bearer " + localStorage.getItem(SCM_TOKEN_NAME),
          },
        }).then((res) => {
          if (res.status == 200) {
            display_contacts();
          }
        });
      });

      deleteButton.addEventListener("click", () =>
        console.log("del", index, ele.name)
      );
    });
  }
}

display_contacts();
