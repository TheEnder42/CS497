//Matt Ritchie
//Feeder
//10-23-16
//for reference, use chrome.  https://docs.oracle.com/javase/7/docs/api/
//This code is the property of Matt Ritchie, any use without permission is stealing and will be perused.

/*import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;*/
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Matt Ritchie
 * @see ExternalSource
 * @see Meals
 */
public class FeederDriver {
	private static boolean quit=false;
	private static Random possMeal = new Random();
	private static Scanner reader = new Scanner(System.in);
	private static int[] homeCook = new int[6];
	private static int[] homeCookPast = new int[6];
	private static int[] eatOut = new int[2];
	private static int[] eatOutPast = new int[2];
	private static  ArrayList<Meals> homemade = new ArrayList<Meals>();
	private static  ArrayList<ExternalSource> outsourced = new ArrayList<ExternalSource>();
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		reload();
		while(!quit){
			menu();
		}//end menu loop
		save();
	}//end main
	
	public static void save() throws IOException{
		try {
	        /*FileOutputStream outputStream = new FileOutputStream("src//FeederData.txt");
	        ObjectOutputStream output = new ObjectOutputStream(outputStream);
	     
	        output.writeObject(homeCook);
	        output.writeObject(homeCookPast);
	        output.writeObject(eatOut);
	        output.writeObject(eatOutPast);
	        output.writeObject(homemade);            //error
	        output.writeObject(outsourced);
	        
	        output.close();*/
			
			FileWriter writer = new FileWriter("src//memory.txt");
			BufferedWriter bwriter = new BufferedWriter(writer);
			PrintWriter pwriter = new PrintWriter(bwriter);
			
			for(int i=0; i<homeCook.length; i++){
				pwriter.println(homeCook[i]);
			}
			for(int i=0; i<homeCookPast.length; i++){
				pwriter.println(homeCookPast[i]);
			}
			for(int i=0; i<eatOut.length; i++){
				pwriter.println(eatOut[i]);
			}
			for(int i=0; i<eatOutPast.length; i++){
				pwriter.println(eatOutPast[i]);
			}
			pwriter.println(homemade.size());
			for(int i=0; i<homemade.size(); i++){
				pwriter.println(homemade.get(i).getName());
				pwriter.println(homemade.get(i).getNote());
				pwriter.println(homemade.get(i).getIngredients().size());
				for(int k=0; k<homemade.get(i).getIngredients().size(); k++){
					pwriter.println(homemade.get(i).getIngredients().get(k).getName());
					pwriter.println(homemade.get(i).getIngredients().get(k).getQuant());
					pwriter.println(homemade.get(i).getIngredients().get(k).getType());
				}
			}
			pwriter.println(outsourced.size());
			for(int i=0; i<outsourced.size(); i++){
				pwriter.println(outsourced.get(i).getName());
				pwriter.println(outsourced.get(i).getNote());
			}
			pwriter.close();
	      } // end try
	      catch(FileNotFoundException | SecurityException e) {
	        System.out.println("Cannot write to file.");
	      } // end catch
	}//end save memory
	
	@SuppressWarnings("unchecked")
	public static void reload() throws ClassNotFoundException, IOException{
		try {
			//".//filename"
			//
		      /*FileInputStream inputStream = new FileInputStream("src//FeederData.txt");
		      ObjectInputStream input = new ObjectInputStream(inputStream);		      
		      homeCook =  (int[]) input.readObject();
		      homeCookPast =  (int[]) input.readObject();
		      eatOut =  (int[]) input.readObject();
		      eatOutPast =  (int[]) input.readObject();
		      homemade =  (ArrayList<Meals>) input.readObject();
		      outsourced =  (ArrayList<ExternalSource>) input.readObject();     //error
		      input.close();*/
		      
			FileReader fReader = new FileReader("src//memory.txt");
			BufferedReader bReader = new BufferedReader(fReader);
			
			for(int i=0; i<homeCook.length; i++){
				homeCook[i]=Integer.parseInt(bReader.readLine());
			}
			for(int i=0; i<homeCookPast.length; i++){
				homeCookPast[i]=Integer.parseInt(bReader.readLine());
			}
			for(int i=0; i<eatOut.length; i++){
				eatOut[i]=Integer.parseInt(bReader.readLine());
			}
			for(int i=0; i<eatOutPast.length; i++){
				eatOutPast[i]=Integer.parseInt(bReader.readLine());
			}
			int runs = Integer.parseInt(bReader.readLine());
			for(int i=0; i<runs; i++){
				Meals temp = new Meals(" ");
				temp.setName(bReader.readLine());
				//System.out.println("A:" + temp.getName());
				temp.setNote(bReader.readLine());
				//System.out.println("B:" + temp.getNote());
				int runs2 = Integer.parseInt(bReader.readLine());
				//System.out.println("C:" + runs);
				for(int k=0; k<runs2; k++){
					Parts tempPart = new Parts();
					tempPart.setName(bReader.readLine());
					tempPart.setQuant(Double.parseDouble(bReader.readLine()));
					tempPart.setType(Integer.parseInt(bReader.readLine()));
					temp.getIngredients().add(tempPart);
				}
				homemade.add(temp);
			}
			runs = Integer.parseInt(bReader.readLine());
			for(int i=0; i<runs; i++){
				ExternalSource temp = new ExternalSource(" ");
				temp.setName(bReader.readLine());
				temp.setNote(bReader.readLine());
				outsourced.add(temp);
			}
			bReader.close();
		}//end try
		catch (FileNotFoundException e) {
		      System.out.println("No existing Feeder data file found" + "\n" + "\n" + "\n");
		      System.out.println("Welcome to Feeder!" + "\n" 
		    		  + "(I apologize my creator is bad at naming things.)" + "\n" 
		    		  + "This program is designed to store your recipes and plan" + "\n" 
		    		  + "the week's meals so you don't have to." + "\n" 
		    		  + "Please note that 8 meals being planned is not a mistake," + "\n" 
		    		  + "it is so that you can pick and choose which meals to eat, and provides" + "\n" 
		    		  + "an alternative in case something unexpected happens during your week." + "\n" 
		    		  + "(Based on cooking at home 6 days/week. Any week's meals will not include" + "\n" 
		    		  + "meals from the previous week. Program does not limit randomized meal" + "\n" 
		    		  + "planning to once per week. Capitalization matters in ingredients' names.)");
		      System.out.println("\n" + "\n");
		}//end catch
	}//end 
	
	/**
	 * Prints the menu, reads in the user's selection, and calls the corresponding method
	 */
	public static void menu(){
		int temp = ReadInput.range(0, 1, 10, "Choose an action by entering number of option:" + "\n" + "\n"
				+ "1: Add a new meal" + "\n" + "2: Remove a meal" + "\n" 
				+ "3: Add an ingredient to a meal" + "\n" + "4: Remove an ingredient from a meal" + "\n" 
				+ "5: Edit notes on a meal" + "\n" + "6: Plan the week's meals" + "\n" 
				+ "7: Show this week's meals" + "\n" + "8: Print shopping list" + "\n" + "9: Display Archives" 
				+ "\n" + "10: Quit");
		//String menuNum=reader.nextLine();
		if(temp==1){
			System.out.println("");
			addMeal();
			System.out.println("\n");
		}//end new item
		else if(temp==2){
			System.out.println("");
			removeMeal();
			System.out.println("\n");
		}//end display
		else if(temp==3){
			System.out.println("");
			addIngredChoice();
			System.out.println("\n");
		}//end new prior
		else if(temp==4){
			System.out.println("");
			removeIngred();
			System.out.println("\n");
		}//end rename
		else if(temp==5){
			System.out.println("");
			editNotes();
			System.out.println("\n");
		}//end (un)complete
		else if(temp==6){
			plan();
			System.out.println("\n");
		}//end new day
		else if(temp==7){
			System.out.println("");
			displayPlannedMeals();
			System.out.println("\n");
		}//end delete
		else if(temp==8){
			System.out.println("");
			displayIngredList();
			System.out.println("\n");
		}//end poof
		else if(temp==9){
			System.out.println("");
			displayArchives();
			System.out.println("\n");
		}//end poof
		else if(temp==10){
			quit=true;
			System.out.println("Have a nice day!");
		}//end quit
		else{
			System.out.println("Something has gone very wrong. Please email Matt Ritchie (the author) at mcritchie97@gmail.com for help.");
		}//end error
	}//end menu
	
	public static void displayIngredList(){
		ArrayList<Parts> used = new ArrayList<Parts>();
		boolean newIngred = true;
		String ingredName;
		String typnm="";
		for(int i=0; i< homeCook.length; i++){
			//System.out.println("current meals: " + i);
			for(int k=0; k<homemade.get(homeCook[i]).getIngredients().size(); k++){
				//System.out.println("meal " + i + " ingred: " + k);
				ingredName=homemade.get(homeCook[i]).getIngredients().get(k).getName();
				//System.out.println("ingred name: " + ingredName);
				//System.out.println("type int " + homemade.get(homeCook[i]).getIngredients().get(k).getType());
				
				int type = homemade.get(homeCook[i]).getIngredients().get(k).getType();
				switch(type){
					case 0:
						typnm = "";
						break;
					case 1:
						typnm = " cups";
						break;
					case 2:
						typnm = " packs of";
						break;
					case 3:
						typnm = " teaspoons";
						break;
					case 4:
						typnm = " tablespoons";
						break;
					case 5:
						typnm = "lbs";
						break;
				}//end typnm case selection
				//System.out.println("type name: " + typnm);
//				for(int x=0; x<used.size(); x++){
//					System.out.println("used ingred: " + x);
//					if(ingredName.equals(used.get(x).getName())){
//						used.get(x).setQuant(used.get(x).getQuant() + homemade.get(homeCook[i]).getIngredients().get(k).getQuant());
//						newIngred=false;
//						System.out.println("i exist");
//					}//end if ingred exists in used
//				}//end check used for copies
				
				for(Parts part :used){
					if(ingredName.equals(part.getName())){
						part.setQuant(part.getQuant() + homemade.get(homeCook[i]).getIngredients().get(k).getQuant());
						newIngred=false;
						//System.out.println("i exist");
					}//end if ingred exists in used
				}//end check used for copies
				
				if(newIngred){
					Parts temp = new Parts(ingredName, homemade.get(homeCook[i]).getIngredients().get(k).getQuant(), homemade.get(homeCook[i]).getIngredients().get(k).getType());
					//System.out.println(ingredName + " " + homemade.get(homeCook[i]).getIngredients().get(k).getQuant() + " " + homemade.get(homeCook[i]).getIngredients().get(k).getType());
					used.add(temp);
					//System.out.println("now i do");
				}//end if no copies make new
				newIngred=true;
				/*for(int x=0; x< homeCook.length; x++){
					for(int y=0; k<homemade.get(x).getIngredients().size(); y++){
						if(ingredName.equals(homemade.get(x).getIngredients().get(y).getName()) && isNotUsed(used, ingredName)){
							//temp += homemade.get(x).getIngredients().get(y).getQuant();
						}//end comparison
					}//end compare ingreds loop
				}//end meals to compare incrementation*/
				//System.out.println(temp + typnm + " " + ingredName + "\n");
			}//end meal's ingred list
		}//end week's meals loop
		//System.out.println("am i still alive?");
		for(int z=0; z<used.size(); z++){
			switch(used.get(z).getType()){
			case 0:
				typnm = "";
				break;
			case 1:
				typnm = " cups";
				break;
			case 2:
				typnm = " packs of";
				break;
			case 3:
				typnm = " teaspoons";
				break;
			case 4:
				typnm = " tablespoons";
				break;
			case 5:
				typnm = "lbs";
				break;
		}//end typnm case selection
			System.out.println(used.get(z).getQuant() + typnm + " " + used.get(z).getName() /*+ "\n"*/);
		}
	}//end print shopping list
		
	public static boolean isNotUsed(ArrayList<Parts> used, String name){
		for(int i=0; i<used.size(); i++){
			if(name.equals(used.get(i).getName())){
				return false;
			}//end check
		}//end loop
		return true;
	}//end isNotUsed
	
	/*public static void plan(){
		int i = 0, tempH, tempO;
		for(int k=0; k<homeCook.length; k++){
			homeCookPast[k] = homeCook[k];
		}//end shift planed meals to past
		for(int k=0; k<eatOut.length; k++){
			eatOutPast[k] = eatOut[k];
		}//end shift planed restaurants to past
		while(i<homeCook.length){
			tempH = possMeal.nextInt(homemade.size());
			if(isUnused(homeCookPast, tempH)){
				homeCook[i]=tempH;
				i++;
			}//end if ok
		}//end homemade loop
		i = 0;
		while(i<eatOut.length){
			tempO = possMeal.nextInt(outsourced.size());
			if(isUnused(eatOutPast, tempO)){
				eatOut[i]=tempO;
				i++;
			}//end if ok
		}//end restaurant loop
		displayPlannedMeals();
	}//end plan*/
	
	public static void plan(){
		int tempH, tempO;
		for(int k=0; k<homeCook.length; k++){
			homeCookPast[k] = homeCook[k];
		}//end shift planed meals to past
		for(int k=0; k<eatOut.length; k++){
			eatOutPast[k] = eatOut[k];
		}//end shift planed restaurants to past
		
		TreeSet<Integer> thisweekc = new TreeSet<Integer>();
		TreeSet<Integer> thisweekcp = new TreeSet<Integer>();
		for(int k=0; k<homeCookPast.length; k++){
			thisweekcp.add(homeCookPast[k]);
		}//end shift past meals to past set
		while(thisweekc.size()<6){
			tempH = possMeal.nextInt(homemade.size());
			//System.out.println(tempH);
			if(!thisweekcp.contains(tempH)){
				thisweekc.add(tempH);
			}//end if not past
		}//end while loop homecook
		for(int k=0; k<homeCook.length; k++){
			homeCook[k]=thisweekc.first();
			thisweekc.remove(thisweekc.first());
		}//end shift current set back to current meals
		
		TreeSet<Integer> thisweekr = new TreeSet<Integer>();
		TreeSet<Integer> thisweekrp = new TreeSet<Integer>();
		for(int k=0; k<eatOutPast.length; k++){
			thisweekrp.add(eatOutPast[k]);
		}//end shift past restaurants to past set
		while(thisweekr.size()<2){
			tempO = possMeal.nextInt(outsourced.size());
			//System.out.println(tempO);
			if(!thisweekrp.contains(tempO)){
				thisweekr.add(tempO);
			}//end if not past
		}//end while loop restaurant
		for(int k=0; k<eatOut.length; k++){
			eatOut[k]=thisweekr.first();
			thisweekr.remove(thisweekr.first());
		}//end shift current set back to current restaurant
		
		displayPlannedMeals();
	}//end plan
	
	public static boolean isUnused(int[] look, int check){
		for(int i=0; i<look.length; i++){
			if(check==look[i]){
				return false;
			}//end check
		}//end loop
		return true;
	}//end is ok to use
	
	public static void displayPlannedMeals(){
		System.out.println("This week's home cooked meals: ");
		for(int i=0; i<homeCook.length; i++){
			System.out.println(homemade.get(homeCook[i]).getName());
		}//end show planed meals
		System.out.println("This week's restaurants: ");
		for(int i=0; i<eatOut.length; i++){
			System.out.println(outsourced.get(eatOut[i]).getName());
		}//end show planed restaurants
	}//end show planned
	
	public static void addMeal(){
		int source = ReadInput.range(-1, 0, 1, "Is this meal homecooked? (1=yes, 0=no): ");
		String tempNotes = "";
		if(source==1){
			System.out.println("What is the name of this meal?: ");
			String tempName = reader.nextLine();
			if(ReadInput.range(-1, 0, 1, "Do you want to add notes to this meal? (1=yes, 0=no): ")==1){
				System.out.println("Type the notes you wish to add, then press enter to save: ");
				tempNotes = reader.nextLine();
			}//end if notes
			Meals nom = new Meals(tempName, tempNotes, addIngredLoop());
			homemade.add(nom);
			System.out.println("Meal added.");
		}//end if home cooked
		else{
			System.out.println("What is the name of the restaurant?: ");
			String tempName = reader.nextLine();
			if(ReadInput.range(-1, 0, 1, "Do you want to add notes to this meal? (1=yes, 0=no): ")==1){
				System.out.println("Type the notes you wish to add, then press enter to save: ");
				tempNotes = reader.nextLine();
			}//end if notes
			ExternalSource nom = new ExternalSource(tempName, tempNotes);
			outsourced.add(nom);
			System.out.println("Restaurant added.");
		}//end if eat out
	}//end new meal
	
	public static void removeMeal(){
		if(ReadInput.range(-1, 1, 2, "Is the undesireable meal home cooked or not? (1=home cooked, 2=restaurant): ")==1){
			System.out.println("Your saved meals: ");
			displayMealArchive();
			int temp = ReadInput.range(-1, 1, homemade.size(), "What is the number of the meal you wish to remove?: ");
			homemade.remove(temp-1);
		}//end remove home
		else{
			System.out.println("Your saved restaurants: ");
			displayOutArchive();
			int temp = ReadInput.range(-1, 1, outsourced.size(), "What is the number of the restaurant you wish to remove?: ");
			outsourced.remove(temp-1);
		}//end remove eat out
	}//end remove meal
	
	public static void addIngredChoice(){
		System.out.println("Your saved meals: ");
		displayMealArchive();
		int temp = ReadInput.range(-1, 1, homemade.size(), "Type the number of the meal you want to add (an) ingredient(s) to: ");
		homemade.get(temp-1).addIngredient(addIngredLoop());
	}//end choose meal to add ingred
	
	public static ArrayList<Parts> addIngredLoop(){
		ArrayList<Parts> temp = new ArrayList<Parts>();
		System.out.println("Type the name of the first ingredient and press enter: ");
		String tempName = reader.nextLine();
		int tempType = ReadInput.range(-1, 0, 5, "Type the number of the unit the ingredient is measured in:"
				+ "\n" + "0: None. Individual objects. (like eggs)" + "\n" + "1: Cups" 
				+ "\n" + "2: Packages" + "\n" + "3: Teaspoons" + "\n" + "4: Tablespoons" 
				+ "\n" + "5: lbs");
		double tempQuant = ReadInput.positive(-1.0, "Type the amount of the ingredient's units needed: ");
		Parts thing = new Parts(tempName, tempQuant, tempType);
		temp.add(thing);
		while(ReadInput.range(-1, 0, 1, "Is there another ingredient? (1=yes, 0=no): ")==1){
			System.out.println("Type the name of the ingredient and press enter: ");
			tempName = reader.nextLine();
			tempType = ReadInput.range(-1, 0, 5, "Type the number of the unit the ingredient is measured in:"
					+ "\n" + "0: None. Individual objects. (like eggs)" + "\n" + "1: Cups" 
					+ "\n" + "2: Packages" + "\n" + "3: Teaspoons" + "\n" + "4: Tablespoons" 
					+ "\n" + "5: lbs");
			tempQuant = ReadInput.positive(-1.0, "Type the amount of the ingredient's units needed: ");
			thing = new Parts(tempName, tempQuant, tempType);
			temp.add(thing);
		}//end while loop
		System.out.println("Processing...");
		return temp;
	}//end ingred loop
	
	public static void displayMealArchive(){
		for(int i=0; i<homemade.size(); i++){
			System.out.println((i+1) + ": " + homemade.get(i).getName());
		}//end print loop
	}//end meal archive
	
	public static void displayOutArchive(){
		for(int i=0; i<outsourced.size(); i++){
			System.out.println((i+1) + ": " + outsourced.get(i).getName());
		}//end print loop
	}//end out archive
	
	public static void removeIngred(){
		System.out.println("Your saved meals: ");
		displayMealArchive();
		int temp = ReadInput.range(-1, 1, homemade.size(), "Type the number of the meal you want to remove an ingredient from: ");
		System.out.println("");
		homemade.get(temp-1).showIngreds();
		int ingred = ReadInput.range(-1, 1, homemade.size(), "Type the number of the ingredient you want to remove from this meal: ");
		homemade.get(temp).removeIngredient(ingred-1);
	}//end choose meal to add ingred
	
	public static void editNotes(){
		if(ReadInput.range(-1, 1, 2, "Is the meal you want to change the notes of home cooked or not?" + "\n" + "(1=home cooked, 2=restaurant): ")==1){
			System.out.println("Your saved meals: ");
			displayMealArchive();
			int temp = ReadInput.range(-1, 1, homemade.size(), "What is the number of the meal who's notes you wish to edit?: ");
			System.out.println("This is the current saved note for this meal: ");
			homemade.get(temp-1).getNote();
			System.out.println("Please type what you want the new note to be and press enter to save the new note." + "\n" 
			+ "Your old note will be destroyed. Copy-and-Paste is ok: ");
			homemade.get(temp-1).setNote(reader.nextLine());
		}//end change home notes
		else{
			System.out.println("Your saved restaurants: ");
			displayOutArchive();
			int temp = ReadInput.range(-1, 1, outsourced.size(), "What is the number of the restaurant who's notes you wish to edit?: ");
			System.out.println("This is the current saved note for this restaurant: ");
			outsourced.get(temp-1).getNote();
			System.out.println("Please type what you want the new note to be and press enter to save the new note." + "\n" 
			+ "Your old note will be destroyed. Copy-and-Paste is ok: ");
			outsourced.get(temp-1).setNote(reader.nextLine());
		}//end change eat out notes
		System.out.println("New notes saved.");
	}//end notes edit
	
	public static void displayArchives(){
		if(ReadInput.range(-1, 1, 2, "Would you like to see your saved meals or your saved restaurants? (1=home-cooked meals, 2=restaurants): ")==1){
			System.out.println("Your saved meals: ");
			displayMealArchive();
		}//end show home
		else{
			System.out.println("Your saved restaurants: ");
			displayOutArchive();
		}//end show eat out
	}//end archive display
	
}//end driver class
