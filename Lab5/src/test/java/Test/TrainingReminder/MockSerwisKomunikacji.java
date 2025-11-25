package Test.TrainingReminder;

import org.example.Models.Pracownik;
import org.example.TrainingReminder.SerwisKomunikacji;

public class MockSerwisKomunikacji implements SerwisKomunikacji {
    private int actualCalls = 0;
    private final int expectedCalls;

    public MockSerwisKomunikacji(int expectedCalls) {
        this.expectedCalls = expectedCalls;
    }

    @Override
    public void wyslijPrzypomnienie(Pracownik pracownik, String tresc) {
        actualCalls++;
    }

    public void verify() {
        if (actualCalls != expectedCalls) {
            throw new IllegalArgumentException("zle zrobione odwolywania");
        }
    }
}
