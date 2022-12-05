package br.com.univali.cpicalc;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BinaryConverter {
    public List<String> hexFileToArray(File hexFile) {
        final List<String> array = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(hexFile);
            while (scanner.hasNext()) {
                array.add(scanner.nextLine());
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Arquivo não encontrado");
        }
        return array;
    }

    public List<String> hexArrayToBinaryArray(List<String> hexArray) {
        final List<String> array = new ArrayList<>();
        for (String hex: hexArray) {
            String bitString = new BigInteger(hex, 16).toString(2);
            while (bitString.length() < 32) {
                bitString = "0".concat(bitString);
            }
            array.add(bitString);
        }
        return array;
    }

    public List<String> binaryArrayToHexArray(List<String> hexArray) {
        final List<String> array = new ArrayList<>();
        for (String bites: hexArray) {
            String hexString = new BigInteger(bites, 2).toString(16);
            while (hexString.length() < 8) {
                hexString = "0".concat(hexString);
            }
            array.add(hexString);
        }
        return array;
    }
}
