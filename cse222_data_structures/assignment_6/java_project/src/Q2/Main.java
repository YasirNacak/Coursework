package Q2;

public class Main<E extends Comparable<E>> {
    public static void main(String[] args) {
        BTree<Integer> BTree = new BTree<>(3);
        System.out.println("=======================================");
        BTree.add(75);
        System.out.println(BTree);
        System.out.println("=======================================");
        BTree.add(59);
        System.out.println(BTree);
        System.out.println("=======================================");
        BTree.add(56);
        System.out.println(BTree);
        System.out.println("=======================================");
        BTree.add(108);
        System.out.println(BTree);
        System.out.println("=======================================");
        BTree.add(77);
        System.out.println(BTree);
        System.out.println("=======================================");
        BTree.add(134);
        System.out.println(BTree);
        System.out.println("=======================================");
        BTree.add(47);
        System.out.println(BTree);
        System.out.println("=======================================");
        BTree.add(53);
        System.out.println(BTree);
        System.out.println("=======================================");
        BTree.add(106);
        System.out.println(BTree);
        System.out.println("=======================================");
        BTree.add(127);
        System.out.println(BTree);
        System.out.println("=======================================");
    }
}
