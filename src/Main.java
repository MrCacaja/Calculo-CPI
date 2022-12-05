import br.com.univali.cpicalc.BinaryConverter;
import br.com.univali.cpicalc.Compiler;
import br.com.univali.cpicalc.MipsResolver;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        BinaryConverter binaryConverter = new BinaryConverter();
        MipsResolver cpiCalculator = new MipsResolver();
        Compiler compiler = new Compiler();
        File dir = new File("./assets");
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                continue;
            }
            System.out.println(file.getName() + ':');
            List<String> hexArray = binaryConverter.hexFileToArray(file);
            List<String> bitArray = binaryConverter.hexArrayToBinaryArray(hexArray);
            cpiCalculator.treatPipelineHazard(bitArray);
            cpiCalculator.calculateCpi(bitArray);
            compiler.writeFile(file.getName(), binaryConverter.binaryArrayToHexArray(bitArray));
            System.out.println();
        }
    }
}