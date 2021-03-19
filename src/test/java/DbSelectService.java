import services.ConnectionPool;

import java.sql.*;

public class DbSelectService {
    public static ResultSet selectFromDb(String selectString, int bookId) {

        Connection con = null;
        Statement st = null;
        try {

            con = ConnectionPool.getInstance().getConnection();
            st = con.createStatement();
            PreparedStatement statement = con.prepareStatement("select ? from book where book_id = ?");
            if (selectString.isEmpty())
                statement.setString(1, "total_word_count");
            else {
                statement.setString(1, selectString);
            }
            statement.setInt(2, bookId);
            ResultSet resultSet = statement.executeQuery();
            st.close();
            return resultSet;
        } catch (SQLException e) {

            e.printStackTrace();
        } catch (

                InterruptedException e) {

            e.printStackTrace();
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                }
            }
            try {
                ConnectionPool.getInstance().returnConnection(con);
            } catch (SQLException e) {
            } catch (InterruptedException e) {
            }
        }
        return null;
    }
}

