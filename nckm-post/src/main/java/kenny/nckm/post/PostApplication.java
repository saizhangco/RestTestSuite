package kenny.nckm.post;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PostApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(PostApplication.class, args);
		PostProperties pp = context.getBean(PostProperties.class);
		System.out.println("type=[" + pp.getType() + "]");
		System.out.println("path=[" + pp.getPath() + "]");
		System.out.println("url =[" + pp.getUrl() + "]");
		AutomaticPost automaticPost = context.getBean(AutomaticPost.class);
		if ("T".equals(pp.getType())) {
			automaticPost.postTable(pp.getPath(), pp.getUrl(), pp.getStatusCode());
		} else if ("A".equals(pp.getType())) {
			automaticPost.postArray(pp.getPath(), pp.getUrl(), pp.getStatusCode(), pp.getEntityContent());
		} else if ("RT".equals(pp.getType())) {
			automaticPost.postRelationTable(pp.getPath(), pp.getUrl(), pp.getStatusCode());
		} else if ("RA".equals(pp.getType())) {
			automaticPost.postRelationArray(pp.getPath(), pp.getUrl(), pp.getStatusCode(), pp.getEntityContent());
		} else {
			System.out.println("post.type is not recognized!");
		}
		context.close();
	}
}
