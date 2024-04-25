package com..test.clients;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTP;
import java.io.BufferedInputStream;
/*
ftp.hostname={hostname}
ftp.username={username}
ftp.password={password}
ftp.remoteFile.path={remoteFilePathName}
ftp.localDirFile.path={localDirFilePathName}
 */
//https://commons.apache.org/proper/commons-net/apidocs/org/apache/commons/net/ftp/FTPClient.html
public class ftpClient {
    FTPClient ftpClient = new FTPClient();
    String dataDir = System.getProperty("user.dir") + "/src/main/resources/data/UI/Batch/";
    String server = System.getProperty("application.server.ip");
    public void connect_login() throws Throwable{

        int port = 21;
        String user = "FtpUser";

        try{
            ftpClient.connect(server, port);
            ftpClient.login(user, System.getProperty("ftp.password"));
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        }catch(IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void logout_disconnect() throws Throwable{
        try {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void uploadFile( String localFileName, String remoteFileName) throws Throwable{
        connect_login();
        File localFile = new File(dataDir + localFileName);
        String remoteFile = remoteFileName;
        InputStream inputStream = new FileInputStream(localFile);
        boolean done = ftpClient.storeFile(remoteFile, inputStream);
        System.out.println(ftpClient.getReplyCode());
        inputStream.close();
        if (!done) {
            System.out.println("The file failed to upload.");
        }
        logout_disconnect();
    }

    public void downloadFile( String remoteFileName, String localFileName) throws Throwable{
        connect_login();
        FileOutputStream fos = new FileOutputStream(localFileName);
        boolean done = ftpClient.retrieveFile(remoteFileName, fos);
        System.out.println(ftpClient.getReplyCode());
        if (!done) {
            System.out.println("The file failed to download.");
        }
        logout_disconnect();
    }


    public List<String> listAllFiles(String remoteFileDirectory) throws Throwable{
        connect_login();
        FTPFile[] files = ftpClient.listFiles(remoteFileDirectory);
        System.out.println(ftpClient.getReplyCode());
        List<String> holder = new ArrayList<String>();
        for (FTPFile file : files) {
            holder.add(file.getName());
        }
        logout_disconnect();
        return holder;
    }

    public boolean findFile(String remoteFileDirectory, String remoteFileName) throws Throwable{
        connect_login();
        FTPFile[] files = ftpClient.listFiles(remoteFileDirectory);
        System.out.println(ftpClient.getReplyCode());
        boolean found_flag = false;
        for(FTPFile file : files){
            if(file.getName().equals(remoteFileName)){
                System.out.println("File found: " + file.getName());
                found_flag = true;
            }
        }
        logout_disconnect();
        return found_flag;
    }

    public void deleteRemoteFile(String remoteFileName) throws Throwable{
        connect_login();
        if(!ftpClient.deleteFile(remoteFileName)){
            System.out.println(ftpClient.getReplyCode());
            System.out.println("Error during delete file: " + remoteFileName);
        }
        logout_disconnect();
    }

    public void deleteAllFileInRemoteDirectory(String remoteDirectory) throws Throwable{
        connect_login();
        List<String> allFileName = this.listAllFiles(remoteDirectory);
        for(String file: allFileName){
            System.out.println("Going to delete " + remoteDirectory + "\\" + file);
            connect_login();
            if(!ftpClient.deleteFile(remoteDirectory + "\\" + file)){
                System.out.println(ftpClient.getReplyCode());
                System.out.println("Error during delete file: " + remoteDirectory + "\\" + file);
            }
        }
        logout_disconnect();
    }

    public void deleteRemoteDirectory(String remoteDirectory) throws Throwable{
        connect_login();
        if(!ftpClient.removeDirectory(remoteDirectory)){
            System.out.println(ftpClient.getReplyCode());
            System.out.println("Error during delete directory: " + remoteDirectory);
        }
        logout_disconnect();
    }

    public String retrieveFileContent(String remoteFileName) throws Throwable{
        connect_login();
        InputStream keepString = ftpClient.retrieveFileStream(remoteFileName);
        System.out.println(ftpClient.getReplyCode());
        String showString = IOUtils.toString(keepString, "UTF-8");
        keepString.close();
        logout_disconnect();
        return showString;
    }

}