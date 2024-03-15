import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class PokeWindow extends JFrame {
    private JPanel panelMain;

    private JButton attackButton = new JButton("Attack");
    private JButton healButton = new JButton("Heal");
    private JButton swapButton = new JButton("Swap");
    private JButton spMove1 = new JButton("SP");
    private JButton spMove2 = new JButton("SP");
    private JButton spMove3 = new JButton("SP");
    private JButton spMove4 = new JButton("SP");

    private Trainer compTrainer;
    private Trainer userTrainer;
    private Pokemon playerCurrentPokemon;
    private Pokemon computerCurrentPokemon;
    private JTextArea leftSquad;
    private JTextArea rightSquad;

    private JLabel playerCurrentPokemonLabel;
    private JLabel compCurrentPokemonLabel;
    public PokeWindow(){
        setTitle("Pokemon Battle");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel for squads
        JPanel topPanel = new JPanel(new BorderLayout());
        leftSquad = new JTextArea("Team 1");
        leftSquad.setEditable(false);
        rightSquad = new JTextArea("Team 2");
        rightSquad.setEditable(false);
        topPanel.add(leftSquad, BorderLayout.WEST);
        topPanel.add(rightSquad, BorderLayout.EAST);

        // Middle panel for Pokémon images and names
        JPanel middlePanel = new JPanel(new GridLayout(1, 2));

        JPanel leftPokemonPanel = new JPanel(new BorderLayout());
        playerCurrentPokemonLabel = new JLabel("Pikachu");
        leftPokemonPanel.add(playerCurrentPokemonLabel, BorderLayout.NORTH);
        // Add Pokémon image label here in CENTER
        JPanel rightPokemonPanel = new JPanel(new BorderLayout());
        compCurrentPokemonLabel = new JLabel("Charizard");
        rightPokemonPanel.add(compCurrentPokemonLabel, BorderLayout.NORTH);
        // Add Pokémon image label here in CENTER
        middlePanel.add(leftPokemonPanel);
        middlePanel.add(rightPokemonPanel);
        Font textAreaFont = new Font("SansSerif", Font.PLAIN, 15);

        leftSquad.setPreferredSize(new Dimension(350, 100));
        rightSquad.setPreferredSize(new Dimension(350, 100));
        leftSquad.setFont(textAreaFont);
        rightSquad.setFont(textAreaFont);
        // Bottom panel for action buttons
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel actionButtonsPanel = new JPanel(new GridLayout(1, 3));
        actionButtonsPanel.add(attackButton);
        actionButtonsPanel.add(healButton);
        actionButtonsPanel.add(swapButton);
        JPanel specialMovesPanel = new JPanel(new GridLayout(1, 4));
        specialMovesPanel.add(spMove1);
        specialMovesPanel.add(spMove2);
        specialMovesPanel.add(spMove3);
        specialMovesPanel.add(spMove4);
        bottomPanel.add(actionButtonsPanel, BorderLayout.NORTH);
        bottomPanel.add(specialMovesPanel, BorderLayout.SOUTH);

        // Adding panels to the frame
        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);


        userTrainer = new Trainer("Sanjar");
        compTrainer = new Trainer("Computer");

        userTrainer.createTeam("team1.csv");
        compTrainer.createTeam("team2.csv");
        userTrainer.getInventory().put("Regular Potion", new Potion("Regular", 3, 25));
        compTrainer.getInventory().put("Regular Potion", new Potion("Regular", 3, 25));

        ArrayList<Pokemon> team1 = userTrainer.passTeam();
        ArrayList<Pokemon> team2 = compTrainer.passTeam();


        StringBuilder compTeamStringBuilder = new StringBuilder();
        for (Pokemon pokemon : team2) {
            compTeamStringBuilder.append(pokemon.getName()).append(", HP: ").append(pokemon.getHealth())
                    .append(", Attack: ").append(pokemon.getAttack())
                    .append(", Type: ").append(pokemon.getType())
                    .append("\n");
        }
        StringBuilder userTeamStringBuilder = new StringBuilder();
        for (Pokemon pokemon : team1) {
            userTeamStringBuilder.append(pokemon.getName()).append(", HP: ").append(pokemon.getHealth())
                    .append(", Attack: ").append(pokemon.getAttack())
                    .append(", Type: ").append(pokemon.getType())
                    .append("\n");
        }


        leftSquad.setText(userTeamStringBuilder.toString());
        rightSquad.setText(compTeamStringBuilder.toString());
        panelMain = new JPanel(new BorderLayout()); // Initialize panelMain with BorderLayout
        panelMain.add(topPanel, BorderLayout.NORTH);
        panelMain.add(middlePanel, BorderLayout.CENTER);
        panelMain.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(panelMain); // Use panelMain as the content pane
        playerCurrentPokemon = userTrainer.findPokemon();
        computerCurrentPokemon = compTrainer.findPokemon();
        initializeGame();
        updatePokemonsText();
        setVisible(true);

        initializeButtonActions();

    }

    public static void main(String[] args){

        SwingUtilities.invokeLater(PokeWindow::new);

    }


    private void initializeGame(){
        playerCurrentPokemon = userTrainer.findPokemon();
        computerCurrentPokemon = compTrainer.findPokemon();
        ArrayList<Move> allMoves = new ArrayList<>();
        try {
            populateMoves(allMoves);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userTrainer.assignMoveToPokemon(allMoves);
        compTrainer.assignMoveToPokemon(allMoves);

    }

    private void initializeButtonActions(){
        attackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performUserAttack();

            }
        });

        healButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performHeal();
            }
        });
        swapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSwap();
            }
        });

        spMove1.addActionListener(e -> performSpecialMove(0));
        spMove2.addActionListener(e -> performSpecialMove(1));
        spMove3.addActionListener(e -> performSpecialMove(2));
        spMove4.addActionListener(e -> performSpecialMove(3));
    }

    private void performUserAttack(){
        checkForWin();

        Move move = new Move(); // This should be replaced with the actual move the Pokémon will use
        int damage = move.giveDamage(playerCurrentPokemon, computerCurrentPokemon);
        computerCurrentPokemon.receiveDamage(damage, playerCurrentPokemon.getName(), compTrainer.getName_id());

        JOptionPane.showMessageDialog(this, "Your pokemon " +
                playerCurrentPokemon.getName() + " attacked " + computerCurrentPokemon.getName() +
                        " and dealt " + damage + " damage!",
                "Special Move Used",
                JOptionPane.INFORMATION_MESSAGE);

        System.out.println("sdsd");
        if (computerCurrentPokemon.getHealth() == 0){
            computerCurrentPokemon = compTrainer.findPokemon();
        }
        updatePokemonsText();

        checkForWin();

        performComputerTurn();
    }
    private void performComputerTurn() {
        checkForWin();

        // Simple AI for computer's turn: randomly choose to attack
        Random random = new Random();
        int action = random.nextInt(4) + 1; // Randomly choose an action

        // Let's assume the computer will always attack for now
        Move move = new Move(); // This should be replaced with the actual move the Pokémon will use
        int damage = move.giveDamage(computerCurrentPokemon, playerCurrentPokemon);
        playerCurrentPokemon.receiveDamage(damage, computerCurrentPokemon.getName(), userTrainer.getName_id());

        JOptionPane.showMessageDialog(this,
                computerCurrentPokemon.getName() + " attacked your pokemon " + playerCurrentPokemon.getName() +
                        " and dealt " + damage + " damage!",
                "Special Move Used",
                JOptionPane.INFORMATION_MESSAGE);

        if (playerCurrentPokemon.getHealth()==0){
            playerCurrentPokemon = userTrainer.findPokemon();
        }
        // Update the GUI to reflect the damage taken
        checkForWin();
        updatePokemonsText();
    }
    private void updatePokemonsText(){
        StringBuilder compTeamStringBuilder = new StringBuilder();
        for (Pokemon pokemon : compTrainer.getTeam()) {
            compTeamStringBuilder.append(pokemon.getName()).append(", HP: ").append(pokemon.getHealth())
                    .append(", Attack: ").append(pokemon.getAttack())
                    .append(", Type: ").append(pokemon.getType())
                    .append("\n");
        }
        StringBuilder userTeamStringBuilder = new StringBuilder();
        for (Pokemon pokemon : userTrainer.getTeam()) {
            userTeamStringBuilder.append(pokemon.getName()).append(", HP: ").append(pokemon.getHealth())
                    .append(", Attack: ").append(pokemon.getAttack())
                    .append(", Type: ").append(pokemon.getType())
                    .append("\n");
        }
        leftSquad.setText(userTeamStringBuilder.toString());
        rightSquad.setText(compTeamStringBuilder.toString());

        playerCurrentPokemonLabel.setText(playerCurrentPokemon.getName());
        compCurrentPokemonLabel.setText(computerCurrentPokemon.getName());
        Font labelFont = new Font("Arial", Font.BOLD, 20); // Choose the desired font and size

        playerCurrentPokemonLabel.setFont(labelFont);
        compCurrentPokemonLabel.setFont(labelFont);
        playerCurrentPokemonLabel.setPreferredSize(new Dimension(100, 50)); // Width, Height
        compCurrentPokemonLabel.setPreferredSize(new Dimension(100, 50));
        setupMoveButtons(playerCurrentPokemon);
        // When initializing or after a swap, call the method like this:
        updatePokemonImageLabel(playerCurrentPokemonLabel, playerCurrentPokemon.getName());
        updatePokemonImageLabel(compCurrentPokemonLabel, computerCurrentPokemon.getName());
        adjustLabelSize(playerCurrentPokemonLabel);
        adjustLabelSize(compCurrentPokemonLabel);
        panelMain.validate();
        panelMain.repaint();
    }

    private void checkForWin() {
        if (userTrainer.isDefeated()) {
            // Trainer 1 is defeated, show a dialog that Trainer 2 wins
            System.out.println("sdfffffffffffffffff");
            JOptionPane.showMessageDialog(this, "All of Trainer 1's Pokémon have fainted!\nTrainer 2 wins!",
                    "Game Over", JOptionPane.INFORMATION_MESSAGE);
            endGame();
        } else if (compTrainer.isDefeated()) {
            System.out.println("sdfffffffffffffffff");
            // Trainer 2 is defeated, show a dialog that Trainer 1 wins
            JOptionPane.showMessageDialog(this, "All of Trainer 2's Pokémon have fainted!\nTrainer 1 wins!",
                    "Game Over", JOptionPane.INFORMATION_MESSAGE);
            endGame();
        }
    }

    private void endGame() {
        attackButton.setEnabled(false);
        healButton.setEnabled(false);
        swapButton.setEnabled(false);
        spMove1.setEnabled(false);
        spMove2.setEnabled(false);
        spMove3.setEnabled(false);
        spMove4.setEnabled(false);

    }
    private void performHeal() {
        if (playerCurrentPokemon != null) {
            userTrainer.healPokemon(playerCurrentPokemon);// Heal the player's current Pokémon
            updatePokemonsText(); // Update the GUI to reflect the new health


            performComputerTurn();
        }
    }
    public static void populateMoves(ArrayList<Move> allMoves) throws IOException {  // populate moves and store them in array and  as Move objects

        String word;
        String moveName;
        int movePower;
        String moveType;
        double moveAccuracy;
        FileInputStream file = null;
        Scanner inFS = null;

        file = new FileInputStream("moves.csv");
        inFS = new Scanner(file);
        word = inFS.nextLine();

        while (inFS.hasNextLine()) {
            word = inFS.nextLine();
            String[] wordArr = word.split(",");
            moveName = wordArr[0];
            movePower = Integer.parseInt(wordArr[1]);
            moveType = wordArr[2];
            moveAccuracy = Double.parseDouble(wordArr[3]);

            Move move = new Move(moveName,moveType,movePower,moveAccuracy);

            allMoves.add(move);


        }

        inFS.close();

    }

    private void setupMoveButtons(Pokemon pokemon) {
        JButton[] moveButtons = {spMove1, spMove2, spMove3, spMove4};
        for (int i = 0; i < moveButtons.length; i++) {
            if (i < pokemon.getSpecialMoves().size()) {
                Move move = pokemon.getSpecialMoves().get(i);
                moveButtons[i].setText(move.getName());
                moveButtons[i].setEnabled(true);
            } else {
                moveButtons[i].setText("N/A");
                moveButtons[i].setEnabled(false);
            }
        }
    }

    private void performSwap() {
        String[] availablePokemons = userTrainer.getTeam().stream()
                .filter(p -> p.getHealth() > 0)
                .map(Pokemon::getName)
                .toArray(String[]::new);
        String chosenPokemon = (String) JOptionPane.showInputDialog(
                this,
                "Choose a Pokémon to swap:",
                "Swap Pokémon",
                JOptionPane.QUESTION_MESSAGE,
                null,
                availablePokemons,
                availablePokemons[0]);

        // Find the chosen Pokémon object by name
        for (Pokemon pokemon : userTrainer.getTeam()) {
            if (pokemon.getName().equals(chosenPokemon)) {
                playerCurrentPokemon = pokemon;
                break;
            }
        }

        // Update the GUI to reflect the current Pokémon
        updatePokemonsText();

        // Now it's the computer's turn to make a move
        performComputerTurn();
    }

    private void performSpecialMove(int moveIndex) {
        // Get the special move from the player's current Pokémon
        if (playerCurrentPokemon.getSpecialMoves().size() > moveIndex) {
            Move specialMove = playerCurrentPokemon.getSpecialMoves().get(moveIndex);

            // Perform the special move on the opponent's current Pokémon
            int damage = specialMove.giveSpecialDamage(computerCurrentPokemon);
            computerCurrentPokemon.receiveDamage(damage, playerCurrentPokemon.getName(), compTrainer.getName_id());

            // Show the dialog with the move performed and the damage
            JOptionPane.showMessageDialog(this,
                    playerCurrentPokemon.getName() + " used " + specialMove.getName() +
                            " and dealt " + damage + " damage!",
                    "Special Move Used",
                    JOptionPane.INFORMATION_MESSAGE);

            updatePokemonsText(); // Update the GUI to reflect the damage

            // Check if the opponent's Pokémon is defeated to switch
            if (computerCurrentPokemon.getHealth() <= 0) {
                computerCurrentPokemon = compTrainer.findPokemon(); // Find the next available Pokémon
            }

            performComputerTurn(); // The computer takes its turn
            checkForWin(); // Check if the game is over
        } else {
            // If the move index is invalid (no move available), inform the user
            JOptionPane.showMessageDialog(this,
                    "No special move available!",
                    "Move Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    private void updatePokemonImageLabel(JLabel label, String pokemonName) {
        String imagePath;
        switch (pokemonName) {
            case "Pikachu":
                imagePath = "pikachu.png";
                break;
            case "Charizard":
                imagePath = "charizard.png";
                break;
            case "Bulbasaur":
                imagePath = "bulbasaur.png";
                break;
            case "Squirtle":
                imagePath = "squirtle.png";
                break;
            case "Eevee":
                imagePath = "eevee.png";
                break;
            case "Jigglypuff":
                imagePath = "jigglypuff.png";
                break;
            // Add additional cases for each Pokémon
            default:
                // Default image or a placeholder if Pokémon is not recognized
                imagePath = "jigglypuff.png";
                break;
        }
        // Update the label's icon
        ImageIcon icon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
        label.setIcon(icon);
    }

    private void adjustLabelSize(JLabel label) {
        ImageIcon icon = (ImageIcon) label.getIcon();
        if (icon != null) {
            int width = icon.getIconWidth();
            int height = icon.getIconHeight();
            Dimension newSize = new Dimension(width, height);
            label.setPreferredSize(newSize);
            label.setMinimumSize(newSize);
            label.setMaximumSize(newSize);
        }
    }
}
