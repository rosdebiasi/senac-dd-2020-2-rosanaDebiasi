package br.sc.senac.view;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import br.sc.senac.controller.VacinaController;
import br.sc.senac.model.vo.Pesquisador;
import br.sc.senac.model.vo.Vacina;

public class CadastroVacina extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup GrupoEstagioPesquisa = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroVacina frame = new CadastroVacina();
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
	public CadastroVacina() {
		setTitle("Cadastro de vacina");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPaisDeOrigem = new JLabel("Pa\u00EDs de origem:");
		lblPaisDeOrigem.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPaisDeOrigem.setBounds(25, 26, 87, 14);
		contentPane.add(lblPaisDeOrigem);
		
		JComboBox comboBoxPaisOrigem = new JComboBox();
		comboBoxPaisOrigem.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBoxPaisOrigem.setBounds(165, 22, 238, 22);
		contentPane.add(comboBoxPaisOrigem);
		
		JLabel lblEstgioDaPesquisa = new JLabel("Est\u00E1gio da Pesquisa:");
		lblEstgioDaPesquisa.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEstgioDaPesquisa.setBounds(24, 62, 101, 14);
		contentPane.add(lblEstgioDaPesquisa);
		
		JRadioButton rdbtnInicial = new JRadioButton("1- Inicial");
		rdbtnInicial.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GrupoEstagioPesquisa.add(rdbtnInicial);
		rdbtnInicial.setBounds(42, 83, 78, 23);
		contentPane.add(rdbtnInicial);
		
		JRadioButton rdbtnTestes = new JRadioButton("2- Testes");
		rdbtnTestes.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GrupoEstagioPesquisa.add(rdbtnTestes);
		rdbtnTestes.setBounds(165, 83, 78, 23);
		contentPane.add(rdbtnTestes);
		
		JRadioButton rdbtnAplicacaoEmMassa = new JRadioButton("3- Aplica\u00E7\u00E3o em massa");
		rdbtnAplicacaoEmMassa.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GrupoEstagioPesquisa.add(rdbtnAplicacaoEmMassa);
		rdbtnAplicacaoEmMassa.setBounds(266, 83, 137, 23);
		contentPane.add(rdbtnAplicacaoEmMassa);
		
		JLabel lblInicioPesquisa = new JLabel("In\u00EDcio pesquisa:");
		lblInicioPesquisa.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblInicioPesquisa.setBounds(25, 127, 87, 14);
		contentPane.add(lblInicioPesquisa);
		
		JLabel lblPesquisadorResponsavel = new JLabel("Pesquisador respons\u00E1vel:");
		lblPesquisadorResponsavel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPesquisadorResponsavel.setBounds(25, 164, 124, 14);
		contentPane.add(lblPesquisadorResponsavel);
		
		JComboBox comboBoxPesquisadorResponsavel = new JComboBox();
		comboBoxPesquisadorResponsavel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBoxPesquisadorResponsavel.setBounds(165, 160, 238, 22);
		contentPane.add(comboBoxPesquisadorResponsavel);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCancelar.setBounds(245, 216, 89, 23);
		contentPane.add(btnCancelar);

		DatePickerSettings dateSettings = new DatePickerSettings();
		dateSettings.setAllowKeyboardEditing(false);
		final DatePicker dataInicioPesquisa = new DatePicker(dateSettings);
		dataInicioPesquisa.setBounds(165, 124, 238, 20);
		contentPane.add(dataInicioPesquisa);
		
		JButton btnSalvar = new JButton("Salvar");
		/* fiz isso porque desaparecia o layout da tela
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Preencher um objeto VACINA com os dados da tela
				Vacina novaVacina = new Vacina();
				Pesquisador pesquisador = new Pesquisador();
				novaVacina.setPaisOrigem(paisOrigem);
				novaVacina.setDataInicioPesquisa(dataInicioPesquisa.getDate()); // não sei se está correto
				novaVacina.setEstagioPesquisa(lblEstgioDaPesquisa.getComponents(rdbtnInicial|rdbtnTestes|rdbtnAplicacaoEmMassa)); // não sei se está correto para pegar estágio da pesquisa
				pesquisador.getNome(comboBoxPesquisadorResponsavel.getComponent(n))

				//Instanciar um controller adequado
				VacinaController vacinaController = new VacinaController(); 
				VacinaController vacinaController = new VacinaController(); 
				
				//Chamar o método SALVAR no controller e pegar a mensagem retornada
				String mensagem = vacinaController.salvar(novaVacina);
				
				//Mostra a mensagem devolvida pelo controller
				JOptionPane.showMessageDialog(contentPane, mensagem);
			}
		});
		*/
		
		btnSalvar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSalvar.setBounds(87, 216, 89, 23);
		contentPane.add(btnSalvar);
		
		
	}
	
}
