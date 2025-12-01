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


    @Test
    void stazWlatachDzisiejszaDataPrzedZatrudnieniemRzucaWyjatek() {
        Pracownik p = new Pracownik("Ktos", "Ktosowski", "@ktos", "FirmaA", Stanowiska.Programista);
        LocalDate zatrudnienie = dzisiaj.plusDays(1);
        p.setDataZatrudnienia(zatrudnienie);

        assertThrows(IllegalArgumentException.class, () -> p.stazWlatach(dzisiaj));
    }

    @Test
    void stazWlatachZeroLatGdyDataZatrudnieniaDzisiaj() {
        Pracownik p = new Pracownik("Ktos", "Ktosowski", "@ktos", "FirmaA", Stanowiska.Programista);
        p.setDataZatrudnienia(dzisiaj);
        assertEquals(0, p.stazWlatach(dzisiaj));
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
    void TestCzyJubileuszFalsz() {
        Pracownik p = new Pracownik("Ktos", "Ktosowski", "@ktos", "FirmaA", Stanowiska.Programista);
        assertThrows(IllegalArgumentException.class, () -> p.czyJubileusz(dzisiaj));
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

    @Test
    void getDataZatrudnieniaDziala(){
        Pracownik p = new Pracownik("Ktos", "Ktosowski", "@ktos", "FirmaA", Stanowiska.Programista);
        p.setDataZatrudnienia(dzisiaj);
        assertThat(p.getDataZatrudnienia(), is(dzisiaj));

    }


    @Test
    void testHamcrestRozszerzone() {
        List<Integer> lata = List.of(1, 5, 10);

        assertThat(lata, hasSize(3));
        assertThat(lata, everyItem(greaterThan(0)));
        assertThat(lata, containsInAnyOrder(10, 1, 5));
        assertThat(lata, not(empty()));

        assertThat(5, allOf(greaterThan(1), lessThan(10)));
    }

    @Test
    void testAssertJ() {
        Pracownik p =
                new Pracownik("Ktos", "Ktosowski", "@ktos", "FirmaA", Stanowiska.Programista);
        p.setDataZatrudnienia(dzisiaj);

        assertThat(p.getDataZatrudnienia())
                .isNotNull()
                .isEqualTo(dzisiaj)
                .isBeforeOrEqualTo(LocalDate.of(2025, 12, 31))
                .isAfter(LocalDate.of(2020, 1, 1));

        List<String> lista = List.of("A", "B", "C");
        assertThat(lista)
                .isNotEmpty()
                .hasSize(3)
                .contains("B")
                .doesNotContain("Z");
    }

    @Test
    void testMatcheryHamcrestNumeryczne() {
        double wynik = 4.5;

        assertThat(wynik, allOf(
                greaterThan(4.0),
                lessThan(5.0),
                not(equalTo(3.0))
        ));

        assertThat(wynik, anyOf(
                is(4.5),
                closeTo(4.6, 0.2)
        ));
    }

}
