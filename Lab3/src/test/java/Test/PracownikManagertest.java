package Test;

import org.example.Models.Pracownik;
import org.example.Models.Stanowiska;
import org.example.Service.PracownikManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PracownikManagertest {



    private PracownikManager manager;

    @BeforeEach
    void setUp() {
        manager = new PracownikManager();
    }



    @Test
    void dodanieNowegoPracownika() {
        Pracownik pracownik1 = new Pracownik("erma","ermowska", "@erma","firmaA",Stanowiska.Programista);
        manager.dodajPracownika(pracownik1);
        assertTrue(manager.getListaPracownikow().contains(pracownik1));
    }

    @Test
    void dodanieNullPracownikaPowinnoRzucicWyjatek() {
        assertThrows(IllegalArgumentException.class, () -> {
            manager.dodajPracownika(null);
        });
    }

    @Test
    void dodaniePracownikaZDuplikatem() {
        Pracownik pracownik1 = new Pracownik("Erma", "Ermowska", "@erma", "FirmaA", Stanowiska.Programista);
        manager.dodajPracownika(pracownik1);

        assertThrows(IllegalArgumentException.class,() -> manager.dodajPracownika(pracownik1)
        );
    }

    @Test
    void MaxymalneWynagrodzenie(){
        Pracownik pracownik4 = new Pracownik("D","D","@D","FirmaD",Stanowiska.Prezes);
        Pracownik pracownik1 = new Pracownik("A","A","@A","FirmaA",Stanowiska.Prezes);
        Pracownik pracownik2 = new Pracownik("B","B","@B","FirmaB",Stanowiska.Stazysta);
        Pracownik pracownik3 = new Pracownik("C","C","@C","FirmaC",Stanowiska.Wiceprezes);
        manager.dodajPracownika(pracownik3);
        manager.dodajPracownika(pracownik2);
        manager.dodajPracownika(pracownik1);
        manager.dodajPracownika(pracownik4);

        List<Pracownik> listamax = new ArrayList<>();
        listamax.add(pracownik1);
        listamax.add(pracownik4);
        assertEquals(listamax,manager.pracownicyZMaxWynagrodzeniem());
    }


    @Test
    void poprawneGrupowanie() {
        Pracownik pracownik1 = new Pracownik("A","A","@A","FirmaA",Stanowiska.Prezes);
        Pracownik pracownik2 = new Pracownik("B","B","@B","FirmaA",Stanowiska.Prezes);
        Pracownik pracownik3 = new Pracownik("C","C","@C","FirmaB",Stanowiska.Programista);
        Pracownik pracownik4 = new Pracownik("D","D","@D","FirmaA",Stanowiska.Programista);

        manager.dodajPracownika(pracownik1);
        manager.dodajPracownika(pracownik2);
        manager.dodajPracownika(pracownik3);
        manager.dodajPracownika(pracownik4);


        Map<Stanowiska, List<Pracownik>> grupa = manager.grupowaniePracownikow();

        assertEquals(2, grupa.size());

        List<Pracownik> prezesi = grupa.get(Stanowiska.Prezes);
        assertTrue(prezesi.contains(pracownik1));
        assertTrue(prezesi.contains(pracownik2));
        assertEquals(2, prezesi.size());

        List<Pracownik> programisci = grupa.get(Stanowiska.Programista);
        assertTrue(programisci.contains(pracownik3));
        assertTrue(programisci.contains(pracownik4));
        assertEquals(2, programisci.size());
    }

    @Test
    void sortowanie() {
        Pracownik pracownik1 = new Pracownik("A","A","@A","FirmaA",Stanowiska.Prezes);
        Pracownik pracownik2 = new Pracownik("B","B","@B","FirmaB",Stanowiska.Prezes);
        Pracownik pracownik3 = new Pracownik("C","C","@C","FirmaC",Stanowiska.Prezes);
        manager.dodajPracownika(pracownik3); // C
        manager.dodajPracownika(pracownik2); // B
        manager.dodajPracownika(pracownik1); // A
        manager.sortujPracownikow();

        List<Pracownik> pracownicy = manager.getListaPracownikow();

        assertEquals("A", pracownicy.get(0).getNazwisko());
        assertEquals("B", pracownicy.get(1).getNazwisko());
        assertEquals("C", pracownicy.get(2).getNazwisko());
    }


    @Test
    void srednieWynagrodzenie() {
        Pracownik pracownik1 = new Pracownik("A","A","@A","FirmaA",Stanowiska.Prezes);
        Pracownik pracownik2 = new Pracownik("B","B","@B","FirmaB",Stanowiska.Programista);
        Pracownik pracownik3 = new Pracownik("C","C","@C","FirmaC",Stanowiska.Stazysta);
        manager.dodajPracownika(pracownik3);
        manager.dodajPracownika(pracownik2);
        manager.dodajPracownika(pracownik1);

        float wynagrodzenie = (pracownik1.getWynagrodzenie() + pracownik2.getWynagrodzenie() + pracownik3.getWynagrodzenie())/3;

        assertEquals(wynagrodzenie, manager.srednieWynagrodzenie());
    }
    @Test
    void srednieWynagrodzenienull() {
        assertEquals(0, manager.srednieWynagrodzenie());
    }

    @Test
    void pracownicyZMaxWynagrodzeniemDlaJednegoPracownika() {
        Pracownik p1 = new Pracownik("A","A","@A","FirmaA",Stanowiska.Prezes);
        manager.dodajPracownika(p1);

        List<Pracownik> maxWynagrodzenie = manager.pracownicyZMaxWynagrodzeniem();
        assertEquals(1, maxWynagrodzenie.size());
        assertEquals(p1, maxWynagrodzenie.get(0));
    }
    @Test
    void wyswietlPracownikowWFirmie() {
        Pracownik p1 = new Pracownik("A","A","@A","FirmaA",Stanowiska.Prezes);
        Pracownik p2 = new Pracownik("B","B","@B","FirmaB",Stanowiska.Programista);
        Pracownik p3 = new Pracownik("C","C","@C","FirmaA",Stanowiska.Stazysta);

        manager.dodajPracownika(p1);
        manager.dodajPracownika(p2);
        manager.dodajPracownika(p3);

        List<Pracownik> firmaA = new ArrayList<>();
        for (Pracownik p : manager.getListaPracownikow()) {
            if(p.getNazwaFirmy().equals("FirmaA")) {
                firmaA.add(p);
            }
        }

        assertTrue(firmaA.contains(p1));
        assertTrue(firmaA.contains(p3));
        assertFalse(firmaA.contains(p2));
        assertEquals(2, firmaA.size());
    }
}
