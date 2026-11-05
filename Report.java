import java.util.ArrayList;

/**
 * Формирование и вывод итогового отчёта (ТЗ 25).
 * Накапливает результаты всех модулей, которые запускал пользователь.
 */
public class Report {
    private final ArrayList<String> entries = new ArrayList<>();
    private boolean orderChecked = false;
    private boolean problemFound = false;

    /** Добавляет запись о принятом решении. Если problem = true, итог по заказу будет «требуется проверка». */
    public void add(String module, String result, boolean problem) {
        entries.add(module + ": " + result);
        if (problem) {
            problemFound = true;
        }
    }

    public void add(String module, String result) {
        add(module, result, false);
    }

    /** Отмечает, что пользователь проверял заказ (комплексный заказ / безопасность). */
    public void markOrderChecked() {
        orderChecked = true;
    }

    public void print(String user, String status) {
        System.out.println();
        System.out.println("==================================================");
        System.out.println("   ИТОГОВЫЙ ОТЧЁТ СИСТЕМЫ SMART DECISION");
        System.out.println("==================================================");
        System.out.println("Клиент: " + user + " (" + status + ")");
        System.out.println("--------------------------------------------------");

        if (entries.isEmpty()) {
            System.out.println("Вы ещё не запускали ни одного модуля — отчёт пуст.");
        } else {
            for (int i = 0; i < entries.size(); i++) {
                System.out.println((i + 1) + ". " + entries.get(i));
            }
        }

        System.out.println("--------------------------------------------------");
        System.out.print("ИТОГОВОЕ РЕШЕНИЕ: ");
        if (entries.isEmpty()) {
            System.out.println("данных для решения недостаточно.");
        } else if (!orderChecked) {
            System.out.println("консультация завершена, заказ не оформлялся.");
        } else if (problemFound) {
            System.out.println("ЗАКАЗ ТРЕБУЕТ ПРОВЕРКИ оператором перед оформлением.");
        } else {
            System.out.println("ЗАКАЗ ОДОБРЕН — можно оформлять.");
        }
        System.out.println("==================================================");
    }
}
