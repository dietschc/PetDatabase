package petdatabase;
import java.util.Scanner;

// This program is a simple pet database application

public class PetDatabase {
    
    // We will create a database array that allows for 100 pets
    static Pet[] pets = new Pet[100];
    static int petCount = 0;
    
    static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
        
        boolean doLoop = true;
        
        System.out.println("Welcome to Pet Database Program \n");
        
        while (doLoop) {          
            switch (getUserChoice()) {
                case 1 -> showAllPets();
                case 2 -> addPets();
                case 3 -> updatePet();
                case 4 -> removePet();
                case 5 -> searchPetsByName();
                case 6 -> searchPetsByAge();
                case 7 -> doLoop = false;
            }
        }
        System.out.println("Goodbye!");
    }
    
    private static int getUserChoice() {
        int choice;    
        System.out.print("""
                    What would you like to do?
                     1) View all pets
                     2) Add more pets
                     3) Update an existing pet
                     4) Remove an existing pet
                     5) Search pets by name 
                     6) Search pets by age 
                     7) Exit program 
                   """); 
        System.out.print("Your choice: ");
        choice = s.nextInt();
        return choice;
    }
    
    private static void addPets() {
        String petName;
        int petAge;
        boolean notDone = true;
        int petCounter = 0;
        
        // Add pets until done string found
        while (notDone) {
            System.out.print("add pet (name, age): ");
            petName = s.next();          
            if (petName.equalsIgnoreCase("done")) {
                notDone = false;
                break;
            }           
            petAge = s.nextInt();
            
            // Add pet to database
            pets[petCount] = new Pet(petName, petAge);
            petCounter++;
            petCount++;
        }
        System.out.println(petCounter + " pets added.\n");
    }
    
    private static void showAllPets() {
        printTableHeader();
        for (int x=0; x < petCount; x++) {
            printTableRow(x, pets[x].getName(), pets[x].getAge()); 
        }
        printTableFooter(petCount);
    }
    
    private static void updatePet() {
       System.out.println("The updatePet() method has not been implemented yet");
    }
    
    private static void removePet() {
       System.out.println("The removePet() method has not been implemented yet");
    }
    
    private static void searchPetsByName() {
       System.out.println("The searchPetsByName() method has not been implemented yet");
    }
    
    private static void searchPetsByAge() {
       System.out.println("The searchPetsByAge() method has not been implemented yet");
    }
    
    private static void printTableHeader() {
        System.out.print("""
           +----------------------+
           | ID | NAME      | AGE |
           +----------------------+
           """); 
    }
    
    private static void printTableRow(int id, String name, int age) {
        System.out.printf("|%3s | %-10s|%4s | %n", id, name, age);
    }
    
    private static void printTableFooter(int rowCount) {
        System.out.println("+----------------------+");
        System.out.println(rowCount + " rows in set.\n");
    }

}