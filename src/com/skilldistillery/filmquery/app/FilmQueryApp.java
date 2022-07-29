package com.skilldistillery.filmquery.app;

import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
//    app.test();
		app.launch();
	}

	private void test() {
		Film film = db.findFilmById(1);
		System.out.println(film);
	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		mainMenu(input);
	}

	private void mainMenu(Scanner input) {
		System.out.println("Welcome to the Film Finder App!");
		System.out.println("");
		System.out.println("  ___________________\n" + ")=|                 |     /\n" + "  |   Film Finder   |====||\n"
				+ "  |                 |====||\n" + "  |                 |+    \\\n" + "  -------------------\n"
				+ "         (--)\n" + "        *    *\n" + "       *      *\n" + "      *        *\n"
				+ "     *          *\n" + "    *            *");
		boolean keepGoing = true;
		while (keepGoing) {

			System.out.println("");
			System.out.println("Please select an option from the following menu:");
			System.out.println("");
			System.out.println("");
			System.out.println("1: Look up a film by its ID number");
			System.out.println("2: Look up a film by a search keyword");
			System.out.println("3: Exit Film Finder");
			int menuChoice = input.nextInt();
			input.nextLine();
			switch (menuChoice) {
			case 1:
				searchById(input);
				break;
			case 2:
				searchByKeyword(input);
				break;
			case 3:
				System.out.println("Thank you for using Film Finder!");
				System.out.println("Goodbye!");
				System.exit(0);
				break;
			default:
				System.out.println("Please select either 1, 2, or 3");
				break;
			}
		}
	}

	private void searchById(Scanner input) {

		boolean keepGoing = true;

		while (keepGoing) {
			System.out.println("");
			System.out.print("Please enter the film ID number: ");
			int menuChoice = input.nextInt();
			input.nextLine();
			try {
				Film film = db.findFilmById(menuChoice);
				System.out.println("");
				System.out.println(film.mostImportantInfo());
				
				keepGoing = subMenu(input, film);

			} catch (Exception e) {
				System.out.println("");
				System.out.println("Film not found.");
			}
		}
		
		idContinueMenu(input);

	}

	private void searchByKeyword(Scanner input) {

		boolean keepGoing = true;

		while (keepGoing) {
			System.out.println("Please enter a keyword search term");
			String keyword = input.next();
			input.nextLine();
			List<Film> films = db.findFilmByKeyword(keyword);
			try {
				if (films.size() > 0) {

					for (Film film : films) {

						System.out.println("");
						System.out.println(film.mostImportantInfo());
						
					}
					keepGoing = subMenu(input, films);
				} else {
					System.out.println("");
					System.out.println("Film not found.");
					System.out.println("");
				}

			} catch (Exception e) {
				System.out.println("");
				System.out.println("Film not found.");
				System.out.println("");
			}
		}
		keywordContinueMenu(input);
		
		}
	

	private void idContinueMenu(Scanner input) {
		System.out.println("");
		System.out.println("1. Make another search query");
		System.out.println("2. Return to main menu");
		int moreSearchInput = input.nextInt();
		switch (moreSearchInput) {
		case 1: searchById(input);
		break;
		case 2: mainMenu(input);
		break;
		default: System.out.println("Please select either 1 or 2");
		break; 
		}
	}
	private void keywordContinueMenu(Scanner input) {
		System.out.println("");
		System.out.println("1. Make another search query");
		System.out.println("2. Return to main menu");
		int moreSearchInput = input.nextInt();
		switch (moreSearchInput) {
		case 1: searchByKeyword(input);
		break;
		case 2: mainMenu(input);
		break;
		default: System.out.println("Please select either 1 or 2");
		break; 
		}
	}

	private boolean subMenu(Scanner input, Film film) {
		boolean stayInSubMenu = true;
		while (stayInSubMenu ) {
			
			System.out.println("");
			System.out.println("1: View all film details");
			System.out.println("2: Return to main menu");
			int subMenuInput = input.nextInt();
			input.nextLine();
			
			switch (subMenuInput) {
			case 1: 
				stayInSubMenu = false;
				System.out.println(film.toString());
			break;
			case 2: 
					stayInSubMenu = false;
					mainMenu(input);
			break;
			default: System.out.println("Please select either 1 or 2");
			break;
			}
			
		}
		
		return stayInSubMenu;
	}
	private boolean subMenu(Scanner input, List<Film> films) {
		boolean stayInSubMenu = true;
		while (stayInSubMenu ) {
			
			System.out.println("");
			System.out.println("1: View all film details");
			System.out.println("2: Return to main menu");
			int subMenuInput = input.nextInt();
			input.nextLine();
			
			switch (subMenuInput) {
			case 1: 
				stayInSubMenu = false;
				for (Film film : films) {
					System.out.println("");					
					System.out.println(film.toString());
				}
				break;
			case 2: 
				stayInSubMenu = false;
				mainMenu(input);
				break;
			default: System.out.println("Please select either 1 or 2");
			break;
			}
			
		}
		
		return stayInSubMenu;
	}
	
	
	
}
