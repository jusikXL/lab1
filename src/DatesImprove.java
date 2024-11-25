class Cheating {
    Cheating() {
    }

    public static int daysInMonth(int month) {
        return month != 9 && month != 7 && month != 6 && month != 11 ? 31 : 30;
    }

    public static void main(String[] args) {
        int someMonth = Integer.parseInt(args[0]);
        int someDay = Integer.parseInt(args[1]);
        int laterMonth = Integer.parseInt(args[2]);
        int laterDay = Integer.parseInt(args[3]);

        int someDayInYear = 0;
        int laterDayInYear = 0;

        int aMonth = 1; // not zero

        while (aMonth < someMonth) {
            someDayInYear += daysInMonth(aMonth);
            ++aMonth;
        }
        someDayInYear += someDay; // add here

        while (aMonth < laterMonth) {
            laterDayInYear += daysInMonth(aMonth);
            ++aMonth;
        }
        laterDayInYear += laterDay; // add here

        int daysBetween = laterDayInYear > someDayInYear ? laterDayInYear - someDayInYear - 1
                : someDayInYear - laterDayInYear - 365 - 1; // FIX A LOT HERE

        System.out.println("The difference in days between " + someMonth + "/" + someDay + " and " + laterMonth + "/"
                + laterDay + " is: " + daysBetween);
    }
}