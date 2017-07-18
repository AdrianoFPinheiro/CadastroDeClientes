package br.com.mfgs.cadastrodeclientes;

import java.io.File;
import java.io.Serializable;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import br.com.mfgs.cadastrodeclientes.dao.ClienteDAO;
import br.com.mfgs.cadastrodeclientes.modelo.Cliente;

public class FormularioActivity extends Activity{
	
	private FormularioHelper helper;
	private String caminhoArquivo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);
		
		final Button botao = (Button) findViewById(R.id.botao);
		
		helper = new FormularioHelper(this);
		
		final Cliente clienteParaAlterar = (Cliente) getIntent().getSerializableExtra("clienteSelecionado");
		
		helper.colocaClienteFormulario(clienteParaAlterar);
		
		Toast.makeText(this, "Cliente: " + clienteParaAlterar, Toast.LENGTH_SHORT).show();		
		botao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*Toast.makeText(FormularioActivity.this, 
						"Clicou no Botão", Toast.LENGTH_LONG).show();*/
				
				Cliente cliente = helper.pegaClienteFormulario();
				
				ClienteDAO dao = new ClienteDAO(FormularioActivity.this);
				if (clienteParaAlterar != null){
					cliente.setId(clienteParaAlterar.getId());
					botao.setText("Alterar");
					dao.atualizar(cliente);
				}else{
					dao.insere(cliente);
				}
				dao.close();
				
				finish();
			}
		});
		
		ImageView foto = helper.getFoto();
		
		foto.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				 Intent irParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				 
				 caminhoArquivo = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + "foto.png";
				 File arquivo = new File(caminhoArquivo);
				 
				 //Salva a foto
				 Uri localFoto = Uri.fromFile(arquivo);
				 irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, localFoto);
				 
				 startActivityForResult(irParaCamera, 123);
				 
			}
		});
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 123){
			if (requestCode == Activity.RESULT_OK){
				helper.carregaImagem(caminhoArquivo);
			} 
			else{
				caminhoArquivo = null;
			}
			
		}

	}
	
}
