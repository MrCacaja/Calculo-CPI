package br.com.univali.cpicalc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Compiler {
    public void writeFile(String programName, List<String> lines) {
        String filename = "assets/out/" + programName + ".txt";
        try {
            File file = new File(filename);
            if (file.exists() || file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(filename);
                for (String line: lines) {
                    fileWriter.write(line + "\n");
                }
                fileWriter.close();
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("Não foi possível escrever o arquivo compilado.");
        }
    }
}
