package kenny.nckm.post.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpCommon {

	public static int post(String url, String json) {
		int statusCode = -1;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/json");
		StringEntity paraEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
		httpPost.setEntity(paraEntity);
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpPost);
			statusCode = response.getStatusLine().getStatusCode();

		} catch (ParseException | IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return statusCode;
	}

	public static HttpResult post1(String url, String json) {
		HttpResult result = new HttpResult();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/json");
		StringEntity paraEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
		httpPost.setEntity(paraEntity);
		CloseableHttpResponse response = null;

		try {
			response = httpclient.execute(httpPost);
			result.setStatusCode(response.getStatusLine().getStatusCode());
			result.setEntityContent(readResponse(response.getEntity().getContent()));
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public static void main(String[] args) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://127.0.0.1:8080/adc/nckm/patientsinhospital");
		httpGet.setHeader("Content-Type", "application/json;charset=utf-8");
		try {
			CloseableHttpResponse response = httpclient.execute(httpGet);
			String responseContent = null; // 响应内容
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				responseContent = EntityUtils.toString(entity, "UTF-8");
				System.out.println(responseContent);
			}
			response.close();
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String readResponse(InputStream in) throws IOException {
		StringBuilder content = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line = null;
		while ((line = reader.readLine()) != null) {
			content.append(line);
		}
		return content.toString();
	}
}
