package org.bjzhou.directmsg.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileHelper {

	public static void saveFile(String content, String path) {
		FileOutputStream fos;
		try {
			File file = new File(path);
			fos = new FileOutputStream(file);
			fos.write(content.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void delFile(String path) {
		File file = new File(path);
		if (file.exists())
			file.delete();
	}

	public static String fromFile(String path) {
		try {
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader reader = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(reader);
			String str;
			StringBuffer sb = new StringBuffer();
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
			fis.close();
			reader.close();
			br.close();
			return sb.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static long getFileSize(String path) {
		File file = new File(path);
		return file.length();
	}

	public static void createDir(String dir) {
		File file = new File(dir);
		if (!file.exists())
			file.mkdirs();
	}

	public static String downloadPic(String urlstr, String pathdir) {
		String name = urlstr.split("//")[1].replace("/", "").replace(".", "_");
		File file;
		if (new File(pathdir).exists()) {
			file = new File(pathdir + File.separator + name);

			if (file.exists() && file.length() > 0) {
				return file.getAbsolutePath();
			}
			try {
				URL url = new URL(urlstr);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.connect();
				InputStream is = connection.getInputStream();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				FileOutputStream fos = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					out.write(buffer, 0, len);
				}
				fos.write(out.toByteArray());
				fos.flush();
				fos.close();
				out.close();
				is.close();
				connection.disconnect();

				return file.getAbsolutePath();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} else
			return null;
	}
}
