package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBcontroller
{
    String ip = null;
    String port = null;
    String databaseName = null;
    String DB_URL = null;
    String driver = "org.mariadb.jdbc.Driver";
    public Connection conn = null;

    //생성자. ip, port, db이름, 계정, 패스워드를 받아 DB와의 세션을 수립한다.
    public DBcontroller(String SERVER_NAME, String DB_IP, String DB_PORT, String DB_NAME, String DB_USER, String DB_PASSWD)
    {
        //DB연결에 필요한 데이터 받고 저장
        this.ip = DB_IP;
        this.port = DB_PORT;
        this.databaseName = DB_NAME;
        this.DB_URL = "jdbc:mariadb://" + ip + ":" + port + "/" + databaseName;

        //DB연결 및 예외처리
        try
        {
            Class.forName(this.driver);
            this.conn = DriverManager.getConnection(this.DB_URL, DB_USER, DB_PASSWD);
            if (this.conn != null)
            {
                System.out.println(SERVER_NAME +" DB connect Complete");
            }
        }
        catch (ClassNotFoundException e)
        {
            System.out.println(SERVER_NAME +" Driver Load Fail");
            e.printStackTrace();
            System.exit(1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println(SERVER_NAME +" DB connect Fail");
        }
    }

    //데이터INSERT
    public void insertData(String insertData)
    {
        String query = null;
        PreparedStatement prparedQuery = null;

        try
        {
            //쿼리준비
            query = "INSERT INTO rawData(title) VALUES(\'" + insertData + "\')";
            prparedQuery = this.conn.prepareStatement(query);
            prparedQuery.executeQuery();
        }
        catch (SQLException e)
        {
            System.out.println("error: " + e);
        }
    }

    //AUTO_INCREMENT 1로 초기화
    public void initIncrement()
    {
        String query = null;
        PreparedStatement prparedQuery = null;

        try
        {
            //쿼리준비
            query = "ALTER TABLE rawData AUTO_INCREMENT=1";
            prparedQuery = this.conn.prepareStatement(query);
            prparedQuery.executeQuery();
        }
        catch (SQLException e)
        {
            System.out.println("error: " + e);
        }
    }

    //데이터DELETE
    public  void deleteDataAll()
    {
        String query = null;
        PreparedStatement prparedQuery = null;

        try
        {
            //쿼리준비
            query = "DELETE FROM rawData" ;
            prparedQuery = this.conn.prepareStatement(query);
            prparedQuery.executeQuery();
        }
        catch (SQLException e)
        {
            System.out.println("error: " + e);
        }
    }

    public void closeSession()
    {
        try
        {
            this.conn.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
