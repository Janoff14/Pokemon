import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Pokemon {
    private String name;
    private int health;
    private int maxhealth;
    private int attack;

    private String type;
    private ArrayList<Move> moves_obj = new ArrayList<>();

    public String[] getMoves() {
        return moves;
    }

    private String[] moves = new String[4];
    // Constructor
    public Pokemon(String name, String type, int health, int attack) {
        this.name = name;
        this.health = health;
        this.maxhealth = health;
        this.attack = attack;
        this.type = type; // Initialize type

    }

    public ArrayList<Move> getSpecialMoves(){
        return moves_obj;
    }
    public void addMoveObj(Move move){
        this.moves_obj.add(move);
    }
    public Pokemon(String[] pokemonData){
        this.name = pokemonData[0];
        this.health = Integer.parseInt(pokemonData[1]);
        this.attack = Integer.parseInt(pokemonData[2]);
        this.type = pokemonData[3];
        this.moves[0] = pokemonData[4];
        this.moves[1] = pokemonData[5];
        this.moves[2] = pokemonData[6];
        this.moves[3] = pokemonData[7];
        // Initialize type
    }

    // Accessor methods
    public String getName() {
        return name;
    }
    public int getHealth() {
        return health;
    }
    public int getAttack() {
        return attack;
    }
    // Mutator methods
    public void setName(String name) {
        this.name = name;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public void setAttack(int attack) {
        this.attack = attack;
    }


    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    // Method to perform a tackle attack
    public void useTackle(Pokemon opponent) {
        System.out.println(this.name + " uses Tackle on " + opponent.getName());
        opponent.receiveDamage(attack, "", "");
    }
    // Method to receive damage
    public void receiveDamage(int damage, String attacker_name, String trainer_name) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
        if (this.health == 0) {
            System.out.println(this.name + " receives " + damage + " damage from " + attacker_name + ".");
            System.out.println(this.name + " from " + trainer_name + "'s team is dead!");
            return;
        }
        System.out.println(this.name + " receives " + damage + " damage from " + attacker_name + ". Health is now: " + this.health);
    }

    //called by heal method from Trainer class. uses healValue to add to this.health. maxHealth is used to see the max capacity for health
    public void recoverHP(int healValue){
        int gain = healValue;
        if (this.health + healValue > this.maxhealth) {
            gain = this.maxhealth - this.health;
            this.health = this.maxhealth;
        } else {
            this.health += healValue;
        }
        System.out.println("+" + gain + " to " + this.name + " through Potion." + " Health is now: " + this.health);

    }
    // Method to display Pokémon's information
    public void displayInfo() {
        System.out.println("| Name: " + name + ", Health: " + health + ", Attack: " +
                attack + " |");
    }


}
