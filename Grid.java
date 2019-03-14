import java.util.*;

class Grid{
	char[][] config;

  // To create empty grid
  Grid(){
		this.config = new char[7][7];

		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 7; j++){
        this.config[i][j] = '-';
			}
		}
	}

	// Print grid's configuration
	static void showGrid(Grid grid){
		for(int i=0; i < 6; i++){
			for(int j=0; j < 7; j++){
				System.out.print(grid.config[i][j]);
			}
			System.out.println();
		}
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

}
