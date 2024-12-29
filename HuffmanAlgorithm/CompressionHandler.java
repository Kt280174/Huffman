// Refactored Encoder.java
package HuffmanAlgorithm;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class CompressionHandler {
    private final Map<Byte, String> huffmanCodeMap;

    public CompressionHandler(HuffmanTreeGenerator huffmanTree) {
        this.huffmanCodeMap = huffmanTree.generateHuffmanCodes();
    }

    public void compressData(byte[] inputData, String outputFilePath) {
        StringBuilder encodedDataBuilder = new StringBuilder();
        for (byte data : inputData) {
            String code = huffmanCodeMap.get(data);
            if (code != null) {
                encodedDataBuilder.append(code);
            } else {
                throw new IllegalArgumentException("Data byte not found in Huffman codes: " + data);
            }
        }

        int completeByteCount = encodedDataBuilder.length() / 8;
        int leftoverBits = encodedDataBuilder.length() % 8;

        byte[] outputBytes = new byte[completeByteCount + (leftoverBits > 0 ? 1 : 0)];

        for (int i = 0; i < outputBytes.length; i++) {
            String byteSegment = encodedDataBuilder.substring(i * 8, Math.min((i + 1) * 8, encodedDataBuilder.length()));
            outputBytes[i] = (byte) Integer.parseInt(byteSegment, 2);
        }

        try (FileOutputStream fileWriter = new FileOutputStream(outputFilePath)) {
            fileWriter.write(leftoverBits);
            fileWriter.write(outputBytes);
        } catch (IOException e) {
            throw new RuntimeException("Error writing compressed data: " + e.getMessage(), e);
        }
    }
}
