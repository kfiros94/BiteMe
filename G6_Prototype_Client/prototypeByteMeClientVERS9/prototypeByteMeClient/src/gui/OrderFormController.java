package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import client.ClientUI;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import logic.ClientInfo;
import logic.Order;


// implements Initializable 
public class OrderFormController {
    private Order s;
        
    @FXML
    private Label lbRestaurant;
    @FXML
    private Label lbOrder_number;
    @FXML
    private Label lbTotal_price;
    @FXML
    private Label lbOrder_list_number ;
    @FXML
    private Label lbOrder_address;
    
    @FXML
    private Label lbOnly_numbers;
    
    @FXML
    private Label lbOrder_updated;
    
    
    @FXML
    private TextField txtiDRestaurant;
    @FXML
    private TextField txtIdOrder_number;
    @FXML
    private TextField txtTotal_price;
    @FXML
    private TextField txtOrder_list_number;
    @FXML
    private TextField txtOrder_address;

    
    
    @FXML
    private TextField txtiDRestaurant1;
    @FXML
    private TextField txtIdOrder_number1;
    @FXML
    private TextField txtTotal_price1;
    @FXML
    private TextField txtOrder_list_number1;
    @FXML
    private TextField txtOrder_address1;
    
    
    @FXML
    private Button btnClose;
    
    
    
    
    //aaaaaaaaaaaaaaaaaaaaaaaa
    @FXML
    private StackPane contentArea;

    
    /*
    @FXML
    public void initialize() 
    {
        showView1();  // Show the default view on startup
    }
    */
    
    
    /*
    @FXML
    private void showView1() 
    {

        loadView("View1.fxml");
        
        
        //ccccccc

        
        this.txtiDRestaurant1.setText(s.getRestaurant());
        this.txtIdOrder_number1.setText(String.valueOf(s.getOrderNumber()));
        this.txtTotal_price1.setText(String.valueOf(s.getTotalPrice()));    
        this.txtOrder_list_number1.setText(String.valueOf(s.getOrderListNumber())); 
        this.txtOrder_address1.setText(String.valueOf(s.getOrderAddress()));
         
        //ccccccccc
        
    
    }
     */
    
    
    
    //gggggggggggggggggggggggg
    
    @FXML
    private void showView1() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("View1.fxml"));
        try {
            Node view = loader.load();
            contentArea.getChildren().setAll(view);

            // Set the data after loading the view
            txtiDRestaurant1 = (TextField) loader.getNamespace().get("txtiDRestaurant1");
            txtIdOrder_number1 = (TextField) loader.getNamespace().get("txtIdOrder_number1");
            txtTotal_price1 = (TextField) loader.getNamespace().get("txtTotal_price1");
            txtOrder_list_number1 = (TextField) loader.getNamespace().get("txtOrder_list_number1");
            txtOrder_address1 = (TextField) loader.getNamespace().get("txtOrder_address1");

            //שלב מקדים לפני טענית הערכים לשדות
            
            txtiDRestaurant1.setText(s.getRestaurant());
            txtIdOrder_number1.setText(String.valueOf(s.getOrderNumber()));
            txtTotal_price1.setText(String.valueOf(s.getTotalPrice()));
            txtOrder_list_number1.setText(String.valueOf(s.getOrderListNumber()));
            txtOrder_address1.setText(String.valueOf(s.getOrderAddress()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //gggggggggggggggggggggggggg
    
    
    
    
    @FXML
    private void showView2() 
    {
        loadView("View2.fxml");
    }
    
    @FXML
    private void showView3() 
    {
        loadView("View3.fxml");
    }

    private void loadView(String fxmlFile) 
    {
        try 
        {
           // Node view = FXMLLoader.load(getClass().getResource(fxmlFile));
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Node view = loader.load();
        	
            
            contentArea.getChildren().setAll(view);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
    
    //aaaaaaaaaaaaaaaaa
    
    
    
    
    
    
//    @FXML
//    private Button btnSave;
//    
//    @FXML
//    ImageView biteMeImage;
//    
//    Image biteMeLogo = new Image(getClass().getResourceAsStream("C:\\Users\\kfira\\Desktop\\images\\wooden-buttons-ui-game\4Z_2101.w017.n001.353B.p15.353.jpg"));
//    public void displayImage()
//    {
//    	biteMeImage.setImage(biteMeLogo);
//    }
    
    /*
    @FXML
    private ComboBox<String> cmbFaculty;    
    */
    
    ObservableList<String> list;
        
    
    
    
    //יש טעות כי דרסתי את הפקולטה עם מידע על לקוח
    public void loadOrder(Order s1) {
        this.s = s1;
        
        
        //hhhhhhhhhhhhhhhhh
        /*
        this.txtiDRestaurant.setText(s.getRestaurant());
        this.txtIdOrder_number.setText(String.valueOf(s.getOrderNumber()));
        this.txtTotal_price.setText(String.valueOf(s.getTotalPrice()));    
        this.txtOrder_list_number.setText(String.valueOf(s.getOrderListNumber())); 
        this.txtOrder_address.setText(String.valueOf(s.getOrderAddress())); 
       */
       //hhhhhhhhhhhhhhhhhhhhhhhhh
       
        //this.cmbFaculty.setValue(s.getClientInfo().getHost());
    }
    
    
    
    
  
    
    @FXML
    private void CloseButton(ActionEvent event) {
        try {
         // FXMLLoader loader = new FXMLLoader(getClass().getResource("SummaryOfExistingOrder.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LogInClient.fxml"));

            Parent root = loader.load();

            Scene scene = new Scene(root);
    		//scene.getStylesheets().add(getClass().getResource("/gui/SummaryOfExistingOrder.css").toExternalForm());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Summary of an Existing Order");

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    @FXML
    private void SaveButton(ActionEvent event) {
    	List<String> list = new ArrayList<>();
		//list.add(s.getRestaurant());// old id
    	
		list.add(txtiDRestaurant.getText().toString());// new id
		list.add(txtIdOrder_number.getText().toString());
		list.add(txtTotal_price.getText().toString());
		list.add(txtOrder_list_number.getText().toString());
		list.add(txtOrder_address.getText().toString());
		//list.add(((ComboBox)cmbFaculty).getValue().toString());
		System.out.println("the Order "+list.toString());
		
		
		System.out.println("lang txtTotal_price  "+txtTotal_price.getText().length());

		
		if( !(  txtTotal_price.getText().matches("\\d+(\\.\\d+)?") ) || txtTotal_price.getText().length()>7 )// אפשר להכניס מחיר כולל בצורה תקינה עד 7 ספרות,אחרת זה משתבש
		 {

			
			lbOrder_updated.setVisible(false);// הודעה של הזמנה עודכנה להסיר מהמסך
			
			System.out.println("NOTTTTT NUMBER!!!!!!!!!!");
			lbOnly_numbers.setText("Numbers up to length 7");
			lbOnly_numbers.setVisible(true);

	     }
		else
		{
			lbOnly_numbers.setVisible(false);// הודעה של טעות להסיר מהמסך
			
			lbOrder_updated.setText("Order updated");
			lbOrder_updated.setVisible(true);
			
			
			ClientUI.chat.accept(list);
			System.out.println("111Update Order sent to server");
		}
		
		
		//ClientUI.chat.accept(list);
		//System.out.println("111Update Order sent to server");
    }
    
    /*
      	this.txtiDRestaurant.setText(s.getRestaurant());
        this.txtIdOrder_number.setText(String.valueOf(s.getOrderNumber()));
        this.txtTotal_price.setText(String.valueOf(s.getTotalPrice()));    
        this.txtOrder_list_number.setText(String.valueOf(s.getOrderListNumber())); 
        this.txtOrder_address.setText(String.valueOf(s.getOrderAddress())); 
     */
    
    
    /*
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {    
        setFacultyComboBox();        
    }
    */
    
    
    
}
