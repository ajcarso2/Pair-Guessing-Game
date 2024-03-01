package client;

import java.net.*;
import java.io.*;

import org.json.*;

import buffers.RequestProtos.Request;
import buffers.ResponseProtos.Response;
import buffers.ResponseProtos.Entry;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

class SockBaseClient {

    public static void main (String args[]) throws Exception {
        Socket serverSock = null;
        OutputStream out = null;
        InputStream in = null;
        int port = 9099; // default port

        // Make sure two arguments are given
        if (args.length != 2) {
            System.out.println("Expected arguments: <host(String)> <port(int)>");
            System.exit(1);
        }
        String host = args[0];
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException nfe) {
            System.out.println("[Port] must be integer");
            System.exit(2);
        }

        // Ask user for username
        System.out.println("Please provide your name for the server.");
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String strToSend = stdin.readLine();

        // Build the first request object just including the name
        Request op = Request.newBuilder()
                .setOperationType(Request.OperationType.NAME)
                .setName(strToSend).build();
        Response response;
        try {
            // connect to the server
            serverSock = new Socket(host, port);

            // write to the server
            out = serverSock.getOutputStream();
            in = serverSock.getInputStream();

            op.writeDelimitedTo(out);

            // read from the server
            response = Response.parseDelimitedFrom(in);

            // print the server response. 
            System.out.println(response.getMessage());

            System.out.println("* \nWhat would you like to do? \n 1 - to see the leader board \n 2 - to enter a game \n 3 - quit the game");

            Scanner scanner = new Scanner(System.in);

            while (true) {

                String input = scanner.nextLine();

                if (input.equals("1")) {
                    // send leaderboard request to server and handle response
                    Request request = Request.newBuilder()
                            .setOperationType(Request.OperationType.LEADER)
                            .build();
                    request.writeDelimitedTo(out);

                    Response resp = Response.parseDelimitedFrom(in);
                    // handle response accordingly
                } else if (input.equals("2")) {
                    // send new game request to server and handle response
                    Request request = Request.newBuilder()
                            .setOperationType(Request.OperationType.NEW)
                            .build();
                    request.writeDelimitedTo(out);

                    boolean oneTileFlipped = false;

                    Response resp = Response.parseDelimitedFrom(in);
                    // handle response accordingly
                    do{
                        System.out.println(resp.getBoard());
                        System.out.println("Please enter the row and column of the tile you want to flip (e.g. a1)");
                        String inputed = scanner.nextLine();
                        if(oneTileFlipped){
                            Request request3 = Request.newBuilder()
                                    .setOperationType(Request.OperationType.TILE2)
                                    .setTile(inputed)
                                    .build();
                            request3.writeDelimitedTo(out);
                            resp = Response.parseDelimitedFrom(in);
                            System.out.println(resp.getBoard());
                            oneTileFlipped = false;
                        }else{
                            Request request3 = Request.newBuilder()
                                    .setOperationType(Request.OperationType.TILE1)
                                    .setTile(inputed)
                                    .build();
                            request3.writeDelimitedTo(out);
                            resp = Response.parseDelimitedFrom(in);
                            System.out.println(resp.getBoard());
                            oneTileFlipped = true;
                        }
                    }while(Response.ResponseType.PLAY == resp.getResponseType());
                    // perform the game steps (TILE1, TILE2) until the game is won or user quits
                    // and handle each response from the server accordingly
                } else if (input.equals("3")) {
                    // send quit request to server and handle response
                    Request request = Request.newBuilder()
                            .setOperationType(Request.OperationType.QUIT)
                            .build();
                    request.writeDelimitedTo(out);

                    Response resp = Response.parseDelimitedFrom(in);
                    // handle response accordingly

                    // close input/output streams and socket, then exit the program
                    out.close();
                    in.close();
                    serverSock.close();
                    System.exit(0);
                } else {
                    System.out.println("Invalid option, please try again");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null)   in.close();
            if (out != null)  out.close();
            if (serverSock != null) serverSock.close();
        }
    }
}


