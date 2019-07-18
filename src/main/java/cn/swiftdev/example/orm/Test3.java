package cn.swiftdev.example.orm;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Table;

public class Test3 {
    public static void main(String[] args) {
		Member member = new Member();
		member.setId(1L);
		List<?> result = select(member);
		System.out.println(Arrays.toString(result.toArray()));
	}

	private static List<?> select(Object condition) {
		List<Object> result = new ArrayList<>();
		Class<?> entityClass = condition.getClass();
		
		
    	Connection conn = null;
    	PreparedStatement pstm = null;
    	ResultSet rs = null;
    	
    	try{
    		Class.forName("com.mysql.jdbc.Driver");
    		conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/blog", "root", "123456");
    		
    		Map<String, String> columnMapper = new HashMap<String,String>();
    		Map<String, String> fieldMapper = new HashMap<String, String>();
    		
    		Field[] fields = entityClass.getDeclaredFields();
    		for(Field field : fields){
    			field.setAccessible(true);
    			if(field.isAnnotationPresent(Column.class)){
    				Column column = field.getAnnotation(Column.class);
    				columnMapper.put(column.name(), field.getName());
    				fieldMapper.put(field.getName(), column.name());
    			}else{
    				columnMapper.put(field.getName(), field.getName());
    				fieldMapper.put(field.getName(), field.getName());
    			}
    		}
    		
    		Table table = entityClass.getAnnotation(Table.class);
    		StringBuffer sb = new StringBuffer();
    		sb.append("select * from " + table.name() + " where 1 = 1");
    		
    		for(Field field : fields){
    			Object value = field.get(condition);
    			if(value != null){
    				//先都按照String来处理
    				sb.append(" AND " + fieldMapper.get(field.getName()) + " = " + value );
    			}
    		}
    		System.out.println(sb.toString());
    		pstm = conn.prepareStatement(sb.toString());
    		rs = pstm.executeQuery();
    		
    		int columnCounts = rs.getMetaData().getColumnCount();
    		while(rs.next()){
    			Object object = entityClass.newInstance();
    			for(int i= 1; i < columnCounts; i ++){
    				String columnName = rs.getMetaData().getColumnName(1);
    			
    				String fieldName = columnMapper.get(columnName);
    				Field field = entityClass.getDeclaredField(fieldName);
    				field.setAccessible(true);
    				field.set(object, rs.getObject(columnName));				
    			}
    			result.add(object);
    		}
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		try {
				if(rs != null){
					rs.close();
				}
				if(pstm != null){
					pstm.close();
				}
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	
		return result;
	}
}
