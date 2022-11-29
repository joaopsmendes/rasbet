package rasbetUI;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rasbetLN.GestaoApostas.Aposta;
import rasbetLN.GestaoJogos.Desporto;
import rasbetLN.GestaoJogos.Jogo;
import rasbetLN.GestaoUtilizadores.Favorito;
import rasbetLN.GestaoUtilizadores.Transacao;
import rasbetLN.IRasbetLN;
import rasbetLN.RasbetLN;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
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
	Map<String,Fornecedor> fornecedorMap = setuFornecedores();

	public static void main(String[] args) {
		SpringApplication.run(RasbetApplication.class, args);
	}

	public Map<String,Fornecedor> setuFornecedores(){
		Map<String,Fornecedor> map = new HashMap<>();
		map.put("futebol",new FornecedorUcras());
		return map;
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

	@PostMapping(path = "login")
	public ResponseEntity<String> login(@RequestBody Map<String, String> myJsonRequest) {
		String email = myJsonRequest.get("email");
		String password = myJsonRequest.get("password");
		System.out.println(email);
		System.out.println(password);

		try {
			rasbetLN.validateLogin(email,password);
			return new ResponseEntity<>("Sucesso", HttpStatus.OK);
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

		}
	}

	@PostMapping(path = "aposta")
	public void aposta(@RequestBody ApostaRequest apostaRequest) {
		try {
			rasbetLN.aposta(apostaRequest);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@RequestMapping(path = "desportos")
	public List<Desporto> getJogos(){
		return rasbetLN.getDesporto();
	}


	@RequestMapping(path = "jogos")
	public Map<String, Jogo> getJogos(@RequestParam(name = "desporto") String desporto) throws SQLException {
		return rasbetLN.getJogos(desporto);
	}

	@RequestMapping(path = "showGamesToAdd")
	public Map<String,List<Game>> showGamesToAdd() {
		Map<String,List<Game>> map = new HashMap<>();
		for (Map.Entry<String, Fornecedor> entry : fornecedorMap.entrySet()){
			Map<String,Game> games = entry.getValue().getGames();
			List<Game> toAdd = new ArrayList<>();
			for (Game g: games.values()){
				if(!g.concluido()) {
					try {
						if (!rasbetLN.existsGame(g.getId(),entry.getKey())) {
							toAdd.add(g);
						}
					} catch (SQLException ignored) {

					}
				}
			}
			map.putIfAbsent(entry.getKey(), toAdd);
		}

		return map;
	}

	@PostMapping(path = "addGame")
	public void addGame(@RequestBody Map<String, String> myJsonRequest) {
		String id =  myJsonRequest.get("gameId");
		String keyB = myJsonRequest.get("keyBookmaker");
		String forn = myJsonRequest.get("desporto");
		Map<String, Game> mapGames  = fornecedorMap.get(forn).getGames();
		Game game = mapGames.get(id);

		try {
			rasbetLN.addGame(game,keyB,forn);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	//TODO
	@PostMapping(path ="changeProfile")
	public void changeProfileInfo(@RequestBody Map<String, String> myJsonRequest) {
		String id = myJsonRequest.get("email");
		try{
			rasbetLN.alterarPerfil(id,myJsonRequest);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}


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
	@PostMapping(path="notificacao")
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

	@RequestMapping(path="favorites")
	public List<Favorito> getFavorites(@RequestBody Map<String, String> myJsonRequest){
		//Get List of favorites
		String id = myJsonRequest.get("userId");
		try {
			return rasbetLN.getFavorites(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@PostMapping(path="fecharAposta")
	public void fecharAposta(@RequestBody Map<String, String> myJsonRequest){
		//Get List of favorites
		String idUser = myJsonRequest.get("userId");
		int idAposta = Integer.parseInt(myJsonRequest.get("idAposta"));
		boolean resultado = Boolean.parseBoolean(myJsonRequest.get("resultado"));
		try {
			rasbetLN.fecharAposta(idUser,idAposta,resultado);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	@RequestMapping(path="updateResultados")
	public void updateResultados(){
		for (Map.Entry<String, Fornecedor> entry : fornecedorMap.entrySet()){
			Map<String,String> map = entry.getValue().updateResultados();
			if (!map.isEmpty()) {
				try {
					rasbetLN.updateResultados(map, entry.getKey());
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	// TODO
	@RequestMapping(path= "historicoAposta")
	public List<Aposta> historicoAposta(@RequestParam(name = "userId") String userId){
		try {
			return rasbetLN.historicoApostas(userId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@RequestMapping(path= "historicoTransacoes")
	public Map<String, List<Transacao>> historicoTransacoes(@RequestParam(name = "userId") String userId){
		try {
			return rasbetLN.historicoTransacoes(userId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	//TODO
	@PostMapping(path="alterarOdd")
	public void alterarOdd(@RequestBody Map<String, String> myJsonRequest){
		//Get List of favorites
		int idOdd = Integer.parseInt(myJsonRequest.get("idOdd"));
		float valor = Float.parseFloat(myJsonRequest.get("valor"));
		try {
			rasbetLN.alterarOdd(idOdd,valor);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@PostMapping(path="alterarEstado")
	public void alterarEstado(@RequestParam(name = "idJogo") String idJogo, @RequestParam(name = "estado") int estado){
		try {
			rasbetLN.alterarEstado(idJogo, estado);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}



	//TODO
	@PostMapping(path="cashout")
	public void cashout(@RequestBody Map<String, String> myJsonRequest){
		//Get List of favorites
		//String idUser = myJsonRequest.get("idUser");
		int idAposta = Integer.parseInt(myJsonRequest.get("idAposta"));
		String userId = myJsonRequest.get("userId");

		try {
			rasbetLN.cashout(idAposta,userId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@RequestMapping(path="saldo")
	public Map<String, Float > getSaldo(@RequestParam(name = "userId") String userId) {
		try {
			return rasbetLN.getSaldo(userId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@RequestMapping(path="info")
	public Map<String, String> info(@RequestParam(name = "userId") String userId) {
		try {
			return rasbetLN.infoUser(userId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}





}
