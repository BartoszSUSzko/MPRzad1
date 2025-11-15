package Test.Cwiczenia4;

import org.example.Models.Pracownik;
import org.example.Models.Stanowiska;
import org.example.Service.PracownikManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TDD {

    private Pracownik pracownik;

    @BeforeEach
    void setUp() {
        pracownik = new Pracownik("Ktos", "Ktosowski", "@ktos", "FirmaA", Stanowiska.Programista);
    }


    @Test
    void awansDziala(){
        PracownikManager.awansPracownika(pracownik);
        assertTrue(Stanowiska.Manager.equals(pracownik.getStanowisko()));
        assertEquals(12000, pracownik.getWynagrodzenie());
    }

    @Test
    void maxAwans(){
        pracownik.setStanowisko(Stanowiska.Prezes);
        assertThrows(IllegalArgumentException.class,
                () -> PracownikManager.awansStanowiska(pracownik));
    }

    @Test
    void podwyzkaDziala(){
        PracownikManager.podwyzkaProcentowa(pracownik,20);
        assertEquals(1.20 * 8000,pracownik.getWynagrodzenie());
    }

    @Test
    void podwyzkaZaduza(){
        assertThrows(IllegalArgumentException.class,
                () -> PracownikManager.podwyzkaProcentowa(pracownik,100));
    }
    @Test podwyzkaUjemna(){
        assertThrows(IllegalArgumentException.class,
                () -> PracownikManager.podwyzkaProcentowa(pracownik,-100));
    }
}
