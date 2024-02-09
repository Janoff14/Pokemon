public class Pokemon {
    private String name;
    private int health;
    private int maxhealth;
    private int attack;

    private String type;
    // Constructor
    public Pokemon(String name, String type, int health, int attack) {
        this.name = name;
        this.health = health;
        this.maxhealth = health;
        this.attack = attack;
        this.type = type; // Initialize type

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

    public void recoverHP(int healValue){
        int gain = healValue;
        if (this.health + healValue > this.maxhealth) {
            gain = this.maxhealth - this.health;
            this.health = this.maxhealth;
        } else {
            this.health += healValue;
        }
        System.out.println("+" + gain + " to " + this.name + "." + " Health is now: " + this.health);

    }
    // Method to display Pokémon's information
    public void displayInfo() {
        System.out.println("| Name: " + name + ", Health: " + health + ", Attack: " +
                attack + " |");
    }
}
