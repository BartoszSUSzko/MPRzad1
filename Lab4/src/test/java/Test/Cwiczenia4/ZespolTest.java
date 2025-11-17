package Test.Cwiczenia4;
import org.example.Models.Pracownik;
import org.example.Models.Stanowiska;
import org.example.Models.Zespol;
import org.example.Service.PracownikManager;
import org.example.Service.ZespolManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class ZespolTest {

    private ZespolManager zespolManager;

    @BeforeEach
    void setup() {
        zespolManager = new ZespolManager();
    }

    @ParameterizedTest
    @MethodSource("przeniesieniaPracownikow")
    void dzialaPrzeniesieniaPracownikow(Pracownik pracownik, Zespol zrodlo, Zespol cel, boolean expectedSuccess) {

        if (expectedSuccess) {
            zespolManager.przeniesPracownika(pracownik, zrodlo, cel);

            assertThat(cel.getPracownicy())
                    .hasSize(2)
                    .isNotEmpty()
                    .contains(pracownik);

        } else {
            assertThrows(IllegalArgumentException.class,
                    () -> zespolManager.przeniesPracownika(pracownik, zrodlo, cel));
        }
    }

    private static Stream<org.junit.jupiter.params.provider.Arguments> przeniesieniaPracownikow() {
        Pracownik p1 = new Pracownik("Ktos", "Ktosowski", "@ktos", "FirmaA", Stanowiska.Programista);
        Pracownik p2 = new Pracownik("A","A","@A","FirmaA",Stanowiska.Prezes);
        Zespol zespol1 = new Zespol("zespol1", 5);
        Zespol zespol2 = new Zespol("zespol2", 5);

        zespol1.dodajPracownika(p1);
        zespol2.dodajPracownika(p2);

        return Stream.of(
                Arguments.of(p1, zespol1, zespol2, true),
                Arguments.of(p1, zespol1, zespol1, false),
                Arguments.of(p2, zespol2, zespol2, false),
                Arguments.of(p2, zespol1, zespol2, false)
        );
    }

    @Test
    public void przeniesieniePracownikaDziala() {
        Pracownik p1 = new Pracownik("Ktos", "Ktosowski", "@ktos", "FirmaA", Stanowiska.Programista);

        Zespol zespol1 = new Zespol("zespol1", 5);
        Zespol zespol2 = new Zespol("zespol2", 5);

        zespol1.dodajPracownika(p1);

        zespolManager.przeniesPracownika(p1, zespol1, zespol2);

        assertThat(zespol2.getPracownicy(), contains(p1));
    }

    @Test
    public void zlePrzeniesieniePracownika() {
        Pracownik p1 = new Pracownik("Ktos", "Ktosowski", "@ktos", "FirmaA", Stanowiska.Programista);

        Zespol zespol1 = new Zespol("zespol1", 5);
        Zespol zespol2 = new Zespol("zespol2", 5);

        zespol2.dodajPracownika(p1);

        assertThrows(IllegalArgumentException.class,
                () -> zespolManager.przeniesPracownika(p1, zespol1, zespol2));
    }

    @Test
    public void zleStworzenieZespolu() {
        assertThrows(IllegalArgumentException.class, () -> new Zespol("", 5));
        assertThrows(IllegalArgumentException.class, () -> new Zespol(null, 5));
        assertThrows(IllegalArgumentException.class, () -> new Zespol("zespol1", 0));
    }

    @Test
    public void czyZroznicowanaGrupa(){
        Pracownik pracownik1 = new Pracownik("A","A","@A","FirmaA",Stanowiska.Manager);
        Pracownik pracownik2 = new Pracownik("B","B","@B","FirmaB",Stanowiska.Programista);
        Pracownik pracownik3 = new Pracownik("C","C","@C","FirmaC",Stanowiska.Stazysta);

        Zespol zespol1 = new Zespol("zespol1", 5);

        zespol1.dodajPracownika(pracownik1);
        zespol1.dodajPracownika(pracownik2);
        zespol1.dodajPracownika(pracownik3);

        assertTrue(zespol1.czyRownowagaStanowisk());

    }

    @Test
    public void zleZroznicowanaGrupa(){
        Pracownik pracownik1 = new Pracownik("A","A","@A","FirmaA",Stanowiska.Programista);
        Pracownik pracownik2 = new Pracownik("B","B","@B","FirmaB",Stanowiska.Programista);
        Pracownik pracownik3 = new Pracownik("C","C","@C","FirmaC",Stanowiska.Programista);

        Zespol zespol1 = new Zespol("zespol1", 5);

        zespol1.dodajPracownika(pracownik1);
        zespol1.dodajPracownika(pracownik2);
        zespol1.dodajPracownika(pracownik3);

        assertFalse(zespol1.czyRownowagaStanowisk());
    }

    @Test
    public void zleZrobionaGrupa(){
        assertThrows(IllegalArgumentException.class, () -> new Zespol("", 5));
        assertThrows(IllegalArgumentException.class, () -> new Zespol("zespol1", 0));
    }



}
