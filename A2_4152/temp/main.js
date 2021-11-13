const grid = [
    [{},{},{},{},{},{},{}],
    [{},{},{},{},{},{},{}],
    [{},{},{},{},{},{},{}],
    [{},{},{},{},{},{},{}],
    [{},{},{},{},{},{},{}],
    [{},{},{},{},{},{},{}]
]

const render =()=>{
    const svg = document.getElementById("svg");
    let doc = ``;
    for(var i=0; i < grid.length; i++){
        var row = grid[i];
        for (var j = 0; j < row.length; j++){
            doc = doc + `<circle fill='black' r='30px'
            x='${i *30}px' y=${i}px </circle>`;
        }
    }
    svg.innerHTML = doc;
};