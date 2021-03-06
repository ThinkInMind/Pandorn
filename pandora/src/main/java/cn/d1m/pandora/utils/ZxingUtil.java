package cn.d1m.pandora.utils;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class ZxingUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ZxingUtil.class);

	public static String generate(String url, int width, int height, String api_url) {
		String filename = "";
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);
			MatrixToImageWriter.writeToStream(bitMatrix, "png", stream);
			byte[] b = stream.toByteArray();
			String base64file = Base64.getUrlEncoder().encodeToString(b);
			LOGGER.info(base64file);
			//上传参数
			Map<String, String> uriVariables = new HashMap<String, String>();
			uriVariables.put("base64file", base64file);
			uriVariables.put("fullname", "zxing.png");
			uriVariables.put("encodetype", "1");
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/x-www-form-urlencoded");
//			HttpEntity<String> request = new HttpEntity<String>(MD5Config.createSignString(uriVariables), headers);
//			LOGGER.info("postData>>" + uriVariables.toString());
//			RestTemplate rest = new RestTemplate();
//			filename = rest.postForObject(api_url, request, String.class, uriVariables);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			try {
				if(stream != null) {
					stream.close();
				}
			} catch (Exception e2) {
				LOGGER.error(e2.getMessage(), e2);
			}
		}
		return filename;
	}
}
