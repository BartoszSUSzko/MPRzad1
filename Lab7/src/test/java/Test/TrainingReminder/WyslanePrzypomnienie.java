package Test.TrainingReminder;

import org.example.Models.Pracownik;
class WyslanePrzypomnienie {
    private final Pracownik pracownik;
    private final String tresc;

    public WyslanePrzypomnienie(Pracownik pracownik, String tresc) {
        this.pracownik = pracownik;
        this.tresc = tresc;
    }

    public Pracownik getPracownik() {
        return pracownik;
    }

    public String getTresc() {
        return tresc;
    }

}
