import java.util.ArrayList;
import java.util.HashMap;
public class Trainer {
    private String name_id;
    private ArrayList<Pokemon> team = new ArrayList<>();

    public HashMap<String, Item> getInventory() {
        return inventory;
    }

    public void addPotionToInventory(int quantity, int healValue){
        Potion potion = new Potion("Regular", quantity, healValue);
        inventory.put("Regular Potion", potion);
        Potion regularPotion = (Potion) inventory.get("Regular Potion");
        System.out.println("Added " + regularPotion.getQuantity() + " potions with healing power of " + regularPotion.getHealValue() + ".");
    }

    public void healPokemon(Pokemon pokemon){
            Potion regularPotion = (Potion) getInventory().get("Regular Potion");
            pokemon.recoverHP(regularPotion.getHealValue());
    }
    public Trainer(String name){
        this.name_id = name;
    }

    private HashMap<String, Item> inventory = new HashMap<>();


    public void displayTeam(){
        System.out.println(name_id + "'s team:");
        String border = "+------------------------------------------+";

        System.out.println(border);
        for(Pokemon p: team){
            p.displayInfo();
        }
        System.out.println(border);

    }
    public Pokemon findPokemon(){
        for (Pokemon pokemon: team) {
            if (pokemon.getHealth() > 0){
                return pokemon;
            }
        }
        return null;
    }

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

    public boolean isDefeated(){
        for (Pokemon p: team) {
            if (p.getHealth() != 0){
                return false;
            }
        }
        System.out.println(1);
        return true;
    }

    public void addToTeam(Pokemon pokemon){
        this.team.add(pokemon);
    }

    public String getName_id() {
        return name_id;
    }


}
