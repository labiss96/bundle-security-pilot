package com.inspien.pilot.manager;

import java.io.*;

public class FileUtil {

	public static void writeToFile(File f, String s) throws IOException {
		FileOutputStream out = new FileOutputStream(f);
		try {
			out.write(s.getBytes("UTF-8"));
		} finally {
			out.close();
		}
	}

	public static void writeBytesToFile(File f, byte[] b) throws IOException {
		FileOutputStream out = new FileOutputStream(f);
		try {
			out.write(b);
		} finally {
			out.close();
		}
	}

	public static String readFromFile(File f) throws IOException {
		DataInputStream in = new DataInputStream(new FileInputStream(f));
		byte[] buf = new byte[(int) f.length()];
		try {
			in.readFully(buf);
		} finally {
			in.close();
		}
		return new String(buf, "UTF-8");
	}

	public static byte[] readBytesFromFile(File f) throws IOException {
		DataInputStream in = new DataInputStream(new FileInputStream(f));
		byte[] buf = new byte[(int) f.length()];
		try {
			in.readFully(buf);
		} finally {
			in.close();
		}
		return buf;
	}

	public static void copyFolder(File src, File dest) throws IOException {
		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
			}
			String files[] = src.list();
			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				copyFolder(srcFile, destFile);
			}
		} else
			copyFile(src, dest);
	}

	public static void copyFile(File src, File dest) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(src));
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dest));
		byte[] buffer = new byte[4096];
		int length;
		while ((length = in.read(buffer)) > 0) {
			out.write(buffer, 0, length);
		}
		in.close();
		out.close();
	}

	public static void purgeDirectory(File dir) throws IOException {
		for (File file : dir.listFiles()) {
			if (file.isDirectory())
				purgeDirectory(file);
			file.delete();
		}
		dir.delete();
	}

	public static File createTempDirectory() throws IOException {
		final File temp;
		temp = File.createTempFile("temp", Long.toString(System.nanoTime()));
		if (!(temp.delete())) {
			throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
		}
		if (!(temp.mkdir())) {
			throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
		}
		return temp;
	}

	public static String readFromInputStream(InputStream in) throws IOException {
		try {
			byte[] buf = new byte[2048];
			int nread = -1;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			while ((nread = in.read(buf)) != -1)
				out.write(buf, 0, nread);
			return new String(out.toByteArray(), "UTF-8");
		} finally {
			in.close();
		}
	}

	public static byte[] readBytesFromInputStream(InputStream in) throws IOException {
		try {
			byte[] buf = new byte[2048];
			int nread = -1;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			while ((nread = in.read(buf)) != -1)
				out.write(buf, 0, nread);
			return out.toByteArray();
		} finally {
			in.close();
		}
	}

	public static void renameTo(File src, File tgt) throws IOException {
		if (!src.renameTo(tgt)) {
			byte[] buf = new byte[1024];
			try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(src));
					BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(tgt))) {
				int read = 0;
				while ((read = in.read(buf, 0, buf.length)) != -1) {
					out.write(buf, 0, read);
				}
			}
		}
		if (tgt.exists())
			src.delete();
	}
}
