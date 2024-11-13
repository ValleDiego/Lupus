package it.polito.tdp.lupus;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.TreeMap;

import it.polito.tdp.lupus.model.Model;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLController<T>{
	
	private Model model;
	

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAggiunto"
    private TextField txtAggiunto; // Value injected by FXMLLoader

    @FXML // fx:id="txtRimosso"
    private TextField txtRimosso; // Value injected by FXMLLoader

    @FXML // fx:id="tblGiocatori"
    private TableView<Giocatore> tblGiocatori; // Value injected by FXMLLoader

    @FXML // fx:id="clnGiocatore"
    private TableColumn<Giocatore, String> clnGiocatore; // Value injected by FXMLLoader

    @FXML // fx:id="clnRuolo"
    private TableColumn<Giocatore, String> clnRuolo; // Value injected by FXMLLoader

    @FXML // fx:id="clnSalute"
    private TableColumn<Giocatore, String> clnSalute; // Value injected by FXMLLoader

    @FXML // fx:id="txtInput"
    private TextField txtInput; // Value injected by FXMLLoader
    
    @FXML // fx:id="txtOutput"
    private TextArea txtOutput; // Value injected by FXMLLoader
    
    @FXML // fx:id="btnAggiungi"
    private Button btnAggiungi; // Value injected by FXMLLoader
    
    @FXML // fx:id="btnRimuovi"
    private Button btnRimuovi; // Value injected by FXMLLoader
    
    @FXML // fx:id="btnStartGame"
    private Button btnStartGame; // Value injected by FXMLLoader
    
    @FXML // fx:id="btnAvanti"
    private Button btnAvanti; // Value injected by FXMLLoader
    
    
    
    private String datiUtente = "";
    private LinkedList<T> list  = new LinkedList<T>();
    
    private List<String> lNomi = new ArrayList<>();
    
    private TreeMap<String, Giocatore> mGiocatori;
    
    int cont = 0;

    @FXML
    void doAggiungi(ActionEvent event) {
    	String nome = txtAggiunto.getText();
    	txtAggiunto.clear();
    	if(nome == null) {
    		txtOutput.appendText("\nInserisci un nome\n");
    		return;
    	}
    	cont++;
    	lNomi.add(nome);
    	txtOutput.appendText(nome + " è stato aggiunto!\nCi sono "+cont+" giocatori!\n");
    	if(cont>=6) {
    		btnStartGame.setDisable(false);
    	}
    }
    
    

  //  @SuppressWarnings("unchecked")
	@FXML
    void doInput(ActionEvent event) {
    	//Learn in the future
    	
    	list.push((T) txtInput.getText());
    }
    
    
    @FXML
    void doRimuovi(ActionEvent event) {
    	String nome = txtRimosso.getText();
    	if(lNomi.contains(nome)) {
    		lNomi.remove(nome);
    		cont--;
    		txtOutput.appendText(nome + "è stato rimosso!\nCi sono "+cont+" giocatori!");
    		if(cont<6) {
    			btnStartGame.setDisable(true);
    		}
    	}
    	txtRimosso.clear();
    }
    
    public synchronized T pop() {
        try {
          while(list.isEmpty())
            wait();

        } catch(InterruptedException ex) {}
    	return list.removeFirst();//Estrazione del primo elemento
      }
    
    public synchronized void push(T element) {
        list.addLast(element);//Inserimento in testa
        notify();
      }
    
    class MyThread extends Thread {
    	  //Override
    	  public void run() {
    	    //flusso di esecuzione
    		  try {
    		      while(true) {
    		        System.out.println("Aspetto i comandi");
    		        String comando = (String) list.pop();   //Metodo bloccante in caso la coda è vuota
    		        System.out.println("trovato il comando"+comando);
    		        
//    		        Thread.sleep(cl.getDurataOperazione());//Mi fermo per emulare lo svolgimento di un operazione
//    		        System.out.println("Il cliente "+cl.id+" e' stato servito");
    		        
    		        
    		        
    		      }
    		    }catch(Exception ex) {
    		  }
    	  }
    	}
    
    
    @FXML
    void doStartGame(ActionEvent event) throws InterruptedException{
    	//Fase di assegnazione dei ruoli
    	txtOutput.clear();
    	txtOutput.setDisable(false);
    	txtAggiunto.setDisable(true);
        txtRimosso.setDisable(true);
        btnAggiungi.setDisable(true);
        btnRimuovi.setDisable(true);
        btnStartGame.setDisable(true);
        txtInput.setDisable(false);
        
    	mGiocatori = new TreeMap<>();
    	assegnaRuoli();
    	
    	tblGiocatori.setItems(FXCollections.observableArrayList(mGiocatori.values()));

    	
		//Inizio del gioco
    	 
    	txtOutput.appendText("(Il gioco comincia! Caro Master, leggi solamente ciò che non è scritto tra parentesi!)\n\n");
    	int turno = 1;
    	
    	
    	
    	
    	do {
    		
    		txtOutput.appendText("(TURNO "+turno+")\n\nè notte e tutti chiudono gli occhi\n");
    		
    		
    		//Salto un po di roba per testing
    		
    		txtOutput.appendText("Il veterano apre gli occhi, e con un cenno mi dice se vuole mettersi in allerta\n");
    		String risposta = "culo";
    		//Inserire l'attesa
   		
    		MyThread th = new MyThread();
    		th.start();
    		
    		
    		
    		
    		//risposta = this.model.getDatiUtente();
    		
    		
    		
    		risposta = datiUtente;
    		
    		txtOutput.appendText(risposta);
    		
    		
    		
    		turno ++;
    	}while(condizioniFineGame());
    	
    	
    	
    }

    private boolean condizioniFineGame() {
		// TODO Auto-generated method stub
    	
    	
		return false;
	}

	private void assegnaRuoli() {
		// TODO Auto-generated method stub
    	List<Ruolo> lRuoli = new ArrayList<>();
    	double r;
    	
    	//Scelgo le classi
    	
    	if(lNomi.size()>=6) {
    		//1
    		lRuoli.add(new Ruolo("Fabbricante di prove", "Lupo"));
    		//2
    		r = Math.random();
    		if(r<0.33) {
    			lRuoli.add(new Ruolo("Boia", "Neutro"));
    		}else if(r<0.66) {
    			lRuoli.add(new Ruolo("Clown", "Neutro"));
    		}else {
    			lRuoli.add(new Ruolo("Sopravvissuto", "Neutro"));
    		}
    		//3
    		r = Math.random();
    		Ruolo controllo = new Ruolo("Escort", "Cittadino");
    		if(r<0.33) {//RIPRISTINARE APPENA ESCORT E CARCERIERE SMETTONO DI ANDARE INSIEME
    			
    			lRuoli.add(controllo);
    		}else if(r<0.66) {
    			lRuoli.add(new Ruolo("Dottore", "Cittadino"));
    		}else {
    			lRuoli.add(new Ruolo("Guardia del corpo", "Cittadino"));
    		}
    		//4
    		r = Math.random();
    		if(r<0.5) {
    			lRuoli.add(new Ruolo("Veggente", "Cittadino"));
    		}else {
    			lRuoli.add(new Ruolo("Medium", "Cittadino"));
    		}
    		//5
    		r = Math.random();
    		if(r<0.5) {
    			lRuoli.add(new Ruolo("Vigilante", "Cittadino"));
    		}else {
    			lRuoli.add(new Ruolo("Veterano", "Cittadino"));
    		}
    		//6
    		r = Math.random();
    		
    		System.out.println("Escort uscita: "+lRuoli.contains(controllo)); //Esce sempre falso, evidentemente controllo non è come l'escort di prima, risolvere con for
    		if(r<0.5 && !(lRuoli.contains(controllo))) {//Escort e cancelliere non usciranno insieme. 
    			lRuoli.add(new Ruolo("Carceriere", "Cittadino"));
    		}else {
    			lRuoli.add(new Ruolo("Investigatore", "Cittadino"));
    		}
    		//7
    		lRuoli.add(new Ruolo("Contadino", "Cittadino"));  
    		//8
    		lRuoli.add(new Ruolo("Contadino", "Cittadino"));
    		
    	}
    	System.out.println("Giocatori: "+ lNomi.size());
    	if(lNomi.size()>=7){
    		//9
    		lRuoli.add(new Ruolo("Capobranco", "Lupo"));
    	}
    	if(lNomi.size()>=8){
    		//10
    		lRuoli.add(new Ruolo("Contadino", "Cittadino"));
    	}
    	
    	//Ora assegno i ruoli ai giocatori
    	
    	
		for(String nome : lNomi) {
			int index = 0;
//			double min = 0.5;
//			double max = 0.5;
//			for(int i=0;i<1000;i++) {
				index = (int) (Math.random()*lRuoli.size());//PROVATO, CON 8 ELEMENTI VA DA 0 A 7
//				if(index>max) {
//					max = index;
//				}else if(index<min) {
//					min = index;
//				}
//			}
//			System.out.println("min = "+min+"\nmax = "+max);
			
			mGiocatori.put(nome, new Giocatore(nome, lRuoli.get(index)));
			lRuoli.remove(index);
		}
		System.out.println("\nSize: "+lRuoli.size());
		//Mi assicuro che i lupi e i neutri siano stati selezionati
		for(Ruolo ru : lRuoli) {
			//System.out.println("\nSize: "+lRuoli.size());
			if(ru.getTeam().equals("Lupo") || ru.getTeam().equals("Neutro")) {
				System.out.println("\nSize: "+lRuoli.size());
				int index = (int) (Math.random()*mGiocatori.size());//PROVATO, CON 6 ELEMENTI VA DA 0 A 5
				int i = 0;
				boolean b = false;
				while(!b) {
				for(String g : mGiocatori.keySet()) {
					//i++;
					if(index == i) {
						if(mGiocatori.get(g).getRuolo().getTeam().equals("Cittadino")) {
							mGiocatori.get(g).setRuolo(ru);
							b = true;
							System.out.println("\nsfiga\n");
							break;
						}else {
							i=0;
							index = (int) (Math.random()*mGiocatori.size());
							System.out.println("\nVA CHE SFIGA\n");
							break;
						}
					}
					i++;
				}
				
			  }
			}
		}
		
		//Assegno i sospetti
		for(String g : mGiocatori.keySet()) {
			if (mGiocatori.get(g).getRuolo().getTeam().equals("Lupo") || mGiocatori.get(g).getRuolo().getRuolo().equals("Boia")) {
				mGiocatori.get(g).setSospetto(true);
			}
		}
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAggiunto != null : "fx:id=\"txtAggiunto\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRimosso != null : "fx:id=\"txtRimosso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tblGiocatori != null : "fx:id=\"tblGiocatori\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clnGiocatore != null : "fx:id=\"clnGiocatore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clnRuolo != null : "fx:id=\"clnRuolo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clnSalute != null : "fx:id=\"clnSalute\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtOutput != null : "fx:id=\"txtOutput\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAggiungi != null : "fx:id=\"btnAggiungi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnRimuovi != null : "fx:id=\"btnRimuovi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnStartGame != null : "fx:id=\"btnStartGame\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAvanti != null : "fx:id=\"btnAvanti\" was not injected: check your FXML file 'Scene.fxml'.";
        
       
        
        clnGiocatore.setCellValueFactory(new PropertyValueFactory<Giocatore, String>("nome"));
        clnRuolo.setCellValueFactory(new PropertyValueFactory<Giocatore, String>("ruolo"));
        clnSalute.setCellValueFactory(new PropertyValueFactory<Giocatore, String>("salute"));
        
        txtAggiunto.setDisable(false);
        txtRimosso.setDisable(false);
        
       
    }



	public void setModel(Model model2) {
		// TODO Auto-generated method stub
		this.model =model2;
	}






	
}
