
public interface ListADT <T> {

	public int size();
	public boolean isEmpty();

	public boolean contains(T findObject);
	public int indexOf(T findObject);
	public T get(int index);

	public void addLast(T newObject);
	public void addFirst(T newObject);
	public void add(int index, T newObject);

	public T delete(int index);
	
}
