Meus Locais Favoritos
===================
Se você costuma esquecer endereços de locais que frequenta, ou precisa registrar de forma rápida e gratuita algum endereço no smartphone, este aplicativo é perfeito para você.
Com ele é possível salvar endereços de forma fácil e ágil, basta tocar e segurar em um determinado ponto do mapa e dar um nome para o local, pronto, já está salvo!.

Funcionalidades
===================
Funcionalidades do aplicativo:

- Exibir mapa híbrido, satelital, do terreno e normal;
- Exibir a localização atual;
- Salvar e exibir pontos no mapa (checkin);
- Listar pontos salvos em formato de lista;
- Excluir pontos salvos;
- Calcular a distância da rota entre a posição atual e um determinado ponto do mapa;
- Exibir o endereço de um determinado ponto no mapa e a sua coordenada;
- Traçar rota entre a posição atual e um determinado ponto do mapa;
- Mostrar rota de forma textual (ex.: Vire a direita na Rua 14, depois vire à esquerda).

Arquitetura
===================
Neste projeto foi aplicada a arquitetura cliente-servidor.
O cliente Android acessa os servidores da Google através de requisições HTTP (ex.: GET, POST, PUT etc.) e o servidor responde a estas requisições com base nos parâmetros 
enviados e URLS apontadas.

Exemplo da construção de uma URL para consulta de rota: http://maps.googleapis.com/maps/api/directions/json
* ?origin = Latitude e Longitude do ponto de origem (separados por vírgula)  
* &destination = Latitude e Longitude do ponto de destino (separados por vírgula) 
* &sensor = Determinar que a localização seja detectada por sensores (boolean)
* &units = Tipo de unidade de distância,  ex.: "metric"
* &mode = Tipo de locomoção, ex.: a pé, carro, ônibus, bicicleta etc.

Mais detalhes: https://developers.google.com/maps/documentation/directions/?hl=pt-br

APIs
===================
APIs utilizadas no projeto:
- **Google Maps para Android** - Exibição de mapas, desenho de polígonos (rota), posicionamento de pinos etc;
- **Google Directions** - Consulta de rotas, distância entre pontos, endereço etc.

Bibliotecas
===================
Bibliotecas utilizadas no projeto:
- **Google Play Services** - Contém a bibliotecas de mapas do Google;
- **RecyclerView** - É o "ListView 2.0" do Android, oferece vários recursos como o de reter a posição do scroll ao reconstruir a tela em outra orientação;
- **AppCompat** - Biblioteca de compatibilidade para que o aplicativo possa rodar em versões antigas do Android;
- **CardView** - Novo componente visual do Android, utilizado para exibir os endereços salvos;
- **Crouton** - Biblioteca de alertas amigáveis, similar ao Toast do Android;
- **Gson** - Biblioteca para manipulação de JSON.

Dados técnicos do aplicativo
===================
- Compilado com a API 21 (Android 5.0) por utilizar o tema Material Design;
- MinSDK com API  10 (Android 2.3.3);
- É necessário que o smartphone esteja conectado à internet para consulta de rotas, endereço e distância;
- Permissões exigidas: INTERNET, ACCESS_NETWORK_STATE, WRITE_EXTERNAL_STORAGE, READ_GSERVICES, ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION.


###### Documentação referente ao projeto na [versão 1.0] (https://github.com/pedrofsn/MeusLocaisFavoritos/releases/tag/Versao1.0) ######

