import java.util.*;

class Player{

  char player1;
  char player2;

  // Constructor for player
  Player(int option){

    // X represents human, O represents CPU
    switch(option){
      case 1:
        player1 = 'X';
        player2 = 'O';
      break;

      case 2:
        player1 = 'O';
        player2 = 'X';
      break;
    }
  }
}
