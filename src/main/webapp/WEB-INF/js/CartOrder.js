function totalPrice() {
    let prices = document.getElementsByClassName('price');
    let sum = 0;

    for (let i = 0; i < prices.length; i++) {
        sum += parseInt(prices.item(i).innerHTML);
    }
    return sum.toString();
}

document.getElementById("totalPrice").innerText = document.getElementById("totalPrice").innerText.replace(': ', ': ' + totalPrice());