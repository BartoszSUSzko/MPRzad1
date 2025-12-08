package Test.TaskAssignment;


import org.example.Models.Pracownik;
import org.example.Models.Stanowiska;
import org.example.TaskAssignment.KalendarzDostepnosci;
import org.example.TaskAssignment.PrzydzielanieZadanService;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PrzydzielanieZadanServiceTest {

    @Test
    void przydzielaZadaniePierwszemuDostepnemuPracownikowi() {
        Pracownik p1 = new Pracownik("A","A","@A","FirmaA",Stanowiska.Prezes);
        Pracownik p2 = new Pracownik("B","B","@B","FirmaB",Stanowiska.Programista);

        StubKalendarz stubKalendarz = new StubKalendarz(List.of(p1, p2));
        FakeRepozytoriumKompetencji fakeRepo = new FakeRepozytoriumKompetencji();
        SpyRejestrPrzydzialow spyRejestr = new SpyRejestrPrzydzialow();

        PrzydzielanieZadanService service = new PrzydzielanieZadanService(
                stubKalendarz, fakeRepo, spyRejestr
        );

        service.dodajKompetencje(p1, List.of("Java"));
        service.dodajKompetencje(p2, List.of("Python"));

        Pracownik przydzielony = service.przydzielZadanie("ZAD1", List.of("Java"), 5);

        assertEquals(p1, przydzielony);
        assertTrue(spyRejestr.getLog().contains("ZAD1:@A"));
    }

    @Test
    void rzucaWyjatekDlaNullIdZadania() {
        PrzydzielanieZadanService service = new PrzydzielanieZadanService(
                new StubKalendarz(List.of()), new FakeRepozytoriumKompetencji(), new SpyRejestrPrzydzialow()
        );

        assertThrows(IllegalArgumentException.class,
                () -> service.przydzielZadanie(null, List.of("Java"), 5));
    }
    @Test
    void rzucaWyjatekDlaBlankIdZadania() {
        PrzydzielanieZadanService service = new PrzydzielanieZadanService(
                new StubKalendarz(List.of()), new FakeRepozytoriumKompetencji(), new SpyRejestrPrzydzialow()
        );

        assertThrows(IllegalArgumentException.class,
                () -> service.przydzielZadanie("", List.of("Java"), 5));
    }

    @Test
    void rzucaWyjatekDlaPustejListyUmiejetnosci() {
        PrzydzielanieZadanService service = new PrzydzielanieZadanService(
                new StubKalendarz(List.of()), new FakeRepozytoriumKompetencji(), new SpyRejestrPrzydzialow()
        );

        assertThrows(IllegalArgumentException.class,
                () -> service.przydzielZadanie("ZAD2", null, 5));
    }

    @Test
    void rzucaWyjatekDlaUjemnegoCzasuRealizacji() {
        PrzydzielanieZadanService service = new PrzydzielanieZadanService(
                new StubKalendarz(List.of()), new FakeRepozytoriumKompetencji(), new SpyRejestrPrzydzialow()
        );

        assertThrows(IllegalArgumentException.class,
                () -> service.przydzielZadanie("ZAD3", List.of("Java"), -1));
    }

    @Test
    void przydzielaZadanieDlaNikogo() {
        Pracownik p1 = new Pracownik("A","A","@A","FirmaA",Stanowiska.Prezes);

        StubKalendarz stubKalendarz = new StubKalendarz(List.of(p1));
        FakeRepozytoriumKompetencji fakeRepo = new FakeRepozytoriumKompetencji();

        SpyRejestrPrzydzialow spyRejestr = new SpyRejestrPrzydzialow();

        PrzydzielanieZadanService service = new PrzydzielanieZadanService(
                stubKalendarz, fakeRepo, spyRejestr
        );

        service.dodajKompetencje(p1, List.of("NicNieUmie"));

        assertEquals(null, service.przydzielZadanie("ZAD3", List.of("Java"), 5));
    }

    @Test
    void konstruktorRzucaGdyKalendarzNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new PrzydzielanieZadanService(
                        null,
                        new FakeRepozytoriumKompetencji(),
                        new SpyRejestrPrzydzialow()
                )
        );
    }

    @Test
    void konstruktorRzucaGdyRepoNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new PrzydzielanieZadanService(
                        new StubKalendarz(List.of()),
                        null,
                        new SpyRejestrPrzydzialow()
                )
        );
    }

    @Test
    void konstruktorRzucaGdyRejestrNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new PrzydzielanieZadanService(
                        new StubKalendarz(List.of()),
                        new FakeRepozytoriumKompetencji(),
                        null
                )
        );
    }


    @Test
    void mockWeryfikujePoprawneWywolanie() {
        Pracownik p1 = new Pracownik("A","A","@A","FirmaA", Stanowiska.Programista);

        StubKalendarz kal = new StubKalendarz(List.of(p1));
        FakeRepozytoriumKompetencji repo = new FakeRepozytoriumKompetencji();

        MockRejestrPrzydzialow mock = new MockRejestrPrzydzialow();
        mock.expect("ZAD777", p1);

        PrzydzielanieZadanService service = new PrzydzielanieZadanService(
                kal, repo, mock
        );

        service.dodajKompetencje(p1, List.of("Java"));

        service.przydzielZadanie("ZAD777", List.of("Java"), 5);

        mock.verifyCalled();
    }

    @Test
    void mockWeryfikujeBrakWywolania() {
        Pracownik p1 = new Pracownik("A","A","@A","FirmaA", Stanowiska.Programista);

        StubKalendarz kal = new StubKalendarz(List.of(p1));
        FakeRepozytoriumKompetencji repo = new FakeRepozytoriumKompetencji();

        MockRejestrPrzydzialow mock = new MockRejestrPrzydzialow();
        mock.expect("ZAD888", p1);

        PrzydzielanieZadanService service = new PrzydzielanieZadanService(
                kal, repo, mock
        );

        service.dodajKompetencje(p1, List.of("Python"));

        service.przydzielZadanie("ZAD888", List.of("Java"), 5);

        assertThrows(IllegalArgumentException.class, ()-> mock.verifyCalled());
    }
    @Test
    void dodajKompetencjeNullPracownik(){
        DummyRejestr dummyRejestr = new DummyRejestr();
        StubKalendarz kal = new StubKalendarz(null);
        FakeRepozytoriumKompetencji repo = new FakeRepozytoriumKompetencji();
        PrzydzielanieZadanService service = new PrzydzielanieZadanService(kal,repo,dummyRejestr);

        assertThrows(IllegalArgumentException.class, ()-> service.dodajKompetencje(null,List.of("Python")));

    }
    @Test
    void dodajKompetencjeNieistniejacyPracownik(){
        DummyRejestr dummyRejestr = new DummyRejestr();
        StubKalendarz kal = new StubKalendarz(List.of());
        FakeRepozytoriumKompetencji repo = new FakeRepozytoriumKompetencji();
        PrzydzielanieZadanService service = new PrzydzielanieZadanService(kal,repo,dummyRejestr);

        assertThrows(IllegalArgumentException.class, ()-> service.dodajKompetencje(new Pracownik("A","A","@A","FirmaA", Stanowiska.Programista),List.of("Java")));

    }
    @Test
    void dodajKompetencjeNull(){
        Pracownik p1 = new Pracownik("A","A","@A","FirmaA", Stanowiska.Programista);
        DummyRejestr dummyRejestr = new DummyRejestr();
        StubKalendarz kal = new StubKalendarz(List.of(p1));
        FakeRepozytoriumKompetencji repo = new FakeRepozytoriumKompetencji();
        PrzydzielanieZadanService service = new PrzydzielanieZadanService(kal,repo,dummyRejestr);
        assertThrows(IllegalArgumentException.class, ()-> service.dodajKompetencje(new Pracownik("A","A","@A","FirmaA", Stanowiska.Programista),null));

    }

}
