# Requisitos funcionais

# Lógica de Negócio

Aposta deve ter estado


# UI

Make UI more generic so we can use different game's providers

Timer for get Results from API

# Database

Add a table API provider to DB, to store info about: url, type of sport and status 

Make one table for each type of user 

# Requisitos não funcionais

Jogos devem ser guardados na base de dados e os seus resultados atualizados automaticamente (odds também)?

# QUERY SQL

SELECT Jogo.idJogo,Odd.idOdd,ApostaJogo.tema,Odd.valor,Jogo.Desporto_idDesporto FROM ApostaJogo INNER JOIN Jogo ON Jogo.idJogo=ApostaJogo.Jogo_idJogo AND Jogo.Desporto_idDesporto=ApostaJogo.Jogo_Desporto_idDesporto INNER J
OIN Odd ON Odd.ApostaJogo_idApostaJogo=ApostaJogo.idApostaJogo;
