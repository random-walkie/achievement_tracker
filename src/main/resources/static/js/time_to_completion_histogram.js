d3.json('/api/v1/achievements').then(data => {
    // Parse the dates and calculate time to completion for each achievement
    let timeToCompletion = data.map(d => {
        let dateStarted = new Date(d.dateStarted);
        let dateCompleted = new Date(d.dateCompleted);
        let timeDiff = (dateCompleted - dateStarted) / (1000 * 60 * 60 * 24); // Difference in days
        return timeDiff;
    });

    // Set up dimensions and margins for the chart
    const margin = { top: 20, right: 20, bottom: 40, left: 40 },
        width = 800 - margin.left - margin.right,
        height = 400 - margin.top - margin.bottom;

    // Create SVG container for the chart
    const svg = d3.select("#timeToCompletionChart")
        .append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform", `translate(${margin.left},${margin.top})`);

    // Set up scales for the histogram
    const x = d3.scaleLinear()
        .domain([0, d3.max(timeToCompletion)])
        .range([0, width]);

    const bins = d3.histogram()
        .domain(x.domain())
        .thresholds(x.ticks(10))(timeToCompletion);

    const y = d3.scaleLinear()
        .domain([0, d3.max(bins, d => d.length)])
        .range([height, 0]);

    // Create the bars for the histogram
    svg.selectAll(".bar")
        .data(bins)
        .enter()
        .append("rect")
        .attr("class", "bar")
        .attr("x", d => x(d.x0))
        .attr("y", d => y(d.length))
        .attr("width", d => x(d.x1) - x(d.x0) - 1)
        .attr("height", d => height - y(d.length))
        .attr("fill", "#69b3a2");

    // Add x-axis
    svg.append("g")
        .attr("transform", `translate(0,${height})`)
        .call(d3.axisBottom(x).ticks(10));

    // Add labels for x-axis and y-axis
    svg.append("text")
        .attr("x", width / 2)
        .attr("y", height + margin.bottom - 5)
        .attr("text-anchor", "middle")
        .text("Time to Completion (Days)");

    svg.append("text")
        .attr("x", -height / 2)
        .attr("transform", "rotate(-90)")
        .attr("text-anchor", "middle");
}).catch(error => {
    console.error("Error fetching achievement data:", error);
});