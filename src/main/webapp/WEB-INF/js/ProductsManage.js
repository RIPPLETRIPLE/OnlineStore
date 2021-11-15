document.querySelectorAll(".updateUrl").forEach(url => {
    url.addEventListener("click", () => {
        updaterRow = url.parentElement.parentElement.nextElementSibling;
        if (updaterRow.getAttributeNames().indexOf("hidden") > -1) {
            updaterRow.removeAttribute("hidden");
        } else {
            updaterRow.setAttribute("hidden", "true");
        }
    })
})