package tn.iit.suivi_enseignement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iit.suivi_enseignement.dao.DepartementDao;
import tn.iit.suivi_enseignement.dao.EnseignantDao;
import tn.iit.suivi_enseignement.dao.EnseignementDao;
import tn.iit.suivi_enseignement.dao.JourDao;
import tn.iit.suivi_enseignement.dao.MatiereDao;
import tn.iit.suivi_enseignement.dao.NiveauDao;
import tn.iit.suivi_enseignement.dao.SalleDao;
import tn.iit.suivi_enseignement.dao.SeanceDao;
import tn.iit.suivi_enseignement.dto.EnseignementDto;
import tn.iit.suivi_enseignement.entites.Departement;
import tn.iit.suivi_enseignement.entites.Enseignant;
import tn.iit.suivi_enseignement.entites.Enseignement;
import tn.iit.suivi_enseignement.entites.Jour;
import tn.iit.suivi_enseignement.entites.Matiere;
import tn.iit.suivi_enseignement.entites.Niveau;
import tn.iit.suivi_enseignement.entites.Salle;
import tn.iit.suivi_enseignement.entites.Seance;

@RestController
@RequestMapping("/enseignement")
public class EnseignementController {

	@Autowired
	private EnseignementDao enseignementDao;
	@Autowired
	private EnseignantDao enseignantDao;
	@Autowired
	private DepartementDao departementDao;
	@Autowired
	private JourDao jourDao;
	@Autowired
	private MatiereDao matiereDao;
	@Autowired
	private NiveauDao niveauDao;
	@Autowired
	private SalleDao salleDao;
	@Autowired
	private SeanceDao seanceDao;

	@PostMapping
	public ResponseEntity<Enseignement> createOrUpdate(@RequestBody EnseignementDto ensDto) {

		Departement departement = departementDao.findOne(ensDto.getDepartement());
		Enseignant enseignant = enseignantDao.findOne(ensDto.getEnseignant());
		Jour jour = jourDao.findOne(ensDto.getJour());
		Matiere matiere = matiereDao.findOne(ensDto.getMatiere());
		Niveau niveau = niveauDao.findOne(ensDto.getNiveaux());
		Salle salle = salleDao.findOne(ensDto.getSalle());
		Seance seance = seanceDao.findOne(ensDto.getSeance());

		Enseignement enseignement = ensDto.toEnseignement(enseignant, niveau, salle, jour, seance, departement,
				matiere);

		enseignementDao.saveAndFlush(enseignement);

		return new ResponseEntity<>(enseignement, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Enseignement> delete(@PathVariable Integer id) {

		Enseignement ens = null;
		ResponseEntity<Enseignement> response = null;
		if ((ens = enseignementDao.findOne(id)) != null) {
			enseignementDao.delete(id);
			response = new ResponseEntity<>(ens, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return response;

	}

	@GetMapping("/{id}")
	public Enseignement getOne(@PathVariable Integer id) {
		return enseignementDao.findOne(id);
	}

	@GetMapping
	public List<Enseignement> list() {
		return enseignementDao.findAll();
	}

}