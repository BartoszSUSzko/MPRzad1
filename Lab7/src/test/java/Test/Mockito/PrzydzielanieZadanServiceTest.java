package Test.Mockito;

import Test.TaskAssignment.SpyRejestrPrzydzialow;
import org.example.Models.Pracownik;
import org.example.Models.Stanowiska;
import org.example.TaskAssignment.KalendarzDostepnosci;
import org.example.TaskAssignment.PrzydzielanieZadanService;
import org.example.TaskAssignment.RejestrPrzydzialow;
import org.example.TaskAssignment.RepozytoriumKompetencji;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PrzydzielanieZadanServiceTest {

    @Mock
    KalendarzDostepnosci kalendarz;

    @Mock
    RepozytoriumKompetencji repozytoriumKompetencji;

    @Spy
    SpyRejestrPrzydzialow rejestr;

    @InjectMocks
    PrzydzielanieZadanService service;


    @Test
    void powinienZwracacNullGdyBrakDostepnychPracownikow() {
        when(kalendarz.pobierzDostepnychPracownikow())
                .thenReturn(List.of());

        Pracownik wynik = service.przydzielZadanie("Z3", List.of("Java"), 2);

        assertNull(wynik);
        verify(rejestr, never()).zarejestrujPrzydzial(any(), any());
    }

    @Test
    void powinienWybracPierwszegoPracownikaGdyBrakWymaganychKompetencji() {
        Pracownik p = new Pracownik("A","A","@A","FirmaA",Stanowiska.Prezes);

        when(kalendarz.pobierzDostepnychPracownikow()).thenReturn(List.of(p));
        when(repozytoriumKompetencji.pobierzKompetencje(p)).thenReturn(List.of("Java"));

        Pracownik wynik = service.przydzielZadanie("Z4", List.of(), 1);

        assertEquals(p, wynik);
    }

    @Test
    void niePowinienWybracGdyZgodnoscKompetencjiJestCzesciowa() {
        Pracownik p = new Pracownik("A","A","@A","FirmaA",Stanowiska.Prezes);

        when(kalendarz.pobierzDostepnychPracownikow()).thenReturn(List.of(p));
        when(repozytoriumKompetencji.pobierzKompetencje(p))
                .thenReturn(List.of("Java", "HTML"));

        Pracownik wynik = service.przydzielZadanie("Z1", List.of("Java", "SQL"), 5);

        assertNull(wynik);
    }


    @Test
    void powinienRzucicWyjatekGdyBladKalendarza() {
        when(kalendarz.pobierzDostepnychPracownikow())
                .thenThrow(new RuntimeException("Blad IO"));

        assertThrows(RuntimeException.class,
                () -> service.przydzielZadanie("Z1", List.of("Java"), 3));
    }


    @Test
    void powinienRzucicWyjatekGdyBladPobieraniaKompetencji() {
        Pracownik p = new Pracownik("A","A","@A","FirmaA",Stanowiska.Prezes);

        when(kalendarz.pobierzDostepnychPracownikow()).thenReturn(List.of(p));
        when(repozytoriumKompetencji.pobierzKompetencje(p))
                .thenThrow(new RuntimeException("Blad repozytorium"));

        assertThrows(RuntimeException.class,
                () -> service.przydzielZadanie("Z1", List.of("Java"), 3));
    }

    @Test
    void powinienPrzydzielicPracownikaJesliMaKompetencje() {


        Pracownik p = new Pracownik("A","A","@A","FirmaA",Stanowiska.Prezes);

        List<String> wymagane = List.of("Java", "SQL");

        when(kalendarz.pobierzDostepnychPracownikow())
                .thenReturn(List.of(p));

        when(repozytoriumKompetencji.pobierzKompetencje(p))
                .thenReturn(List.of("Java", "SQL", "Git"));


        Pracownik wynik = service.przydzielZadanie("Z1", wymagane, 5);


        assertEquals(p, wynik);

        verify(rejestr, times(1))
                .zarejestrujPrzydzial("Z1", p);

        verify(kalendarz, times(1))
                .pobierzDostepnychPracownikow();
    }

    @Test
    void niePowinienPrzydzielicJesliBrakKompetencji() {


        Pracownik p = new Pracownik("A","A","@A","FirmaA",Stanowiska.Prezes);

        when(kalendarz.pobierzDostepnychPracownikow())
                .thenReturn(List.of(p));

        when(repozytoriumKompetencji.pobierzKompetencje(p))
                .thenReturn(List.of("Java"));


        Pracownik wynik = service.przydzielZadanie("Z2",
                List.of("Java", "SQL"), 3);


        assertNull(wynik);

        verify(rejestr, never()).zarejestrujPrzydzial(any(), any());
    }

    @Test
    void powinienRzucicWyjatekPrzyBladRejestracji() {
        Pracownik p = new Pracownik("A","A","@A","FirmaA",Stanowiska.Prezes);
        List<String> wymagane = List.of("Java");

        when(kalendarz.pobierzDostepnychPracownikow()).thenReturn(List.of(p));
        when(repozytoriumKompetencji.pobierzKompetencje(p)).thenReturn(List.of("Java"));

        doThrow(new RuntimeException("Blad rejestracji"))
                .when(rejestr).zarejestrujPrzydzial("Z1", p);

        assertThrows(RuntimeException.class,
                () -> service.przydzielZadanie("Z1", wymagane, 5));

        verify(rejestr, times(1)).zarejestrujPrzydzial("Z1", p);
    }


    @Captor
    ArgumentCaptor<Pracownik> pracownikCaptor;

    @Captor
    ArgumentCaptor<String> zadanieCaptor;

    @Test
    void powinienPrzechwycicArgumentyRejestracji() {
        Pracownik p = new Pracownik("A","A","@A","FirmaA",Stanowiska.Prezes);
        List<String> wymagane = List.of("Java");

        when(kalendarz.pobierzDostepnychPracownikow()).thenReturn(List.of(p));
        when(repozytoriumKompetencji.pobierzKompetencje(p)).thenReturn(List.of("Java"));

        service.przydzielZadanie("Z1", wymagane, 5);

        verify(rejestr).zarejestrujPrzydzial(zadanieCaptor.capture(), pracownikCaptor.capture());

        assertEquals("Z1", zadanieCaptor.getValue());
        assertEquals(p, pracownikCaptor.getValue());
    }

    @Test
    void spyPowinienLogowacRzeczywistyPrzydzial() {
        Pracownik p = new Pracownik("A","A","@A","FirmaA",Stanowiska.Prezes);

        when(kalendarz.pobierzDostepnychPracownikow()).thenReturn(List.of(p));
        when(repozytoriumKompetencji.pobierzKompetencje(p))
                .thenReturn(List.of("Java"));


        service.przydzielZadanie("Z99", List.of("Java"), 1);

        assertEquals("Z99:@A", rejestr.getLog().get(0));

        verify(rejestr, times(1)).zarejestrujPrzydzial("Z99", p);
    }


}
