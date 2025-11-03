package org.example.Service;

import org.example.Exception.InvalidDataException;
import org.example.Models.Pracownik;
import org.example.Models.PodsumowanieImportu;
import org.example.Models.Stanowiska;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ImportService {

    private final PracownikManager manager;

    public ImportService(PracownikManager manager) {
        if (manager == null) {
            throw new IllegalArgumentException("Manager nie może być null");
        }
        this.manager = manager;
    }


    public PodsumowanieImportu importujZCsv(String sciezka) {
        PodsumowanieImportu podsumowanie = new PodsumowanieImportu();

        try (BufferedReader br = new BufferedReader(new FileReader(sciezka))) {
            String linia;
            int numerLinii = 0;

            while ((linia = br.readLine()) != null) {
                numerLinii++;


                linia = linia.trim();
                if (linia.isEmpty()){
                    continue;
                }

                String[] pola = linia.split(",");
                if (pola.length != 6) {
                    podsumowanie.dodajBlad("Linia " + numerLinii + ": nieprawidłowa liczba pól");
                    continue;
                }

                String imie = pola[0].trim();
                String nazwisko = pola[1].trim();
                String email = pola[2].trim();
                String firma = pola[3].trim();
                String stanowiskoStr = pola[4].trim();
                String wynagrodzenieStr = pola[5].trim();

                try {
                    Stanowiska stanowisko = Stanowiska.valueOf(stanowiskoStr);
                    int wynagrodzenie = Integer.parseInt(wynagrodzenieStr);

                    if (wynagrodzenie <= 0) {
                        throw new InvalidDataException("Wynagrodzenie mniejsze lub równe 0");
                    }

                    Pracownik pracownik = new Pracownik(imie, nazwisko, email, firma, stanowisko);
                    pracownik.setWynagrodzenie(wynagrodzenie);

                    manager.dodajPracownika(pracownik);
                    podsumowanie.zwiekszLicznik();

                } catch (InvalidDataException e) {
                    podsumowanie.dodajBlad("Linia " + numerLinii + ": " + e.getMessage());
                } catch (IllegalArgumentException e) {
                    podsumowanie.dodajBlad("Linia " + numerLinii + ": niepoprawne stanowisko lub dane (" + stanowiskoStr + ")");
                }
            }
        } catch (IOException e) {
            podsumowanie.dodajBlad("Błąd odczytu pliku: " + e.getMessage());
        }

        return podsumowanie;
    }
}
