public class Medicine {
    private String name;
    private String dose;
    private String frequency;

    public Medicine(String name, String dose, String frequency) {
        this.name = name;
        this.dose = dose;
        this.frequency = frequency;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getDose() {
        return dose;
    }

    public String getFrequency() {
        return frequency;
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
