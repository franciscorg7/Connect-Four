import java.util.*;

class Grid{
	char[][] config;

  // To create empty grid
  Grid(){
		this.config = new char[7][7];

		for(int i = 0; i < 7; i++){
			for(int j = 0; j < 7; j++){
        this.config[i][j] = '-';
			}
		}
	}

	// Print grid's configuration
	static void showGrid(Grid grid){
		for(int i=0; i < 7; i++){
			for(int j=0; j < 7; j++){
				System.out.print(grid.config[i][j]);
			}
			System.out.println();
		}
	}

}
