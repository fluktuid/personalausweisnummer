/**
 * Created by lfp on 13.03.17.
 * Die Klasse stellt einen Personalausweis dar;
 */
class Personalausweis {
    //    private int[] behördenkennzahl = new int[4];
//    private int[] laufendeNr = new int[5];
    private int[] block0 = new int[9];
    private int[] block1 = new int[6];
    private int[] block2 = new int[6];
    private byte[] prüfung = new byte[]{7, 3, 1};   //Multiplikator: 73173...
    private int[] prüfziffer = new int[4];

    /**
     * Konstruktor, der Verwendung findet, wenn die gesamte PauswZahl angegeben wurden
     *
     * @param ar0 Behördenkennzahl + PAuswNr.
     * @param ar1 Geburtsdatum
     * @param ar2 Ablaufdatum
     */
    Personalausweis(int[] ar0, int[] ar1, int[] ar2) {
        System.arraycopy(ar0, 0, block0, 0, block0.length);
        System.arraycopy(ar1, 0, block1, 0, block1.length);
        System.arraycopy(ar2, 0, block2, 0, block2.length);
        calculateChecks();
    }

    /**
     * Konstruktor, der Verwendung findet, wenn nur ein Teil der PauswZahl angegeben wurde
     *
     * @param ar0  Argumente
     * @param part Teil der angegeben wurde
     */
    Personalausweis(int[] ar0, int part) {
        switch (part) {
            case 0:
                System.arraycopy(ar0, 0, block0, 0, block0.length);
            case 1:
                System.arraycopy(ar0, 0, block1, 0, block1.length);
            case 2:
                System.arraycopy(ar0, 0, block2, 0, block2.length);
        }
        calculateChecks();
    }


    /**
     * rechnet die Prüfsummen aus
     */
    private void calculateChecks() {
        prüfziffer[0] = calculate(block0, 0);
        prüfziffer[1] = calculate(block1, 0);
        prüfziffer[2] = calculate(block2, 0);
        prüfziffer[3] = calculateGesamt();
    }

    /**
     * Errechnen der jeweiligen Prüfzahl des Blockes
     *
     * @param block  Integer Array, das berechnet werden soll
     * @param offset Offset für das prüfziffern[], da nicht zwingend mit 7 begonnen werden muss zu multiplizieren
     * @return Prüfzahl
     */
    private int calculate(int[] block, int offset) {
        int sum = 0;
        for (int i = 0; i < block.length; i++)
            sum += (block[i] * prüfung[(offset + i) % 3]);
        return sum % 10;
    }

    /**
     * Getter für die Prüfziffern
     *
     * @return Array aus Prüfziffern
     */
    int[] getPrüfziffern() {
        return prüfziffer;
    }

    /**
     * Errechnen der 4. Prüfzahl
     *
     * @return 4. Prüfzahl
     */
    private int calculateGesamt() {
        int sum = calculate(block0, 0);
        sum += prüfziffer[0] * 7;
        sum += calculate(block1, 1);
        sum += prüfziffer[1] * 3;
        sum += calculate(block2, 2);
        sum += prüfziffer[2];
        return sum % 10;
    }
}
