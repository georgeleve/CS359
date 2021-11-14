/* Georgios Gerasimos Leventopoulos csd4152  */
"use strict";
var grid = [];
var currentColor = 'red';
var activeGame = false;
var filledCircles = 0;
var redPlayerWins = 0;
var yellowPlayerWins = 0;
var totalDraws = 0;
var flag = 0;

// Bug: na ftiaxo thn kato kato grammh sto  vertically
// na anavosbhnoun ama kerdisei kapoios
// na xanado to updatePage ● Ποιος παίκτης έπαιξε (1 μονάδα)
// ● Πόση ώρα του πήρε (2 μονάδες)
// na kano to bonus

function updateInfobox() {
    document.getElementById("infobox").innerHTML =
    "Player Turn: " + currentColor + " player <br>" +
    "Total Moves: " + filledCircles + "<br>" +
    "Empty Cells: " + (42-filledCircles) + " player <br>" +
    " Red player wins: " + redPlayerWins + "<br>" +
    " Yellow player wins: " + yellowPlayerWins + "<br>" +
    " Draws: " + totalDraws;
}

function getTotalMoves(){
    return filledCircles;
}

function disableButtons(){
    activeGame = false;
}

function updateGoogleChartInfo(){
    google.charts.load("current", {packages:["corechart"]});
    google.charts.setOnLoadCallback(drawChart);
}

function isDraw(){
    if(filledCircles === 42){
        totalDraws++;
        return true;
    }
    return false;
}

function showWinner(color){
    document.getElementById("showWinner").innerHTML = color + " player is the winner! Press New Game to play again";
    updateInfobox();
    updateGoogleChartInfo();
    disableButtons();
}

function updateLabel(){
    if(flag !== 0){
        var playerThatJustPlayed = getPlayerTurn();
        var time = 0;
        if(playerThatJustPlayed === 'yellow'){
            playerThatJustPlayed = 'red';
        }else{
            playerThatJustPlayed = 'yellow';
        }
        document.getElementById("updatePageLabel").innerHTML = "Player that just played: " + playerThatJustPlayed +
         " player<br> Time it took: " + time + " seconds";
    }else{
        flag = 1;
    }
}

function updatePage() {
    let circles = "";
    const svg = document.getElementById("svg");
    for(var i = 0; i < grid.length; i++){
        var row = grid[i];
        for (var j = 0; j < row.length; j++){
            const circle = grid[i][j];
            const color = circle && circle.color || 'blanchedalmond';
            circles = circles + `<circle onclick="play(${j})" fill="${color}" r="29px" cx="${j * 70 + 60}px" cy="${i * 70 + 30}px"> </circle>`;
        }
    }
    svg.innerHTML = circles;
    updateLabel(); //show player that just played and how much it took
}

function changePlayerTurn(currentColor){
    if (currentColor === 'red')
        return 'yellow';
    return 'red';
}

function verticalWin(i, j, grid, circle){
    if (grid[i+1][j].color === circle.color && grid[i+2][j].color === circle.color && grid[i+3][j].color === circle.color){
        return true;
    }
    return false;
}

function horizontialWin(i, j, grid, circle){
    if(grid[i][j+1].color === circle.color && grid[i][j+2].color === circle.color && grid[i][j+3].color === circle.color){
        return true;
    }
    return false;
}

function diagonialWin(i, j, grid, circle){
    if(j === 0 || j === 1 || j === 2 || j === 3) {
        if(grid[i+1][j+1].color === circle.color && grid[i+2][j+2].color === circle.color && grid[i+3][j+3].color === circle.color){
            return true;
        }
    }
    if(j === 3 || j === 4 || j === 5 || j === 6) {
        if(grid[i+1][j-1].color === circle.color && grid[i+2][j-2].color === circle.color && grid[i+3][j-3].color === circle.color){
            return true;
        }
    }
    return false;
}

function updateStats(color){
    if (color === 'red'){
        redPlayerWins++;
    }else{
        yellowPlayerWins++;
    }
}

function hasPlayerWon(){
    if(isDraw()){
        updateInfobox();
        updateGoogleChartInfo();
        document.getElementById("showWinner").innerHTML = "Draw !!! Press New Game to play again";
        disableButtons();
        return;
    }
    for(var i=0; i < grid.length; i++){
        var row = grid[i];
        for (var j = 0; j < row.length; j++){
            var circle = grid[i][j];
            if(circle && circle.color){
                if(j === 0 || j === 1 || j === 2 || j === 3) {
                    if(horizontialWin(i, j, grid, circle)){
                        updateStats(circle.color);
                        showWinner(circle.color);
                        return;  
                    }
                }
                if(i===0 || i === 1){
                   if(verticalWin(i, j, grid, circle)){
                        updateStats(circle.color);
                        showWinner(circle.color);
                        return;
                   }
                   if(diagonialWin(i, j, grid, circle)){
                        updateStats(circle.color);
                        showWinner(circle.color);
                        return;
                   }
                }
            }
        }
    }
}

function getPlayerTurn(){
    return currentColor;
}

// Pick the first empty cell in the column "j"
function isValidMove(i,j){
    if(!grid[i][j].color){
        return true;
    }
    return false;
}

function play(j) {
    if(activeGame === false) return;
    
    for(var i = grid.length-1; i >= 0; i--){ //check valid move in the column "j"
        if(isValidMove(i,j)) {
            grid[i][j] = {color:currentColor};
            currentColor = changePlayerTurn(currentColor);
            updatePage();
            filledCircles++;
            hasPlayerWon();
            updateInfobox();
            return;
        }
    }
}

function drawChart() {
  var data = google.visualization.arrayToDataTable([
    ['Event', 'Times'],
    ['Draws', totalDraws],
    ['Red Player Wins', redPlayerWins],
    ['Yellow Player Wins', yellowPlayerWins],
  ]);

  var options = {
    title: 'Game Statistics',
    is3D: true,
  };

  var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));
  chart.draw(data, options);
}

function newGame(){
    if(activeGame === false){
        grid = [
            [{},{},{},{},{},{},{}],
            [{},{},{},{},{},{},{}],
            [{},{},{},{},{},{},{}],
            [{},{},{},{},{},{},{}],
            [{},{},{},{},{},{},{}],
            [{},{},{},{},{},{},{}]
        ];
        activeGame = true;
        filledCircles = 0;
        currentColor = 'red';
        document.getElementById("showWinner").innerHTML = "-";
        updateInfobox();
        updatePage();
    } else{
        document.getElementById("showWinner").innerHTML = "You can't press new game if the game is still active.";
    }
}