public class Move {
    private String name;
    private String type;
    private int power;
    double accuracy;

    public Move(String name, String type, int power, double accuracy){
        this.accuracy = accuracy;
        this.type = type;
        this.power = power;
        this.name = name;
    }

    public Move(){}

    public int giveSpecialDamage(Pokemon target){
        double effectiveness = typeEffectiveness(this.type, target.getType());
        return (int)(this.power*(this.accuracy/100)*effectiveness);
    }

    //returns total attack value
    public int giveDamage(Pokemon attacker, Pokemon target){

        double effectiveness = typeEffectiveness(attacker.getType(), target.getType());
        return (int)((attacker.getAttack()) * effectiveness);
    }

    //returns type effectiveness multiplier for attack
    public double typeEffectiveness(String attackerType, String targetType) {
        String[] types = {"Normal", "Fire", "Grass", "Water"};
        int targetNum = 0;
        for (int i = 0; i < types.length; i++) {
            if (targetType.equals(types[i])) {
                targetNum = i;
                break;
            }
        }

        double effect = 1.0;
        if (attackerType.equals("Normal")) {
            return 1.0;
        }
        if (attackerType.equals("Fire")) {
            switch (targetNum) {
                case 2:
                    effect = 2.0;
                    break;
                case 3:
                    effect = 0.5;
                    break;
                default:
                    effect = 1.0;
                    break;
            }
        }

        if (attackerType.equals("Grass")) {
            switch (targetNum) {
                case 1:
                    effect = 0.5;
                    break;
                case 3:
                    effect = 2.0;
                    break;
                default:
                    effect = 1.0;
                    break;
            }
        }

        if (attackerType.equals("Water")) {
            switch (targetNum) {
                case 2:
                    effect = 0.5;
                    break;
                case 1:
                    effect = 2.0;
                    break;
                default:
                    effect = 1.0;
                    break;
            }
        }
        return effect;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }



}
