package Test.TrainingReminder;

import org.example.Models.Pracownik;
import org.example.TrainingReminder.SerwisKomunikacji;

import java.util.ArrayList;
import java.util.List;

public class SpySerwisKomunikacji implements SerwisKomunikacji {

    private final List<WyslanePrzypomnienie> historia = new ArrayList<>();

    @Override
    public void wyslijPrzypomnienie(Pracownik pracownik, String tresc) {
        historia.add(new WyslanePrzypomnienie(pracownik, tresc));
    }

    public List<WyslanePrzypomnienie> getHistoria() {
        return new ArrayList<>(historia);
    }
}
