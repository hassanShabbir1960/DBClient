package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Authentication implements Initializable{
	
	@FXML
	TextField textfeild=new TextField();
	@FXML
	PasswordField passfeild=new PasswordField();
	@FXML
	Button b1=new Button();
        
	static String DBMS_name;
        
        //static String userName;
        
        //static String password;
        
        //static int portNumber = 3306;
        
        String userName;
        
        String password;
        
        int portNumber = 3306;
        
        static Connection Established_Connection;
        
	@FXML
	ComboBox<String> DBMS=new ComboBox<String>();
	//Connection conn;
	
	@FXML
	void login(ActionEvent event) throws IOException, SQLException {
		
        	if(DBMS_name.equals("MySQL")) {
                    userName = textfeild.getText();
                    password = passfeild.getText();
                    Established_Connection = getConnection(userName,password);
           
                    System.out.println("I CHOOSE YOU >>> MYSQL !!!");
		}
		else if(DBMS_name.equals("MsAcces")) {
                    System.out.println("I CHOOSE YOU >>> MS-ACCESS !!!");
                    Established_Connection = getConnectionMsAccess();
		}
			Parent root = new FXMLLoader().load(getClass().getResource("/application/Main.fxml"));
			Scene scene = new Scene(root);
			Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
			window.setTitle("DATABASES");
	        
			window.setScene(scene);
			window.show();
	}

	public Connection getConnection(String userName,String password) throws SQLException {
		Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);
		System.out.println("trying to get connection!! ");
		Connection conn = DriverManager.getConnection("jdbc:mysql://"+ "localhost" + ":" + portNumber + "/" + "",connectionProps);		
		System.out.println(" Connection achieved!! ");
                System.out.println("connection name is :: "+conn.getClass().getName());
		return conn;
	}
	
        public Connection getConnectionMsAccess() {
                Connection conn=null;
		try {

			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		}
		catch(ClassNotFoundException cnfex) {

			System.out.println("Problem in loading or "
					+ "registering MS Access JDBC driver");
			cnfex.printStackTrace();
		}

		// Step 2: Opening database connection
		try {
                        String msAccDB="C:/Users/HUZAIFA ZAHID/Documents/Database1.accdb";
			String dbURL = "jdbc:ucanaccess://" + msAccDB; 

			// Step 2.A: Create and 
			// get connection using DriverManager class
			conn = DriverManager.getConnection(dbURL); 
		}
		catch(SQLException sqlex){
			sqlex.printStackTrace();
		}
                System.out.println("connection name is :: "+conn.getClass().getName());
		return conn;
	}
	
        public void Selectdbms(ActionEvent e) {
		DBMS_name=DBMS.getSelectionModel().getSelectedItem();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		ObservableList<String>list=FXCollections.observableArrayList();
		list.add("MySQL");
		list.add("MsAcces");
		DBMS.setItems(list);
	}
	
}
