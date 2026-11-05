/**
 * Основная логика принятия решений для онлайн-магазина электроники.
 * Здесь только условные операторы и расчёты — ввод/вывод выполняет Menu.
 * Денежные суммы указаны в тенге (₸).
 */
public class DecisionSystem {

    // Коды статуса пользователя
    public static final int ACTIVE = 1;
    public static final int INACTIVE = 2;
    public static final int BLOCKED = 3;

    // ===================== ТЗ 3, 5, 9: доступ и статус =====================

    /** ТЗ 5. Проверка логина и пароля. Возвращает код статуса аккаунта или -1, если данные неверны. */
    public int authenticate(String login, String password) {
        if (login.equals("ivan") && password.equals("1234")) {
            return ACTIVE;
        } else if (login.equals("anna") && password.equals("5678")) {
            return INACTIVE;
        } else if (login.equals("petr") && password.equals("0000")) {
            return BLOCKED;
        } else {
            return -1;
        }
    }

    /** ТЗ 3. Статус пользователя: активный, неактивный или заблокированный. */
    public String userStatus(int code) {
        if (code == ACTIVE) {
            return "Активный";
        } else if (code == INACTIVE) {
            return "Неактивный";
        } else if (code == BLOCKED) {
            return "Заблокированный";
        } else {
            return "Неизвестный";
        }
    }

    /** ТЗ 9. Оператор ! — пользователь не заблокирован и может пользоваться системой. */
    public boolean canUseSystem(boolean blocked) {
        return !blocked;
    }

    // ===================== ТЗ 4: числовой показатель =====================

    /** ТЗ 4. Проверка остатка товара на складе — один из трёх результатов. */
    public String checkStock(int quantity) {
        if (quantity == 0) {
            return "Нет в наличии — доступен предзаказ";
        } else if (quantity <= 5) {
            return "Заканчивается (осталось " + quantity + " шт.) — рекомендуем оформить скорее";
        } else {
            return "В наличии (" + quantity + " шт.)";
        }
    }

    // ===================== ТЗ 6, 7, 8: возраст и сложные условия =====================

    /** ТЗ 6. Возрастные категории клиента (пять категорий, if — else if). */
    public String ageCategory(int age) {
        if (age < 14) {
            return "Ребёнок — покупки только через родителей";
        } else if (age < 18) {
            return "Подросток — заказ возможен с согласия родителей";
        } else if (age < 35) {
            return "Молодой клиент — доступны все услуги и акции для молодёжи";
        } else if (age < 60) {
            return "Взрослый клиент — доступны все услуги магазина";
        } else {
            return "Старший клиент — доступна помощь оператора и льготная доставка";
        }
    }

    /** ТЗ 7. Оператор && — рассрочка доступна только при выполнении двух требований. */
    public boolean canUseInstallment(int age, double income) {
        return age >= 21 && income >= 200_000;
    }

    /** ТЗ 8. Оператор || — льготный статус, если выполняется хотя бы одно условие. */
    public String benefitStatus(boolean isStudent, int age, boolean hasPromoCode) {
        if (isStudent || age >= 60 || hasPromoCode) {
            return "Льготный клиент — бесплатная доставка и повышенные бонусы";
        } else {
            return "Обычный клиент — стандартные условия";
        }
    }

    // ===================== ТЗ 10: вложенные if =====================

    /** ТЗ 10. Решение по заказу: результат зависит от четырёх последовательных проверок. */
    public String nestedOrderDecision(int statusCode, boolean inStock, double sum, boolean cityDelivery) {
        if (statusCode == ACTIVE) {                         // проверка 1: аккаунт
            if (inStock) {                                  // проверка 2: наличие
                if (sum >= 5_000) {                         // проверка 3: минимальная сумма
                    if (cityDelivery) {                     // проверка 4: адрес
                        return "Заказ принят: курьерская доставка по городу";
                    } else {
                        return "Заказ принят: доставка в пункт выдачи / транспортной компанией";
                    }
                } else {
                    return "Заказ отклонён: минимальная сумма заказа — 5 000 ₸";
                }
            } else {
                return "Товара нет в наличии: предложен предзаказ";
            }
        } else {
            return "Заказ невозможен: аккаунт не активен";
        }
    }

    // ===================== ТЗ 11: скидки =====================

    /** ТЗ 11. Процент скидки зависит от суммы покупки и статуса клиента (1 — обычный, 2 — Silver, 3 — Gold). */
    public double discountPercent(double sum, int level) {
        double percent;
        if (sum >= 500_000) {
            percent = 10;
        } else if (sum >= 200_000) {
            percent = 7;
        } else if (sum >= 50_000) {
            percent = 5;
        } else {
            percent = 0;
        }

        if (level == 2) {
            percent += 2;
        } else if (level == 3) {
            percent += 5;
        }
        return percent;
    }

    // ===================== ТЗ 12: финансы =====================

    /** ТЗ 12. Финансовый статус по доходам и расходам. */
    public String financeStatus(double income, double expenses) {
        double balance = income - expenses;
        if (balance < 0) {
            return "Дефицит бюджета (" + format(balance) + " ₸): покупки в рассрочку не рекомендуются";
        } else if (balance < income * 0.2) {
            return "Устойчивое положение: свободно " + format(balance) + " ₸ — подойдёт недорогая техника";
        } else {
            return "Хорошее положение: свободно " + format(balance) + " ₸ — можно рассматривать крупные покупки";
        }
    }

    // ===================== ТЗ 13: оценка по баллам =====================

    /** ТЗ 13. Оценка и результат верификации покупателя по баллам (0–100). */
    public String gradeScore(int points) {
        if (points >= 90) {
            return "Оценка 5 — верификация пройдена, доступны все способы оплаты";
        } else if (points >= 75) {
            return "Оценка 4 — верификация пройдена";
        } else if (points >= 50) {
            return "Оценка 3 — верификация пройдена с ограничениями (без рассрочки)";
        } else {
            return "Оценка 2 — верификация не пройдена, нужна проверка оператором";
        }
    }

    // ===================== ТЗ 14: безопасность =====================

    /** ТЗ 14. Уровень риска заказа по нескольким параметрам. */
    public String securityRisk(boolean newDevice, boolean foreignCountry, double sum, int failedAttempts) {
        int risk = 0;
        if (newDevice) {
            risk++;
        }
        if (foreignCountry) {
            risk += 2;
        }
        if (sum >= 300_000) {
            risk++;
        }
        if (failedAttempts >= 3) {
            risk += 2;
        }

        if (risk >= 4) {
            return "ВЫСОКИЙ";
        } else if (risk >= 2) {
            return "СРЕДНИЙ";
        } else {
            return "НИЗКИЙ";
        }
    }

    /** Пояснение к уровню риска. */
    public String riskAdvice(String level) {
        if (level.equals("ВЫСОКИЙ")) {
            return "Заказ заморожен до проверки оператором, потребуется подтверждение по телефону";
        } else if (level.equals("СРЕДНИЙ")) {
            return "Потребуется SMS-подтверждение платежа";
        } else {
            return "Дополнительных проверок не требуется";
        }
    }

    // ===================== ТЗ 15: рекомендации =====================

    /**
     * ТЗ 15. Рекомендация устройства.
     * purpose: 1 — игры, 2 — работа, 3 — учёба, 4 — фото/видео.
     * budget: бюджет в тенге.
     */
    public String recommend(int purpose, double budget) {
        if (purpose == 1) {
            if (budget >= 600_000) {
                return "Игровой ноутбук с видеокартой RTX 4070+ или мощный игровой ПК";
            } else if (budget >= 300_000) {
                return "Ноутбук среднего игрового класса (RTX 4050/4060)";
            } else {
                return "Игровая консоль или бюджетный ноутбук + игровые аксессуары";
            }
        } else if (purpose == 2) {
            if (budget >= 400_000) {
                return "Ультрабук премиум-класса (лёгкий, 16 ГБ ОЗУ, SSD 512 ГБ+)";
            } else if (budget >= 200_000) {
                return "Офисный ноутбук 15\" + беспроводная мышь и гарнитура";
            } else {
                return "Недорогой ноутбук или моноблок для офисных задач";
            }
        } else if (purpose == 3) {
            if (budget >= 250_000) {
                return "Планшет с пером или лёгкий ноутбук для учёбы";
            } else {
                return "Бюджетный ноутбук/планшет + наушники для онлайн-занятий";
            }
        } else {
            if (budget >= 500_000) {
                return "Флагманский смартфон с мощной камерой или беззеркальная камера";
            } else if (budget >= 200_000) {
                return "Смартфон среднего класса с хорошей камерой + штатив";
            } else {
                return "Экшн-камера или бюджетный смартфон";
            }
        }
    }

    // ===================== ТЗ 16–18: switch =====================

    /** ТЗ 17. Описание категории товара (switch). */
    public String categoryInfo(int category) {
        switch (category) {
            case 1:
                return "Смартфоны: фильтры по бренду, памяти, камере; trade-in старого телефона";
            case 2:
                return "Ноутбуки: подбор по процессору, ОЗУ, видеокарте; сборка и настройка ОС";
            case 3:
                return "Телевизоры: диагональ, тип матрицы, Smart TV; доставка с подъёмом и установка";
            case 4:
                return "Наушники и колонки: проводные/беспроводные, шумоподавление; возврат в 14 дней";
            case 5:
                return "Аксессуары: чехлы, зарядки, кабели; самовывоз за 1 час";
            default:
                return "Неизвестная категория";
        }
    }

    public String categoryName(int category) {
        switch (category) {
            case 1:
                return "Смартфоны";
            case 2:
                return "Ноутбуки";
            case 3:
                return "Телевизоры";
            case 4:
                return "Наушники и колонки";
            case 5:
                return "Аксессуары";
            default:
                return "Неизвестная категория";
        }
    }

    /** Название тарифа сервиса. */
    public String tariffName(int tariff) {
        switch (tariff) {
            case 1:
                return "Базовый";
            case 2:
                return "Стандарт";
            case 3:
                return "Премиум";
            default:
                return "Неизвестный";
        }
    }

    /**
     * ТЗ 18. Калькулятор стоимости: зависит от тарифа (switch) и дополнительных параметров.
     * Тариф: 1 — Базовый (+0%), 2 — Стандарт (+3% — гарантия 24 мес.), 3 — Премиум (+7% — гарантия 36 мес. и экспресс-доставка).
     */
    public double calculateTotal(double price, int quantity, int tariff, boolean installation, boolean giftWrap) {
        double total = price * quantity;
        switch (tariff) {
            case 1:
                break;
            case 2:
                total += total * 0.03;
                break;
            case 3:
                total += total * 0.07;
                break;
            default:
                break;
        }
        if (installation) {
            total += 7_000;
        }
        if (giftWrap) {
            total += 1_500;
        }
        return total;
    }

    /** ТЗ 16. Название раздела меню (switch, девять разделов). */
    public String sectionName(int choice) {
        switch (choice) {
            case 1:
                return "ПРОФИЛЬ КЛИЕНТА";
            case 2:
                return "КАТАЛОГ И КАЛЬКУЛЯТОР СТОИМОСТИ";
            case 3:
                return "СКИДКИ И ЛОЯЛЬНОСТЬ";
            case 4:
                return "ФИНАНСЫ И РАССРОЧКА";
            case 5:
                return "БЕЗОПАСНОСТЬ ЗАКАЗА";
            case 6:
                return "РЕКОМЕНДАЦИИ ПО ВЫБОРУ ТЕХНИКИ";
            case 7:
                return "КОМПЛЕКСНЫЙ ЗАКАЗ";
            case 8:
                return "АНАЛИЗ ВЫГОДНОСТИ ПОКУПКИ";
            case 9:
                return "ИТОГОВЫЙ ОТЧЁТ";
            default:
                return "";
        }
    }

    // ===================== ТЗ 20: switch + if-else + логические операторы =====================

    /**
     * ТЗ 20. Пять сценариев принятия решений в одной системе.
     * payment: 1 — карта, 2 — наличные курьеру, 3 — рассрочка.
     */
    public String[] smartScenarios(int category, double total, int payment, boolean cityDelivery, int age, int statusCode) {
        String[] result = new String[5];

        // Сценарий 1: способ оплаты (switch + &&)
        switch (payment) {
            case 1:
                result[0] = "Оплата картой доступна";
                break;
            case 2:
                if (cityDelivery && total <= 300_000) {
                    result[0] = "Оплата наличными курьеру доступна";
                } else {
                    result[0] = "Наличные недоступны (только по городу и до 300 000 ₸) — выберите карту";
                }
                break;
            case 3:
                if (age >= 21 && total >= 50_000 && statusCode == ACTIVE) {
                    result[0] = "Рассрочка одобрена предварительно";
                } else {
                    result[0] = "Рассрочка недоступна (нужен возраст от 21, сумма от 50 000 ₸ и активный аккаунт)";
                }
                break;
            default:
                result[0] = "Неизвестный способ оплаты";
        }

        // Сценарий 2: способ доставки (switch + if)
        switch (category) {
            case 3:
                result[1] = "Крупногабаритная доставка с подъёмом (2–3 дня)";
                break;
            case 5:
                if (total < 10_000) {
                    result[1] = "Доставка почтой или самовывоз";
                } else {
                    result[1] = "Курьерская доставка";
                }
                break;
            default:
                if (cityDelivery) {
                    result[1] = "Бесплатная курьерская доставка по городу";
                } else if (total >= 50_000) {
                    result[1] = "Бесплатная доставка транспортной компанией до пункта выдачи";
                } else {
                    result[1] = "Платная доставка транспортной компанией";
                }
        }

        // Сценарий 3: акции (if-else + && и ||)
        if (total >= 500_000 && payment == 1) {
            result[2] = "Акция: дополнительная скидка 3% за оплату картой";
        } else if (total >= 100_000 || category == 2) {
            result[2] = "Акция: подарок — сумка/мышь/чехол на выбор";
        } else {
            result[2] = "Дополнительных акций нет";
        }

        // Сценарий 4: гарантия (switch + !)
        boolean notBlocked = statusCode != BLOCKED;
        switch (category) {
            case 1:
            case 2:
                result[3] = (total >= 200_000 && notBlocked)
                        ? "Рекомендуем расширенную гарантию 36 мес. и страховку от повреждений"
                        : "Стандартная гарантия 12 мес.";
                break;
            case 3:
                result[3] = "Рекомендуем гарантию от пробоя матрицы";
                break;
            default:
                result[3] = "Стандартная гарантия 6 мес.";
        }

        // Сценарий 5: проверка безопасности (if-else + ||)
        if (total >= 1_000_000 || (payment == 2 && total > 300_000)) {
            result[4] = "Требуется ручная проверка заказа оператором";
        } else if (!cityDelivery && total >= 300_000) {
            result[4] = "Требуется подтверждение по телефону";
        } else {
            result[4] = "Автоматическое подтверждение заказа";
        }
        return result;
    }

    /** Нужна ли ручная проверка по итогам сценариев (для итогового отчёта). */
    public boolean needsManualCheck(String[] scenarios) {
        return scenarios[4].startsWith("Требуется ручная")
                || scenarios[0].contains("недоступн")
                || scenarios[0].contains("Неизвестный");
    }

    // ===================== ТЗ 21–24: индивидуальный модуль =====================

    /** Результат анализа выгодности покупки. */
    public static class Analysis {
        public int points;
        public String level;
        public String recommendation;
    }

    /**
     * ТЗ 21–24. Анализ выгодности покупки по пяти параметрам:
     * скидка относительно рынка (%), рейтинг товара (1–5), гарантия (мес.), число отзывов, срок доставки (дней).
     * Каждый параметр даёт 0–2 балла (максимум 10). Уровни: низкий / средний / высокий.
     */
    public Analysis analyzePurchase(double discountVsMarket, double rating, int warrantyMonths, int reviews, int deliveryDays) {
        int points = 0;

        // условие 1: цена
        if (discountVsMarket >= 10) {
            points += 2;
        } else if (discountVsMarket >= 0) {
            points += 1;
        }
        // условие 2: рейтинг
        if (rating >= 4.5) {
            points += 2;
        } else if (rating >= 4.0) {
            points += 1;
        }
        // условие 3: гарантия
        if (warrantyMonths >= 24) {
            points += 2;
        } else if (warrantyMonths >= 12) {
            points += 1;
        }
        // условие 4: отзывы
        if (reviews >= 100) {
            points += 2;
        } else if (reviews >= 20) {
            points += 1;
        }
        // условие 5: доставка
        if (deliveryDays <= 3) {
            points += 2;
        } else if (deliveryDays <= 7) {
            points += 1;
        }

        Analysis a = new Analysis();
        a.points = points;

        // ТЗ 23: три уровня результата
        if (points >= 7) {
            a.level = "ВЫСОКИЙ";
        } else if (points >= 4) {
            a.level = "СРЕДНИЙ";
        } else {
            a.level = "НИЗКИЙ";
        }

        // ТЗ 24: персонализированные рекомендации по комбинации условий
        if (discountVsMarket >= 30 && (rating < 4.0 || reviews < 20)) {
            a.recommendation = "Подозрительно низкая цена при слабой репутации товара — "
                    + "проверьте продавца и не вносите предоплату";
        } else if (a.level.equals("ВЫСОКИЙ") && deliveryDays <= 3) {
            a.recommendation = "Отличная покупка: берите сразу — выгодная цена, надёжный товар, быстрая доставка";
        } else if (a.level.equals("ВЫСОКИЙ")) {
            a.recommendation = "Хорошая покупка: оформляйте заказ, но учтите срок доставки";
        } else if (a.level.equals("СРЕДНИЙ") && warrantyMonths < 12) {
            a.recommendation = "Покупка приемлема, но гарантия короткая — купите расширенную гарантию";
        } else if (a.level.equals("СРЕДНИЙ") && discountVsMarket < 0) {
            a.recommendation = "Цена выше рыночной — сравните предложения и дождитесь акции";
        } else if (a.level.equals("СРЕДНИЙ")) {
            a.recommendation = "Нормальный вариант: сравните ещё 1–2 предложения перед покупкой";
        } else if (reviews < 20) {
            a.recommendation = "Мало отзывов и невыгодные условия — лучше выбрать другой товар";
        } else {
            a.recommendation = "Невыгодная покупка: ищите альтернативу с лучшей гарантией и ценой";
        }
        return a;
    }

    // ===================== Вспомогательное =====================

    /** Форматирование суммы: 1 234 567. */
    public String format(double value) {
        return String.format("%,.0f", value).replace(',', ' ').replace(' ', ' ');
    }
}
