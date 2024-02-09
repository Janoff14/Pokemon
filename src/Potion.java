public class Potion extends Item{
    private final int healValue;

    public int getHealValue() {
        return healValue;
    }

    public Potion(String name, int quantity, int healValue) {
        super(name, quantity);
        this.healValue = healValue;
    }
}
