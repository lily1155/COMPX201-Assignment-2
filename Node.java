 //A node is necessary to construct our binary search tree. It stores our appliance, then has a node directing to the left and right subtrees
    //then finally it has a constructor in which initialising the node requires it to be passed an appliance, which as a variable is named appy for short
    public class Node{
        public Appliance appy;
        public Node left;
        public Node right;
        public Node parent;
        public Node(Appliance Appy){
            appy = Appy;
            left = null;
            right = null;
        }
    }