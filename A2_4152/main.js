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

function play(x,y){
    document.getElementById('p'+x+'_'+y).innerText=game.plays;
    document.getElementById('p'+x+'_'+y).disabled=true;
    game.cells[x][y]=game.plays;
    game.moves++;
    this.checkWinner(x,y);
    if(game.winner===false)
    setTurn();
}

function checkWinner(x,y){
    if(game.moves>=5 && (horizontalWin(x)==true || verticalWin(y)==true || diagonialWin()==true)){
        game.winner=true;
        disableButtons();
    }
    else if(game.moves===9){
        document.getElementById('message').innerHTML+="Draw";
    }
}

function horizontalWin(row){
    if(game.cells[row].filter(filterFunc).length===3){
        return true;
    }
    return false;
}

function filterFunc(value){
    return value===game.plays;
}

function verticalWin(col){
    const temp=[game.cells[0][col],game.cells[1][col],game.cells[2][col]];
    if(temp.filter(filterFunc).length===3){
        return true;
    }
    return false;
}

function filterFunc(value){
    return value===game.plays;
}

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

function disableButtons(){
    for(var i=0;i<3;i++){
        for(var j=0;j<3;j++){
            document.getElementById('p'+i+'_'+j).disabled=true;
        }
    }
}

function setTurn(){
    if(game.plays=='X')
        game.plays='O';
    else
        game.plays='X';
}