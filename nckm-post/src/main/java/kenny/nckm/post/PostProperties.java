package kenny.nckm.post;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PostProperties {

	@Value("${post.type}")
	private String type;

	@Value("${post.path}")
	private String path;

	@Value("${post.url}")
	private String url;

	@Value("${post.statusCode}")
	private Integer statusCode;

	@Value("${post.entityContent}")
	private String entityContent;

	public String getType() {
		return type;
	}

	public String getPath() {
		return path;
	}

	public String getUrl() {
		return url;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public String getEntityContent() {
		return entityContent;
	}
}
