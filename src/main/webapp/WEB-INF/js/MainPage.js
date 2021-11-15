function sortCards(row, sortMethod, asc) {
    const dirModifier = asc ? 1 : -1;

    const cards = Array.from(row.querySelectorAll('.col-md-3')).sort((a, b) => {
        const classForFind = "." + sortMethod;

        if (sortMethod === "price") {
            return parseInt(a.querySelector(classForFind).textContent.trim()) - parseInt(b.querySelector(classForFind).textContent.trim()) > 0 ? (1 * dirModifier) : (-1 * dirModifier);
        } else {
            return a.querySelector(classForFind).textContent.trim() > b.querySelector(classForFind).textContent.trim() ? (1 * dirModifier) : (-1 * dirModifier);
        }
    })


    while (row.firstChild) {
        row.removeChild(row.firstChild);
    }
    row.append(...cards);
}

function search() {
    let input, filter, products, name, i, txtValue;
    input = document.getElementById("search");
    filter = input.value.toUpperCase();
    products = document.getElementsByClassName("col-md-3");
    console.log(products);
    for (i = 0; i < products.length; i++) {
        name = products[i].getElementsByClassName("card-title")[0];
        if (name) {
            txtValue = name.textContent || name.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                products[i].style.display = "";
            } else {
                products[i].style.display = "none";
            }
        }
    }
}

document.querySelectorAll(".sortCards").forEach(dropDown => {
    dropDown.addEventListener("click", () => {
        sortCards(document.getElementsByClassName('row').item(0),
            dropDown.getAttribute("sortBy"),
            dropDown.classList.contains("asc"));
    });
});