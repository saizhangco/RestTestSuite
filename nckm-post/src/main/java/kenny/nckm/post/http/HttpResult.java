package kenny.nckm.post.http;

public class HttpResult {

	private int statusCode;
	private String entityContent;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getEntityContent() {
		return entityContent;
	}

	public void setEntityContent(String entityContent) {
		this.entityContent = entityContent;
	}
}
