package implementazionePostgresDAO;

import dao.ProiezioneDAO;
import model.Film;
import model.Proiezione;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProiezioneImplementazionePostgresDAO implements ProiezioneDAO {
    @Override
    public ArrayList<Proiezione> recuperaProiezioniDiUnFilm(Film film) throws SQLException {
        ArrayList<Proiezione> lista = new ArrayList<>();
        // aggiungere query SQL
        return lista;
    }
}