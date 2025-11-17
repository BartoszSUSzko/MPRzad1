package org.example.Models;


import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pracownik {
    private String imie;
    private String nazwisko;
    private String email;
    private String nazwaFirmy;
    private Stanowiska stanowisko;
    private int wynagrodzenie;
    private LocalDate dataZatrudnienia;
    private List<Integer> ocenyRoczne = new ArrayList<>();

    public Pracownik(String imie, String nazwisko, String email, String nazwaFirmy, Stanowiska stanowisko) {
        setImie(imie);
        setNazwisko(nazwisko);
        setEmail(email);
        setNazwaFirmy(nazwaFirmy);
        this.stanowisko = stanowisko;
        this.wynagrodzenie = stanowisko.getWynagrodzenieBazowe();
        this.dataZatrudnienia = LocalDate.now();
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        if (imie == null || imie.isBlank()) {
            throw new IllegalArgumentException("zle imie!");
        }
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }


    public void setNazwisko(String nazwisko) {
        if (nazwisko == null || nazwisko.isBlank()) {
            throw new IllegalArgumentException("zle nazwisko!");
        }
        this.nazwisko = nazwisko;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email == null || email.isBlank()) {
            throw new IllegalArgumentException("zle  email!");
        }
        this.email = email;
    }

    public String getNazwaFirmy() {
        return nazwaFirmy;
    }

    public void setNazwaFirmy(String nazwaFirmy) {
        if (nazwaFirmy == null || nazwaFirmy.isBlank()) {
            throw new IllegalArgumentException("zle  nazwaFirmy!");
        }
        this.nazwaFirmy = nazwaFirmy;
    }

    public Stanowiska getStanowisko() {
        return stanowisko;
    }

    public void setStanowisko(Stanowiska stanowisko) {
        if (stanowisko == null) {
            throw new IllegalArgumentException("zle  stanowisko!");
        }
        this.stanowisko = stanowisko;
    }

    public int getWynagrodzenie() {
        return wynagrodzenie;
    }

    public void setWynagrodzenie(int wynagrodzenie) {
        if (wynagrodzenie < 0) {
            throw new IllegalArgumentException("zle  wynagrodzenie!");
        }
        this.wynagrodzenie = wynagrodzenie;
    }

    @Override
    public String toString() {
        return "Pracownik{" +
                "imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", email='" + email + '\'' +
                ", nazwaFirmy='" + nazwaFirmy + '\'' +
                ", stanowisko=" + stanowisko +
                ", wynagrodzenie=" + wynagrodzenie +
                '}';

    }

    public LocalDate getDataZatrudnienia() {
        return dataZatrudnienia;
    }

    public void setDataZatrudnienia(LocalDate dataZatrudnienia) {
        if (dataZatrudnienia == null || dataZatrudnienia.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("data zatrudnienia");
        }
        this.dataZatrudnienia = dataZatrudnienia;
    }

    public int stazWlatach(LocalDate dzisiaj) {
        if (dataZatrudnienia == null) {
            throw new IllegalStateException("brak daty zatrudnienia");
        }
        if (dzisiaj.isBefore(dataZatrudnienia)) {
            throw new IllegalArgumentException("data dzisiaj nie moze byc przed data zatrudnienia");
        }
        return Period.between(dataZatrudnienia, dzisiaj).getYears();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pracownik pracownik = (Pracownik) o;
        return Objects.equals(email, pracownik.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public List<Integer> getOcenyRoczne() {
        return ocenyRoczne;
    }

    public void dodajOcene(int ocena){
        if (ocena < 1 || ocena > 5) {
            throw new IllegalArgumentException("zle ocenyRoczne!");
        }
        ocenyRoczne.add(ocena);
    }

    public double sredniaOcen(){
        if (ocenyRoczne.isEmpty()){
            throw new IllegalArgumentException("zle ocenyRoczne!");
        }
        double srednia = 0.0;
        for (Integer i : ocenyRoczne) {
            srednia += i;
        }
        srednia /= ocenyRoczne.size();
        return srednia;

    }
    public boolean czyJubileusz(LocalDate dzisiaj) {
        if(dzisiaj == null || dzisiaj.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("zle dzisiaj!");
        }
        int lata = stazWlatach(dzisiaj);
        return lata > 0 && lata % 5 == 0;
    }
}
