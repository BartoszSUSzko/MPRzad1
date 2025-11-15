package Test.Cwiczenia3;

import org.example.Models.Stanowiska;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StanowiskaTest {

    @Test
    void kazdeStanowiskoMaPoprawneWartosci() {
        assertEquals(25000, Stanowiska.Prezes.getWynagrodzenieBazowe());
        assertEquals(18000, Stanowiska.Wiceprezes.getWynagrodzenieBazowe());
        assertEquals(12000, Stanowiska.Manager.getWynagrodzenieBazowe());
        assertEquals(8000, Stanowiska.Programista.getWynagrodzenieBazowe());
        assertEquals(3000, Stanowiska.Stazysta.getWynagrodzenieBazowe());

        assertEquals(5, Stanowiska.Prezes.getPoziomHierarchii());
        assertEquals(4, Stanowiska.Wiceprezes.getPoziomHierarchii());
        assertEquals(3, Stanowiska.Manager.getPoziomHierarchii());
        assertEquals(2, Stanowiska.Programista.getPoziomHierarchii());
        assertEquals(1, Stanowiska.Stazysta.getPoziomHierarchii());
    }

    @Test
    void metodaPorownaniaHierarchiiDzialaPoprawnie() {
        assertTrue(Stanowiska.Prezes.wyzszeOd(Stanowiska.Stazysta));
        assertTrue(Stanowiska.Wiceprezes.wyzszeOd(Stanowiska.Programista));
        assertFalse(Stanowiska.Stazysta.wyzszeOd(Stanowiska.Prezes));
        assertFalse(Stanowiska.Programista.wyzszeOd(Stanowiska.Wiceprezes));
    }
}
