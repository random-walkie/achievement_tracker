// Fetch achievement data from the API
fetch('/api/v1/achievements')
    .then(response => response.json())
    .then(data => {
        // Debugging log to check the fetched data
        console.log('Fetched achievements:', data);

        const tags = [];
        data.forEach(achievement => {
            // Check if tags exist and push to the array
            if (achievement.tags && achievement.tags.length > 0) {
                achievement.tags.forEach(tag => {
                    tags.push(tag);
                });
            }
        });

        // Debugging log to check the tags array
        console.log('Extracted tags:', tags);

        // If there are no tags, show "No tags found" message
        if (tags.length === 0) {
            document.getElementById("noTagsMessage").style.display = "block";
        } else {
            // Hide the "No tags found" message if tags are available
            document.getElementById("noTagsMessage").style.display = "none";

            // Process the tag data to create the tag cloud
            createTagCloud(tags);
        }
    })
    .catch(error => {
        console.error('Error fetching achievement data:', error);
    });

// Function to create the tag cloud using D3.js
function createTagCloud(tags) {
    const width = 600;
    const height = 400;

    // Create an SVG container to hold the tag cloud
    const svg = d3.select("#tagCloud")
        .append("svg")
        .attr("width", width)
        .attr("height", height)
        .append("g")
        .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

    // Count the frequency of each tag using d3.groups() for grouping
    const tagCounts = d3.groups(tags, d => d)
        .map(([key, value]) => ({ key, value: value.length }));

    // Debugging log to check the frequency data
    console.log('Tag counts:', tagCounts);

    // Prepare the data for word cloud layout
    const tagData = tagCounts.map(tag => ({
        text: tag.key,
        size: tag.value * 50 // Adjust the size factor as needed
    }));

    // Define the word cloud layout
    const layout = d3.layout.cloud()
        .size([width, height])
        .words(tagData)
        .padding(5)  // Padding between words
        .rotate(() => Math.random() > 0.5 ? 0 : 90)  // Random rotation of words
        .font("Impact")  // Font for the tags
        .fontSize(d => d.size)  // Size of the word based on frequency
        .on("end", draw);  // When the layout is done, call the draw function

    // Start the layout calculation
    layout.start();

    // Function to draw the words on the cloud
    function draw(words) {
        svg.selectAll("text")
            .data(words)
            .enter().append("text")
            .style("font-size", d => `${d.size}px`)
            .style("font-family", "Impact")
            .style("fill", (d, i) => d3.scaleOrdinal(d3.schemeCategory10)(i))  // Random colors from D3 color scheme
            .attr("text-anchor", "middle")
            .attr("transform", d => `translate(${d.x},${d.y})rotate(${d.rotate})`)
            .text(d => d.text);  // Add the text of each tag to the cloud
    }
}