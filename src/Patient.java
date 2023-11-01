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
    private final int id;
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
        if (bmi < 18.5f) return "Underweight";
        if (bmi < 25f) return "Normal weight";
        if (bmi < 30f) return "Overweight";
        return "Obese";
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
            System.out.println("Error: Height must be between 0 and 250.");
        }
    }

    void setWeight(float weight) {
        if (weight > 0 && weight <= 250) {
            this.weight = weight;
        } else {
            System.out.println("Error: Weight must be between 0 and 250.");
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

    void addNewMedicine(String medicineName, String dose, String frequency) {
        Medicine medicine = new Medicine(medicineName, dose, frequency);
        this.medicines.add(medicine);
    }

    public void printMedicine() {
        System.out.println("List of medicines:");
        for (Medicine medicine : this.medicines) {
            System.out.println("Medicine: " + medicine.getName() + "\nDosage: " + medicine.getDose()
                    + "\nFrequency: " + medicine.getFrequency());
            System.out.println();
        }
    }

    public void addMedicine() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the name of the medicine:");
        String medicineName = scanner.nextLine();

        System.out.println("Enter the dosage:");
        String dose = scanner.nextLine();

        System.out.println("Enter the frequency and any additional notes:");
        String frequency = scanner.nextLine();

        addNewMedicine(medicineName, dose, frequency);
        System.out.println("New medicine added!");
    }

    void editMedicine(int index, String medicineName, String dose, String frequency, User requestingUser) {
        if (requestingUser.canEditMedicine()) {
            if (index >= 0 && index < medicines.size()) {
                Medicine medicine = medicines.get(index);
                medicine.setName(medicineName);
                medicine.setDose(dose);
                medicine.setFrequency(frequency);
                System.out.println("Medicine successfully edited.");
            } else {
                System.out.println("Invalid medicine index. Provide a valid index.");
            }
        } else {
            System.out.println("The user does not have permission to edit medicines.");
        }
    }

    public void editMedicineForPatient(User requestingUser) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select a medicine:");
        for (int i = 0; i < medicines.size(); i++) {
            System.out.println((i + 1) + ". " + medicines.get(i).getName());
        }

        int choice = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Choose a medicine to edit: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice > 0 && choice <= medicines.size()) {
                    validInput = true;
                } else {
                    System.out.println("Error, invalid input. Choose a number from the list.");
                }
            } else {
                System.out.println("Error, invalid input. Enter a number.");
                scanner.next();
            }
        }

        int medicationIndex = choice - 1;

        scanner.nextLine();

        System.out.print("Medicine Name: ");
        String medicineName = scanner.nextLine();

        System.out.print("Dosage: ");
        String dose = scanner.nextLine();

        System.out.print("Frequency: ");
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
        System.out.format("%-17s %s\n", "Surname:", surname);
        System.out.format("%-17s %s\n", "First Name:", firstName);
        System.out.format("%-17s %s\n", "Date of Birth",
                dateOfBirth.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        if (requestingUser.canAccessPatientLength()) {
            System.out.format("%-17s %.2f M%n", "Height:", height);
        } else {
            System.out.print("");
        }
        if (requestingUser.canAccessPatientWeight()) {
            System.out.format("%-17s %.2f kg%n", "Weight:", weight);
        } else {
            System.out.print("");
        }
        System.out.format("%-17s %.2f\n", "Body Mass Index:", getBMI());
        System.out.format("%-17s %s\n", "BMI Status:", getBMIStatus());
        System.out.format("%-17s %.2f L\n", "Lung Capacity:", getLungsCapacity());
        System.out.format("%-17s %d Years\n", "Age:", age);
        System.out.format("%-17s %.2f\n", "Body Mass Index:", getBMI());
        System.out.format("%-17s %s\n", "BMI Status:", getBMIStatus());
        System.out.format("%-17s %.2f L\n", "Lung Capacity:", getLungsCapacity());
    }

    public float getHeight(User requestingUser) {
        List<String> roles = requestingUser.getRoles();
        if (roles != null && roles.contains("DENTIST")) {
            System.out.println("Not accessible for a dentist.");
            return -1;
        }
        return height;
    }

    public float getWeight(User requestingUser) {
        List<String> roles = requestingUser.getRoles();
        if (roles != null && roles.contains("DENTIST")) {
            System.out.println("Not accessible for a dentist.");
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

