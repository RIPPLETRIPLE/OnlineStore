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
