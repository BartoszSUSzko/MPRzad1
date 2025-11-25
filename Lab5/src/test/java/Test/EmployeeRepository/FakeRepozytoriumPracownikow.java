package Test.EmployeeRepository;

import org.example.EmployeeRepository.RepozytoriumPracownikow;
import org.example.Models.Pracownik;

import java.util.ArrayList;
import java.util.List;

public class FakeRepozytoriumPracownikow implements RepozytoriumPracownikow {
    private final List<Pracownik> lista = new ArrayList<>();

    public void dodaj(Pracownik p) {
        lista.add(p);
    }

    @Override
    public List<Pracownik> getRepozytorium() {
        return lista;
    }
}
