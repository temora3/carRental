//package gui;
//
//import javax.swing.*;
//import java.sql.*;
//
//public class database {
//    public static void main(String[] args) {
//     try {
//         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tecomarental","root","");
//         Statement statement= conn.createStatement();
//
//         ResultSet result= statement.executeQuery("select * from customer_cred");
//          while(result.next()){
//              System.out.print(result.getString("customerEmail"));
//          }
//
//     } catch(Exception e){
//         e.printStackTrace();
//     }
//    }
//
//    private user  insert(String fname, String email, String phone, String password) {
//        user user=null;
//
//        try{
//            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tecomarental","root","");
//            // Connected to database successfully.
//
//            Statement stmt = conn.createStatement();
//            String sql = "INSERT INTO customer_cred (customerName, customerEmail, customerPhone, customerPassword) " +
//                    "VALUES (?, ?, ?, ?, ?)";
//            PreparedStatement preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setString(1, fname);
//
//            preparedStatement.setString(2, email);
//            preparedStatement.setString(3, phone);
//            preparedStatement.setString(4, password);
//
//            //Insert row into the table
//            int addedRows = preparedStatement.executeUpdate();
//            if (addedRows > 0) {
//                user = new user();
//                user.fname = fname;
//
//                user.email = email;
//                user.phone = phone;
//
//                user.password = password;
//            }
//
//            stmt.close();
//            conn.close();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//        return user;
//    }
////    private void registeruser() {
////        String fname = txtfname.getText();
////
////        String email = txtemail.getText();
////        String phone = txtphonenumber.getText();
////        String password = String.valueOf(passwordfield.getPassword());
////        String confirmpassword = String.valueOf(confirmpasswordfield.getPassword());
////        if (fname.isEmpty()  || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmpassword.isEmpty()) {
////            JOptionPane.showConfirmDialog(null,
////                    "Please enter all fields",
////                    "try again",
////                    JOptionPane.ERROR_MESSAGE);
////            return;
////        }
////
////        if (!password.equals(confirmpassword)) {
////            JOptionPane.showMessageDialog(null,
////                    "confirm password does not match",
////                    "try again",
////                    JOptionPane.ERROR_MESSAGE);
////            return;
////        }
////        user=insert(fname, email, phone, password);
////        if (user !=user) {
////            dispose();
////
////            signuppanel.disable();
////        }
////        else{
////            JOptionPane.showMessageDialog(null,
////                    "Failed to register user",
////                    "try again",
////                    JOptionPane.ERROR_MESSAGE);
////
////        }
////
////    }
//
////    public static void main(String[] args) {
////        signup myForm = new signup();
////        user user = myForm.user;
////        if (user != null) {
////            System.out.println("Successful registration of: " + user.fname+" "+user.lname);
////        }
////        else {
////            System.out.println("Registration canceled");
////        }
////    }
//}
