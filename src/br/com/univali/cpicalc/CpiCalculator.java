package br.com.univali.cpicalc;

import java.util.*;

public class CpiCalculator {
    private final Map<String, Integer> instructionCycles;
    private final List<String> missingInstructions;
    private final int load = 5;
    private final int store = 4;
    private final int rType = 4;
    private final int branch = 3;
    private final int jump = 3;

    public CpiCalculator() {
        missingInstructions = new ArrayList<>();
        instructionCycles = new HashMap<String, Integer>() {{
            put("000000", rType);
            put("001111", load);
            put("001101", branch);
            put("000010", jump);
            put("000011", jump);
            put("000100", branch);
            put("001000", store);
            put("000101", branch);
            put("101011", store);
            put("100011", load);
            put("001001", branch);
        }};
    }

    public void calculateCpi(List<String> bitInstructionArray) {
        float cycles = 0;
        float instructions = 0;

        for (String line : bitInstructionArray) {
            String opcode = line.substring(0, 6);
            Integer opcodeCycles = instructionCycles.get(opcode);
            if (opcodeCycles == null) {
                if (!missingInstructions.contains(opcode)) {
                    System.out.println("No cycle value for opcode " + opcode);
                    missingInstructions.add(opcode);
                }
            } else {
                instructions++;
                cycles += opcodeCycles;
            }
        }

        System.out.println("Ciclos: " + cycles + " | Instruções: " + instructions + " | CPI: " + cycles / instructions);
    }
}
