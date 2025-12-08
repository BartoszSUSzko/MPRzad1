package org.example.TaskAssignment;

import org.example.Models.Pracownik;

import java.util.List;

public interface RepozytoriumKompetencji {
    List<String> pobierzKompetencje(Pracownik pracownik);
    void dodajKompetencje(Pracownik p, List<String> kompetencje);
}
