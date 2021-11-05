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
        int updateID;
        String newName;
        String oldName;
        int newAge;
        int oldAge;
        
        showAllPets();
        System.out.print("Enter the pet ID you want to update: ");
        updateID = s.nextInt();
        System.out.print("Enter the new name and new age: ");
        newName = s.next(); 
        newAge = s.nextInt();
        
        oldName = pets[updateID].getName();
        oldAge = pets[updateID].getAge();
        pets[updateID].setName(newName);
        pets[updateID].setAge(newAge);
        
        System.out.println(oldName + " " + oldAge + " changed to " + newName + " " + newAge + "\n");
    }
    
    private static void removePet() {
        int removeID;
        String petName;
        int petAge;
        Pet[] tempPets = new Pet[petCount];
        
        showAllPets();
        System.out.print("Enter the pet ID to remove: ");
        removeID = s.nextInt();
        
        petName = pets[removeID].getName();
        petAge = pets[removeID].getAge();
        
        for (int x=0; x < removeID; x++) { // Copy first half of array up to but not
            tempPets[x] = pets[x];         // including the element we want to remove
        }
        
        for (int x = removeID; x < petCount; x++) { // Copy remainder of array
            tempPets[x] = pets[x + 1];
        }
               
        for (int x=0; x < tempPets.length; x++) { // Copy temp array back over to global DB
            pets[x] = tempPets[x];
        }

        petCount--;                         // Decrement pet count since we just removed one
        System.out.println(petName + " " + petAge + " is removed.\n");
    }
    
    private static void searchPetsByName() {
        String searchString;
        int[] displayRows = new int[petCount];
        int matchingRows = 0;
        
        System.out.print("Enter a name to search: ");
        searchString = s.next();
        
        printTableHeader();
        for (int x=0; x < petCount; x++) {
            if (searchString.toLowerCase().matches(pets[x].getName().toLowerCase())) {
                printTableRow(x, pets[x].getName(), pets[x].getAge()); 
                matchingRows++;
            }
        }
        printTableFooter(matchingRows);
    }
    
    private static void searchPetsByAge() {
        int searchNumber;
        int[] displayRows = new int[petCount];
        int matchingRows = 0;
        System.out.print("Enter age to search: ");
        searchNumber = s.nextInt();
        printTableHeader();
        for (int x=0; x < petCount; x++) {
            if (searchNumber == pets[x].getAge()) {
                printTableRow(x, pets[x].getName(), pets[x].getAge()); 
                matchingRows++;
            }
        }
        printTableFooter(matchingRows);
        
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