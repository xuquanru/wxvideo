package com.rootbant.wxapp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FFMpegUtils {

	private  String ffmpegEXE;
	
	public FFMpegUtils(String ffmpegEXE) {
		super();
		this.ffmpegEXE = ffmpegEXE;
	}

	public void Merge(String Mp3InputPath, String Mp4InputPathString,String logoPathString,double seconds,String videoOutputPath) throws Exception {
		boolean windows = System.getProperty("os.name").toUpperCase().contains("WINDOWS");
		if(windows){winMerge(Mp3InputPath,Mp4InputPathString,logoPathString,seconds,videoOutputPath);}else{
			LinuxMerge(Mp3InputPath,Mp4InputPathString,logoPathString,seconds,videoOutputPath);
		}
	}

	private  void LinuxMerge(String Mp3InputPath, String Mp4InputPathString,String logoPathString,double seconds,String videoOutputPath) throws Exception {
		List<String> command = new ArrayList<>();
		command.add(ffmpegEXE);

		command.add("-i");
		command.add(Mp3InputPath);
		command.add("-i");
		command.add(Mp4InputPathString);
		command.add("-i");
		command.add(logoPathString);

		command.add("-c:v");
		command.add("libx264");
		command.add("-b:v");
		command.add("4000k");
		command.add("-filter_complex");
		command.add("\"[0:a][1:a]amerge=inputs=2[a];[2:v]scale=50:50[logo];[1:v][logo]overlay=x=0:y=0\"");
		command.add("-map");
		command.add("1:v");
		command.add("-map");
		command.add("\"[a]\"");
		command.add("-t");
		command.add(String.valueOf(seconds));
		command.add(videoOutputPath);

		String joincmd = String.join(" ", command);
		Process exec = Runtime.getRuntime().exec(new String[]{"sh", "-c", joincmd});
		printProcessMsg(exec);
		int i = exec.waitFor();


	}

	private  void winMerge(String Mp3InputPath, String Mp4InputPathString,String logoPathString,double seconds,String videoOutputPath) throws Exception {
//		ffmpeg -i input.mp4 -y output.avi
		//ffmpeg -i bgm.mp3 -i input.mp4 -t 6 -filter_complex amix=inputs=2 output.mp4

	//	ffmpeg -i 2.mp3  -i 1.mp4  -i logo.png  -c:v libx264 -b:v 4000k -c:a copy -filter_complex "[2:v]scale=50:50[logo];[1:v][logo]overlay=x=0:y=0" output.mp4
		List<String> command = new ArrayList<>();
		command.add(ffmpegEXE);

		command.add("-i");
		command.add(Mp3InputPath);
		command.add("-i");
		command.add(Mp4InputPathString);
		command.add("-i");
		command.add(logoPathString);

		command.add("-c:v");
		command.add("libx264");
		command.add("-b:v");
		command.add("4000k");
		command.add("-filter_complex");
		command.add("\"[0:a][1:a]amerge=inputs=2[a];[2:v]scale=50:50[logo];[1:v][logo]overlay=x=0:y=0\"");
		command.add("-map");
		command.add("1:v");
		command.add("-map");
		command.add("\"[a]\"");
		command.add("-t");
		command.add(String.valueOf(seconds));
		command.add(videoOutputPath);

		for (String c : command) {
			System.out.print(c + " ");
		}


		ProcessBuilder builder = new ProcessBuilder(command);
		Process process = builder.start();

		InputStream errorStream = process.getErrorStream();
		InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
		BufferedReader br = new BufferedReader(inputStreamReader);

		String line = "";
		while ( (line = br.readLine()) != null ) {
		}

		if (br != null) {
			br.close();
		}
		if (inputStreamReader != null) {
			inputStreamReader.close();
		}
		if (errorStream != null) {
			errorStream.close();
		}

	}


	public  void cover(String videopath,String outputpath) throws IOException, InterruptedException {
		boolean windows = System.getProperty("os.name").toUpperCase().contains("WINDOWS");
		if(windows){wincover(videopath,outputpath);}else{
			linuxcover(videopath,outputpath);
		}
	}

	private void linuxcover(String videopath,String outputpath) throws IOException, InterruptedException {
		//String
		//exec = Runtime.getRuntime().exec(new String[]{"sh","-c",cmd});
		List<String> command = new ArrayList<>();
		command.add(ffmpegEXE);
		command.add("-ss");
		command.add("00:00:01");
		command.add("-y");
		command.add("-i");
		command.add(videopath);
		command.add("-vframes");
		command.add("1");
		command.add(outputpath);

		String joincmd = String.join(" ", command);
		Process exec = Runtime.getRuntime().exec(new String[]{"sh", "-c", joincmd});
		printProcessMsg(exec);
		int i = exec.waitFor();

	}



	/**
	 * 处理process输出流和错误流，防止进程阻塞，在process.waitFor();前调用
	 * @param exec
	 * @throws IOException
	 */
	private void printProcessMsg(Process exec) throws IOException {
		//防止ffmpeg进程塞满缓存造成死锁
		InputStream error = exec.getErrorStream();
		InputStream is = exec.getInputStream();

		StringBuffer result = new StringBuffer();
		String line = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(error,"GBK"));
			BufferedReader br2 = new BufferedReader(new InputStreamReader(is,"GBK"));

			while((line = br.readLine()) != null){
				result.append(line+"\n");
			}

			result = new StringBuffer();
			line = null;

			while((line = br2.readLine()) != null){
				result.append(line+"\n");
			}
		}catch (IOException e2){
			e2.printStackTrace();
		}finally {
			error.close();
			is.close();
		}

	}



	private   void wincover(String videopath,String outputpath) throws IOException {
		//ffmpeg -ss 00:00:01 -y -i 1.mp4 -vframes 1 new.jpg
		List<String> command = new ArrayList<>();
		command.add(ffmpegEXE);
		command.add("-ss");
		command.add("00:00:01");
		command.add("-y");
		command.add("-i");
		command.add(videopath);
		command.add("-vframes");
		command.add("1");
		command.add(outputpath);
		ProcessBuilder builder = new ProcessBuilder(command);
		Process process = builder.start();

		InputStream errorStream = process.getErrorStream();
		InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
		BufferedReader br = new BufferedReader(inputStreamReader);

		String line = "";
		while ( (line = br.readLine()) != null ) {
		}

		if (br != null) {
			br.close();
		}
		if (inputStreamReader != null) {
			inputStreamReader.close();
		}
		if (errorStream != null) {
			errorStream.close();
		}

	}

	public static void main(String[] args) {
		FFMpegUtils ffmpeg = new FFMpegUtils("D:\\QMDownload\\ffmpeg\\bin\\ffmpeg.exe");
//		try {
//			ffmpeg.Merge("D:\\QMDownload\\ffmpeg\\bin\\2.mp3",
//					"D:\\QMDownload\\ffmpeg\\bin\\1.mp4",
//					"D:/Xpro/logo/logo.png",
//					8,
//					"D:\\QMDownload\\ffmpeg\\bin\\xhello.mp4");
//
//			//ffmpeg.cover("D:\\QMDownload\\ffmpeg\\bin\\3.mp4","D:\\QMDownload\\ffmpeg\\bin\\new.jpg");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}


		List<String> lis = new ArrayList<>();
		lis.add("AAA");
		lis.add("BB");
		lis.add("CC");

		//list<String>转字符串
		System.out.println(String.join(" ", lis));


	}

}
