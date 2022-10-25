package rasbetUI;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@SpringBootApplication
@RestController
public class RasbetApplication {

	public static void main(String[] args) {
		SpringApplication.run(RasbetApplication.class, args);
	}

	// Get Games from Stor's API
	@RequestMapping("games")
	public Game[] hello(){
		RestService rest = new RestService(new RestTemplateBuilder());
		//Gson gson = new Gson();
		Game[] games = rest.getPostsPlainJSON();
		for (Game game : games){
			System.out.println(game.id);
		}
		return games;
		//return gson.fromJson(rest.getPostsPlainJSON(),Object[].class);
	}

	// Register
	@PostMapping(path = "register")
	public String register(@RequestBody UserRegister userRegister){
		System.out.println(userRegister);
		return userRegister.email;
	}

	@PostMapping(path = "login")
	public String login (@RequestBody UserLogin userLogin){
		System.out.println(userLogin.email +" -> " + userLogin.password);
		return userLogin.email;
	}
	@PostMapping(path = "login2")
	public void login2(@RequestBody Map<String, String> myJsonRequest) {
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

}
