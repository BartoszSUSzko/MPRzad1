package Test.EmployeeRepository;

import org.example.EmployeeRepository.SystemPlikow;

public class MockSystemPlikow implements SystemPlikow {
    private final String expectedPath;
    private final String expectedContent;
    public boolean called = false;

    public MockSystemPlikow(String expectedPath, String expectedContent) {
        this.expectedPath = expectedPath;
        this.expectedContent = expectedContent;
    }

    @Override
    public void zapisz(String path, String content) {
        if (!expectedPath.equals(path))
            throw new AssertionError("niepoprawna sciezka");
        if (!expectedContent.equals(content))
            throw new AssertionError("niepoprawna zawartosc");
        called = true;
    }

    public void verifyCalled() {
        if (!called) {
            throw new IllegalArgumentException("zle zrobione odwolywania");
        }

    }
}
