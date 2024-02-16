// --== CS400 File Header Information ==--
// Name: Chengtao Dai
// Email: cdai53@wisc.edu
// Group and Team: G09
// Group TA: Robert Nagel
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType>{

  /**
   * This class is used to store pairs of key-value pairs in hashtable array.
   */
  protected class Pair {

    public KeyType key;
    public ValueType value;

    public Pair(KeyType key, ValueType value) {
      this.key = key;
      this.value = value;
    }

  }

  protected LinkedList<Pair>[] table;
  private int size;
  @SuppressWarnings("unchecked")
  public HashtableMap(int capacity){
    table = (LinkedList<Pair>[]) new LinkedList[capacity];
    for(int i = 0; i < capacity; i++){ //fixme here, why previous is not correct? ie for each
      table[i] = new LinkedList<>();
    }
    size = 0;
  }
  public HashtableMap(){
    this(32);
  } // with default capacity = 32

  @Override
  public void put(KeyType key, ValueType value) throws IllegalArgumentException {
    if (key == null)
      throw new IllegalArgumentException("Invalid key.");

    if (this.containsKey(key))
      throw new IllegalArgumentException("Duplicate key.");

    //when load factor becomes greater than or equal to 75%, dynamically grow your hashtable
    double comp = (double)(this.getSize() + 1) / this.getCapacity();
    if (comp >= 0.75){
      growHashtable();
    }

    int index = calcHashIndex(key);
    table[index].add(new Pair(key, value));
    size++;

  }

  /**
   * This method dynamically grow your hashtable by doubling its capacity and rehashing, whenever
   * its load factor becomes greater than or equal to 75%.
   */
  private void growHashtable(){
    LinkedList<Pair>[] newTable = (LinkedList<Pair>[]) new LinkedList[getCapacity() * 2];
    for(int i = 0; i < getCapacity() * 2; i++){
      newTable[i] = new LinkedList<>();
    }

    for (int i = 0; i < table.length; i++) {
      for(Pair pair : table[i]) {
        newTable[Math.abs(pair.key.hashCode()) % newTable.length].add(pair);
      }
    }
    table = newTable;
  }

  @Override
  public boolean containsKey(KeyType key) {
    if (key == null)
      throw new NullPointerException("null keys not allowed");
    int index = Math.abs(key.hashCode()) % this.getCapacity();
    LinkedList<Pair> cmp = table[index];
    if (cmp == null)
      return false;

    for (Pair pair : cmp){
      if (pair.key.equals(key))
        return true;
    }

    return false;
  }

  @Override
  public ValueType get(KeyType key) throws NoSuchElementException {
    if (key == null)
      throw new NullPointerException("Null keys are not allowed.");
    if (!this.containsKey(key))
      throw new NoSuchElementException();
    Pair toReturn = lookup(key); // keys cannot be duplicate
    return toReturn.value;
  }

  @Override
  public ValueType remove(KeyType key) throws NoSuchElementException {
    if (key == null)
      throw new NullPointerException("Null keys are not allowed.");
    if (!this.containsKey(key))
      throw new NoSuchElementException();

    Pair toReturn = lookup(key);
    table[calcHashIndex(key)].remove(toReturn);
    size--;
    return toReturn.value;
  }

  /**
   * This is a helper method that is used to look up the exact key-value pair based on the given
   * key. If it doesn't exist, return null.
   * @param key - the key that the loopup is based on.
   * @return - the exact Pair with the given key.
   */
  private Pair lookup(KeyType key){
    Pair toReturn = null;
    for (LinkedList<Pair> list : table){
      for (Pair pair : list){
        if (pair.key.equals(key)){
          toReturn = pair;
        }
      }
    }
    return toReturn;
  }

  /**
   *
   * @param key
   * @return
   */
  private int calcHashIndex (KeyType key){
    return Math.abs(key.hashCode()) % this.getCapacity();
  }

  @Override
  public void clear() {
    this.size = 0;
    for (LinkedList<Pair> list : table){
      list = new LinkedList<>();
    }
  }

  @Override
  public int getSize() {
    return this.size;
  }

  @Override
  public int getCapacity() {
    return this.table.length;
  }

  @Test
  public void testCapacity(){
    {
      HashtableMap<String, Integer> map = new HashtableMap<>();
      Assertions.assertEquals(32, map.table.length);
    }
    {
      HashtableMap<String, Integer> map = new HashtableMap<>(64);
      Assertions.assertEquals(64, map.table.length);
    }
  }

  @Test
  public void testInitialSize(){
    HashtableMap<String, Integer> map = new HashtableMap<>();
    Assertions.assertEquals(0, map.getSize());
  }

  @Test
  public void testPut(){
    HashtableMap<String, Integer> map = new HashtableMap<>();
    map.put("firstDay", 20230920);
    Assertions.assertEquals(1, map.getSize());
  }

  @Test
  public void testGet(){
    HashtableMap<String, Integer> map = new HashtableMap<>();
    map.put("firstDay", 20230920);
    Assertions.assertEquals(20230920, map.get("firstDay"));
  }

  @Test
  public void testContains(){
    HashtableMap<String, Integer> map = new HashtableMap<>();
    map.put("firstDay", 20230920);
    Assertions.assertTrue(map.containsKey("firstDay"));
    Assertions.assertFalse(map.containsKey("endDay"));
  }

  @Test
  public void testRemove(){
    HashtableMap<String, Integer> map = new HashtableMap<>();
    map.put("firstDay", 20230920);
    map.put("endDay", 0);
    Assertions.assertEquals(0, map.remove("endDay"));
    Assertions.assertEquals(1, map.getSize());
    Assertions.assertFalse(map.containsKey("endDay"));
  }

}
