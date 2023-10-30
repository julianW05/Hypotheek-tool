package org.example;

import java.util.Scanner;

import java.util.Scanner;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class App {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    public static double berekenMaxTeLenenBedrag(double maandinkomen, boolean heeftStudieschuld, int rentevastePeriode, String postcode) {
        // Check of alle invoeren correct zijn
        if (maandinkomen <= 0 || (rentevastePeriode != 1 && rentevastePeriode != 5 && rentevastePeriode != 10 && rentevastePeriode != 20 && rentevastePeriode != 30)) {
            throw new IllegalArgumentException("Ongeldige invoer");
        }

        // Check voor aardbevinggebied
        if (postcode.equals("9679") || postcode.equals("9681") || postcode.equals("9682")) {
            return 0.0;
        }

        // Kijk wat de rentevasteperiode is
        double rentePercentage = switch (rentevastePeriode) {
            case 1 -> 0.02;
            case 5 -> 0.03;
            case 10 -> 0.035;
            case 20 -> 0.045;
            case 30 -> 0.05;
            default -> throw new IllegalArgumentException("Ongeldige rentevaste periode");
        };

        // Bereken het maximale te lenen bedrag
        double maxTeLenen = maandinkomen * 12 * 4.25;

        // Check of er een studieschuld is en pas het maximale te lenen bedrag aan
        if (heeftStudieschuld == true) {
            maxTeLenen = maxTeLenen * 0.75;
        }

        // Bereken de rente en aflossingsbedrag
        double renteBedrag = maxTeLenen * (rentePercentage / 12);
        double aflossingsBedrag = maxTeLenen / (rentevastePeriode * 12);

        // Bereken het totaalbedrag
        double totaalBedrag = renteBedrag + aflossingsBedrag;

        // Bereken het totaalbedrag na 30 jaar
        double totaalBetaald = totaalBedrag * rentevastePeriode * 12;

        // Print alle overige uitkomsten!
        System.out.println("Maximaal te lenen:" + df.format(maxTeLenen));
        System.out.println("Rente bedrag:" + df.format(renteBedrag));
        System.out.println("Aflossingsbedrag:" + df.format(aflossingsBedrag));
        System.out.println("Totaal maandbedrag " + df.format(totaalBedrag));

        // Geef het totaalbedrag na 30 jaar terug
        return totaalBetaald;
    }

    public static void main(String[] args) {
        // Vraag voor alle invoeren
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welkom bij de Hypotheekberekeningstool!");
        System.out.print("Voer uw maandinkomen in: ");
        double maandinkomen = scanner.nextDouble();

        System.out.print("Heeft u een partner? (ja/nee): ");
        boolean heeftPartner = scanner.next().equalsIgnoreCase("ja");

        double partnerInkomen = 0.0;
        if (heeftPartner) {
            System.out.print("Voer het maandinkomen van uw partner in: ");
            partnerInkomen = scanner.nextDouble();
        }

        System.out.print("Heeft u een studieschuld? (ja/nee): ");
        boolean heeftStudieschuld = scanner.next().equalsIgnoreCase("ja");

        System.out.print("Selecteer de rentevaste periode (1/5/10/20/30 jaar): ");
        int rentevastePeriode = scanner.nextInt();

        System.out.print("Voer uw postcode in: ");
        String postcode = scanner.next();

        // Roep de functie aan om alles te berekenen
        double maxTeLenenBedrag = berekenMaxTeLenenBedrag(maandinkomen + partnerInkomen, heeftStudieschuld, rentevastePeriode, postcode);

        // Print het resultaat
        System.out.println("Totaal betaald na 30 jaar: " + df.format(maxTeLenenBedrag));

        scanner.close();
    }
}