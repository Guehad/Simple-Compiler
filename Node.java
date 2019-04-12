import java.util.ArrayList;

public class Node {
	
	
	private int parentIndex;
	private int token;
	private String value;
	private ArrayList<Integer> children ;
	private Boolean visted;
	
	
	public Node(int parentIndex, int token, String value) {
		super();
		this.parentIndex = parentIndex;
		this.token = token;
		this.value = value;
		this.children = new ArrayList<>();
		this.visted = false;
	}
	
	
	
	public Boolean getVisted() {
		return visted;
	}



	public void setVisted(Boolean visted) {
		this.visted = visted;
	}



	public void addChild(int childIndex) {
		this.children.add(childIndex);
	}
	
	
	public ArrayList<Integer> getChildren() {
		return children;
	}


	public void setChildren(ArrayList<Integer> children) {
		this.children = children;
	}


	public int getParentIndex() {
		return parentIndex;
	}
	public void setParentIndex(int parentIndex) {
		this.parentIndex = parentIndex;
	}
	public int getToken() {
		return token;
	}
	public void setToken(int token) {
		this.token = token;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	

}
