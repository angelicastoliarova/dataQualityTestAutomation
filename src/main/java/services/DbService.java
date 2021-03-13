package services;

import dto.Book;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DbService {
    public static void addData(Book book, int bookID) {

        Connection con = null;
        Statement st = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            st = con.createStatement();
            int result = st.executeUpdate("INSERT INTO book(book_id, content, date) VALUES(" + bookID + ",'"
                    + book.getTotalWordCount() + "','"
                    + book.getParagraphCount() + "','"
                    + book.getCharacterCount() + "','"
                    + book.getWhitespaceCount() + "','"
                    + book.getSentenceCount() + "');");
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

