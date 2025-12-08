package Test.TaskAssignment;

import org.example.Models.Pracownik;
import org.example.TaskAssignment.RejestrPrzydzialow;

public class MockRejestrPrzydzialow implements RejestrPrzydzialow {

    private String expectedTaskId;
    private Pracownik expectedPracownik;
    private boolean called = false;

    public void expect(String taskId, Pracownik pracownik) {
        this.expectedTaskId = taskId;
        this.expectedPracownik = pracownik;
    }

    @Override
    public void zarejestrujPrzydzial(String idZadania, Pracownik p) {
        called = true;

        if (!idZadania.equals(expectedTaskId)) {
            throw new IllegalArgumentException("Mock error: oczekiwano taskId = " + expectedTaskId +
                    ", otrzymano " + idZadania);
        }

        if (!p.equals(expectedPracownik)) {
            throw new IllegalArgumentException("Mock error: oczekiwano pracownika = "
                    + expectedPracownik + ", otrzymano " + p);
        }
    }

    public void verifyCalled() {
        if (!called) {
            throw new IllegalArgumentException("Mock error: metoda nie została wywolana");
        }
    }

}
