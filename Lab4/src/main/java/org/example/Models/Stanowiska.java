package org.example.Models;


public enum Stanowiska {
    Prezes(25000, 5),
    Wiceprezes(18000, 4),
    Manager(12000, 3),
    Programista(8000, 2),
    Stazysta(3000, 1);

    private int wynagrodzenieBazowe;
    private int poziomHierarchii;

    Stanowiska(int wynagrodzenieBazowe, int poziomHierarchii) {
        this.wynagrodzenieBazowe = wynagrodzenieBazowe;
        this.poziomHierarchii = poziomHierarchii;
    }

    public int getWynagrodzenieBazowe() {
        return wynagrodzenieBazowe;
    }

    public int getPoziomHierarchii() {
        return poziomHierarchii;
    }

    public boolean wyzszeOd(Stanowiska inne) {
        return this.poziomHierarchii > inne.poziomHierarchii;
    }
}

