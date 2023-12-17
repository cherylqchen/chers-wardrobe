package ui;

import model.Wardrobe;
import model.Clothing;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Represents the console-based digital wardrobe app
public class ChersWardrobeApp {
    private static final String JSON_STORE = "./data/wardrobe.json";
    private Wardrobe digitalWardrobe = new Wardrobe();
    private Scanner input;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    // EFFECTS: runs the wardrobe app
    // reference: TellerApp, TellerApp.java class
    public ChersWardrobeApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runChersWardrobe();
    }

    // MODIFIES: this
    // EFFECTS: initializes scanner input and main menu, facilitates the app
    //          to continue running until user presses "q" to quit
    // reference: TellerApp, TellerApp.runTeller() method
    private void runChersWardrobe() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            mainMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nThanks for using Cher's Wardrobe."
                + " Come again soon!");
    }

    // MODIFIES: this
    // EFFECTS: initializes the scanner input
    // reference: TellerApp, TellerApp.init() method
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: prints main menu
    // reference: TellerApp, TellerApp.displayMenu() method
    private void mainMenu() {
        System.out.println("\nWelcome to your digital wardrobe! "
                + "What would you like to do today?");
        System.out.println("\ta -> Add New Clothing Item");
        System.out.println("\tc -> Closet Cleanout");
        System.out.println("\tv -> View Current Wardrobe");
        System.out.println("\to -> Get a New Outfit");
        System.out.println("\ts -> Save Wardrobe to Computer");
        System.out.println("\tl -> Load Wardrobe from File");
        System.out.println("\tq -> Quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user input in response to main menu options
    // reference: TellerApp, TellerApp.processCommand() method
    private void processCommand(String command) {
        switch (command) {
            case "a":
                doAddClothing();
                break;
            case "c":
                doClosetCleanout();
                break;
            case "v":
                doViewWardrobe();
                break;
            case "o":
                doOutfitPicker();
                break;
            case "s":
                saveWardrobe();
                break;
            case "l":
                loadWardrobe();
                break;
            default:
                System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes "add clothing" feature. prompts user to input an ID
    //          for a clothing item, and tag its type, colour, etc., then
    //          constructs a clothing item using that and adds to wardrobe
    private void doAddClothing() {
        System.out.println("To add a new clothing item to your digital "
                + "wardrobe, please first give it a short description, e.g, "
                + "'Fancy blouse'");
        String itemId = input.next();

        System.out.println("Great! Now is this a top, bottom, jacket, or "
                + "accessory?");
        String itemType = scanType();

        System.out.println("What colour is it mainly?");
        String itemColour = input.next();

        System.out.println("Please enter which of the following best describes"
                        + " this item's fit: 'tight','comfy', or 'baggy'?");
        String itemFit = scanFit();

        System.out.println("Describe the mood of this clothing item. E.g,"
                + "'flirty','sporty','elegant', etc.");
        String itemMood = input.next();

        System.out.println("Lastly, enter one of the following to describe "
                + "the dress code: 'casual', 'business casual', 'formal', "
                + "'cocktail', or 'black tie'");
        String itemDressCode = scanDressCode();

        Clothing newestItem = new Clothing(itemId,itemType,itemColour,itemFit,
                itemMood,itemDressCode);
        digitalWardrobe.addClothing(newestItem);
        System.out.println(itemId + " has been added to your wardrobe!");
    }

    // MODIFIES: this
    // EFFECTS: processes "remove clothing" feature. displays current wardrobe
    //          and prompts user to input ID of item they want to remove, then
    //          removes it from wardrobe
    private void doClosetCleanout() {
        if (!(digitalWardrobe.getAllClothes().isEmpty())) {
            System.out.println("Downsizing today, are we? That's amazing!");
            doViewWardrobe();
            System.out.println("Enter the ID of the clothing item you'd like "
                    + " to donate.");
            String itemToRemove = input.next();
            digitalWardrobe.removeClothing(itemToRemove);
            System.out.println("Bye, " + itemToRemove + "! Congratulations on "
                    + "donating! It'll make someone else a very happy "
                    + "shopper.");
        } else {
            System.out.println("You can't clean out an empty closet!");
        }
    }

    // EFFECTS: processes "view wardrobe" feature. prints a neat table of every
    //          clothing item added so far in chronological order
    private void doViewWardrobe() {
        System.out.println("Here is your wardrobe from earliest to latest...");

        if (!(digitalWardrobe.getAllClothes().isEmpty())) {
            int maxIndex = digitalWardrobe.getAllClothes().size();
            final Object[][] table = new String[maxIndex + 1][];
            table[0] = new String[] {"ID", "Type", "Colour", "Fit", "Mood",
                    "Dress Code"};

            for (int num = 0; num < maxIndex; num++) {
                Clothing c = digitalWardrobe.getAllClothes().get(num);
                table[num + 1] = new String[]{c.getId(),c.getType(),
                        c.getColour(),c.getFit(),c.getMood(),c.getDressCode()};
            }

            for (final Object[] row : table) {
                System.out.format("%-25s%-25s%-25s%-25s%-25s%-25s%n", row);
            }
        } else {
            System.out.println("Your wardrobe is empty at the moment!");
        }
    }

    // EFFECTS: processes "filter clothing" feature. filters allClothes in
    //          wardrobe by colour, mood, and dress code and feeds filtered
    //          list to filterTypes() to be sorted by type
    private void doOutfitPicker() {
        if (!(digitalWardrobe.getAllClothes().isEmpty())) {
            System.out.println("Hi, your digital wardrobe here to recommend "
                    + "you an outfit! First give me a colour you'd like to "
                    + "wear today.");
            String wantColour = input.next();

            System.out.println("Now what vibes are we going for today?");
            String wantMood = input.next();

            System.out.println("And finally, what's the general dress code?");
            String wantDressCode = input.next();

            List<Clothing> allClothes = digitalWardrobe.getAllClothes();
            allClothes = digitalWardrobe.filterAll("colour",wantColour,
                    allClothes);
            allClothes = digitalWardrobe.filterAll("mood",wantMood,allClothes);
            allClothes = digitalWardrobe.filterAll("dress code",wantDressCode,
                    allClothes);

            filterTypes(allClothes);
        } else {
            System.out.println("Add some clothing to your wardrobe "
                    + "before requesting an outfit!");
        }
    }

    // MODIFIES: filteredTops, filteredBottoms, filteredJackets, and
    //           filtered Accessories
    // EFFECTS: sorts a filtered list of allClothes into types and feeds
    //          type-specific filtered lists to displayList() to be
    //          printed out
    private void filterTypes(List<Clothing> allFiltered) {
        List<Clothing> filteredTops = new ArrayList<>();
        List<Clothing> filteredBottoms = new ArrayList<>();
        List<Clothing> filteredJackets = new ArrayList<>();
        List<Clothing> filteredAccessories = new ArrayList<>();

        for (Clothing nextClothing : allFiltered) {
            if (nextClothing.getType().equals("top")) {
                filteredTops.add(nextClothing);
            } else if (nextClothing.getType().equals("bottom")) {
                filteredBottoms.add(nextClothing);
            } else if (nextClothing.getType().equals("jacket")) {
                filteredJackets.add(nextClothing);
            } else {
                filteredAccessories.add(nextClothing);
            }
        }

        System.out.println("\n");
        displayList("TOPS",filteredTops);
        displayList("BOTTOMS",filteredBottoms);
        displayList("JACKETS",filteredJackets);
        displayList("ACCESSORIES",filteredAccessories);
    }

    // EFFECTS: prints out a table of clothing items matching the preferences
    //          entered by user. clothes are shown by type (e.g, all
    //          recommended tops together). if none match, prints out "NO TOPS
    //          (or any other type) MATCH SEARCH CRITERIA"
    private void displayList(String title, List<Clothing> list) {
        if (list.isEmpty()) {
            System.out.println("NO " + title + " MATCH SEARCH CRITERIA \n");
        } else {
            int maxIndex = list.size();
            final Object[][] table = new String[maxIndex + 1][];
            table[0] = new String[] {"ID", "Type", "Colour", "Fit", "Mood",
                    "Dress Code"};

            for (int num = 0; num < maxIndex; num++) {
                Clothing c = list.get(num);
                table[num + 1] = new String[]{c.getId(),c.getType(),
                        c.getColour(),c.getFit(),c.getMood(),c.getDressCode()};
            }

            System.out.println("RECOMMENDED " + title);
            for (final Object[] row : table) {
                System.out.format("%-25s%-25s%-25s%-25s%-25s%-25s%n", row);
            }
            System.out.println("\n");
        }
    }

    // EFFECTS: scans user input for "t", "b", "j", or "a" and returns
    //          appropriate string, e.g, "top" for clothing type
    private String scanType() {
        boolean loopAgain = true;
        String itemType = "";

        while (loopAgain) {
            loopAgain = false;

            typeMenu();
            itemType = input.next();
            itemType = itemType.toLowerCase();
            if (itemType.equals("t")) {
                itemType = "top";
            } else if (itemType.equals("b")) {
                itemType = "bottom";
            } else if (itemType.equals("j")) {
                itemType = "jacket";
            } else if (itemType.equals("a")) {
                itemType = "accessory";
            } else {
                loopAgain = invalidInput();
            }
        }
        return itemType;
    }

    // EFFECTS: prints input options for clothing type
    private void typeMenu() {
        System.out.println("\tt -> Top");
        System.out.println("\tb -> Bottom");
        System.out.println("\tj -> Jacket");
        System.out.println("\ta -> Accessory");
    }

    // EFFECTS: scans user input for "1", "2", or "3" and returns
    //          appropriate string, e.g, "baggy" for clothing fit
    private String scanFit() {
        boolean loopAgain = true;
        String itemFit = "";

        while (loopAgain) {
            loopAgain = false;

            fitMenu();
            itemFit = input.next();
            if (itemFit.equals("1")) {
                itemFit = "tight";
            } else if (itemFit.equals("2")) {
                itemFit = "comfy";
            } else if (itemFit.equals("3")) {
                itemFit = "baggy";
            } else {
                loopAgain = invalidInput();
            }
        }
        return itemFit;
    }

    // EFFECTS: prints input options for clothing fit
    private void fitMenu() {
        System.out.println("\t1 -> Tight");
        System.out.println("\t2 -> Comfy");
        System.out.println("\t3 -> Baggy");
    }

    // EFFECTS: scans user input for "1", "2", "3", "4" or "5" and returns
    //          appropriate string from "casual" to "white tie" for dress code
    private String scanDressCode() {
        boolean loopAgain = true;
        String itemDressCode = "";

        while (loopAgain) {
            loopAgain = false;

            dressCodeMenu();
            itemDressCode = input.next();
            if (itemDressCode.equals("1")) {
                itemDressCode = "casual";
            } else if (itemDressCode.equals("2")) {
                itemDressCode = "business casual";
            } else if (itemDressCode.equals("3")) {
                itemDressCode = "formal";
            } else if (itemDressCode.equals("4")) {
                itemDressCode = "cocktail";
            } else if (itemDressCode.equals("5")) {
                itemDressCode = "white tie";
            } else {
                loopAgain = invalidInput();
            }
        }
        return itemDressCode;
    }

    // EFFECTS: saves the wardrobe to file
    private void saveWardrobe() {
        try {
            jsonWriter.open();
            jsonWriter.write(this.digitalWardrobe);
            jsonWriter.close();
            System.out.println("Saved your wardrobe to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads wardrobe from file
    private void loadWardrobe() {
        try {
            digitalWardrobe = jsonReader.read();
            System.out.println("Loaded your wardrobe from " + JSON_STORE + " !");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: prints input options for clothing dress code
    private void dressCodeMenu() {
        System.out.println("\t1 -> Casual");
        System.out.println("\t2 -> Business Casual");
        System.out.println("\t3 -> Formal");
        System.out.println("\t4 -> Cocktail");
        System.out.println("\t5 -> White Tie");
    }

    // EFFECTS: prints error message and returns true to help any scan function
    //          continue looping until user inputs something acceptable
    private boolean invalidInput() {
        System.out.println("Invalid selection. Try again.");
        return true;
    }
}
