package Test.Cwiczenia4;

import org.example.Models.Pracownik;
import org.example.Models.Stanowiska;
import org.example.Service.PracownikManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThatList;
import static org.junit.jupiter.api.Assertions.*;
import org.example.Models.Pracownik;
import org.example.Models.Stanowiska;
import org.example.Service.PracownikManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class AwansIPodwyzkaTest {

    private Pracownik pracownik;
    private PracownikManager pracownikManager;

    @BeforeEach
    void setUp() {
        pracownik = new Pracownik("Ktos", "Ktosowski", "@ktos", "FirmaA", Stanowiska.Programista);
        pracownikManager = new PracownikManager();
        pracownikManager.dodajPracownika(pracownik);
    }


    @Test
    void awansDziala(){
        pracownikManager.awansPracownika(pracownik);
        assertThat(pracownik.getWynagrodzenie())
                .isEqualTo(12000)
                .isGreaterThan(8000);

        assertThat(pracownik.getStanowisko(),is(Stanowiska.Manager));
    }

    @Test
    void maxAwans() {
        pracownik.setStanowisko(Stanowiska.Prezes);

        assertThrows(IllegalArgumentException.class, () -> pracownikManager.awansPracownika(pracownik));

        assertThat(pracownik.getStanowisko()).isEqualTo(Stanowiska.Prezes);
    }
    @ParameterizedTest
    @CsvSource({
            "Programista, Manager",
            "Manager, Wiceprezes"
    })
    void testAwansParametrycznie(String start, String expected) {
        pracownik.setStanowisko(Stanowiska.valueOf(start));

        pracownikManager.awansPracownika(pracownik);

        assertThat(pracownik.getStanowisko().name()).isEqualTo(expected);

    }



    @ParameterizedTest
    @CsvSource({
            "10, 8800",
            "20, 9600",
            "5, 8400"
    })
    void testPodwyzekParametrycznie(int procent,int expected) {

        pracownikManager.podwyzkaProcentowa(pracownik, procent);

        assertThat(pracownik.getWynagrodzenie())
                .isEqualTo(expected);

        assertThat(pracownik.getWynagrodzenie(), greaterThan(8000));

    }


    @ParameterizedTest
    @ValueSource(ints = {100, -10, 500})
    void testZlePodwyzki(int procent) {
        assertThrows(IllegalArgumentException.class,
                () -> pracownikManager.podwyzkaProcentowa(pracownik, procent)
        );
    }


}
