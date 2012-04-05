package pckServerChat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import pckChat.Mensagem;

public class JanelaServerChat extends JFrame {

	//labels
	public JLabel lbNome;
	public JLabel lbAssunto;
	public JLabel lbMsg;
	public JLabel lbMsgLog;	
	//entrada de texto
	public JTextField tfNome;
	public JTextField tfAssunto;
	public JTextArea taMsg;
	//botões
	public JButton btSend;
	//checkbox
	public JCheckBox chCloseConnection;
	//paineis
	public JPanel pnNorth;
	public JPanel pnSouth;
	
	public JanelaServerChat(){//método construtor
		//configura JFrame
		this.setBounds(0, 0, 600, 400);
		this.setTitle("Aplicação Client2");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);  
		//cria labels
		lbNome = new JLabel("  Nome :");
		lbAssunto = new JLabel("  Assunto:");
		lbMsg = new JLabel("  Mensagem");	
		lbMsgLog = new JLabel("Status de conexão");
		lbMsgLog.setEnabled(false);//desabilita pois é só para leitura	
		//cria componentes de entrada de texto
		tfNome = new JTextField("", 28);
		tfAssunto = new JTextField("",28);	
		taMsg  = new JTextArea("");
		//cria checkbox
		chCloseConnection = new JCheckBox();
		chCloseConnection.setSelected(false);
		chCloseConnection.setText("Fechar conexão após envio");	
		//cria botões
		btSend = new JButton("Send");
		btSend.setToolTipText("Enviar mensagem");
        //cria paineis		
		pnNorth = new JPanel();
		pnSouth = new JPanel();		
		
		//configura o layout do panel para Grid 3x3
		pnNorth.setLayout(new GridLayout(3,3));
		//configura cor de paineis
		pnSouth.setBackground(Color.yellow);	
		
		//adiciona componentes no panel em formato de grid
		//vai adicionando por linha, todas as celulas precisam ter um objeto
		/* vai ficar mais ou menos assim...
		  ----------------------------------------------
		  | lbNome       |  tfNome      |  panel(vazio)|
		  ----------------------------------------------
		  | lbAssunto    |  tfAssunto   |  panel(vazio)|
		  ----------------------------------------------
		  | lbMsg        |  panel(vazio)|  btSend      |
		  ----------------------------------------------
		*/
		pnNorth.add(lbNome);
		pnNorth.add(tfNome);
		pnNorth.add(chCloseConnection);
		
		pnNorth.add(lbAssunto);
		pnNorth.add(tfAssunto);
		pnNorth.add(new JPanel());
		
		pnNorth.add(lbMsg);
		pnNorth.add(new JPanel());
		pnNorth.add(btSend);
	
		pnSouth.add(lbMsgLog);
		
		this.setLayout(new BorderLayout());

		this.add("North", pnNorth);
		this.add("Center", taMsg);
		this.add("South", pnSouth);
		
		//vincula eventos
		btSend.addActionListener(
		        new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
				        ObjectOutputStream output;
				        Mensagem msg = new Mensagem(tfNome.getText(), tfAssunto.getText(), taMsg.getText(), chCloseConnection.isSelected());
				        taMsg.setText("");
                        try{
					        Socket socketCliente = new Socket("127.0.0.1", 6788);
	
					        output = new ObjectOutputStream(socketCliente.getOutputStream());
					        output.flush();
					        output.writeObject(msg);
					        output.flush();
					        output.close();
					        
					        socketCliente.close();
                        }
                        catch(Exception e){
                        	taMsg.setText(e.getMessage());
                        }						
					}
						
		        });
		        
	}
	
	public JLabel getLabelLog(){
		return lbMsgLog;
	}
	
	public JTextArea getTextAreaMsg(){
		return taMsg;
	}	
}
