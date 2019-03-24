import java.util.*;

public class Searches{

  // Closer to 7, harder to defeat CPU mind
  public int maxDepth = 5;

  public Play minimax(Play play, int depth, int currentPlayer){
    int currentValue;

    // Algorithm stops running when someone wins or search reaches maxDepth
    if(play.grid.winnerCheck() != 0 || depth == maxDepth){
      play.value = play.grid.utility(currentPlayer);
      return play;
    }

    else if(currentPlayer == 1){ // Flag for Player depth field
      currentValue = minfunction(play, depth, play.proxjogador(currentPlayer)).value;
      play.value = currentValue;
      return play;
    }

    else{ // Flag for CPU depth field
      currentValue = maxfunction(play, depth, play.proxjogador(currentPlayer)).value;
      play.value = currentValue;
      return play;
    }
  }

  private Play minfunction(Play play, int depth, int currentPlayer) {
    int currentValue = Integer.MAX_VALUE;
    int min = Integer.MAX_VALUE;
    int col = 0;

    play.getSons();

    for(Play w : play.sons){
      Play aux = new Play(w.grid, currentPlayer);

      aux.duplicatePlay(minimax(w, depth+1, currentPlayer));
      currentValue = Math.min(currentValue, aux.value);

      if(currentValue < min){
        min = currentValue;
        col = aux.col;
      }
    }

    play.value = currentValue;

    if(depth==0) play.col = col;

    return play;
  }

  private Play maxfunction(Play play, int depth, int currentPlayer){
    int currentValue = Integer.MIN_VALUE;
    int max = Integer.MIN_VALUE;
    int col = 0;

    play.getSons();

    for(Play w : play.sons){
      Play aux = new Play(w.grid, currentPlayer);
      aux.duplicatePlay(minimax(w, depth+1, currentPlayer));
      currentValue = Math.max(currentValue, aux.value);

      if(currentValue > max){
        max = currentValue;
        col = aux.col;
      }
    }

    play.value = currentValue;

    if(depth == 0) play.col = col;

    return play;
  }



  public Play alphaBeta(Play play, int depth, int currentPlayer, int alpha, int beta){ //Decisao
    int currentValue;
    if(play.grid.winnerCheck() != 0 || depth == maxDepth){
      play.value=play.grid.utility(currentPlayer);
      return play;
    }

    else if(currentPlayer == 0){ // Flag for CPU turn
      currentValue = alphaBetaMaxfunction(play, play.proxjogador(currentPlayer), depth, alpha, beta).value;
      play.value = currentValue;
      return play;
    }

    else{ // Flag for player turn
      currentValue = alphaBetaMinfunction(play, play.proxjogador(currentPlayer), depth, alpha, beta).value;
      play.value = currentValue;
      return play;
    }
  }

  private Play alphaBetaMaxfunction(Play play, int currentPlayer, int depth, int alpha, int beta){
    int currentValue = Integer.MIN_VALUE;
    int best = Integer.MIN_VALUE;
    int col = 0;
    play.beta=beta;
    play.getSons();

    for(Play w : play.sons){
      Play aux = new Play(w.grid,currentPlayer);
      aux.duplicatePlay(alphaBeta(w, depth+1, currentPlayer, play.alpha, play.beta));
      currentValue = Math.max(currentValue, aux.value);
      if(currentValue>best){
        best = currentValue;
        col = aux.col;
      }
      if(currentValue >= play.beta){
        play.value = currentValue;
        return play;
      }
      alpha = Math.max(alpha, currentValue);
    }

    play.value = currentValue;
    if(depth == 0) play.col = col;

    return play;
  }

  private Play alphaBetaMinfunction(Play play, int currentPlayer,int depth, int alpha,int beta) {
    int currentValue = Integer.MAX_VALUE;
    int best = Integer.MAX_VALUE;
    int col = 0;
    play.alpha=alpha;
    play.getSons();

    for(Play w : play.sons){
      Play aux = new Play(w.grid,currentPlayer);
      aux.duplicatePlay(alphaBeta(w,depth+1, currentPlayer,alpha,beta));
      currentValue = Math.min(currentValue, aux.value);

      if(currentValue < best){
        best = currentValue;
        col = aux.col;
      }

      if(currentValue <= play.alpha){
        play.value = currentValue;
        return play;
      }

      beta = Math.min(beta, currentValue);
    }

    play.value=currentValue;
    if(depth == 0) play.col = col;

    return play;
  }
}
