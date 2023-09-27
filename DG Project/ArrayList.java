/*HW: Queues, Stacks, and Quacks: old ADTs now with Linked Lists!
 * Quack Class
 * CSS 143
 * Ryan Vu
 * 5/17/2023
 */
public class ArrayList<T>{
  //Inner class -> Node
  private class Node{
    //Variables used
    private T data; //Holds the object
    private Node link; //Links with the other nodes

    /*Default constructor: sets data to 0 and link to null*/

    public Node(){
      this.data = null;
      this.link = null;
    }

    /*Constructor: sets data to the input and link to null*/

    public Node(T newData){
      this.data = newData;
      this.link = null;
    }

    /*Constructor: sets data to input and link to the node input*/

    public Node(T newData, Node newNode){
      this.data = newData;
      this.link = newNode;
    }
  }
  //Variables used in the Quack Class
  private Node head = null; //Sets the head to null

 /**
 * Description: add data Object item to whichever index
 * PRE-CONDITION: Valid input and index
 * POST-CONDITION: Object is inserted at x index and connecting to the next link
 */   

  protected void insert(T newData, int index){
    if(index > size() || index < 0){
      System.out.println("index is out of bounds");
    } else{
        if(isEmpty()){
          head = new Node(newData);
        } else if(size() == 1 || index == 0){
            head = new Node(newData, head);
          } else if(size() >= 2){
              Node current = head;
              for(int i = 0; i < index-1; i++){
                current = current.link;
              }
              current.link = new Node(newData, current.link);
            }
      }
  }

 /**
 * Description: add data Object item to the end of the list
 * PRE-CONDITION: Valid input
 * POST-CONDITION: Node is created using the Object at the last link
 */   

  protected void add(T newData){
    if(isEmpty()){
      head = new Node(newData);
    } else{
        Node current = head;
        while(current.link != null){
          current = current.link;
        }
        current.link = new Node(newData);
      }
  }

 /**
 * Description: Removes the object at x index and returns the removed variable
 * PRE-CONDITION: Valid input
 * POST-CONDITION: List is one data shorter and returns the variable removed
 */   

  protected T remove(int index){
    if(index > size() || index < 0){
      System.out.println("Index is out of bounds");
      return null;
    }
    T retVal = null;
    if(isEmpty()){
      System.out.println("Can't remove an empty list");
      return null;
    } else if(size() == 1 || index == 0){

      retVal = head.data;
      head = head.link;
      return retVal;
    } else if(size() >= 2){
        Node current = head;
        for(int i = 0; i < index-1; i++){
          current = current.link;
        }
        retVal = current.link.data;
        current.link = current.link.link;
        return retVal;
    }
    return retVal;
  }

 /**
 * Description: add data Object item to the end of the list
 * PRE-CONDITION: Valid input
 * POST-CONDITION: Node is created using the Object at the last link
 */ 

  protected void append(T newData){
    if(isEmpty()){
      head = new Node(newData);
    } else{
        Node current = head;
        while(current.link != null){
          current = current.link;
        }
        current.link = new Node(newData);
      }
  }

 /**
 * Description: Removes the object at x index
 * PRE-CONDITION: Valid input
 * POST-CONDITION: List is one data shorter at x index
 */   

  protected void delete(int index){
    if(isEmpty()){
      System.out.println("deleting from empty list");
      head = null;
    } else if(size() == 1){
        head = head.link;
      } else if(size() >= 2){
          Node current = head;
          for(int i = 0; i < index-1; i++){
            current = current.link;
          }
          current.link = current.link.link;
        }
  }

 /**
 * Description:  returns the object at that index. (if that index doesn't exist, print a message and return null.) 
 * PRE-CONDITION: Valid input
 * POST-CONDITION: Retrieves an object
 */   

  protected T get(int index){
    if(isEmpty()){
      System.out.println("Retrieving from empty list");
      return null;
    }
    if(index > size() || index < 0){
      System.out.println("Index is out of bounds");
      return null;
    }
    Node current = head;
    for(int i = 0; i < index - 1; i++){
      current = current.link;
    }
    T retVal = current.link.data;
    return retVal;
  }

 /*Description: Loops through the list and adds 1 to size if there's a valid
 * Object in the index of the list
 * POST-CONDITION: Returns size
 */ 

  public int size(){
    int count = 0;
    Node current = head;
    while(current != null){
      count++;
      current = current.link;
    }
    return count;
  }

//ToString method to list all of the objects separated by spaces to make it easier to read
  public String toString(){
    Node current = head;
    String s = "";
    if(isEmpty()){
      return s;
    }
    while(current != null){
      s = s + " " + current.data;
      current = current.link;
    }
    return s;
  }
  
/**
 * Description: Compareds head if null or not
 * POST-CONDITION: returns true/false
 */   

  public boolean isEmpty(){
    return head == null;
  }

 /**
 * Description:  returns the index of the Object in the list
 * PRE-CONDITION: Valid input
 * POST-CONDITION: returns an int or -1 if not found
 */   

  protected int indexOf(Object target){
    int index = 0;
    Node current = head;
    while(current != null){
      if(current.data.equals(target)){
        return index;
      }
      current = current.link;
      index++;
    }
    return -1;
  }

/**
 * Description: Tranverses both nodes and compares the data and position
 * PRE-CONDITION: Valid input
 * POST-CONDITION: returns true/false
 */  

  public boolean equals(Object otherO){
    if(otherO == null){
      return false;
    } else if (this.getClass() != otherO.getClass()) {
        return false;
    } else{
        ArrayList<T> other = (ArrayList<T>) otherO;
        Node thisPosition = this.head;
        Node otherPosition = other.head;
        while(thisPosition != null && otherPosition != null){
          if(thisPosition.data != otherPosition.data){
            return false;
          } else{
              thisPosition = thisPosition.link;
              otherPosition = otherPosition.link;
          }
        }
        if(thisPosition == null && otherPosition == null){
          return true;
        } else{
            return false;
        }
      }
  }

 /**
 * Description: Empties the array List
 * PRE-CONDITION: None
 * POST-CONDITION: Array List no longer has any objects
 */   
  public void clear(){
    if(!isEmpty()){
      delete(0);
    }
  }
}