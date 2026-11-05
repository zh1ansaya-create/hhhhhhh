import java.util.Scanner;

/**
 * Отображение меню и обработка выбора пользователя (ТЗ 1, 16, 25).
 */
public class Menu {
    private final Validation in;
    private final DecisionSystem system = new DecisionSystem();
    private final Report report = new Report();

    private String user = "";
    private int statusCode = DecisionSystem.ACTIVE;
    private int level = 1;          // 1 — обычный, 2 — Silver, 3 — Gold
    private int age = 25;           // уточняется в разделе «Профиль»

    public Menu(Scanner scanner) {
        this.in = new Validation(scanner);
    }

    // ===================== Запуск =====================

    public void start() {
        printHeader();
        if (!login()) {
            return;
        }
        mainLoop();
    }

    private void printHeader() {
        System.out.println("==================================================");
        System.out.println("   SMART DECISION — ОНЛАЙН-МАГАЗИН ЭЛЕКТРОНИКИ");
        System.out.println("==================================================");
        System.out.println("Система поддержки решений: статус клиента, цены,");
        System.out.println("скидки, рассрочка, безопасность и рекомендации.");
        System.out.println("--------------------------------------------------");
        System.out.println("Тестовые аккаунты: ivan/1234 (активный),");
        System.out.println("anna/5678 (неактивный), petr/0000 (заблокирован)");
        System.out.println("==================================================");
    }

    // ===================== Вход (ТЗ 3, 5, 9) =====================

    private boolean login() {
        final int maxAttempts = 3;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            String login = in.readText("Логин: ");
            String password = in.readText("Пароль: ");
            int code = system.authenticate(login, password);

            if (code == -1) {
                System.out.println("[Предупреждение] Неверный логин или пароль. Попытка "
                        + attempt + " из " + maxAttempts + ".");
                continue;
            }

            user = login;
            statusCode = code;
            String status = system.userStatus(statusCode);
            System.out.println("\nДобро пожаловать, " + user + "! Статус аккаунта: " + status);

            boolean blocked = (statusCode == DecisionSystem.BLOCKED);
            if (!system.canUseSystem(blocked)) {
                System.out.println("[Доступ закрыт] Ваш аккаунт заблокирован. Обратитесь в поддержку.");
                return false;
            }
            if (statusCode == DecisionSystem.INACTIVE) {
                System.out.println("[Внимание] Аккаунт неактивен: оформление заказов недоступно до активации.");
            }
            return true;
        }
        System.out.println("[Доступ закрыт] Превышено число попыток входа.");
        return false;
    }

    // ===================== Главное меню (ТЗ 1, 16) =====================

    private void mainLoop() {
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = in.readInt("Выберите пункт (0–9): ", 0, 9);

            if (choice != 0 && choice != 9) {
                System.out.println("\n--- " + system.sectionName(choice) + " ---");
            }

            switch (choice) {
                case 1:
                    profileSection();
                    break;
                case 2:
                    catalogSection();
                    break;
                case 3:
                    discountSection();
                    break;
                case 4:
                    financeSection();
                    break;
                case 5:
                    securitySection();
                    break;
                case 6:
                    recommendSection();
                    break;
                case 7:
                    complexOrderSection();
                    break;
                case 8:
                    individualSection();
                    break;
                case 9:
                    report.print(user, system.userStatus(statusCode));
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("[Ошибка] Такого пункта нет. Попробуйте ещё раз.");
            }

            if (running) {
                in.pause();
            }
        }
        System.out.println("\nСпасибо, что пользовались SMART DECISION. До свидания!");
    }

    private void printMainMenu() {
        System.out.println("\n==================================================");
        System.out.println("   SMART DECISION — ГЛАВНОЕ МЕНЮ   [" + user + "]");
        System.out.println("==================================================");
        System.out.println("1. Профиль клиента (возраст, льготы)");
        System.out.println("2. Каталог и калькулятор стоимости");
        System.out.println("3. Скидки и лояльность");
        System.out.println("4. Финансы и рассрочка");
        System.out.println("5. Безопасность заказа");
        System.out.println("6. Рекомендации по выбору техники");
        System.out.println("7. Комплексный заказ");
        System.out.println("8. Анализ выгодности покупки");
        System.out.println("9. Итоговый отчёт");
        System.out.println("0. Выход");
        System.out.println("==================================================");
    }

    // ===================== 1. Профиль (ТЗ 3, 6, 8) =====================

    private void profileSection() {
        String status = system.userStatus(statusCode);
        System.out.println("Статус аккаунта: " + status);
        report.add("Статус аккаунта", status);

        age = in.readInt("Ваш возраст: ", 1, 120);
        String category = system.ageCategory(age);
        System.out.println("Возрастная категория: " + category);
        report.add("Возраст " + age, category);

        boolean student = in.readYesNo("Вы студент?");
        boolean promo = in.readYesNo("Есть промокод?");
        String benefit = system.benefitStatus(student, age, promo);
        System.out.println("Льготы: " + benefit);
        report.add("Льготы", benefit);
    }

    // ===================== 2. Каталог (ТЗ 4, 17, 18, 19) =====================

    private void catalogSection() {
        printCategories();
        int category = in.readInt("Категория (1–5): ", 1, 5);
        System.out.println(system.categoryInfo(category));

        int stock = in.readInt("Остаток товара на складе (шт.): ", 0, 100_000);
        String stockResult = system.checkStock(stock);
        System.out.println("Наличие: " + stockResult);

        double price = in.readDouble("Цена за единицу (₸): ", 1, 100_000_000);
        int quantity = in.readInt("Количество (1–10): ", 1, 10);

        System.out.println("Тариф сервиса: 1 — Базовый, 2 — Стандарт (+3%, гарантия 24 мес.), "
                + "3 — Премиум (+7%, гарантия 36 мес. и экспресс-доставка)");
        int tariff = in.readInt("Тариф (1–3): ", 1, 3);
        boolean installation = in.readYesNo("Нужна установка/настройка (+7 000 ₸)?");
        boolean giftWrap = in.readYesNo("Подарочная упаковка (+1 500 ₸)?");

        double total = system.calculateTotal(price, quantity, tariff, installation, giftWrap);
        System.out.println("\nИтоговая стоимость: " + system.format(total) + " ₸ (тариф «"
                + system.tariffName(tariff) + "»)");

        String name = system.categoryName(category);
        report.add("Каталог: " + name, stockResult + "; стоимость " + system.format(total) + " ₸");
    }

    private void printCategories() {
        System.out.println("Категории: 1 — Смартфоны, 2 — Ноутбуки, 3 — Телевизоры, "
                + "4 — Наушники и колонки, 5 — Аксессуары");
    }

    // ===================== 3. Скидки (ТЗ 11, 13) =====================

    private void discountSection() {
        double sum = in.readDouble("Сумма покупки (₸): ", 0, 100_000_000);
        System.out.println("Статус клиента: 1 — Обычный, 2 — Silver, 3 — Gold");
        level = in.readInt("Ваш статус (1–3): ", 1, 3);

        double percent = system.discountPercent(sum, level);
        double discount = sum * percent / 100;
        System.out.println("Скидка: " + percent + "%  (−" + system.format(discount) + " ₸)");
        System.out.println("К оплате: " + system.format(sum - discount) + " ₸");
        report.add("Скидка", percent + "% при сумме " + system.format(sum) + " ₸");

        int points = in.readInt("Баллы верификации аккаунта (0–100): ", 0, 100);
        String grade = system.gradeScore(points);
        System.out.println(grade);
        report.add("Верификация (" + points + " б.)", grade, points < 50);
    }

    // ===================== 4. Финансы (ТЗ 7, 12) =====================

    private void financeSection() {
        double income = in.readDouble("Ежемесячный доход (₸): ", 0, 1_000_000_000);
        double expenses = in.readDouble("Ежемесячные расходы (₸): ", 0, 1_000_000_000);

        String status = system.financeStatus(income, expenses);
        System.out.println(status);
        report.add("Финансовый статус", status);

        age = in.readInt("Ваш возраст: ", 1, 120);
        if (system.canUseInstallment(age, income)) {
            System.out.println("Рассрочка: доступна (возраст от 21 и доход от 200 000 ₸)");
            report.add("Рассрочка", "доступна");
        } else {
            System.out.println("Рассрочка: недоступна (нужен возраст от 21 и доход от 200 000 ₸)");
            report.add("Рассрочка", "недоступна");
        }
    }

    // ===================== 5. Безопасность (ТЗ 14) =====================

    private void securitySection() {
        boolean newDevice = in.readYesNo("Вход с нового устройства?");
        boolean foreign = in.readYesNo("Заказ из другой страны?");
        double sum = in.readDouble("Сумма заказа (₸): ", 0, 100_000_000);
        int failed = in.readInt("Неудачных попыток оплаты за сутки: ", 0, 100);

        String risk = system.securityRisk(newDevice, foreign, sum, failed);
        System.out.println("Уровень риска: " + risk);
        System.out.println(system.riskAdvice(risk));
        report.add("Безопасность", "риск " + risk, risk.equals("ВЫСОКИЙ"));
        report.markOrderChecked();
    }

    // ===================== 6. Рекомендации (ТЗ 15) =====================

    private void recommendSection() {
        System.out.println("Цель покупки: 1 — игры, 2 — работа, 3 — учёба, 4 — фото/видео");
        int purpose = in.readInt("Цель (1–4): ", 1, 4);
        double budget = in.readDouble("Бюджет (₸): ", 1, 100_000_000);

        String advice = system.recommend(purpose, budget);
        System.out.println("Рекомендуем: " + advice);
        report.add("Рекомендация", advice);
    }

    // ===================== 7. Комплексный заказ (ТЗ 10, 19, 20) =====================

    private void complexOrderSection() {
        printCategories();
        int category = in.readInt("Категория (1–5): ", 1, 5);
        double price = in.readDouble("Цена за единицу (₸): ", 1, 100_000_000);
        int quantity = in.readInt("Количество (1–10): ", 1, 10);
        double total = price * quantity;
        boolean inStock = in.readYesNo("Товар есть в наличии?");
        boolean city = in.readYesNo("Доставка по городу?");

        System.out.println("Способ оплаты: 1 — карта, 2 — наличные курьеру, 3 — рассрочка");
        int payment = in.readInt("Оплата (1–3): ", 1, 3);

        System.out.println("\nСумма заказа: " + system.format(total) + " ₸");

        String decision = system.nestedOrderDecision(statusCode, inStock, total, city);
        System.out.println("Решение по заказу: " + decision);
        boolean accepted = decision.startsWith("Заказ принят");

        String[] scenarios = system.smartScenarios(category, total, payment, city, age, statusCode);
        String[] titles = {"Оплата", "Доставка", "Акции", "Гарантия", "Проверка безопасности"};
        for (int i = 0; i < scenarios.length; i++) {
            System.out.println("  Сценарий " + (i + 1) + " (" + titles[i] + "): " + scenarios[i]);
        }

        boolean problem = !accepted || system.needsManualCheck(scenarios);
        report.add("Комплексный заказ (" + system.categoryName(category) + ", "
                + system.format(total) + " ₸)", decision, problem);
        report.markOrderChecked();
    }

    // ===================== 8. Индивидуальный модуль (ТЗ 21–24) =====================

    private void individualSection() {
        System.out.println("Анализ выгодности покупки по пяти параметрам.");
        double discount = in.readDouble("Цена ниже рыночной на (%; если дороже — отрицательное число): ", -100, 100);
        double rating = in.readDouble("Рейтинг товара (1–5): ", 1, 5);
        int warranty = in.readInt("Гарантия (месяцев): ", 0, 120);
        int reviews = in.readInt("Количество отзывов: ", 0, 1_000_000);
        int days = in.readInt("Срок доставки (дней): ", 0, 90);

        DecisionSystem.Analysis result = system.analyzePurchase(discount, rating, warranty, reviews, days);
        System.out.println("\nБаллы: " + result.points + " из 10");
        System.out.println("Уровень выгоды: " + result.level);
        System.out.println("Рекомендация: " + result.recommendation);
        report.add("Анализ выгодности", result.level + " уровень (" + result.points + "/10)");
    }
}
