package org.example;

import java.util.Scanner;


public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welkom bij de Hypotheekberekening!");

        while (true) {
            System.out.println("\nOpties:");
            System.out.println("1. Bereken hypotheek");
            System.out.println("2. Afsluiten");
            System.out.print("Voer uw keuze in (1/2): ");

            int keuze = scanner.nextInt();

            if (keuze == 1) {
                System.out.print("Voer uw maandinkomen in: ");
                double maandinkomen = scanner.nextDouble();

                System.out.print("Heeft u een partner? (ja/nee): ");
                boolean heeftPartner = scanner.next().equalsIgnoreCase("ja");

                System.out.print("Wat is het inkomen van uw partner?: ");
                double partnerInkomen = scanner.nextDouble();

                System.out.print("Heeft u een studieschuld? (ja/nee): ");
                boolean heeftStudieschuld = scanner.next().equalsIgnoreCase("ja");

                System.out.print("Voer de rentevaste periode in (1/5/10/20/30): ");
                int rentevastePeriode = scanner.nextInt();

                System.out.print("Voer uw postcode in: ");
                String postcode = scanner.next();

                double maxHypotheek = berekenMaxTeLenenBedrag(maandinkomen + partnerInkomen, heeftStudieschuld, rentevastePeriode, postcode);

                System.out.println("U kunt maximaal €" + maxHypotheek + " lenen.");
            } else if (keuze == 2) {
                System.out.println("Dank u voor het gebruik van onze service!");
                break;
            } else {
                System.out.println("Ongeldige keuze. Probeer opnieuw.");
            }
        }

        scanner.close();
    }
    public static double berekenMaxTeLenenBedrag(double maandinkomen, boolean heeftStudieschuld, int rentevastePeriode, String postcode) {
        if (maandinkomen <= 0 || (rentevastePeriode != 1 && rentevastePeriode != 5 && rentevastePeriode != 10 && rentevastePeriode != 20 && rentevastePeriode != 30)) {
            throw new IllegalArgumentException("Ongeldige invoer");
        }

        if (postcode.equals("9679") || postcode.equals("9681") || postcode.equals("9682")) {
            return 0.0;
        }

        double rentePercentage = switch (rentevastePeriode) {
            case 1 -> 0.02;
            case 5 -> 0.03;
            case 10 -> 0.035;
            case 20 -> 0.045;
            case 30 -> 0.05;
            default -> throw new IllegalArgumentException("Ongeldige rentevaste periode");
        };

        double maxTeLenen = maandinkomen * 12 * 4.25;

        if (heeftStudieschuld == true) {
            maxTeLenen = maxTeLenen * 0.75;
        }

        double renteBedrag = maxTeLenen * (rentePercentage / 12);

        double aflossingsBedrag = maxTeLenen / (rentevastePeriode * 12);

        double totaalBedrag = renteBedrag + aflossingsBedrag;

        double totaalBetaald = totaalBedrag * rentevastePeriode * 12;

        System.out.println("Uw maximale hypotheekbedrag is: " + maxTeLenen);
        System.out.println("Uw rentebedrag is: " + renteBedrag);
        System.out.println("Uw aflossingsbedrag is: " + aflossingsBedrag);
        System.out.println("Uw totale maandbedrag is: " + totaalBedrag);
        System.out.println("Uw totale hypotheekbedrag is: " + totaalBedrag);


        return totaalBetaald;
    }
}