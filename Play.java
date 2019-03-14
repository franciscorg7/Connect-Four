import java.util.*;

class Play{

  Grid grid;

  Play(Grid grid){
    this.grid = grid;
  }

  // Receive place to drop piece and make a play
   void makePlay(int col){
    if(!grid.colFull(col)) this.grid.config[grid.freeLine(col)][col] = 'X';
    else return;
  }
}
