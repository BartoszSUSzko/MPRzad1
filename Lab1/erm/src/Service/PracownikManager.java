package Service;

import Models.Pracownik;
import Models.Stanowiska;

import java.util.*;

public class PracownikManager {
    private List<Pracownik> listaPracownikow =  new ArrayList<>();

    public void dodajPracownika(Pracownik pracownik) {
        for  (Pracownik p : listaPracownikow) {
            if(pracownik.hashCode() == p.hashCode()) {
                System.out.println("juz jest taki pracownik");
                return;
            }
        }
        listaPracownikow.add(pracownik);
    }

    public void wyswietlPracownikow() {
        for (Pracownik p : listaPracownikow) {
            System.out.println(p.toString());
        }
    }

    public void wyswietlPracownikowWFirmie(String nazwaFirmy) {
        for (Pracownik p : listaPracownikow) {
            if(p.getNazwaFirmy().equals(nazwaFirmy)) {
                System.out.println(p.toString());
            }
        }
    }

    public void sortujPracownikow() {
        listaPracownikow.sort(Comparator.comparing(Pracownik::getNazwisko));
    }

    public Map<Stanowiska, List<Pracownik>> grupowaniePracownikow(){

        Map<Stanowiska, List<Pracownik>> mapa = new HashMap<>();

        for (Pracownik p : listaPracownikow) {
            mapa.computeIfAbsent(p.getStanowisko(), k -> new ArrayList<>()).add(p);

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


}
