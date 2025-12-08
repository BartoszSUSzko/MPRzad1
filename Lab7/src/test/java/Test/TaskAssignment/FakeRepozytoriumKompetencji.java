package Test.TaskAssignment;

import org.example.Models.Pracownik;
import org.example.TaskAssignment.RepozytoriumKompetencji;

import java.util.*;

public class FakeRepozytoriumKompetencji implements RepozytoriumKompetencji {
    private final Map<Pracownik, List<String>> kompetencjeMap = new HashMap<>();

    @Override
    public void dodajKompetencje(Pracownik p, List<String> kompetencje) {
        kompetencjeMap.put(p, kompetencje);
    }

    @Override
    public List<String> pobierzKompetencje(Pracownik p) {
        if (kompetencjeMap.containsKey(p)) {
            return kompetencjeMap.get(p);
        } else {
            return Collections.emptyList();
        }
    }
}
