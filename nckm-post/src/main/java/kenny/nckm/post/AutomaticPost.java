package kenny.nckm.post;

public interface AutomaticPost {

	boolean post(String path, String url);

	boolean postTable(String path, String url, Integer statusCode);

	boolean postArray(String path, String url, Integer statusCode, String entityContent);

	boolean postRelationTable(String path, String url, Integer statusCode);

	boolean postRelationArray(String path, String url, Integer statusCode, String entityContent);
}
