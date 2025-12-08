package Test.EmployeeRepository;

import Test.TaskAssignment.FakeRepozytoriumKompetencji;
import Test.TaskAssignment.StubKalendarz;
import org.example.EmployeeRepository.EksportDanychService;
import org.example.Models.Pracownik;
import org.example.Models.Stanowiska;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EksportDanychServiceTest {

    @Test
    void testExportWithFakeStubSpy() {
        FakeRepozytoriumPracownikow repo = new FakeRepozytoriumPracownikow();
        repo.dodaj(new Pracownik("Jan", "Kowalski", "@a", "FirmaA", Stanowiska.Programista));

        StubFormatter stub = new StubFormatter("SFORMATOWANE_DANE");

        SpySystemPlikow spyFS = new SpySystemPlikow();

        EksportDanychService service = new EksportDanychService(repo, stub, spyFS);

        service.export("plik.csv");

        assertEquals("plik.csv", spyFS.sciezka);
        assertEquals("SFORMATOWANE_DANE", spyFS.zawartosc);
    }

    @Test
    void testExportWithMockVerifiesParams() {
        FakeRepozytoriumPracownikow repo = new FakeRepozytoriumPracownikow();
        repo.dodaj(new Pracownik("Anna","Nowak","@b","FirmaB",Stanowiska.Prezes));

        StubFormatter stub = new StubFormatter("MOCK_OUTPUT");

        MockSystemPlikow mockFS = new MockSystemPlikow("export.json", "MOCK_OUTPUT");

        EksportDanychService service = new EksportDanychService(repo, stub, mockFS);

        service.export("export.json");

        assertTrue(mockFS.called);
    }

    @Test
    void testExportThrowsOnEmptyPath() {
        FakeRepozytoriumPracownikow repo = new FakeRepozytoriumPracownikow();
        repo.dodaj(new Pracownik("A","A","@a","FirmaA", Stanowiska.Programista));
        StubFormatter stub = new StubFormatter("X");
        SpySystemPlikow spyFS = new SpySystemPlikow();
        EksportDanychService service = new EksportDanychService(repo, stub, spyFS);

        assertThrows(IllegalArgumentException.class, () -> service.export(""));
    }
    @Test
    void testExportThrowsOnNullPath() {
        FakeRepozytoriumPracownikow repo = new FakeRepozytoriumPracownikow();
        repo.dodaj(new Pracownik("A","A","@a","FirmaA", Stanowiska.Programista));
        StubFormatter stub = new StubFormatter("X");
        SpySystemPlikow spyFS = new SpySystemPlikow();
        EksportDanychService service = new EksportDanychService(repo, stub, spyFS);

        assertThrows(IllegalArgumentException.class, () -> service.export(null));
    }

    @Test
    void testExportThrowsOnEmptyRepo() {
        FakeRepozytoriumPracownikow repo = new FakeRepozytoriumPracownikow();
        StubFormatter stub = new StubFormatter("X");
        SpySystemPlikow spyFS = new SpySystemPlikow();
        EksportDanychService service = new EksportDanychService(repo, stub, spyFS);

        assertThrows(IllegalStateException.class, () -> service.export("plik.csv"));
    }

    @Test
    void testExportThrowsOnEmptyFormattedData() {
        FakeRepozytoriumPracownikow repo = new FakeRepozytoriumPracownikow();
        repo.dodaj(new Pracownik("A","A","@a","FirmaA", Stanowiska.Programista));
        StubFormatter stub = new StubFormatter("");
        SpySystemPlikow spyFS = new SpySystemPlikow();
        EksportDanychService service = new EksportDanychService(repo, stub, spyFS);

        assertThrows(IllegalStateException.class, () -> service.export("plik.csv"));
    }
    @Test
    void testExportThrowsNullFormattedData() {
        FakeRepozytoriumPracownikow repo = new FakeRepozytoriumPracownikow();
        repo.dodaj(new Pracownik("A","A","@a","FirmaA", Stanowiska.Programista));
        StubFormatter stub = new StubFormatter(null);
        SpySystemPlikow spyFS = new SpySystemPlikow();
        EksportDanychService service = new EksportDanychService(repo, stub, spyFS);

        assertThrows(IllegalStateException.class, () -> service.export("plik.csv"));
    }

    @Test
    void testExportThrowsOnNullFakeRepozytorium() {
        assertThrows(IllegalArgumentException.class, () ->
                new EksportDanychService(
                        null,
                        new StubFormatter("string"),
                        new SpySystemPlikow()
                )
        );
    }
    @Test
    void testExportThrowsOnNullStubFormatter() {
        assertThrows(IllegalArgumentException.class, () ->
                new EksportDanychService(
                        new FakeRepozytoriumPracownikow(),
                        null,
                        new SpySystemPlikow()
                )
        );
    }
    @Test
    void testExportThrowsOnNullSpySystemPlikow() {
        assertThrows(IllegalArgumentException.class, () ->
                new EksportDanychService(
                        new FakeRepozytoriumPracownikow(),
                        new StubFormatter("string"),
                        null
                )
        );
    }


    @Test
    void testExportWithMockVerifyCalledWorks() {

        FakeRepozytoriumPracownikow repo = new FakeRepozytoriumPracownikow();
        repo.dodaj(new Pracownik("A","A","@a","FirmaA", Stanowiska.Programista));


        StubFormatter stub = new StubFormatter("sformatowane");

        MockSystemPlikow mock = new MockSystemPlikow("cos", "sformatowane");

        EksportDanychService service = new EksportDanychService(repo, stub, mock);

        service.export("cos");

        assertDoesNotThrow(mock::verifyCalled);
    }


    @Test
    void testDlaMockaPusty() {
        FakeRepozytoriumPracownikow repo = new FakeRepozytoriumPracownikow();
        repo.dodaj(new Pracownik("A","A","@a","FirmaA", Stanowiska.Programista));

        StubFormatter stub = new StubFormatter("JAKIES_DANE");

        MockSystemPlikow mock = new MockSystemPlikow("", "");

        EksportDanychService service = new EksportDanychService(repo, stub, mock);

        assertThrows(IllegalArgumentException.class, mock::verifyCalled);
    }

    @Test
    void testDlaMockaDziala() {
        FakeRepozytoriumPracownikow repo = new FakeRepozytoriumPracownikow();
        repo.dodaj(new Pracownik("A","A","@a","FirmaA", Stanowiska.Programista));
        StubFormatter stub = new StubFormatter("POPRAWNE_DANE");


        MockSystemPlikow mock = new MockSystemPlikow("dane.csv", "POPRAWNE_DANE");

        EksportDanychService service = new EksportDanychService(repo, stub, mock);

        service.export("dane.csv");


        assertDoesNotThrow(mock::verifyCalled);
    }
}
