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
    private static final int VIEW_CONSULTANT_RATES = 4; // Voeg dit toe aan de andere constants
    private static final int ADD_MEDICINE = 5;
    private static final int EDIT_MEDICINE = 6;
    private static final int STOP = 7;




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

        System.out.println("Beschikbare patiënten:");
        for (Patient patient : patients) {
            System.out.println(patient.getId() + ". " + patient.getFirstName() + " " + patient.getSurname());
        }
        currentPatient = selectCurrentPatientByName();
        System.out.format("Huidige patiënt: [%d] %s %s\n", requestingUser.getUserId(), currentPatient.getFirstName(), currentPatient.getSurname());
    }
    private Patient searchPatientByName(String firstName, String lastName) {
        for (Patient patient : patients) {
            if (patient.getFirstName().equalsIgnoreCase(firstName) && patient.getSurname().equalsIgnoreCase(lastName)) {
                return patient;
            }
        }
        return null;
    }

    private Patient selectCurrentPatientByName() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Voer de voornaam in van de patiënt:");
            String firstName = scanner.next();

            System.out.println("Voer de achternaam in van de patiënt:");
            String lastName = scanner.next();

            Patient foundPatient = searchPatientByName(firstName, lastName);

            if (foundPatient != null) {
                return foundPatient;
            } else {
                System.out.println("Patiënt niet gevonden. Probeer opnieuw.");
            }
        }
    }

    public void viewConsultantRates() {
        if (requestingUser instanceof TandartsUser) {
            TandartsUser tandartsUser = (TandartsUser) requestingUser;

            System.out.println("==== Tandarts Consultprijzen:========================");
            System.out.println("Routine controle - €" + tandartsUser.getConsultRate("Routine controle"));
            System.out.println("Extractie - €" + tandartsUser.getConsultRate("Extractie"));
            System.out.println("Fluoridebehandeling - €" + tandartsUser.getConsultRate("Fluoridebehandeling"));
            System.out.println("Wortelkanaalbehandeling - €" + tandartsUser.getConsultRate("Wortelkanaalbehandeling"));
            System.out.println("Implantaat - €" + tandartsUser.getConsultRate("Implantaat"));
        } else if (requestingUser instanceof FysioUser) {
            FysioUser fysioUser = (FysioUser) requestingUser;

            System.out.println("==== Huisarts Consultprijzen:========================");
            System.out.println("Standaard behandeling - €" + fysioUser.getConsultRate("Standaard behandeling"));
            System.out.println("Tapen en bandageren - €" + fysioUser.getConsultRate("Tapen en bandageren"));
            System.out.println("Mobilisatie - €" + fysioUser.getConsultRate("Mobilisatie"));
            System.out.println("Massage - €" + fysioUser.getConsultRate("Massage"));
            System.out.println("Manuele therapie - €" + fysioUser.getConsultRate("Manuele therapie"));
            System.out.println("Dry Needling - €" + fysioUser.getConsultRate("Dry Needling"));
            System.out.println("Gebruik van oefenbad - €" + fysioUser.getConsultRate("Gebruik van oefenbad"));
        } else if (requestingUser instanceof HuisartsUser) {
            HuisartsUser huisartsUser = (HuisartsUser) requestingUser;

            System.out.println("==== Fysio Consultprijzen:========================");
            System.out.println("Consult - €" + huisartsUser.getConsultRate("Consult"));
            System.out.println("Huisbezoek - €" + huisartsUser.getConsultRate("Huisbezoek"));
            System.out.println("Gezondheidsonderzoek - €" + huisartsUser.getConsultRate("Gezondheidsonderzoek"));
        } else {
            System.out.println("Access denied. User is not authorized to view consultant rates.");
        }
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
                System.out.format("%d:  Bekijk consulttarieven\n", VIEW_CONSULTANT_RATES); // Dit is toegevoegd
                if (requestingUser.canEditMedicijnen()) {
                    System.out.format("%d:  Een recept voorschrijven\n", ADD_MEDICINE);
                } else {
                    System.out.print("");
                }
                if (requestingUser.canEditMedicijnen()) {
                    System.out.format("%d:  Bewerk de medicijnen\n", EDIT_MEDICINE);
                } else {
                    System.out.print("");
                }

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
                        currentPatient = selectCurrentPatientByName();
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
