package org.example.TrainingReminder;

import org.example.Models.Pracownik;

public interface SerwisKomunikacji {
    void wyslijPrzypomnienie(Pracownik pracownik, String tresc);
}
