class Node {
	private Object item;
	private Node next;

	private boolean debug = false;

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public Node(Object item){
		this.item = item;
		this.next = null;
	}

	public Node(Object item, Object next){
		this.item = item;
		this.item = next;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	public void toggleDebug(){
		if (debug){
			System.out.println("Node: Debug-Mode Turned off.");
			debug = false;

			return;
		}

		System.out.println("Node: Debug-Mode Turned on.");
		debug = true;

		return;
	}

	public void itemClass(){	//remove later
		System.out.println(item.getClass());
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public Object item(){
		return item;
	}

	public Node next(){
		return next;
	}

	public String toString(){
		return item.toString();
	}

}

class Set {
	private Node head;
	private Node tail;

	private boolean debug = false;

	public Object setType;		//remove later

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public Set(){
		this.head = null;
		this.tail = null;
	}

	public Set(Node head){
		this.head = head;

		while(head.next() != null){
			head = head.next();
		}

		this.tail = head.next();
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public void checkClass(){
		System.out.println(setType.getClass());
	}

	public void checkNodeClass(){
		head.itemClass();
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public boolean insert(Object item){
		return true;
	}
}


class Node<T> {
	private T item;
	private Node<T> next;

	

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public <T> Node(T item){
		this.item = item;
		this.next = null;
	}

	public <T> Node(T item, T next){
		this.item = item;
		this.item = next;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public void checkClass(){	//remove later
		System.out.println(item.getClass());
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public T item(){
		return item;
	}

	public Node<T> next(){
		return next;
	}

	public String toString(){
		return item.toString();
	}

}

class Set<T> {
	private Node<T> head;
	private Node<T> tail;

	public T setType;		//remove later

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public Set(){
		this.head = null;
		this.tail = null;
	}

	public Set(Node<T> head){
		this.head = head;

		while(head.next() != null){
			head = head.next();
		}

		this.tail = head.next();
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public void checkClass(){
		System.out.println(setType.getClass());
	}

	public void checkNodeClass(){
		head.checkClass();
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

	public boolean insert(T item){
		return true;
	}
}
