package org.example.TrainingReminder;

import org.example.Models.Pracownik;
import java.time.LocalDate;

public class Certyfikat {
    private Pracownik pracownik;
    private String nazwa;
    private LocalDate dataWygasniecia;

    public Certyfikat(Pracownik pracownik, String nazwa, LocalDate dataWygasniecia) {

        if (pracownik == null) {
            throw new IllegalArgumentException("pracownik nie może być null");
        }
        if (nazwa == null) {
            throw new IllegalArgumentException("nazwa nie może być null");
        }
        if (dataWygasniecia == null) {
            throw new IllegalArgumentException("data wygasniecia nie może być null");
        }
        this.pracownik = pracownik;
        this.nazwa = nazwa;
        this.dataWygasniecia = dataWygasniecia;
    }

    public Pracownik getPracownik() { return pracownik; }
    public String getNazwa() { return nazwa; }
    public LocalDate getDataWygasniecia() { return dataWygasniecia; }
}