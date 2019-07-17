package kenny.nckm.post.csv;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import kenny.nckm.post.AutomaticPost;
import kenny.nckm.post.http.HttpCommon;
import kenny.nckm.post.http.HttpResult;
import kenny.nckm.post.model.ArrayNode;
import kenny.nckm.post.model.Line;
import kenny.nckm.post.model.Node;
import kenny.nckm.post.model.NodeType;
import kenny.nckm.post.model.NumberNode;
import kenny.nckm.post.model.ObjectNode;
import kenny.nckm.post.model.StringNode;

@Component
public class AutomaticPostByCSV implements AutomaticPost {

	private Line lines = new Line();
	private Line linesB = new Line();
	private Line linesR = new Line();
	private Line linesS = new Line();
	// private int headerLength;
	private ObjectNode headerTemplate = new ObjectNode();
	private Map<String, ObjectNode> sublistHashMap = new HashMap<String, ObjectNode>();
	private Map<String, ArrayNode> relationHashMap = new HashMap<String, ArrayNode>();

	@Override
	public boolean post(String path, String url) {
		loadFileContent(path + ".csv", lines);
		for (int i = 1; i <= lines.length(); i++) {
			// 1. parse header
			// parseHeader();
			// 2. validate all lines
			// 3. batch processing per line
			batchProcessing(lines.getLine(0), lines.getLine(i));
			String json = headerTemplate.toJson();
			int statusCode = HttpCommon.post(url, json);
			if (statusCode != 200)
				System.out.println(json);
			System.out.println(i + "\t" + statusCode);
		}
		return false;
	}

	@Override
	public boolean postTable(String path, String url, Integer expectedStatusCode) {
		lines.clear(); // clear lines
		loadFileContent(path + ".csv", lines);
		// 1. parse header
		parseHeader();
		// 2. validate all lines
		// 3. batch processing per line
		for (int i = 1; i <= lines.length(); i++) {
			ObjectNode on = parseLine(lines.getLine(0), lines.getLine(i));
			String json = on.toJson();
			int statusCode = HttpCommon.post(url, json);
			if (statusCode != expectedStatusCode)
				System.out.println(json);
			System.out.println(i + "\t" + statusCode);
		}
		return true;
	}

	@Override
	public boolean postArray(String path, String url, Integer expectedStatusCode, String expectedEntityContnet) {
		lines.clear(); // clear lines
		loadFileContent(path + ".csv", lines);
		ArrayNode arrayNode = new ArrayNode();
		for (int i = 1; i <= lines.length(); i++) {
			ObjectNode on = parseLine(lines.getLine(0), lines.getLine(i));
			arrayNode.getList().add(on);
		}
		String json = arrayNode.toJson();
		HttpResult httpResult = HttpCommon.post1(url, json);
		int statusCode = httpResult.getStatusCode();
		String entityContent = httpResult.getEntityContent();
		System.out.println("" + lines.length() + "\t" + statusCode + "\t" + entityContent);
		if (statusCode != expectedStatusCode || entityContent.equals(entityContent))
			System.out.println(json);
		return true;
	}

	@Override
	public boolean postRelationTable(String path, String url, Integer expectedStatusCode) {
		linesB.clear();
		linesR.clear();
		linesS.clear();
		loadFileContent(path + "-B.csv", linesB);
		loadFileContent(path + "-R.csv", linesR);
		loadFileContent(path + "-S.csv", linesS);
		// step 1 : parse sublist
		// step 2 : parse relation
		// step 3 : parse lines
		parseSublist("PhrOrderIdse");
		parseRelation("ChartNo", "PhrOrderIdse");
		for (int i = 1; i <= linesB.length(); i++) {
			ObjectNode on = parseLine(linesB.getLine(0), linesB.getLine(i));
			String json = on.toJson();
			int statusCode = HttpCommon.post(url, json);
			if (statusCode != 200)
				System.out.println(json);
			System.out.println(i + "\t" + statusCode);
		}
		return true;
	}

	@Override
	public boolean postRelationArray(String path, String url, Integer expectedStatusCode,
			String expectedEntityContent) {
		linesB.clear();
		linesR.clear();
		linesS.clear();
		loadFileContent(path + "-B.csv", linesB);
		loadFileContent(path + "-R.csv", linesR);
		loadFileContent(path + "-S.csv", linesS);
		// step 1 : parse sublist
		// step 2 : parse relation
		// step 3 : parse lines
		parseSublist("PhrOrderIdse");
		parseRelation("ChartNo", "PhrOrderIdse");
		ArrayNode arrayNode = new ArrayNode();
		for (int i = 1; i <= linesB.length(); i++) {
			ObjectNode on = parseLine(linesB.getLine(0), linesB.getLine(i));
			arrayNode.getList().add(on);
		}
		String json = arrayNode.toJson();
		HttpResult httpResult = HttpCommon.post1(url, json);
		int statusCode = httpResult.getStatusCode();
		String entityContent = httpResult.getEntityContent();
		System.out.println("" + lines.length() + "\t" + statusCode + "\t" + entityContent);
		if (statusCode != expectedStatusCode || entityContent.equals(entityContent))
			System.out.println(json);
		return true;
	}

	private void parseRelation(String baseKeyName, String subKeyName) {
		relationHashMap.clear();
		if (linesR.length() <= 0) {
			return;
		}
		for (int i = 1; i <= linesR.length(); i++) {
			ObjectNode on = parseLine(linesR.getLine(0), linesR.getLine(i));
			if (on.getObject().containsKey(baseKeyName) && on.getObject().containsKey(subKeyName)) {
				Node baseColumn = on.getObject().get(baseKeyName);
				Node subColumn = on.getObject().get(subKeyName);
				try {
					String baseColumnValue = baseColumn.getValue();
					String subColumnValue = subColumn.getValue();
					if (sublistHashMap.containsKey(subColumnValue)) {
						ObjectNode subColumnNode = sublistHashMap.get(subColumnValue);
						if (relationHashMap.containsKey(baseColumnValue)) {
							relationHashMap.get(baseColumnValue).getList().add(subColumnNode);
						} else {
							ArrayNode relationArrayNode = new ArrayNode();
							relationArrayNode.getList().add(subColumnNode);
							relationHashMap.put(baseColumnValue, relationArrayNode);
						}
					} else {
						System.out.println("Sublist not contains the object " + subColumnValue);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("ObjectNode does not contains specific baseTable key name or sublist key name");
				System.out.println("[" + on.toJson() + "]");
			}
		}
	}

	private void parseSublist(String primaryKeyName) {
		sublistHashMap.clear();
		if (linesS.length() <= 0) {
			return;
		}
		for (int i = 1; i <= linesS.length(); i++) {
			ObjectNode on = parseLine(linesS.getLine(0), linesS.getLine(i));
			if (on.getObject().containsKey(primaryKeyName)) {
				Node primaryColumn = on.getObject().get(primaryKeyName);
				String key = "";
				if (primaryColumn.getType() == NodeType.Number) {
					key = ((NumberNode) primaryColumn).getValue();
				} else if (primaryColumn.getType() == NodeType.String) {
					key = ((StringNode) primaryColumn).getValue();
				}
				if ("".equals(key)) {
					System.out.println("ObjectNode contains specific primary key, but it's blank string.");
					System.out.println("[" + on.toJson() + "]");
					continue;
				}
//				if( sublistHashMap.containsKey(key)) {
//					sublistHashMap.get(key).getList().add(value);
//				} else {
//					ArrayNode an = new ArrayNode();
//					an.getList().add(value);
//					sublistHashMap.put(key, an);
//				}
				sublistHashMap.put(key, on);
			} else {
				System.out.println("ObjectNode does not contains specific primary key");
				System.out.println("[" + on.toJson() + "]");
			}
		}
	}

	public void testCopy(String path) {
		loadFileContent(path + ".csv", lines);
		batchProcessing(lines.getLine(0), lines.getLine(1));
		ObjectNode copyOn = headerTemplate.clone();
		for (Map.Entry<String, Node> entry : copyOn.getObject().entrySet()) {
			Node node = entry.getValue();
			if (node.getType() == NodeType.String) {
				StringNode sn = (StringNode) node;
				sn.setValue(sn.getValue() + "-A");
			}
		}
		System.out.println(copyOn.toJson());
		System.out.println(headerTemplate.toJson());
	}

//	private String getNodeString(String header, Node node) {
//		StringBuilder sb = new StringBuilder();
//		if (node.getType() == NodeType.Number) {
//			sb.append("\"").append(header).append("\" : ").append(((NumberNode) node).getValue());
//		} else if (node.getType() == NodeType.String) {
//			sb.append("\"").append(header).append("\" : ").append("\"").append(((StringNode) node).getValue())
//					.append("\"");
//		} else if (node.getType() == NodeType.Object) {
//			sb.append("\"").append(header).append("\" : { ");
//			boolean isFirst = true;
//			for (Map.Entry<String, Node> entry : ((ObjectNode) node).getObject().entrySet()) {
//				String h = entry.getKey();
//				Node n = entry.getValue();
//				if (isFirst) {
//					sb.append(getNodeString(h, n));
//					isFirst = false;
//				} else {
//					sb.append(" , ").append(getNodeString(h, n));
//				}
//			}
//			sb.append(" } ");
//		}
//		return sb.toString();
//	}

	private void batchProcessing(String header, String line) {
		headerTemplate.getObject().clear();
		String[] headers = header.split(",");
		String[] lines = line.split(",");
		if (!(headers.length == lines.length && headers.length > 0)) {
			System.out.println("batch processing error : " + line);
			return;
		}
		int length = headers.length;
		for (int i = 0; i < length; i++) {
			String h = headers[i];
			String l = lines[i];
			if (h.endsWith(":n")) {
				h = h.substring(0, h.length() - 2);
				parseItem(h, true, l);
			} else if (h.endsWith(":s")) {
				h = h.substring(0, h.length() - 2);
				parseItem(h, false, l);
			} else {
				parseItem(h, false, l);
			}
		}
	}

	private ObjectNode parseLine(String header, String line) {
		ObjectNode on = new ObjectNode();
		String[] headers = header.split(",");
		String[] lines = line.split(",");
		if (!(headers.length == lines.length && headers.length > 0)) {
			System.out.println("batch processing error : " + line);
			return null;
		}
		int length = headers.length;
		for (int i = 0; i < length; i++) {
			String h = headers[i];
			String l = lines[i];
			if (h.endsWith(":n")) {
				h = h.substring(0, h.length() - 2);
				parseItem(h, true, l, on);
			} else if (h.endsWith(":s")) {
				h = h.substring(0, h.length() - 2);
				parseItem(h, false, l, on);
			} else if (h.endsWith(":an")) {
				h = h.substring(0, h.length() - 3);
				parseItemArray(h, NodeType.Number, l, on);
			} else if (h.endsWith(":as")) {
				h = h.substring(0, h.length() - 3);
				parseItemArray(h, NodeType.String, l, on);
			} else if (h.endsWith(":ao")) {
				h = h.substring(0, h.length() - 3);
				parseItemArray(h, NodeType.Object, l, on);
			} else {
				parseItem(h, false, l, on);
			}
		}
		return on;
	}

	private void parseItemArray(String header, NodeType type, String value, ObjectNode objectNode) {
		if (type == NodeType.Object) {
			if (relationHashMap.containsKey(value)) {
				objectNode.getObject().put(header, relationHashMap.get(value));
			}
		} else if (type == NodeType.Number) {
			objectNode.getObject().put(header, new ArrayNode(value.split("#"), NodeType.Number));
		} else if (type == NodeType.String) {
			objectNode.getObject().put(header, new ArrayNode(value.split("#"), NodeType.String));
		}
	}

	private void parseItem(String header, boolean isNumber, String value, ObjectNode objectNode) {
		if (header.contains("-")) {
			String[] properties = header.split("-");
			ObjectNode on = objectNode;
			for (int i = 0; i < properties.length - 1; i++) {
				if (on.getObject().containsKey(properties[i])) {
					on = (ObjectNode) on.getObject().get(properties[i]);
				} else {
					ObjectNode non = new ObjectNode();
					on.getObject().put(properties[i], non);
					on = non;
				}
			}
			if (isNumber) {
				on.getObject().put(properties[properties.length - 1], new NumberNode(value));
			} else {
				on.getObject().put(properties[properties.length - 1], new StringNode(value));
			}
		} else {
			if (isNumber) {
				objectNode.getObject().put(header, new NumberNode(value));
			} else {
				objectNode.getObject().put(header, new StringNode(value));
			}
		}
	}

	private void parseHeader() {
		headerTemplate.getObject().clear();
		String header = lines.getLine(0);
		if (header == null) {
			System.out.println("header parse error 1");
			return;
		}
		String[] items = header.split(",");
		if (items.length <= 0) {
			System.out.println("header parse error 2");
			return;
		}
		for (String item : items) {
			if (item.endsWith(":n")) {
				item = item.substring(0, item.length() - 2);
				parseItem(item, true, null);
			} else if (item.endsWith(":s")) {
				item = item.substring(0, item.length() - 2);
				parseItem(item, false, null);
			} else {
				parseItem(item, false, null);
			}
		}
	}

	private void parseItem(String header, boolean isNumber, String value) {
		if (header.contains("-")) {
			String[] properties = header.split("-");
			ObjectNode on = headerTemplate;
			for (int i = 0; i < properties.length - 1; i++) {
				if (on.getObject().containsKey(properties[i])) {
					on = (ObjectNode) on.getObject().get(properties[i]);
				} else {
					ObjectNode non = new ObjectNode();
					on.getObject().put(properties[i], non);
					on = non;
				}
			}
			if (isNumber) {
				on.getObject().put(properties[properties.length - 1], new NumberNode(value));
			} else {
				on.getObject().put(properties[properties.length - 1], new StringNode(value));
			}
		} else {
			if (isNumber) {
				headerTemplate.getObject().put(header, new NumberNode(value));
			} else {
				headerTemplate.getObject().put(header, new StringNode(value));
			}
		}
	}

	// for single thread
	// UTF-8
	private void loadFileContent(String path, Line lines) {
		BufferedReader in = null;
		lines.clear();
		int count = 0;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
			String line = null;
			while ((line = in.readLine()) != null) {
				lines.addLine(count++, line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
