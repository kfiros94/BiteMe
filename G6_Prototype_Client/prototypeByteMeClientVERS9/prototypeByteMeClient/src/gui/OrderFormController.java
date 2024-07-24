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
    private Button btnClose;
    
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
        this.txtiDRestaurant.setText(s.getRestaurant());
        this.txtIdOrder_number.setText(String.valueOf(s.getOrderNumber()));
        this.txtTotal_price.setText(String.valueOf(s.getTotalPrice()));    
        this.txtOrder_list_number.setText(String.valueOf(s.getOrderListNumber())); 
        this.txtOrder_address.setText(String.valueOf(s.getOrderAddress())); 
        //this.cmbFaculty.setValue(s.getClientInfo().getHost());
    }
    
  
    
    @FXML
    private void CloseButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SummaryOfExistingOrder.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
    		scene.getStylesheets().add(getClass().getResource("/gui/SummaryOfExistingOrder.css").toExternalForm());
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
