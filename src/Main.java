import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        String border = "-----------------------";
        System.out.println("Type a name for Trainer 1:");

        Scanner scanner = new Scanner(System.in);
        Trainer trainer1 = new Trainer(scanner.nextLine());

        System.out.println("Type a name for Trainer 2:");
        Trainer trainer2 = new Trainer(scanner.nextLine());

//        Trainer ben = new Trainer("Ben");
//        Trainer timur = new Trainer("Timur");

        Pokemon pikachu = new Pokemon("Pikachu", "Electricity",35, 55);
        Pokemon charizard = new Pokemon("Charizard", "Fire",78, 84);
        Pokemon bulbasaur = new Pokemon("Bulbasaur", "Grass",45, 49);
        Pokemon squirtle = new Pokemon("Squirtle", "Water",44, 48);
        Pokemon eevee = new Pokemon("Eevee", "Normal", 55,55);
        Pokemon jigglypuff = new Pokemon("Jigglypuff", "Normal",115, 45);

        ArrayList<Pokemon> pokemonArrayList = new ArrayList<>();
        pokemonArrayList.add(pikachu);
        pokemonArrayList.add(charizard);
        pokemonArrayList.add(bulbasaur);
        pokemonArrayList.add(squirtle);
        pokemonArrayList.add(eevee);
        pokemonArrayList.add(jigglypuff);


        System.out.println("Time to draft pokemons for your teams!");
        System.out.println("We will toss a coin for the first turn." + trainer1.getName_id() + " is heads and " + trainer2.getName_id() + " is tails.");
        System.out.println("Press any key to toss the coin.");
        scanner.nextLine();

        Random random = new Random();
        int randomNumber = random.nextInt(2)+1;

        // boolean that will be used to determine the turn. Trainer1 if it's true and Trainer2 otherwise.
        boolean turn = true;
        if (randomNumber == 1) {
            System.out.println("It's heads. So, " + trainer1.getName_id() + " gets to start the draft and the battle.");
        } else {
            System.out.println("It's tails. So, " + trainer2.getName_id() + " gets to start the draft and the battle.");
            turn = false;
        }

        while (!pokemonArrayList.isEmpty()) {
            if (turn) { //if turn is true, its trainer1's turn
                displayPokemonList(pokemonArrayList);
                System.out.println(trainer1.getName_id() + " pick your pokemon and type its number to add to your team:");
                int num = scanner.nextInt();
                Pokemon pokemonToAdd = pokemonArrayList.get(num-1);
                pokemonArrayList.remove(num-1);
                trainer1.addToTeam(pokemonToAdd);
                System.out.println("-> " + pokemonToAdd.getName() + " joins " + trainer1.getName_id() + "'s team.");

            } else {
                displayPokemonList(pokemonArrayList);
                System.out.println(trainer2.getName_id() + " pick your pokemon and type its number to add to your team:");
                int num = scanner.nextInt();
                Pokemon pokemonToAdd = pokemonArrayList.get(num-1);
                pokemonArrayList.remove(num-1);
                trainer2.addToTeam(pokemonToAdd);
                System.out.println("-> " + pokemonToAdd.getName() + " joins " + trainer2.getName_id() + "'s team.");

            }
            scanner.nextLine();
            turn = !turn; // it changes the value of boolean to opposite for the next turn.
        }
        System.out.println("Draft is over.");
        trainer1.getInventory().put("Regular Potion", new Potion("Regular", 3, 25));
        trainer2.getInventory().put("Regular Potion", new Potion("Regular", 3, 25));

        trainer1.displayTeam();
        trainer2.displayTeam();

        System.out.println("Time for battle.");

        while (!trainer1.isDefeated() && !trainer2.isDefeated()){
            Pokemon champion1 = trainer1.findPokemon();
            Pokemon champion2 = trainer2.findPokemon();

            if (turn) {
                Move move = new Move();
                System.out.println(border);
                System.out.println(trainer1.getName_id() + "'s turn -> " + champion1.getName() + ":");
                displayOptions();
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        int damage = move.giveDamage(champion1, champion2);
                        champion2.receiveDamage(damage, champion1.getName(), trainer2.getName_id());
                        break;
                    case 2:
                        champion1 = trainer1.swapPokemon(champion1, trainer1.getName_id());
                        break;
                    case 3:
                        trainer1.healPokemon(champion1);
                        break;
                    default:
                        System.out.println("Try again!");
                }
            } else {
                Move move = new Move();
                System.out.println(border);
                System.out.println(trainer2.getName_id() + "'s turn -> " + champion2.getName() + ":");
                displayOptions();
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        int damage = move.giveDamage(champion2, champion1);
                        champion1.receiveDamage(damage, champion2.getName(), trainer1.getName_id());
                        break;
                    case 2:
                        champion2 = trainer2.swapPokemon(champion2, trainer2.getName_id());
                        break;
                    case 3:
                        trainer2.healPokemon(champion2);
                        break;
                    default:
                        System.out.println("Try again!");
                }
            }
            turn = !turn;
        }
//        ben.setTeam(new Pokemon[]{pikachu, bulbasaur, squirtle});
//        timur.setTeam(new Pokemon[]{charizard, eevee, jigglypuff});
//
//
//        ben.displayTeam();
//        System.out.println();
//
//        timur.displayTeam();
//
//        System.out.println();
//        System.out.println("Battle starts now!");
//        boolean turn = true;
//        while (!ben.isDefeated() && !timur.isDefeated()){
//

//
//            if (turn) {
//                Move move = new Move();
//                Random random = new Random();
//                int randomNumber = random.nextInt(3) + 1;
//
//                if (randomNumber == 3){
//
//                }
//

//            } else {
//                Move move = new Move();
//                Random random = new Random();
//                int randomNumber = random.nextInt(3) + 1;
//
//                if (randomNumber == 3){
//
//                    champion_ben = ben.swapPokemon(champion_ben, ben.getName_id());
//                }
//
//                int damage = move.giveDamage(champion_timur, champion_ben);
//                champion_ben.receiveDamage(damage, champion_timur.getName(), ben.getName_id());
//            }
//            turn = !turn;
//        }


        if (trainer1.isDefeated()) {
            System.out.println(border);
            System.out.println(trainer2.getName_id() + "'s team wins!");
            System.out.println(border);

            trainer2.displayTeam();
        } else {
            System.out.println(border);

            System.out.println(trainer1.getName_id() + "'s team wins!");
            System.out.println(border);

            trainer1.displayTeam();
        }
        scanner.close();
    }

    public static void displayPokemonList(ArrayList<Pokemon> pokemonArray) {
        int i = 1;
        for (Pokemon pokemon: pokemonArray) {
            System.out.print(i + ". ");
            pokemon.displayInfo();
            i++;
        }
    }

    public static void displayOptions(){
        System.out.println("1. Attack.");
        System.out.println("2. Swap.");
        System.out.println("3. Heal.");
    }
}