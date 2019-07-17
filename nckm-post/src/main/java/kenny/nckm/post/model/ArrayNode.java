package kenny.nckm.post.model;

import java.util.ArrayList;
import java.util.List;

public class ArrayNode extends Node {

	private List<Node> list = new ArrayList<Node>();

	public ArrayNode() {
		setType(NodeType.Array);
	}

	public ArrayNode(String[] ss, NodeType type) {
		for (String s : ss) {
			if (type == NodeType.Number) {
				list.add(new NumberNode(s));
			} else if (type == NodeType.String) {
				list.add(new StringNode(s));
			}
		}
	}

	public List<Node> getList() {
		return list;
	}

	public void setList(List<Node> list) {
		this.list = list;
	}

	@Override
	public ArrayNode clone() {
		ArrayNode copy = new ArrayNode();
		for (Node node : list) {
			copy.getList().add(node.clone());
		}
		return copy;
	}

	@Override
	public String toJson() {
		StringBuilder json = new StringBuilder();
		json.append("[ ");
		boolean isFirst = true;
		for (Node node : list) {
			if (isFirst) {
				isFirst = false;
				json.append(node.toJson());
			} else {
				json.append(" , ").append(node.toJson());
			}
		}
		json.append(" ]");
		return json.toString();
	}

	@Override
	public String toJson(String header) {
		return new StringBuilder().append("\"").append(header).append("\" : ").append(toJson()).toString();
	}

	@Override
	public String getValue() throws Exception {
		throw new Exception("ArrayNode not support getValue()");
	}
}
