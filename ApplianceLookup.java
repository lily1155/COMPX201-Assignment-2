import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ApplianceLookup{
    private static ApplianceBST appliances = new ApplianceBST();
    private static Scanner scanny = new Scanner(System.in);
    static boolean continuable;

    public static void main(String[] args){
        initialise();
        while(true){
            System.out.println("Please type a number corresponding to one of the following tasks:");
            System.out.println("1: Search for an appliance in the BST and print if it's included or not");
            System.out.println("2: Add a new appliance to the BST");
            System.out.println("3: Remove an appliance from the BST");
            System.out.println("4: Print all the appliances within a certain category");
            System.out.println("5: Print all the appliances within a certain category with a min and/or max price");
        
            String response = scanny.nextLine();
            continuable = false;
            switch(response){
                case "1":
                    System.out.println("In that case, please type the category, name and then price separated by commas (category, name, price)");
                    response = scanny.nextLine();
                    continuable = applianceGeneratable(response);
                    if(continuable){
                        Appliance appy = applianceGenerator(response);
                        boolean found = appliances.search(appy);
                        if(found){
                            System.out.println("That appliance is in the list");
                        }else{System.out.println("That appliance was not found");}
                    }
                    break;
                case "2":
                    System.out.println("In that case, please type the category, name and then price separated by commas (category, name, price)");
                    response = scanny.nextLine(); //receives appliance info from users
                    continuable = applianceGeneratable(response); //checks for validity
                    if(continuable){
                        Appliance appy = applianceGenerator(response);
                        appliances.insert(appy); //adds appliance to tree
                        System.out.println("Appliance inserted");
                    }
                    break;
                case "3":
                    System.out.println("In that case, please type the category, name and then price separated by commas (category, name, price)");
                    System.out.println("The computer will remove any nodes in the tree that match your specifications");
                    response = scanny.nextLine();
                    continuable = applianceGeneratable(response);
                    if(continuable){
                        Appliance appy = applianceGenerator(response);
                        appliances.remove(appy);
                        System.out.println("Appliance removed");
                    }
                    break;
                case "4":
                    System.out.println("Please type in the category that you'd like to have printed out");
                    response = scanny.nextLine();
                    try {
                        appliances.printCategory(response);
                    } catch (Exception e) {
                        System.out.println("Something went wrong when trying to parse your response. please try again and make sure you type your input correctly");
                    }
                    break;
                case "5":
                    System.out.println("Please type in the category you'd like printed out, then maximum and/or minimum values separated by a comma. Please type unused values as a single space. format: (category, min, max)");
                    response = scanny.nextLine();
                    try {
                        String[] params = response.split(",", -1);
                        if(params[2] == null || params[2] == " "){
                            appliances.printCategoryAbovePrice(params[0],Float.parseFloat(params[1]));
                        }else if(params[1] == null || params[1] == " "){
                            appliances.printCategoryBelowPrice(params[0],Float.parseFloat(params[2]));
                        }else{
                            appliances.printCategoryWithPriceRange(params[0],Float.parseFloat(params[1]),Float.parseFloat(params[2]));
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Your response was not parsed as a valid response");
                    break;
            }
            System.out.println("Enter anything to end");
            String useless = scanny.nextLine();
            }
        }
    //check if the string being given by the user can be turned into an appliance
    private static boolean applianceGeneratable(String s){
        try {
            String[] sections = s.split(",", -1);
            float price = Float.parseFloat(sections[2]); //may throw error if the third section isn't parseable as a float. we want that to happen, since it's being handled
            if(sections.length != 3){
                throw new IllegalArgumentException("Input must contain exactly three sections");
            }
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
    //make an appiance from a string
    private static Appliance applianceGenerator(String s){
        String[] sections = s.split(",", -1);
        float price = Float.parseFloat(sections[2]);
        Appliance a = new Appliance(sections[0], sections[1], price); //format of category, name, price necessary
        return a;
    }
    private static void initialise(){
        try (BufferedReader br = new BufferedReader(new FileReader("appliances.csv"))) { //change filename here as needed
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); // Split by comma
                appliances.insert(new Appliance(values[0], values[1], Float.parseFloat(values[2]))); //turn into an appliance
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}