package com.gxlu.interfacePlatform.server.webservice;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlJavaTypeAdapter(UserAdapter.class)
public interface User {

    String getName();
}
