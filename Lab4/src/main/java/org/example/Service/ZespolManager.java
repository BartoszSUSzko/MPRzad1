package org.example.Service;

import org.example.Models.Pracownik;
import org.example.Models.Zespol;

public class ZespolManager {

    public void przeniesPracownika(Pracownik p, Zespol zrodlo, Zespol cel){

        if (p == null) {
            throw new IllegalArgumentException("pracownik nie może być null");
        }


        if (!zrodlo.getPracownicy().contains(p)) {
            throw new IllegalArgumentException("pracownik nie znajduje się w zespole zrodlowym.");
        }


        if (cel.getPracownicy().contains(p)) {
            throw new IllegalArgumentException("pracownik już jest w zespole docelowym.");
        }


        if (cel.getLiczebnosc() >= cel.getMaxLiczebnosc()) {
            throw new IllegalArgumentException("zespol docelowy jest pelny.");
        }


        zrodlo.usunPracownika(p);
        cel.dodajPracownika(p);
    }
}
