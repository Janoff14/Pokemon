import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.util.Objects;

public class Trainer {
    private String name_id;

    public ArrayList<Pokemon> getTeam() {
        return team;
    }

    private ArrayList<Pokemon> team = new ArrayList<>();

    public HashMap<String, Item> getInventory() {
        return inventory;
    }

    //adds Regular potion to inventory when provided with quantity and value for healing
    public void addPotionToInventory(int quantity, int healValue){
        Potion potion = new Potion("Regular", quantity, healValue);
        inventory.put("Regular Potion", potion);
        Potion regularPotion = (Potion) inventory.get("Regular Potion");
        System.out.println("Added " + regularPotion.getQuantity() + " potions with healing power of " + regularPotion.getHealValue() + ".");
    }

    //uses recoverHP method from Pokemon class to apply it to the given pokemon. Potion value is passed to recoverHP
    public void healPokemon(Pokemon pokemon){
            Potion regularPotion = (Potion) getInventory().get("Regular Potion");
            pokemon.recoverHP(regularPotion.getHealValue());
    }
    public Trainer(String name){
        this.name_id = name;
    }

    private HashMap<String, Item> inventory = new HashMap<>();


    //displays all pokemons in the team of the instance of a trainer, with some formatting for better UX?UI
    public void displayTeam(){
        System.out.println(name_id + "'s team:");
        String border = "+------------------------------------------+";

        System.out.println(border);
        for(Pokemon p: team){
            p.displayInfo();
        }
        System.out.println(border);

    }
    //needed to determine starting pokemons and swapping
    public Pokemon findPokemon(){
        for (Pokemon pokemon: team) {
            if (pokemon.getHealth() > 0){
                return pokemon;
            }
        }
        return null;
    }
    //swaps current pokemon to the next alive pokemon and returns it
    public Pokemon swapPokemon(Pokemon pokemon, String trainer){
        for (Pokemon next_pokemon: team) {
            if (next_pokemon.getHealth() > 0 && next_pokemon != pokemon){
                System.out.println("|||SWAP|||" + trainer + " is swapping " + pokemon.getName() + " for " + next_pokemon.getName() + "|");
                return next_pokemon;
            }
        }
        System.out.println("Swap attempt unsuccessful! Everyone other pokemon is defeated.");
        return pokemon;
    }

    //iterates over a team of pokemons. if someone is alive, returns false, otherwise true
    public boolean isDefeated(){
        for (Pokemon p: team) {
            if (p.getHealth() != 0){
                System.out.println(p.getHealth());
                return false;
            }
        }
        System.out.println("vlalalalalala");
        return true;
    }

    public void addToTeam(Pokemon pokemon){
        this.team.add(pokemon);
    }

    public String getName_id() {
        return name_id;
    }


    public void createTeam(String teamFile){
        try (BufferedReader reader = new BufferedReader(new FileReader(teamFile))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null){
                String[] pokemonData = line.split(",");
                Pokemon pokemon = new Pokemon(pokemonData);
                team.add(pokemon);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<Pokemon> passTeam(){
        return team;
    }
    public void assignMoveToPokemon(ArrayList<Move> allMoves){
        for (Pokemon pokemon: team){
            for (String move_name: pokemon.getMoves()){
                for (Move move_obj: allMoves){
                    if (Objects.equals(move_name, move_obj.getName())){
                        pokemon.addMoveObj(move_obj);
                    }
                }
            }
        }
    }
}
