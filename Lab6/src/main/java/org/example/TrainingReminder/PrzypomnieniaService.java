package org.example.TrainingReminder;


import java.time.LocalDate;
import java.util.List;

public class PrzypomnieniaService {

    private final RepozytoriumCertyfikatow repo;
    private final SerwisKomunikacji serwisKom;
    private final Logger logger;

    public PrzypomnieniaService(RepozytoriumCertyfikatow repo, SerwisKomunikacji serwisKom, Logger logger) {
        if (repo == null) {
            throw new IllegalArgumentException("Repozytorium nie może być null");
        }
        if (serwisKom == null) {
            throw new IllegalArgumentException("Formatter nie może być null");
        }
        if (logger == null) {
            throw new IllegalArgumentException("System plikow nie może być null");
        }


        this.repo = repo;
        this.serwisKom = serwisKom;
        this.logger = logger;
    }

    public void wyslijPrzypomnienia() {
        List<Certyfikat> certyfikaty = repo.pobierzWszystkieCertyfikaty();

        LocalDate dzisiaj = LocalDate.now();
        LocalDate termin = dzisiaj.plusDays(30);

        for (Certyfikat c : certyfikaty) {
            if (!c.getDataWygasniecia().isAfter(termin)) {
                String tresc = "Przypomnienie: certyfikat " + c.getNazwa() +
                        " wygasa dnia " + c.getDataWygasniecia();
                serwisKom.wyslijPrzypomnienie(c.getPracownik(), tresc);
                logger.log("Wysłano przypomnienie dla " + c.getPracownik().getEmail());
            }
        }
    }

}

