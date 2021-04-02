/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kursach.client;

import SeparatorGeneration.SeparatorGeneration;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Gagadok
 */
public class Connection extends SeparatorGeneration{

    private int port = 2525;
    private String address = "127.0.0.1";
    private Socket cs;
    private DataInputStream input;
    private DataOutputStream output;

    public Connection(String Address, int Port) throws IOException {
        this.address = Address;
        this.port = Port;
        cs = new Socket(address, port);
        input = new DataInputStream(cs.getInputStream());
        output = new DataOutputStream(cs.getOutputStream());
    }

    public void send(String data) throws IOException { // Отправка данных на сервер
        output.writeUTF(data);
    }

    public void send(String[] data) throws IOException { // Отправка данных на сервер
        output.writeUTF(separatorGeneration(data));
        output.flush();
    }
    
    public String receive() throws IOException { // Получение данных с сервера
        String data = input.readUTF();
        return data;
    }

    public void close() throws IOException {
        cs.close();
        input.close();
        output.close();
    }
}
