import java.util.Arrays;

/**
 * Created by lfp on 26.03.17.
 * Der IntegrationCheck, um Eingabefehler und logische Fehler zu finden und evtl. zu korrigieren
 */
abstract class IntegrationCheck {
    private static String[] wrongs = new String[]{
            "o", "b", "q",
            "d", "s"
    };
    private static String[] corrects = new String[]{
            "0", "8", "0",
            "0", "5"
    };
    private static String[] allowed = new String[]{
            "0", "1", "2",
            "3", "4", "5",
            "6", "7", "8",
            "9", "c", "f",
            "g", "h", "j",
            "k", "l", "m",
            "n", "p", "r",
            "t", "v", "w",
            "x", "z"
    };

    /**
     * Die Methode prüft auf syntaktisch fehlerhaften Input und versucht ihn zu korrigieren
     *
     * @param input der zu überprüfende Input
     * @return korrigierter Input
     * @throws IllegalArgumentException wenn die Eingabe nicht korrigiert werden konnte
     */
    static String correctWrongInput(String input) throws IllegalArgumentException {
        input = input.toLowerCase();

        if (input.contains("-"))
            return input;

        boolean replaced = false;
        for (int i = 0; i < wrongs.length; i++)
            if (input.contains(wrongs[i])) {
                input = input.replace(wrongs[i], corrects[i]);
                replaced = true;
            }

        if (replaced)
            Output.printInputHasBeenCorrected();

        for (int j = 0; j < input.length(); j++)
            if (Arrays.stream(allowed).parallel().noneMatch(String.valueOf(input.charAt(j))::contains))
                throw new IllegalArgumentException();

        input = input.toUpperCase();
        return input;
    }

    /**
     * Die Methode prüft auf logisch falschen Input wirft falls solcher auftritt eine Exception
     * @param input der zu überprüfende String
     * @throws IllegalArgumentException wenn mindestens ein Wert unlogisch ist
     */
    static void checkLogical(String input) throws IllegalArgumentException {
        char[] chars = input.toCharArray();
        int[] values = new int[chars.length];
        for (int i = 0; i < chars.length; i++)
            values[i] = Main.charToInt(chars[i]);

        for (int i = 0; i < values.length; i++) {
            int value = values[i];
            if (value > 9)
                throw new IllegalArgumentException();
            switch (i) {
                case 2:
                    if (value > 1)
                        throw new IllegalArgumentException();
                    break;
                case 3:
                    if (value > 2)
                        if (values[i - 1] > 0)
                            throw new IllegalArgumentException();
                    break;
                case 4:
                    if (value > 3)
                        throw new IllegalArgumentException();
                    break;
                case 5:
                    if (value > 1)
                        if (values[i - 1] > 2)
                            throw new IllegalArgumentException();
                    break;
            }
        }
    }
}
