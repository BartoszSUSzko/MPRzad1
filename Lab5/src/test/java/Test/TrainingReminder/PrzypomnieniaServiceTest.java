package Test.TrainingReminder;

import org.example.EmployeeRepository.RepozytoriumPracownikow;
import org.example.Models.Pracownik;
import org.example.Models.Stanowiska;
import org.example.TrainingReminder.Certyfikat;
import org.example.TrainingReminder.PrzypomnieniaService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class PrzypomnieniaServiceTest {

    @Test
    void powinnoWyslacPrzypomnienieTylkoDlaCertyfikatuWygasajacego() {
        Pracownik p1 = new Pracownik("A", "A", "@A", "FirmaA", Stanowiska.Prezes);
        Pracownik p2 = new Pracownik("B", "B", "@B", "FirmaB", Stanowiska.Programista);

        List<Certyfikat> certyfikaty = List.of(
                new Certyfikat(p1, "BHP", LocalDate.now().plusDays(10)), // w okresie 30 dni
                new Certyfikat(p2, "RODO", LocalDate.now().plusDays(40)) // poza okresem 30 dni
        );

        StubRepozytoriumCertyfikatow stubRepo = new StubRepozytoriumCertyfikatow(certyfikaty);
        DummyLogger dummyLogger = new DummyLogger();
        SpySerwisKomunikacji spyKom = new SpySerwisKomunikacji();

        PrzypomnieniaService service = new PrzypomnieniaService(stubRepo, spyKom, dummyLogger);


        service.wyslijPrzypomnienia();


        assertEquals(1, spyKom.getHistoria().size());
        assertEquals("@A", spyKom.getHistoria().get(0).pracownik.getEmail());
        assertTrue(spyKom.getHistoria().get(0).tresc.contains("BHP"));
    }

    @Test
    void powinnoWyslacDokladnieJednoPrzypomnienie() {
        Pracownik p1 = new Pracownik("A", "A", "@A", "FirmaA", Stanowiska.Prezes);
        Pracownik p2 = new Pracownik("B", "B", "@B", "FirmaB", Stanowiska.Programista);

        List<Certyfikat> certyfikaty = List.of(
                new Certyfikat(p1, "BHP", LocalDate.now().plusDays(10)),
                new Certyfikat(p2, "RODO", LocalDate.now().plusDays(40))
        );

        StubRepozytoriumCertyfikatow stubRepo = new StubRepozytoriumCertyfikatow(certyfikaty);
        DummyLogger dummyLogger = new DummyLogger();
        MockSerwisKomunikacji mockKom = new MockSerwisKomunikacji(1);

        PrzypomnieniaService service = new PrzypomnieniaService(stubRepo, mockKom, dummyLogger);

        service.wyslijPrzypomnienia();

        mockKom.verify();
    }

    @Test
    void wysylaZaMalo() {
        Pracownik p1 = new Pracownik("A","A","@A","FirmaA", Stanowiska.Prezes);
        List<Certyfikat> certyfikaty = List.of(
                new Certyfikat(p1, "BHP", LocalDate.now().plusDays(10))
        );

        StubRepozytoriumCertyfikatow stubRepo = new StubRepozytoriumCertyfikatow(certyfikaty);
        DummyLogger dummyLogger = new DummyLogger();
        MockSerwisKomunikacji mockKom = new MockSerwisKomunikacji(2); // oczekuje 2, ale będzie 1

        PrzypomnieniaService service = new PrzypomnieniaService(stubRepo, mockKom, dummyLogger);
        service.wyslijPrzypomnienia();

        assertThrows(IllegalArgumentException.class, () -> mockKom.verify());
    }

    @Test
    void wysylaZaDuzo() {
        Pracownik p1 = new Pracownik("A","A","@A","FirmaA", Stanowiska.Prezes);
        Pracownik p2 = new Pracownik("B","B","@B","FirmaB", Stanowiska.Programista);
        List<Certyfikat> certyfikaty = List.of(
                new Certyfikat(p1, "BHP", LocalDate.now().plusDays(10)),
                new Certyfikat(p2, "RODO", LocalDate.now().plusDays(10))
        );

        StubRepozytoriumCertyfikatow stubRepo = new StubRepozytoriumCertyfikatow(certyfikaty);
        DummyLogger dummyLogger = new DummyLogger();
        MockSerwisKomunikacji mockKom = new MockSerwisKomunikacji(1); // oczekuje 1, ale będzie 2

        PrzypomnieniaService service = new PrzypomnieniaService(stubRepo, mockKom, dummyLogger);
        service.wyslijPrzypomnienia();

        assertThrows(IllegalArgumentException.class, () -> mockKom.verify());
    }


    @Test
    void konstruktorRzucaGdyRepoNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new PrzypomnieniaService(
                        null,
                        new SpySerwisKomunikacji(),
                        new DummyLogger()
                )
        );
    }

    @Test
    void konstruktorRzucaGdySerwisKomNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new PrzypomnieniaService(
                        new StubRepozytoriumCertyfikatow(List.of()),
                        null,
                        new DummyLogger()
                )
        );
    }

    @Test
    void konstruktorRzucaGdyLoggerNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new PrzypomnieniaService(
                        new StubRepozytoriumCertyfikatow(List.of()),
                        new SpySerwisKomunikacji(),
                        null
                )
        );
    }

    @Test
    void certyfikatRzucaGdyPracownikNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Certyfikat(
                        null,
                        "BHP",
                        LocalDate.now().plusDays(10)

                )
        );
    }
    @Test
    void certyfikatRzucaGdyNazwaNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Certyfikat(
                        new Pracownik("A", "A", "@A", "FirmaA", Stanowiska.Prezes),
                        null,
                        LocalDate.now().plusDays(10)

                )
        );
    }
    @Test
    void certyfikatRzucaGdydataNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Certyfikat(
                        new Pracownik("A", "A", "@A", "FirmaA", Stanowiska.Prezes),
                        "BHP",
                        null

                )
        );
    }



}
