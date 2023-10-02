import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class zorgapp {
    public static void main(String[] args) {
        List<User> users = new ArrayList<>();

        users.add(new User(1, "Huisarts"));
        users.add(new User(2, "Fysiotherapeut"));
        users.add(new User(3, "Apotheek"));
        users.add(new User(4, "Tandarts"));

        User selectedUser = selectUser(users);

        Administration administration = new Administration(selectedUser);
        administration.menu();
    }

    public static User selectUser(List<User> users) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Kies een gebruiker:");

            // Toon alle gebruikers
            for (int i = 0; i < users.size(); i++) {
                System.out.println((i + 1) + ". " + users.get(i).getUserName());
            }

            int choice = scanner.nextInt();

            if (choice > 0 && choice <= users.size()) {
                return users.get(choice - 1); // -1 omdat lijsten 0-gebaseerd zijn
            } else {
                System.out.println("Error, probeer opnieuw. Kies een cijfer uit de lijst");
            }
        }
    } }
