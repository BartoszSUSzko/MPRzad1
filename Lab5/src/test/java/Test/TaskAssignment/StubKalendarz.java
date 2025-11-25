package Test.TaskAssignment;

import org.example.Models.Pracownik;
import org.example.TaskAssignment.KalendarzDostepnosci;

import java.util.List;

public class StubKalendarz implements KalendarzDostepnosci {
    private final List<Pracownik> dostepni;

    public StubKalendarz(List<Pracownik> dostepni) {
        this.dostepni = dostepni;
    }

    @Override
    public List<Pracownik> pobierzDostepnychPracownikow() {
        return dostepni;
    }
}
