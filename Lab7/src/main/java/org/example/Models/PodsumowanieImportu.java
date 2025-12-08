package org.example.Models;

import java.util.ArrayList;
import java.util.List;

public class PodsumowanieImportu {
    private int liczbaZaimportowanych;
    private List<String> bledy;

    public PodsumowanieImportu() {
        this.liczbaZaimportowanych = 0;
        this.bledy = new ArrayList<>();
    }

    public void dodajBlad(String blad) {
        if(blad == null || blad.isBlank()){
            throw new IllegalArgumentException("zly blad");
        }
        bledy.add(blad);
    }

    public void zwiekszLicznik() {
        liczbaZaimportowanych++;
    }

    public int getLiczbaZaimportowanych() {
        return liczbaZaimportowanych;
    }

    public List<String> getBledy() {
        return bledy;
    }

    @Override
    public String toString() {
        return "PodsumowanieImportu{" +
                "liczbaZaimportowanych=" + liczbaZaimportowanych +
                ", bledy=" + bledy +
                '}';
    }
}
