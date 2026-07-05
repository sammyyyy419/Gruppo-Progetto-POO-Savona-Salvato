package implementazionePostgresDAO;

import dao.ProiezioneDAO;
import model.Film;
import model.Proiezione;
import model.Sala;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * The type Proiezione implementazione postgres dao.
 */
public class ProiezioneImplementazionePostgresDAO implements ProiezioneDAO {

    @Override
    public ArrayList<Proiezione> recuperaProiezioniDiUnFilm(Film film) throws SQLException {
        ArrayList<Proiezione> listaProiezioni = new ArrayList<>();
        if (film == null || film.getDataInizioProgrammazione() == null) return listaProiezioni;

        String tipo = film.getSalaAssegnata().equals("Sala 1") ? "IMAX" : "Standard";
        Sala salaDelFilm = new Sala(film.getSalaAssegnata(), 50, tipo);
        LocalDate dataInizio = film.getDataInizioProgrammazione();

        for (int i = 0; i <= 45; i++) {
            LocalDate dataCorrente = dataInizio.plusDays(i);

            int minutiOffset = 0;
            if (film.getSalaAssegnata().startsWith("Sala ")) {
                try { minutiOffset = Integer.parseInt(film.getSalaAssegnata().replace("Sala ", "").trim()) * 15; }
                catch (Exception ignored) {}
            }

            LocalDateTime orario1 = LocalDateTime.of(dataCorrente, LocalTime.of(17, 0).plusMinutes(minutiOffset));
            listaProiezioni.add(new Proiezione(orario1, orario1.plusMinutes(film.getDurataMinuti()), 8.00, film, salaDelFilm));

            LocalDateTime orario2 = LocalDateTime.of(dataCorrente, LocalTime.of(21, 0).plusMinutes(minutiOffset));
            listaProiezioni.add(new Proiezione(orario2, orario2.plusMinutes(film.getDurataMinuti()), 8.00, film, salaDelFilm));
        }
        return listaProiezioni;
    }
}