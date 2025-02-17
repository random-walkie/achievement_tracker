// Fetch achievement data from the API endpoint
d3.json('/api/v1/achievements').then(data => {
    // Count occurrences of each status
    const statusCount = {
        "TODO": 0,
        "IN_PROGRESS": 0,
        "COMPLETED": 0
    };

    data.forEach(d => {
        if (statusCount.hasOwnProperty(d.status)) {
            statusCount[d.status]++;
        }
    });

    // Prepare data for the bar chart
    const chartData = Object.keys(statusCount).map(status => ({
        status: status,
        count: statusCount[status]
    }));

    // Set up chart dimensions and margins
    const margin = { top: 20, right: 30, bottom: 40, left: 40 };
    const width = 800 - margin.left - margin.right;
    const height = 400 - margin.top - margin.bottom;

    // Create the SVG element
    const svg = d3.select("#statusDistributionChart")
        .append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform", `translate(${margin.left},${margin.top})`);

    // Define a color scale based on status
    const colorScale = d3.scaleOrdinal()
        .domain(["TODO", "IN_PROGRESS", "COMPLETED"])
        .range(["#FF5733", "#FFCD33", "#28A745"]);  // Red for TODO, Yellow for IN_PROGRESS, Green for COMPLETED

    // Set up scales
    const x = d3.scaleBand()
        .domain(chartData.map(d => d.status))
        .range([0, width])
        .padding(0.1);

    const y = d3.scaleLinear()
        .domain([0, d3.max(chartData, d => d.count)])
        .nice()
        .range([height, 0]);

    // Create X axis
    svg.append("g")
        .selectAll(".x-axis")
        .data(chartData)
        .enter()
        .append("text")
        .attr("class", "x-axis")
        .attr("x", d => x(d.status) + x.bandwidth() / 2)
        .attr("y", height + 25)
        .attr("text-anchor", "middle")
        .text(d => d.status);


    // Draw bars with color scale
    svg.selectAll(".bar")
        .data(chartData)
        .enter()
        .append("rect")
        .attr("class", "bar")
        .attr("x", d => x(d.status))
        .attr("y", d => y(d.count))
        .attr("width", x.bandwidth())
        .attr("height", d => height - y(d.count))
        .attr("fill", d => colorScale(d.status));  // Apply color based on status

    // Add labels to bars (with a flexible Y position to avoid overlap)
    svg.selectAll(".label")
        .data(chartData)
        .enter()
        .append("text")
        .attr("class", "label")
        .attr("x", d => x(d.status) + x.bandwidth() / 2)
        .attr("y", d => {
            // Adjust Y position to avoid overlap when counts are the same
            const offset = 5; // Adjust the offset here
            return y(d.count) - offset;
        })  // Position the label above the bar
        .attr("text-anchor", "middle")
        .text(d => d.count)
        .style("font-weight", "bold")
        .style("font-size", "14px");  // Increase font size to make it more visible
});