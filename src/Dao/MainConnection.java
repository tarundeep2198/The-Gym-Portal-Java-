/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;


import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.User;

/**
 *
 * @author HP
 */
public class MainConnection {
    public static Connection getCon() throws ClassNotFoundException{
    Class.forName("com.mysql.jdbc.Driver");
    Connection con=null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root" ,"" );
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    return con;
    }
    
    public static String Retrieve() throws ClassNotFoundException, SQLException{
        
        Statement s=(Statement) getCon().createStatement();
        ResultSet rs=s.executeQuery("select * from details");
            String userdata=" ";

            while(rs.next()){
                int id=rs.getInt("Mem_No");
                String Name=rs.getString("Name");
                String Ph=rs.getString("Phone_Number");
                String Plan=rs.getString("Plan");
            //userdata="\n"+rs.getInt(1)+ " " +rs.getString(2)+ " " +rs.getString(3);
            System.out.println(id+ "  " +Name+"  " +Ph+   "   "+Plan);
            
            
            }
            s.close();
            getCon().close();
        return userdata;
            
    }
   
    //Insertion
    public static int Join(String Name,String Phone_Number,String Plan) throws ClassNotFoundException, SQLException{
      
        PreparedStatement ps=getCon().prepareStatement("insert into details(Name,Phone_Number,Plan) values(?,?,?)");
        ps.setString(1,Name);
         ps.setString(2,Phone_Number);
         ps.setString(3,Plan);
        int count=ps.executeUpdate();
        System.out.println("Member Added" +count);
       
        ps.close();
        getCon().close();
        return count;
    }
    
    //update
    public static void Update(int Mem_No,String Name,String Phone_Number,String Plan) throws SQLException{
        try {
            String query="update details Set Phone_Number= ? , Plan= ? , Name = ?  WHERE Mem_No=?";
            PreparedStatement ps=getCon().prepareStatement(query);
            ps.setInt(4,Mem_No);
            ps.setString(3, Name);
            ps.setString(1, Phone_Number);
            ps.setString(2,Plan);
            int count =ps.executeUpdate();
            if(count>0){
                JOptionPane.showMessageDialog(new JFrame(), "User Updated");
            }else{
                JOptionPane.showMessageDialog(new JFrame(), "Something went wrong.");
            }
            ps.close();
            getCon().close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    //show
    public static User Showquery(String  Phone_Number) throws ClassNotFoundException, SQLException{
        //Class.forName("com.mysql.jdbc.Driver");
        //Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root" ,"" );
        //java.sql.Statement st=con.createStatement();
        PreparedStatement ps=getCon().prepareStatement("select * from details where Phone_Number = ?");
        ps.setString(1, Phone_Number);
        ResultSet rs=ps.executeQuery();
        User user = new User();
        while(rs.next()){
            user.setMem_No(rs.getInt(1));
            user.setName(rs.getString(2));
            user.setPhone_Number(rs.getString(3));
            user.setPlan(rs.getString(4));
            String info=rs.getInt(1)+ " "+rs.getString(2)+ " "+rs.getString(3)+" "+rs.getString(4);
            System.out.println(info);
            return user;
        }
        rs.close();
        getCon().close();
        return null;
    }
    
    public static List<String[]> getAllMembers(){
        List<String[]> list = new ArrayList<>();
        try {
            PreparedStatement ps = getCon().prepareStatement("select * from details");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(new String[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)});
            }
            return list;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MainConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
