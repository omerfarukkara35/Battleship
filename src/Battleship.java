import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Battleship {
	public static void main(String[] args) {

	System.out.println("**** Welcome to the Battle Ships game ****");
	System.out.println("");
	System.out.println("Right now, the sea is empty.");
	System.out.println("");
	Scanner input = new Scanner(System.in);
	String[][] oceanMap = createOceanMap();
	print(oceanMap);
	System.out.println("");
	int[][] userCoordinates = new int[5][2];
	createCoordinates(userCoordinates, oceanMap, "player");
	int[][] computerCoordinates = new int[5][2];

	System.out.println("");
	System.out.println("Computer is deploying ships...");
	createCoordinates(computerCoordinates, oceanMap, "computer");
	System.out.println("-------------------------------------");
	System.out.println("");
	print(oceanMap);

	//Battle System..
	int[] placedShipsCounter = {5, 5}; //[0] Player, [1] Computer, I did it in this way because I wanted to carry the healths across the methods.
	do {
		System.out.println(""); // space
		int xCord = 0, yCord = 0;
		System.out.println("--YOUR TURN--");
		//Ask the X coordinate until its valid.
		for (int i = 0; i <= 0; i++) {
			System.out.print("Enter X coordinate: ");
			xCord = input.nextInt();
			if (gridCheck(xCord)) {
				i--;
			}
		}
		//Ask the Y coordinate until its valid.
		for (int i = 0; i <= 0; i++) {
			System.out.print("Enter Y coordinate: ");
			yCord = input.nextInt();
			if (gridCheck(yCord)) {
				i--;
			}
		}
		hit(oceanMap, xCord, yCord, "player", placedShipsCounter);
		System.out.println("--COMPUTER'S TURN--");
		Random randomNumber = new Random();
		xCord = randomNumber.nextInt(9);
		yCord = randomNumber.nextInt(9);
		hit(oceanMap, xCord, yCord, "computer", placedShipsCounter);
		System.out.println("");
		print(oceanMap);
		scoreBoard(placedShipsCounter);
		if (placedShipsCounter[1] == 0) {
			System.out.println("");
			System.out.println("Hooray! You win the battle");
		} else if (placedShipsCounter[0] == 0) {
			System.out.println("");
			System.out.println("Next time buddy, you lost.");
		}



	} while (placedShipsCounter[0] != 0 && placedShipsCounter[1] != 0);

}

	public static void hit(String[][] oceanMap, int xCord, int yCord, String who, int[] healths) {
		//User
		for (int i = 0; i < oceanMap.length; i++) {
			for (int j = 0; j < oceanMap[0].length; j++) {
				if (who.equals("player") && oceanMap[i][j].equals("2") && i == xCord && j == yCord) {
					healths[1]--;
					oceanMap[i][j] = "!";
					System.out.println("Boom! You sunk the ship!");
				} else if (who.equals("player") && oceanMap[i][j].equals("1") && i == xCord && j == yCord) {
					healths[0]--;
					oceanMap[i][j] = "x";
					System.out.println("Oh no, you sunk your own ship :(");
				} else if (who.equals("player") && oceanMap[i][j].equals(" ") && i == xCord && j == yCord) {
					System.out.println("Sorry, you missed");
					oceanMap[i][j] = "-";
				}
			}
		}
		//Computer
		for (int i = 0; i < oceanMap.length; i++) {
			for (int j = 0; j < oceanMap[0].length; j++) {
				if (who.equals("computer") && oceanMap[i][j].equals("2") && i == xCord && j == yCord) {
					healths[0]--;
					oceanMap[i][j] = "x";
					System.out.println("The Computer sunk one of your ships!");
				} else if (who.equals("computer") && oceanMap[i][j].equals("1") && i == xCord && j == yCord) {
					healths[1]--;
					oceanMap[i][j] = "!";
					System.out.println("The Computer sunk one of its own ships");
				} else if (who.equals("computer") && oceanMap[i][j].equals(" ") && i == xCord && j == yCord) {
					System.out.println("Computer missed");
					oceanMap[i][j] = "-";
				}
			}
		}
	}

	//Creating an ocean map.
	public static String[][] createOceanMap() {
		String[][] oceanMap = new String[10][10];
		for (String[] row: oceanMap)
			Arrays.fill(row, " ");
		return oceanMap;
	}

	//Printing the ocean map by taking into cons. that user is 1, computer is 2.
	public static void print(String[][] theMap) {
		System.out.println("   0123456789");
		for (int i = 0; i < theMap.length; i++) {
			System.out.print(i + " |");
			for (int j = 0; j < theMap[0].length; j++) {
				if (theMap[i][j].equals("1")) {
					System.out.print("@");
				} else if (theMap[i][j].equals("2")) {
					System.out.print(" ");
				} else {
					System.out.print(theMap[i][j]);
				}
			}
			System.out.print("| " + i);
			System.out.println();
		}
		System.out.println("   0123456789");
	}
	//Check if the coordinates in the boundaries of the field.
	public static boolean gridCheck(int coordinateValue) {
		boolean flag = false;
		if (coordinateValue > 9 || coordinateValue < 0) {
			System.err.println("Invalid grid, please give your coordinate again.");
			flag = true;
		}
		return flag;
	}

	//Simple print method, just prints the of a 2d array.
	public static void printCoordinates(int[][] userCoordinates) {
		for (int i = 0; i < userCoordinates.length; i++) {
			for (int j = 0; j < userCoordinates[0].length; j++) {
				System.out.print(userCoordinates[i][j] + " ");
			}
			System.out.println("");
		}
	}

	//Checking if there is any identical coordinates.
	public static boolean checkDuplicate(int[][] userCoordinates, int xCoordinate, int yCoordinate) {
		int counter = 0;
		for (int i = 0; i < userCoordinates.length; i++) {
			for (int j = 0; j < userCoordinates[0].length; j++) {
				if (userCoordinates[i][j] == xCoordinate) {
					for (j = 0; j < userCoordinates[0].length; j++) {
						if (userCoordinates[i][j] == yCoordinate) {
							counter++;
						}
					}
				}
			}
		}
		return counter == 1;
	}

	//Create coordinates for both user and the computer, for computers its done automatically with randomized numbers.
	public static void createCoordinates(int[][] coordinates, String[][] oceanMap, String who) {
		int coordinateValueX = 0, coordinateValueY = 0;
		Random randomNumber = new Random();
		Scanner input = new Scanner(System.in);
		for (int i = 0; i < coordinates.length; i++) {
			// X - Value
			for (int j = 0; j <= 0; j++) {
				if (who.equals("player")) {
					System.out.print("Enter X coordinate for your " + (i + 1) + ". ship:");
					coordinateValueX = input.nextInt();
				} else {
					coordinateValueX = randomNumber.nextInt(9);
				}
				if (gridCheck(coordinateValueX)) {
					j--;
				}
			}

			// Y - Value
			for (int j = 1; j <= 1; j++) {
				if (who.equals("player")) {
					System.out.print("Enter Y coordinate for your " + (i + 1) + ". ship:");
					coordinateValueY = input.nextInt();
				} else {
					coordinateValueY = randomNumber.nextInt(9);
				}
				if (gridCheck(coordinateValueY)) {
					j--;
				}
			}

			if (checkDuplicate(coordinates, coordinateValueX, coordinateValueY) && who.equals("player")) {
				System.err.println("There is a ship placed, please give another coordinate.");
				i--;
				continue;
			} else if (checkDuplicate(coordinates, coordinateValueX, coordinateValueY) && who.equals("computer")) {
				i--;
				continue;
			}

			if (who.equals("player")) {
				coordinates[i][0] = coordinateValueX;
				coordinates[i][1] = coordinateValueY;
				oceanMap[coordinateValueX][coordinateValueY] = "1";
			} else {
				coordinates[i][0] = coordinateValueX;
				coordinates[i][1] = coordinateValueY;
				System.out.println((i + 1) + ". ship DEPLOYED.");
				oceanMap[coordinateValueX][coordinateValueY] = "2";
			}
		}
	}

	public static void scoreBoard(int[] healths) {

		System.out.println("");
		System.out.println("Your ships: " + healths[0] + " | " + "Computer's ships: " + healths[1]);
		System.out.println("-------------------------------------");

	}
}