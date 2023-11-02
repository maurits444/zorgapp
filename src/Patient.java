import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Patient {
    // Constants for properties
    private static final int RETURN = 0;
    private static final int SURNAME = 1;
    private static final int FIRSTNAME = 2;
    private static final int DATEOFBIRTH = 3;
    private static final int HEIGHT = 4;
    private static final int WEIGHT = 5;
    private static final int LUNGSCAPACITY = 6;

    // Properties
    private final int id; //encapsulation
    private String surname;
    private String firstName;
    private LocalDate dateOfBirth;
    private int age;
    private float height;
    private float weight;
    private float lungsCapacity;
    private final List<Medicine> medicines;

    // Constructor
    public Patient(int id, String surname, String firstName, LocalDate dateOfBirth,
                   float height, float weight, float lungsCapacity) {
        this.id = id;
        setSurname(surname);
        setFirstName(firstName);
        setDateOfBirth(dateOfBirth);
        setHeight(height);
        setWeight(weight);
        setLungsCapacity(lungsCapacity);
        this.medicines = createDefaultMedicine();
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

    public float getLungsCapacity() {
        return lungsCapacity;
    }

    public float getBMI() {
        return weight / (height * height);
    }

    String getBMIStatus() {
        float bmi = getBMI();
        if (bmi < 18.5f) return "Ondergewicht";
        if (bmi < 25f) return "Gezond gewicht";
        if (bmi < 30f) return "Overgewicht";
        return "Obese";
    }

    // Setters with validation
    void setSurname(String surname) { //imperatief (selectie)
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

    void setWeight(float weight) { //polymorphism (override)
        if (weight > 0 && weight <= 250) {
            this.weight = weight;
        } else {
            System.out.println("Error: Gewicht moet tussen de 0 en 250 vallen.");
        }
    }

    void setLungsCapacity(float lungsCapacity) {
        if (lungsCapacity > 0) {
            this.lungsCapacity = lungsCapacity;
        }
    }

    // Medicine
    private List<Medicine> createDefaultMedicine() {
        List<Medicine> defaultMedicine = new ArrayList<>();

        defaultMedicine.add(new Medicine("Ibuprofen", "200mg", "3 keer per dag \n"));
        defaultMedicine.add(new Medicine("Omeprazol", "20mg", "1 keer per dag \n"));
        defaultMedicine.add(new Medicine("Loratadine", "10mg", "2 keer per dag \n"));
        defaultMedicine.add(new Medicine("Amoxicilline", "500mg", "3 keer per dag \n"));

        return defaultMedicine;
    }

    void addNewMedicine(String medicineName, String dose, String frequency) { //abstraction en imperatief (bevat stappen)
        Medicine medicine = new Medicine(medicineName, dose, frequency);
        this.medicines.add(medicine);
    }

    public void printMedicine() { //iteratie (for loop)
        System.out.println("Medicijnenlijst:");
        for (Medicine medicine : this.medicines) {
            System.out.println("Medicijn: " + medicine.getName() + "\nDosering: " + medicine.getDose()
                    + "\nFrequentie: " + medicine.getFrequency());
            System.out.println();
        }
    }

    public void addMedicine() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Voer de naam van het medicijn in:");
        String medicineName = scanner.nextLine();

        System.out.println("Voer de dosering in:");
        String dose = scanner.nextLine();

        System.out.println("Voer de frequentie in en een eventuele bijsluiter:");
        String frequency = scanner.nextLine();

        addNewMedicine(medicineName, dose, frequency);
        System.out.println("Gelukt!");
    }

    void editMedicine(int index, String medicineName, String dose, String frequency, User requestingUser) {
        if (requestingUser.canEditMedicine()) {
            if (index >= 0 && index < medicines.size()) {
                Medicine medicine = medicines.get(index);
                medicine.setName(medicineName);
                medicine.setDose(dose);
                medicine.setFrequency(frequency);
                System.out.println("Medicijn bewerkt!");
            } else {
                System.out.println("Error: Verkeerde input. Voer een geldige index in.");
            }
        } else {
            System.out.println("De gebruiker heeft geen permissies om medicijnen te bewerken.");
        }
    }

    public void editMedicineForPatient(User requestingUser) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Selecteer een medicijn:");
        for (int i = 0; i < medicines.size(); i++) {
            System.out.println((i + 1) + ". " + medicines.get(i).getName());
        }

        int choice = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Kies een medicijn om te bewerken: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice > 0 && choice <= medicines.size()) {
                    validInput = true;
                } else {
                    System.out.println("Error, verkeerde input. Kies een index uit de lijst.");
                }
            } else {
                System.out.println("Error, verkeerde input. U kunt alleen een index selecteren.");
                scanner.next();
            }
        }

        int medicationIndex = choice - 1;

        scanner.nextLine();

        System.out.print("Medicijn naam: ");
        String medicineName = scanner.nextLine();

        System.out.print("Dosering: ");
        String dose = scanner.nextLine();

        System.out.print("Frequentie: ");
        String frequency = scanner.nextLine();

        editMedicine(medicationIndex, medicineName, dose, frequency, requestingUser);
    }

    // Other methods for patient data handling
    private int calcAge(LocalDate dob) {
        LocalDate today = LocalDate.now();
        return Period.between(dob, today).getYears();
    }

    String fullName() {
        return firstName + " " + surname;
    }

    void viewData(User requestingUser) {
        System.out.format("===== Patient id=%d ==============================\n", id);
        System.out.format("%-17s %s\n", "Achternaam:", surname);
        System.out.format("%-17s %s\n", "Voornaam:", firstName);
        System.out.format("%-17s %s\n", "Geboortedatum",
                dateOfBirth.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
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
        System.out.format("%-17s %.2f L\n", "Longcapaciteit:", getLungsCapacity());
        System.out.format("%-17s %d Years\n", "Leeftijd:", age);
    }

    void editData() { //polymorphism en imperatief lussen (kan korter)
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.format("===== Edit de data van een patient =%d ==============================\n", id);
            System.out.println("[0] Terug");
            System.out.println("[1] Achternaam (" + surname + ")");
            System.out.println("[2] Voornaam (" + firstName + ")");
            System.out.println("[3] Geboortedatum " +
                    "(" + dateOfBirth.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + ")");
            System.out.println("[4] Lengte (" + height + "M)");
            System.out.println("[5] Gewicht (" + weight + "kg)");
            System.out.println("[6] Longinhoud (" + lungsCapacity + "L)");

            int propertyId = scanner.nextInt();

            if (propertyId < 0 || propertyId > 6) {
                System.out.println("Error: Voer aub een getal in tussen 0 en 6.");
                continue;
            }

            if (propertyId == RETURN) {
                break;
            }

            System.out.println("Vul de nieuwe gegevens in :");
            scanner.nextLine();
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
                            System.out.println
                                    ("Error: Geboortedatum mag niet in de toekomst liggen. Probeer het opnieuw.");
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
            }
        }
    }
}

