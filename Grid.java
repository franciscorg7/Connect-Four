import java.util.*;


class Grid{
	char[][] config;
		FrontEnd visuals = new FrontEnd();

  // To create empty grid
  Grid(){
		this.config = new char[6][7];

		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 7; j++){
        this.config[i][j] = '-';
			}
		}
	}

	// To fill grid for debugging
	Grid(char fill){
		this.config = new char[6][7];

		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 7; j++){
				this.config[i][j] = fill;
			}
		}
	}

	// Print grid's configuration
	 void showGrid(Grid grid){
		System.out.println(" 0 1 2 3 4 5 6");
		for(int i=0; i < 6; i++){
			System.out.print(visuals.ANSI_CYAN + '|' + visuals.ANSI_RESET);
			for(int j=0; j < 7; j++){
				System.out.print(visuals.ANSI_YELLOW + grid.config[i][j] + visuals.ANSI_RESET);
				System.out.print(visuals.ANSI_CYAN + '|' + visuals.ANSI_RESET);
			}
			System.out.println();

		}
			System.out.println();
	}

	// Check if collumn is full
	boolean colFull(int col){

		if(this.config[0][col] != '-') return true;
		return false;
	}

	// Get first free place for piece in collumn required
	int freeLine(int col){

		for(int i=5; i >= 0; i--){
			if(this.config[i][col] == '-') return i;
		}
		return -1;
	}

	// Grid is full
	boolean gridFull(){
		for(int j=0; j < 7; j++){
			if(!colFull(j)) return false;
		}
		return true;
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
						return 0; // Flag for player win
					}
				}

				else if(this.config[i][j] == 'O'){
					oCounter++;
					xCounter = 0;
					if(oCounter == 4){
						return 1; // Flag for CPU win
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
						return 0; // Flag for Player win
					}
				}

				else if(this.config[i][j] == 'O'){
					oCounter++;
					xCounter = 0; // X's line must be contiguous
					if(oCounter == 4){
						return 1; // Flag for CPU win
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
					return 0; // Flag for Player win
				}

				if(this.config[i][j] == 'O' && this.config[i+1][j+1] == 'O' && this.config[i+2][j+2] == 'O' && this.config[i+3][j+3] == 'O'){
					return 1; // Flag for CPU win
				}
			}
		}

		// SECONDARY_DIAGONAL-SEARCHING
		for(int i=3; i < 6; i++){
			for(int j=0; j < 4; j++){

				if(this.config[i][j] == 'X' && this.config[i-1][j+1] == 'X' && this.config[i-2][j+2] == 'X' && this.config[i-3][j+3] == 'X'){
					return 0; // Flag for Player win
				}

				if(this.config[i][j] == 'O' && this.config[i-1][j+1] == 'O' && this.config[i-2][j+2] == 'O' && this.config[i-3][j+3] == 'O'){
					return 1; // Flag for CPU win
				}
			}

		}

		return -1; // Flag for no winner (DRAW)
	}
}
