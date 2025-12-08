package org.example.EmployeeRepository;

import org.example.Models.Pracownik;

import java.util.List;

public interface FormatterPracownikow {
    String formatuj(List<Pracownik> pracownicy);
}
