# Requisitos funcionais
- use case para o adminstrador/especialista (?):
	- consultar jogos para adicionar
	- adicionar jogo

# Lógica de Negócio

Jogo não tem informação relativo aos intervinientes

Resultado não tem informação relativa aos intervinientes:
	- em vez de ter os golos (informação irrelevante), ter o outcoume (?) maybe change class to string attribute

Utilizador atributo com número de cartão de cidadão

Aposta deve ter alguma referência para as odds

Aposta deve ter estado

Pensar em possibilidade de diferentes tipos de apostas
	- String a identificar o tipo de aposta

# UI

Make UI more generic so we can use different game's providers

# Database

Add a table API provider to DB, to store info about: url, type of sport and status 

Make one table for each type of user 

Rethink structure

# Requisitos não funcionais

Adminstrador/especialista adiciona os jogos disponíveis, e escolhe qual a casa de apostas

Jogos devem ser guardados na base de dados e os seus resultados atualizados automaticamente (odds também)?
