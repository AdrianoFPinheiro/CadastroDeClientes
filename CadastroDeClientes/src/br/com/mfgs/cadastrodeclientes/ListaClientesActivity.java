package br.com.mfgs.cadastrodeclientes;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import br.com.mfgs.cadastrodeclientes.dao.ClienteDAO;
import br.com.mfgs.cadastrodeclientes.modelo.Cliente;


public class ListaClientesActivity extends Activity{
	
	private ListView lista;
	Cliente clienteSelecionado;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listagem_clientes);
		
		lista = (ListView) findViewById(R.id.lista);
		registerForContextMenu(lista);	
		
		//Clique normal
		lista.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int posicao, long id) {
				
				Cliente alterarCliente = (Cliente) adapter.getItemAtPosition(posicao);
								
				Intent irParaFormulario = new Intent(ListaClientesActivity.this, FormularioActivity.class);
				irParaFormulario.putExtra("clienteSelecionado", alterarCliente);
				
				startActivity(irParaFormulario);
				
				//Toast.makeText(ListaClientesActivity.this, "A posição é: " + posicao, Toast.LENGTH_SHORT).show();				
			}
		});
		
		//Clique Longo
		lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int posicao, long id) {
				
				clienteSelecionado = (Cliente) adapter.getItemAtPosition(posicao);
				
				return false;
			}
		});
		
	}
	
	@Override
	protected void onResume() {
		
		carregaLista();
		super.onResume();
		
		ClienteDAO dao = new ClienteDAO(this);
		List<Cliente> clientes = dao.getLista();
	
		ArrayAdapter<Cliente> adapter = 
			new ArrayAdapter<Cliente>(this, android.R.layout.simple_list_item_1, clientes); 
		lista.setAdapter(adapter);

	}

		private void carregaLista() {
			ClienteDAO dao = new ClienteDAO(this);
			List<Cliente> clientes = dao.getLista();
		
			ArrayAdapter<Cliente> adapter = 
				new ArrayAdapter<Cliente>(this, android.R.layout.simple_list_item_1, clientes); 
			lista.setAdapter(adapter);
			
		}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.menu_listaclientes, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case R.id.novo:
			Intent irParaFormulario = new Intent(ListaClientesActivity.this, FormularioActivity.class); 
			startActivity(irParaFormulario);
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		
		MenuItem ligar = menu.add("Ligar");
		ligar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent irParaDiscagem = new Intent(Intent.ACTION_CALL);
				
				Uri felefoneDoCliente = Uri.parse("tel:" + clienteSelecionado.getTelefone());
				irParaDiscagem.setData(felefoneDoCliente );
				
				startActivity(irParaDiscagem);
				return false;
			}
		});	
			
		menu.add("Enviar SMS");
		menu.add("Achar no Mapa");
		
		MenuItem site = menu.add("Navegar no site");
		site.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent abrirSite = new Intent(Intent.ACTION_VIEW);
				
				Uri siteDoCliente = Uri.parse("http://" + clienteSelecionado.getEmail());
				abrirSite.setData(siteDoCliente );
				
				return false;
			}
		});	
		
		MenuItem deletar = menu.add("Deletar");
		deletar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				
		
				/*Toast.makeText(ListaClientesActivity.this, 
						"Clicou no Botão", Toast.LENGTH_LONG).show();*/
				
				AlertDialog.Builder msg = new AlertDialog.Builder(ListaClientesActivity.this);
				msg.setMessage("Deseja apagar este cliente?");
				msg.setNegativeButton("Não", null);
				msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ClienteDAO dao = new ClienteDAO(ListaClientesActivity.this);
						dao.deletar(clienteSelecionado);
						dao.close();
						Toast.makeText(getBaseContext(),
									"Sucesso ao apagar o cliente.", Toast.LENGTH_SHORT).show();
						
						carregaLista();
					}
				});
				msg.show();
				return false;
			}
		});
		
		menu.add("Enviar E-Mail");
		
		super.onCreateContextMenu(menu, v, menuInfo);
	}
		
}

	



