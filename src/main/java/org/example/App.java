package org.example;

import java.util.Scanner;

public class App {
    public static double berekenMaxTeLenenBedrag(double maandinkomen, boolean heeftPartner, boolean heeftStudieschuld, int rentevastePeriode, String postcode) {
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

        double maxHypotheek = maandinkomen * 12 * rentevastePeriode / (1 - Math.pow(1 + rentePercentage / 12, -rentevastePeriode * 12));

        if (heeftPartner) {
            maxHypotheek += maxHypotheek * 0.2;
        }
        if (heeftStudieschuld) {
            maxHypotheek *= 0.75;
        }

        return Math.round(maxHypotheek * 100) / 100.0;
    }
}