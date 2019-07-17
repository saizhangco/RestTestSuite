package kenny.nckm.post.model;

import java.util.HashMap;
import java.util.Map;

public class Line {

	private Map<Integer, String> lines;

	public Line() {
		lines = new HashMap<Integer, String>();
	}

	public void addLine(Integer no, String txt) {
		lines.put(no, txt);
	}

	// null
	public String getLine(Integer no) {
		return lines.get(no);
	}

	public void clear() {
		lines.clear();
	}

	public Integer length() {
		return lines.size() - 1;
	}
}
