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
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author MarcoSilva
 */
public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Socket socket = null;

        BufferedReader in = null;

        BufferedWriter out = null;

        try {
            socket = new Socket("localhost", 12345);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
        }

        String buffer = "init";

        boolean inicio_sessao = false;

        Scanner sc = new Scanner(System.in);

        try {

            while (!inicio_sessao) {

                System.out.println("Bem vindo ao Overwatch!\nPara se registar selecione 1.\nSe já tem uma conta, selecione 2 para iniciar sessão.");

                String option = sc.nextLine();

                boolean username_valido = false;

                if (option.equals("1")) {
                    while (!username_valido) {
                        System.out.print("Introduza o nome de utilizador que pretende associar à sua conta: ");
                        buffer = sc.nextLine();

                        out.write("1");
                        out.newLine();
                        out.flush();

                        out.write(buffer);
                        out.newLine();
                        out.flush();

                        if (in.readLine().equals("OK")) {
                            username_valido = true;
                            System.out.print("O nome de utilizador encontra-se disponível.\nIntroduza uma palavra passe de registo: ");
                            buffer = sc.nextLine();
                            out.write(buffer);
                            out.newLine();
                            out.flush();
                            inicio_sessao = true;
                        } else { //talvez precisamos aqui de uma var status para verificar o que o servidor respondeu
                            System.out.print("O nome de utilizador não se encontra disponível.\nSe pretender cancelar o processo de registo selecione a opção 0.");
                            if (sc.nextLine().equals("0")) {
                                break;
                            }
                        }

                    }
                } else if (option.equals("2")) {
                    boolean username_existe = false;

                    while (!username_existe) {
                        System.out.print("Introduza o seu nome de utilizador: ");
                        buffer = sc.nextLine();
                        out.write(buffer);
                        out.newLine();
                        out.flush();

                        buffer = in.readLine();

                        if (buffer.equals("username válido")) {
                            username_existe = true;

                            boolean password_valida = false;

                            while (!password_valida) {
                                System.out.print("Introduza a sua palavra passe: ");
                                buffer = sc.nextLine();
                                out.write(buffer);
                                out.newLine();
                                out.flush();
                                if (in.readLine().equals("palavra-passe válida")) {
                                    password_valida = true;
                                    inicio_sessao = true;
                                }
                            }
                        }
                    }
                }

            }
            
            System.out.println("Iniciou sessão com sucesso");

            socket.shutdownOutput();
            socket.shutdownInput();
            socket.close();
        } catch (IOException e) {
        }

    }
}
