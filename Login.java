package application;



import static application.Authentication.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.sql.Statement;
import java.awt.List;
import java.io.IOException;
import java.net.URL;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import com.mysql.jdbc.DatabaseMetaData;
import com.mysql.jdbc.ResultSetMetaData;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;


public class Login implements Initializable {
	Authentication auth;
	/** The name of the MySQL account to use (or empty for anonymous) */
	private final String userName = "root";
	@FXML
	ListView<String> lv;
	
	/** The password for the MySQL account (or empty for anonymous) */
	private final String password = "Anime18";

	/** The name of the computer running MySQL */
	private final String serverName = "localhost";

	/** The port of the MySQL server (default is 3306) */
	private final int portNumber = 3306;

	/** The name of the database we are testing with (this default is installed with MySQL) */
	private String dbName = "";
        /** The mode viewable or editable)  */
	private String Mode = "View";
	String dname;
	/** The name of the table we are testing with */
	private final String tableName = "sometable";
	Boolean check=false;
	private Statement stmt;
	Connection conn = null;
	private String combox_db;
	private ObservableList<ObservableList> data;
	private TableView tableview;
        
        @FXML
	ComboBox<String> combox=new ComboBox<String>();
	@FXML
	ComboBox<String> dbmscombox=new ComboBox<String>();
        @FXML
        ComboBox<String> TableMode=new ComboBox<String>();
	
	
	@FXML
	TreeView<String> tv;
	
	
	@FXML
	TextArea TA;
	
	@FXML
	Label db;
	@FXML
	Label console;
	
	@FXML
	Button extract,writequery,runQuery,back;
	String tablename="";
	@FXML
	Label l1;
	ArrayList<TreeItem<String>> tree=new ArrayList<TreeItem<String>> (); 
    
	ObservableList<String>list=FXCollections.observableArrayList();
	ObservableList<String>dbmslist=FXCollections.observableArrayList();
	
	public ObservableList<String> getList() {
		return list;
	}

	public void setList(ObservableList<String> list) {
		this.list = list;
	}

	@FXML
	PasswordField p1;
	@FXML
	TextField t1;
	
	public Connection getConnection() throws SQLException {
               
            Properties connectionProps = new Properties();
            connectionProps.put("user", "root");
            connectionProps.put("password", "Anime18");
            conn = DriverManager.getConnection("jdbc:mysql://"+ this.serverName + ":" + this.portNumber + "/" + this.dbName,connectionProps);		
            return conn;
	}
	
        public void Selectmode(ActionEvent e) {
		Mode = TableMode.getSelectionModel().getSelectedItem();
	}
        
	public void run() {
		// Connect to MySQL
		Connection conn = null;
		
		try {
			conn = this.getConnection();
			System.out.println("connection name is :: "+conn.getClass().getName());
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
		}
		try {
		    
		    DatabaseMetaData meta = (DatabaseMetaData) conn.getMetaData();
		    ResultSet resultSet = meta.getCatalogs();
		    
		    TreeItem<String > root=new TreeItem<String>("databases");
		    
		    
		    while (resultSet.next()) {
		    	String db = resultSet.getString("TABLE_CAT");
			    list.add(db);
			    tree.add(new TreeItem<String>(db));
			    
		    }
		    
		    for (int i = 0; i < tree.size(); i++) {
		    	//System.out.println(i);
		    	root.getChildren().add(tree.get(i));
			}
		    combox.setItems(list);
		    tv.setRoot(root);
		    //
		    //lv.setItems(list);
		    
		for (int i = 0; i < tree.size(); i++) {
	    	
	    	String name=list.get(i);
	    	String query="SELECT TABLE_NAME"+ 
				    " FROM INFORMATION_SCHEMA.TABLES"+
				    " WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA="+"'"+name+"'";
	    	//System.out.println(query);		    
			
			
		    Statement s = conn.createStatement();
		    try {
		    	ResultSet rs = s.executeQuery(query);
		    	while(rs.next()){
					String db=rs.getString(1);
					tree.get(i).getChildren().add(new TreeItem<String>(db));
			    }
		    }
		    catch (Exception e){
		    	System.out.println("Invalid syntax of query");
		    	
		    }
			
		}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}
        
        public void my_run()
        {
            try 
            {
                if(DBMS_name.equals("MySQL"))
                {
                    System.out.println("I CHOOSE YOU >>> MYSQL !!!");
                    
                    DatabaseMetaData meta = (DatabaseMetaData) Established_Connection.getMetaData();
                    
                    ResultSet resultSet = meta.getCatalogs();
		    
		    TreeItem<String > root=new TreeItem<String>("databases");
		    
                    while (resultSet.next()) 
                    {
                        String db = resultSet.getString("TABLE_CAT");
			list.add(db);
                        tree.add(new TreeItem<String>(db));    
		    }
		    
		    for (int i = 0; i < tree.size(); i++) 
                    {
                        root.getChildren().add(tree.get(i));
                    }
		    combox.setItems(list);
		    tv.setRoot(root);
		    
                    for (int i = 0; i < tree.size(); i++) 
                    {
                        String name=list.get(i);
                        String query="SELECT TABLE_NAME"+ " FROM INFORMATION_SCHEMA.TABLES"+ 
                        " WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA="+"'"+name+"'";
	    	
                        Statement s = Established_Connection.createStatement();
                        
                        try 
                        {
                            ResultSet rs = s.executeQuery(query);
                            while(rs.next())
                            {
                                String db=rs.getString(1);
                                tree.get(i).getChildren().add(new TreeItem<String>(db));
                            }
                        }
                        catch (Exception e)
                        {
                            System.out.println("Invalid syntax of query");
                        }
                    }
		}
                else if(DBMS_name.equals("MsAcces"))
                {
                    System.out.println("I CHOOSE YOU >>> MS-ACCESS !!!");
                    
                    java.sql.DatabaseMetaData metab = Established_Connection.getMetaData();
                    
                    ResultSet resultSet = metab.getTables(null, null, null, null);
		    
                    TreeItem<String > root=new TreeItem<String>("databases");
		    
		    list.add("MS-ACCESS DATABASE");
                    
		    while (resultSet.next()) 
                    {
                        String db = resultSet.getString(3);
                        System.out.println(db);
                        tree.add(new TreeItem<String>(db));    
		    }
                    for (int i = 0; i < tree.size(); i++) 
                    {
                        System.out.println(tree.get(i));
                        root.getChildren().add(tree.get(i));
                    }
		    combox.setItems(list);
		    tv.setRoot(root);
                }
            }
            catch (Exception e) 
            {
                System.out.println("Databases Data Fetching Failed !!!");
            }
            
	}
	
	public void ExtractButton(ActionEvent event) throws SQLException 
        {
            tablename=tv.getSelectionModel().getSelectedItem().getValue();
            for(int i=0;i<tree.size();i++) 
            {
                for(int j=0;j<tree.get(i).getChildren().size();j++) 
                {
                    if(tablename.equals(tree.get(i).getChildren().get(j).getValue()) )
                    {
                        dname=tree.get(i).getChildren().get(j).getParent().getValue();
                    }
                }
            }
            String SQL = null;
            if(DBMS_name.equals("MySQL"))
            {
                System.out.println("I CHOOSE YOU >>> MYSQL !!!");
                SQL="SELECT *" + " FROM "+dname+"."+tablename;
		/////////////////////////////////////////////////////
		System.out.println(dname+"-----------"+tablename);
		
                System.out.println(SQL);
            }
            else if(DBMS_name.equals("MsAcces")) 
            {
                System.out.println("I CHOOSE YOU >>> MS-ACCESS !!!");
                SQL="SELECT *"+ " FROM "+tablename;
		/////////////////////////////////////////////////////
		System.out.println("MS-ACCESS-----------"+tablename);

                System.out.println(SQL);
            }
            Generateresult(SQL);
        }
	
	
	Boolean Generateresult(String SQL) {
            tableview = new TableView();
            tableview.setEditable(true);
            
            data = FXCollections.observableArrayList();
            try
            {
                ResultSet rs = Established_Connection.createStatement().executeQuery(SQL);
           /**********************************
           * TABLE COLUMN ADDED DYNAMICALLY *
           **********************************/
        //Function for creating a dynamic table ///Source GITHUB seifallah/Dynamic-TableView--Java-Fx-2.0-
          for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
              //We are using non property style for making dynamic table
              final int j = i; 
      		//c1.setCellValueFactory(new PropertyValueFactory<Person,String>("firstname"));
              TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
              col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                  public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) { 
                	    return new SimpleStringProperty((String) param.getValue().get(j));
                	  
					
                  }                    
              });
              if(Mode.equals("Editable")){col.setCellFactory(TextFieldTableCell.forTableColumn());}
              col.setOnEditCommit(
              		new EventHandler<CellEditEvent<String,String>>() {
              			@Override
              			public void handle(CellEditEvent<String,String>t) {
                                   
                            
              				TablePosition<String, String> pos = t.getTablePosition();            		        	 
              				String valueafteredit = t.getNewValue();
              				int row = pos.getRow();
              				//TableColumn<String, String> s=pos.getTableColumn();
              			
              				int colindex=pos.getColumn();
              				int colindexofpk=0;
              				
              				System.out.println(colindex);
              				//System.out.println(row);
              				
              				String valuebeforeedit = (String) col.getCellObservableValue(row).getValue();
              				String temp=valuebeforeedit;
              				valuebeforeedit=valueafteredit;
              				
              				System.out.println(valuebeforeedit+"---"+valueafteredit);
              				
              				try {
								String colname=rs.getMetaData().getColumnName(colindex+1);
								System.out.println(colname);
								System.out.println(dname);
								System.out.println(tablename);
                                                                String update_query = null;
                                                                if(DBMS_name.equals("MySQL")) {
                                                                    System.out.println("I CHOOSE YOU >>> MYSQL !!!");
                                                                    update_query="UPDATE " +dname +"."+tablename+ 
										" SET "+colname+"="+"'"+valueafteredit+"'" + 
										" WHERE "+colname+"="+"'"+temp+"'";
                                                                }
                                                                else if(DBMS_name.equals("MsAcces")) {
                                                                    
                                                                    update_query="UPDATE " +tablename+ 
										" SET "+colname+"="+"'"+valueafteredit+"'" + 
										" WHERE "+colname+"="+"'"+temp+"'";
								
                                                                    System.out.println("I CHOOSE YOU >>> MS-ACCESS !!!");
                                                                }
                    
                    
                                                                /**/
								System.out.println(update_query);
								executeUpdate(update_query);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
              				
              		        }
              		    }
              		);
              
              SimpleStringProperty strProperty = new SimpleStringProperty() ;
              ArrayList<SimpleStringProperty> aList = new ArrayList<>();
              
              String temp1 = "DB";
              strProperty.set(temp1);
              
              tableview.getColumns().addAll(col); 
              //System.out.println("Column ["+i+"] ");
          }
          /********************************
           * Data added to ObservableList *
           ********************************/
          int j=0;
          while(rs.next()){
              //Iterate Row
        	  
              ObservableList<String> row = FXCollections.observableArrayList();
              for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                  //Iterate Column
                  row.add(rs.getString(i));
              }
              //System.out.println("Row no "+j+" added "+row );
              data.add(row);
              j++;

          }

          //FINALLY ADDED TO TableView
          tableview.setEditable(true);
          
          tableview.setItems(data);
          
          
        }catch(Exception e){
            //e.printStackTrace();
            System.out.println("Invalid selection.You can only extract data of tables");
            return false;
        }
        
        
        Stage stage=new Stage();
        //Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setWidth(400);
        stage.setHeight(450);
        stage.setTitle("DATA in table");
        stage.setResizable(true);
        //Main Scene
        
        Scene scene = new Scene(tableview);        
        stage.setScene(scene);
        stage.show();
       
		
		
		return true;
		
		

	}
	
	public void queryButton(ActionEvent e) throws IOException {
		
		Parent root = new FXMLLoader().load(getClass().getResource("/application/QueryWrite.fxml"));
		Scene scene = new Scene(root);
		
		Stage window=(Stage)((Node)e.getSource()).getScene().getWindow();
		window.setTitle("Write Query");
        
		window.setScene(scene);
		window.show();
		
		//System.out.println(TA.getText());
		
	}
	
        String tokenize(String q,String table,String delim) {
		if(q.contains(table)) {
			return q;
		}
		String emp="";
		StringTokenizer st1=new StringTokenizer(q);
		for (int i = 1; st1.hasMoreTokens(); i++) {
	         //System.out.print(st1.nextToken());
			 String tok=st1.nextToken();
			 if((tok.equals(delim.toUpperCase()))||(tok.equals(delim.toLowerCase()))) {
		            if(DBMS_name.equals("MySQL")) {
                                 emp=emp+" "+tok+" "+table+".";
                                System.out.println("I CHOOSE YOU >>> MYSQL !!!");
                            }
                            else if(DBMS_name.equals("MsAcces")) {
                                 emp=emp+" "+tok+" ";
                                System.out.println("I CHOOSE YOU >>> MS-ACCESS !!!");
                            }
                         }
			 else {
				 emp=emp+""+tok+" ";
			 }
	         
		 }
		
		return emp;
	}
	@FXML
	public void GetComboBoxText(ActionEvent e) {
		db.setText("");
		combox_db=combox.getSelectionModel().getSelectedItem();
	}
	@FXML
	EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
		   @Override 
		   public void handle(MouseEvent e) { 
			   db.setText("");
		   } 
	};  

        public void runquery(ActionEvent e) throws IOException, SQLException {
		dname=combox_db;
		if(combox_db!=null) {
			String query=TA.getText();
			if(!query.isEmpty()) {
				if(Pattern.compile(Pattern.quote("insert"), Pattern.CASE_INSENSITIVE).matcher(query).find()){
						String SQL=tokenize(query, combox_db,"into");
						if(executeUpdate(SQL)) {
							//System.out.println("One row added");
							
						}
							else {
								console.setText("Invalid query");
								return;
							}
					}
					else if(Pattern.compile(Pattern.quote("Select"), Pattern.CASE_INSENSITIVE).matcher(query).find()){
						tablename=getTablename(query);
						
						String SQL=tokenize(query, combox_db,"from");
						//tablename=getTablename(SQL);
						System.out.println("tablename==="+tablename);
						if(Generateresult(SQL)){
							System.out.println("combox db ==="+combox_db);
						}
						else {
								console.setText("Invalid query");
								return;
						}
						
					}
					else if(Pattern.compile(Pattern.quote("Delete"), Pattern.CASE_INSENSITIVE).matcher(query).find()){
						String SQL=tokenize(query, combox_db,"from");
						if(executeUpdate(SQL)) {
							console.setText("Row(s) deleted");
						}
						else {
							console.setText("Invalid query");
							return;
						}
					}
					else if(Pattern.compile(Pattern.quote("create"), Pattern.CASE_INSENSITIVE).matcher(query).find()){
						this.dbName=combox_db;
                                                if(DBMS_name.equals("MySQL")) {
                                                    System.out.println("I CHOOSE YOU >>> MYSQL !!!");
                                                    Statement s = getConnection().createStatement();
						    s.executeUpdate(query);
                                                }
                                                else if(DBMS_name.equals("MsAcces")) {
                                                    System.out.println("I CHOOSE YOU >>> MS-ACCESS !!!");
                                              
						/*Statement s = getConnection().createStatement();
						s.executeUpdate(query);
                                                  System.out.println(" 1 "+query);
						
                                                */  if(executeUpdate(query)){
							console.setText("Created table in given database...");
						}
						else {
							console.setText("Invalid query");
							return;
						}  
                                                }
					}
					else {
						console.setText("Invalid query");
						return;
					}
					console.setText("Query compiled sucessfully");
			}
				else {
					console.setText("Error !Some text is required");
				}
		}
				
		
		else {
			db.setText("**Select database first");
		}
	}	
	
	public void Goback(ActionEvent e) throws IOException {
		Parent root = new FXMLLoader().load(getClass().getResource("/application/Main.fxml"));
		Scene scene = new Scene(root);
		
		Stage window=(Stage)((Node)e.getSource()).getScene().getWindow();
		window.setTitle("Databases");
        
		window.setScene(scene);
		window.show();
		
	}

        static String getTablename(String query) {
		String tok="";
		StringTokenizer st1=new StringTokenizer(query);
		
		for (int i = 1; st1.hasMoreTokens(); i++) {
	         //System.out.print(st1.nextToken());
			 tok=st1.nextToken();
			 if((tok.equals("from"))) {
				 tok=st1.nextToken();
				 if(tok.contains(".")) {
					 tok=tok.substring(tok.indexOf(".")+1,tok.length()-1);
					 return tok;
				 }
				 System.out.println(tok);
				 if(tok.endsWith(";")) {
					 tok=tok.substring(0, tok.length()-1);
				 }
				 return tok;
			 }
			 else {
				 //emp=emp+""+tok+" ";
			 }
	        //return tok;
		 }
		return tok;
	}	

        public boolean executeUpdate(String command) throws SQLException {
		 Statement s1 = Established_Connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
		 //Statement s1 = Established_Connection.createStatement();
		 
                 try {
	    	
	        try {
                    System.out.println(command);
	        s1.executeUpdate(command); // This will throw a SQLException if it fails
                }
	        catch(Exception e) {
	        	return false;
	        }
	        return true;
	    }
		  finally {

	    	// This will run whether we throw an exception or not
	        //if (s != null) { s.close(); }
	    }
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
            // TODO Auto-generated method stub
                //run();
                ObservableList<String>modelist=FXCollections.observableArrayList();
		modelist.add("View");
		modelist.add("Editable");
		TableMode.setItems(modelist);
                
                my_run();
            
		
	}

	


}
