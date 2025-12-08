package Test.TaskAssignment;

import org.example.Models.Pracownik;
import org.example.TaskAssignment.RejestrPrzydzialow;

import java.util.*;

public class SpyRejestrPrzydzialow implements RejestrPrzydzialow {
    private final List<String> log = new ArrayList<>();

    @Override
    public void zarejestrujPrzydzial(String idZadania, Pracownik p) {
        log.add(idZadania + ":" + p.getEmail());
    }

    public List<String> getLog() {
        return log;
    }
}
