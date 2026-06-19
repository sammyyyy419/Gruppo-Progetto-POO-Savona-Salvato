package dao;

import model.Film;
import model.Proiezione;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ProiezioneDAO {
    ArrayList<Proiezione> recuperaProiezioniDiUnFilm(Film film) throws SQLException;
}