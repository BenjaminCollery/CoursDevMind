package com.esme.spring.faircorp.hello1;


import org.eclipse.paho.client.mqttv3.*;

import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class SimpleMqttClient {

    private static MqttClient generalClient;

    public static void connecting(){

        System.out.println("connecting");

        String broker       = "ssl://m21.cloudmqtt.com:26964";
        String clientId     = "zvafqgcd";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            System.out.println("salut 2 ");
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);


            connOpts.setUserName("zvafqgcd");
            connOpts.setPassword("HgxcwISOxy_z".toCharArray());      // login et mdp

            System.out.println("Connecting to broker: " + broker);
            sampleClient.connect(connOpts);

            generalClient = sampleClient;
            System.out.println("Connected");
        }
        catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }


        try {
            generalClient.subscribe("order");
            generalClient.setCallback(new MqttCallback() {
                public void connectionLost(Throwable cause) {}

                public void messageArrived(String topic,
                                           MqttMessage message)
                        throws Exception {
                    System.out.println(message.toString());

                    String[] parsedMsg = new String[5];

                    parsedMsg = message.toString().split(" ");

                    // utiliser le message parser pour modifier correctement la base de donnees
                }

                public void deliveryComplete(IMqttDeliveryToken token) {}
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }


    }

    public static void publishMqtt(String msg , String tp) {


        String topic        = tp;
        int qos             = 2;

        System.out.println("Publishing message: "+ msg);

        MqttMessage message = new MqttMessage(msg.getBytes());
        message.setQos(qos);

        try {
            generalClient.publish(topic, message);
            System.out.println("Message published");
        } catch (MqttException e) {
            e.printStackTrace();
        }


    }

    public void disconnectMqtt(){
        try {
            generalClient.disconnect();
            System.out.println("Disconnected");
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

}