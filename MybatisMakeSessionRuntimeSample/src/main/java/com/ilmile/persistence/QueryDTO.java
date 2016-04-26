package com.ilmile.persistence;

//all static import(our,third party,java,javax) block

//our source import block

//third party library import block

//java import block
import java.util.Map;
import java.util.HashMap;

//javax import block

public class QueryDTO {
  private String queryString;
  private Map<String, Object> params;
  
  public QueryDTO() {
    this.queryString = "";
    this.params = new HashMap<String, Object>();
  }
  
  public void setQueryString(String queryString) {
    this.queryString = queryString;
  }
  public String getQueryString() {
    return this.queryString;
  }
  
  public void setParams(Map<String, Object> params) {
    this.params = params;
  }
  public Map<String, Object> getParams() {
    return this.params;
  }
  
  public void addParam(String key, Object value) {
    this.params.put(key, value);
  }
  @SuppressWarnings("unchecked")
  public <T> T getParam(String key) {
    return (T) this.params.get(key);
  }
}
