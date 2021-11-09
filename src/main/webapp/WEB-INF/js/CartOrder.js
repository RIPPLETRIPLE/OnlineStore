function totalPrice() {
    let prices = document.getElementsByClassName('price');
    let sum = 0;

    for (let i = 0; i < prices.length; i++) {
        sum += parseInt(prices.item(i).innerHTML);
    }
    return sum.toString();
}
/**
 * @param {HTMLTableElement} table
 * @param {number} columnId
 * @param {boolean} asc
 */
function sortTableByColumn(table, columnId, asc = true) {
    columnId = columnId + 1;
    const selector = `th:nth-child(` + columnId + `)`;
    const selectorForSort = `td:nth-child(` + columnId + `)`;
    const dirModifier = asc ? 1 : -1;
    const tBody = table.tBodies[0];
    const rows = Array.from(tBody.querySelectorAll("tr"));

    const sortedRows = rows.sort((a, b) => {
        const aColText = a.querySelector(selectorForSort).textContent.trim();
        const bColText = b.querySelector(selectorForSort).textContent.trim();

        return aColText > bColText ? (1 * dirModifier) : (-1 * dirModifier);
    });

    while (tBody.firstChild) {
        tBody.removeChild(tBody.firstChild);
    }

    tBody.append(...sortedRows);

    console.log(columnId);
    console.log(asc);

    table.querySelectorAll("th").forEach((th) => {
        th.classList.remove("th-sort-asc");
        th.classList.remove("th-sort-desc")
    });

    if (asc) {
        console.log(table.querySelector(selector));
        table.querySelector(selector).classList.add("th-sort-asc");
    } else {
        table.querySelector(selector).classList.add("th-sort-desc");
    }
}

document.querySelectorAll(".table-sortable .columnToSort").forEach(headerCell => {
    headerCell.addEventListener("click", () => {
        const tableElement = headerCell.parentElement.parentElement.parentElement;
        const headerIndex = Array.prototype.indexOf.call(headerCell.parentElement.children, headerCell);
        const currentIsAscending = headerCell.classList.contains("th-sort-asc");

        sortTableByColumn(tableElement, headerIndex, !currentIsAscending);
    });
});

document.getElementById("totalPrice").innerText = document.getElementById("totalPrice").innerText.replace(': ', ': ' + totalPrice());