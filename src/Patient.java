import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//... any other imports


public class Patient {
    private static final int RETURN = 0;
    private static final int SURNAME = 1;
    private static final int FIRSTNAME = 2;
    private static final int DATEOFBIRTH = 3;
    private static final int HEIGHT = 4;
    private static final int WEIGHT = 5;
    private static final int LUNGSCAPACITY = 6;

    private int id;
    private String surname;
    private String firstName;
    private LocalDate dateOfBirth;
    private int age;
    private float height;
    private float weight;
    private float lungsCapacity;
    private List<Medicijn> medicijnen;

    public Patient(int id, String surname, String firstName, LocalDate dateOfBirth, float height, float weight, float lungsCapacity) {
        this.id = id;
        setSurname(surname);
        setFirstName(firstName);
        setDateOfBirth(dateOfBirth);
        setHeight(height);
        setWeight(weight);
        setLungsCapacity(lungsCapacity);
        this.medicijnen = createDefaultMedicijnen();
    }


    private int calcAge(LocalDate dob) {
        LocalDate today = LocalDate.now();
        return Period.between(dob, today).getYears();
    }

    public String fullName() {
        return firstName + " " + surname;
    }

    // Getters
    int getId() {
        return id;
    }

    String getSurname() {
        return surname;
    }

    String getFirstName() {
        return firstName;
    }

    LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public int getAge() {
        return age;
    }
    public float getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public float getLungsCapacity() {
        return lungsCapacity;
    }

    private List<Medicijn> createDefaultMedicijnen() {
        List<Medicijn> defaultMedicijnen = new ArrayList<>();

        defaultMedicijnen.add(new Medicijn("Ibuprofen", "200mg", "3 keer per dag \nVoorraad: 14 stuks"));
        defaultMedicijnen.add(new Medicijn("Omeprazol", "20mg", "1 keer per dag \nVoorraad: 29 stuks"));
        defaultMedicijnen.add(new Medicijn("Loratadine", "10mg", "2 keer per dag \nVoorraad: 43 stuks"));
        defaultMedicijnen.add(new Medicijn("Amoxicilline", "500mg", "3 keer per dag \nVoorraad: 56 stuks"));

        return defaultMedicijnen;
    }

    // Setters with validation
    void setSurname(String surname) {
        if (surname != null && !surname.trim().isEmpty()) {
            this.surname = surname;
        }
    }

    void setFirstName(String firstName) {
        if (firstName != null && !firstName.trim().isEmpty()) {
            this.firstName = firstName;
        }
    }

    void setDateOfBirth(LocalDate dob) {
        if (dob != null && !dob.isAfter(LocalDate.now())) {
            this.dateOfBirth = dob;
            this.age = calcAge(dob);
        }
    }

    void setHeight(float height) {
        if (height > 0 && height <= 250) {
            this.height = height;
        } else {
            System.out.println("Error: Lengte moet tussen de 0 en 250 vallen.");
        }
    }

    void setWeight(float weight) {
        if (weight > 0 && weight <=250) {
            this.weight = weight;
        } else {
            System.out.println("Error: Gewicht moet tussen de 0 en 250 vallen.");
        }
    }

    float getBMI() {
        return weight / (height * height);
    }

    void setLungsCapacity(float lungsCapacity) {
        if (lungsCapacity > 0) {
            this.lungsCapacity = lungsCapacity;
        }
    }

    public List<Medicijn> getMedicijnen() {
        return medicijnen;
    }

    void addNewMedicijn(String medicijnnaam, String dosering, String frequentie) {
        Medicijn medicijn = new Medicijn(medicijnnaam, dosering, frequentie);
        this.medicijnen.add(medicijn);
    }


    public void printMedicijnen() {
        System.out.println("Lijst van medicijnen:");
        for (Medicijn medicijn : this.medicijnen) {
            System.out.println("Medicijn: " + medicijn.getName() + " \nDosering: " + medicijn.getDose() + " \nFrequentie: " + medicijn.getFrequency());
        System.out.println(); // Voeg een extra witregel toe aan het einde, voor nette opmaak
    } }
    public void voegNieuwMedicijnToe() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Voer de naam van het medicijn in:");
        String medicijnnaam = scanner.nextLine();

        System.out.println("Voer de dosering in:");
        String dosering = scanner.nextLine();

        System.out.println("Voer de frequentie in:");
        String frequentie = scanner.nextLine();

        addNewMedicijn(medicijnnaam, dosering, frequentie);
        System.out.println("Nieuw medicijn toegevoegd!");
    }

    String getBMIStatus() {
        float bmi = getBMI();
        if (bmi < 18.5f) return "Ondergewicht";
        if (bmi < 25f) return "Normaal gewicht";
        if (bmi < 30f) return "Overgewicht";
        return "Obese";
    }

    void viewData(User requestingUser) {
        System.out.format("===== Patient id=%d ==============================\n", id);
        System.out.format("%-17s %s\n", "Achternaam:", surname);
        System.out.format("%-17s %s\n", "Voornaam:", firstName);
        System.out.format("%-17s %s\n", "Geboortedatum:", dateOfBirth.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        if (requestingUser.canAccessPatientLength()) {
            System.out.format("%-17s %.2f M%n", "Lengte:", height);
        } else {
            System.out.print("");
        }
        if (requestingUser.canAccessPatientWeight()) {
            System.out.format("%-17s %.2f kg%n", "Gewicht:", weight);
        } else {
            System.out.print("");
        }
        System.out.format("%-17s %.2f\n", "Body Mass Index:", getBMI());
        System.out.format("%-17s %s\n", "BMI Status:", getBMIStatus());
        System.out.format("%-17s %.2f L\n", "Longinhoud:", getLungsCapacity());
        System.out.format("%-17s %d Jaar\n", "Leeftijd:", age);
        System.out.format("%-17s %.2f\n", "Body Mass Index:", getBMI());
        System.out.format("%-17s %s\n", "BMI Status:", getBMIStatus());
        System.out.format("%-17s %.2f L\n", "Longinhoud:", getLungsCapacity());
    }
    public float getHeight(User requestingUser) {
        List<String> roles = requestingUser.getRoles();
        if (roles != null && roles.contains("TANDARTS")) {
            System.out.println("Niet toegankelijk voor tandarts.");
            return -1;
        }
        return height;
    }

    public float getWeight(User requestingUser) {
        List<String> roles = requestingUser.getRoles();
        if (roles != null && roles.contains("TANDARTS")) {
            System.out.println("Niet toegankelijk voor tandarts.");
            return -1;
        }
        return weight;
    }

    void editData(User requestingUser) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.format("===== Edit de data van een patient =%d ==============================\n", id);
            System.out.println("[0] Terug");
            System.out.println("[1] Achternaam (" + surname + ")");
            System.out.println("[2] Voornaam (" + firstName + ")");
            System.out.println("[3] Geboortedatum (" + dateOfBirth.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + ")");
            System.out.println("[4] Lengte (" + height + "M)");
            System.out.println("[5] Gewicht (" + weight + "kg)");
            System.out.println("[6] Longinhoud (" + lungsCapacity + "L)");

            int propertyId = scanner.nextInt();

            if (propertyId < 0 || propertyId > 6) {
                System.out.println("Error: Voer aub een getal in tussen 0 en 6.");
                continue; // Skip the rest of the loop iteration and start over
            }

            if (propertyId == RETURN) {
                break;
            }

            System.out.println("Vul de nieuwe gegevens in :");
            scanner.nextLine(); // Consume newline
            String text = scanner.nextLine();

            switch (propertyId) {
                case SURNAME:
                    setSurname(text);
                    break;
                case FIRSTNAME:
                    setFirstName(text);
                    break;
                case DATEOFBIRTH:
                    try {
                        LocalDate enteredDate = LocalDate.parse(text, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                        if (enteredDate.isAfter(LocalDate.now())) {
                            System.out.println("Error: Geboortedatum mag niet in de toekomst liggen. Probeer het opnieuw.");
                        } else {
                            setDateOfBirth(enteredDate);
                            System.out.println("Geboortedatum succesvol verandert.");
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Error, Gebruik format dd-MM-yyyy. Klik eerst opnieuw op 3.");
                    }
                    break;
                case HEIGHT:
                    try {
                        height = Float.parseFloat(text.replace(",", "."));
                    } catch (Exception e) {
                        System.out.println("Voer aub een geldig nummer in. Klik eerst opnieuw op 4.");
                    }
                    break;
                case WEIGHT:
                    try {
                        weight = Float.parseFloat(text.replace(",", "."));
                    } catch (Exception e) {
                        System.out.println("Voer een geldig nummer in. Klik eerst opnieuw op 5.");
                    }
                    break;
                case LUNGSCAPACITY:
                    try {
                        lungsCapacity = Float.parseFloat(text.replace(",", "."));
                    } catch (Exception e) {
                        System.out.println("Voer een geldig nummer in. KLik eerst opnieuw op 6.");
                    }
                    break;
                default:
                    System.out.println("Incorrect cijfer. Kies een cijfer uit de lijst.");
                    scanner.close();
            } } }

    public void editMedicijn(int index, String medicijnnaam, String dosering, String frequentie, User requestingUser) {
        if (requestingUser.canEditMedicijnen()) {
            if (index >= 0 && index < medicijnen.size()) {
                Medicijn medicijn = medicijnen.get(index);
                medicijn.setName(medicijnnaam);
                medicijn.setDose(dosering);
                medicijn.setFrequency(frequentie);
                System.out.println("Medicijn succesvol bewerkt.");
            } else {
                System.out.println("Ongeldige medicijnindex. Geef een geldige index op.");
            }
        } else {
            System.out.println("De gebruiker heeft geen toestemming om medicijnen te bewerken.");
        }
    }

    public void editMedicijnForPatient(User requestingUser) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Selecteer een medicijn:");
        for (int i = 0; i < medicijnen.size(); i++) {
            System.out.println((i + 1) + ". " + medicijnen.get(i).getName());
        }

        int choice = scanner.nextInt();
        if (choice > 0 && choice <= medicijnen.size()) {
            int medicationIndex = choice - 1;
            System.out.println("Voer de nieuwe gegevens in:");

            System.out.print("Medicijnnaam: ");
            String medicijnnaam = scanner.next();

            System.out.print("Dosering: ");
            String dosering = scanner.next();

            System.out.print("Frequentie: ");
            String frequentie = scanner.next();

            editMedicijn(medicationIndex, medicijnnaam, dosering, frequentie, requestingUser);
        } else {
            System.out.println("Error, verkeerde invoer. Probeer opnieuw.");
        }
    }

    public void editMedicijnForPatient() {
    }
}