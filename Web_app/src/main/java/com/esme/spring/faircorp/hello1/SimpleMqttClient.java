package com.esme.spring.faircorp.hello1;


import org.eclipse.paho.client.mqttv3.*;

import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

// Création de la classe du client MQTT permettant de communiquer avec le cloud
public class SimpleMqttClient {

    private static MqttClient generalClient;    // Déclaration d'un client MQTT

    public static void connecting(){    // Création de la méthode de connection

        System.out.println("connecting");   // On affiche qu'on essaie de se connecter

        String broker       = "ssl://m21.cloudmqtt.com:26964";  // On fournit l'adresse du cloud
        String clientId     = "zvafqgcd";   // On fournit l'identifiant du client
        MemoryPersistence persistence = new MemoryPersistence();    // déclaration et initialisation d'une nouvelle mémoire persistante

        // On continue de lire le try jusqu'à la fin tant qu'aucune erreur ne survient
        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);    // Initialisation d'un nouveau client MQTT
            System.out.println("salut 2 ");     // Affichage de debugging
            MqttConnectOptions connOpts = new MqttConnectOptions();     // Déclaration et initialisation des options de connexions
            connOpts.setCleanSession(true);     // Nettoyage des précédentes options de connexion


            connOpts.setUserName("zvafqgcd");   // Identifiant du client
            connOpts.setPassword("HgxcwISOxy_z".toCharArray());      // Mot de passe du client

            System.out.println("Connecting to broker: " + broker);  // Affichage de la tentative de connexion au broker
            sampleClient.connect(connOpts);     // Tentative de connexion au broker en utilisant les options

            generalClient = sampleClient;   // Récupération du client
            System.out.println("Connected");    // Affichage de la confirmation de connexion
        }

        // Si une erreur survient dans le try, on rentre dans le catch et on efface tout ce qui a pu se passer lors du try
        catch(MqttException me) {

            // Affichage du descriptif de l'erreur survenue
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }

        // On continue de lire le try jusqu'à la fin tant qu'aucune erreur ne survient
        try {
            generalClient.subscribe("order");   // On souscrit à order
            generalClient.setCallback(new MqttCallback() {  // On crée une fonction callback qui sera exécutée en fin de programme
                public void connectionLost(Throwable cause) {}  // Méthode en cas de perte de connection

                public void messageArrived(String topic, MqttMessage message) throws Exception {    // Méthode gérant la réception d'un message
                    System.out.println(message.toString());     // Afficher le message reçu

                    String[] parsedMsg = new String[5];     // Déclaration de la chaîne qui recevra les différentes parties du message (action, id, valeur...)

                    parsedMsg = message.toString().split(" ");  // Partition du message reçu - utiliser le message parser pour modifier correctement la base de donnees
                }

                public void deliveryComplete(IMqttDeliveryToken token) {}   // Réception du message achevée
            });
        }

        // Si une erreur se produit lors du try
        catch (MqttException e) {
            e.printStackTrace();    // Affiche l'exception et l'état de la pile d'exécution lors de l'erreur
        }


    }


    // Méthode de publication d'un message par le client vers le cloud
    public static void publishMqtt(String msg , String tp) {


        String topic = tp;  // Affectation du canal d'écoute
        int qos = 2;    // Affectation de la valeur de la qualité de service

        System.out.println("Publishing message: "+ msg);    // Affichage du déroulement de la publication du message et de son contenu

        MqttMessage message = new MqttMessage(msg.getBytes());  // Déclaration d'un message MQTT formaté avec le nombre de bytes nécessaires
        message.setQos(qos);    // Fixation de la valeur de la qualité de service du message

        // On continue de lire le try jusqu'à la fin tant qu'aucune erreur ne survient
        try {
            generalClient.publish(topic, message); // On publie le message sur le canal topic
            System.out.println("Message published");
        }

        // Si une erreur se produit lors du try
        catch (MqttException e) {
            e.printStackTrace();    // Affiche l'exception et l'état de la pile d'exécution lors de l'erreur
        }


    }

    // Méthode de déconnection du client MQTT
    public void disconnectMqtt(){

        // On continue de lire le try jusqu'à la fin tant qu'aucune erreur ne survient
        try {
            generalClient.disconnect();     // Déconnection du client
            System.out.println("Disconnected");     // Affichage de la confirmation de la déconnection
        }

        // Si une erreur se produit lors du try
        catch (MqttException e) {
            e.printStackTrace();    // Affiche l'exception et l'état de la pile d'exécution lors de l'erreur
        }

    }

}