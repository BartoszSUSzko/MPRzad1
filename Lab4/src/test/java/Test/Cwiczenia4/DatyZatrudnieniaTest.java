package Test.Cwiczenia4;

import org.example.Models.Pracownik;
import org.example.Models.Stanowiska;
import org.example.Service.PracownikManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DatyZatrudnieniaTest {

    private LocalDate dzisiaj;

    @BeforeEach
    void setUp() {
        dzisiaj = LocalDate.of(2025, 11, 15);
    }

    @ParameterizedTest
    @CsvSource({
            "2018-11-15, 7",
            "2020-01-01, 5",
            "2016-02-29, 9",
            "2023-11-15, 2"
    })
    void testStazWlatach(String dataZatrudnienia, int expectedLata) {
        Pracownik p = new Pracownik("Ktos", "Ktosowski", "@ktos", "FirmaA", Stanowiska.Programista);
        p.setDataZatrudnienia(LocalDate.parse(dataZatrudnienia));

        assertThat(p.stazWlatach(dzisiaj), is(expectedLata));
    }

    @ParameterizedTest
    @CsvSource({
            "2018-11-15, false",
            "2020-11-15, true",
            "2015-11-15, true",
            "2010-11-15, true"
    })
    void testCzyJubileusz(String dataZatrudnienia, boolean expected) {
        Pracownik p = new Pracownik("Ktos", "Ktosowski", "@ktos", "FirmaA", Stanowiska.Programista);
        p.setDataZatrudnienia(LocalDate.parse(dataZatrudnienia));

        assertThat(p.czyJubileusz(dzisiaj), is(expected));
    }
    @Test
    void ustawienieDatyPoPrzyszlosci() {
        Pracownik p = new Pracownik("Ktos", "Ktosowski", "@ktos", "FirmaA", Stanowiska.Programista);
        LocalDate przyszlosc = LocalDate.now().plusDays(1);
        assertThrows(IllegalArgumentException.class, () -> p.setDataZatrudnienia(przyszlosc));
    }

    @Test
    void ustawienieNullDaty() {
        Pracownik p = new Pracownik("Ktos", "Ktosowski", "@ktos", "FirmaA", Stanowiska.Programista);
        assertThrows(IllegalArgumentException.class, () -> p.setDataZatrudnienia(null));
    }


}
