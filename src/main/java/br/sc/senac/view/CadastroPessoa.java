package br.sc.senac.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.JFormattedTextField;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import br.sc.senac.controller.PessoaController;
import br.sc.senac.model.vo.Instituicao;
import br.sc.senac.model.vo.Pesquisador;

public class CadastroPessoa extends JFrame {

	private JPanel contentPane;
	private JTextField txtNome;
	private final ButtonGroup GrupoSexo = new ButtonGroup();
	private final ButtonGroup GrupoCategoria = new ButtonGroup();
	private JFormattedTextField formattedTextFieldTelefone;
	private JFormattedTextField formattedTextFieldCpf;
	private DatePicker dataNascimento;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroPessoa frame = new CadastroPessoa();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CadastroPessoa() {

		setTitle("Cadastro de pessoa");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 454, 374);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNome.setBounds(28, 24, 48, 14);
		contentPane.add(lblNome);
		
		JLabel lblSobrenome = new JLabel("CPF:");
		lblSobrenome.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSobrenome.setBounds(28, 61, 120, 14);
		contentPane.add(lblSobrenome);
		
		txtNome = new JTextField();
		txtNome.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtNome.setBounds(154, 22, 251, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblDataDeNascimento = new JLabel("Data de nascimento:");
		lblDataDeNascimento.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDataDeNascimento.setBounds(28, 99, 101, 14);
		contentPane.add(lblDataDeNascimento);
		
		JLabel lblSexo = new JLabel("Sexo:");
		lblSexo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSexo.setBounds(28, 137, 48, 14);
		contentPane.add(lblSexo);
		
		JRadioButton rdbtnMasculino = new JRadioButton("Masculino");
		rdbtnMasculino.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GrupoSexo.add(rdbtnMasculino);
		rdbtnMasculino.setBounds(96, 133, 109, 23);
		contentPane.add(rdbtnMasculino);
		
		JRadioButton rdbtnFeminino = new JRadioButton("Feminino");
		rdbtnFeminino.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GrupoSexo.add(rdbtnFeminino);
		rdbtnFeminino.setBounds(207, 133, 109, 23);
		contentPane.add(rdbtnFeminino);
		
		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTelefone.setBounds(28, 173, 48, 14);
		contentPane.add(lblTelefone);
		
		JLabel lblCategoria = new JLabel("Categoria:");
		lblCategoria.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCategoria.setBounds(28, 211, 60, 14);
		contentPane.add(lblCategoria);
		
		JRadioButton rdbtnPblicoGeral = new JRadioButton("Público geral");
		rdbtnPblicoGeral.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GrupoCategoria.add(rdbtnPblicoGeral);
		rdbtnPblicoGeral.setBounds(96, 207, 89, 23);
		contentPane.add(rdbtnPblicoGeral);
		
		JRadioButton rdbtnVoluntrio = new JRadioButton("Voluntário");
		rdbtnVoluntrio.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GrupoCategoria.add(rdbtnVoluntrio);
		rdbtnVoluntrio.setBounds(207, 207, 77, 23);
		contentPane.add(rdbtnVoluntrio);
		
		JRadioButton rdbtnPesquisador = new JRadioButton("Pesquisador");
		rdbtnPesquisador.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GrupoCategoria.add(rdbtnPesquisador);
		rdbtnPesquisador.setBounds(316, 207, 89, 23);
		contentPane.add(rdbtnPesquisador);
		
		JLabel lblInstituicao = new JLabel("Instituição:");
		lblInstituicao.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblInstituicao.setEnabled(false);
		lblInstituicao.setBounds(28, 249, 77, 14);
		contentPane.add(lblInstituicao);
		
		ArrayList<Instituicao> intituicoes = obterInstituicoes();
		JComboBox comboBoxIntituicao = new JComboBox();
		comboBoxIntituicao.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBoxIntituicao.setEnabled(false);
		comboBoxIntituicao.setBounds(154, 245, 251, 22);
		contentPane.add(comboBoxIntituicao);
				
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCancelar.setBounds(260, 293, 89, 23);
		contentPane.add(btnCancelar);
			
		try {
			MaskFormatter mascaraCpf = new MaskFormatter("###-###-###-##");
			MaskFormatter mascaraTelefone = new MaskFormatter("(##) ##### ####");
			
			formattedTextFieldCpf = new JFormattedTextField(mascaraCpf);
			formattedTextFieldCpf.setBounds(154, 58, 251, 20);
			contentPane.add(formattedTextFieldCpf);
			
			formattedTextFieldTelefone = new JFormattedTextField(mascaraTelefone);
			formattedTextFieldTelefone.setBounds(154, 170, 251, 20);
			contentPane.add(formattedTextFieldTelefone);
			contentPane.add(lblSexo);
			contentPane.add(comboBoxIntituicao);
				
			DatePickerSettings dateSettings = new DatePickerSettings();
			dateSettings.setAllowKeyboardEditing(false);
			dataNascimento = new DatePicker(dateSettings);
			dataNascimento.setBounds(154, 96, 251, 20);
			contentPane.add(dataNascimento);
	
		}catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro no sistema, entre em contato com o administrador.");
			System.out.println("Causa da exceção: " + e.getMessage());
		}
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Preencher um objeto PESQUISADOR com os dados da tela
				Pesquisador novoPesquisador = new Pesquisador();
				novoPesquisador.setNome(txtNome.getText());
				novoPesquisador.setCpf(formattedTextFieldCpf.getText());
				novoPesquisador.setDataNascimento(dataNascimento.getDate()); 

				novoPesquisador.setSexo(lblSexo.getText()); // dúvida pegar sexo
				novoPesquisador.setInstituicao(comboBoxIntituicao.setEnabled(true).getText()); // dúvida pegar instituicao
							
				//Instanciar um controller adequado
				PessoaController pesquisadorController = new PessoaController(); 
				
				//Chamar o método SALVAR no controller e pegar a mensagem retornada
				String mensagem = pesquisadorController.salvar(novoPesquisador);
				
				//Mostra a mensagem devolvida pelo controller
				JOptionPane.showMessageDialog(contentPane, mensagem);
			}
		});
		
		btnSalvar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSalvar.setBounds(84, 293, 89, 23);
		contentPane.add(btnSalvar);
		
	}
}
