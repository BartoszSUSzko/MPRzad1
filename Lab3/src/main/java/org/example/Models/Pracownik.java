package org.example.Models;


import java.util.Objects;

public class Pracownik {
    private String imie;
    private String nazwisko;
    private String email;
    private String nazwaFirmy;
    private Stanowiska stanowisko;
    private int wynagrodzenie;


    public Pracownik(String imie, String nazwisko, String email, String nazwaFirmy, Stanowiska stanowisko) {
        setImie(imie);
        setNazwisko(nazwisko);
        setEmail(email);
        setNazwaFirmy(nazwaFirmy);
        this.stanowisko = stanowisko;
        this.wynagrodzenie = stanowisko.getWynagrodzenieBazowe();
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



}
