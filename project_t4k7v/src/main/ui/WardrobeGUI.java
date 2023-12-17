package ui;


import model.Clothing;
import model.Event;
import model.EventLog;
import model.Wardrobe;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static java.awt.GridBagConstraints.*;

// Represents the graphical user interface for a ChersWardrobeApp
public class WardrobeGUI extends JFrame {
    public static final int WIDTH = 1100;
    public static final int HEIGHT = 700;
    public static final String LIGHT_ACCENT_COLOUR = "#adc9de";
    public static final String DARK_ACCENT_COLOUR = "#274861";

    private Wardrobe wardrobe;
    private JPanel mainMenu;
    private JPanel wardrobeMenu;

    private JPanel topsPanel;
    private JPanel bottomsPanel;
    private JPanel jacketsPanel;
    private JPanel accessoriesPanel;

    private final Map<String, JPanel> topsMap = new HashMap<>();
    private final Map<String, JPanel> bottomsMap = new HashMap<>();
    private final Map<String, JPanel> jacketsMap = new HashMap<>();
    private final Map<String, JPanel> accessoriesMap = new HashMap<>();

    private static final String JSON_STORE = "./data/wardrobe.json";
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private boolean loaded = false;

    private final EventLog eventLog = EventLog.getInstance();


    //MODIFIES: this
    //EFFECTS: creates mainMenu and wardrobe Menu, but only displays mainMeny
    public WardrobeGUI() {
        super("Cher's Wardrobe: Your Digital Wardrobe App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(WIDTH,HEIGHT);
        this.setLocationRelativeTo(null);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        initMainMenu();
        this.wardrobe = new Wardrobe();

        splash();
        this.setVisible(true);
        initWardrobeMenu();
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                for (Event next : eventLog) {
                    System.out.println(next.toString());
                }
            }
        });
    }

    // EFFECTS: creates and displays a 5 sec splash screen
    // reference: PokeJar by Anthony Du, https://github.com/anthonydu/cpsc210-pokejar,
    //            PokeJarGUI.splash() method
    private void splash() {
        JWindow splashScreen = new JWindow();
        splashScreen.setSize(960, 540);
        splashScreen.setLocationRelativeTo(null);
        splashScreen.add(new JLabel(new ImageIcon("./data/splash.jpg")));
        splashScreen.setVisible(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            splashScreen.setVisible(false);
        }
    }

    // MODIFIES: mainMenu
    // EFFECTS: initializes the opening screen/main menu and all its components
    private void initMainMenu() {
        mainMenu = new JPanel();
        mainMenu.setLayout(new BoxLayout(mainMenu, BoxLayout.PAGE_AXIS));
        mainMenu.setBackground(Color.decode(LIGHT_ACCENT_COLOUR));

        initMainImage();
        initMainGreeting();
        initMainButtons();

        this.add(mainMenu);
    }

    // MODIFIES: mainMenu
    // EFFECTS: renders the welcome image and adds it to mainMenu
    private void initMainImage() {
        JPanel mainImagePanel = new JPanel();
        JLabel mainImage = new JLabel();
        mainImage.setIcon(new ImageIcon("./data/welcomeimage.png"));
        mainImagePanel.add(mainImage);
        mainImagePanel.setBackground(Color.decode(LIGHT_ACCENT_COLOUR));
        mainMenu.add(mainImagePanel);
    }

    // MODIFIES: mainMenu
    // EFFECTS: renders the welcome message and adds it to mainMenu
    private void initMainGreeting() {
        JPanel mainGreetingPanel = new JPanel();
        JLabel mainGreeting = new JLabel("Digitize your wardrobe today! "
                + "Click below to begin.");
        mainGreeting.setFont(new Font("Helvetica Neue", Font.BOLD, 24));
        mainGreetingPanel.add(mainGreeting);
        mainGreetingPanel.setBackground(Color.decode(LIGHT_ACCENT_COLOUR));
        mainMenu.add(mainGreetingPanel);
    }

    // MODIFIES: mainMenu
    // EFFECTS: initializes the three buttons onto the main menu
    //          and sets up their action listeners
    private void initMainButtons() {
        JPanel mainButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,
                70,5));
        JButton openWardrobeBtn = new JButton("Open Wardrobe");
        JButton loadWardrobeBtn = new JButton("Load Wardrobe from File");
        JButton quitAppBtn = new JButton("Quit");

        stylizeMainBtns(openWardrobeBtn);
        stylizeMainBtns(loadWardrobeBtn);
        stylizeMainBtns(quitAppBtn);

        openWardrobeBtn.addActionListener(e -> openWardrobe());
        loadWardrobeBtn.addActionListener(e -> loadWardrobe());
        quitAppBtn.addActionListener(e -> quitApp());

        mainButtonPanel.add(openWardrobeBtn);
        mainButtonPanel.add(loadWardrobeBtn);
        mainButtonPanel.add(quitAppBtn);
        mainButtonPanel.setBackground(Color.decode(LIGHT_ACCENT_COLOUR));

        mainMenu.add(mainButtonPanel);
    }

    // EFFECTS: applies style to mainMenu buttons
    private void stylizeMainBtns(JButton button) {
        button.setFont(new Font("Helvetica Neue", Font.PLAIN, 18));
        button.setMargin(new Insets(7,7,7,7));
    }

    // REQUIRES: openWardrobeBtn is clicked (this is its event handler)
    // EFFECTS: sets up the wardrobe page in a certain way
    //          depending on whether user has loaded in data, switches display
    //          from mainMenu to wardrobeMenu
    private void openWardrobe() {
        mainMenu.setVisible(false);

        if (loaded) {
            loadWardrobeMenu();
        }
        wardrobeMenu.setVisible(true);
    }

    // REQUIRES: loadWardrobeBtn is clicked (this is its event handler)
    // EFFECTS: loads in and reads data and initializes the wardrobeMenu in the
    //          "data loaded" case
    private void loadWardrobe() {
        try {
            this.wardrobe = jsonReader.read();
            loaded = true;
        } catch (IOException e) {
            System.out.println("ERROR: Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: wardrobeMenu
    // EFFECTS: sets up the wardrobeMenu split pane in the case where user loaded in data
    private void loadWardrobeMenu() {
        wardrobeMenu = new JPanel();
        wardrobeMenu.setBackground(Color.white);

        JPanel sidePanel = initWardrobeSidePanel();
        JPanel wardrobeScreen = initLoadedWardrobeScreen();

        JSplitPane splitWardrobe = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                sidePanel,wardrobeScreen);

        splitWardrobe.setDividerLocation(230);
        splitWardrobe.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        wardrobeMenu.add(splitWardrobe);
        this.add(wardrobeMenu);
    }

    // EFFECTS: sets up the wardrobe display screen (right panel) in the case
    //          where user loaded in data
    private JPanel initLoadedWardrobeScreen() {
        JPanel wardrobeScreen = new JPanel();
        wardrobeScreen.setLayout(new GridLayout(2,2));

        topsPanel = displayLoadedTops();
        bottomsPanel = displayLoadedBottoms();
        jacketsPanel = displayLoadedJackets();
        accessoriesPanel = displayLoadedAccessories();

        wardrobeScreen.add(topsPanel);
        wardrobeScreen.add(bottomsPanel);
        wardrobeScreen.add(jacketsPanel);
        wardrobeScreen.add(accessoriesPanel);

        return wardrobeScreen;
    }

    // EFFECTS: displays the Tops panel in the case where user loads in data
    private JPanel displayLoadedTops() {
        topsPanel = initClothesPanel("Tops");

        if (!this.wardrobe.getTops().isEmpty()) {
            for (Clothing c : this.wardrobe.getTops()) {
                JPanel newPanel = createClothingPanel(c);
                topsPanel.add(newPanel);
                topsMap.put(c.getId(),newPanel);
                topsPanel.add(Box.createVerticalStrut(HEIGHT / 20));
                validate();
            }
        }
        return topsPanel;
    }

    // EFFECTS: displays the Bottoms panel in the case where user loads in data
    private JPanel displayLoadedBottoms() {
        bottomsPanel = initClothesPanel("Bottoms");

        if (!this.wardrobe.getBottoms().isEmpty()) {
            for (Clothing c : this.wardrobe.getBottoms()) {
                JPanel newPanel = createClothingPanel(c);
                bottomsPanel.add(newPanel);
                bottomsMap.put(c.getId(),newPanel);
                bottomsPanel.add(Box.createVerticalStrut(HEIGHT / 20));
                validate();
            }
        }
        return bottomsPanel;
    }

    // EFFECTS: displays the Jackets panel in the case where user loads in data
    private JPanel displayLoadedJackets() {
        jacketsPanel = initClothesPanel("Jackets");

        if (!this.wardrobe.getJackets().isEmpty()) {
            for (Clothing c : this.wardrobe.getJackets()) {
                JPanel newPanel = createClothingPanel(c);
                jacketsPanel.add(newPanel);
                jacketsMap.put(c.getId(),newPanel);
                jacketsPanel.add(Box.createVerticalStrut(HEIGHT / 20));
                validate();
            }
        }
        return jacketsPanel;
    }

    // EFFECTS: displays the Accessories panel in the case where user loads in data
    private JPanel displayLoadedAccessories() {
        accessoriesPanel = initClothesPanel("Accessories");

        if (!this.wardrobe.getAccessories().isEmpty()) {
            for (Clothing c : this.wardrobe.getAccessories()) {
                JPanel newPanel = createClothingPanel(c);
                accessoriesPanel.add(newPanel);
                accessoriesMap.put(c.getId(),newPanel);
                accessoriesPanel.add(Box.createVerticalStrut(HEIGHT / 20));
                validate();
            }
        }
        return accessoriesPanel;
    }

    // REQUIRES: quitAppBtn is clicked (this is its event handler)
    // EFFECTS: quits the application
    private void quitApp() {
        for (Event next : eventLog) {
            System.out.println(next.toString());
        }

        System.exit(0);
    }

    // MODIFIES: wardrobeMenu
    // EFFECTS: initializes wardrobeMenu split pane in the case where user
    //          does NOT load in data
    private void initWardrobeMenu() {
        wardrobeMenu = new JPanel();
        wardrobeMenu.setBackground(Color.white);

        JPanel sidePanel = initWardrobeSidePanel();
        JPanel wardrobeScreen = initWardrobeScreen();

        JSplitPane splitWardrobe = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                sidePanel,wardrobeScreen);

        splitWardrobe.setDividerLocation(230);
        splitWardrobe.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        wardrobeMenu.add(splitWardrobe);
        this.add(wardrobeMenu);
        wardrobeMenu.setVisible(false);
    }

    // EFFECTS: creates the side panel navbar for the wardrobeMenu
    private JPanel initWardrobeSidePanel() {
        JPanel sidePanel = new JPanel();

        sidePanel.setBackground(Color.decode(LIGHT_ACCENT_COLOUR));
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.PAGE_AXIS));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        initSideNavBtns(sidePanel);

        return sidePanel;
    }

    // EFFECTS: initializes side panel buttons and adds to side panel
    // reference: D&D Character Tracker by Huxley, https://github.com/huxleymgb/CPSC-210-Project,
    //            CharacterAppGUI.setupButtons() method
    private void initSideNavBtns(JPanel parent) {
        JButton returnMainBtn = new JButton("Main Menu");
        JButton addClothingBtn = new JButton("Add New Clothing Item");
        JButton getOutfitBtn = new JButton("Get Suggested Outfit");
        JButton saveWardrobeBtn = new JButton("Save Wardrobe");

        returnMainBtn.addActionListener(e -> returnToMain());
        addClothingBtn.addActionListener(e -> addClothingItem());
        getOutfitBtn.addActionListener(e -> getNewOutfit());
        saveWardrobeBtn.addActionListener(e -> saveWardrobe());

        stylizeSideNavBtns(returnMainBtn);
        stylizeSideNavBtns(addClothingBtn);
        stylizeSideNavBtns(getOutfitBtn);
        stylizeSideNavBtns(saveWardrobeBtn);

        addSideNavBtns(returnMainBtn,parent);
        addSideNavBtns(addClothingBtn,parent);
        addSideNavBtns(getOutfitBtn,parent);
        addSideNavBtns(saveWardrobeBtn,parent);
    }

    // EFFECTS: applies style to side navbar buttons
    private void stylizeSideNavBtns(JButton button) {
        button.setFont(new Font("Helvetica Neue", Font.BOLD, 16));
        button.setForeground(Color.decode(DARK_ACCENT_COLOUR));
        button.setMargin(new Insets(12,12,12,12));
    }

    // EFFECTS: adds each button to the parent panel (side menu)
    private void addSideNavBtns(JButton button, JPanel parent) {
        parent.add(button);
        parent.add(Box.createVerticalStrut(HEIGHT / 15));
    }

    // REQUIRES: returnMainBtn is clicked (this is its event handler)
    // EFFECTS: returns user to mainMenu by making wardrobeMenu invisible
    private void returnToMain() {
        wardrobeMenu.setVisible(false);
        mainMenu.setVisible(true);
    }

    // REQUIRES: addClothingBtn is clicked (this is its event handler)
    // MODIFIES: this.wardrobe
    // EFFECTS: prompts input dialog to open and uses user input to
    //          construct a Clothing instance and add it to this.wardrobe
    //          then displays updated wardrobe and refreshes wardrobeScreen
    // reference: Workout Tracker by keigol, https://github.com/keigol/CPSC210-project,
    //            MyProgramCard.addExerciseAction() method
    private void addClothingItem() {
        Optional<String[]> response = addClothingDialog();
        if (response.isPresent()) {
            String newId = response.get()[0];
            String newType = response.get()[1];
            String newColour = response.get()[2];
            String newFit = response.get()[3];
            String newMood = response.get()[4];
            String newDressCode = response.get()[5];
            Clothing newClothing = new Clothing(newId,newType,newColour,newFit,newMood,newDressCode);
            this.wardrobe.addClothing(newClothing);

            displayClothing();
            revalidate();
            repaint();
        }
    }

    // EFFECTS: creates custom dialog asking for input about the clothing item
    //          and passes user input back to the event handler
    // reference: Workout Tracker by keigol, https://github.com/keigol/CPSC210-project,
    //            MyProgramCard.addExerciseDialog() method
    private Optional<String[]> addClothingDialog() {
        JPanel addClothingPopup = new JPanel();
        addClothingPopup.setLayout(new GridLayout(0, 2));

        JTextField idInput;
        JComboBox typeInput;
        JTextField colourInput;
        JComboBox fitInput;
        JTextField moodInput;
        JComboBox dressCodeInput;

        idInput = initTextInputRow("ID (e.g, UBC sweater, Levi's)", addClothingPopup);
        typeInput = initTypeInput(addClothingPopup);
        colourInput = initTextInputRow("Primary colour", addClothingPopup);
        fitInput = initFitInput(addClothingPopup);
        moodInput = initTextInputRow("Mood (e.g, flirty, sporty, elegant)", addClothingPopup);
        dressCodeInput = initDressCodeInput(addClothingPopup);

        int result = JOptionPane.showConfirmDialog(this, addClothingPopup,
                "Add Clothing Item", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            return Optional.of(new String[]{idInput.getText(), (String) typeInput.getSelectedItem(),
                    colourInput.getText(), (String) fitInput.getSelectedItem(), moodInput.getText(),
                    (String) dressCodeInput.getSelectedItem()});
        }

        return Optional.empty();
    }

    // EFFECTS: abstracted method for creating a prompt and user input field together
    private JTextField initTextInputRow(String input, JPanel parent) {
        JLabel inputPrompt = new JLabel(input + ": ");
        inputPrompt.setHorizontalAlignment(JLabel.RIGHT);
        JTextField newInput = new JTextField();
        parent.add(inputPrompt);
        parent.add(newInput);
        return newInput;
    }

    // EFFECTS: creates prompt and dropdown menu for user to select clothing type
    private JComboBox initTypeInput(JPanel parent) {
        JLabel typePrompt = new JLabel("Type: ");
        typePrompt.setHorizontalAlignment(JLabel.RIGHT);
        String[] types = { "Top", "Bottom", "Jacket", "Accessory" };
        JComboBox typeDropdown = new JComboBox(types);
        parent.add(typePrompt);
        parent.add(typeDropdown);
        return typeDropdown;
    }

    // EFFECTS: creates prompt and dropdown menu for user to select clothing fit
    private JComboBox initFitInput(JPanel parent) {
        JLabel fitPrompt = new JLabel("Fit: ");
        fitPrompt.setHorizontalAlignment(JLabel.RIGHT);
        String[] fits = { "Tight", "Comfy", "Baggy" };
        JComboBox fitDropdown = new JComboBox(fits);
        parent.add(fitPrompt);
        parent.add(fitDropdown);
        return fitDropdown;
    }

    // EFFECTS: creates prompt and dropdown menu for user to select clothing dress code
    private JComboBox initDressCodeInput(JPanel parent) {
        JLabel dressCodePrompt = new JLabel("Dress code: ");
        dressCodePrompt.setHorizontalAlignment(JLabel.RIGHT);
        String[] codes = { "Casual", "Business Casual", "Formal", "Cocktail",
                "White Tie" };
        JComboBox dressCodeDropdown = new JComboBox(codes);
        parent.add(dressCodePrompt);
        parent.add(dressCodeDropdown);
        return dressCodeDropdown;
    }

    // MODIFIED: topsMap, bottomsMap, jacketsMap, accessoriesMap
    //           topsPanel, bottomsPanel, jacketsPanel, accessoriesPanel
    // EFFECTS: updates the wardrobeScreen by displaying the most recently
    //          added clothing item and maps each clothing item to its id
    private void displayClothing() {
        List<Clothing> clothesSoFar = this.wardrobe.getAllClothes();
        Clothing c = clothesSoFar.get(clothesSoFar.size() - 1);
        JPanel newPanel = createClothingPanel(c);

        if (c.getType().equals("Top") || c.getType().equals("top")) {
            topsPanel.add(newPanel);
            topsMap.put(c.getId(),newPanel);
            topsPanel.add(Box.createVerticalStrut(HEIGHT / 20));
        } else if (c.getType().equals("Bottom") || c.getType().equals("bottom")) {
            bottomsPanel.add(newPanel);
            bottomsMap.put(c.getId(),newPanel);
            bottomsPanel.add(Box.createVerticalStrut(HEIGHT / 15));
        } else if (c.getType().equals("Jacket") || c.getType().equals("jacket")) {
            jacketsPanel.add(newPanel);
            jacketsMap.put(c.getId(),newPanel);
            jacketsPanel.add(Box.createVerticalStrut(HEIGHT / 15));
        } else if (c.getType().equals("Accessory") || c.getType().equals("accessory")) {
            accessoriesPanel.add(newPanel);
            accessoriesMap.put(c.getId(),newPanel);
            accessoriesPanel.add(Box.createVerticalStrut(HEIGHT / 15));
        }
    }

    // EFFECTS: creates and styles a panel for each new clothing item
    private JPanel createClothingPanel(Clothing c) {
        JPanel clothingItemPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        clothingItemPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        clothingItemPanel.setPreferredSize(new Dimension(425, 350 / 8));

        JPanel itemInfo = itemInfo(c);
        JPanel itemRemove = itemDispose(c);

        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.weightx = 0.5;
        constraints.anchor = LINE_START;
        clothingItemPanel.add(itemInfo,constraints);
        constraints.gridwidth = 1;
        constraints.gridx = 2;
        constraints.anchor = FIRST_LINE_END;
        clothingItemPanel.add(itemRemove, constraints);
        return clothingItemPanel;
    }

    // EFFECTS: gets the fields and applies style for the information
    //          fields of each clothing item's panel
    private JPanel itemInfo(Clothing c) {
        String itemColour = "Colour: " + c.getColour();
        String itemFit = "     Fit: " + c.getFit();
        String itemMood = "     Mood: " + c.getMood();
        String itemDressCode = "     Dress Code: " + c.getDressCode();
        String description = itemColour + itemFit + itemMood + itemDressCode;

        JLabel itemTitle = new JLabel(c.getId());
        itemTitle.setFont(new Font("Helvetica Neue", Font.BOLD, 15));
        itemTitle.setHorizontalAlignment(JLabel.LEFT);
        JLabel itemDesc = new JLabel(description);
        itemDesc.setFont(new Font("Helvetica Neue", Font.ITALIC, 11));
        itemDesc.setHorizontalAlignment(JLabel.LEFT);

        JPanel itemInfo = new JPanel(new GridLayout(2,0));
        itemInfo.setPreferredSize(new Dimension(425 - 16, 350 / 8));
        itemInfo.add(itemTitle);
        itemInfo.add(itemDesc);

        return itemInfo;
    }

    // EFFECTS: renders and formats a small garbage bin icon for the remove clothing item
    //          feature and initializes action listeners for removal
    private JPanel itemDispose(Clothing c) {
        JButton itemRemove = new JButton("");
        itemRemove.setIcon(new ImageIcon("./data/trashbin.png"));
        itemRemove.setPreferredSize(new Dimension(16,16));
        itemRemove.addActionListener(e -> removeItem(c));

        JPanel result = new JPanel();
        result.setPreferredSize(new Dimension(16, 350 / 8));
        result.add(itemRemove);
        return result;
    }

    // REQUIRES: removeItem is clicked (this is its event handler)
    // EFFECTS: calls the appropriate event handler to remove the item from its
    //          correct panel
    private void removeItem(Clothing c) {
        if (topsMap.containsKey(c.getId())) {
            removeTop(c);
        } else if (bottomsMap.containsKey(c.getId())) {
            removeBottom(c);
        } else if (jacketsMap.containsKey(c.getId())) {
            removeJacket(c);
        } else if (accessoriesMap.containsKey(c.getId())) {
            removeAccessory(c);
        }

        this.wardrobe.removeClothing(c.getId());
    }

    // EFFECTS: removes clothing item from tops panel and refreshes screen
    private void removeTop(Clothing c) {
        JPanel panelToRemove = topsMap.get(c.getId());
        topsPanel.remove(panelToRemove);
        this.remove(panelToRemove);
        topsMap.remove(c.getId());
        validate();
        revalidate();
        repaint();
    }

    // EFFECTS: removes clothing item from bottoms panel and refreshes screen
    private void removeBottom(Clothing c) {
        JPanel panelToRemove = bottomsMap.get(c.getId());
        bottomsPanel.remove(panelToRemove);
        this.remove(panelToRemove);
        bottomsMap.remove(c.getId());
        validate();
        revalidate();
        repaint();
    }

    // EFFECTS: removes clothing item from jackets panel and refreshes screen
    private void removeJacket(Clothing c) {
        JPanel panelToRemove = jacketsMap.get(c.getId());
        jacketsPanel.remove(panelToRemove);
        this.remove(panelToRemove);
        jacketsMap.remove(c.getId());
        validate();
        revalidate();
        repaint();
    }

    // EFFECTS: removes clothing item from accessories panel and refreshes screen
    private void removeAccessory(Clothing c) {
        JPanel panelToRemove = accessoriesMap.get(c.getId());
        accessoriesPanel.remove(panelToRemove);
        this.remove(panelToRemove);
        accessoriesMap.remove(c.getId());
        validate();
        revalidate();
        repaint();
    }

    // REQUIRES: getOutfitBtn is clicked (this is its event handler)
    // EFFECTS: prompts input dialog to open and uses user input to
    //          filter through all clothing for matching items
    // reference: Workout Tracker by keigol, https://github.com/keigol/CPSC210-project,
    //            MyProgramCard.addExerciseAction() method
    private void getNewOutfit() {
        Optional<String[]> searchCriteria = filterOutfitDialog();

        if (searchCriteria.isPresent()) {
            String colourFilter = searchCriteria.get()[0];
            String moodFilter = searchCriteria.get()[1];
            String dressCodeFilter = searchCriteria.get()[2];

            List<Clothing> filtered = new ArrayList<>(this.wardrobe.getAllClothes());
            filtered = this.wardrobe.filterAll("colour",colourFilter,
                    filtered);
            filtered = this.wardrobe.filterAll("mood",moodFilter,
                    filtered);
            filtered = this.wardrobe.filterAll("dress code",dressCodeFilter,
                    filtered);

            displayRecommendations(filtered);
        }
    }

    // EFFECTS: creates popup dialog asking for input about the search criteria and
    //          passes user input back to the event handler
    // reference: Workout Tracker by keigol, https://github.com/keigol/CPSC210-project,
    //            MyProgramCard.addExerciseDialog() method
    private Optional<String[]> filterOutfitDialog() {
        JPanel filterClothingPopup = new JPanel();
        filterClothingPopup.setLayout(new GridLayout(0, 2));

        JTextField colourWant = initTextInputRow("What colour are we feeling?     ",
                filterClothingPopup);
        JTextField moodWant = initTextInputRow("What vibes do we want to give off?     ",
                filterClothingPopup);
        JTextField dressCodeWant = initTextInputRow("Now rein it in and give me a dress code.     ",
                filterClothingPopup);

        int result = JOptionPane.showConfirmDialog(this, filterClothingPopup,
                "Filter for Recommended Clothing Items", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            return Optional.of(new String[]{colourWant.getText(), moodWant.getText(),
                    dressCodeWant.getText()});
        }

        return Optional.empty();
    }

    // EFFECTS: creates a table, renders every filtered item (i.e, search matches) as a row
    //          and displays it in a message dialog with error handling if no matches occur
    // reference: FilmFlix by Kimia Rostin, https://github.com/kim1339/FilmFlix,
    //            FilmFlixGUI.setUpTableAndPane() and FilmFlixGUI.updateCatalogue() method
    private void displayRecommendations(List<Clothing> filtered) {
        DefaultTableModel tableModel = new DefaultTableModel(new String[] { "ID", "Type",
                "Colour", "Fit", "Mood", "Dress Code" }, 0);
        JTable recs = new JTable(tableModel);
        recs.setMinimumSize(new Dimension(WIDTH / 2,200));
        recs.setBorder(new EmptyBorder(15, 15, 15, 15));

        tableModel.setRowCount(0);
        if (!filtered.isEmpty()) {
            for (Clothing c : filtered) {
                tableModel.addRow(clothingToObjectArray(c));
            }

            JLabel recsIntro = new JLabel("Here are your recommendations, based"
                    + " off the search criteria you provided!");
            JPanel finalRecs = new JPanel(new GridLayout(2,0));
            finalRecs.add(recsIntro);
            finalRecs.add(recs);

            JOptionPane.showMessageDialog(this, finalRecs);
        } else {
            JLabel noMatches = new JLabel("Whoops... Nothing matches that search criteria."
                    + " Make sure you typed in the right keywords with the correct capitalization."
                    + " Or maybe hit the shops and buy yourself something nice?");
            JOptionPane.showMessageDialog(this, noMatches);
        }
    }

    // EFFECTS: converts Clothing type to Object array to be displayed in table
    // reference: FilmFlix by Kimia Rostin, https://github.com/kim1339/FilmFlix,
    //            FilmFlixGUI.filmToObjectArray() method
    public static Object[] clothingToObjectArray(Clothing c) {
        Object[] array = new Object[6];
        array[0] = c.getId();
        array[1] = c.getType();
        array[2] = c.getColour();
        array[3] = c.getFit();
        array[4] = c.getMood();
        array[5] = c.getDressCode();
        return array;
    }

    // REQUIRES: saveWardrobeBtn is clicked (this is its event handler)
    // EFFECTS: writes data to wardrobe.json file
    private void saveWardrobe() {
        try {
            jsonWriter.open();
            jsonWriter.write(this.wardrobe);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: initializes the wardrobeScreen (right panel) in the case where user
    //          does NOT load in data
    private JPanel initWardrobeScreen() {
        JPanel wardrobeScreen = new JPanel();
        wardrobeScreen.setLayout(new GridLayout(2,2));

        topsPanel = initClothesPanel("Tops");
        bottomsPanel = initClothesPanel("Bottoms");
        jacketsPanel = initClothesPanel("Jackets");
        accessoriesPanel = initClothesPanel("Accessories");

        wardrobeScreen.add(topsPanel);
        wardrobeScreen.add(bottomsPanel);
        wardrobeScreen.add(jacketsPanel);
        wardrobeScreen.add(accessoriesPanel);

        return wardrobeScreen;
    }

    // EFFECTS: abstracted method that sets up and renders a category panel
    private JPanel initClothesPanel(String title) {
        JPanel newPanel = new JPanel();
        newPanel.setPreferredSize(new Dimension(425, 350));
        newPanel.setBackground(Color.white);

        JLabel panelTitle = new JLabel(title);
        panelTitle.setFont(new Font("Helvetica Neue", Font.BOLD, 18));
        panelTitle.setForeground(Color.decode(DARK_ACCENT_COLOUR));
        newPanel.add(panelTitle);

        return newPanel;
    }
}
