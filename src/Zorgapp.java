import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Zorgapp {

    public static void main(String[] args) {
        List<User> users = new ArrayList<>();

        users.add(new HuisartsUser(1, "Huisarts",
                Arrays.asList("Bart Betermaker", "Henk Hulper", "Pieter Paracetemol")));
        users.add(new FysioUser(2, "Fysiotherapeut",
                Arrays.asList("Vincent Voeten", "Bas Blessure", "Gerrie Gewricht")));
        users.add(new ApotheekUser(3, "Apotheker",
                Arrays.asList("Mert Medicijnen", "Peter Pijnstiller", "Zara Zorgzaam")));
        users.add(new TandartsUser(4, "Tandarts",
                Arrays.asList("Tina Tandenbleker", "Karel Kiespijn", "Barry Beugel")));

        User selectedUser = selectUser(users);

        String selectedRole = selectRole(selectedUser);
        System.out.println("Huidige Gebruiker: " + selectedRole);

        Administration administration = new Administration(selectedUser);
        administration.menu();
    }

    // Role selection
    public static String selectRole(User user) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Kies een gebruiker voor " + user.getUserName() + ":");

            for (int i = 0; i < user.getRoles().size(); i++) {
                System.out.println((i + 1) + ". " + user.getRoles().get(i));
            }

            int choice = scanner.nextInt();

            if (choice > 0 && choice <= user.getRoles().size()) {
                return user.getRoles().get(choice - 1);
            } else {
                System.out.println("Error, probeer opnieuw. Kies een cijfer uit de lijst");
            }
        }
    }

    // User selection
    public static User selectUser(List<User> users) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Kies een gebruiker:");

            for (int i = 0; i < users.size(); i++) {
                System.out.println((i + 1) + ". " + users.get(i).getUserName());
            }

            int choice = scanner.nextInt();

            if (choice > 0 && choice <= users.size()) {
                return users.get(choice - 1);
            } else {
                System.out.println("Error, probeer opnieuw. Kies een cijfer uit de lijst");
            }
        }
    }
}
