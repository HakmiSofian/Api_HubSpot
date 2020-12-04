package com.keepcall;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.w3c.dom.Document;  

public class Utility {

	private static String strurl ;
    private static String strdbuser ; 
    private static String strdbpassword ;
    private static String strPathSearch ;
    private static String strhapikey ;
    private static String strPathTmpDoneImport;
    private static String strPathTmpDonePostTask;
    
	

	
	public static String getIdOperateur(String name,String client ) {
	    String owner ;
	    if(client.equals("enquete")) {
	    	switch(name) {
	        
	        case "Keep Call keepcall":
	        	owner = "40663787";
	          break;
	       
	        case "sofian":
	        	owner = "43237300";
	          break;
	        case "NISRINE ROUIHA":
	        	owner = "49600601";
	          break;
	       
	        default:
	            System.out.println("** ERROR ** ContactID : "+name);
	            owner = "43237300";
	      } 
	    	return owner;
	    }
	    else {
	    
		    switch(name) {
		        case "ibtissam khamlich":
		        	owner = "40944660";
		          break;
//		        case "Adil EL WARARI (Deactivated User)":
//		        	owner = "40944661";
//		          break;
		        case "Fabrice Bruel Oswald Kangbazou":
		        	owner = "40944767";
		          break;
//		        case "Najat Bouaamri":
//		        	owner = "40944769";
//		          break;
		        case "Keep Call keepcall":
		        	owner = "40861304";
		          break;
//		        case "Ayoub BARMAKY":
//		        	owner = "40944667";
//		          break;
		        case "sofian":
		        	owner = "40182795";
		          break;
		        case "Sofian":
		        	owner = "40182795";
		          break;
//		        case "amine tahari (Deactivated User)":
//		    		owner = "41753816";
//		    		break;
//		        case "Chaimaa Naouazili (Deactivated User)":
//		    		owner = "42187184";
//		    		break;
//		        case "yasmine BENNARI":
//		    		owner = "42256876";
//		    		break;
		        case "Oussama Essakhi":
		    		owner = "42493855";
		    		break;
//		        case "Mariam Chehbani":
//		    		owner = "43062804";
//		    		break;
//		        case "DOUNIA BEN-AMAR":
//		    		owner = "43062797";
//		    		break;
//		        case "Houda BENSEFFAJ":
//		    		owner = "44109651";
//		    		break;
//		        case "Mounir Lafkih":
//		    		owner = "44558606";
//		    		break;
		        case "NISRINE ROUIHA":
		    		owner = "44109658";
		    		break;
		        case "Houda Jemel":
		    		owner = "44556041";
		    		break;
		        default:
		            System.out.println("** ERROR ** Owner  : "+name);
	                System.out.println("** ERROR ** Attribue a Keep Call keepcall");
		            owner = "40861304";
		      } 
	     
    	
	    return owner;
	    }
	}
    public static int count_line_file(File file) {
    	int count=0;
    	LineNumberReader reader = null;
    	 try {
    	      reader = new LineNumberReader(new FileReader(file));
    	      // Read file till the end
    	      while ((reader.readLine()) != null);
    	      count=reader.getLineNumber();
    	    } catch (Exception ex) {
    	      ex.printStackTrace();
    	    } finally { 
    	      if(reader != null){
    	        try {
    	          reader.close();
    	        } catch (IOException e) {
    	          // TODO Auto-generated catch block
    	          e.printStackTrace();
    	        }
    	      }
    	    }
    	 return count;
    }
    

	
	
	
	
	
	
	
	
	
//-----------------------------------------------------------------------------------------------------------------	
//-----------------------------------------------------------------------------------------------------------------	
//-----------------------------					My devloppement							---------------------------	
//-----------------------------------------------------------------------------------------------------------------	
//-----------------------------------------------------------------------------------------------------------------	

	
	
	
	
	
	
	
	
	/**
	 * Browse file
	 * 
	 * @return
	 */
	public static void run() {
		readConfigFile();
		JFrame parent;
        parent = new JFrame("Fichier à importer"); 
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel p = new JPanel(); 
        JButton btnImport = new JButton("Import");
        btnImport.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	JFileChooser fileChooser = new JFileChooser();
                		fileChooser.setCurrentDirectory(new File(strPathSearch));//System.getProperty("user.home")));
                		int result = fileChooser.showOpenDialog(p);
                		if (result == JFileChooser.APPROVE_OPTION) {

                			//GET list ref from file xlsx
                			System.out.print("Récupération des référence depuis le fichier");
                			HashMap<String,List<String>> mapRefTel=getRefsFromFileXLSX(fileChooser.getSelectedFile());
                			System.out.println(" ...........  SUCCESS\n\n");


                			
                			//POST REF
                			System.out.println( "***********************************************" );
                			System.out.println( "*************  START POST CONTACTS  ***********" );
                			System.out.println( "***********************************************" );
                			createContacts(mapRefTel);
                			System.out.println( "***********************************************" );
                			System.out.println( "*************  END POST CONTACTS  ***********" );
                			System.out.println( "**********************************************\n\n" );
                			

                			
                			// GET IDs OF REFF from file done
                			System.out.print("get listRefIds : ");
                			HashMap<String, List<String>> MapIds = getDataFromDoneFile(strPathTmpDoneImport);
                			System.out.println(MapIds.size() +" (size) ...........  SUCCESS\n\n");

                			
                			//POST - CREATION LIST
                			System.out.print("Création de la liste depuis le fichier : "+fileChooser.getSelectedFile().getName());
                			String output = HubRequests.CreateListe(fileChooser.getSelectedFile().getName(),strhapikey);
                			System.out.println(" ...........  SUCCESS\n\n");
                			
                			// Get ListID created
                			System.out.print("get listeId : ");
                			String listId = getListId(output);
                			System.out.println(listId+" ...........  SUCCESS\n\n");
                			
                			// ADD refId in LIST created
                			System.out.print( "Ajout des références dans la liste :" +fileChooser.getSelectedFile().getName());
                			output = AddContactsInList(listId,MapIds );
                			System.out.println(" ...........  SUCCESS\n\n");
                			System.out.println(" ...........  SUCCESS\n\n");
                			
                			
//                	        File fileTmpDone = new File(strPathTmpDoneImport);
//                			DeleteFile(fileTmpDone);

                			JOptionPane.showMessageDialog(parent,
                				    "Importation Réussie.");
                			

//                			System.exit(0);
                		}
                    }
                });
        
        JButton btnPush = new JButton("Creation Tache");
        btnPush.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	try {
                			HashMap<String, List<String>> MapIds = getDataFromDoneFile(strPathTmpDoneImport);
        					new PushFormJavaSwing(strhapikey,MapIds,strPathTmpDonePostTask);
        				} catch (Exception e1) {
        					e1.printStackTrace();
        				}
                    }
                });
//        p.setLayout(new BorderLayout());
        p.setLayout(new FlowLayout()); 
        //p.add(b,BorderLayout.CENTER); 
//        p.add(btnImport,BorderLayout.CENTER);
//        p.add(btnPush,BorderLayout.SOUTH);
        p.add(btnImport);
        p.add(btnPush);
        
        parent.add(p); 
        // set the size of the frame 
        parent.setSize(800, 300); 
        parent.setVisible(true); 
	}
	


	protected static String AddContactsInList(String listId, HashMap<String, List<String>> mapIds) {
		String request = "https://api.hubapi.com/contacts/v1/lists/"+ listId+"/add?hapikey="+strhapikey;
		String body ="{\r\n" + 
				"  \"vids\": [\r\n" + 
				"    " ;
		int count=1;
        for(String i : mapIds.keySet() ) {
        	String refId = i;
        	if(count == mapIds.size())
        		body = body + refId ;
        	else
        		body = body + refId+",";
        	count++;
        }
        body.replace(body.substring(body.length()-2), "");
        body = body + "\r\n" + 
				"  ]\r\n" + 
				"}";
        
        String output = HubRequests.postRequest(request,body);
        return output;
	}

	
	
	
	/**
	 * get Id of the created list from the output
	 * @param output
	 * @return
	 */
	private static String getListId(String output) {
		JSONObject obj = new JSONObject(output);
		String strlistId =obj.get("listId").toString();
		

		return strlistId;
	}

	/**
	 * read the import file. get all refs in LIST.
	 * @param file
	 * @return List<String>
	 */
	private static HashMap<String, List<String>> getRefsFromFileXLSX(File file) {
		//obtaining input bytes from a file  
		
		HashMap<String,List<String>> MapRefTel = new HashMap<String,List<String>>();
		
		
		FileInputStream fis;
		XSSFWorkbook  wb = null;
		try {
			fis = new FileInputStream(file);
			wb=new XSSFWorkbook(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Ce fichier n'est pas un fichier .xlsx");
			e.printStackTrace();
		}  
		//creating workbook instance that refers to .xls file  
		//creating a Sheet object to retrieve the object  
		XSSFSheet sheet=wb.getSheetAt(0);  
		//evaluating cell type   
		// Get iterator to all the rows in current sheet
        Iterator<Row> rowIterator = sheet.iterator();
       
        //init rank of the Référence cells
        int rankRefCell = -1;
        int rankTel1 = -1;
        int rankTel2 = -1;
        int rankTel3 = -1;
        
        // Traversing over each row of XLSX file
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            List<String> lTels = new ArrayList<String>();
            // For each row, iterate through each columns
            if(rankRefCell >= 0 && rankTel1 >= 0 && rankTel2 >= 0 && rankTel3 >= 0) {
            	lTels.add(row.getCell(rankTel1).getStringCellValue());
            	lTels.add(row.getCell(rankTel2).getStringCellValue());
            	lTels.add(row.getCell(rankTel3).getStringCellValue());
            	MapRefTel.put(row.getCell(rankRefCell).getStringCellValue(),lTels);

//            	System.out.println("this ref is added : "+row.getCell(rankRefCell).getStringCellValue());
            }else {
	            Iterator<Cell> cellIterator = row.cellIterator();
	            loop: while (cellIterator.hasNext()) {
	
	                Cell cell = cellIterator.next();
	
	                switch (cell.getCellType()) {
		                case Cell.CELL_TYPE_STRING:
		                	if(cell.getStringCellValue().equals("Référence")) 
		                		rankRefCell=cell.getColumnIndex();
		                		//break loop;
		                	if(cell.getStringCellValue().contains("Téléphone 1"))
		                		rankTel1=cell.getColumnIndex();
		                	if(cell.getStringCellValue().contains("Téléphone 2")) 
		                		rankTel2=cell.getColumnIndex();
		                	if(cell.getStringCellValue().contains("Gsm")) 
		                		rankTel3=cell.getColumnIndex();
//		                    System.out.print(cell.getStringCellValue() + "\t");
		                    break;
//		                case Cell.CELL_TYPE_NUMERIC:
//		                    System.out.print(cell.getNumericCellValue() + "\t");
//		                    break;
//		                case Cell.CELL_TYPE_BOOLEAN:
//		                    System.out.print(cell.getBooleanCellValue() + "\t");
//		                    break;
		                default :
//		                	System.out.print("this is not a String value in this cell.\n getCellType() = "+cell.getCellType());
		                    break;
	             
	                }
	            }
//	            System.out.println("");
            }
        }
        
        return MapRefTel;
	}
	
	static List<String> createContacts( HashMap<String, List<String>> mapRefTels) {
		
		File tmpDone =new File(strPathTmpDoneImport);
		
		//Create file done if not exist
		if(!tmpDone.exists())
			createFileDone(tmpDone);
		
		//Generate list of owners
		List<String> owners = GenerateOwners(mapRefTels.size());
		
		List<String> refIds = new ArrayList<String>();
		
		int count= -1;
	    for (Entry<String, List<String>> entry : mapRefTels.entrySet()) {
        	String refToBeImported = entry.getKey();
        	
        	List<String> lTels = entry.getValue();
        	String tel1 = lTels.get(0);
        	String tel2 = lTels.get(1);
        	String tel3 = lTels.get(2);
                
        	
        	
			count++;
			// Test if line done			
			if(searchIfDone(refToBeImported,tmpDone))continue;
				
			//Push in hubspot
			System.out.print("Creatation de la référence : "+ refToBeImported );
			String output= HubRequests.Push_ContactInHubspot(refToBeImported,owners.get(count),strhapikey);
			String contactId = getContactId(output);
			System.out.println(" ...........  SUCCESS");


			
			refIds.add(contactId);
//			Write this reference in the done file
			writeDone(tmpDone,contactId,refToBeImported,owners.get(count),tel1,tel2,tel3);
		}
//		DeleteFile(tmpDone);
		
		return refIds;
	}
	
	
	static void writeDone(File tmpDone, String contactId, String refToBeImported, String owner, String tel1, String tel2, String tel3) {
		// TODO Auto-generated method stub
		FileWriter writer;
		try {
			/*Creer le fichier des lignes déjà faites.*/
			writer = new FileWriter(tmpDone,true);
	        StringBuilder header = new StringBuilder();
	        header.append(contactId+','+refToBeImported+","+owner+","+tel1+","+tel2+","+tel3);
	        header.append('\n');
			
	        writer.write(header.toString());
	        
			if (writer != null) {
				writer.flush();
				writer.close();					
			}
//			System.out.println("$$$ LINE DONE REF : "+refToBeImported);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}


	static void createFileDone(File tmpDone) {
		FileWriter writer;
		try {
			/*Creer le fichier des lignes déjà faites.*/
			writer = new FileWriter(tmpDone,true);
	        StringBuilder header = new StringBuilder();
	        header.append("id,ref,owner,tel1,tel2,tel3");
	        header.append('\n');
			
	        writer.write(header.toString());
	        
			if (writer != null) {
				writer.flush();
				writer.close();					
			}
//			System.out.println("File done created success.");
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
	
	static boolean searchIfDone(String ref,File tmpDone) {
		try {
			   BufferedReader br2 = new BufferedReader(
					    new InputStreamReader(
					        new FileInputStream(tmpDone),
					        "UTF-8"
					        )
					    );
				br2.mark(1);
		        if (br2.read() != 0xFEFF)
		        	br2.reset();
		        String line = "";
		        while ((line = br2.readLine()) != null) {
					String[] splitedLine = line.split(",");
					if(splitedLine[1].equals(ref)) {
						System.out.println("ref already done (exist in tmpFile) :"+ref+ " ........... FAILED.\n"	);
						br2.close();
						return true;
					}
		        }
		        br2.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
	}
	
	private static HashMap<String, List<String>> getDataFromDoneFile(String strPathTmpDoneImport) {
//		List<String> listRefIds = new ArrayList<String>();
		HashMap<String, List<String>> listRefIds = new HashMap<String, List<String>>();

		boolean firstline= true;
		try {
			   BufferedReader br2 = new BufferedReader(
					    new InputStreamReader(
					        new FileInputStream(new File(strPathTmpDoneImport)),
					        "UTF-8"
					        )
					    );
				br2.mark(1);
		        if (br2.read() != 0xFEFF)
		        	br2.reset();
		        String line = "";
		        while ((line = br2.readLine()) != null) {
		        	if(firstline) {
		        		firstline = false;
		        		continue;
		        		}
					String[] splitedLine = line.split(",");
					List<String> lData = new ArrayList<String>();
					
					for (String i : splitedLine)
						lData.add(i);
					
					listRefIds.put(splitedLine[0],lData);
		        }
		        br2.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return listRefIds;
	}
	

	/**
	 * get Id of the contact from the output
	 * @param output
	 * @return
	 */
	private static String getContactId(String output) {
		JSONObject obj = new JSONObject(output);
		String strcontactId =obj.get("vid").toString();
		return strcontactId;
	}

	private static List<String> GenerateOwners(int size){
		List<String> res = new ArrayList<String>();
		List<String> owners = getOwnersActive();
		
		int ownersSize = owners.size();
		
		for(int i=0 ; i<size;i++) {
			res.add(owners.get(i % ownersSize));
		}
		return res;
	}

	private static List<String> getOwnersActive(){
		List<String> result = new ArrayList<String>();
		try {
        	Connection con = DriverManager.getConnection(strurl, strdbuser, strdbpassword);
        	Statement statement = con.createStatement();
        	ResultSet rs = statement.executeQuery("select ownerid from hubspot_orwners where isactive is true;");
        	while(rs.next()) {
        		result.add(rs.getString("ownerid"));
    		}
        	con.close();

        } catch (SQLException ex) {
        	System.out.println("Login Connection failure.");
            ex.printStackTrace();
        }
		return result;
	}
	
    public static void readConfigFile() {

   	 try {

	 	        File fXmlFile = new File("c:\\config.xml");
	 	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	 	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	 	        Document doc = dBuilder.parse(fXmlFile);
	
	 	       //optional, but recommended
	 	       //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	 	        doc.getDocumentElement().normalize();
		
	 	        strurl = doc.getElementsByTagName("DriverUrl").item(0).getTextContent();
	 	        strdbuser = doc.getElementsByTagName("DriverUser").item(0).getTextContent();
	 	        strdbpassword = doc.getElementsByTagName("DriverPassword").item(0).getTextContent();
	//	 	        strPathOut = doc.getElementsByTagName("PathOutLog").item(0).getTextContent();
	//	 	        strPathErr = doc.getElementsByTagName("PathErrLog").item(0).getTextContent();
	 	        strhapikey = doc.getElementsByTagName("Hapikey").item(0).getTextContent();
	 	        strPathTmpDoneImport = doc.getElementsByTagName("PathTmpDoneImport").item(0).getTextContent();
	 	        strPathSearch = doc.getElementsByTagName("PathSearch").item(0).getTextContent();
	 	       strPathTmpDonePostTask = doc.getElementsByTagName("PathTmpDonePostTask").item(0).getTextContent();
	 	      
	 	       
	        } catch (Exception e) {
	        e.printStackTrace();
	        }
   	
   	
   }
    
    /**
     * Clean temporary file used
     * 
     * @param fileExportDone
     */
	static void DeleteFile(File fileExportDone) {
        if(fileExportDone.delete()) //returns Boolean value 
        	System.out.println(fileExportDone.getName() + " deleted");   //getting and printing the file name  
        else  
        	System.out.println(fileExportDone.getName() +" failed");  
	}
	
	static String GetCurrentDate(String format) {
	//GET CURENT DATE
	   DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);  
	   LocalDateTime now = LocalDateTime.now();  
	   //System.out.println(dtf.format(now));  
	   return  dtf.format(now);
	}
	public static boolean isRecherche(List<String> value) {
		// TODO Auto-generated method stub
		String str="";
		for(int i = 3; i<value.size();i++) {
			str=str+value.get(i);
		}
		if(str.equals(""))
			return true;
		
		return false;
	}
}
