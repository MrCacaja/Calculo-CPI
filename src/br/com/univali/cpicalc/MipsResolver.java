package br.com.univali.cpicalc;

import java.util.*;

public class MipsResolver {
    private final Map<String, InstructionType> instructionTypes;
    private final Map<InstructionType, Integer> typeCycles;
    private final List<String> missingInstructions;
    private final List<String> compiledLines;
    private final String nop = "00000000000000000000000000000000";
    private final String syscall = "00000000000000000000000000001100";

    public MipsResolver() {
        compiledLines = new ArrayList<>();
        missingInstructions = new ArrayList<>();
        typeCycles = new HashMap<InstructionType, Integer>() {{
           put(InstructionType.branch, 3);
           put(InstructionType.rType, 4);
           put(InstructionType.jump, 3);
           put(InstructionType.load, 5);
           put(InstructionType.store, 4);
        }};
        instructionTypes = new HashMap<String, InstructionType>() {{
            put("000000", InstructionType.rType);
            put("001111", InstructionType.load);
            put("001101", InstructionType.branch);
            put("000010", InstructionType.jump);
            put("000011", InstructionType.jump);
            put("000100", InstructionType.branch);
            put("001000", InstructionType.store);
            put("000101", InstructionType.branch);
            put("101011", InstructionType.store);
            put("100011", InstructionType.load);
            put("001001", InstructionType.branch);
        }};
    }

    public void calculateCpi(List<String> bitInstructionArray) {
        float cycles = 0;
        float instructions = 0;

        for (String line : bitInstructionArray) {
            String opcode = line.substring(0, 6);
            InstructionType instructionType = instructionTypes.get(opcode);
            Integer opcodeCycles = typeCycles.get(instructionType);
            if (opcodeCycles == null) {
                if (!missingInstructions.contains(opcode)) {
                    System.out.println("No cycle value for opcode " + opcode);
                    missingInstructions.add(opcode);
                }
            } else {
                instructions++;
                cycles += opcodeCycles;
            }
            this.compiledLines.add(line);
        }

        System.out.println("Ciclos: " + cycles + " | Instruções: " + instructions + " | CPI: " + cycles / instructions);
    }

    public void treatPipelineHazard(List<String> bitInstructionArray) {
        for (int i = 0; i < bitInstructionArray.size(); i++) {
            String instruction = bitInstructionArray.get(i);
            String opcode = instruction.substring(0, 6);
            InstructionType instructionType = instructionTypes.get(opcode);
            boolean mayChangeDestiny = instructionType == InstructionType.rType ||
                    instructionType == InstructionType.load || instructionType == InstructionType.store;
            if (mayChangeDestiny && !instruction.equals(nop) && !instruction.equals(syscall)) {
                checkAndInsertNopAfter(bitInstructionArray, i);
            }
        }
    }

    private void checkAndInsertNopAfter(List<String> bitInstructionArray, int index) {
        String instruction = bitInstructionArray.get(index);
        String opcode = instruction.substring(0, 6);
        InstructionType instructionType = instructionTypes.get(opcode);
        String destinyRegister = instructionType == InstructionType.rType ? instruction.substring(16, 21) : instruction.substring(11, 16);
        for (int i = 1; i < 3; i++) {
            if (i + index >= bitInstructionArray.size()) {
                return;
            }
            String nInstruction = bitInstructionArray.get(i + index);
            String nOpcode = nInstruction.substring(0, 6);
            InstructionType nInstructionType = instructionTypes.get(nOpcode);
            if (nInstructionType == InstructionType.jump) continue;
            String nrs = nInstruction.substring(6, 11);
            String nrt = nInstruction.substring(11, 16);
            if (nrs.equals(destinyRegister) || nrt.equals(destinyRegister)) {
                bitInstructionArray.add(index + i, nop);
            }
        }
    }
}
