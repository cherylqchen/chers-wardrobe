package ui;

// Represents control center, Main config
public class Main {

    // EFFECTS: run the GUI when given new WardrobeGUI();
    //          will run console-based app if given new ChersWardrobeApp();
    public static void main(String[] args) {
        new WardrobeGUI();

        // for console based app:
        // uncomment: new ChersWardrobeApp();
    }
}
