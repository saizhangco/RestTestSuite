package kenny.nckm.post.model;

public class StringNode extends Node {

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public StringNode(String value) {
		setType(NodeType.String);
		this.value = value;
	}

	@Override
	public StringNode clone() {
		return new StringNode(value);
	}

	@Override
	public String toJson() {
		return new StringBuilder().append("\"").append(value).append("\"").toString();
	}

	@Override
	public String toJson(String header) {
		return new StringBuilder().append("\"").append(header).append("\" : ").append(toJson()).toString();
	}
	
}
