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
import rasbetLN.GestaoUtilizadores.Notificacao;
import rasbetLN.GestaoUtilizadores.Transacao;
import rasbetLN.IRasbetLN;
import rasbetLN.RasbetLN;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@CrossOrigin(origins = "*")
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@RestController
public class RasbetApplication {
	IRasbetLN rasbetLN;
	Timer timer ;
	Set<LocalDateTime> set = new HashSet<>();


	{
		try {
			rasbetLN = new RasbetLN();
			timer = new Timer();
			timer();
		} catch (ClassNotFoundException | SQLException e) {
			throw new RuntimeException(e);
		}
	}
	Map<String,Fornecedor> fornecedorMap = setuFornecedores();


	private void createTimer (LocalDateTime localDateTime,String desporto) {
		Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		if (!set.contains(localDateTime)) {
			System.out.println(date);
			set.add(localDateTime);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					try {
						rasbetLN.updateEstadoJogos();
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
					//System.out.println("yes");
				}
			}, date);
			LocalDateTime end = localDateTime.plusHours(2).plusMinutes(30);
			Date dateEnd = Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					updateResultados(desporto);
					//System.out.println("NO");
				}
			}, dateEnd);
		}
	}

	private void timer() throws SQLException {
		List<Desporto> list = rasbetLN.getDesporto();
		for (Desporto d : list){
			String desporto = d.getModalidade();
			for (Jogo jogo : rasbetLN.getJogos(desporto).values()){
				LocalDateTime localDateTime = jogo.getData();
				createTimer(localDateTime,desporto);
			}
		}
	}

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
			String sessionId = rasbetLN.registarApostador(email,password,nome,nif,date,morada,telemovel);
			return new ResponseEntity<>(sessionId, HttpStatus.OK);
		} catch (SQLException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "login")
	public ResponseEntity<Map<String,String>> login(@RequestBody Map<String, String> myJsonRequest) {
		String email = myJsonRequest.get("email");
		String password = myJsonRequest.get("password");
		System.out.println(email);
		System.out.println(password);

		try {
			Map<String, String> tipo = rasbetLN.validateLogin(email,password);
			return new ResponseEntity<>(tipo, HttpStatus.OK);
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
			Map<String, String> map = new HashMap<>();
			map.put("error",e.getMessage());
			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);

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
	public Map<String,List<GameOutput>> showGamesToAdd() {
		Map<String,List<GameOutput>> map = new HashMap<>();
		for (Map.Entry<String, Fornecedor> entry : fornecedorMap.entrySet()){
			Map<String,Game> games = entry.getValue().getGames();
			List<GameOutput> toAdd = new ArrayList<>();
			for (Game g: games.values()){
				if(!g.concluido()) {
					try {
						if (!rasbetLN.existsGame(g.getId(),entry.getKey())) {
							GameOutput game = new GameOutput(g);
							toAdd.add(game);
						}
					} catch (SQLException ignored) {

					}
				}
			}
			map.putIfAbsent(entry.getKey(), toAdd);
		}
		System.out.println(map);
		return map;
	}

	@PostMapping(path = "adicionarJogo")
	public void adicionarJogo(@RequestBody GameOutput game) {
		System.out.println("ADICIONAR JOGO");
		try {
			rasbetLN.addGame(game);
			createTimer(game.getData(),game.getDesporto());

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	//TODO
	@PostMapping(path ="changeProfile")
	public void changeProfileInfo(@RequestBody Map<String, String> myJsonRequest) {
		String id = myJsonRequest.get("sessionId");
		try{
			rasbetLN.alterarPerfil(id,myJsonRequest);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	//pre-condition: deposit was valid
	@PostMapping(path = "deposito")
	public void deposit(@RequestBody Map<String, String> myJsonRequest) {
		String id = myJsonRequest.get("sessionId");
		float value = Float.parseFloat(myJsonRequest.get("value"));
		try {
			rasbetLN.deposito(id,value);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@PostMapping(path = "levantamento")
	public void withdraw(@RequestBody Map<String, String> myJsonRequest) {
		String id = myJsonRequest.get("sessionId");
		float value = Float.parseFloat(myJsonRequest.get("value"));
		try {
			rasbetLN.levantamento(id,value);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@PostMapping(path = "addFavorito")
	public void addFavorito(@RequestBody Map<String, String> myJsonRequest) {
		String id = myJsonRequest.get("sessionId");
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
		String id = myJsonRequest.get("sessionId");
		String fav= myJsonRequest.get("value");
		String desporto = myJsonRequest.get("desporto");
		try {
			rasbetLN.removeFavorito(id,desporto,fav);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@PostMapping(path="removeNotificacao")
	public void removeNotificacao(@RequestBody Map<String, String> myJsonRequest){
		String id = myJsonRequest.get("sessionId");
		int notificacao = Integer.parseInt(myJsonRequest.get("notificacao"));
		try {
			rasbetLN.removeNotificacao(id,notificacao);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@PostMapping(path="vistaNotificacao")
	public void vistaNotificacao(@RequestBody Map<String, String> myJsonRequest){
		String id = myJsonRequest.get("sessionId");
		int notificacao = Integer.parseInt(myJsonRequest.get("notificacao"));
		try {
			rasbetLN.vistaNotificacao(id,notificacao);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@PostMapping(path="notificacao")
	public void addNotificacao(@RequestBody Map<String, String> myJsonRequest){
		//Get List of favorites
		String id = myJsonRequest.get("sessionId");
		String notificacao = myJsonRequest.get("conteudo");
		try {
			rasbetLN.addNotificacao(id, notificacao);
		}catch (SQLException e){
			System.out.println(e.getMessage());
		}
	}

	@RequestMapping(path="notificacoes")
	public List<Notificacao> getNotifications(@RequestParam(name = "sessionId") String userId){
		//Get List of notifications
		try {
			return rasbetLN.getNotifications(userId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@RequestMapping(path="favoritos")
	public List<Favorito> getFavorites(@RequestParam(name = "sessionId") String userId){
		//Get List of favorites
		try {
			return rasbetLN.getFavorites(userId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@PostMapping(path="fecharAposta")
	public void fecharAposta(@RequestBody Map<String, String> myJsonRequest){
		String idUser = myJsonRequest.get("sessionId");
		int idAposta = Integer.parseInt(myJsonRequest.get("idAposta"));
		boolean resultado = Boolean.parseBoolean(myJsonRequest.get("resultado"));
		try {
			rasbetLN.fecharAposta(idUser,idAposta,resultado);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void updateResultados(String desporto){
		Fornecedor fornecedor = fornecedorMap.get(desporto);
		Map<String,String> map = fornecedor.updateResultados();
		if (!map.isEmpty()) {
			try {
				rasbetLN.updateResultados(map, desporto);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@RequestMapping(path="updateResultados")
	public void updateResultados(){
		for (String entry : fornecedorMap.keySet()){
			updateResultados(entry);
		}
	}

	// TODO
	@RequestMapping(path= "historicoAposta")
	public List<Aposta> historicoAposta(@RequestParam(name = "sessionId") String userId){
		try {
			return rasbetLN.historicoApostas(userId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@RequestMapping(path= "historicoTransacoes")
	public List<Transacao> historicoTransacoes(@RequestParam(name = "sessionId") String userId){
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

	@PostMapping(path="suspenderJogo")
	public void suspenderJogo(@RequestBody Map<String, String> myJsonRequest){
		String idJogo = myJsonRequest.get("idJogo");
		try {
			rasbetLN.suspenderJogo(idJogo);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@PostMapping(path="ativarJogo")
	public void ativarJogo(@RequestBody Map<String, String> myJsonRequest){
		String idJogo = myJsonRequest.get("idJogo");
		try {
			rasbetLN.ativarJogo(idJogo);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}



	//TODO
	@PostMapping(path="cashout")
	public String cashout(@RequestBody Map<String, String> myJsonRequest){
		//Get List of favorites
		//String idUser = myJsonRequest.get("idUser");
		int idAposta = Integer.parseInt(myJsonRequest.get("idAposta"));
		String userId = myJsonRequest.get("sessionId");
		System.out.println(userId);
		try {
			rasbetLN.cashout(idAposta,userId);
			return "Sucessso";
		} catch (SQLException e) {
			return e.getMessage();
		}
	}

	@RequestMapping(path="saldo")
	public Map<String, Float > getSaldo(@RequestParam(name = "sessionId") String userId) {
		try {
			return rasbetLN.getSaldo(userId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@RequestMapping(path="info")
	public Map<String, String> info(@RequestParam(name = "sessionId") String userId) {
		try {
			return rasbetLN.infoUser(userId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
/*
	public void sendNotificao(@RequestBody Notificacao notificacao) {
		try {
			rasbetLN.sendNotificao(notificacao);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}


 */
@RequestMapping(path="sendNotificacao")
	public void sendNotificao(@RequestBody Map<String, String> myJsonRequest){
		//Get List of favorites
		String conteudo = myJsonRequest.get("conteudo");
		try {
			rasbetLN.sendNotificacao(conteudo);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@RequestMapping(path="addJogoASeguir")
	public void addJogoASeguir(@RequestBody Map<String, String> myJsonRequest){
		String idJogo = myJsonRequest.get("idJogo");
		String desporto = myJsonRequest.get("desporto"); ;
		String idUser = myJsonRequest.get("sessionId"); ;
		try {
			rasbetLN.addJogoASeguir(idJogo, desporto, idUser );
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@RequestMapping(path="removeJogoASeguir")
	public void removeJogoASeguir(@RequestBody Map<String, String> myJsonRequest){
		String idJogo = myJsonRequest.get("idJogo");
		String desporto = myJsonRequest.get("desporto"); ;
		String idUser = myJsonRequest.get("sessionId"); ;
		try {
			rasbetLN.removeJogoASeguir(idJogo, desporto, idUser );
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@RequestMapping(path="jogosAseguir")
	public Map<String, List<String>> jogosAseguir(@RequestBody Map<String, String> myJsonRequest) {
		String idUser = myJsonRequest.get("sessionId"); ;
		try {
			return rasbetLN.getJogosASeguir(idUser);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}



}
