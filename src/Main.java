import br.com.univali.cpicalc.BinaryConverter;
import br.com.univali.cpicalc.CpiCalculator;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        BinaryConverter binaryConverter = new BinaryConverter();
        CpiCalculator cpiCalculator = new CpiCalculator();
        File dir = new File("./assets");
        for (File file : dir.listFiles()) {
            System.out.println(file.getName() + ':');
            cpiCalculator.calculateCpi(binaryConverter.hexFileToBinaryArray(file));
            System.out.println();
        }
    }
}