import java.util.*;

class Play{

  Grid grid;
  int depth; // Node depth in search tree

  Play(Grid grid){
    this.grid = grid;
    this.depth = depth;
  }

  // Receive place to drop piece and make a play
  void makePlay(int col, int picker){
   if(col < 0 || col > 6){
     System.out.println("ERROR");
     return;
   }

   if(!grid.colFull(col)){
     if(even(picker)) this.grid.config[grid.freeLine(col)][col] = 'X';
     else this.grid.config[grid.freeLine(col)][col] = 'O';
   }
   else return;
  }



  // Picker system auxiliar
  boolean even(int a){
    if(a % 2 == 0) return true;
    return false;
  }
}
