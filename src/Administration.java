import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.util.Map;


class Administration {
    private static final int SWITCH = 0;
    private static final int VIEW = 1;
    private static final int EDIT = 2;
    private static final int VIEW_MEDICINES = 3;
    private static final int ADD_MEDICINE = 4;
    private static final int EDIT_MEDICINE = 5;
    private static final int STOP = 6;
    private static final int VIEW_CONSULTANT_RATES = 7; // Voeg dit toe aan de andere constants



    public Patient currentPatient;
    public User requestingUser;
    public List<Patient> patients = new ArrayList<>();


    /**
     * Constructor
     */
    public Administration(User requestingUser) {
        this.requestingUser = requestingUser;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        patients.add(new Patient(1, "Huisman", "Michiel",
                LocalDate.parse("30-01-2001", formatter), 1.7f, 88.0f, 5.0f));
        patients.add(new Patient(2, "Bos", "Henk",
                LocalDate.parse("15-04-1999", formatter), 1.9f, 90.0f, 6.0f));
        patients.add(new Patient(3, "Beek", "Jan",
                LocalDate.parse("16-06-1990", formatter), 1.6f, 65.0f, 7.0f));
        patients.add(new Patient(4, "Boersma", "Johan",
                LocalDate.parse("21-02-1995", formatter), 1.7f, 75.0f, 4.5f));
        patients.add(new Patient(5, "Tukker", "Tom",
                LocalDate.parse("25-08-2003", formatter), 1.8f, 85.0f, 4.8f));


        currentPatient = selectCurrentPatient();
        System.out.format("Huidige patient: [%d] %s\n", requestingUser.getUserId(), requestingUser.getUserName());
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
    public void viewConsultantRates() {
        if (requestingUser != null) {
            if (requestingUser instanceof TandartsUser) {
                TandartsUser TandartsUser = (TandartsUser) requestingUser;

                System.out.println("Tandarts Consultprijzen:");
                System.out.println("Routine controle - €" + TandartsUser.getConsultRate("Routine controle"));
                System.out.println("Extractie - €" + TandartsUser.getConsultRate("Extractie"));
                System.out.println("Fluoridebehandeling - €" + TandartsUser.getConsultRate("Fluoridebehandeling"));
                System.out.println("Wortelkanaalbehandeling - €" + TandartsUser.getConsultRate("Wortelkanaalbehandeling"));
                System.out.println("Implantaat - €" + TandartsUser.getConsultRate("Implantaat"));
            } else {
                System.out.print("");
            } }
    }

        public void menu() {
            Scanner scanner = new Scanner(System.in);  // User input via this scanner.

            boolean nextCycle = true;
            while (nextCycle) {
                System.out.format("%s\n", "=".repeat(80));
                System.out.format("Huidige patient: %s\n", currentPatient.fullName());

                // Print menu on screen
                System.out.format("%d:  Terug\n", SWITCH);
                System.out.format("%d:  Patient gegevens\n", VIEW);
                System.out.format("%d:  Bewerk de patient gegevens\n", EDIT);
                System.out.format("%d:  Medicijnenlijst\n", VIEW_MEDICINES);
                if (requestingUser.canEditMedicijnen()) {
                    System.out.format("%d:  Voeg een medicijn toe\n", ADD_MEDICINE);
                } else {
                    System.out.print("");
                }
                if (requestingUser.canEditMedicijnen()) {
                    System.out.format("%d:  Bewerk de medicijnen\n", EDIT_MEDICINE);
                } else {
                    System.out.print("");
                }
                System.out.format("%d:  Bekijk consulttarieven\n", VIEW_CONSULTANT_RATES); // Dit is toegevoegd

                System.out.print("Kies een optie: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case STOP: // stop loop
                        nextCycle = false;
                        break;
                    case VIEW:
                        currentPatient.viewData(requestingUser);
                        break;
                    case EDIT:
                        currentPatient.editData(requestingUser);
                        break;
                    case SWITCH:
                        currentPatient = selectCurrentPatient();
                        break;
                    case VIEW_MEDICINES:
                        currentPatient.printMedicijnen();
                        break;
                    case ADD_MEDICINE:
                        currentPatient.voegNieuwMedicijnToe();
                        break;
                    case EDIT_MEDICINE:
                        currentPatient.editMedicijnForPatient(requestingUser);
                        break;
                    case VIEW_CONSULTANT_RATES:
                        viewConsultantRates(); // Dit is toegevoegd
                        break;
                    default:
                        System.out.println("Error, probeer opnieuw. Kies een cijfer uit de lijst.");
                        break;
                }
            }
        }
    }
