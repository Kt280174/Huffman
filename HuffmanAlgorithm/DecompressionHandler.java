// Refactored Decoder.java
package HuffmanAlgorithm;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class DecompressionHandler {
    public static String decompress(String codeFilePath, String encodedFilePath) {
        Map<String, Byte> huffmanCodeMap;
        try {
            huffmanCodeMap = FileIOHandler.readCodeMappings(codeFilePath);
        } catch (NumberFormatException e) {
            return "Invalid code file format.";
        }

        StringBuilder decodedOutput = new StringBuilder();
        try (FileInputStream fileReader = new FileInputStream(encodedFilePath)) {
            int extraBits = fileReader.read(); // Number of valid bits in the last byte
            StringBuilder binaryDataBuilder = new StringBuilder();
            int currentByte;
            while ((currentByte = fileReader.read()) != -1) {
                String binaryRepresentation = Integer.toBinaryString(currentByte & 0xFF);
                while (binaryRepresentation.length() < 8) {
                    binaryRepresentation = "0" + binaryRepresentation;
                }
                binaryDataBuilder.append(binaryRepresentation);
            }

            // Remove invalid trailing bits if any
            if (extraBits > 0 && binaryDataBuilder.length() > 8) {
                int totalBits = binaryDataBuilder.length();
                binaryDataBuilder.delete(totalBits - 8, totalBits - extraBits);
            }

            String binaryData = binaryDataBuilder.toString();
            int startIndex = 0;

            while (startIndex < binaryData.length()) {
                boolean isMatchFound = false;
                for (int endIndex = startIndex + 1; endIndex <= binaryData.length(); endIndex++) {
                    String binarySegment = binaryData.substring(startIndex, endIndex);
                    if (huffmanCodeMap.containsKey(binarySegment)) {
                        decodedOutput.append((char) huffmanCodeMap.get(binarySegment).byteValue());
                        startIndex = endIndex;
                        isMatchFound = true;
                        break;
                    }
                }
                if (!isMatchFound) {
                    throw new IllegalArgumentException("Corrupted encoding found at position: " + startIndex);
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error during decoding: " + e.getMessage());
        }

        return decodedOutput.toString();
    }
}
