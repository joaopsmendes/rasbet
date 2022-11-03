package rasbetUI;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rasbetLN.GestaoJogos.Jogo;
import rasbetLN.GestaoUtilizadores.Favorito;
import rasbetLN.IRasbetLN;
import rasbetLN.RasbetLN;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
public class RasbetApplication {
	IRasbetLN rasbetLN;

	{
		try {
			rasbetLN = new RasbetLN();
		} catch (ClassNotFoundException | SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(RasbetApplication.class, args);
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
			System.out.println(e.getMessage());
			return false;
		}
	}

	@PostMapping(path = "apostaSimples")
	public void apostaSimples(@RequestBody ApostaRequest apostaRequest) {
		try {
			rasbetLN.apostaSimples(apostaRequest);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@PostMapping(path = "apostaMultipla")
	public void apostaMultipla(@RequestBody ApostaRequest apostaRequest){
		try {
			rasbetLN.apostaMultipla(apostaRequest);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@RequestMapping(path = "showGames")
	public Map<String, Jogo> showGames(@RequestBody Map<String, String> myJsonRequest) throws SQLException {
		String desporto = myJsonRequest.get("desporto");
		return rasbetLN.getJogos(desporto);
	}

	@RequestMapping(path = "showGamesToAdd")
	public List<GameFutebol> showGamesToAdd() {
		RestService rest = new RestService(new RestTemplateBuilder());
		Map<String, GameFutebol> games = rest.getPostsPlainJSON();
		List<GameFutebol> toAdd = new ArrayList<>();
		for (GameFutebol g: games.values()){
			if(!g.completed) {
				try {
					if (!rasbetLN.existsGame(g.getId(), "futebol")) {
						toAdd.add(g);
					}
				} catch (SQLException ignored) {

				}
			}
		}
		return toAdd;
	}

	@PostMapping(path = "addGame")
	public void addGame(@RequestBody Map<String, String> myJsonRequest) {
		String id =  myJsonRequest.get("gameId");
		String keyB = myJsonRequest.get("keyBookmaker");
		RestService rest = new RestService(new RestTemplateBuilder());
		Map<String, GameFutebol> mapGames  = rest.getPostsPlainJSON();
		GameFutebol gameFutebol = mapGames.get(id);

		try {
			rasbetLN.addGame(gameFutebol,keyB,"futebol");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	//TODO
	@PostMapping(path ="changeProfile")
	public void changeProfileInfo(@RequestBody Map<String, String> myJsonRequest) {
		String id = myJsonRequest.get("id");


	}


	//pre-condition: deposit was valid
	@PostMapping(path = "deposito")
	public void deposit(@RequestBody Map<String, String> myJsonRequest) {
		String id = myJsonRequest.get("id");
		float value = Float.parseFloat(myJsonRequest.get("value"));
		try {
			rasbetLN.deposito(id,value);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
		
	@PostMapping(path = "levantamento")
	public void withdraw(@RequestBody Map<String, String> myJsonRequest) {
		String id = myJsonRequest.get("id");
		float value = Float.parseFloat(myJsonRequest.get("value"));
		try {
			rasbetLN.levantamento(id,value);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@PostMapping(path = "addFavorito")
	public void addFavorito(@RequestBody Map<String, String> myJsonRequest) {
		String id = myJsonRequest.get("id");
		String fav= myJsonRequest.get("value");
		String desporto = myJsonRequest.get("desporto");
		try {
			rasbetLN.addFavorito(id,desporto,fav);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@PostMapping(path = "removeFavorito")
	public void removeFavorito(@RequestBody Map<String, String> myJsonRequest) {
		String id = myJsonRequest.get("id");
		String fav= myJsonRequest.get("value");
		String desporto = myJsonRequest.get("desporto");
		try {
			rasbetLN.removeFavorito(id,desporto,fav);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	@RequestMapping("notificacao")
	public void addNotificacao(@RequestBody Map<String, String> myJsonRequest){
		//Get List of favorites
		String id = myJsonRequest.get("userId");
		String notificacao = myJsonRequest.get("conteudo");
		try {
			rasbetLN.addNotificacao(id, notificacao);
		}catch (SQLException e){
			System.out.println(e.getMessage());
		}

	}

	@RequestMapping("favorites")
	public List<Favorito> getFavorites(@RequestBody Map<String, String> myJsonRequest){
		//Get List of favorites
		String id = myJsonRequest.get("userId");
		try {
			return rasbetLN.getFavorites(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	@RequestMapping("updateResultados")
	public void updateResultados(){
		RestService rest = new RestService(new RestTemplateBuilder());
		Map<String, GameFutebol> games = rest.getPostsPlainJSON();
		Map<String,String> map = new HashMap<>();
		for (GameFutebol g: games.values()){
			if (g.completed) {
				String vencedor = g.whoWon();
				map.put(g.id,vencedor);
			}
		}
		try {
			rasbetLN.updateResultados(map,"futebol");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
