package HuffmanAlgorithm;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class HuffmanTreeGenerator {
    private TreeNode root;
    private final Map<Byte, String> huffmanCodeMap = new HashMap<>();
    private Map<Byte, Integer> calculateFrequency(String filePath) throws IOException {
        Map<Byte, Integer> frequencyMap = new HashMap<>();
        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            int byteData;
            while ((byteData = inputStream.read()) != -1) {
                byte dataByte = (byte) byteData;
                frequencyMap.put(dataByte, frequencyMap.getOrDefault(dataByte, 0) + 1);
            }
        }
        return frequencyMap;
    }
    public void constructTreeFromData(String filePath) throws IOException {
        Map<Byte, Integer> frequencyMap = calculateFrequency(filePath);

        PriorityQueue<TreeNode> nodeQueue = frequencyMap.entrySet()
            .stream()
            .map(entry -> new TreeNode(entry.getKey(), entry.getValue()))
            .collect(Collectors.toCollection(PriorityQueue::new));

        while (nodeQueue.size() > 1) {
            TreeNode left = nodeQueue.poll();
            TreeNode right = nodeQueue.poll();
            TreeNode parent = new TreeNode((byte) 0, left.frequency + right.frequency, left, right);
            nodeQueue.add(parent);
        }

        root = nodeQueue.isEmpty() ? null : nodeQueue.poll();
        if (root != null) {
            generateHuffmanCodes(root, "");
        }
    }


    private void generateHuffmanCodes(TreeNode node, String code) {
        if (node == null) {
            return;
        }

        if (node.left == null && node.right == null) {
            if (code.isEmpty()) {
                code = "0";
            }
            huffmanCodeMap.put(node.data, code);
        }

        generateHuffmanCodes(node.left, code + '0');
        generateHuffmanCodes(node.right, code + '1');
    }

    public String exportCodesAsString() {
        StringBuilder result = new StringBuilder();
        huffmanCodeMap.forEach((key, value) -> {
        result.append(key)
            .append(":")
            .append(value)
            .append(System.lineSeparator());
        });
        return result.toString();
    }

    public Map<Byte, String> generateHuffmanCodes() {
        return huffmanCodeMap;
    }

    public TreeNode getRootNode() {
        return root;
    }
}
