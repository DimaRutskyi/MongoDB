package hw5.users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by v.davidenko on 03.02.2016.
 */
public class UserJDBCManager {

    public int create(User user) throws SQLException {
        String query = "insert into USERS \n" +
                        "(ID, USERNAME, PSW, REGHDATE)\n" +
                        "values\n" +
                        "(SEQ_USERS.nextval,?,?,?)";
        Connection conn = null;
        try{
            conn = connectToDB();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setDate(3, new Date(user.getDate().getTime()));
            ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null){
                conn.close();
            }
        }
        return 1;
    }

    public List<User> findAll() throws SQLException {
        String query = "select * from USERS";

        List<User> list = new ArrayList<User>();
        Connection conn = null;
        try {
            conn = connectToDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(query);

            while (res.next()) {
                User user = new User();
                user.setId(res.getInt("ID"));
                user.setName(res.getString("USERNAME"));
                user.setPassword(res.getString("PSW"));
                user.setDate(res.getDate("REGDATE"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null){
                conn.close();
            }
        }
        return list;
    }

    public Connection connectToDB() throws SQLException {
        final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
        final String DB_LOGIN = "notebooks";
        final String DB_PASSWORD = "notebooks";

        Locale.setDefault(Locale.ENGLISH);
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
