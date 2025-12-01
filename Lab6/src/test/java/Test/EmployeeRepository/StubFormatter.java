package Test.EmployeeRepository;

import org.example.EmployeeRepository.FormatterPracownikow;
import org.example.Models.Pracownik;

import java.util.List;

public class StubFormatter implements FormatterPracownikow {
    private final String output;

    public StubFormatter(String output) {
        this.output = output;
    }

    @Override
    public String formatuj(List<Pracownik> pracownicy) {
        return output;
    }
}