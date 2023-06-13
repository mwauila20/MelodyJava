import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.List;

public class Server {
    private static final int SERVER_PORT = 8888; // Порт сервера

    private ServerSocket serverSocket;
    private List<ClientHandler> clients;
    private List<String> songAnswers;
    private int number_variants = 7;
    private Integer[] songs_variants = new Integer[number_variants];

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public Server() {
        clients = new ArrayList<>();
        songAnswers = new ArrayList<>();
        
        initializeSongsAndAnswers();
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server started on port " + SERVER_PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);

                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void initializeSongsAndAnswers() {
    songAnswers.add("Katy Perry - Hot n Cold.wav");
    songAnswers.add("Kendrick Lamar - HUMBLE.wav");
    songAnswers.add("Lil Nas X - Old Town Road.wav");
    songAnswers.add("Polo G-Finer Things.wav");
    songAnswers.add("Taylor Swift - Blank Space.wav");
    songAnswers.add("Travis Scott and Drake - Sicko Mode.wav");
    songAnswers.add("Wiz Khalifa-Black and Yellow.wav");
    }

    private class ClientHandler implements Runnable {
        private Socket clientSocket;
        private BufferedReader reader;
        private PrintWriter writer;
        private int score;
        private String right_answer;
        private int index_right_answer;
        

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            score = 0;
        }

        @Override
        public void run() {
            try {
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writer = new PrintWriter(clientSocket.getOutputStream(), true);

                String clientData;
                while ((clientData = reader.readLine()) != null) {
                    // Обработка данных от клиента
                    processClientData(clientData);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Закрытие сокета и потоков при отключении клиента
                try {
                    reader.close();
                    writer.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        private void chooseRandomSong() {
            Random random = new Random();
                            
            //Случайный выбор позиции правильного ответа
            for (int i = 0; i < number_variants; i++)
            {
                songs_variants[i] = i;
            }                

            for (int i = number_variants; i < songAnswers.size(); i++)
            {
                int j = random.nextInt(i);
                if (j < number_variants)
                    songs_variants[j] = i;
            }
            
            index_right_answer = random.nextInt(7);
            right_answer = (String) songAnswers.toArray()[songs_variants[index_right_answer]];
        }

        
        private void processClientData(String clientData) {
            
            Random random = new Random();
            // Логика обработки данных от клиента и отправки данных другим клиентам
            // Здесь вы можете проверять время ответа, обновлять статистику и т.д.

            // Пример:
            if (clientData.equals("START")) {
                // Логика начала игры
                // Отправить данные клиенту
                score = 0;
                chooseRandomSong();
                //writer.println("SONG_RIGHT: " + right_answer);
                writer.println("SONGS: " + (String) songAnswers.toArray()[songs_variants[0]] + "," + (String) songAnswers.toArray()[songs_variants[1]]
                        + "," + (String) songAnswers.toArray()[songs_variants[2]]   + "," + (String) songAnswers.toArray()[songs_variants[3]] + "," + (String) songAnswers.toArray()[songs_variants[4]] + "," + (String) songAnswers.toArray()[songs_variants[5]] + "," + (String) songAnswers.toArray()[songs_variants[6]]);
                writer.println("SCORE: " + Integer. toString(score));
                writer.println("PLAY_SONG: " + (String) songAnswers.toArray()[songs_variants[index_right_answer]]);
            } else if (clientData.equals("NEXT")) {
                // Логика перехода к следующей песне
                // Отправить данные клиенту
                chooseRandomSong();
                writer.println("SONGS: " + (String) songAnswers.toArray()[songs_variants[0]] + "," + (String) songAnswers.toArray()[songs_variants[1]]
                        + "," + (String) songAnswers.toArray()[songs_variants[2]] + "," + (String) songAnswers.toArray()[songs_variants[3]] + "," + (String) songAnswers.toArray()[songs_variants[4]] + "," + (String) songAnswers.toArray()[songs_variants[5]] + "," + (String) songAnswers.toArray()[songs_variants[6]]);
                writer.println("SCORE: " + Integer. toString(score));
                writer.println("PLAY_SONG: " + (String) songAnswers.toArray()[songs_variants[index_right_answer]]);
            } else if (clientData.startsWith("ANSWER: ")) {
                // Логика проверки ответа клиента
                String answer = clientData.substring(8);
                if (answer.equals(right_answer)){
                    score = score + 10;
                    writer.println("CORRECT");
                    writer.println("SCORE: " + Integer. toString(score));
                }
                else{
                    writer.println("ERROR");
                    if (score > 0){
                        score = score - 10;
                    }
                    writer.println("SCORE: " + Integer. toString(score));
                }
                // Проверить ответ и отправить данные клиенту
            }
            else if (clientData.startsWith("TIMEOUT")) {
                if (score > 0){
                        score = score - 10;
                }
                writer.println("TIMEOUT");
                writer.println("SCORE: " + Integer. toString(score));
            }
        }
    }
}
