package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.FireStationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/firestation")
public class FireStationController
{
	private static final Logger logger = LogManager.getLogger("FireStationController");

	private FireStationService fireStationService;

	public FireStationController(FireStationService fireStationService) {
		this.fireStationService = fireStationService;
	}

	// Ajouter une nouvelle caserne/adresse
	@PostMapping( "/addFireStation")
	public ResponseEntity<Object> addFireStation (@RequestBody FireStation fireStation) {
		FireStation fire = fireStationService.getFireStationByAddress(fireStation.getAddress());
		if (fire == null) {
			return new ResponseEntity<>(fireStationService.addFireStation(fireStation), HttpStatus.OK);
		}
		logger.error("Error: address exist already");
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	// Mettre à jour le numéro de la caserne de pompiers d'une adresse
	@PutMapping("/updateFireStation/")
	public ResponseEntity<Object> updateFireStation(@RequestBody FireStation fireStation) {
		FireStation fire = fireStationService.getFireStationById(fireStation.getId());
		if (fire == null){
			logger.error("Error: Id is not valid");
			//return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		fireStation.setId(fire.getId());
		fireStation.setAddress(fire.getAddress());
		return new ResponseEntity<>(fireStationService.updateFireStation(fireStation), HttpStatus.OK);
	}

	// Supprimer le mapping d'une caserne ou d'une adresse.
	@DeleteMapping ( "/deleteAddress/{address}")
	public void deleteFireStation (@PathVariable String address)
	{
		int i = fireStationService.deleteFireStationByAddress(address);
		if(i > 0)
		{
			logger.info("Delete address successfully");
		}
		else
			logger.error("Address is not valid");
	}

	@DeleteMapping ( "/deleteStation/{station}")
	public void deleteFireStation (@PathVariable int station)
	{
		int i = fireStationService.deleteFireStationByStation(station);
		if(i > 0)
		{
			logger.info("Delete station successfully");
			//fireStationService.deleteFireStationByStation(station);
		}
		else
			logger.error("Error: Station does not exist");
	}

	/*
	@GetMapping("/firestation?stationNumber=<station>")
	public @ResponseBody List<Person> listPersonByStationNumber (@RequestParam ("station") int station){
		List<FireStation> fireStation = fireStationService.getListStation(station);


	}

	 */

}
