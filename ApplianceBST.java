public class ApplianceBST{
        Node root;
        public ApplianceBST(){
            root = null;
        }
        public void insert(Appliance a){
            Node insert = new Node(a);
            if(root == null){
                root = insert;
                System.out.println(root.appy.getCategory());
                return;}
            else{
                int flipper = a.compareTo(root.appy);
                root = insertManager(flipper, insert, root);
            }
        }
        private Node insertManager(int flipper, Node insert, Node cRoot){
            if(cRoot == null){
                return insert;
            }else if (flipper > 0){
                int flip = insert.appy.compareTo(cRoot.appy);
                cRoot.left = insertManager(flip, insert, cRoot.left);
            }else if (flipper < 0){
                int flip = insert.appy.compareTo(cRoot.appy);
                cRoot.right = insertManager(flip, insert, cRoot.right);
            } //matches are implicitly resolved by simply not doing anything unless a node is null or not the same as the insert node
            return cRoot;
        }

        public void remove(Appliance a){
            removeManager(root, a);
        }
        //in order to move down the tree from the root, there needs to be some kind of code repetition in which a different node is being investigated each time. i've chosen to implement that through a method being 
        //called repeatedly. removeManager takes a node and an appliance and works out if the appliance is in the node. if it is, great! if not, then it calls itself using the nodes children as inputs.
        private void removeManager(Node cRoot, Appliance a){
            if(cRoot.appy == a){
                if(cRoot.left == null){
                    removeExecuter(cRoot, false);
                    return;
                }
                else{
                    removeExecuter(cRoot, true);
                    return;
                }
            }else if(cRoot.left == null&& cRoot.right == null || cRoot == null){
                return;
            }
            //if the code of this method is still running this is the case where the node has children but doesn't match our deletion criteria
            int searchAssistant = a.compareTo(cRoot.appy);
            if (searchAssistant > 0){
                removeManager(cRoot.left, a);
            }else{
                removeManager(cRoot.right, a);
            }
        }

        //removeManager will use this method to finally remove the node once a correct node has been identified. the reason why this is in a separate method is mostly because i find that more readable
        //delNode is the node to be removed and isLeft tells us if the deleted node is the parents left or right node. its quite a complex algorithm so explanations will be provided in comments
        private void removeExecuter(Node delNode, boolean isLeft){
            if(delNode.left == null && delNode.right == null){//case where the node has no children. it can simply be deleted then instead of its position needing to be replaced!
                delNode = null;
            }else if (delNode.left != null && delNode.right == null ){// case where there's a left node. single parents are removed by their children taking their position
                delNode.left = delNode;            
            }else if (delNode.left == null && delNode.right != null){//case where there's only a right node. single parents are removed by their children taking their position
                delNode.right = delNode;    
            }else if (delNode.left != null && delNode.right != null){
                Node setNode = null;
                Node currentNode = delNode.right;//we want to find the leftmost available node of the right subtree
                while(setNode == null){
                    if(currentNode.left !=null){
                        currentNode = currentNode.left;
                    }else{
                        setNode = currentNode;
                    }
                }
                delNode = setNode;
            }
        }//end of removeExecuter
        //executes required method to see if appliance a is in the BST
        public boolean search(Appliance a){
            return searchManager(root, a);
        }
        //searches for appliance a. it inputs cRoot. if cRoot has a, then it returns true. if not and cRoot is null it returns false. if it's neither, it'll work out which direction in the
        //tree it needs to go down and ask that node if it has a or not. eventually, a node will be found that either has a or is null, and this will pass the answer back up through
        //however many used searchManager calls and pass our answer back to the search method itself. this method is private to avoid confusion when this class is being interacted with
        //as another class shouldn't have nodes to pass to this method anyway so they have no business calling it.
        private boolean searchManager(Node cRoot, Appliance a){
            if(cRoot == null){return false;}
            else if(a.compareTo(cRoot.appy) == 0){return true;}
            else{
                System.out.println(a.compareTo(cRoot.appy));
                int searchAssistant = a.compareTo(cRoot.appy);
                if(searchAssistant > 0){
                    return searchManager(cRoot.left, a);
                }else{return searchManager(cRoot.right, a);}
            }
        }
        //gets the height of the tree. this is mostly done through another method, but of course a different method needs to exist that doesn't need to be passed a root node so other objects can interact with this
        //class even though they won't have access to the root node. however this has the additional functionality of solving an inaccuracy the iteration causes which can't be resolved within the iteration itself
        //as it needs to happen exactly once
        public int getHeight(){
            return getHeightManager(root) -1;//because getHeightManager technically counts the root node as contributing to the height, the total needs to be reduced by exactly one. 
        }
        //peforms the necessary algorithm for getHeight's functionality
        private int getHeightManager(Node cRoot){
            if(cRoot == null){
                return 0;
            }else{
                int leftHeight = getHeightManager(cRoot.left);
                int rightHeight = getHeightManager(cRoot.right);
                return Math.max(leftHeight, rightHeight) + 1; //the plus one is necessary because otherwise this will always return 0. instead it returns the number of times this method was iterated from a null node
            }
        }
        //Calls its manager to get the minimum value within the tree having passed its manager the root node
        public Appliance getMinimum(){
            return getMinimumManager(root).appy;
        }
        //Algorithm for finding the minimum value. this will be the leftmost node.
        private Node getMinimumManager(Node cRoot){
            if(cRoot.left != null){
                return getMinimumManager(cRoot.left);
            }
            return cRoot;
        }
        //Calls its manager to get the maximum value within the tree having passed its manager the root node
        public Appliance getMaximum(){
            return getMaximumManager(root).appy;
        }
        //Algorithm for finding the maximum value. this will be the rightmost node.
        private Node getMaximumManager(Node cRoot){
            if(cRoot.right != null){
                return getMaximumManager(cRoot.right);
            }return cRoot;
        }

        //prints all values in the tree. doesn't need to be passed anything. simply runs its manager with the root node and then ends
        public void print(){
            printManager(root);
        }

        private void printManager(Node cRoot){
            if(cRoot == null){ //case where the root node is null, in which we want this to do nothing
                return;
            }
            if (cRoot.left != null){ //move to the left node first
                printManager(cRoot.left);
            }
            System.out.println(appy.toString()); //then the root node
            if (cRoot.right != null){
                printManager(cRoot.right); //move to the right node last
            }
        }


        //prints everything in category c that's between the price ranges of min and max. uses another method for the main algorithm so that other classes can use this method without having access to the root node
        public void printCategoryWithPriceRange(String c, float min, float max){
            Appliance lowerBound = new Appliance(c, "aaaaaaa", min);
            Appliance upperBound = new Appliance(c,"zzzzzzz", max);
            printCategoryWithPriceRangeManager(c, lowerBound, upperBound, root);
        }

        private void printCategoryWithPriceRangeManager(String c, Appliance lowerBound, Appliance upperBound, Node cRoot){
            System.out.println("if no number follows, it thinks the node is null");
            if (cRoot == null){return;}
            System.out.println(cRoot.appy.compareTo(lowerBound));
            if(cRoot.appy.compareTo(lowerBound) > 0){
                printCategoryWithPriceRangeManager(c, lowerBound,upperBound, cRoot.right);
            }else if (cRoot.appy.compareTo(upperBound) < 0){
                printCategoryWithPriceRangeManager(c, lowerBound, upperBound, cRoot.left);
            }//it's safe to assume anything that's still running is now within our boundary prices and within category c
            printCategoryWithPriceRangeManager(c, lowerBound, upperBound, cRoot.left); //look at the left node first
            System.out.println(cRoot.appy.toString());// look at the root node next
            printCategoryWithPriceRangeManager(c, lowerBound, upperBound, cRoot.right);// look at the right node last
        }
        //This method initiates the printing of every appliance in the tree that contains the category. it starts by finding the first node travelling down the right in the category, 
        //then as long as a node is in the category and above the required minimum price it'll run itself for the left node, print its own value, then run itself for the right node. 
        public void printCategoryAbovePrice(String c, float min){
            Appliance lowerBound = new Appliance(c, "aaaaaaa", min);
            printCategoryAbovePriceManager(c, lowerBound,root);
        }
        //executes algorithm for printCategoryAbovePrice
        private void printCategoryAbovePriceManager(String c, Appliance lowerBound, Node cRoot){
            if (cRoot == null){return;}
            System.out.println(cRoot.appy.compareTo(lowerBound));
            if (cRoot.appy.compareTo(lowerBound)>0){
                printCategoryAbovePriceManager(c, lowerBound,cRoot.right);
            }else if(cRoot.appy.getCategory() == c && cRoot.appy.compareTo(lowerBound) < 0){ //only prints and checks the children of nodes that are in the category and above the minimum price
                printCategoryAbovePriceManager(c, lowerBound,root.left); //look at left node first
                System.out.println(cRoot.appy.toString()); //look at root node next
                printCategoryAbovePriceManager(c, lowerBound,cRoot.right);// look at right node last
            }
        }

        // Similar to printCategoryAbovePrice but for printing everything below a maximum value. the string is still passed to register the category but now the float is called max and is used to create an appliance
        // which is only used for the sake of ensuring the upper limit isn't exceeded, instead of for a lower limit. uses in order traversal like all other printing methods
        public void printCategoryBelowPrice(String c, float max){
            Appliance upperBound = new Appliance(c, "aaaaaaa", max);
            printCategoryBelowPriceManager(c, upperBound, root);
        }
        //executes algorithm for printCategoryBelowPrice
        private void printCategoryBelowPriceManager(String c, Appliance upperBound, Node cRoot){
            if (cRoot == null){return;}
            Appliance catappy = new Appliance(c,"aaaaaa", 0.0); //used to test if a node is to the left of the desired category
            if (cRoot.appy.compareTo(catappy)>0){
                printCategoryAbovePriceManager(c, upperBound,cRoot.right);
            }else if(cRoot.appy.getCategory() == c && cRoot.appy.compareTo(upperBound) > 0){ //only prints and checks the children of nodes that are in the category and below the maximum price
                printCategoryAbovePriceManager(c, upperBound,root.left); //look at left node first
                System.out.println(cRoot.appy.toString()); //look at root node next
                printCategoryAbovePriceManager(c, upperBound,cRoot.right);// look at right node last
            }
        }

        //prints everything inside the category. a manager for this would be superfluous, it simply runs printCategoryAbovePriceManager but passing an appliance that's free. as a result this program assumes
        //anything inserted into the tree that customers would be paid to take is a mistake
        public void printCategory(String c){
            //constructs a test appliance with the absolute lowest value this category could have
            Appliance test = new Appliance(c, "aaaaaaa", 0.0f); //screaming is always free
            printCategoryAbovePriceManager(c, test, root);
        }
    }//end of ApplianceBST class