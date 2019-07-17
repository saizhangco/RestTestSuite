package kenny.nckm.post.test;

import kenny.nckm.post.csv.AutomaticPostByCSV;

public class TestNodeCopy {

	public static void main(String[] args) {
		String path = "C:\\nckm\\csv\\admission-data-test.csv";
		AutomaticPostByCSV ap = new AutomaticPostByCSV();
		ap.testCopy(path);
	}
}
