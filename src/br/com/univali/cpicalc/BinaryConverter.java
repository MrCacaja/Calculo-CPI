package br.com.univali.cpicalc;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BinaryConverter {

    public List<String> hexFileToBinaryArray(File hexFile) {
        final List<String> binaryArray = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(hexFile);
            while (scanner.hasNext()) {
                String bitString = new BigInteger(scanner.nextLine(), 16).toString(2);
                while (bitString.length() < 32) {
                    bitString = "0".concat(bitString);
                }
                binaryArray.add(bitString);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Arquivo não encontrado");
        }
        return binaryArray;
    }
}
