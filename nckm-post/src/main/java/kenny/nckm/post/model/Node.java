package kenny.nckm.post.model;

public abstract class Node {
	private NodeType type;

	public NodeType getType() {
		return type;
	}

	public void setType(NodeType type) {
		this.type = type;
	}

	@Override
	public abstract Node clone();
	
	public abstract String toJson();
	public abstract String toJson(String header);
	public abstract String getValue() throws Exception;
}
