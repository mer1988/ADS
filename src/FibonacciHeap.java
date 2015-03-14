import java.util.ArrayList;
import java.util.List;



public class FibonacciHeap {
 
    private Node min = null;
    private int size = 0;

    
    public void enqueue(Integer value, double priority) {

        Node newNode = new Node(value, priority);
        
        min = mergeLists(min, newNode);
        size += 1;
        
    }
    
    public Integer getMin() throws Exception{
        if (isEmpty())
            throw new Exception("Heap is empty.");
        return min.getElem();
    }

    public boolean isEmpty() {
        return min == null;
    }

 
    public int getSize() {
        return size;
    }

    
    public FibonacciHeap merge(FibonacciHeap fbh1, FibonacciHeap fbh2) {
        
    	FibonacciHeap result = new FibonacciHeap();

       
        result.min = mergeLists(fbh1.min, fbh2.min);

        
        result.size = fbh1.size + fbh2.size;

        fbh1.size = 0;
        fbh2.size = 0;
        fbh1.min  = null;
        fbh2.min  = null;

        return result;
    }

   
    public Integer dequeueMin() {
        
    	//if (isEmpty())
        //    throw new Exception("Heap is empty.");
        
        size -= 1;;

        Node minCopy = min;
       
        if (min.next == min) { 
            min = null; 
        }
        else {
            min.prev.next = min.next;
            min.next.prev = min.prev;
            min = min.next; 
        }

        /* Next, clear the parent fields of all of the min element's children,
         * since they're about to become roots.  Because the elements are
         * stored in a circular list, the traversal is a bit complex.
         */
        if (minCopy.child != null) {
            
            Node n = minCopy.child;
            
            do {
                n.parent = null;
                n = n.next;
            } while (n != minCopy.child);
        }

        /* Next, splice the children of the root node into the topmost list, 
         * then set mMin to point somewhere in that list.
         */
        min = mergeLists(min, minCopy.child);

        /* If there are no entries left, we're done. */
        if (min == null) return minCopy.getElem();

        /* Next, we need to coalsce all of the roots so that there is only one
         * tree of each degree.  To track trees of each size, we allocate an
         * ArrayList where the entry at position i is either null or the 
         * unique tree of degree i.
         */
        List<Node> treeTable = new ArrayList<Node>();

        /* We need to traverse the entire list, but since we're going to be
         * messing around with it we have to be careful not to break our
         * traversal order mid-stream.  One major challenge is how to detect
         * whether we're visiting the same node twice.  To do this, we'll
         * spent a bit of overhead adding all of the nodes to a list, and
         * then will visit each element of this list in order.
         */
        List<Node> toVisit = new ArrayList<Node>();

        /* To add everything, we'll iterate across the elements until we
         * find the first element twice.  We check this by looping while the
         * list is empty or while the current element isn't the first element
         * of that list.
         */
        for (Node n = min; toVisit.isEmpty() || toVisit.get(0) != n; n = n.next)
            toVisit.add(n);

        /* Traverse this list and perform the appropriate unioning steps. */
        for (Node n: toVisit) {
            /* Keep merging until a match arises. */
            while (true) {
                /* Ensure that the list is long enough to hold an element of this
                 * degree.
                 */
                while (n.degree >= treeTable.size())
                    treeTable.add(null);

                /* If nothing's here, we're can record that this tree has this size
                 * and are done processing.
                 */
                if (treeTable.get(n.degree) == null) {
                    treeTable.set(n.degree, n);
                    break;
                }

                /* Otherwise, merge with what's there. */
                Node n2 = treeTable.get(n.degree);
                treeTable.set(n.degree, null); // Clear the slot

                /* Determine which of the two trees has the smaller root, storing
                 * the two tree accordingly.
                 */
                Node min = (n2.priority < n.priority)? n2 : n;
                Node max = (n2.priority < n.priority)? n  : n2;

                /* Break max out of the root list, then merge it into min's child
                 * list.
                 */
                max.next.prev = max.prev;
                max.prev.next = max.next;

                /* Make it a singleton so that we can merge it. */
                max.next = max.prev = max;
                min.child = mergeLists(min.child, max);
                
                /* Reparent max appropriately. */
                max.parent = min;

                /* Clear max's mark, since it can now lose another child. */
                max.marked = false;

                /* Increase min's degree; it now has another child. */
                min.degree += 1;

                /* Continue merging this tree. */
                n = min;
            }

            /* Update the global min based on this node.  Note that we compare
             * for <= instead of < here.  That's because if we just did a
             * reparent operation that merged two different trees of equal
             * priority, we need to make sure that the min pointer points to
             * the root-level one.
             */
            if (n.priority <= min.priority) min = n;
        }
        return minCopy.getElem();
    }

    
    
    /**
     * Deletes this Entry from the Fibonacci heap that contains it.
     *
     * It is assumed that the entry belongs in this heap.  For efficiency
     * reasons, this is not checked at runtime.
     *
     * @param entry The entry to delete.
     */
    public void delete(Node entry) throws Exception{
        /* Use decreaseKey to drop the entry's key to -infinity.  This will
         * guarantee that the node is cut and set to the global minimum.
         */
        decreaseKey(entry, Double.NEGATIVE_INFINITY);

        /* Call dequeueMin to remove it. */
        dequeueMin();
    }

   
    /**
     * Utility function which, given two pointers into disjoint circularly-
     * linked lists, merges the two lists together into one circularly-linked
     * list in O(1) time.  Because the lists may be empty, the return value
     * is the only pointer that's guaranteed to be to an element of the
     * resulting list.
     *
     * This function assumes that one and two are the minimum elements of the
     * lists they are in, and returns a pointer to whichever is smaller.  If
     * this condition does not hold, the return value is some arbitrary pointer
     * into the doubly-linked list.
     *
     * @param one A pointer into one of the two linked lists.
     * @param two A pointer into the other of the two linked lists.
     * @return A pointer to the smallest element of the resulting list.
     */
    private static Node mergeLists(Node n1, Node n2) {
        /* There are four cases depending on whether the lists are null or not.
         * We consider each separately.
         */
        if (n1 == null && n2 == null) { // Both null, resulting list is null.
            return null;
        }
        else if (n1 != null && n2 == null) { // Two is null, result is one.
            return n1;
        }
        else if (n1 == null && n2 != null) { // One is null, result is two.
            return n2;
        }
        else { // Both non-null; actually do the splice.
            /* This is actually not as easy as it seems.  The idea is that we'll
             * have two lists that look like this:
             *
             * +----+     +----+     +----+
             * |    |--N->|one |--N->|    |
             * |    |<-P--|    |<-P--|    |
             * +----+     +----+     +----+
             *
             *
             * +----+     +----+     +----+
             * |    |--N->|two |--N->|    |
             * |    |<-P--|    |<-P--|    |
             * +----+     +----+     +----+
             *
             * And we want to relink everything to get
             *
             * +----+     +----+     +----+---+
             * |    |--N->|one |     |    |   |
             * |    |<-P--|    |     |    |<+ |
             * +----+     +----+<-\  +----+ | |
             *                  \  P        | |
             *                   N  \       N |
             * +----+     +----+  \->+----+ | |
             * |    |--N->|two |     |    | | |
             * |    |<-P--|    |     |    | | P
             * +----+     +----+     +----+ | |
             *              ^ |             | |
             *              | +-------------+ |
             *              +-----------------+
             *
             */
            Node oneNext = n1.next; // Cache this since we're about to overwrite it.
            n1.next = n2.next;
            n1.next.prev = n1;
            n2.next = oneNext;
            n2.next.prev = n2;

            /* Return a pointer to whichever's smaller. */
            return n1.priority < n2.priority? n1 : n2;
        }
    }

    /**
     * Decreases the key of a node in the tree without doing any checking to ensure
     * that the new priority is valid.
     *
     * @param entry The node whose key should be decreased.
     * @param priority The node's new priority.
     */
    public void decreaseKey(Node n, double priority) {
        /* First, change the node's priority. */
        n.priority = priority;

        /* If the node no longer has a higher priority than its parent, cut it.
         * Note that this also means that if we try to run a delete operation
         * that decreases the key to -infinity, it's guaranteed to cut the node
         * from its parent.
         */
        if (n.parent != null && n.priority <= n.parent.priority)
            cutNode(n);

        /* If our new value is the new min, mark it as such.  Note that if we
         * ended up decreasing the key in a way that ties the current minimum
         * priority, this will change the min accordingly.
         */
        if (n.priority <= min.priority)
            min = n;
    }

    /**
     * Cuts a node from its parent.  If the parent was already marked, recursively
     * cuts that node from its parent as well.
     *
     * @param entry The node to cut from its parent.
     */
    private void cutNode(Node n) {
        /* Begin by clearing the node's mark, since we just cut it. */
        n.marked = false;

        /* Base case: If the node has no parent, we're done. */
        if (n.parent == null) return;

        /* Rewire the node's siblings around it, if it has any siblings. */
        if (n.next != n) { // Has siblings
            n.next.prev = n.prev;
            n.prev.next = n.next;
        }

        /* If the node is the one identified by its parent as its child,
         * we need to rewrite that pointer to point to some arbitrary other
         * child.
         */
        if (n.parent.child == n) {
            /* If there are any other children, pick one of them arbitrarily. */
            if (n.next != n) {
                n.parent.child = n.next;
            }
            /* Otherwise, there aren't any children left and we should clear the
             * pointer and drop the node's degree.
             */
            else {
                n.parent.child = null;
            }
        }

        /* Decrease the degree of the parent, since it just lost a child. */
        n.parent.degree -= 1;

        /* Splice this tree into the root list by converting it to a singleton
         * and invoking the merge subroutine.
         */
        n.prev = n.next = n;
        min = mergeLists(min, n);

        /* Mark the parent and recursively cut it if it's already been
         * marked.
         */
        if (n.parent.marked)
            cutNode(n.parent);
        else
            n.parent.marked = true;

        /* Clear the relocated node's parent; it's now a root. */
        n.parent = null;
    }
    
    
    /*
     * Fib Heap Node class
     * */
    
    private class Node {
        private int     	degree = 0;       
        private boolean 	marked = false; 
        private Node 		next, prev, parent, child;  
        private Integer     elem;     
        private double 		priority; 

        private Node(Integer elem, double priority) {
            next = this;
            prev = this;
            this.elem = elem;
            this.priority = priority;
        }
        
        public Integer getElem() {
            return elem;
        }
       
        public void setElem(Integer elem) {
            this.elem = elem;
        }

        public double getPriority() {
            return priority;
        }

        
    }
    
    
}