package rasbetUI;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
public class RasbetApplication {

	public static void main(String[] args) {
		SpringApplication.run(RasbetApplication.class, args);
	}

	// Get Games from Database
	@RequestMapping("games")
	public void getGames(){
	}

	// Register
	@PostMapping(path = "register")
	public String register(@RequestBody UserRegister userRegister){
		System.out.println(userRegister);
		return userRegister.email;
	}


	@PostMapping(path = "login")
	public void login(@RequestBody Map<String, String> myJsonRequest) {
		System.out.println(myJsonRequest.get("email"));
		System.out.println(myJsonRequest.get("password"));
	}


	@PostMapping(path = "aposta")
	public void aposta(@RequestBody Map<String, String> myJsonRequest) {
		System.out.println(myJsonRequest.get("gameId"));
		System.out.println(myJsonRequest.get("value"));
	}

	@PostMapping(path = "showGames")
	public void showGames(@RequestBody Map<String, String> myJsonRequest) {
		System.out.println(myJsonRequest.get("desporto"));
	}

	@PostMapping(path = "showGamesToAdd")
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
