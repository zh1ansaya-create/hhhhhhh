import java.util.Scanner;

/**
 * Проверка корректности введённых данных (пункты ТЗ 2 и 19).
 * Каждый метод повторяет запрос, пока пользователь не введёт правильное значение.
 */
public class Validation {
    private final Scanner scanner;

    public Validation(Scanner scanner) {
        this.scanner = scanner;
    }

    /** Читает строку; пустое значение — ошибка. */
    public String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = readLine().trim();
            if (line.isEmpty()) {
                System.out.println("[Ошибка] Значение не может быть пустым. Повторите ввод.");
            } else {
                return line;
            }
        }
    }

    /** Читает целое число из диапазона [min; max]. */
    public int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = readLine().trim();
            if (line.isEmpty()) {
                System.out.println("[Ошибка] Значение не может быть пустым. Повторите ввод.");
                continue;
            }
            try {
                int value = Integer.parseInt(line);
                if (value < min || value > max) {
                    System.out.println("[Ошибка] Введите число от " + min + " до " + max + ".");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("[Ошибка] \"" + line + "\" — недопустимое число. Повторите ввод.");
            }
        }
    }

    /** Читает дробное число из диапазона [min; max]. Допускается запятая как разделитель. */
    public double readDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String line = readLine().trim().replace(',', '.');
            if (line.isEmpty()) {
                System.out.println("[Ошибка] Значение не может быть пустым. Повторите ввод.");
                continue;
            }
            try {
                double value = Double.parseDouble(line);
                if (Double.isNaN(value) || Double.isInfinite(value) || value < min || value > max) {
                    System.out.println("[Ошибка] Введите число от " + min + " до " + max + ".");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("[Ошибка] \"" + line + "\" — недопустимое число. Повторите ввод.");
            }
        }
    }

    /** Вопрос «да/нет». */
    public boolean readYesNo(String prompt) {
        while (true) {
            String answer = readText(prompt + " (да/нет): ").toLowerCase();
            if (answer.equals("да") || answer.equals("д") || answer.equals("yes") || answer.equals("y")) {
                return true;
            } else if (answer.equals("нет") || answer.equals("н") || answer.equals("no") || answer.equals("n")) {
                return false;
            }
            System.out.println("[Ошибка] Ответьте «да» или «нет».");
        }
    }

    /** Ожидание Enter. */
    public void pause() {
        System.out.print("\nНажмите Enter для продолжения...");
        readLine();
    }

    private String readLine() {
        if (!scanner.hasNextLine()) {
            System.out.println("\nВвод завершён. Работа программы остановлена.");
            System.exit(0);
        }
        return scanner.nextLine();
    }
}
