/**
 * 
 */
package com.gxlu.international.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gxlu.interfacePlatform.foundation.ftp.component.FTPException;
import com.gxlu.interfacePlatform.foundation.ftp.component.FTPMessageCollector;
import com.gxlu.interfacePlatform.foundation.ftp.component.FTPServer;

/**
 * @author Clark
 *
 */
public abstract class FtpUpJobTemplate {
  protected FTPServer ftpServer;
  protected List<File> upLoadFiles = new ArrayList<File>();
  protected File tempFilePath;
  private static Log logger = LogFactory.getLog(FtpUpJobTemplate.class);
  
  
  protected FtpUpJobTemplate(){
    tempFilePath = getTempFilePath();
  }
  
  
  protected abstract void generateFiles() throws Exception;
  
  protected abstract FTPServer getFtpServer() throws Exception;
  
  
  
  protected void upLoadFiles() throws Exception{
    ftpServer = getFtpServer();
    FTPMessageCollector listener = new FTPMessageCollector();
    ftpServer.setMessageListener(listener);
    if(upLoadFiles != null && upLoadFiles.size()>0){
      for(File file: upLoadFiles){
        logger.info("上传文件: "+file.getName());
        ftpServer.put(file.getAbsolutePath(),file.getName());
        file.delete();
      }
    }
    ftpServer.quit();
  }
  
  public File getTempFilePath(){
    File tempPath = new File("temp");
    if(!tempPath.exists())
      tempPath.mkdir();
    return tempPath;
  }

  public void execute() throws Exception{
    generateFiles();
    upLoadFiles();
  }
  

  public List<File> getUpLoadFiles() {
    return upLoadFiles;
  }


  public void setUpLoadFiles(List<File> upLoadFiles) {
    this.upLoadFiles = upLoadFiles;
  }
  
  
}
