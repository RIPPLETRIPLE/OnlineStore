function sortCards(row, sortMethod, asc) {
    const dirModifier = asc ? 1 : -1;

    const cards = Array.from(row.querySelectorAll('.col-md-3')).sort((a, b) => {
        const classForFind = "." + sortMethod;
        console.log(classForFind);
        return a.querySelector(classForFind).textContent.trim() > b.querySelector(classForFind).textContent.trim() ? (1 * dirModifier) : (-1 * dirModifier);
    })


    while (row.firstChild) {
        row.removeChild(row.firstChild);
    }

    row.append(...cards);
}

document.querySelectorAll(".sortCards").forEach(dropDown => {
    dropDown.addEventListener("click", () => {
        sortCards(document.getElementsByClassName('row').item(0),
            dropDown.getAttribute("sortBy"),
            dropDown.classList.contains("asc"));
    });
});