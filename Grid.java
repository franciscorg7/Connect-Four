import java.util.*;

public class Grid {

  FrontEnd visuals = new FrontEnd();

  public char config[][];

  // Empty grid constructor
  Grid(){
    config = new char[6][7];

    for(int i=0; i < 6; i++) {
      for(int j=0; j < 7; j++) {
        config[i][j] = '-';
      }
    }
  }

  // Check if collumn is full
	public boolean colFull(int col){
		if(this.config[0][col] != '-') return true;
		return false;
	}

  // To check if grid is already full (means Draw)
  public boolean gridFull(){
    for(int j=0; j < 7; j++){
      if(!colFull(j)) return false;
    }
    return true;
  }

  // To check first free space available in collumn required
  public int freeLine(int col){
    for(int i=5; i >= 0; i--){
      if(this.config[i][col] == '-') return i;
    }
    return -1; // Flag for full collumn
  }

  // Make a play and updates grid
  public int makePlay(int col, int nextPlayer){

    if((col < 0 || col > 6) || colFull(col)){
      System.out.println();
      System.out.print(visuals.ANSI_RED + "WARNING: " + visuals.ANSI_RESET + "Invalid play");
      return -1; // INVALID PLAY FLAG
    }

    switch(nextPlayer){

      case 0: this.config[freeLine(col)][col] = 'X';
      return 1;

      case 1: this.config[freeLine(col)][col] = 'O';
      return 1;
    }
    return 1;
  }

  // To print grid configuration
  public void showGrid(){
    System.out.println(visuals.ANSI_WHITE + " 0 1 2 3 4 5 6" + visuals.ANSI_RESET);
		for(int i=0; i < 6; i++){
		System.out.print(visuals.ANSI_CYAN + '|' + visuals.ANSI_RESET);
			for(int j=0; j < 7; j++){
        if(config[i][j] == '-') System.out.print(visuals.ANSI_WHITE + config[i][j]+ visuals.ANSI_RESET);
				else{
          if(config[i][j] == 'X') System.out.print(visuals.ANSI_YELLOW + config[i][j] + visuals.ANSI_RESET);
          if(config[i][j] == 'O') System.out.print(visuals.ANSI_RED + config[i][j] + visuals.ANSI_RESET);
        }
          System.out.print(visuals.ANSI_CYAN + '|' + visuals.ANSI_RESET);
			}
			System.out.println();
		}
  }

  // To check if there is any winner
	int winnerCheck(){

		int oCounter = 0;
		int xCounter = 0;

		// LINE-SEARCHING
		for(int i=0; i < 6; i++){
			for(int j=0; j < 7; j++){

				if(this.config[i][j] == 'X'){
					xCounter++;
					oCounter = 0;
					if(xCounter == 4){
						return 1; // Flag for player win
					}
				}

				else if(this.config[i][j] == 'O'){
					oCounter++;
					xCounter = 0;
					if(oCounter == 4){
						return -1; // Flag for CPU win
					}
				}

				// Lines must be contiguous
				else{
					oCounter = 0;
					xCounter = 0;
				}
			}

			// Those reset for each line (line-searching)
			oCounter = 0;
			xCounter = 0;
		}

		// COLLUMN-SEARCHING
		for(int j=0; j < 7; j++){
			for(int i=0; i < 6; i++){

				if(this.config[i][j] == 'X'){
					xCounter++;
					oCounter = 0; // O's line must be contiguous
					if(xCounter == 4){
						return 1; // Flag for Player win
					}
				}

				else if(this.config[i][j] == 'O'){
					oCounter++;
					xCounter = 0; // X's line must be contiguous
					if(oCounter == 4){
						return -1; // Flag for CPU win
					}
				}

				// Lines must be contiguous
				else{
					oCounter = 0;
					xCounter = 0;
				}
			}

			// Those reset for each collumn (collumn-searching)
			oCounter = 0;
			xCounter = 0;
		}

		// PRINCIPAL_DIAGONAL-SEARCHING
		for(int i=0; i < 3; i++){
			for(int j=0; j < 4; j++){

				if(this.config[i][j] == 'X' && this.config[i+1][j+1] == 'X' && this.config[i+2][j+2] == 'X' && this.config[i+3][j+3] == 'X'){
					return 1; // Flag for Player win
				}

				if(this.config[i][j] == 'O' && this.config[i+1][j+1] == 'O' && this.config[i+2][j+2] == 'O' && this.config[i+3][j+3] == 'O'){
					return -1; // Flag for CPU win
				}
			}
		}

		// SECONDARY_DIAGONAL-SEARCHING
		for(int i=3; i < 6; i++){
			for(int j=0; j < 4; j++){

				if(this.config[i][j] == 'X' && this.config[i-1][j+1] == 'X' && this.config[i-2][j+2] == 'X' && this.config[i-3][j+3] == 'X'){
					return 1; // Flag for Player win
				}

				if(this.config[i][j] == 'O' && this.config[i-1][j+1] == 'O' && this.config[i-2][j+2] == 'O' && this.config[i-3][j+3] == 'O'){
					return -1; // Flag for CPU win
				}
			}

		}

		return 0; // Flag for no winner
	}

  // Check if play is valid
  public boolean properPlay(int col){
    if((col < 0 || col > 6) || colFull(col)) return false;

    return true;
  }

  // Utility calculator
  public int utility(int player){
    return (linesUtility(player) + colUtility(player) + principalDiagUtility(player) + secondaryDiagUtility(player));
  }

  private int linesUtility(int currentPlayer){
    int xCounter = 0;
    int oCounter = 0;
    int linesSum = 0;

    for(int i=0; i < 6; i++){
      for(int j=0; j < 4; j++){

        if(config[i][j] == 'X') xCounter++;
        if(config[i][j+1] == 'X') xCounter++;
        if(config[i][j+2] == 'X') xCounter++;
        if(config[i][j+3] == 'X') xCounter++;

        if(config[i][j] == 'O') oCounter++;
        if(config[i][j+1] == 'O') oCounter++;
        if(config[i][j+2] == 'O') oCounter++;
        if(config[i][j+3] == 'O') oCounter++;

        linesSum += segmentEval(xCounter, oCounter);
        xCounter = 0; oCounter = 0; // Resets each line
      }
    }

    int finalSum;
    if(currentPlayer == 0) finalSum = linesSum + 16; // Rule for bonus
    else finalSum = linesSum - 16; // Rule for bonus

    return finalSum;
  }

  private int colUtility(int currentPlayer){

    int xCounter = 0;
    int oCounter = 0;
    int colsSum = 0;

    for(int i=0; i < 3; i++){
      for(int j=0; j < 7; j++){

        if(config[i][j] == 'X') xCounter++;
        if(config[i+1][j] == 'X') xCounter++;
        if(config[i+2][j] == 'X') xCounter++;
        if(config[i+3][j] == 'X') xCounter++;

        if(config[i][j] == 'O') oCounter++;
        if(config[i+1][j] == 'O') oCounter++;
        if(config[i+2][j] == 'O') oCounter++;
        if(config[i+3][j] == 'O') oCounter++;

        colsSum += segmentEval(xCounter, oCounter);
        xCounter = 0; oCounter = 0; // Resets each collumn
      }
    }

    int finalSum;
    if(currentPlayer == 0) finalSum = colsSum + 16; // Rule for bonus
    else finalSum = colsSum - 16; // Rule for bonus

    return finalSum;
  }

  private int principalDiagUtility(int currentPlayer){
    int xCounter = 0;
    int oCounter = 0;
    int diagonalSum = 0;

    for(int i=3; i < 6; i++){
      for(int j=0; j < 4; j++){

        if(config[i][j] == 'X') xCounter++;
        if(config[i-1][j+1] == 'X') xCounter++;
        if(config[i-2][j+2] == 'X') xCounter++;
        if(config[i-3][j+3] == 'X') xCounter++;

        if(config[i][j] == 'O') oCounter++;
        if(config[i-1][j+1] == 'O') oCounter++;
        if(config[i-2][j+2] == 'O') oCounter++;
        if(config[i-3][j+3] == 'O') oCounter++;

        diagonalSum += segmentEval(xCounter, oCounter);
        xCounter = 0; oCounter = 0;
      }
    }

    int finalSum;
    if(currentPlayer == 0) finalSum = diagonalSum + 16;
    else finalSum = diagonalSum - 16;

    return finalSum;
  }

  private int secondaryDiagUtility(int currentPlayer){
    int xCounter = 0;
    int oCounter = 0;
    int diagonalSum = 0;

    for(int i=3; i < 6; i++){
      for(int j=6; j > 2; j--){

        if(config[i][j] == 'X') xCounter++;
        if(config[i-1][j-1] == 'X') xCounter++;
        if(config[i-2][j-2] == 'X') xCounter++;
        if(config[i-3][j-3] == 'X') xCounter++;

        if(config[i][j] == 'O') oCounter++;
        if(config[i-1][j-1] == 'O') oCounter++;
        if(config[i-2][j-2] == 'O') oCounter++;
        if(config[i-3][j-3] == 'O') oCounter++;

        diagonalSum += segmentEval(xCounter, oCounter);
        xCounter = 0; oCounter = 0;
      }
    }

    int finalSum;
    if(currentPlayer == 0) finalSum = diagonalSum + 16;
    else finalSum = diagonalSum - 16;

    return finalSum;
  }

  private int segmentEval(int xCounter, int oCounter){

    // Segments with no O's
    if(xCounter == 1 && oCounter == 0) return 1;
    if(xCounter == 2 && oCounter == 0) return 10;
    if(xCounter == 3 && oCounter == 0) return 50;
    if(xCounter == 4 && oCounter == 0) return 512;

    // Segments with no X's
    if(xCounter == 0 && oCounter == 1) return -1;
    if(xCounter == 0 && oCounter == 2) return -10;
    if(xCounter == 0 && oCounter == 3) return -50;
    if(xCounter == 0 && oCounter == 4) return -512;

    // Empty or mixed segments
    else return 0;
  }

    // Working with auxiliar grid
    public void duplicateGrid(Grid x){
      for(int i = 0; i < x.config.length; i++){
        for(int j=0; j < x.config[i].length; j++){
          this.config[i][j] = x.config[i][j];
        }
      }

    }
  }
