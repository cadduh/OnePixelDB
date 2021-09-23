import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;


public class testGUI extends JFrame{
	
	private JLabel label1, label2, label3, label4;
	private JButton btGravar, btExcluir, btAlterar,btNovo,btLocalizar,btCancelar,btSair;
	private JTextField tfCodigo, tfTitulo, tfGenero, tfAno;
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
		label1 = new JLabel("Código");
		label2 = new JLabel("Titulo");
		label3 = new JLabel("Gênero");
		label4 = new JLabel("Ano");
		tfCodigo = new JTextField(10);
		tfTitulo = new JTextField(35);
		tfGenero = new JTextField(15);
		tfAno = new JTextField(10);
		btGravar = new JButton("Gravar");
		btExcluir = new JButton("Excluir");
		btAlterar = new JButton("Alterar");
		btNovo = new JButton("Novo");
		btLocalizar = new JButton("Localizar");
		btCancelar= new JButton("Cancelar");
		btSair = new JButton("Sair");
		add(label1);
		add(tfCodigo);
		add(label2);
		add(tfTitulo);
		add(label3);
		add(tfGenero);
		add(label4);
		add(tfAno);
		add(btAlterar);
		add(btLocalizar);
		add(btExcluir);
		add(btGravar);
		add(btNovo);
		add(btSair);
		add(btSair);
		setTitle("Consulta de filmes");
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
				if(tfCodigo.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "O código não pode ser vazio!");
					tfCodigo.requestFocus();
					return;
				}
				if(tfTitulo.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "O título não pode ser vazio!");
					tfTitulo.requestFocus();
					return;
				}
				if(tfGenero.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "O gênero não pode ser vazio!");
					tfGenero.requestFocus();	
					return;
				}
				if(tfAno.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "O ano não pode ser vazio!");
					tfAno.requestFocus();	
					return;
				}
				
				f.pixel.setId(tfCodigo.getText());
				f.pixel.setName(tfTitulo.getText());
				f.pixel.setGenero(tfGenero.getText());
				f.pixel.setCheckpoint(tfAno.getText());
				
				JOptionPane.showMessageDialog(null, f.atualizar(onePixelDAO.INCLUSAO) );
				f.atualizarInventario(onePixelDAO.INCLUSAO);
				limparCampos();
			}
		});
		
		btAlterar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
					
				f.pixel.setId(tfCodigo.getText());
				f.pixel.setPixelR(Integer.valueOf(tfTitulo.getText()));	
				f.pixel.setPixelG(Integer.valueOf(tfGenero.getText()));
				f.pixel.setPixelB(Integer.valueOf(tfAno.getText()));
				JOptionPane.showMessageDialog(null,f.atualizarInventario(onePixelDAO.ALTERACAO));
				limparCampos();
			}
		});
		btExcluir.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				f.pixel.setId(tfCodigo.getText());
				f.buscar();
				int n = JOptionPane.showConfirmDialog(null, f.pixel.getName(),"Excluir o filme?", JOptionPane.YES_OPTION);
				if (n == JOptionPane.YES_OPTION){
					JOptionPane.showMessageDialog(null, f.atualizar(onePixelDAO.EXCLUSAO));
					limparCampos();
				}
			}
		});
		
		btLocalizar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				atualizarCampos();
				System.out.println(f.pixel.getPixelR());
				System.out.println(f.pixel.getPixelG());
				System.out.println(f.pixel.getPixelB());
				System.out.println(f.pixel.getAliado1());
				System.out.println(f.pixel.getAliado2());

				
				
			}
		});
	}
	public void limparCampos(){
		tfCodigo.setText("");
		tfTitulo.setText("");
		tfGenero.setText("");
		tfAno.setText("");
		tfCodigo.requestFocus();
		setBotoes(true,true,false,false,false,false);
	}
	public void atualizarCampos(){
			f.pixel.setId(tfCodigo.getText());
			if(f.buscar()){
			tfCodigo.setText(f.pixel.getId());
			tfTitulo.setText(String.valueOf(f.pixel.getPixelR()));
			tfGenero.setText(String.valueOf(f.pixel.getPixelG()));
			tfAno.setText(String.valueOf(f.pixel.getPixelB()));
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
