import java.util.*;

public class Searches{

  // Closer to 7, harder to defeat CPU mind
  public int maxDepth = 5;

  // Exploration parameter - Auxiliar to MCTS algorithm
  public double expParam = Math.sqrt(2);

  // Random number for simulation process
  public Random random = new Random();

  public Play minimax(Play play, int depth, int currentPlayer){
    int currentValue;

    // Algorithm stops running when someone wins or search reaches maxDepth
    if(play.grid.winnerCheck() != 0 || depth == maxDepth){
      play.value = play.grid.utility(currentPlayer);
      return play;
    }

    else if(currentPlayer == 1){ // Flag for Player depth field
      currentValue = min(play, depth, play.nextPlayer(currentPlayer)).value;
      play.value = currentValue;
      return play;
    }

    else{ // Flag for CPU depth field
      currentValue = max(play, depth, play.nextPlayer(currentPlayer)).value;
      play.value = currentValue;
      return play;
    }
  }

  private Play min(Play play, int depth, int currentPlayer) {
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
        col = aux.col; // Saves optimal collumn that player can play
      }
    }

    play.value = currentValue;

    if(depth == 0) play.col = col;

    return play;
  }

  private Play max(Play play, int depth, int currentPlayer){
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
        col = aux.col; // Saves optimal collumn for CPU to play
      }
    }

    play.value = currentValue;

    if(depth == 0) play.col = col;

    return play;
  }


  // Adapted from MiniMax
  public Play alphaBeta(Play play, int depth, int currentPlayer, int alpha, int beta){ //Decisao
    int currentValue;

    // Algorithm stops running when someone wins or search reaches maxDepth
    if(play.grid.winnerCheck() != 0 || depth == maxDepth){
      play.value=play.grid.utility(currentPlayer);
      return play;
    }

    else if(currentPlayer == 0){ // Flag for CPU turn
      currentValue = auxBeta(play, play.nextPlayer(currentPlayer), depth, alpha, beta).value;
      play.value = currentValue;
      return play;
    }

    else{ // Flag for player turn
      currentValue = auxAlpha(play, play.nextPlayer(currentPlayer), depth, alpha, beta).value;
      play.value = currentValue;
      return play;
    }
  }

  private Play auxBeta(Play play, int currentPlayer, int depth, int alpha, int beta){
    int currentValue = Integer.MIN_VALUE;
    int best = Integer.MIN_VALUE;
    int col = 0;
    play.beta = beta;

    play.getSons();

    for(Play w : play.sons){
      Play aux = new Play(w.grid, currentPlayer);
      aux.duplicatePlay(alphaBeta(w, depth+1, currentPlayer, play.alpha, play.beta));
      currentValue = Math.max(currentValue, aux.value);

      if(currentValue > best){
        best = currentValue;
        col = aux.col;
      }

      // If current alpha >= beta then the other sons are rejected, because they won't be helpful
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

  private Play auxAlpha(Play play, int currentPlayer, int depth, int alpha, int beta) {
    int currentValue = Integer.MAX_VALUE;
    int best = Integer.MAX_VALUE;
    int col = 0;
    play.alpha = alpha;
    play.getSons();

    for(Play w : play.sons){
      Play aux = new Play(w.grid,currentPlayer);
      aux.duplicatePlay(alphaBeta(w, depth+1, currentPlayer,alpha,beta));
      currentValue = Math.min(currentValue, aux.value);

      if(currentValue < best){
        best = currentValue;
        col = aux.col;
      }

      // If current alpha >= beta then the other sons are rejected, because they won't be helpful
      if(currentValue <= play.alpha){
        play.value = currentValue;
        return play;
      }

      beta = Math.min(beta, currentValue);
    }

    play.value = currentValue;
    if(depth == 0) play.col = col;

    return play;
  }


  // MCTS is not working...

  public Play MCTS(Play play, int currentPlayer, int depth){

    Play cur = play;

    // SELECT
    while(depth < 4){
      cur = select(cur);
    }

    // EXPAND
    play.nextPlayer(currentPlayer);
    expand(cur, currentPlayer);

    // SIMULATION
    cur.getSons();
    if(cur.sons.size() > 0){
      cur = rollout(cur, currentPlayer);
    }
    return cur;
  }

  private Play select(Play play){
    Play selectedPlay = null;
    double bestValue = Integer.MIN_VALUE;

    for(Play w : play.sons){
      double value = w.UCTValue();
      if(value > bestValue){
        bestValue = value;
        selectedPlay = w;
      }
    }
    return selectedPlay;
  }

  private void expand(Play play, int currentPlayer){
      for(int i = 0; i < 7; i++){
        play.getSons();
        play.sons.get(0); // Expand through first child created
      }
  }

  private Play rollout(Play play, int currentPlayer){

    Play aux = play;

    while(play.grid.winnerCheck() == 0){
      int randomIndex = (int) (Math.random()*((6-0) + 1));
      play.getSons();
      aux = play.sons.get(randomIndex);
    }

    return aux;
  }

  // Auxiliar to ln()
  public static double ln(double d){
    return Math.log(d) / Math.log(2.0);
  }
}
