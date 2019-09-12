package com.estes.myestes.claims.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

import com.edps.config.J2CToken;
import com.edps.config.J2CUtils;
import com.edps.ftp.FTPCredentials;
import com.estes.framework.config.ESTESConfigUtil;
import com.estes.framework.logger.ESTESLogger;

/**
 * @see http://stackoverflow.com/questions/3701073/how-can-a-socket-be-both-connected-and-closed
 * @see http://www.odi.ch/prog/design/newbies.php
 */
public class Web4FTPConnection {
	private Socket socket;
	private BufferedReader replyReader;
	private PrintWriter commandWriter;
	
	private static final String ERROR_550 = 
		"550-Specified directory does not exist or cannot be accessed";

	public Web4FTPConnection() {
		
	}
	
	public void sendFile(String directory, String fileName, InputStream inputStream)
	throws IOException {
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		Socket dataConnection = null;
		
		try {
			connect();
	
			if(sendCommand("CWD " + directory).contains(ERROR_550)) {
	 			sendCommand("MKD " + directory);
				sendCommand("CWD " + directory);
	 		}	
			
			dataConnection = getDataConnection();
			sendCommand("STOR " + fileName);
			
			in = new BufferedInputStream(inputStream);
			out = new BufferedOutputStream(dataConnection.getOutputStream());
	
			byte[] bytesIn = new byte[1024];
			int i = 0;
			
			while((i = in.read(bytesIn)) >= 0) {
				out.write(bytesIn, 0, i);
			}
		} finally {
			if(in != null) { in.close(); }
			
			if(out != null) { out.close(); }
		
			if(dataConnection != null) {
				if(!dataConnection.isClosed()) {
					dataConnection.close();
				}
			}
			
			disconnect();
		}
	}
	
    public void renameFile(String fromDirectory, String fromFileName, 
    String toDirectory, String toFileName) throws IOException {
    	try {
    		connect();
    	
    		if(sendCommand("CWD " + toDirectory).contains(ERROR_550)) {
     			sendCommand("MKD " + toDirectory);
    			sendCommand("CWD " + toDirectory);
     		}
                
			sendCommand("RNFR " + fromDirectory + fromFileName);
			sendCommand("RNTO " + toDirectory + toFileName);
    	} finally {
            disconnect();
    	}
    }
	
	public void deleteFile(String directory, String fileName) throws IOException {
		try {
			connect();
			sendCommand("CWD " + directory);
			sendCommand("DELE " + fileName);
		} finally {
			disconnect();
		}
	}
	
	private void connect() throws IOException {
		socket = new Socket(getFtpCredentials().getHostName(), getFtpCredentials().getPort());
		replyReader = new BufferedReader(
			new InputStreamReader(socket.getInputStream()));
		commandWriter = new PrintWriter(socket.getOutputStream(), true);
		readReply();
		login();
	}

	private Socket getDataConnection() throws IOException {
		int p = extractPortAddress(sendCommand("PASV"));
		Socket dataConnection = new Socket(getFtpCredentials().getHostName(), p);
		return dataConnection;
	}

	private int extractPortAddress(String s) throws IOException {
		StringTokenizer tokens = new StringTokenizer(s, ",");

		if(tokens.countTokens() < 6) {
			throw new IOException(
				"Unable to extract port address from PASV response: " + s);
		}

		for(int i = 0; i < 4; i ++) {
			tokens.nextToken();
		}
		
		int highOrderPort = Integer.parseInt(tokens.nextToken());
		int lowOrderPort;
		String lowOrder = tokens.nextToken();
		
		if(lowOrder.indexOf(")") != -1) {
			lowOrderPort = Integer.parseInt(lowOrder.substring(0,
				lowOrder.indexOf(")")));
		} else {
			lowOrderPort = Integer.parseInt(lowOrder);
		}

		return (highOrderPort * 256) + lowOrderPort;
	}

	private String sendCommand(String command) throws IOException {
		commandWriter.print(command + "\r\n");
		commandWriter.flush();
		
		ESTESLogger.log(ESTESLogger.DEBUG, Web4FTPConnection.class, "sendCommand", "FTP Command: " + command);
		return readReply();
	}

	private String readReply() throws IOException {
		String currentLine = replyReader.readLine();
		String code = currentLine.substring(0, 3);
		StringBuilder sb = new StringBuilder(currentLine);
		boolean reading = true;

		while(reading) {
			if((currentLine.length() > 3)
					&& (currentLine.substring(0, 3).equals(code))
					&& (currentLine.charAt(3) == ' ')) {
				reading = false;
			} else {
				currentLine = replyReader.readLine();
				sb.append("\n" + currentLine);
			}
		}
			
		ESTESLogger.log(ESTESLogger.DEBUG, Web4FTPConnection.class, "readReply", "FTP Reply: " + sb.toString());
		return sb.toString();
	}

	private void login() throws IOException {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String user = getFtpCredentials().getUsername();
		String pass = getFtpCredentials().getPassword();
		String status1 = sendCommand("USER " + user);
		String status = sendCommand("PASS " + pass);
		
		if(!status.startsWith("230")) {
			ESTESLogger.log(ESTESLogger.ERROR, Web4FTPConnection.class, "login", "FTP login failed.");
			throw new IOException("FTP login failed.");
		}
	}
	
	private void disconnect() throws IOException {
		if(socket != null) {
			//This method does not answer whether the Socket is currently
			//connected - it answers whether the Socket was EVER connected.
			if(socket.isConnected()) { //should be called didConnect()
				if(commandWriter != null) {
					sendCommand("QUIT");
				}
				
				socket.close();
				socket = null;
			}
		}
		
		if(commandWriter != null) {
			commandWriter.close();
			commandWriter = null;
		}
		
		if(replyReader != null) {
			replyReader.close();
			replyReader = null;
		}
	}

	private FTPCredentials getFtpCredentials() {
		FTPCredentials tFtpCredentials = new FTPCredentials();
		tFtpCredentials.setHostName(ESTESConfigUtil.getProperty(ClaimsConstant.APP_EXT_PROP_FTP_HOST, ClaimsConstant.APP_EXT_PROP_FTP_HOST));
		String ftpJ2CName = ESTESConfigUtil.getProperty(ClaimsConstant.APP_EXT_PROP_FTP_ALIAS, ClaimsConstant.APP_EXT_PROP_FTP_ALIAS);
		J2CToken j2cToken = J2CUtils.getJ2CAuthToken(ftpJ2CName);
		if (j2cToken != null) {
			tFtpCredentials.setUsername(j2cToken.getUsername());
			tFtpCredentials.setPassword(j2cToken.getPassword());
		}
		String ftpPort = ESTESConfigUtil.getProperty(ClaimsConstant.APP_EXT_PROP_FTP_PORT, ClaimsConstant.APP_EXT_PROP_FTP_PORT);
		if (ftpPort != null && !ftpPort.trim().equalsIgnoreCase("")) {
			tFtpCredentials.setPort(Integer.parseInt(ftpPort));
		}
		
		return tFtpCredentials;
	}
	
}