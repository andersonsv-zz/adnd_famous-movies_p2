# Android - Nanodegree - FilmesFamosos - P2

Versão do  Documento - 1.0

# Revisões:
------
|

1 - 09/10/2018 - Primeiro envio
 

Segundo projeto do Nanodegree - Android - Filmes Famosos. O projeto está baseados em três telas: 

 1. Lista de filmes em grade ordenados por "Mais populares" (Most popular) e "Mais Votados"(Top Rated). Por padrão a classificação é por filmes "Mais Votados" (Top Rated).
 2. Detalhes do filme com as informações de: imagem de fundo, capa, nome do filme, lançamento, título e sinopse, vídeosm, críticas e marcação de favoritos.
 3. Favoritos 

# Objetivos
------ 
O aplicativo está escrito apenas na linguagem de programação Java.

O aplicativo está de acordo com as normas comuns encontradas nas Diretrizes gerais de projetos do Nanodegree Android.

A UI contém um elemento (ex: um spinner ou menu de configuração) para mudar a ordenação dos filmes por: mais popular ou melhor avaliado.

Os filmes são exibidos no layout principal através de uma grade com os cartazes dos filmes correspondentes.

A UI contém uma tela para exibir os detalhes do filme selecionado. 

O layout com detalhes do filme contém título, data de lançamento, cartaz do filme, média de votos e sinopse.

O layout com detalhes do filme contém uma seção para exibir vídeos de trailer e avaliações de usuários.

Quando um usuário altera os critérios de ordenação (mais populares, mais votados e favoritos), a visualização principal é atualizada corretamente.

Quando uma miniatura de cartaz de filme é selecionada, a tela de detalhes do filme é iniciada.

Quando um trailer é selecionado, o aplicativo usa um intent para rodar o trailer.

Na tela de detalhes do filme, um usuário pode tocar em um botão (por exemplo, uma estrela) para marcá-lo como favorito.

Em uma thread em segundo plano, o aplicativo consulta a API /movie/popular ou /movie/top_rated para os critérios de ordenação especificados no menu de configurações.

Solicitações de aplicativo para vídeos relacionados de um filme selecionado através do endpoint /movie/{id}/videos em uma thread em segundo plano e exibe esses detalhes quando o usuário seleciona um filme.

Solicitações do aplicativo para avaliação de usuários de um filme selecionado através do endpoint /movie/{id}/reviews em segundo plano e exibe esses detalhes quando o usuário seleciona um filme.

Os títulos e IDs dos filmes favoritos do usuário são armazenados em um banco de dados SQLite nativo e expostos por meio de um ContentProvider
OU
armazenado usando o Room.

Os dados são atualizados sempre que o usuário os favoritos ou desfavorece um filme. Nenhuma outra biblioteca de persistência é usada.

Quando a opção de configuração "favoritos" é selecionada, a visualização principal exibe toda a coleção de favoritos com base nos IDs de filme armazenados no banco de dados.

Se Room for usado, o banco de dados não será consultado desnecessariamente após a rotação. LiveData em cache de ViewModel é usado em seu lugar.

Se Room for usado, o banco de dados não será consultado desnecessariamente. O LiveData é usado para observar as alterações no banco de dados e atualizar a interface do usuário de acordo.

Estenda o banco de dados de favoritos para armazenar o pôster do filme, sinopse, classificação do usuário e data de lançamento, e exibi-los mesmo quando estiver off-line.

Implemente a funcionalidade de compartilhamento para permitir que o usuário compartilhe o URL "YouTube" do primeiro trailer na tela de detalhes do filme.

# Arquitetura
------
- Java 8
- Android
- Gradle

# IDE 
------
Android Studio (atualizada na última versão)

# Chave de API
------
  [TheMovieDB](https://www.themoviedb.org) > Gerar a chave.
  Necessário incluir a chave moviedb_api_key no arquivo gradle.properties:
  ```
  moviedb_api_key=[api da conta do site]
```
