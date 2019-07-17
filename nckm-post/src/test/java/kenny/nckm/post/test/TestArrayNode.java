package kenny.nckm.post.test;

import kenny.nckm.post.AutomaticPost;
import kenny.nckm.post.csv.AutomaticPostByCSV;

public class TestArrayNode {

	public static void main(String[] args) {
		String path = "C:\\nckm\\csv\\admission-data.csv";
		AutomaticPost ap = new AutomaticPostByCSV();
		ap.postArray(path, "", 200, "");
	}
}
