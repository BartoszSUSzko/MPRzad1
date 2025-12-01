package Test.EmployeeRepository;

import org.example.EmployeeRepository.SystemPlikow;

public class SpySystemPlikow implements SystemPlikow {
    public String sciezka;
    public String zawartosc;

    @Override
    public void zapisz(String path, String content) {
        this.sciezka = path;
        this.zawartosc = content;
    }
}
