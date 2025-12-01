package Test.TaskAssignment;


import org.example.Models.Pracownik;
import org.example.TaskAssignment.RejestrPrzydzialow;

public class DummyRejestr implements RejestrPrzydzialow {

    @Override
    public void zarejestrujPrzydzial(String idZadania, Pracownik pracownik){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}