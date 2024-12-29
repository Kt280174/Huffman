// Refactored Main.java
package HuffmanAlgorithm;

import java.io.IOException;

public class ApplicationLauncher {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: <action: compress/decompress> <input file> <code file> <output file>");
            return;
        }

        String action = args[0];
        String inputFile = args[1];
        String codeFile = args[2];
        String outputFile = args[3];

        try {
            if (action.equalsIgnoreCase("compress")) {
                byte[] inputData = FileIOHandler.readFileAsByteArray(inputFile);
                HuffmanTreeGenerator treeGenerator = new HuffmanTreeGenerator();
                treeGenerator.constructTreeFromData(inputFile);
                CompressionHandler compressor = new CompressionHandler(treeGenerator);
                compressor.compressData(inputData, outputFile);

                FileIOHandler.writeCodeMappings(codeFile, treeGenerator.generateHuffmanCodes());
                System.out.println("Compression successful.");

            } else if (action.equalsIgnoreCase("decompress")) {
                String decompressedContent = DecompressionHandler.decompress(codeFile, inputFile);
                FileIOHandler.writeToFile(outputFile, decompressedContent);
                System.out.println("Decompression successful.");

            } else {
                System.out.println("Invalid action. Use 'compress' or 'decompress'.");
            }

        } catch (IOException e) {
            System.err.println("IO Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
