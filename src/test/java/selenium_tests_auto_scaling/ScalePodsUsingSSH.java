package selenium_tests_auto_scaling;

import java.io.IOException;
import java.io.InputStream;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class ScalePodsUsingSSH {
	

	/* SSH : username, host / ip address, pem file, ssh port (22)*/
	
	static String username = "azureuser";
	static String host = "74.225.249.141";
	static String pemFile = "AZTLDevOpsvm.pem";
	static int port = 22;
	static Session session;
	static ChannelExec channel;
	
	private static void init() throws JSchException {
		JSch sch = new JSch();
		sch.addIdentity(pemFile);
		session = sch.getSession(username, host, port);
		session.setConfig("StrictHostKeyChecking", "no");
		session.connect();
		channel = (ChannelExec)session.openChannel("exec");
		System.out.println("Connection done");
	}

	public static void scaleUp(int podSize) throws JSchException, IOException {
		init(); // authenticate and connect
		
		int pSize = podSize+1;
		
		// Channel to execute
		//ChannelExec channel = (ChannelExec)session.openChannel("exec");
		channel.setCommand("kubectl scale deployment.apps/selenium-node-chrome --replicas="+pSize);
		
		// Do I need know the output of the command?
		InputStream in = channel.getInputStream();
		
		channel.connect();
		
		/*
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line;
		while((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		
		
		// Close the reader
		reader.close();
		*/
		
		// Disconnect
		channel.disconnect();
	
		// tear down
		tearDown();
		
	}

	public static void scaleDown(int podSize) {

	}
	
	public static void tearDown() {
		session.disconnect();
	}


}
