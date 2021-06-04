package com.keepcall;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class PushFormJavaSwing {

	private JFrame frame;
 

	

	/**
	 * Create the application.
	 */
	public PushFormJavaSwing(String strhapikey, String strPathTmpDonePostTask,String strPathTmpDoneImport ) {
		File fileDonePost = new File(strPathTmpDonePostTask);
		initialize(strhapikey,fileDonePost,strPathTmpDoneImport);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String strhapikey,  File fileDonePost,String strPathTmpDoneImport ) {
		frame = new JFrame();
        frame = new JFrame("Creation de Tache"); 
		frame.setBounds(100, 100, 730, 489);
		frame.getContentPane().setLayout(null);
	
		HashMap<String, List<String>> mapIdsString = Utility.getDataFromDoneFile(strPathTmpDoneImport);

		// TITRE
		JLabel lblName = new JLabel("Titre");
		lblName.setBounds(65, 31, 46, 14);
		frame.getContentPane().add(lblName);
		
		JComboBox<String> comboBoxtitre = new JComboBox<String>();
		comboBoxtitre.addItem("PREMIER APPEL VOO");
		comboBoxtitre.addItem("PREMIER APPEL RESA");
		comboBoxtitre.addItem("PREMIER APPEL CHC");
		comboBoxtitre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		comboBoxtitre.setBounds(200, 28, 200, 20);
		frame.getContentPane().add(comboBoxtitre);
		
		
		//Status
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(65, 68, 46, 14);
		frame.getContentPane().add(lblStatus);
		
		
		JLabel lblCompleted = new JLabel("Completed");
		lblCompleted.setBounds(200, 68, 100, 14);
		frame.getContentPane().add(lblCompleted);
		
		JRadioButton radioButonCompleted = new JRadioButton("");
		radioButonCompleted.setBounds(278, 68, 20, 23);
		frame.getContentPane().add(radioButonCompleted);
		
		JLabel lblNotCompleted = new JLabel("NOT Completed");
		lblNotCompleted.setBounds(330, 68, 120, 14);
		frame.getContentPane().add(lblNotCompleted);
		
		JRadioButton radioButon = new JRadioButton("",true);
		radioButon.setBounds(430, 68, 109, 23);
		frame.getContentPane().add(radioButon);
		
		
		
		
		//Echeance Date
		JLabel lblEchanceDate = new JLabel("Echeance Date");
		lblEchanceDate.setBounds(65, 115, 100, 14);
		frame.getContentPane().add(lblEchanceDate);
		
		
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		JDatePickerImpl datePickerEcheance = new JDatePickerImpl(datePanel, new DateLabelFormatter());

		datePickerEcheance.setBounds(200, 112, 247, 50);
		frame.add(datePickerEcheance);

		
		
		//Reception Date
		JLabel lblReceptionDate = new JLabel("Reception Date");
		lblReceptionDate.setBounds(65, 165, 100, 14);
		frame.getContentPane().add(lblReceptionDate);
		
		
		UtilDateModel model1 = new UtilDateModel();
		JDatePanelImpl datePanel1 = new JDatePanelImpl(model1);
		JDatePickerImpl datePickerReception = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
		datePickerReception.setBounds(200, 165, 247, 50);
		frame.add(datePickerReception);
		

		
		
		JButton btnClear = new JButton("Clear");
		
		btnClear.setBounds(312, 387, 89, 23);
		frame.getContentPane().add(btnClear);
		
		
		
		
		JButton btnSubmit = new JButton("submit");
		
		btnSubmit.setBackground(Color.BLUE);
		btnSubmit.setForeground(Color.MAGENTA);
		btnSubmit.setBounds(65, 387, 89, 23);
		frame.getContentPane().add(btnSubmit);
		
		
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Date d = (Date) datePickerReception.getModel().getValue();
				Date d2 = (Date) datePickerEcheance.getModel().getValue();
				
				if (d2  == null) {
					JOptionPane.showMessageDialog(null, "Data Missing");
				}
				else {
				
				
					//Create file done if not exist
					if(!fileDonePost.exists())
						Utility.createFileDone(fileDonePost);
					
					String status;
					if(radioButonCompleted.isSelected())
						status ="COMPLETED";
					else
						status ="NOT_STARTED";
					

					String receptionDate;
					Long longEcheanceDate;
	
					DateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yy");  
					DateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
					DateFormat dateFormat3 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	
					if (d  == null) 
						receptionDate = Utility.GetCurrentDate("dd/MM/yy");
					else 
						receptionDate = dateFormat1.format(d); 

					String strdateWithoutTime = dateFormat2.format(d2);
					Date echeanceDate= new Date();
					try {
						echeanceDate = dateFormat3.parse(strdateWithoutTime+ " 08:00:00");
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					longEcheanceDate = echeanceDate.getTime();
					

					String type = "TASK";
					
							
	//				if(textField.getText().isEmpty()||(textField_1.getText().isEmpty())||(textField_2.getText().isEmpty()))
	//					JOptionPane.showMessageDialog(null, "Data Missing");
	//				else		
	//				JOptionPane.showMessageDialog(null, "Data Submitted");
			    	System.out.println("*************************************************************");
			    	System.out.println("******************* PUSH TASKS START ************************");
			    	System.out.println("*************************************************************\n");
				    for (Map.Entry<String, List<String>> entry : mapIdsString.entrySet()) {
				    	String titre="";
				    	String body="";
				    	String contactId = entry.getValue().get(0);
				    	String ref = entry.getValue().get(1);
				    	String ownerId = entry.getValue().get(2);
				    	String tel1 ="";
				    	String tel2 ="";
				    	String tel3 ="";
				    	
						for(int i = 3; i<entry.getValue().size();i++) {
							if(i==3)
								tel1=entry.getValue().get(i);
							if(i==4)
								tel2=entry.getValue().get(i);
							if(i==5)
								tel3=entry.getValue().get(i);
						}				    		
				    	
						String tels=tel1+tel2+tel3;
						boolean isNotRech=false;
		                if(tels.equals("")||tels.equals("#")) {
		                	titre ="CCENTER/RECH "+receptionDate ;
		                	body ="Pas de numero sur la fiche";	
		            		status = "COMPLETED";
		                }
		                else {
		                	isNotRech=true;
							titre = comboBoxtitre.getSelectedItem().toString()+" "+receptionDate;
							body = "MD envoye dans les environs du "+receptionDate;
		            		status = "NOT_STARTED";
		                }
		                System.out.println("ContactID : "+contactId);
		                System.out.println("Ref : "+ref);
		                System.out.println("OwnerID : "+ownerId);
		                System.out.println("titre : "+titre);
		                System.out.println("description : "+body);
		                System.out.println("STATUS : "+status);
		                System.out.println("Echance Date : "+ strdateWithoutTime+ " 08:00:00" );	
		                
		                if(Utility.searchIfDone(entry.getKey(), fileDonePost))continue;
		                String output="";
		                
		                if(isNotRech)
		                 output= HubRequests.Post_Task( ownerId,longEcheanceDate,  type, contactId,
								 body,  titre,  status, strhapikey) ;

		                System.out.println("..................SUCCESS\n" );	
		                
		                Utility.writeDone(fileDonePost, contactId, ref, ownerId,tel1,tel2,tel3);
//		                (File tmpDone, String contactId, String refToBeImported, String owner, String tel1, String tel2, String tel3) 

				    }
			    	System.out.println("*************************************************************");
			    	System.out.println("******************* PUSH TASKS END **************************");
			    	System.out.println("*************************************************************\n");
				}
				
				Utility.DeleteFile(fileDonePost);
				Utility.DeleteFile(new File(strPathTmpDoneImport));
			}
		});
		
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				textField_1.setText(null);
//				textField_2.setText(null);
//				textField.setText(null);
				
			}
		});
		frame.setVisible(true);
	}
}
