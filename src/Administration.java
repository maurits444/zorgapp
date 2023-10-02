import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

class Administration {
    private static final int STOP = 0;
    private static final int VIEW = 1;
    private static final int EDIT = 2;
    private static final int SWITCH = 3;
    private Patient currentPatient;
    private User currentUser;
    private List<Patient> patients = new ArrayList<>();

    /**
     * Constructor
     */
    public Administration(User user) {
        currentUser = user;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        patients.add(new Patient(1, "Huisman", "Michiel", LocalDate.parse("30-01-2001", formatter), 1.7f, 88.0f, 5.0f));
        patients.add(new Patient(2, "Bos", "Henk", LocalDate.parse("15-04-1999", formatter), 1.9f, 90.0f, 6.0f));
        patients.add(new Patient(3, "Beek", "Jan", LocalDate.parse("16-06-1990", formatter), 1.6f, 65.0f, 7.0f));
        patients.add(new Patient(4, "Boersma", "Johan", LocalDate.parse("21-02-1995", formatter), 1.7f, 75.0f, 4.5f));
        patients.add(new Patient(5, "Tukker", "Tom", LocalDate.parse("25-08-2003", formatter), 1.8f, 85.0f, 4.8f));


        currentPatient = selectCurrentPatient();
        System.out.format("Huidige patient: [%d] %s\n", user.getUserId(), currentUser.getUserName());
    }
    private Patient selectCurrentPatient() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Kies een patient:");
            for (int i = 0; i < patients.size(); i++) {
                System.out.println((i + 1) + ". " + patients.get(i).getFirstName() + " " + patients.get(i).getSurname());
            }

            int choice = scanner.nextInt();
            if (choice > 0 && choice <= patients.size()) {
                return patients.get(choice - 1);
            } else {
                System.out.println("Error, probeer opnieuw.");
            }
        }
    }

    void menu() {
        Scanner scanner = new Scanner(System.in);  // User input via this scanner.

        boolean nextCycle = true;
        while (nextCycle) {
            System.out.format("%s\n", "=".repeat(80));
            System.out.format("Huidige patient: %s\n", currentPatient.fullName());


            // Print menu on screen
            System.out.format("%d:  STOP\n", STOP);
            System.out.format("%d:  Bekijk de patient gegevens\n", VIEW);
            System.out.format("%d:  Edit de patient gegevens\n", EDIT);
            System.out.format("%d:  Terug\n", SWITCH);

            System.out.print("Kies een optie: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case STOP: // stop loop
                    nextCycle = false;
                    break;
                case VIEW:
                    currentPatient.viewData();
                    break;
                case EDIT:
                    currentPatient.editData();
                    break;
                case SWITCH:
                    currentPatient = selectCurrentPatient();
                    break;
                default:
                    System.out.println("Error, probeer opnieuw. Kies een cijfer uit de lijst.");
                    break;
            }
        }
    }
}
