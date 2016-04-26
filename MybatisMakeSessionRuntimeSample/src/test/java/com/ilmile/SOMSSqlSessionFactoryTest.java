package com.ilmile;

//all static import(our,third party,java,javax) block

//our source import block
import com.ilmile.persistence.QueryDTO;

//third party library import block
import org.junit.Test;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.apache.ibatis.session.SqlSession;
import org.apache.tomcat.jdbc.pool.DataSourceFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

//java import block
import java.util.Properties;
import java.util.List;
import java.util.Map;

//javax import block

public class SOMSSqlSessionFactoryTest {
  static Properties properties = new Properties();
  public static final String PROP_DRIVERCLASSNAME = "driverClassName";
  public static final String PROP_URL = "url";
  public static final String PROP_USERNAME = "username";
  public static final String PROP_PASSWORD = "password";
  
  static {
    properties.setProperty(PROP_DRIVERCLASSNAME, "com.mysql.jdbc.Driver");
    properties.setProperty(PROP_URL, "jdbc:mysql://192.168.1.151:13306/python");
    properties.setProperty(PROP_USERNAME, "python");
    properties.setProperty(PROP_PASSWORD, "python");
  }
  
  public SqlSession getSqlSession() {
    try {
      SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
      sqlSessionFactoryBean.setDataSource(new DataSourceFactory().createDataSource(properties));
      ClassPathResource[] classPathResrouces = { new ClassPathResource("com/ilmile/persistence/QueryMapper.xml") };
      sqlSessionFactoryBean.setMapperLocations(classPathResrouces);
      
      return sqlSessionFactoryBean.getObject().openSession();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  
  public void jsonPrint(Object data) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  //select list test
  @Test
  public void selectAllListTest() {
    QueryDTO dto = new QueryDTO();
    dto.setQueryString("SELECT id, email, name, count FROM user ORDER BY id");
    
    SqlSession sqlSession = getSqlSession();
    
    List<Map<String, Object>> allUserList = sqlSession.selectList("sample.select", dto);
    
    sqlSession.close();
    
    jsonPrint(allUserList);
  }
  
  //select list test 2
  @Test
  public void selectListTest() {
    try {
      QueryDTO dto = new QueryDTO();
      dto.setQueryString("SELECT * FROM user WHERE id = #{params.id}");
      dto.addParam("id", 1);
      
      SqlSession sqlSession = getSqlSession();
      
      List<Map<String, Object>> userList = sqlSession.selectList("sample.select", dto);
      
      sqlSession.close();
      
      jsonPrint(userList);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  //select one test
  @Test
  public void selectOneTest() {
    try {
      QueryDTO dto = new QueryDTO();
      dto.setQueryString("SELECT * FROM user WHERE id = #{params.id}");
      dto.addParam("id", 1);
      
      SqlSession sqlSession = getSqlSession();
      
      Map<String, Object> user = sqlSession.selectOne("sample.select", dto);
      
      sqlSession.close();
      
      jsonPrint(user);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  //insert test
  @Test
  public void insertTest() {
    try {
      QueryDTO dto = new QueryDTO();
      dto.setQueryString("INSERT INTO user (email, name) values (#{params.email}, #{params.name})");
      dto.addParam("email", "bonggi.seo@ilmile.com");
      dto.addParam("name", "bonggi.seo insert test");
      
      SqlSession sqlSession = getSqlSession();
      
      sqlSession.insert("sample.insert", dto);
      
      sqlSession.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  //update test
  @Test
  public void updateTest() {
    try {
      QueryDTO dto = new QueryDTO();
      dto.setQueryString("UPDATE user SET name = #{params.name} WHERE id = #{params.id}");
      dto.addParam("name", "update test");
      dto.addParam("id", 4);
      
      SqlSession sqlSession = getSqlSession();
      
      sqlSession.update("sample.update", dto);
      
      sqlSession.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Test
  public void deleteTest() {
    try {
      QueryDTO dto = new QueryDTO();
      dto.setQueryString("DELETE FROM user WHERE id = #{params.id}");
      dto.addParam("id", 5);
      
      SqlSession sqlSession = getSqlSession();
      
      sqlSession.delete("sample.delete", dto);
      
      sqlSession.close();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
