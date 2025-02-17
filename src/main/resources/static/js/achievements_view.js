document.getElementById("searchInput").addEventListener("keyup", function() {
    let filter = this.value.toLowerCase();
    let rows = document.querySelectorAll("#achievementsTable tr");
    rows.forEach(row => {
        let title = row.cells[0].textContent.toLowerCase();
        row.style.display = title.includes(filter) ? "" : "none";
    });
});

document.getElementById("statusFilter").addEventListener("change", function() {
    let filter = this.value;
    let rows = document.querySelectorAll("#achievementsTable tr");
    rows.forEach(row => {
        let status = row.getAttribute("data-status");
        row.style.display = (filter === "" || status === filter) ? "" : "none";
    });
});

let sortDirections = {}; // Store sorting state for each column

function sortTable(n) {
    document.querySelector(".table");
    let tbody = document.querySelector("#achievementsTable");
    let rows = Array.from(tbody.querySelectorAll("tr")).filter(row => row.style.display !== "none");

    // Determine sorting direction (toggle)
    sortDirections[n] = !sortDirections[n]; // Flip sorting order
    let ascending = sortDirections[n];

    rows.sort((a, b) => {
        let aValue = a.cells[n].textContent.trim();
        let bValue = b.cells[n].textContent.trim();

        // Convert date values to proper Date objects for sorting
        if (n === 2 || n === 3) {
            aValue = new Date(aValue);
            bValue = new Date(bValue);
        }

        return ascending ? aValue > bValue ? 1 : -1 : aValue < bValue ? 1 : -1;
    });

    // Append sorted rows back to the table
    rows.forEach(row => tbody.appendChild(row));
}