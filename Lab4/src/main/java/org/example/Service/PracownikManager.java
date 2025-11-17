package org.example.Service;




import org.example.Models.Pracownik;
import org.example.Models.Stanowiska;
import org.example.Models.StatystykiFirmy;

import java.util.*;

public class PracownikManager {
    private List<Pracownik> listaPracownikow =  new ArrayList<>();

    public void dodajPracownika(Pracownik pracownik) {
        if (pracownik == null) {
            throw new IllegalArgumentException("Pracownik nie może być null");
        }
        for  (Pracownik p : listaPracownikow) {
            if(pracownik.hashCode() == p.hashCode()) {
                throw new IllegalArgumentException("Pracownik o tym emailu już istnieje");
            }
        }
        listaPracownikow.add(pracownik);
    }

    public List<Pracownik> getListaPracownikow(){
        return listaPracownikow;
    }

    public void wyswietlPracownikow() {
        for (Pracownik p : listaPracownikow) {
            System.out.println(p.toString());
        }
    }

    public void wyswietlPracownikowWFirmie(String nazwaFirmy) {
        if(nazwaFirmy == null) {
            throw new IllegalArgumentException("nazwa firmy nie może być null");
        }

        for (Pracownik p : listaPracownikow) {
            if(p.getNazwaFirmy().equals(nazwaFirmy)) {
                System.out.println(p.toString());
            }
        }
    }

    public void sortujPracownikow() {
        listaPracownikow.sort(Comparator.comparing(Pracownik::getNazwisko));
    }

    public Map<Stanowiska, List<Pracownik>> grupowaniePracownikow() {
        Map<Stanowiska, List<Pracownik>> mapa = new HashMap<>();

        for (Pracownik p : listaPracownikow) {
            List<Pracownik> lista = mapa.get(p.getStanowisko());
            if (lista == null) {
                lista = new ArrayList<>();
                mapa.put(p.getStanowisko(), lista);
            }
            lista.add(p);
        }

        return mapa;
    }

    public Map<Stanowiska, Integer> liczbaPracownikowNaStanowisku() {
        Map<Stanowiska, Integer> mapa = new HashMap<>();

        for (Pracownik p : listaPracownikow) {
            mapa.put(p.getStanowisko(), mapa.getOrDefault(p.getStanowisko(), 0) + 1);
        }

        return mapa;
    }

    public float srednieWynagrodzenie() {
        float srednieWynagrodzenie = 0;

        if (listaPracownikow.isEmpty()) return 0;

        for(Pracownik p : listaPracownikow) {
            srednieWynagrodzenie += p.getWynagrodzenie();
        }

        srednieWynagrodzenie /= listaPracownikow.size();
        return srednieWynagrodzenie;
    }

    public List<Pracownik> pracownicyZMaxWynagrodzeniem(){
        List<Pracownik> wynik =  new ArrayList<>();

        int maxWynagrodzenie = 0;
        for(Pracownik p : listaPracownikow) {
            if(p.getWynagrodzenie()>maxWynagrodzenie){
                maxWynagrodzenie = p.getWynagrodzenie();
            }
        }

        for (Pracownik p : listaPracownikow) {
            if(p.getWynagrodzenie()==maxWynagrodzenie){
                wynik.add(p);
            }
        }
        return wynik;
    }


    public List<Pracownik> validateSalaryConsistency() {
        List<Pracownik> niezgodne = new ArrayList<>();
        for (Pracownik p : listaPracownikow) {
            if (p.getWynagrodzenie() < p.getStanowisko().getWynagrodzenieBazowe()) {
                niezgodne.add(p);
            }
        }
        return niezgodne;
    }


    public Map<String, StatystykiFirmy> getCompanyStatistics() {
        Map<String, List<Pracownik>> grupyFirm = new HashMap<>();


        for (Pracownik p : listaPracownikow) {
            String firma = p.getNazwaFirmy();
            List<Pracownik> lista = grupyFirm.get(firma);
            if (lista == null) {
                lista = new ArrayList<>();
                grupyFirm.put(firma, lista);
            }
            lista.add(p);
        }


        Map<String, StatystykiFirmy> statystyki = new HashMap<>();

        for (String firma : grupyFirm.keySet()) {
            List<Pracownik> pracownicy = grupyFirm.get(firma);
            int liczba = pracownicy.size();
            float suma = 0;
            int maxWynagrodzenie = 0;
            List<Pracownik> najwyzejPlaca = new ArrayList<>();

            for (Pracownik p : pracownicy) {
                suma += p.getWynagrodzenie();
                if (p.getWynagrodzenie() > maxWynagrodzenie) {
                    maxWynagrodzenie = p.getWynagrodzenie();
                }
            }

            for (Pracownik p : pracownicy) {
                if (p.getWynagrodzenie() == maxWynagrodzenie) {
                    najwyzejPlaca.add(p);
                }
            }

            float srednie = suma / liczba;
            statystyki.put(firma, new StatystykiFirmy(firma, liczba, srednie, najwyzejPlaca));
        }

        return statystyki;
    }


    public void awansPracownika(Pracownik p) {
        Stanowiska aktualne = p.getStanowisko();
        Stanowiska nowe = aktualne.nastepneWyzej();

        if (nowe == null) {
            throw new IllegalArgumentException("nie moze dalej awansowac");
        }

        p.setStanowisko(nowe);
        p.setWynagrodzenie(nowe.getWynagrodzenieBazowe());
    }

    public void podwyzkaProcentowa(Pracownik p, int procent) {
        if (procent <= 0) {
            throw new IllegalArgumentException("Podwyżka musi być dodatnia");
        }

        double nowaPensja = p.getWynagrodzenie() * (1 + procent / 100.0);

        Stanowiska maxStanowisko = p.getStanowisko().nastepneWyzej();
        if (maxStanowisko != null && nowaPensja > maxStanowisko.getWynagrodzenieBazowe()) {
            throw new IllegalArgumentException("Za duża podwyżka");
        }

        p.setWynagrodzenie((int)Math.round(nowaPensja));
    }

    public List<Pracownik> najlepsiPracownicy(){
        if (listaPracownikow.isEmpty()) {
            throw new IllegalArgumentException("pusta lista");
        }
        List<Pracownik> najlepsipracownicy = new ArrayList<>();
        double najwyzszasrednia = 0.0;
        for (Pracownik p : listaPracownikow) {
            if(p.sredniaOcen() >  najwyzszasrednia) {
                najwyzszasrednia = p.sredniaOcen();
            }
        }
        for(Pracownik p : listaPracownikow) {
            if(najwyzszasrednia == p.sredniaOcen()) {
                najlepsipracownicy.add(p);
            }
        }

        return najlepsipracownicy;
    }


}
