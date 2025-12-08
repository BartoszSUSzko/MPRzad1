package org.example.EmployeeRepository;

import org.example.Models.Pracownik;

import java.util.List;

public class EksportDanychService {
    private final FormatterPracownikow formatter;
    private final RepozytoriumPracownikow repozytorium;
    private final SystemPlikow system;


    public EksportDanychService(RepozytoriumPracownikow repo,
                                FormatterPracownikow formatter,
                                SystemPlikow pliki) {

        if (repo == null) {
            throw new IllegalArgumentException("Repozytorium nie może być null");
        }
        if (formatter == null) {
            throw new IllegalArgumentException("Formatter nie może być null");
        }
        if (pliki == null) {
            throw new IllegalArgumentException("System plikow nie może być null");
        }

        this.repozytorium = repo;
        this.formatter = formatter;
        this.system = pliki;
    }
    public void export(String path) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("scieżka nie może być pusta");
        }

        List<Pracownik> data = repozytorium.getRepozytorium();
        if (data.isEmpty()) {
            throw new IllegalStateException("brak pracowników do eksportu");
        }

        String formatted = formatter.formatuj(data);
        if (formatted == null || formatted.isEmpty()) {
            throw new IllegalStateException("sformatowane dane nie moga być puste");
        }

        system.zapisz(path, formatted);
    }
}