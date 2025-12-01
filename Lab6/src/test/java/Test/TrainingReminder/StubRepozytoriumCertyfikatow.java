package Test.TrainingReminder;

import org.example.TrainingReminder.Certyfikat;
import org.example.TrainingReminder.RepozytoriumCertyfikatow;

import java.util.List;

public class StubRepozytoriumCertyfikatow implements RepozytoriumCertyfikatow {

    private final List<Certyfikat> certyfikaty;


    public StubRepozytoriumCertyfikatow(List<Certyfikat> certyfikaty) {
        this.certyfikaty = certyfikaty;
    }

    @Override
    public List<Certyfikat> pobierzWszystkieCertyfikaty() {
        return certyfikaty;
    }
}
