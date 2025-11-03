package org.example.Models;

import java.util.List;

public class StatystykiFirmy {
    private String nazwaFirmy;
    private int liczbaPracownikow;
    private float srednieWynagrodzenie;
    private List<Pracownik> pracownicyZNajwyzszymWynagrodzeniem;

    public StatystykiFirmy(String nazwaFirmy, int liczbaPracownikow, float srednieWynagrodzenie, List<Pracownik> pracownicyZNajwyzszymWynagrodzeniem) {
        setNazwaFirmy(nazwaFirmy);
        setLiczbaPracownikow(liczbaPracownikow);
        setSrednieWynagrodzenie(srednieWynagrodzenie);
        setPracownicyZNajwyzszymWynagrodzeniem(pracownicyZNajwyzszymWynagrodzeniem);
    }

    @Override
    public String toString() {
        String wynik = "Statystyki firmy: " + nazwaFirmy + "\n" +
                "Liczba pracowników: " + liczbaPracownikow + "\n" +
                "Średnie wynagrodzenie: " + srednieWynagrodzenie + "\n" +
                "Pracownicy z najwyższym wynagrodzeniem:\n";

        for (Pracownik p : pracownicyZNajwyzszymWynagrodzeniem) {
            wynik += " " + p + "\n";
        }

        return wynik;
    }

    public String getNazwaFirmy() {
        return nazwaFirmy;
    }

    public void setNazwaFirmy(String nazwaFirmy) {
        if(nazwaFirmy == null || nazwaFirmy.isBlank()) {
            throw new IllegalArgumentException("zla nazwaFirmy!");
        }
        this.nazwaFirmy = nazwaFirmy;
    }

    public int getLiczbaPracownikow() {
        return liczbaPracownikow;
    }

    public void setLiczbaPracownikow(int liczbaPracownikow) {
        if(liczbaPracownikow < 0) {
            throw new IllegalArgumentException("zla liczbaPracownikow!");
        }
        this.liczbaPracownikow = liczbaPracownikow;
    }

    public float getSrednieWynagrodzenie() {

        return srednieWynagrodzenie;
    }

    public void setSrednieWynagrodzenie(float srednieWynagrodzenie) {
        if(srednieWynagrodzenie < 0) {
            throw new IllegalArgumentException("zla liczbaPracownikow!");
        }
        this.srednieWynagrodzenie = srednieWynagrodzenie;
    }

    public List<Pracownik> getPracownicyZNajwyzszymWynagrodzeniem() {
        return pracownicyZNajwyzszymWynagrodzeniem;
    }

    public void setPracownicyZNajwyzszymWynagrodzeniem(List<Pracownik> pracownicyZNajwyzszymWynagrodzeniem) {
        if(pracownicyZNajwyzszymWynagrodzeniem == null || pracownicyZNajwyzszymWynagrodzeniem.isEmpty())
        {
            throw new IllegalArgumentException("zle....!");
        }
        this.pracownicyZNajwyzszymWynagrodzeniem = pracownicyZNajwyzszymWynagrodzeniem;
    }
}
