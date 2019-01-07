#include <WiFiNINA.h>
#include <MQTT.h>
#include <SPI.h>
#include <ArduinoHttpClient.h>

/* Déclaration et initialisation de toutes les variables */
WiFiClient wifi_client ;  // Client wifi 
MQTTClient mqtt_client ;  // Client mqtt
const char* wifi_ssid     = "iPhone de Axel";   // Nom du réseau wifi
const char* wifi_password = "12345678";   // Mot de passe du réseau wifi
const char* mqtt_host = "m21.cloudmqtt.com" ;  // Adresse du serveur mqtt
const uint16_t mqtt_port =  16964 ;   // Port de connexion au serveur mqtt
const char* mqtt_user = "zvafqgcd";   // Nom d'utilisateur pour le serveur mqtt
const char* mqtt_passwd = "HgxcwISOxy_z";   // Mot de passe de connexion au serveur mqtt
/*char hueHubIP[] = "192.168.1.131";  // Adresse IP du bridge 
HttpClient client = HttpClient(wifi_client,hueHubIP);*/   // Client http
boolean status;   // Méthode du client WiFi indiquant si la connexion est réussie
int lastMillis;   // Contient la date à laquelle est parue le dernier message 
int i = 1;  // Compteur

// entrée digitale où les boutons sont branchés
#define PIN_ENTREE_BOUTON_1 7
#define PIN_ENTREE_BOUTON_2 6
 
// temps "d'anti-rebond", en millisecondes, pendant lequel les rebonds seront ignorés
#define TEMPS_ANTI_REBOND_MS 50
 
// Variables globales contenant les états des boutons, initialisées avec les boutons non-appuyés (donc relâchés)
bool bouton1Appuye = false;
bool bouton2Appuye = false;

// Variables globales contenant lisant et contenant statut de la light, ainsi que sa couleur 
boolean etatAllumage;
String stateLight;
String colorLight;
 
// Compteurs de temps pour l'anti-rebond, initialisés à 0 ce qui veut dire qu'il n'y a pas d'anti-rebond en cours
unsigned long dateAntiRebond_1 = 0;
unsigned long dateAntiRebond_2 = 0;
 
// Compteur de clics/appuis sur nos boutons
int compteurDeClicks_1 = 0;
int compteurDeClicks_2 = 0;

void callback(String &intopic, String &payload)   // Fonction appelée lors de la réception d'un message sur le cloud 
{
  /* On insère les variables déjà déclarées au préalable qui seront utiles à la fonction callback() */
  wifi_client;
  int i = 1;
  /* char hueHubIP[] = "192.168.1.131";  
  HttpClient client = HttpClient(wifi_client, hueHubIP);*/
  
  Serial.println("incoming: " + intopic + " - " + payload);   // On affiche l'expéditeur du message et son contenu
  
  String IDchar;  // Déclaration d'une chaîne de caractères destinée à stocker l'ID de la light cible du message
  IDchar = payload.charAt(0);   // Initialisation de la précédente chaîne de caractères avec le premier caractère du message (qui débute par l'ID de la light)
  while (payload.charAt(i) != ' ') {  // On parcourt le message tant qu'on ne rencontre pas d'espace 
    IDchar += payload.charAt(i);  // on ajoute chaque caractère afin de reconstituer l'ID complet de la light
    i = i + 1;  // On passe au caractère suivant
  }
  
  if(intopic.equals(String("order"))) {   // Si l'expéditeur est order
    if(payload.indexOf("changeColor") > 0) {  // Si le message contient changeColor
      if (payload.indexOf("vert") > 0){   // Si le message contient vert
        /* client.put("/api/Ja9lPJpWsgyfL1RMJuCboPrcWt3XES-jEqSesvrE/lights/" + IDchar + "/state","application/json", "{\"hue\":25000}");   // On crée une requête put afin de changer la couleur de la lampe */
        Serial.println ("OK");  // On confirme qu'on est bien rentré dans la bonne boucle 
      }
      /*else if (payload.indexOf("bleu") > 0) {   // Si le message contient bleu
        client.put("/api/Ja9lPJpWsgyfL1RMJuCboPrcWt3XES-jEqSesvrE/lights/" + IDchar + "/state","application/json", "{\"hue\":43000}");  // On crée une requête put afin de changer la couleur de la lampe
      }
      else if (payload.indexOf("rouge") > 0) {  // Si le message contient rouge
        client.put("/api/Ja9lPJpWsgyfL1RMJuCboPrcWt3XES-jEqSesvrE/lights/" + IDchar + "/state","application/json", "{\"hue\":2000}");   // On crée une requête put afin de changer la couleur de la lampe
      }
      else if (payload.indexOf("jaune") > 0) {  // Si le message contient jaune
        client.put("/api/Ja9lPJpWsgyfL1RMJuCboPrcWt3XES-jEqSesvrE/lights/" + IDchar + "/state","application/json", "{\"hue\":12000}");  // On crée une requête put afin de changer la couleur de la lampe
      }
    }
      else if (payload.indexOf("switch") > 0){  // Si le message contient switch
        if (payload.indexOf("OFF") > 0) {   // Si le message contient OFF
          client.put("/api/Ja9lPJpWsgyfL1RMJuCboPrcWt3XES-jEqSesvrE/lights/" + IDchar + "/state","application/json", "{\"on\":false}");   // On crée une requête put afin de changer le statut de la lampe
      }
        }
        else if (payload.indexOf("ON") > 0) {   // Si le message contient ON
          client.put("/api/Ja9lPJpWsgyfL1RMJuCboPrcWt3XES-jEqSesvrE/lights/" + IDchar + "/state","application/json", "{\"on\":true}");  // On crée une requête put afin de changer le statut de la lampe
        }
      }
      else if (payload.indexOf("changeBrightness") > 0){  // Si le message contient changeBrightness
        client.put("/api/Ja9lPJpWsgyfL1RMJuCboPrcWt3XES-jEqSesvrE/lights/" + IDchar + "/state","application/json", "{\"bri\":" + payload.substring(payload.indexOf("s")+3) + "}");  // On crée une requête put afin de changer l'intensité lumineuse de la lampe
      }*/
    else {  // Si aucune des trois actions précédentes n'a été trouvée dans le message
      Serial.println("Action demanded is not available, please replace by changeColor, switch or changeBrightness.\n");   // On prévient l'utilisateur de la réception d'un message au format incompatible
    }
  }
 } 
  else {  // Si l'expéditeur n'est pas order, on n'avertit l'utilisateur que l'expéditeur est incompatible 
    Serial.println("Please, use the topic order.\n");
  }
}


void connect(){   // Fonction de connexion au réseau wifi et au serveur mqtt
  Serial.print("checking wifi...");   // On prévient que la connexion est en cours
  while (WiFi.status() != WL_CONNECTED) {   // Tant que la connexion n'est pas établie
    Serial.print(".");  // On affiche un point 
    delay(1000);   // On essaie de se connecter toutes les secondes 
  }
  
  Serial.print("Connected to wifi.\nConnecting to mqtt...");  // On prévient que la connexion a réussi 
  while (!mqtt_client.connect("arduino", "zvafqgcd", "HgxcwISOxy_z")) {   // On essaie de se connecter au serveur mqtt à l'aide de nos identifiants, le client s'appellera arduino, tant que la connexion ne s'établit pas :
    Serial.print(".");  // On affiche un point 
    delay(1000);  // On essaie de se connecter toutes les secondes
  }

  Serial.println("\nConnected!");   // On affiche qu'on est connecté 

  mqtt_client.subscribe("#");   // On écoute l'ensemble des expéditeurs possibles 
}


void setup() {  // Ensemble des actions à exécuter en début de programme 
  
  // Configurer les PIN où sont branchés les boutons en tant qu'entrées
  pinMode(PIN_ENTREE_BOUTON_1, INPUT);
  pinMode(PIN_ENTREE_BOUTON_2, INPUT);

  /*stateLight = client.get("/api/Ja9lPJpWsgyfL1RMJuCboPrcWt3XES-jEqSesvrE/lights/1/state");  // On récupère le statut actuel de la lampe à l'aide d'une requête get*/  
  /*colorLight = client.get("/api/Ja9lPJpWsgyfL1RMJuCboPrcWt3XES-jEqSesvrE/lights/1/color");  // On récupère la couleur actuelle de la lampe à l'aide d'une requête get*/
  stateLight = "ON";  // On initialise le statut de la light à ON 
  colorLight = "vert";  // On initialise la couleur de la light à vert
  
  if(stateLight.equals(String("ON"))) {   // Si la light est allumée 
    etatAllumage = 1;   // La variable etatAllumage prend la valeur 1
  }
  else {  // Si la light est éteinte
    etatAllumage = 0;   // La variable etatAllumage prend la valeur 0
  }
   
  lastMillis = 0;   // Initialisation à 0
  WiFi.begin(wifi_ssid, wifi_password);   // On crée le client qui se connectera au bridge
  mqtt_client.begin(mqtt_host, mqtt_port, wifi_client);   // On crée le client mqtt
  mqtt_client.onMessage(callback);  // A chaque message reçu, on lance la fonction callback
  connect();  // On lance la fonction connect()

  // Configuration de la connexion série
  Serial.begin(115200);
}

void loop() {   // Suite de commandes à exécuter en boucle 

  mqtt_client.loop();   // On entretient la connexion au serveur mqtt

  if (!mqtt_client.connected()) {   // Si on est plus connecté au serveur mqtt
    connect();  // On relance la fonction connect()
  }

  /* Lire l'état de chaque entrée dans deux nouvelles variables temporaires */
  bool etatSignal_1 = digitalRead(PIN_ENTREE_BOUTON_1);
  bool etatSignal_2 = digitalRead(PIN_ENTREE_BOUTON_2);
 
  if ( !etatSignal_1 )   // Si le bouton 1 semble avoir été appuyé (pas de signal, ou signal = 0/false)
  {
    
    if ( dateAntiRebond_1 != 0 )  // Si l'anti-rebond est déjà en train d'ignorer les rebonds
    {
      unsigned long tempsEcoule_1 = millis() - dateAntiRebond_1;  // Obtenir et déterminer combien de temps s'est écoulé depuis le début de l'anti-rebond ( = date actuelle - date de début)
      
      if ( tempsEcoule_1 >= TEMPS_ANTI_REBOND_MS )   // Si le temps écoulé est supérieur ou égal au temps d'anti-rebond
      {
        bouton1Appuye = true;   // Considérer alors que le bouton est appuyé
        dateAntiRebond_1 = 0;   // Arrêter l'anti-rebond
         
        /* Comme le bouton vient d'être considéré comme appuyé (front montant) c'est le moment d'incrémenter le compteur de clicks */
        compteurDeClicks_1++;
         
        /* Afficher le compteur de clicks   */
        Serial.print("Clicks : ");
        Serial.println(compteurDeClicks_1);

        /*stateLight = client.get("/api/Ja9lPJpWsgyfL1RMJuCboPrcWt3XES-jEqSesvrE/lights/1/state");  // On récupère à l'aide d'une requête get le statut actuel de la light*/
        if(stateLight.equals(String("ON"))) {   // Si la light est allumée 
          etatAllumage = 1;   // La variable etatAllumage prend la valeur 1
        }
        else {  // Si la light est éteinte
          etatAllumage = 0;   // La variable etatAllumage prend la valeur 0
        }
        
        if (etatAllumage == 1) // Si etatAllumage vaut 1
        {
          etatAllumage=0; // On le passe à 0
          Serial.print("Extinction de la light \n");  // On prévient l'utilisateur qu'on éteint la light 
          mqtt_client.publish("order", "1 changeColor vert"); // On éteint la light en publiant un message sur le serveur mqtt qui déclenchera la fonction callback
        }

        else // Si etatAllumage vaut 0
        {
          etatAllumage=1; // On le passe à 1
          Serial.print("Allumage de la light \n");  // On prévient l'utilisateur qu'on allume la light
          mqtt_client.publish("order", "1 changeColor vert"); // On allume la light en publiant un message sur le serveur mqtt qui déclenchera la fonction callback
        }
      }
    }
    
    else if ( !bouton1Appuye )  // Sinon, si le bouton 1 est actuellement considéré comme relâché
    {
      dateAntiRebond_1 = millis();  // Activer l'anti-rebond 1 en obtenant la date actuelle du système
    }
    
  /* Sinon, le bouton est déjà considéré comme appuyé, il n'y a rien à faire */
  }

  else  // Sinon, le bouton 1 semble avoir été relâché
  {
    
    if ( bouton1Appuye )  // Si le bouton 1 était appuyé
    {
      bouton1Appuye = false;  // Le considérer maintenant comme relâché
    }
    
    else   // Sinon, le bouton 1 était déjà relâché ou l'anti-rebond était en cours
    {
      dateAntiRebond_1 = 0;   // Arrêter l'anti-rebond 1 s'il était en cours
    }
  }


  if ( !etatSignal_2 )  // Si le bouton 2 semble avoir été appuyé (pas de signal, ou signal = 0/false)
  {
    
    if ( dateAntiRebond_2 != 0 )  // Si l'anti-rebond 2 est déjà en train d'ignorer les rebonds
    {
      unsigned long tempsEcoule_2 = millis() - dateAntiRebond_2;  // Obtenir et déterminer combien de temps s'est écoulé depuis le début de l'anti-rebond ( = date actuelle - date de début)
       
      if ( tempsEcoule_2 >= TEMPS_ANTI_REBOND_MS )  // Si le temps écoulé est supérieur ou égal au temps d'anti-rebond
      {
        
        bouton2Appuye = true;   // Considérer alors que le bouton 2 est appuyé
        dateAntiRebond_2 = 0;   // Arrêter l'anti-rebond
         
        compteurDeClicks_2++;   // Comme le bouton 2 vient d'être considéré comme appuyé (front montant) c'est le moment d'incrémenter le compteur de clicks 2
         
        /* Afficher le compteur de clicks */
        Serial.print("Clicks : ");
        Serial.println(compteurDeClicks_2);

        /*colorLight = client.get("/api/Ja9lPJpWsgyfL1RMJuCboPrcWt3XES-jEqSesvrE/lights/1/color");  // On récupère à l'aide d'une requête get la couleur actuelle de la light*/
        
        if (colorLight.equals(String("vert"))) // Si la couleur est verte
        {
          colorLight = "rouge"; // On actualise la nouvelle valeur de la couleur de la lampe;
          Serial.print("la lampe devient rouge \n");  // On prévient l'utilisateur de la nouvelle couleur 
          mqtt_client.publish("order", "1 changeColor rouge"); // On actualise la nouvelle couleur en publiant un message sur le serveur mqtt qui déclenchera la fonction callback
        }

        else if (colorLight.equals(String("rouge"))) // Si la couleur est rouge
        {
          colorLight = "bleu"; // On actualise la nouvelle valeur de la couleur de la lampe;
          Serial.print("la lampe devient bleue \n");  // On prévient l'utilisateur de la nouvelle couleur
          mqtt_client.publish("order", "1 changeColor bleu"); // On actualise la nouvelle couleur en publiant un message sur le serveur mqtt qui déclenchera la fonction callback
        }

        else if (colorLight.equals(String("bleu"))) // Si la couleur est bleue
        {
          colorLight = "jaune"; // On actualise la nouvelle valeur de la couleur de la lampe;
          Serial.print("la lampe devient jaune \n");  // On prévient l'utilisateur de la nouvelle couleur
          mqtt_client.publish("order", "1 changeColor jaune"); // On actualise la nouvelle couleur en publiant un message sur le serveur mqtt qui déclenchera la fonction callback
        }

        else if (colorLight.equals(String("jaune"))) //si la couleur est jaune
        {
          colorLight = "vert"; // On actualise la nouvelle valeur de la couleur de la lampe;
          Serial.print("la lampe devient verte \n");  // On prévient l'utilisateur de la nouvelle couleur
          mqtt_client.publish("order", "1 changeColor vert"); // On actualise la nouvelle couleur en publiant un message sur le serveur mqtt qui déclenchera la fonction callback
        }
      }
    }
    
    else if ( !bouton2Appuye )  // Sinon, si le bouton 2 est actuellement considéré comme relâché
    {
      dateAntiRebond_2 = millis();  // Activer l'anti-rebond 2 en obtenant la date actuelle du système
    }
    
  /* Sinon, le bouton 2 est déjà considéré comme appuyé, il n'y a rien à faire */
  }

  else  // Sinon, le bouton 2 semble avoir été relâché
  {
    
    if ( bouton2Appuye )  // Si le bouton 2 était appuyé
    {
      bouton2Appuye = false;  // Le considérer maintenant comme relâché
    }
    
    else  // Sinon, le bouton 2 était déjà relâché ou l'anti-rebond 2 était en cours
    {
      dateAntiRebond_2 = 0;   // Arrêter l'anti-rebond 2 s'il était en cours
    }
  }
}
