package br.com.mfgs.cadastrodeclientes.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.mfgs.cadastrodeclientes.modelo.Cliente;

public class ClienteDAO extends SQLiteOpenHelper{

	private static final String DATABASE = "db_CadClie";
	private static final int VERSAO = 2;
	private static final String TABELA = "Cliente";

	public ClienteDAO(Context ctx) {
		super(ctx, DATABASE, null, VERSAO);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	    String sql = "CREATE TABLE " + TABELA + " ( "
	    		+ "id INTEGER PRIMARY KEY, "
	    		+ "nome TEXT UNIQUE NOT NULL, "
	    		+ "telefone TEXT, "
	    		+ "endereco TEXT, "
	    		+ "email TEXT, "
	    		+ "nota REAL, "
	    		+ "caminhoFoto TEXT"
	    		+ ");";
	    
	    db.execSQL(sql);
	    
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS " + TABELA;
		db.execSQL(sql);
		onCreate(db);
	}

	public void insere(Cliente cliente) {
		ContentValues cv = new ContentValues();
		cv.put("nome", cliente.getNome());
		cv.put("telefone", cliente.getTelefone());
		cv.put("endereco", cliente.getEndereco());
		cv.put("email", cliente.getEmail());
		cv.put("nota", cliente.getNota());
		cv.put("caminhoFoto", cliente.getCaminhoFoto());
		
		getWritableDatabase().insert(TABELA, null, cv);
	}

	public List<Cliente> getLista() {
		List<Cliente> clientes = new ArrayList<Cliente>();
		
		String sql = "SELECT * FROM " + TABELA + ";";
		Cursor c = getReadableDatabase().rawQuery(sql, null);
		
		
		while (c.moveToNext()){
			Cliente cliente = new Cliente();
			
			cliente.setId(c.getLong(c.getColumnIndex("id")));
			cliente.setNome(c.getString(c.getColumnIndex("nome")));
			cliente.setTelefone(c.getString(c.getColumnIndex("telefone")));
			cliente.setEndereco(c.getString(c.getColumnIndex("endereco")));
			cliente.setEmail(c.getString(c.getColumnIndex("email")));
			cliente.setNota(c.getDouble(c.getColumnIndex("nota")));
			cliente.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));
			
			clientes.add(cliente);
		}
		
		return clientes;
	}

	public void deletar(Cliente cliente) {
		String[] args = {cliente.getId().toString()};
		getWritableDatabase().delete("Cliente", "id=?", args);
		
	}

	public void atualizar(Cliente cliente) {
		ContentValues cv = new ContentValues();
		cv.put("nome", cliente.getNome());
		cv.put("telefone", cliente.getTelefone());
		cv.put("endereco", cliente.getEndereco());
		cv.put("email", cliente.getEmail());
		cv.put("nota", cliente.getNota());
		cv.put("caminhoFoto", cliente.getCaminhoFoto());
		
		String[] args = {cliente.getId().toString()};
		getWritableDatabase().update("Cliente", cv , "id=?", args);
		
	}

}



