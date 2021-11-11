package petdatabase;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

// Cole Dietsch

// This program is a simple pet database application

public class PetDatabase implements Serializable {
    
    // We will create a database that allows for 5 rows
    static Pet[] pets = new Pet[5];
    static int petCount = 0;
    static boolean loadedFromFile = false;
    
    static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
            
        // Load questions from file
        try {
            pets = loadPets("myPets.db");
            
            // The following method removes null records and sets the pet counter to the correct value
            removeNullValuesFromPets();
            loadedFromFile = true;
            
            // Debug what was loaded
//            for (int i=0; i < pets.length; i++) {
//                System.out.println("Value: " + pets[i]);
//            }
        } catch (FileNotFoundException e) {
            System.out.println("Pet database not found");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } 
        
        // If we did not load from file, create a new pet array
        if (!loadedFromFile) {
            System.out.println("Creating new DB");
            pets = new Pet[5];
        }
        
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
                default -> System.out.println("Please enter a valid menu option");
            }
        }
        savePets(pets);
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
        int petAge = 0;
        boolean notDone = true;
        int petCounter = 0;
        String inputString;
        Scanner s = new Scanner(System.in);
        
        while (notDone) {
            if (petCount == 5) {
                System.out.println("Warning, there are already 5 pets in the database!");
                System.out.println("Please come back and add more after you have removed a pet from the options menu");
                notDone = false;
                break;
            }
            
            System.out.print("add pet (name, age): ");
            
            // Read both values in at once and split them after
            inputString = s.nextLine();
            String[] inputArray = inputString.split(" ");
            
            petName = inputArray[0];
            
            if (petName.equalsIgnoreCase("done")) {
                notDone = false;
                break;
            }
            
            // We have the correct input if there are two list items
            if (inputArray.length == 2) {
                petAge = Integer.parseInt(inputArray[1]);

                if (petAge > 1 && petAge <= 20) {
                    pets[petCount] = new Pet(petName, petAge); // Add pet to database
                    petCounter++;
                    petCount++;
                } else {
                    System.out.println("Pet age must be between 1 and 20");
                }
            } else {
                System.out.println("Error " + inputString + " is not valid input.");
            }
            
        } // end while loop
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
        String oldName = "";
        int newAge;
        int oldAge = 0;
        
        showAllPets();
        System.out.print("Enter the pet ID you want to update: ");
        updateID = s.nextInt();
        
        // Reject bad input
        if (updateID < 0 || updateID > pets.length || pets[updateID] == null) {
            System.out.println("Please enter a valid pet id");
            return;
        }
        
        System.out.print("Enter the new name and new age: ");
        newName = s.next(); 
        newAge = s.nextInt();
        oldName = pets[updateID].getName();
        oldAge = pets[updateID].getAge();
        
        if (newAge > 1 && newAge <= 20) {
            pets[updateID].setName(newName);
            pets[updateID].setAge(newAge);
            System.out.println(oldName + " " + oldAge + " changed to " + newName + " " + newAge + "\n");
        } else {
            System.out.println("Pet age must be between 1 and 20");
        }
        
    }
    
    private static void removePet() {
        int removeID;
        String petName;
        int petAge;
        Pet[] tempPets = new Pet[pets.length - 1];
        
        showAllPets();
        System.out.print("Enter the pet ID to remove: ");
        removeID = s.nextInt();
        
        if (removeID < 0 || removeID > pets.length || pets[removeID] == null) {
            System.out.println("Please enter a valid pet id");
            return;
        }
        
        petName = pets[removeID].getName();
        petAge = pets[removeID].getAge();
        
        // Use 2 array method to create a copy without unwanted index
        for (int x=0, y=0; x < pets.length; x++) {
            if (x != removeID) {
                tempPets[y++] = pets[x];
            }
        }
        
        // Creat a fresh array for pets 
        pets = new Pet[5];
        
        for (int x=0; x < tempPets.length; x++) { // Copy temp array back over to global DB
            pets[x] = tempPets[x];
        }

        petCount--;                         // Decrement pet count since we just removed one
        
        // Debug
//        for (int i=0; i < pets.length; i++) {
//            System.out.println("Value: " + pets[i]);
//        }

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

    public static boolean savePets(Pet[] petSet) {

        try {
            // Debug line
//            for (int i=0; i < petSet.length; i++) {
//                System.out.println("Value: " + petSet[i]);
//            }
            
            // Create output streams
            FileOutputStream myFile = new FileOutputStream("myPets.db");
            ObjectOutputStream myStream = new ObjectOutputStream(myFile);

            // Write object to stream
            myStream.writeObject(petSet);

            // Close the streams
            myStream.close();
            myFile.close();
            System.out.println("File saved ");
            return true;

        } catch (IOException e) {
            System.out.println("Error saving File");
            System.out.println(e);
        }
        return false;
    }
    
        public static Pet[] loadPets(String filename) throws IOException {

        Pet[] myPets = new Pet[5];

        try {
            // Create input streams
            FileInputStream myFile = new FileInputStream(filename);
            ObjectInputStream myStream = new ObjectInputStream(myFile);

            // Read stream into object
            myPets = (Pet[]) myStream.readObject();

            // Close the streams
            myStream.close();
            myFile.close();
            System.out.println("\nPet database loaded successfully!");
            
            // Debug line to see what was loaded from file
//            for (int i=0; i < myPets.length; i++) {
//                System.out.println("Value: " + myPets[i]);
//            }

        } catch (IOException e) {
            System.out.println("Error loading File");
            throw e;

        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

       return myPets;
    }
        // Because the array could contain null values, we need to remove them
        public static void removeNullValuesFromPets() {
            int count = 0;
            for (int i=0; i < pets.length; i++) {
//                System.out.println("Value: " + pets[i]);
                if (pets[i] != null) {
                    count++;
                }
            }
            petCount = count;
        }

}