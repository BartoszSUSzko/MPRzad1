import Models.Pracownik;
import Models.Stanowiska;
import Service.PracownikManager;



public class Main {
    public static void main(String[] args) {
        PracownikManager pracownikManager = new PracownikManager();


        Pracownik pracownik1 = new Pracownik("Erma","Emra","@com","FirmaA",Stanowiska.Prezes);
        Pracownik pracownik2 = new Pracownik("Ermica", "Zakas", "@jakas","FirmaA" ,Stanowiska.Stazysta);
        Pracownik pracownik3 = new Pracownik("Ktos", "Ktosowski", "@nobaska","FirmaB" ,Stanowiska.Manager);
        Pracownik pracownik4 = new Pracownik("Gulpa", "Gulp", "@chills","FirmaA",Stanowiska.Stazysta);
        Pracownik pracownik5 = new Pracownik("Derma","Alm","@brig","FirmaB",Stanowiska.Manager);
        Pracownik pracownik6 = new Pracownik("Zar","Szewc", "@eepy","FirmaA",Stanowiska.Programista);

        pracownikManager.dodajPracownika(pracownik1);
        pracownikManager.dodajPracownika(pracownik2);
        pracownikManager.dodajPracownika(pracownik3);
        pracownikManager.dodajPracownika(pracownik4);
        pracownikManager.dodajPracownika(pracownik5);
        pracownikManager.dodajPracownika(pracownik6);


        System.out.println("\n");
        pracownikManager.dodajPracownika(pracownik1);
        System.out.println("\n");
        pracownikManager.wyswietlPracownikow();
        System.out.println("\n");
        pracownikManager.sortujPracownikow();
        pracownikManager.wyswietlPracownikow();
        System.out.println("\n");
        pracownikManager.wyswietlPracownikowWFirmie("FirmaA");


        System.out.println("\n");
        System.out.println(pracownikManager.grupowaniePracownikow());

        System.out.println("\n");
        System.out.println(pracownikManager.liczbaPracownikowNaStanowisku());

        System.out.println("\n");
        System.out.println(pracownikManager.srednieWynagrodzenie());

        System.out.println("\n");
        System.out.println(pracownikManager.pracownicyZMaxWynagrodzeniem());
    }
}