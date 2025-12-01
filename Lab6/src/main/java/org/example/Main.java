package org.example;

import org.example.Models.PodsumowanieImportu;
import org.example.Models.Pracownik;
import org.example.Models.StatystykiFirmy;
import org.example.Service.ApiService;
import org.example.Service.ImportService;
import org.example.Service.PracownikManager;
import org.example.Exception.ApiException;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        PracownikManager manager = new PracownikManager();


        ImportService importService = new ImportService(manager);
        PodsumowanieImportu podsumowanie = importService.importujZCsv("employees.csv");
        System.out.println("=== Import CSV ===");
        System.out.println(podsumowanie);


        ApiService apiService = new ApiService();
        try {
            List<Pracownik> pracownicyZApi = apiService.pobierzPracownikowZApi();
            for (Pracownik p : pracownicyZApi) {
                manager.dodajPracownika(p);
            }
            System.out.println("\n=== Pracownicy z API ===");
            for (Pracownik p : pracownicyZApi) {
                System.out.println(p);
            }
        } catch (ApiException e) {
            System.out.println("Błąd pobierania danych z API: " + e.getMessage());
        }


        System.out.println("\n=== Pracownicy z wynagrodzeniem poniżej bazowego ===");
        List<Pracownik> niskieWynagrodzenia = manager.validateSalaryConsistency();
        for (Pracownik p : niskieWynagrodzenia) {
            System.out.println(p);
        }


        System.out.println("\n=== Statystyki firm ===");
        Map<String, StatystykiFirmy> statystyki = manager.getCompanyStatistics();
        for (String firma : statystyki.keySet()) {
            System.out.println(statystyki.get(firma));
        }
    }
}
