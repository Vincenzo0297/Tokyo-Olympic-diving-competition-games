import java.util.*;

class Country {
    // declare variable
    private String name;
    private String player;
    private int age;
    
    //default constructor
    public Country(String name, String player, int age) {
        this.name = name;
        this.player = player;
        this.age = age;
    }
    
     // Accessor methods
    public String getName() {
        return name;
    }
    
    public String getPlayer() {
        return player;
    }
    
    public int getAge() {
        return age;
    }
    
    // Mutator method
    public void setInfo(String name, String player, int age) {
        this.name = name;
        this.player = player;
        this.age = age;
    }
}

class Diving {
    
    // declare variable
    public final static int SIZE = 7;   // No. of judges : 7
    private Country name;              //Country name
    private double [] score;
    private double difficulty;
    private double cf;               //Carried Forward
    private double fs;              //Final score
    
     //default constructor
    public Diving(Country name, double [] score, double difficulty, double cf) {
        this.name = name;
        this.score = score;
        this.difficulty = difficulty;
        this.cf = cf;
    }
    
    // Accessor methods
    public Country getCountry() {
        return name;
    }
    
    public double getDifficulty() {
        return difficulty;
    }
    
    public double getCarriedForward() {
        return cf;
    }
    
    public double getFinalScore() {
        return fs;
    }
    
     // Mutator method
    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }
    
    private ArrayList<Double> getSortList() {
        ArrayList<Double> SortedList = new ArrayList<Double>(); // Initialization using Arrays.asList
        
        for (Double i: score) // total up all the value inside No. of judges : 7
        SortedList.add(i);

        Collections.sort(SortedList);  // Collection.sort in ascending order.
        return SortedList;
    }
    
    private double highest() {
        return getSortList().get(SIZE - 1); //find the highest values
    } 
    
    private double sechighest() {
         return getSortList().get(SIZE - 2); //find the sechighest values
    }
    
    private double lowest() {
          return getSortList().get(0); //find the lowest values
    }
    
    private double seclowest() { 
         return getSortList().get(0 + 1); //find the seclowest values
    }
    
    public double FinalScore() {
        double total = 0.0;
        
        for (int i = 0; i <SIZE; i++)  // Calculate to get Final Score
        total += score[i];
            
        return (total - highest() - sechighest() - lowest() - seclowest()) * difficulty;
    }
    
    public double getTotalScore() {
        return (FinalScore() + cf);  // Calculation of total score (FinalScore + cf)
    }
    
    public void printinfo() {
    	System.out.printf("%-12s", name.getName()); 
    	for(int i = 0;i < score.length; i++) {
    		System.out.printf("%6.1f", score[i]);
    	}
    	System.out.printf("%12.1f", difficulty);
        System.out.printf("%9.2f", cf); 
        System.out.printf("%9.2f", FinalScore());
        System.out.printf("%9.2f", getTotalScore());
    	System.out.println();
    }
}

class Vincent_A3{
    
    private static final String [] Countries = {"China 2", "Thailand", "China 1", "South Korea", 
                                                "Japan", "USA", "Australia", "Malaysia", "Russia", 
                                                "Brazil"};
                                                
    private static double sizes[] = new double[Diving.SIZE];  // Initialize an array of size 	
    private static double carryforward[] = new double[Countries.length]; // Initialise an array of carried forward scores
    private static ArrayList<Country> cList = new ArrayList<Country>(); // Construct an arraylist of country object
    private static ArrayList<Diving> dList = new ArrayList<Diving>();  // Initialise an arraylist of Diving object
    private static double difficulty[] = new double[Countries.length]; // Initilize an array of difficulty
    private static ArrayList<Diving> temp = new ArrayList<Diving>();  // Temporary arraylist used for sorting to view result table 
   
    
     public static void main(String[] args) {
        
    	for(int i = 0; i < carryforward.length; i++) {
    		carryforward[i] = 0.00; //carryforward array to be 0.00 
    	}
    	
        for (int i = 0; i < Countries.length; i++) { 
            cList.add(new Country(Countries[i], "Name " + (i + 1), getAge()));  // Add countries to the cList arraylist
        }
        
        for(int Rounds = 1; Rounds <= 5; Rounds++) {
          
            for (int i = 0; i < Countries.length; i++) {
            	difficulty[i] = getDifficulty(); // Add into difficulty array 
            }
            
            // Add into diving arraylist 
            for(int i = 0; i < cList.size(); i++) {
            	getScore(sizes);		                                    // Get 7 random judge scores and put into marks array
            	double[] sizestemp = new double[sizes.length];
            	for(int x = 0; x < sizes.length; x++) {
            		//System.out.println(marks[i]);
            		System.arraycopy(sizes, 0, sizestemp, 0, sizes.length);
            	}
            	dList.add(new Diving(cList.get(i), sizestemp, difficulty[i], carryforward[i]));
            }
            
        	System.out.println("Round: " + Rounds);
        	System.out.println("Starting position");
        	System.out.println();
        	displayGameInfo(dList);	
            
        	// Displays out table 1
        	System.out.println();
            displayResultInfo(dList); 
            
            // Displays out table 2
        	System.out.println();
        	
        	// Run the update cf array function
        	updateCFArray(dList, carryforward);
        	
        	// Create a temporary arraylist to do sorting without affecting previous codes 
        	temp = dList;
        	displaySortedList(temp);
        	
        	// Remove all elements inside dList
        	dList.clear();
        }
    }

    //generate Random vaules that round up or down to the nearest 1dp
    private static void getScore(double[] score) {
        for(int i = 0; i < score.length; i++) {
        	sizes[i] = Math.round((new Random().nextDouble() * (10 - 0) + 0) * 2) / 2.0;            // Round this up to nearest 1dp	
        }
    }

    //generate a random values in the range 2 - 5 
    private static double getDifficulty() {
        double difficultly = Math.round((2 + new Random().nextDouble() * (5 - 2)) * 10) / 10.0;	    // Round this up to nearest 1dp
        return difficultly;
    }

    //generate a random age in the range 15 - 30
    private static int getAge() {
        int age = (int)((Math.random() * (30 - 15) + 15));
        return age;
    }

    //Update the CF for every round
    private static void updateCFArray(ArrayList<Diving> aList, double[]cfArray) {
    	for(int i = 0; i < carryforward.length; i++) {
    		carryforward[i] = aList.get(i).getTotalScore();
    	}
    }

    //display countries names, divers, names,ages and difficulty
    private static void displayGameInfo(ArrayList<Diving> aList) {
        System.out.printf("%-15s%-10s%-10s%s%n", "Country", "Diver", "Age", "Difficulty");
        
        // Print list of country objects
        for(int i = 0; i < cList.size(); i++) {
            System.out.printf("%-15s%-10s%-10s%10.1f%n", 
            aList.get(i).getCountry().getName(),
            aList.get(i).getCountry().getPlayer(),
            aList.get(i).getCountry().getAge(),
            aList.get(i).getDifficulty());
        }
    }

    //Display result of judge 1 - 7 for each country
    private static void displayResultInfo(ArrayList<Diving> aList) {
        System.out.printf("%-15s %-6s%-6s%-6s%-6s%-6s%-6s%-7s%-13s%-9s%-9s%7s%n", 
            "Countries","J1", "J2", "J3", "J4", "J5", "J6", "J7", "Difficulty", "c/f", "Current", "Total");
        	
            // Displays out table 2
        	for(int i = 0; i < aList.size(); i++) {
            	aList.get(i).printinfo();
            }
    }

     // Sorts the temp arraylist based on total score
     private static void displaySortedList(ArrayList<Diving> aList) {
         Collections.reverse(aList);
         aList.sort((o1, o2) -> Double.compare(o1.getTotalScore(), o2.getTotalScore()));
        
         System.out.println("The result is");  // The third table of the results
         int rank = 1;
         for(Diving temp : aList) {
            System.out.printf("%-4d%-15s%.2f%n", rank++, temp.getCountry().getName(), temp.getTotalScore());
         } 
     }
}
