import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        try {
            for (int i = 0; i < args.length; i++)
                try {
                    args[i] = IntegrationCheck.correctWrongInput(args[i]);
                } catch (IllegalArgumentException ignore) {
                    Output.printIncorrectableInput();
                }

            if (args.length > 0 && args[0].contains("-"))
                switch (args[0]) {
                    case "-g":
                        generateNumbers(args); //Eingabe aller Prüfziffern
                        break;
                    case "-f":
                        generatePart(args, 0); //Eingabe der ersten Prüfziffer
                        break;
                    case "-s":
                        generatePart(args, 1); //Eingabe der zweiten Prüfziffer
                        break;
                    case "-t":
                        generatePart(args, 2); //Eingabe der dritten Prüfziffer
                        break;
                    case "-c":
                        try {
                            checkNumbers(args);
                        } catch (ArrayIndexOutOfBoundsException ignore) {
                            Output.printNoMatchingInput();
                        }
                        break;
                    case "-h":
                    default:
                        Output.printHelp(); //Ausgabe der Hilfe
                        break;
                }
            else
                Output.printHelp();
        } catch (Exception ignore) {
            Output.printSomeError();
        }
    }

    /**
     * @param args   Die Programmargumente
     * @param number die Nummer des Parts 0 = erster String, 1 = zweiter String [...]
     */
    private static void generatePart(String[] args, int number) {
        int[][] ar = getIds(args, 1);
        int[] pz = new Personalausweis(ar[0], number).getPrüfziffern();
        System.out.println("Prüfziffer: " + pz[number]);
    }

    /**
     * Korrigiert die StringLänge, falls ein mitgegebenes Argument zu lang ist
     *
     * @param args Array der Argumente
     * @return korrigiertes Array der Argumente
     */
    private static String[] correctStringLength(String[] args,final boolean printWrong) {
        String[] arg = args.clone();
        try {
            if (printWrong) {
                if (args[1].length() < 9)
                    Output.printInputWrongLength(1, args[1], args[1].length() < 9, Math.abs(9 - args[1].length()));
                if (args[2].length() < 6)
                    Output.printInputWrongLength(2, args[2], args[2].length() < 6, Math.abs(6 - args[2].length()));
                if (args[3].length() < 6)
                    Output.printInputWrongLength(3, args[3], args[3].length() < 6, Math.abs(6 - args[3].length()));
            }

            arg[1] = args[1].substring(0, 9);
            arg[2] = args[2].substring(0, 6);
            arg[3] = args[3].substring(0, 6);
            if (printWrong)
                for (int i = 0; i < args.length; i++)
                    if (args[i].length() != arg[i].length())
                        Output.printInputWrongLength(i, args[i], false, args[i].length() - arg[i].length());
        } catch (StringIndexOutOfBoundsException | NullPointerException ignore) {
            Output.printIncorrectableInput();
        }
        return arg;
    }

    /**
     * @param args alle Argumente, die das Programm mitbekommen hat
     * @param part -1 = kompletter String, 0 = erster String, [...]
     * @return die Argumente in einem zweidimensionalen int array {erster Block, zweiter Block, dritter Block}
     */
    private static int[][] getIds(String[] args, int part) {
        String ids[] = new String[args.length - 1];
        try {
            if (part != -1) {
                ids[0] = args[1];
            } else if (args.length == 4) {
                ids[0] = args[1];
                ids[1] = args[2];
                ids[2] = args[3];
            } else if (args.length == 2) {
                ids = new String[3];
                if (args[1].length() > 21)
                    Output.printInputWrongLength(-1, args[1], args[1].length() < 21, Math.abs(21 - args[1].length()));
                try {
                    ids[0] = args[1].substring(0, 9);
                    ids[1] = args[1].substring(9, 15);
                    ids[2] = args[1].substring(15, 21);
                } catch (StringIndexOutOfBoundsException ignore) {
                    Output.printInputWrongLength(-1, args[1], args[1].length() < 21, Math.abs(21 - args[1].length()));
                    System.exit(-1);
                }
            } else
                Output.printNoMatchingInput();
        } catch (ArrayIndexOutOfBoundsException ignore) {
            Output.printNoMatchingInput();
        }

        int[][] arr = new int[3][];
        if (ids[0].length() < 7)
            arr[0] = splitArg1and2(0, ids[0]);
        else
            arr[0] = splitArg0(ids[0]);

        if (part == -1) {
            arr[1] = splitArg1and2(2, ids[1]);
            arr[2] = splitArg1and2(3, ids[2]);
        }

        return arr;
    }

    /**
     * @param args die Programmargumente
     */
    private static void generateNumbers(String[] args) {
        if (args.length > 3)
            args = correctStringLength(args, true);
        int arr[][] = getIds(args, -1);
        int[] pzA = new Personalausweis(arr[0], arr[1], arr[2]).getPrüfziffern();
        if (args.length > 2)
            Output.printComplete(args, pzA);
        else {
            String[] ids = new String[4];
            ids[1] = args[1].substring(0, 9);
            ids[2] = args[1].substring(9, 15);
            ids[3] = args[1].substring(15, 21);
            Output.printComplete(ids, pzA);
        }
    }

    /**
     * Die Methode holt die Werte des 1. Arguments aus dem String
     *
     * @param string String, der die Werte des Arguments enthält
     * @return Array der Zahlwerte
     */
    private static int[] splitArg0(String string) {
        int[] ar = new int[9];
        if (string.length() > ar.length)
            Output.printInputWrongLength(1, string, false, string.length() - ar.length);

        try {
            for (int i = 0; i < ar.length; i++)
                ar[i] = charToInt(string.charAt(i));
        } catch (StringIndexOutOfBoundsException ignore) {
            Output.printInputWrongLength(1, string, true, 9 - string.length());
            System.exit(-1);
        }
        return ar;
    }

    /**
     * Die Methode holt die Werte für
     *
     * @param arg    die Nummer des Arguments (für die Fehlerbehandlung)
     * @param string der String, der die Werte enthält
     * @return Array der Zahlwerte
     */
    private static int[] splitArg1and2(int arg, String string) {
        int[] ar = new int[6];

        try {
            IntegrationCheck.checkLogical(string);
        } catch (IllegalArgumentException ignore) {
            Output.printIncorrectableInput();
        }

        if (string.length() > ar.length)
            Output.printInputWrongLength(arg, string, false, string.length() - ar.length);

        try {
            for (int i = 0; i < ar.length; i++)
                ar[i] = charToInt(string.charAt(i));
        } catch (StringIndexOutOfBoundsException ignore) {
            Output.printInputWrongLength(arg, string, true, 9 - string.length());
            System.exit(-1);
        }
        return ar;
    }

    private static void checkNumbers(String[] args) throws ArrayIndexOutOfBoundsException{
        String[] ar = new String[args.length - 1];
        System.arraycopy(args, 1, ar, 0, ar.length);

        if (ar.length < 4) {
            ar = splitArguments(ar[0]);
        }
        int[] insertedPzA = getInsertedPzA(ar);
        args = correctStringLength(args, false);
        ar = new String[4];
        //fixme: läuft hier in eine ArrayOutOfBoundsException
        System.arraycopy(args, 0, ar, 0, ar.length);

        int arr[][] = getIds(ar, -1);
        int[] pzA = new Personalausweis(arr[0], arr[1], arr[2]).getPrüfziffern();
        if(!Arrays.equals(insertedPzA,pzA))
            Output.printWrongPzA(insertedPzA,pzA);
        else
            Output.printRightPzA();
    }

    private static int[] getInsertedPzA(String[] args) throws ArrayIndexOutOfBoundsException{
        int[] insertedPzA = new int[4];
        insertedPzA[0] = (int) args[0].charAt(args[0].length() - 1) - '0';
        insertedPzA[1] = (int) args[1].charAt(args[1].length() - 1) - '0';
        insertedPzA[2] = (int) args[2].charAt(args[2].length() - 1) - '0';
        insertedPzA[3] = (int) args[3].charAt(args[3].length() - 1) - '0';
        return insertedPzA;
    }

    private static String[] splitArguments(String args) {
        String[] ids = new String[4];
        ids[0] = args.substring(0, 10);
        ids[1] = args.substring(10, 17);
        ids[2] = args.substring(17, 24);
        ids[3] = args.substring(24, 25);
        return ids;
    }

    /**
     * Hilfsmethode um den Char in einen Int umzuwandeln
     *
     * @param c Character
     * @return Zahlwert
     */
    static int charToInt(char c) {
        return Character.getNumericValue(c);
    }
}