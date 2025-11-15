package Test.Cwiczenia3;

import org.example.Models.Pracownik;
import org.example.Models.Stanowiska;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PracownikTest {

    private Pracownik pracownik;

    @BeforeEach
    void setUp() {
        pracownik = new Pracownik("Ktos", "Ktosowski", "@ktos", "FirmaA", Stanowiska.Programista);
    }

    @Test
    void KonstuktorDziala() {
        assertEquals("Ktos", pracownik.getImie());
        assertEquals("Ktosowski", pracownik.getNazwisko());
        assertEquals("@ktos", pracownik.getEmail());
        assertEquals("FirmaA", pracownik.getNazwaFirmy());
        assertEquals(Stanowiska.Programista, pracownik.getStanowisko());
        assertEquals(8000, pracownik.getWynagrodzenie());
    }

    @Test
    void PustyString() {
        assertThrows(IllegalArgumentException.class,
                () -> new Pracownik("", "Nowak", "x@x.pl", "FirmaA", Stanowiska.Stazysta));
    }

    @Test
    void equalsDlaEmaila() {
        Pracownik p2 = new Pracownik("Adam", "Nowak", "@ktos", "FirmaB", Stanowiska.Stazysta);
        assertEquals(pracownik, p2);
        assertEquals(pracownik.hashCode(), p2.hashCode());
    }

    @Test
    void toStringWszystkieDane() {
        String tekst = pracownik.toString();
        assertTrue(tekst.contains("Ktos"));
        assertTrue(tekst.contains("Ktosowski"));
        assertTrue(tekst.contains("FirmaA"));
        assertTrue(tekst.contains("Programista"));
    }
}
