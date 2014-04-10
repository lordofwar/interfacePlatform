package com.gxlu.interfacePlatform.foundation.sftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public class SFTPServer {
	private static Log log = LogFactory.getLog(SFTPServer.class);

	private ChannelSftp sftpChannel;

	public SFTPServer(ChannelSftp channel) {
		this.sftpChannel = channel;
	}

	/**
	 * Put a local file onto the FTP server. It is placed in the current
	 * directory. If a remote file name is supplied, it is stored as that name
	 * on the server.
	 * 
	 * @param localPath
	 *            path of the local file
	 * @param remoteFile
	 *            name of remote file in current directory.
	 * 
	 */
	public void put(File file, String remoteFileName) throws FileNotFoundException, SftpException {
		if (file.exists()) {
			this.sftpChannel.put(new FileInputStream(file), remoteFileName);
		}
	}

	/**
	 * Get data from the FTP server.
	 * 
	 * @param localPath
	 *            local file to put data in
	 * @param remoteFile
	 *            name of remote file in current directory
	 */
	public void get(String localPath, String remoteFile) throws FileNotFoundException, SftpException {
		File file = new File(localPath);
		sftpChannel.get(remoteFile, new FileOutputStream(file));
	}

	/**
	 * Disconnect with server
	 */
	public void quit() {
		if (this.sftpChannel != null) {
			if (this.sftpChannel.isConnected()) {
				try {
					this.sftpChannel.getSession().disconnect();
				} catch (JSchException e) {
					log.error("sftp sesion closed exception:", e);
				}
				this.sftpChannel.disconnect();
			} else if (this.sftpChannel.isClosed()) {
				log.info("sftpChannel is closed already");
			}
		}

	}

	/**
	 * Delete the specified remote working directory
	 * 
	 * @param dir
	 *            name of remote directory to delete
	 * @throws SftpException
	 */
	public void rmdir(String dir) throws SftpException {
		this.sftpChannel.rmdir(dir);
	}

	/**
	 * Create the specified remote working directory
	 * 
	 * @param dir
	 *            name of remote directory to create
	 * @throws SftpException
	 */
	public void mkdir(String dir) throws SftpException {
		this.sftpChannel.mkdir(dir);
	}

	/**
	 * Change the remote working directory to that supplied
	 * 
	 * @param dir
	 *            name of remote directory to change to
	 * @throws SftpException
	 */
	public void chdir(String dir) throws SftpException {
		this.sftpChannel.cd(dir);
	}

	/**
	 * create Directory
	 * 
	 * @param filepath
	 * @param sftp
	 */
	public void createDir(String filepath) {
		boolean bcreated = false;
		boolean bparent = false;
		File file = new File(filepath);
		String ppath = file.getParent();
		try {
			this.sftpChannel.cd(ppath);
			bparent = true;
		} catch (SftpException e1) {
			bparent = false;
		}
		try {
			if (bparent) {
				try {
					this.sftpChannel.cd(filepath);
					bcreated = true;
				} catch (Exception e) {
					bcreated = false;
				}
				if (!bcreated) {
					this.sftpChannel.mkdir(filepath);
					bcreated = true;
				}
				return;
			} else {
				createDir(ppath);
				this.sftpChannel.cd(ppath);
				this.sftpChannel.mkdir(filepath);
			}
		} catch (SftpException e) {
			System.out.println("mkdir failed :" + filepath);
			e.printStackTrace();
		}

		try {
			this.sftpChannel.cd(filepath);
		} catch (SftpException e) {
			e.printStackTrace();
			System.out.println("can not cd into :" + filepath);
		}

	}

	/**
	 * list all the file or directory in current directory.
	 * 
	 * @return List
	 * @throws SftpException
	 */

	public List<String> dir() throws SftpException {
		Vector<LsEntry> v = this.sftpChannel.ls("*");
		List<String> files = null;
		if (v != null && v.size() > 0) {
			files = new ArrayList<String>(v.size());
			Iterator<LsEntry> it = v.iterator();
			while (it.hasNext()) {
				LsEntry entry = it.next();
				files.add(entry.getFilename());
			}
		}
		return files;
	}

}
