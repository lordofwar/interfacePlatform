package com.gxlu.interfacePlatform.database.common;

import java.util.Vector;

public interface CommonPersistDAO {
  public abstract Object createObject(Object obj);

  public abstract Object updateObject(Object obj);

  public abstract Object mergeObject(Object obj);

  public abstract void deleteObject(Object obj);

  public abstract Vector<Object> createAll(Vector<Object> vector);

  public abstract Vector<Object> updateAll(Vector<Object> vector);

  public abstract void deleteAll(Vector<Object> vector);
}
