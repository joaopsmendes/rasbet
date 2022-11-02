package rasbetUI;



import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rasbetLN.Apostador;
import rasbetLN.IRasbetLN;
import rasbetLN.RasbetLN;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
public class RasbetApplication {

	IRasbetLN rasbetLN = new RasbetLN();
	public static void main(String[] args) {
		SpringApplication.run(RasbetApplication.class, args);
	}

	// Get Games from Database
	@RequestMapping("games")
	public void getGames(){

	}

	// Register
	@PostMapping(path = "register")
	public ResponseEntity<String> register(@RequestBody Map<String,String> myJsonRequest){
		String email = myJsonRequest.get("email");
		String password = myJsonRequest.get("password");
		String nome = myJsonRequest.get("nome");
		String nif = myJsonRequest.get("nif");
		LocalDate date = LocalDate.parse(myJsonRequest.get("date"));
		String morada = myJsonRequest.get("morada");
		String telemovel = myJsonRequest.get("telemovel");
		try {
			rasbetLN.registarApostador(email,password,nome,nif,date,morada,telemovel);
			return new ResponseEntity<>("Apostador registado", HttpStatus.OK);
		} catch (SQLException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}


	@RequestMapping(path = "login")
	public boolean login(@RequestBody Map<String, String> myJsonRequest) {
		String email = myJsonRequest.get("email");
		String password = myJsonRequest.get("password");
		try {
			rasbetLN.validateLogin(email,password);
			return true;
		}
		catch (SQLException e){
			return false;
		}
	}

	@PostMapping(path = "apostaSimples")
	public void apostaSimples(@RequestBody ApostaRequest apostaRequest) {
		//System.out.println(apostaRequest.gameId);
		System.out.println(apostaRequest);


	}

	@PostMapping(path = "apostaMultipla")
	public void apostaMultipla(@RequestBody ApostaRequest apostaRequest){
		System.out.println(apostaRequest);

	}

	@RequestMapping(path = "showGames")
	public void showGames(@RequestBody Map<String, String> myJsonRequest) {
		System.out.println(myJsonRequest.get("desporto"));
	}

	@RequestMapping(path = "showGamesToAdd")
	public List<Game> showGamesToAdd(@RequestBody Map<String, String> myJsonRequest) {
		// Check if there's new games to add
		RestService rest = new RestService(new RestTemplateBuilder());
		Map<String,Game> games = rest.getPostsPlainJSON();

		List<Game> toAdd = new ArrayList<>();
		for (Game g: games.values()){
			//Check if game is at Database
			//LN.exists - add to List if not
		}
		return toAdd;
	}

	@PostMapping(path = "addGame")
	public void addGame(@RequestBody Map<String, String> myJsonRequest) {
		String id =  myJsonRequest.get("gameId");
		String keyB = myJsonRequest.get("keyBookmaker");
		RestService rest = new RestService(new RestTemplateBuilder());
		Map<String,Game> mapGames  = rest.getPostsPlainJSON();
		Game game = mapGames.get(id);

	}


	@PostMapping(path = "changeProfile")
	public void changeProfileInfo(@RequestBody Map<String, String> myJsonRequest) {
		String id = myJsonRequest.get("id");
	}


	//pre-condition: deposit was valid
	@PostMapping(path = "deposit")
	public void deposit(@RequestBody Map<String, String> myJsonRequest) {
		String id = myJsonRequest.get("id");
		Float value = Float.parseFloat(myJsonRequest.get("value"));
		//
	}
		
	@PostMapping(path = "withdraw")
	public void withdraw(@RequestBody Map<String, String> myJsonRequest) {
		String id = myJsonRequest.get("id");
		Float value = Float.parseFloat(myJsonRequest.get("value"));
		//
	}

	@PostMapping(path = "addFavorito")
	public void add(@RequestBody Map<String, String> myJsonRequest) {
		String id = myJsonRequest.get("id");
		String fav= myJsonRequest.get("value");
		//
	}

	@RequestMapping("favorites")
	public void getFavorites(){
		//Get List of favorites

	}

}
