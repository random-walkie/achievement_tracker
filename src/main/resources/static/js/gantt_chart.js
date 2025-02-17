d3.json('/api/v1/achievements').then(data => {
    // Parse dates and prepare data for timeline
    data.forEach(d => {
        d.dateStarted = new Date(d.dateStarted);
        d.dateCompleted = new Date(d.dateCompleted);
    });

    // Define dimensions and scales
    const margin = {top: 20, right: 20, bottom: 30, left: 50};
    const width = document.getElementById('progressTimeline').clientWidth - margin.left - margin.right;  // Dynamically get the width
    const height = 200 - margin.top - margin.bottom;

    const xScale = d3.scaleTime()
        .domain([d3.min(data, d => d.dateStarted), d3.max(data, d => d.dateCompleted)])
        .range([0, width]);

    const svg = d3.select("#progressTimeline")
        .append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform", `translate(${margin.left}, ${margin.top})`);

    // Draw timeline bars for each achievement
    svg.selectAll("line")
        .data(data)
        .enter()
        .append("line")
        .attr("x1", d => xScale(d.dateStarted))
        .attr("x2", d => xScale(d.dateCompleted))
        .attr("y1", (d, i) => i * 30)  // Adjusted spacing for clarity
        .attr("y2", (d, i) => i * 30)
        .attr("stroke", "steelblue")
        .attr("stroke-width", 5);

    // Add task names (titles) next to each bar
    svg.selectAll("text")
        .data(data)
        .enter()
        .append("text")
        .attr("x", d => xScale(d.dateStarted) - 10)  // Position text slightly after the start of the bar
        .attr("y", (d, i) => i * 30 - 10)  // Position text a little above the bar for readability
        .attr("font-size", "12px")
        .attr("fill", "black")
        .text(d => d.title);  // Display task name (title of the achievement)

    // Add X axis
    svg.append("g")
        .attr("transform", `translate(0, ${height})`)
        .call(d3.axisBottom(xScale));
});
