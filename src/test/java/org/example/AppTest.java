package org.example;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AppTest {

    @Test
    public void testMaxHypotheekMetPartnerEnStudieschuld() {
        double maxHypotheek = App.berekenMaxTeLenenBedrag(5000, true, 20, "1234");
        assertEquals(363375.0, maxHypotheek, 0.01);
    }

    @Test
    public void testMaxHypotheekZonderPartnerEnGeenStudieschuld() {
        double maxHypotheek = App.berekenMaxTeLenenBedrag(6000, false, 10, "1234");
        assertEquals(413100.0, maxHypotheek, 0.01);
    }


    @Test
    public void testMaxHypotheekMetOnjuisteRentevastePeriode() {
        try {
            App.berekenMaxTeLenenBedrag(4000, false, 7, "1234");
            fail("Moet een IllegalArgumentException genereren voor ongeldige rentevaste periode");
        } catch (IllegalArgumentException e) {
            // Verwachtte uitzondering
        }
    }

    @Test
    public void testMaxHypotheekMetAardbevingsgebied() {
        double maxHypotheek = App.berekenMaxTeLenenBedrag(5500, false, 30, "9679");
        assertEquals(0.0, maxHypotheek, 0.01);
    }
    @Test
    public void testMaxHypotheekMetVerkeerdeInvoer() {
        try {
            App.berekenMaxTeLenenBedrag(-1000, false, 5, "1234");
            fail("Moet een IllegalArgumentException genereren voor ongeldig maandinkomen");
        } catch (IllegalArgumentException e) {
            // Verwachtte uitzondering

        }
    }

    // Integration tests

    // Integratietest voor volledige hypotheekberekening met correcte invoer:
    @Test
    public void testFullHypotheekBerekeningMetCorrecteInvoer() {
        // Simuleer gebruikersinvoer
        String input = "5000\nja\n2000\nnee\n30\n1234";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Vang de standaarduitvoer op
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Voer de applicatie uit
        App.main(new String[0]);

        // Zet de standaarduitvoer terug naar de oorspronkelijke staat
        System.setOut(originalOut);

        // Vang de uitvoer op en controleer deze
        String actualOutput = outputStream.toString();
        String expectedOutput = "Totaal betaald na 30 jaar: 892500.00";
        assertTrue(actualOutput.contains(expectedOutput));
    }

    // Integratietest voor berekening met studieschuld:
    @Test
    public void testHypotheekMetStudieschuld() {
        // Simuleer gebruikersinvoer
        String input = "5000\nnee\nja\n30\n1234";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Vang de standaarduitvoer op
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Voer de applicatie uit
        App.main(new String[0]);

        // Zet de standaarduitvoer terug naar de oorspronkelijke staat
        System.setOut(originalOut);

        // Vang de uitvoer op en controleer deze
        String actualOutput = outputStream.toString();
        String expectedOutput = "Totaal betaald na 30 jaar: 478125.00";
        assertTrue(actualOutput.contains(expectedOutput));
    }

    // Integratietest voor aardbevingsgebied:
    @Test
    public void testHypotheekInAardbevingsgebied() {
        // Simuleer gebruikersinvoer met een aardbevingsgebied postcode
        String input = "5000\n0\n0\n30\n9679";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Vang de standaarduitvoer op
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Voer de applicatie uit
        App.main(new String[0]);

        // Zet de standaarduitvoer terug naar de oorspronkelijke staat
        System.setOut(originalOut);

        // Vang de uitvoer op en controleer deze
        String actualOutput = outputStream.toString();
        String expectedOutput = "Totaal betaald na 30 jaar: 0.00";
        assertTrue(actualOutput.contains(expectedOutput));
    }
}