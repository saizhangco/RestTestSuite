package kenny.nckm.post.model;

import java.util.HashMap;
import java.util.Map;

public class ObjectNode extends Node {

	private Map<String, Node> object = new HashMap<String, Node>();

	public Map<String, Node> getObject() {
		return object;
	}

	public void setObject(Map<String, Node> object) {
		this.object = object;
	}
	
	public ObjectNode() {
		setType(NodeType.Object);
	}
	
	@Override
	public ObjectNode clone() {
		ObjectNode copy = new ObjectNode();
		Map<String, Node> objectCopy = new HashMap<String, Node>();
		objectCopy.clear();
		for( Map.Entry<String, Node> entry : object.entrySet() ) {
			String key = entry.getKey();
			Node value = entry.getValue().clone();
			objectCopy.put(key, value);
		}
		copy.setObject(objectCopy);
		return copy;
	}

	@Override
	public String toJson() {
		StringBuilder json = new StringBuilder();
		json.append("{ ");
		boolean isFirst = true;
		for (Map.Entry<String, Node> entry : object.entrySet()) {
			String header = entry.getKey();
			Node node = entry.getValue();
			if (isFirst) {
				json.append(node.toJson(header));
				isFirst = false;
			} else {
				json.append(" , ").append(node.toJson(header));
			}
		}
		json.append(" }");
		return json.toString();
	}

	@Override
	public String toJson(String header) {
		StringBuilder json = new StringBuilder();
		json.append("\"").append(header).append("\" : ").append(toJson());
		return json.toString();
	}

	@Override
	public String getValue() throws Exception {
		throw new Exception("ObjectNode not support getValue()");
	}
}
