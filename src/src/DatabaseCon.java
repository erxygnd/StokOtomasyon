package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseCon implements Database{

    public boolean flag=true;

    public User user;
    public Product product;
    public ArrayList<Product> products;
    public int id;

    private  Connection con;
    private static DatabaseCon con2;

    private Statement statement = null;


    private PreparedStatement preparedStatement = null;
    public DatabaseCon getSqlFrontController(){
        if(con2==null)
            con2=new DatabaseCon();
        return con2;
    }
    public void command(String command){
        switch(command.toLowerCase()){
            case "insertuser":
                insertUser();
                break;
            case "insertmessage":
                insertMessage();
                break;
            case "select":
                selectUser();
                break;
            case "list_product":
                list_product();
                break;
            case "addproduct":
                addproduct();
                break;
            case "connect":
                connect();
                break;
            case "delete":
                delete();
                break;
            default:
                break;
        }
    }
    private void delete() {
        try {
            statement = (Statement) con.createStatement();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String request="Delete FROM Product WHERE id=?";
        try {
            preparedStatement = con.prepareStatement(request);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            preparedStatement.setString(1,String.valueOf(id));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            int rowsDeleted = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void list_product() {
        try {
            products=new ArrayList<Product>();
            statement = (Statement) con.createStatement();
            String request="SELECT * FROM product";
            preparedStatement = con.prepareStatement(request);
            ResultSet rs=preparedStatement.executeQuery();
            while(rs.next()) {
                Product ms=new Product(rs.getString("name"), rs.getString("price"), rs.getString("type"));
                products.add(ms);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();         user=null;
        }finally {
            if(statement != null){
                try{
                    statement.close();

                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void selectUser(){
        try {
            statement = (Statement) con.createStatement();
            String request="SELECT * FROM user WHERE username = ? and password = ?";
            preparedStatement = con.prepareStatement(request);
            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2,user.getPassword());
            ResultSet rs=preparedStatement.executeQuery();
            if(!rs.next()){
                user=null;

            }
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();           user=null;
        }finally {
            if(statement != null){
                try{
                    statement.close();

                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    private void insertMessage() {
        // TODO Auto-generated method stub


    }
    private void addproduct() {
        try {
            statement = (Statement) con.createStatement();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String request = "Insert Into Product (name,price,type) VALUES(" + "'" + product.getName()+ "'," + "'" + product.getPrice()+"','"+product.getType()+"')";

        try {
            statement.executeUpdate(request);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
    private void insertUser() {
        try {
            statement = (Statement) con.createStatement();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String request = "Insert Into User (username,name,password) VALUES(" + "'" + user.getUsername()+ "'," + "'" + user.getName()+"','"+user.getPassword()+"')";

        try {
            statement.executeUpdate(request);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
    private void connect(){

        String url = "jdbc:sqlite:test.db";
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver Bulunamadı....");
        }
        try {
            con = DriverManager.getConnection(url);
            System.out.println("Bağlantı Başarılı...");
        } catch (SQLException ex) {
            flag=false;

        }
    }
    @Override
    public User getUser() {
        // TODO Auto-generated method stub
        return this.user;
    }
    @Override
    public void setUser(User user) {
        // TODO Auto-generated method stub
        this.user=user;

    }
}
