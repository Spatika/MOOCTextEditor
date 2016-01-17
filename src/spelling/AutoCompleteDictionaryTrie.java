package spelling;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT - interfaces
 * @author Spatika
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
		size = 0 ; 
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should ignore the word's case.
	 * That is, you should convert the string to all lower case as you insert it. */
	public boolean addWord(String word)
	{
	   
		String lowerCase_word = word.toLowerCase() ;  

		TrieNode curNode = new TrieNode() ; 
		
		if(isWord(lowerCase_word))
			return false ;
		
		curNode = root ; 
		int i ; 
		
		for(i = 0 ; i < lowerCase_word.length() ; i++) {
			
			TrieNode nextNode = curNode.getChild(lowerCase_word.charAt(i)) ; 
			
			if(nextNode != null)
				curNode = nextNode ; 
			
			else {
				curNode = curNode.insert(lowerCase_word.charAt(i)) ;  //if one of the letters isn't there then it's not a word in the trie, right?

			}
		}
		
		curNode.setEndsWord(true);
		size++ ;
		
		
	    return true ;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size() //size of dictionary, NOT number of nodes
	{
	    //TODO: Implement this method

	    return size ;
	}
	
	
	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String s) 
	{
	    // TODO: Implement this method
		
		if(s == "") //empty string
			return false ;
		
		String lowerCase_word = s.toLowerCase() ; 
		
		TrieNode curNode = new TrieNode() ; 
		
		curNode = root ; 
		int i ; 
		
		for(i = 0 ; i < lowerCase_word.length() ; i++) {
			
			TrieNode nextNode = curNode.getChild(lowerCase_word.charAt(i)) ; 
			
			if(nextNode != null)
				curNode = nextNode ; 
			else
				return false ;  //if one of the letters isn't there then it's not a word in the trie, right?
 
		}
		
		//another condition where false must be returned
		if(curNode.endsWord() == false) {
			return false ;
		}
		
		return true ; 
			
	}

	/** 
	 *  * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     * @param text The text to use at the word stem
     * @param n The maximum number of predictions desired.
     * @return A list containing the up to n best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 
    	 
    	 List<String> completions = new ArrayList<String>() ;
    	 
    	 prefix = prefix.toLowerCase() ;
    	 

 		 System.out.println("prefix is "+ prefix) ;
    	 
    	 TrieNode curNode = new TrieNode() ; 
 		
 		 curNode = root ; 
 		 int j ;
 		 
 		 for(j = 0 ; j < prefix.length() ; j++) {

  			//System.out.println(prefix.charAt(j)); -- is h
  			
 			TrieNode nextNode = curNode.getChild(prefix.charAt(j)) ; 
 			//System.out.println(curNode.getValidNextCharacters()); - is empty
 			//System.out.println(nextNode); -- null
 			
 			if(nextNode != null)
 				curNode = nextNode ;
 			
 			else {  //prefix isn't in here
 				break ;
 			}
 		 }

 		 System.out.println("curnode text is "+ curNode.getText()) ;
 		
 		
 		 //empty string can be prefix 
 		 if(prefix != "" && j < prefix.length())
 			 return completions ; 
 		 
 		 int numAdded = 0 ;
 		 
 		 if(curNode.endsWord()) {
 			 completions.add(curNode.getText());
 			 numAdded++;
 			 
 		 }
 		 //reached here --> prefix is in the Trie, perform BFS to populate completions
 		 Queue<TrieNode> qyoo = new LinkedList<TrieNode>() ;
 		 
 		 Set<Character> validCharsNext = curNode.getValidNextCharacters() ;
 		 
 		 System.out.println(validCharsNext) ;
 		
 	     for (Iterator<Character> it = validCharsNext.iterator(); it.hasNext(); ) {
 		        Character c = it.next() ;
 		        qyoo.add(curNode.getChild(c)) ;
 		 }
 	 
 	      
 		
 		 TrieNode x ;
 		 
 		 while(!(qyoo.isEmpty()) && (numAdded < numCompletions)) {
 			 x = qyoo.remove() ;
 			 
 			 //System.out.println("ends word of " + x.getText() + " is " + x.endsWord()) ;
 			 
 			 if(x.endsWord()) {
 				 
 				 completions.add(x.getText()) ;
 				 numAdded++ ;
 			 }//endif
 				 
 				 //add all child nodes to queue
 			 //validCharsNext.clear() ;
 			 validCharsNext = x.getValidNextCharacters() ;
 		 		 
 		 	 for (Iterator<Character> idx = validCharsNext.iterator(); idx.hasNext(); ) {
 		 		       Character c = idx.next() ;
 		 		       qyoo.add(x.getChild(c)) ;
 		 	 }//endfor
 				 
 		 }//end while
    	 
         return completions ;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}