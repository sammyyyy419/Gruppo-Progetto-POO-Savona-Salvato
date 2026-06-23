package dao;

import model.Dipendente;
import java.sql.SQLException;
import java.util.ArrayList;

public interface DipendenteDAO {

	ArrayList<Dipendente> recuperaTuttiDipendenti() throws SQLException;

	void aggiornaPasswordDipendenteDB(String email, String nuovaPassword) throws SQLException;
}