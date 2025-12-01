package Test.Cwiczenia3;

import org.example.Models.Pracownik;
import org.example.Models.Stanowiska;
import org.example.Models.StatystykiFirmy;
import org.example.Service.PracownikManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PracownikManagerTest {



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
        assertThrows(IllegalArgumentException.class, () -> manager.dodajPracownika(null));
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
    void liczbaPracownikowDzialaPoprawnie(){
        Pracownik pracownik1 = new Pracownik("A","A","@A","FirmaA",Stanowiska.Prezes);
        Pracownik pracownik2 = new Pracownik("B","B","@B","FirmaB",Stanowiska.Programista);
        Pracownik pracownik3 = new Pracownik("C","C","@C","FirmaC",Stanowiska.Stazysta);
        Pracownik pracownik4 = new Pracownik("D","D","@D","FirmaC",Stanowiska.Stazysta);
        manager.dodajPracownika(pracownik3);
        manager.dodajPracownika(pracownik2);
        manager.dodajPracownika(pracownik1);
        manager.dodajPracownika(pracownik4);

        Map<Stanowiska, Integer> wynik = manager.liczbaPracownikowNaStanowisku();


        assertEquals(1, wynik.get(Stanowiska.Programista));
        assertEquals(1, wynik.get(Stanowiska.Prezes));
        assertEquals(2, wynik.get(Stanowiska.Stazysta));



        assertEquals(3, wynik.size());
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
    void WyswietlPracownikowDziala(){
        Pracownik p1 = new Pracownik("A","A","@A","FirmaA",Stanowiska.Prezes);
        Pracownik p2 = new Pracownik("B","B","@B","FirmaB",Stanowiska.Programista);

        manager.dodajPracownika(p1);
        manager.dodajPracownika(p2);

        List<Pracownik> lista = new ArrayList<>();
        lista.add(p1);
        lista.add(p2);

        assertDoesNotThrow(() -> manager.wyswietlPracownik());
    }


    @Test
    void wyswietlPracownikowWFirmieNull(){
        assertThrows(IllegalArgumentException.class,() -> manager.wyswietlPracownikowWFirmie(null));
    }



    @Test
    void poprawnyKonstruktor() {
        Pracownik p = new Pracownik("A","A","a@f.pl","FirmaA", Stanowiska.Programista);
        StatystykiFirmy s = new StatystykiFirmy("FirmaA", 1, 8000, List.of(p));
        assertEquals("FirmaA", s.getNazwaFirmy());
        assertEquals(1, s.getLiczbaPracownikow());
        assertEquals(8000, s.getSrednieWynagrodzenie());
        assertEquals(List.of(p), s.getPracownicyZNajwyzszymWynagrodzeniem());
    }

    @Test
    void setNazwaFirmyNullLubPustaRzucaWyjatek() {
        StatystykiFirmy s = new StatystykiFirmy("FirmaA",1,1000,List.of(new Pracownik("A","A","a@f.pl","FirmaA", Stanowiska.Programista)));
        assertThrows(IllegalArgumentException.class, () -> s.setNazwaFirmy(null));
        assertThrows(IllegalArgumentException.class, () -> s.setNazwaFirmy(" "));
    }

    @Test
    void setLiczbaPracownikowMniejszaZeroRzucaWyjatek() {
        StatystykiFirmy s = new StatystykiFirmy("FirmaA",1,1000,List.of(new Pracownik("A","A","a@f.pl","FirmaA", Stanowiska.Programista)));
        assertThrows(IllegalArgumentException.class, () -> s.setLiczbaPracownikow(-1));
    }

    @Test
    void validateSalaryConsistencyDziałaPoprawnie() {

        Pracownik p1 = new Pracownik("A", "A", "@a", "FirmaA", Stanowiska.Prezes);
        p1.setWynagrodzenie(1000);

        Pracownik p2 = new Pracownik("B", "B", "@b", "FirmaB", Stanowiska.Stazysta);
        p2.setWynagrodzenie(3000);

        manager.dodajPracownika(p1);
        manager.dodajPracownika(p2);

        List<Pracownik> wynik = manager.validateSalaryConsistency();

        assertEquals(1, wynik.size());
        assertTrue(wynik.contains(p1));
    }

    @Test
    void getCompanyStatisticsDziałaPoprawnie() {
        Pracownik p1 = new Pracownik("A", "A", "@a", "FirmaA", Stanowiska.Prezes);
        Pracownik p2 = new Pracownik("B", "B", "@b", "FirmaA", Stanowiska.Manager);
        Pracownik p3 = new Pracownik("C", "C", "@c", "FirmaB", Stanowiska.Programista);

        manager.dodajPracownika(p1);
        manager.dodajPracownika(p2);
        manager.dodajPracownika(p3);

        Map<String, StatystykiFirmy> statystyki = manager.getCompanyStatistics();

        assertEquals(2, statystyki.size());

        StatystykiFirmy firmaA = statystyki.get("FirmaA");
        assertEquals("FirmaA", firmaA.getNazwaFirmy());
        assertEquals(2, firmaA.getLiczbaPracownikow());
        float expectedSredniaA = (p1.getWynagrodzenie() + p2.getWynagrodzenie()) / 2f;
        assertEquals(expectedSredniaA, firmaA.getSrednieWynagrodzenie());
        assertTrue(firmaA.getPracownicyZNajwyzszymWynagrodzeniem().contains(p1));

        StatystykiFirmy firmaB = statystyki.get("FirmaB");
        assertEquals("FirmaB", firmaB.getNazwaFirmy());
        assertEquals(1, firmaB.getLiczbaPracownikow());
        assertEquals(p3.getWynagrodzenie(), firmaB.getSrednieWynagrodzenie());
        assertEquals(List.of(p3), firmaB.getPracownicyZNajwyzszymWynagrodzeniem());
    }


    @Test
    void setSrednieWynagrodzenieMniejszeZeroRzucaWyjatek() {
        StatystykiFirmy s = new StatystykiFirmy("FirmaA",1,1000,List.of(new Pracownik("A","A","a@f.pl","FirmaA", Stanowiska.Programista)));
        assertThrows(IllegalArgumentException.class, () -> s.setSrednieWynagrodzenie(-5));
    }

    @Test
    void setPracownicyZNajwyzszymWynagrodzeniemNullLubPustaRzucaWyjatek() {
        StatystykiFirmy s = new StatystykiFirmy("FirmaA",1,1000,List.of(new Pracownik("A","A","a@f.pl","FirmaA", Stanowiska.Programista)));
        assertThrows(IllegalArgumentException.class, () -> s.setPracownicyZNajwyzszymWynagrodzeniem(null));
        assertThrows(IllegalArgumentException.class, () -> s.setPracownicyZNajwyzszymWynagrodzeniem(List.of()));
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

        assertDoesNotThrow(() -> manager.wyswietlPracownikowWFirmie("FirmaA"));
        assertTrue(firmaA.contains(p1));
        assertTrue(firmaA.contains(p3));
        assertFalse(firmaA.contains(p2));
        assertEquals(2, firmaA.size());
    }

    @Test
    void testToString() {
        Pracownik p1 = new Pracownik("A", "A", "@A", "FirmaA", Stanowiska.Prezes);
        Pracownik p2 = new Pracownik("B", "B", "@B", "FirmaB", Stanowiska.Programista);

        StatystykiFirmy stat = new StatystykiFirmy(
                "TechCorp",
                50,
                1000,
                List.of(p1, p2)
        );


        String wynik = stat.toString();


        assertTrue(wynik.contains("Statystyki firmy: TechCorp"));
        assertTrue(wynik.contains("Liczba pracowników: 50"));
        assertTrue(wynik.contains("Średnie wynagrodzenie: 1000"));
        assertTrue(wynik.contains("Pracownicy z najwyższym wynagrodzeniem:"));
        assertTrue(wynik.contains(p1.toString()));
        assertTrue(wynik.contains(p2.toString()));
    }


}