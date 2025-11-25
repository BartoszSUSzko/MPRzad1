package org.example.TaskAssignment;

import org.example.Models.Pracownik;

import java.util.List;

public class PrzydzielanieZadanService {
    private final KalendarzDostepnosci kalendarzDostepnosci;
    private final RepozytoriumKompetencji repozytoriumKompetencji;
    private final RejestrPrzydzialow rejestrPrzydzialow;

    public PrzydzielanieZadanService(KalendarzDostepnosci kalendarzDostepnosci, RepozytoriumKompetencji repozytoriumKompetencji, RejestrPrzydzialow rejestrPrzydzialow) {

        if (kalendarzDostepnosci == null) {
            throw new IllegalArgumentException("kalendarz nie może być null");
        }
        if (repozytoriumKompetencji == null) {
            throw new IllegalArgumentException("repozytorium nie może być null");
        }
        if (rejestrPrzydzialow == null) {
            throw new IllegalArgumentException("rejestr nie może być null");
        }

        this.kalendarzDostepnosci = kalendarzDostepnosci;
        this.repozytoriumKompetencji = repozytoriumKompetencji;
        this.rejestrPrzydzialow = rejestrPrzydzialow;
    }

    public Pracownik przydzielZadanie(String idZadania, List<String> wymaganeUmiejetnosci, int czasRealizacji) {
        if (idZadania == null || idZadania.isBlank()) {
            throw new IllegalArgumentException("idZadania jest zle");
        }
        if (wymaganeUmiejetnosci == null) {
            throw new IllegalArgumentException("wymaganeUmiejetnosci jest zle");
        }
        if (czasRealizacji < 0) {
            throw new IllegalArgumentException("czasRealizacji jest zle");
        }

        for (Pracownik p : kalendarzDostepnosci.pobierzDostepnychPracownikow()) {
            List<String> kompetencje = repozytoriumKompetencji.pobierzKompetencje(p);
            if (kompetencje.containsAll(wymaganeUmiejetnosci)) {
                rejestrPrzydzialow.zarejestrujPrzydzial(idZadania, p);
                return p;
            }
        }
        return null;
    }

    public void dodajKompetencje(Pracownik p, List<String> kompetencje) {
        if(p == null) {
            throw new IllegalArgumentException("p jest nullu");
        }
        if(kompetencje == null) {
            throw new IllegalArgumentException("kompetencje nullu");
        }
        if(!kalendarzDostepnosci.pobierzDostepnychPracownikow().contains(p)) {
            throw new IllegalArgumentException("nie ma go");
        }
        repozytoriumKompetencji.dodajKompetencje(p, kompetencje);
    }



}
