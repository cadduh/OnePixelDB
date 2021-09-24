import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;


public class testGUI extends JFrame{
	
	private JLabel label1, label2, label3, label4,label5,label6,label7,label8,label9;
	private JButton btGravar, btExcluir, btAlterar,btNovo,btLocalizar,btCancelar,btSair;
	private JTextField tfId, tfNome, tfGenero, tfCheckPoint,tfPixelR,tfPixelG,tfPixelb,tfAliado1,tfAliado2;
	private onePixelDAO f;

	
	public static void main(String args[]) {
		JFrame frame = new testGUI();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public testGUI(){
		inicializarComponentes();
		definirEventos();
	}
	
	public void inicializarComponentes(){
		setLayout(new FlowLayout(FlowLayout.LEFT));
		label1 = new JLabel("ID");
		label2 = new JLabel("Nome");
		label3 = new JLabel("Gênero");
		label4 = new JLabel("CheckPoint");
		label5 = new JLabel("Pixel R");
		label6 = new JLabel("Pixel G");
		label7 = new JLabel("Pixel B");
		label8 = new JLabel("Aliado 1");
		label9 = new JLabel("Aliado 2");
		
		
		tfId = new JTextField(10);
		tfNome = new JTextField(35);
		tfGenero = new JTextField(15);
		tfCheckPoint = new JTextField(10);
		tfPixelR = new JTextField(10);
		tfPixelG = new JTextField(10);
		tfPixelb = new JTextField(10);
		tfAliado1 = new JTextField(10);
		tfAliado2 = new JTextField(10);
		btGravar = new JButton("Gravar");
		btExcluir = new JButton("Excluir");
		btAlterar = new JButton("Alterar");
		btNovo = new JButton("Novo");
		btLocalizar = new JButton("Localizar");
		btCancelar= new JButton("Cancelar");
		btSair = new JButton("Sair");
		add(label1);
		add(tfId);
		add(label2);
		add(tfNome);
		add(label3);
		add(tfGenero);
		add(label4);
		add(tfCheckPoint);
		add(label5);
		add(tfPixelR);
		add(label6);
		add(tfPixelG);
		add(label7);
		add(tfPixelb);
		add(label8);
		add(tfAliado1);
		add(label9);
		add(tfAliado2);
	
		add(btAlterar);
		add(btLocalizar);
		add(btExcluir);
		add(btGravar);
		add(btNovo);
		add(btSair);
		add(btSair);
		setTitle("GUI TEST DO DB");
		setBounds(200,300,620,150 );
		setResizable(false);
		setBotoes(true,true,false,false,false,false); // método criado para ativar/desativar os botões
		f = new onePixelDAO(); //orientação do objeto da classe filmesMetodos
		if(!f.bd.connection()){ //verificação da conexão com o bd.
			JOptionPane.showMessageDialog(null,"Falha na conexão!");
			System.exit(0);
		}
	}
	
	public void definirEventos() {
		btSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.bd.terminarSessao();
				System.exit(0);
			}
		});
		
		btNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
				setBotoes(false,false,true,false,false,true);
			}
		});
		
		btCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		
		btGravar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tfId.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "O código não pode ser vazio!");
					tfId.requestFocus();
					return;
				}
				if(tfNome.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "O título não pode ser vazio!");
					tfNome.requestFocus();
					return;
				}
				if(tfGenero.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "O gênero não pode ser vazio!");
					tfGenero.requestFocus();	
					return;
				}
				if(tfCheckPoint.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "O ano não pode ser vazio!");
					tfCheckPoint.requestFocus();	
					return;
				}
				
				f.pixel.setName(tfNome.getText());
				f.pixel.setGenero(tfGenero.getText());
				f.pixel.setCheckpoint(tfCheckPoint.getText());
				f.pixel.setPixelR(Integer.valueOf(tfPixelR.getText()));	
				f.pixel.setPixelG(Integer.valueOf(tfPixelG.getText()));
				f.pixel.setPixelB(Integer.valueOf(tfPixelb.getText()));
				f.pixel.setAliado1(Integer.valueOf(tfAliado1.getText()));
				f.pixel.setAliado2(Integer.valueOf(tfAliado2.getText()));
				
				JOptionPane.showMessageDialog(null, f.atualizar(onePixelDAO.INCLUSAO) );
				f.atualizarInventario(onePixelDAO.INCLUSAO);
				limparCampos();
			}
		});
		
		btAlterar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
					
				f.pixel.setId(tfId.getText());
				f.pixel.setName(tfNome.getText());
				f.pixel.setGenero(tfGenero.getText());
				f.pixel.setCheckpoint(tfCheckPoint.getText());			
				f.pixel.setPixelR(Integer.valueOf(tfPixelR.getText()));	
				f.pixel.setPixelG(Integer.valueOf(tfPixelG.getText()));
				f.pixel.setPixelB(Integer.valueOf(tfPixelb.getText()));
				f.pixel.setAliado1(Integer.valueOf(tfAliado1.getText()));
				f.pixel.setAliado2(Integer.valueOf(tfAliado2.getText()));
				
				JOptionPane.showMessageDialog(null,f.atualizar(onePixelDAO.ALTERACAO));
				f.atualizarInventario(onePixelDAO.ALTERACAO);
				limparCampos();
			}
		});
		btExcluir.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				f.pixel.setId(tfId.getText());
				f.buscar();
				int n = JOptionPane.showConfirmDialog(null, f.pixel.getName(),"Excluir o filme?", JOptionPane.YES_OPTION);
				if (n == JOptionPane.YES_OPTION){
					JOptionPane.showMessageDialog(null, f.atualizarInventario(onePixelDAO.EXCLUSAO));
					f.atualizar(onePixelDAO.EXCLUSAO);
					limparCampos();
				}
			}
		});
		
		btLocalizar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				atualizarCampos();				
			}
		});
	}
	public void limparCampos(){
		tfId.setText("");
		tfNome.setText("");
		tfGenero.setText("");
		tfCheckPoint.setText("");
		tfId.requestFocus();
		tfPixelR.setText("");
		tfPixelG.setText("");
		tfPixelb.setText("");
		tfAliado1.setText("");
		tfAliado2.setText("");
		setBotoes(true,true,false,false,false,false);
	}
	public void atualizarCampos(){
			f.pixel.setId(tfId.getText());
			if(f.buscar()){
			tfId.setText(f.pixel.getId());
			tfNome.setText(f.pixel.getName());
			tfGenero.setText(f.pixel.getGenero());
			tfCheckPoint.setText(f.pixel.getCheckpoint());
			tfPixelR.setText(String.valueOf(f.pixel.getPixelR()));
			tfPixelG.setText(String.valueOf(f.pixel.getPixelG()));
			tfPixelb.setText(String.valueOf(f.pixel.getPixelB()));
			tfAliado1.setText(String.valueOf(f.pixel.getAliado1()));
			tfAliado2.setText(String.valueOf(f.pixel.getAliado2()));
			setBotoes(true,true,false,true,true,true);
			
		}else{
			JOptionPane.showMessageDialog(null, "Filme não encontrado");
			limparCampos();
		}
	}
	public void setBotoes(boolean bNovo, boolean bLocalizar, boolean bGravar, boolean bAlterar, boolean bExcluir, boolean bCancelar){
		btNovo.setEnabled(bNovo);
		btLocalizar.setEnabled(bLocalizar);
		btGravar.setEnabled(bGravar);
		btAlterar.setEnabled(bAlterar);
		btExcluir.setEnabled(bExcluir);
		btCancelar.setEnabled(bCancelar);
	}
}
