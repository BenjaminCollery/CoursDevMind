package com.esme.spring.faircorp.hello1;


import org.eclipse.paho.client.mqttv3.*;

import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class SimpleMqttClient {

    public static void sendMqtt(String msg , String tp) {


    String topic        = tp;
    // String content      = msg;
    int qos             = 2;
    String broker       = "ssl://m21.cloudmqtt.com:26964";
    String clientId     = "zvafqgcd";
    MemoryPersistence persistence = new MemoryPersistence();
        System.out.println("salut 1 ");

            try {
        MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
                System.out.println("salut 2 ");
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);

        connOpts.setUserName("zvafqgcd");
        connOpts.setPassword("HgxcwISOxy_z".toCharArray());      // login et mdp

        System.out.println("Connecting to broker: "+broker);
        sampleClient.connect(connOpts);


        System.out.println("Connected");
        System.out.println("Publishing message: "+ msg);

        MqttMessage message = new MqttMessage(msg.getBytes());
        message.setQos(qos);
        sampleClient.publish(topic, message);
        System.out.println("Message published");

        sampleClient.subscribe("order");

                sampleClient.setCallback(new MqttCallback() {
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


        //sampleClient.disconnect();
        //System.out.println("Disconnected");

    } catch(MqttException me) {
        System.out.println("reason "+me.getReasonCode());
        System.out.println("msg "+me.getMessage());
        System.out.println("loc "+me.getLocalizedMessage());
        System.out.println("cause "+me.getCause());
        System.out.println("excep "+me);
        me.printStackTrace();
    }
    }
}