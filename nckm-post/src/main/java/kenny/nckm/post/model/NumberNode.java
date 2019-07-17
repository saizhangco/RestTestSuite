package kenny.nckm.post.model;

public class NumberNode extends Node {

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public NumberNode(String value) {
		setType(NodeType.Number);
		this.value = value;
	}

	@Override
	public NumberNode clone() {
		return new NumberNode(value);
	}

	@Override
	public String toJson() {
		return new StringBuilder().append(value).toString();
	}

	@Override
	public String toJson(String header) {
		return new StringBuilder().append("\"").append(header).append("\" : ").append(toJson()).toString();
	}
}
