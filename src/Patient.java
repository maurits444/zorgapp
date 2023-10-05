import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.List;

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

    public Patient(int id, String surname, String firstName, LocalDate dateOfBirth, float height, float weight, float lungsCapacity) {
        this.id = id;
        setSurname(surname);
        setFirstName(firstName);
        setDateOfBirth(dateOfBirth);
        setHeight(height);
        setWeight(weight);
        setLungsCapacity(lungsCapacity);
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

    public float getLungsCapacity() {
        return lungsCapacity;
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
        if (height > 0) {
            this.height = height;
        }
    }

    void setWeight(float weight) {
        if (weight > 0) {
            this.weight = weight;
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
            System.out.format("%-17s %.2f M\n", "Lengte:", height);
        } else {
            System.out.println("Lengte: Niet toegankelijk voor tandarts");
        }
        if (requestingUser.canAccessPatientWeight()) {
            System.out.format("%-17s %.2f kg\n", "Gewicht:", weight);
        } else {
            System.out.println("Gewicht: Niet toegankelijk voor tandarts");
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
            } } } }

//encapulation
// polyformisme
//imperatief programmeren
// false true