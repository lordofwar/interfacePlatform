package com.gxlu.interfacePlatform.foundation.sftp;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gxlu.interfacePlatform.foundation.ftp.component.FTPSite;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SFTPFactory {
	private static Log log = LogFactory.getLog(SFTPFactory.class);

	public static SFTPServer getSFTPServer(FTPSite ftpSite) {
		SFTPServer sftpServer = null;
		try {
			JSch jsch = new JSch();
			Session sshSession = jsch.getSession(ftpSite.getUser(), ftpSite.getHost(), ftpSite.getPort());
			sshSession.setPassword(ftpSite.getPassword());
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			ChannelSftp channelSftp = (ChannelSftp) channel;
			channelSftp.cd(ftpSite.getDirectory());
			sftpServer = new SFTPServer(channelSftp);
			log.info("Connected to " + ftpSite.getHost() + " by SFTP protocol. Directory is :"+ftpSite.getDirectory());
		} catch (Exception e) {
			log.error("Encounter exception when config sftpChannel:", e);
		}
		return sftpServer;
	}

}
