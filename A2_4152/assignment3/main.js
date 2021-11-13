const game = {
    plays: "X",
    moves: 0,
    cells: [],
    winner: false
};

game.cells[0]=[null,null,null,null,null,null,null,null];
game.cells[1]=[null,null,null,null,null,null,null,null];
game.cells[2]=[null,null,null,null,null,null,null,null];
game.cells[3]=[null,null,null,null,null,null,null,null];
game.cells[4]=[null,null,null,null,null,null,null,null];
game.cells[5]=[null,null,null,null,null,null,null,null];

function newGame(){
    document.getElementById("message").innerHTML += 
    "Infobox Player: Total Moves: Empty Cells: Draws: Red Player Wins: Yellow Player Wins: <br>";
    //initialize infobox, variables etc
}

//Event handler when someone presses a button
function play(x,y){
    document.getElementById('p'+x+'_'+y).innerText = game.plays;
    document.getElementById('p'+x+'_'+y).disabled = true;
    document.getElementById('p'+x+'_'+y).innerHTML = "<img src='redPawn.png'/>";
    game.cells[x][y] = game.plays;
    game.moves++;
    //this.checkWinner(x,y);
    if(game.winner===false){
        changePlayerTurn();
    }
}

function changePlayerTurn(){
    if(game.plays=='X')
        game.plays='O';
    else
        game.plays='X';
}

//checks if this move is valid
function isValidMove(){
    return true;
    return false;
}

function hasPlayerWon(x,y){
    if(game.moves>=5 && (horizontalWin(x)==true || verticalWin(y)==true || diagonialWin()==true)){
        game.winner=true;
        disableButtons();
    }
    else if(game.moves===9){
        document.getElementById('message').innerHTML+="Draw";
    }
}

function verticalWin(col){
    const temp=[game.cells[0][col],game.cells[1][col],game.cells[2][col],game.cells[3][col]];
    if(temp.filter(filterFunc).length===3){
        return true;
    }
    return false;
}

function horizontalWin(row){
    if(game.cells[row].filter(filterFunc).length===4){
        return true;
    }
    return false;
}

function diagonialWin(){
    if(game.cells[0][0]==game.cells[1][1] && game.cells[1][1]==game.cells[2][2] && game.cells[2][2]==game.plays){
        return true;
    }
    else if(game.cells[0][2]==game.cells[1][1] && game.cells[1][1]==game.cells[2][0] && game.cells[2][0]==game.plays){
        return true;
    }
    return false;
}

function isDraw(){
    //checks if all of the deck is full


}

//may also use jquery
function updatePage(){
    //updates the deck
    //if the move is valid write to the infobox:
        //who played
        //how much time it took using date  var start = new Date().getTime();
        
        var start = new Date().getTime();
        for (i = 0; i < 50000; ++i) {
        // do something
        }
        var end = new Date().getTime();
        alert('Execution time: ' + (end-start));
////////////////////////////
}

function showWinningMessageAndBlink(){

}

function disableButtons(){
    for(var i=0;i<3;i++){
        for(var j=0;j<3;j++){
            document.getElementById('p'+i+'_'+j).disabled=true;
        }
    }
}

function filterFunc(value){
    return value===game.plays;
}


// g) bonus

function newGameAnytime(){


}

function calculatePlayerWins(player){

}

function functionShowGoogleCharts(){
    yellow_player_wins = 0;
    yellow_player_wins = 0;
}