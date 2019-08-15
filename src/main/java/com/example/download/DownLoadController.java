package com.example.download;

import org.apache.juli.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
public class DownLoadController {


	@GetMapping("/download")
	public void getUserGuide(HttpServletResponse res) {
		String fileName = "用户手册.docx";
		res.setContentType("application/octet-stream");
		try {
			res.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "iso-8859-1"));
		} catch (UnsupportedEncodingException e) {
			//e.printStackTrace();
			res.setHeader("Content-Disposition", "attachment; filename=" + "UserGuide");
		}

		byte[] buff = new byte[1024];
		BufferedInputStream bis = null;
		OutputStream os = null;

		try {
			os = res.getOutputStream();
			ClassPathResource classPathResource = new ClassPathResource("userGuide.docx");
			res.setContentLength((int) classPathResource.contentLength());
			InputStream is = classPathResource.getInputStream();

			bis = new BufferedInputStream(is);
			int i = bis.read(buff);

			while (i != -1) {
				os.write(buff, 0, buff.length);
				os.flush();
				i = bis.read(buff);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("export file finish");
	}

}
