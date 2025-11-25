package Test.TrainingReminder;

import org.example.Models.Pracownik;
import org.example.TrainingReminder.SerwisKomunikacji;

import java.util.ArrayList;
import java.util.List;

public class SpySerwisKomunikacji implements SerwisKomunikacji {

    public static class WyslanePrzypomnienie {
        public final Pracownik pracownik;
        public final String tresc;
        public WyslanePrzypomnienie(Pracownik pracownik, String tresc) {
            this.pracownik = pracownik;
            this.tresc = tresc;
        }
    }

    private final List<WyslanePrzypomnienie> historia = new ArrayList<>();

    @Override
    public void wyslijPrzypomnienie(Pracownik pracownik, String tresc) {
        historia.add(new WyslanePrzypomnienie(pracownik, tresc));
    }

    public List<WyslanePrzypomnienie> getHistoria() {
        return new ArrayList<>(historia);
    }

}
