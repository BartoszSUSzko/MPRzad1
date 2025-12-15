package org.example;


import org.example.Models.Pracownik;
import org.example.Models.StatystykiFirmy;
import org.example.Service.ApiService;
import org.example.Service.ImportService;
import org.example.Service.PracownikManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@ImportResource("classpath:employees-beans.xml")
public class EmployeeManagementApplication implements CommandLineRunner {

    private final ImportService importService;
    private final PracownikManager employeeService;
    private final ApiService apiService;

    @Resource(name = "xmlEmployees")
    private List<Pracownik> xmlEmployees;

    @Value("${app.import.csv-file}")
    private String csvFilePath;

    public EmployeeManagementApplication(
            ImportService importService,
            PracownikManager employeeService,
            ApiService apiService
    ) {
        this.importService = importService;
        this.employeeService = employeeService;
        this.apiService = apiService;
    }

    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagementApplication.class, args);
    }

    @Override
    public void run(String... args) {

        System.out.println("=== START APLIKACJI ===");


        System.out.println("\n--- Import z pliku CSV ---");
        importService.importujZCsv(csvFilePath);

        System.out.println("\n--- Dodawanie pracowników z XML ---");
        for (Pracownik e : xmlEmployees) {
            employeeService.dodajPracownika(e);
        }


        System.out.println("\n--- Import z REST API ---");
        try {
            List<Pracownik> apiEmployees = apiService.pobierzPracownikowZApi();
            for (Pracownik e : apiEmployees) {
                employeeService.dodajPracownika(e);
            }
        } catch (Exception e) {
            System.err.println("Błąd API: " + e.getMessage());
        }


        System.out.println("\n--- Statystyki firmy ---");
        Map<String, StatystykiFirmy> stats = employeeService.getCompanyStatistics();
        stats.values().forEach(System.out::println);


        System.out.println("\n--- Walidacja wynagrodzeń ---");
        List<Pracownik> invalidSalaries = employeeService.validateSalaryConsistency();
        if (invalidSalaries.isEmpty()) {
            System.out.println("Wszystkie wynagrodzenia są poprawne");
        } else {
            invalidSalaries.forEach(System.out::println);
        }

        System.out.println("\n=== KONIEC DZIAŁANIA ===");
    }
}
