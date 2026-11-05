import java.util.Scanner;

/**
 * Точка входа: SmartDecision — система принятия решений для онлайн-магазина электроники.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu(scanner);
        menu.start();
        scanner.close();
    }
}
