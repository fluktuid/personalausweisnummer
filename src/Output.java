/**
 * Created by lfp on 26.03.17.
 * Klasse, die sich um den Output kümmert
 */
abstract class Output {

    /**
     * Gibt aus, dass keine passende Eingabe gefunden wurde
     */
    static void printNoMatchingInput() {
        System.out.println();
        System.out.println(" ___________________________________________________");
        System.out.println("/                                                   \\");
        System.out.println("| Es konnte keine passende Eingabe gefunden werden. |");
        System.out.println("\\___________________________________________________/");
        printHelp();
        System.exit(-1);
    }
    static void printSomeError() {
        printHelp();
        System.out.println();
        System.out.println("Es ist ein Fehler aufgetreten.");
        System.out.println("Bitte überprüfen Sie Ihre Eingabe und versuchen es erneut.");
        System.out.println();
        System.exit(-1);
    }

    /**
     * Gibt aus, dass die Eingabe fehlerhaft war, jedoch korrigiert werden konnte
     */
    static void printInputHasBeenCorrected() {
        System.out.println();
        System.out.println(" ____________________________________________________________________");
        System.out.println("/                                                                    \\");
        System.out.println("| Ihre Eingabe ist fehlerhaft. Es wurde versucht sie zu korrigieren. |");
        System.out.println("|           DIE ERGEBNISSE SIND MÖGLICHERWEISE FEHLERHAFT.           |");
        System.out.println("\\____________________________________________________________________/");
        System.out.println();
    }

    /**
     * Gibt aus, dass die Eingabe fehlerhaft war und nicht korrigiert werden konnte
     */
    static void printIncorrectableInput() {
        System.out.println(" _________________________________________________________________");
        System.out.println("/                                                                 \\");
        System.out.println("| Ihre Eingabe ist fehlerhaft und konnte nicht korrigiert werden. |");
        System.out.println("\\_________________________________________________________________/");
        System.exit(-1);
    }

    /**
     * Gibt die Eingabeargumente mit der dazugehörigen Prüfziffer und die Gesamtprüfziffer aus
     *
     * @param args Eingabeargumente
     * @param pzA  Prüfziffern
     */
    static void printComplete(String[] args, int[] pzA) {
        System.out.println();
        System.out.println("                                 PZ");
        System.out.println("Personalausweisnummer: " + args[1] + " " + pzA[0]);
        System.out.println("Geburtsdatum         : " + args[2] + "    " + pzA[1]);
        System.out.println("Ablaufdatum          : " + args[3] + "    " + pzA[2]);
        System.out.println("                                ---");
        System.out.println("Prüfziffer gesamt    :           " + pzA[3]);
        System.out.println();
        printShouldLookLike(args, pzA);
    }

    /**
     * Gibt die Schrift auf dem Personalausweis aus
     *
     * @param args Eingabeargumente
     * @param pzA  Prüfziffern
     */
    private static void printShouldLookLike(String[] args, int[] pzA) {
        System.out.println();
        System.out.println("Der Aufdruck sollte lauten wie folgt:");
        System.out.println(" ________________________________");
        System.out.println("/                                \\");
        System.out.println("| IDD<<" + args[1] + pzA[0] + "<<<<<<<<<<<<<<< |");
        System.out.println("| " + args[2] + pzA[1] + "<" + args[3] + pzA[2] + "D<<<<<<<<<<<<<" + pzA[3] + " |");
        System.out.println("\\________________________________/");
    }

    /**
     * Gibt das Eingabemuster aus
     *
     * @param number      Nummer der Eingabesektion
     * @param specific    fehlerhafte Eingabe
     * @param toShort     true wenn die fehlerhafte Eingabe zu kurz ist
     * @param soMuchWrong Anzahl der Stellen, um die die Eingabe zu lang/kurz ist
     */
    static void printInputWrongLength(int number, String specific, boolean toShort, int soMuchWrong) {
        System.out.println(" ___________________________");
        System.out.println("/                           \\");
        System.out.println("| Ihre Eingabe war zu kurz  |");
        System.out.println("| Das Eingabemuster lautet: |");
        System.out.println("|                           |");
        System.out.println("|   xxxxxxxx xxxxxx xxxxxx  |");
        System.out.println("|     (9)      (6)    (6)   |");
        System.out.println("|   oder xxxxxxxxxxxxxxxx   |");
        System.out.println("|             (21)          |");
        System.out.println("|                           |");
        System.out.println("| (n) : Anzahl der Sellen   |");
        System.out.println("\\___________________________/");

        String numberString = (number != -1) ? " (" + number + ". Eingabe)" : "";
        if (specific != null) {
            System.out.println();
            if (toShort)
                System.out.println("Die Eingabe " + specific + numberString + " ist " + soMuchWrong + " Stelle(n) zu kurz.");
            else
                System.out.println("Die Eingabe " + specific + numberString + " ist " + soMuchWrong + " Stelle(n) zu lang.\nEs kann zu falschen Ergebnissen kommen.");
            System.out.println();
        }
    }

    /**
     * Gibt die Hilfe aus
     */
    static void printHelp() {
        System.out.println();
        System.out.println("Mögliche Eingaben:");
        System.out.println("-h: Hilfe");
        System.out.println();
        System.out.println("    GENERIEREN");
        System.out.println("   ------------");
        System.out.println("-g: generieren der Prüfziffer");
        System.out.println("    xxxxxxxx xxxx xxxx");
        System.out.println("       (9)    (6)  (6)");
        System.out.println("    oder");
        System.out.println("    xxxxxxxxxxxxxxxx");
        System.out.println("           (21)");
        System.out.println("    Die Eingabe erfolgt ohne Prüfziffer.");
        System.out.println("-f: generieren der ersten Prüfziffer");
        System.out.println("-s: generieren der zweiten Prüfziffer");
        System.out.println("-t: generieren der dritten Prüfziffer");
        System.out.println();
        System.out.println("    PRÜFEN");
        System.out.println("   --------");
        System.out.println("-c: Überprüfen der Prüfziffer");
        System.out.println("    xxxxxxxx xxxx xxxx x");
//        System.out.println("      (10)   (7)  (7) (1)");
//        System.out.println("    oder");
//        System.out.println("    xxxxxxxxxxxxxxxx");
//        System.out.println("           (24)");
    }

    static void printWrongPzA(int[] insertedPzA, int[] pzA) {
        System.out.println(" _____________________________");
        System.out.println("/                             \\");
        System.out.println("| Mindestens eine ermittelte  |");
        System.out.println("| Prüfziffer entspricht nicht |");
        System.out.println("| der Eingabe!                |");
        System.out.println("|                             |");
        System.out.println("| PzNr | Eingabe | Berechnung |");
        for (int i = 0;i<pzA.length;i++)
            System.out.println("|   "+(i+1)+"  |    "+insertedPzA[i]+"    |      "+pzA[i]+"     |");
        System.out.println("\\_____________________________/");
    }
    static void printRightPzA() {
        System.out.println(" ______________________________");
        System.out.println("/                              \\");
        System.out.println("| Alle ermittelten Prüfziffern |");
        System.out.println("| entsprechen der Eingabe.     |");
        System.out.println("\\______________________________/");
    }
}
