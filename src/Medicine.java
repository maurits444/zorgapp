public class Medicine {
    private String name;
    private String dose;
    private String frequency;
    public String stock;

    public Medicine(String name, String dose, String frequency, String stock) {
        this.name = name;
        this.dose = dose;
        this.frequency = frequency;
        this.stock = stock;
    }

    // Getters
    public String getName() {
        return name;
    }
    public String getDose() { return dose; }
    public String getFrequency() {
        return frequency;
    }
    public String getStock() { return stock; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setDose(String dose) {
        this.dose = dose;
    }
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    public void setStock(String stock) { this.stock = stock; }
}
