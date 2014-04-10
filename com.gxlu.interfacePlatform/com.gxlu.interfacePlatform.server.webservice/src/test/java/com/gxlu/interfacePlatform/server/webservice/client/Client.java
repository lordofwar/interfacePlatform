package com.gxlu.interfacePlatform.server.webservice.client;

import java.net.URL;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import com.gxlu.interfacePlatform.server.webservice.HelloWorld;
import com.gxlu.interfacePlatform.server.webservice.User;
import com.gxlu.interfacePlatform.server.webservice.UserImpl;

public class Client {
	 private static final QName SERVICE_NAME 
     = new QName("http://webservice.server.interfacePlatform.gxlu.com/", "HelloWorld");
 private static final QName PORT_NAME 
     = new QName("http://webservice.server.interfacePlatform.gxlu.com/", "HelloWorldPort");


 private Client() {
 } 

 public static void main(String args[]) throws Exception {
     Service service = Service.create(new URL("http://localhost:9000/helloWorld?wsdl"),SERVICE_NAME);
     // Endpoint Address
     String endpointAddress = "http://localhost:9000/helloWorld";
     // If web service deployed on Tomcat deployment, endpoint should be changed to:
     // String endpointAddress = "http://localhost:8080/java_first_jaxws/services/hello_world";

     // Add a port to the Service
     //service.addPort(PORT_NAME, SOAPBinding.SOAP11HTTP_BINDING, endpointAddress);
     Iterator it= service.getPorts();
     while(it.hasNext()){
    	 QName name =(QName) it.next();
    	 System.out.println(name.getLocalPart());
     }
     HelloWorld hw = service.getPort(HelloWorld.class);
     System.out.println(hw.sayHi("OMG"));

     User user = new UserImpl("World");
     System.out.println(hw.sayHiToUser(user));

     //say hi to some more users to fill up the map a bit
     user = new UserImpl("Galaxy");
     System.out.println(hw.sayHiToUser(user));

     user = new UserImpl("Universe");
     System.out.println(hw.sayHiToUser(user));

     System.out.println();
//     System.out.println("Users: ");
//     Map<Integer, User> users = hw.getUsers();
//     for (Map.Entry<Integer, User> e : users.entrySet()) {
//         System.out.println("  " + e.getKey() + ": " + e.getValue().getName());
//     }

 }
}

