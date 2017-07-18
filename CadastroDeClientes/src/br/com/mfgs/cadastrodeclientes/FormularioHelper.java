package br.com.mfgs.cadastrodeclientes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import br.com.mfgs.cadastrodeclientes.modelo.Cliente;

public class FormularioHelper {
	
	private EditText campoNome;
	private EditText campoTelefone;
	private EditText campoEndereco;
	private EditText campoEmail;
	private RatingBar campoNota;
	private Cliente cliente;
	private ImageView foto;

		
	

	public FormularioHelper(FormularioActivity activity) {
		cliente = new Cliente();
		
		campoNome = (EditText) activity.findViewById(R.id.nome);
		campoTelefone = (EditText) activity.findViewById(R.id.telefone);
		campoEndereco = (EditText) activity.findViewById(R.id.endereco);
		campoEmail = (EditText) activity.findViewById(R.id.email);
		campoNota = (RatingBar) activity.findViewById(R.id.nota);
		foto = (ImageView) activity.findViewById(R.id.foto);
	}
	
	public Cliente pegaClienteFormulario(){
		
		String nome = campoNome.getText().toString();
		String telefone = campoTelefone.getText().toString();
		String endereco = campoEndereco.getText().toString();
		String email = campoEmail.getText().toString();
		double nota = campoNota.getProgress();
		
		cliente.setNome(nome);
		cliente.setTelefone(telefone);
		cliente.setEndereco(endereco);
		cliente.setEmail(email);
		cliente.setNota(Double.valueOf(nota));
		
		
		return cliente;
			
	}

	public void colocaClienteFormulario(Cliente clienteParaAlterar) {
		cliente = clienteParaAlterar;
		
		campoNome.setText(clienteParaAlterar.getNome());
		campoTelefone.setText(clienteParaAlterar.getTelefone());
		campoEndereco.setText(clienteParaAlterar.getEndereco());
		campoEmail.setText(clienteParaAlterar.getEmail());
		campoNota.setRating(clienteParaAlterar.getNota().floatValue());
		
		if (cliente.getCaminhoFoto() != null){
			carregaImagem(cliente.getCaminhoFoto());
		}
	}
	public ImageView getFoto(){
		return foto;
	}

	public void carregaImagem(String caminhoArquivo) {
		cliente.setCaminhoFoto(caminhoArquivo);
		
		Bitmap imagem = BitmapFactory.decodeFile(caminhoArquivo);
		Bitmap imagemReduzida = Bitmap.createScaledBitmap(imagem, 100, 100, true);
		
		foto.setImageBitmap(imagemReduzida);
		
	}
}
