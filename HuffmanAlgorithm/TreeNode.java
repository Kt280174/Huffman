package HuffmanAlgorithm;

public class TreeNode implements Comparable<TreeNode> {
    byte data;
    long frequency;
    TreeNode left, right;

    public TreeNode(byte data, long frequency) {
        this.data = data;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }

    public TreeNode(byte data, long frequency, TreeNode left, TreeNode right) {
        this.data = data;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    @Override
    public int compareTo(TreeNode otherNode) {
        return Long.compare(this.frequency, otherNode.frequency);
    }
}
