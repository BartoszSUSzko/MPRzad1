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
                () -> new Pracownik("", "ktosowski", "@ktos", "FirmaA", Stanowiska.Stazysta));
        assertThrows(IllegalArgumentException.class,
                () -> new Pracownik("ktos", "", "@ktos", "FirmaA", Stanowiska.Stazysta));
        assertThrows(IllegalArgumentException.class,
                () -> new Pracownik("ktos", "ktosowski", "", "FirmaA", Stanowiska.Stazysta));
    }
    @Test
    void nullString() {
        assertThrows(IllegalArgumentException.class,
                () -> new Pracownik(null, "ktosowski", "@ktos", "FirmaA", Stanowiska.Stazysta));
        assertThrows(IllegalArgumentException.class,
                () -> new Pracownik("ktos", null, "@ktos", "FirmaA", Stanowiska.Stazysta));
        assertThrows(IllegalArgumentException.class,
                () -> new Pracownik("ktos", "ktosowski", null, "FirmaA", Stanowiska.Stazysta));
    }

    @Test
    void setNazwaFirmynull(){
        assertThrows(IllegalArgumentException.class,
                () -> pracownik.setNazwaFirmy(null));

    }
    @Test
    void setNazwaFirmypuste(){
        assertThrows(IllegalArgumentException.class,
                () -> pracownik.setNazwaFirmy(""));}


    @Test
    void SetNazwaFirmyNull(){
        assertThrows(IllegalArgumentException.class, () -> pracownik.setNazwaFirmy(null));
    }

    @Test
    void SetNazwaFirmyPusty(){
        assertThrows(IllegalArgumentException.class, () -> pracownik.setNazwaFirmy(""));
    }

    @Test
    void SetStanowiskoNull(){
        assertThrows(IllegalArgumentException.class, () -> pracownik.setStanowisko(null));
    }

    @Test
    void WynagrodzenieUjemne(){
        assertThrows(IllegalArgumentException.class, () -> pracownik.setWynagrodzenie(-20));
    }



    @Test
    void equalsDlaEmaila() {
        Pracownik p2 = new Pracownik("A", "A", "@ktos", "FirmaB", Stanowiska.Stazysta);
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
