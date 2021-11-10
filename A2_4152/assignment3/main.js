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
    var turn = "player1";
    //
    //initialize infobox, variables etc
}

//Event handler when someone presses a button
function play(x,y){
    document.getElementById('p'+x+'_'+y).innerText=game.plays;
    document.getElementById('p'+x+'_'+y).disabled=true;
    game.cells[x][y]=game.plays;
    game.moves++;
    this.checkWinner(x,y);
    if(game.winner===false)
    setTurn();
}

function getPlayerTurn(){
    if(game.plays=='X')
        game.plays='O';
    else
        game.plays='X';
}

//checks if this move is valid
function isValidMove(){
    return true;


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

//check for vertical win
function verticalWin(col){
    const temp=[game.cells[0][col],game.cells[1][col],game.cells[2][col]];
    if(temp.filter(filterFunc).length===3){
        return true;
    }
    return false;
}

//check for horizontal win
function horizontalWin(row){
    if(game.cells[row].filter(filterFunc).length===3){
        return true;
    }
    return false;
}

//check for diagonal win
function diagonialWin(){
    if(game.cells[0][0]==game.cells[1][1] && game.cells[1][1]==game.cells[2][2] && game.cells[2][2]==game.plays){
        return true;
    }
    else if(game.cells[0][2]==game.cells[1][1] && game.cells[1][1]==game.cells[2][0] &&
        game.cells[2][0]==game.plays){
        return true;
    }
    return false;
}

// Checks if the game finished with draw
function isDraw(){
    //checks if all of the deck is full


}

function updatePage(){

}

function changePlayerTurn(){


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