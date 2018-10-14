package problemSet_1;

public class AVLTree {

    public static void main(String[] a){
        AVLTree avlTree = new AVLTree();
        Node root = null;
        // Insertion operation
        root = avlTree.insert(root, 150);
        root = avlTree.insert(root, 1);
        root = avlTree.insert(root, -10);
        root = avlTree.insert(root, 20);
        root = avlTree.insert(root, -2);
        root = avlTree.insert(root, 100);
        root = avlTree.insert(root, 200);
        avlTree.inOrder(root);
        System.out.println();
        avlTree.preOrder(root);
        System.out.println();
        System.out.println();

        // Deletion Operation
        root = avlTree.delete(root, 20);
        avlTree.inOrder(root);
        System.out.println();
        avlTree.preOrder(root);
    }

    private Node rightRotate(Node root){
        Node newNode = root.left;
        root.left = root.left.right;
        newNode.right = root;
        setHeight(root);
        setHeight(newNode);
        return newNode;
    }

    private Node leftRotate(Node root){
        Node newNode = root.right;
        root.right = root.right.left;
        newNode.left = root;
        setHeight(root);
        setHeight(newNode);
        return newNode;
    }

    private void setHeight(Node root){
        root.height = 1 + Math.max((root.left != null ? root.left.height : 0),
                (root.right != null ? root.right.height : 0));
    }

    private int height(Node root){
        return root != null ? root.height : 0;
    }

    Node insert(Node root, int data){
        if(root == null)
            return new Node(data);
        if(data <= root.data){
            root.left = insert(root.left, data);
        } else {
            root.right = insert(root.right, data);
        }

        return balancing(root);
    }

    private Node balancing(Node root){
        int balance = (root.left != null ? root.left.height : 0) - (root.right != null ? root.right.height : 0);
        if(balance > 1){
            if(height(root.left.left) >= height(root.left.right))
                root = rightRotate(root);
            else {
                root.left = leftRotate(root.left);
                root = rightRotate(root);
            }

        } else if(balance < -1){
            if(height(root.right.right) >= height(root.right.left))
                root = leftRotate(root);
            else {
                root.right = rightRotate(root.right);
                root = leftRotate(root);
            }
        } else
            setHeight(root);

        return root;
    }

    private Node delete(Node root, int data){
        if(root == null)
            return root;
        if(data < root.data)
            root.left = delete(root.left, data);
        else if(data > root.data)
            root.right = delete(root.right, data);
        else {
            if(root.left == null && root.right == null)
                root = null;
            else if(root.left == null && root.right != null)
                root = root.right;
            else if(root.right == null && root.left != null)
                root = root.left;
            else{
                Node temp = root.left;
                while(temp.right != null){
                    temp = temp.right;
                }
                root.data = temp.data;
                delete(root.left, temp.data);
            }
        }
        if(root == null)
            return root;
        setHeight(root);
        return balancing(root);
    }

    private void inOrder(Node root){
        if(root == null)
            return;
        inOrder(root.left);
        System.out.println(root.data);
        inOrder(root.right);
    }

    private void preOrder(Node root){
        if(root == null)
            return;
        System.out.println(root.data);
        preOrder(root.left);
        preOrder(root.right);
    }
    private class Node {
        private Node(int data){
            this.data = data;
            this.height = 1;
        }
        private int data;
        private Node left;
        private Node right;
        private int height;
    }
}
