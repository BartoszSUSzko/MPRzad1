package Test.Cwiczenia4;

import org.example.Models.Pracownik;
import org.example.Models.Stanowiska;
import org.example.Service.PracownikManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class OcenyPracownikowTest {


    @ParameterizedTest
    @CsvSource({
            "'5', 5.0",
            "'3,4,5', 4.0",
            "'1,1,1,1', 1.0",
            "'2,3', 2.5",
            "'4,4,4,4,4', 4.0"
    })
    void testSredniaOcen(String ocenyCsv, double expectedSrednia) {
        Pracownik p = new Pracownik("Ktos", "Ktosowski", "@ktos", "FirmaA", Stanowiska.Programista);


        String[] ocenyArray = ocenyCsv.split(",");
        for (String ocenaStr : ocenyArray) {
            int ocena = Integer.parseInt(ocenaStr.trim());
            p.dodajOcene(ocena);
        }


        assertThat(p.getOcenyRoczne())
                .hasSize(ocenyCsv.split(",").length)
                .allMatch(ocena -> ocena >= 1 && ocena <= 5);


        assertThat(p.sredniaOcen()).isEqualTo(expectedSrednia);
    }

    @Test
    void testNajlepsiPracownicy() {
        PracownikManager manager = new PracownikManager();

        Pracownik p1 = new Pracownik("A", "A", "@a", "FirmaA", Stanowiska.Prezes);
        Pracownik p2 = new Pracownik("B", "B", "@b", "FirmaA", Stanowiska.Programista);
        Pracownik p3 = new Pracownik("C", "C", "@c", "FirmaA", Stanowiska.Manager);

        p1.dodajOcene(5);
        p1.dodajOcene(4);

        p2.dodajOcene(3);
        p2.dodajOcene(3);

        p3.dodajOcene(5);
        p3.dodajOcene(5);

        manager.dodajPracownika(p1);
        manager.dodajPracownika(p2);
        manager.dodajPracownika(p3);

        List<Pracownik> najlepsi = manager.najlepsiPracownicy();

        assertThat(najlepsi)
                .hasSize(1)
                .contains(p3);
    }
    @Test
    void testHistoriaOcen() {
        Pracownik p = new Pracownik("Ktos", "Ktosowski", "@ktos", "FirmaA", Stanowiska.Programista);

        p.dodajOcene(5);
        p.dodajOcene(3);
        p.dodajOcene(4);

        assertThat(p.getOcenyRoczne())
                .isNotEmpty()
                .containsExactly(5, 3, 4);
    }

    @ParameterizedTest
    @ValueSource(ints = {6, -1, 0, 100})
    void zleOceny(int ocena){
        Pracownik p = new Pracownik("Ktos", "Ktosowski", "@ktos", "FirmaA", Stanowiska.Programista);
        assertThrows(IllegalArgumentException.class, () -> p.dodajOcene(ocena));
    }






}
