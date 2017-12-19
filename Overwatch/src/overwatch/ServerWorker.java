/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overwatch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

/**
 *
 * @author MarcoSilva
 */
public class ServerWorker implements Runnable {

    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private Map<String, Jogador> jogadores;

    public ServerWorker(Socket socket, Map<String, Jogador> jogadores) {
        this.socket = socket;
        this.jogadores = jogadores;

        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Erro no establecimento da ligação.");
        }
    }

    public void writeLine(BufferedWriter out, String value) throws IOException {
        out.write(value);
        out.newLine();
        out.flush();
    }

    public void run() {

        String username = null;
        String password = null;

        try {

            String option = in.readLine();
			System.out.println(option);

            if (option.equals("1")) {
                boolean registou = false;
                
                while (!registou) {
                    username = in.readLine();
                    if (this.jogadores.containsKey(username)) {
                        out.println("Indisponivel");
			//out.write("indisponivel");
                    } else {
						out.println("OK");
//                        out.write("OK");
//                        out.newLine();
//                        out.flush();

                        password = in.readLine();
                        Jogador novo_jogador = new Jogador(username, password);
                        this.jogadores.put(username, novo_jogador);
                        registou = true;
                    }
                }
            } else if (option.equals("2")) {
				
                username = in.readLine();
                if (!this.jogadores.containsKey(username)){
					out.println("nao existe");
//                    out.write("nao existe");
//                    out.newLine();
//                    out.flush();
                }
                else {
					
					out.println("username válido");
//                    out.write("username válido");
//                    out.newLine();
//                    out.flush();
                    
                    password = in.readLine();
                    Jogador aux = this.jogadores.get(username);
                    if (aux.checkPassword(password)){
						out.println("palavra-passe válida");
//                        out.write("palavra-passe válida");
//                        out.newLine();
//                        out.flush();
                    }
                    else {
						out.println("palavra-passe errada");
//                        out.write("palavra-passe errada");
//                        out.newLine();
//                        out.flush();
                    }
                }
            }

            this.in.close();
            this.out.close();
            this.socket.close();

        } catch (IOException e) {
        }
    }

}