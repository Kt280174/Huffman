// Refactored FileManager.java
package HuffmanAlgorithm;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FileIOHandler {

    public static byte[] readFileAsByteArray(String filePath) throws IOException {
        Path inputPath = Paths.get(filePath);
        try (InputStream inputStream = Files.newInputStream(inputPath)) {
            long fileSize = Files.size(inputPath);
            if (fileSize > Integer.MAX_VALUE) {
                throw new IOException("File is too large to process.");
            }

            byte[] content = new byte[(int) fileSize];
            int readBytes = inputStream.read(content);
            if (readBytes != content.length) {
                throw new IOException("Failed to read the complete file content.");
            }
            return content;
        }
    }

    public static Map<String, Byte> readCodeMappings(String codeFilePath) {
        Map<String, Byte> codeMap = new HashMap<>();

        try (DataInputStream dataStream = new DataInputStream(new FileInputStream(codeFilePath))) {
            while (dataStream.available() > 0) {
                byte dataByte = dataStream.readByte();
                int codeLength = dataStream.readByte();
                int codeValue = dataStream.readInt();
                String binaryCode = String.format("%" + codeLength + "s", Integer.toBinaryString(codeValue)).replace(' ', '0');
                codeMap.put(binaryCode, dataByte);
            }
        } catch (IOException e) {
            System.out.println("Error reading code file: " + e.getMessage());
            return null;
        }

        return codeMap;
    }

    public static void writeToFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }

    public static void writeCodeMappings(String filePath, Map<Byte, String> codeMappings) {
        try (DataOutputStream dataStream = new DataOutputStream(new FileOutputStream(filePath))) {
            for (Map.Entry<Byte, String> entry : codeMappings.entrySet()) {
                dataStream.writeByte(entry.getKey());
                dataStream.writeByte(entry.getValue().length());
                dataStream.writeInt(Integer.parseInt(entry.getValue(), 2));
            }
        } catch (IOException e) {
            System.out.println("Error writing code mappings: " + e.getMessage());
        }
    }
}
