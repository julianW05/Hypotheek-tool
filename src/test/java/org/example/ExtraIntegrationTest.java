package org.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class ExtraIntegrationTest {

    @Test
    public void testMaxHypotheekMetVoorafIngesteldeWaarden() {
        // Definieer vooraf ingestelde waarden
        double maandinkomen = 5000;
        boolean heeftPartner = true;
        double partnerInkomen = 2000;
        boolean heeftStudieschuld = false;
        int rentevastePeriode = 20;
        String postcode = "1234";

        // Roep de methode rechtstreeks aan met de vooraf ingestelde waarden
        double result = App.berekenMaxTeLenenBedrag(maandinkomen + partnerInkomen, heeftStudieschuld, rentevastePeriode, postcode);

        // Controleer of het resultaat overeenkomt met de verwachte waarde
        assertEquals(678300.0, result, 0.01);
    }
}
