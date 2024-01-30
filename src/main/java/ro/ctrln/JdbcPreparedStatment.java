package ro.ctrln;

import java.sql.*;

public class Jdbc {
    public static void main(String[] args) {
        String dbUrl = "jdbc:sqlite:C:\\Users\\macar\\IdeaProjects\\JDBC\\sql\\sqlite.db";

        Connection connection = null;

        try {
            Class.forName("org.sqlite.JDBC").newInstance();
            connection = DriverManager.getConnection(dbUrl);

            runSelect(connection);

            runStatment(connection, insertStatment());
            runSelect(connection);

            runStatment(connection, updateStatement());
            runSelect(connection);

            runStatment(connection, deleteStatement());
            runSelect(connection);

           connection.close();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private static String deleteStatement(){
        return "delete person where id=5";
    }

    private static String updateStatement() {
        return "update person set last_name = 'Bastard' where id=1";
    }

    private static void runStatment(Connection connection, String sqlStatement) throws SQLException {
        Statement statement = getStatment(connection);
        statement.execute(sqlStatement);
        statement.close();
    }

    private static String insertStatment() {
            return "insert into person values(5,'Gulliver', 'The Giant', 34, 'Tourist')";
    }

    private static void runSelect(Connection connection) throws SQLException {
        Statement statement = getStatment(connection);
        ResultSet resultSet = statement.executeQuery("select * from person");
        parseResult(resultSet);
        statement.close();
    }

    private static Statement getStatment(Connection connection) throws SQLException {
        return connection.createStatement();
    }

    private static void parseResult(ResultSet resultSet) throws SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int numberOfColumns =resultSetMetaData.getColumnCount();

        for (int i = 1; i <= numberOfColumns; i++) {
            System.out.print(resultSetMetaData.getColumnLabel(i) + "\t");
        }
        System.out.println("\n");

        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String firstName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            int age = resultSet.getInt(4);
            String job = resultSet.getString(5);
            System.out.print(id + "\t" + firstName +"\t" + lastName + "\t" + age +"\t"+ job + "\n");
        }
        System.out.println("\n");
        resultSet.close();
    }
}