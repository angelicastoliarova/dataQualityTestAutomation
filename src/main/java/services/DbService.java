package services;

import dto.Book;

import java.sql.*;

public class DbService {
    public static void addData(Book book, int bookID) {

        Connection con = null;
        Statement st = null;
        try {

            con = ConnectionPool.getInstance().getConnection();
            st = con.createStatement();
            String sql = "INSERT INTO task4 (totalWordCount, paragraphCount, characterCount,whitespaceCount,sentenceCount) Values (?,?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, book.getTotalWordCount());
            preparedStatement.setInt(2, book.getParagraphCount());
            preparedStatement.setInt(3, book.getCharacterCount());
            preparedStatement.setInt(4, book.getWhitespaceCount());
            preparedStatement.setInt(5, book.getSentenceCount());

            int rows = preparedStatement.executeUpdate();

            st.close();
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

    }
}

