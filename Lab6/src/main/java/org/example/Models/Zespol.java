package org.example.Models;

import java.util.ArrayList;
import java.util.List;

public class Zespol {
    private String nazwaZespolu;
    private int maxLiczebnosc;
    private List<Pracownik> pracownicy;

    public Zespol(String nazwaZespolu, int maxLiczebnosc) {
        setNazwaZespolu(nazwaZespolu);
        setMaxLiczebnosc(maxLiczebnosc);
        this.pracownicy = new ArrayList<>();
    }

    public String getNazwaZespolu() {
        return nazwaZespolu;
    }

    public List<Pracownik> getPracownicy() {
        return new ArrayList<>(pracownicy);
    }

    public void dodajPracownika(Pracownik p) {
        if (p == null) {
            throw new IllegalArgumentException("pracownik nie może być null");
        }
        if (pracownicy.contains(p)) {
            throw new IllegalArgumentException("pracownik już w zespole");
        }
        if (pracownicy.size() >= maxLiczebnosc) {
            throw new IllegalArgumentException("zespol pelny");
        }
        pracownicy.add(p);
    }

    public void usunPracownika(Pracownik p) {
        if (p == null) {
            throw new IllegalArgumentException("pracownik nie może być null.");
        }

        if (!pracownicy.contains(p)) {
            throw new IllegalArgumentException("pracownika nie ma w zespole.");
        }

        pracownicy.remove(p);
    }


    public boolean czyRownowagaStanowisk() {
        boolean maProgramiste = false;
        boolean maManagera = false;
        boolean maStazyste = false;

        for (Pracownik p : pracownicy) {
            if (p.getStanowisko() == Stanowiska.Programista) {
                maProgramiste = true;
            }
            if (p.getStanowisko() == Stanowiska.Manager){
                maManagera = true;
            }
            if (p.getStanowisko() == Stanowiska.Stazysta) {
                maStazyste = true;
            }
        }

        int liczbaRoznych = 0;
        if (maProgramiste) liczbaRoznych++;
        if (maManagera) liczbaRoznych++;
        if (maStazyste) liczbaRoznych++;

        return liczbaRoznych >= 2;
    }

    public int getLiczebnosc() {
        return pracownicy.size();
    }

    public int getMaxLiczebnosc() {
        return maxLiczebnosc;
    }

    public void setNazwaZespolu(String nazwaZespolu) {
        if (nazwaZespolu == null || nazwaZespolu.isEmpty()) {
            throw new IllegalArgumentException("wpisz nazwe zespolu...");
        }
        this.nazwaZespolu = nazwaZespolu;
    }

    public void setMaxLiczebnosc(int maxLiczebnosc) {
        if (maxLiczebnosc <= 0) {
            throw new IllegalArgumentException("max musi byc wieksze niz 0");
        }
        this.maxLiczebnosc = maxLiczebnosc;
    }


}
