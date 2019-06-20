import java.io.*;
import java.net.*;
import java.util.regex.*;

public class Main {

    public static void main(String[] args) {
        BinaryTree<Integer> intTree = new BinaryTree<>();
        BinaryTree<Double> doubTree = new BinaryTree<>();
        BinaryTree<String> strTree = new BinaryTree<>();
        short mode = 0;


        try {
            ServerSocket myServerSocket = new ServerSocket(9999);

            while (mode != 4) {
                Socket skt = myServerSocket.accept();

                BufferedReader Input = new BufferedReader(new InputStreamReader(skt.getInputStream())); //odczyt
                PrintStream Output = new PrintStream(skt.getOutputStream());

                //Reading inpur from the stream
                String buffer = Input.readLine();
                String[] parts = buffer.split("~");
                String command = parts[0];
                String buf = parts[1];

                Pattern integerPattern = Pattern.compile("^[0-9]+$");
                Matcher integerMatcher = integerPattern.matcher(buf);
                Pattern doublePattern = Pattern.compile("^[0-9]+.[0-9]+$");
                Matcher doubleMatcher = doublePattern.matcher(buf);

                System.out.print(command);

                if (command != null) {
                    intTree.tree = "";
                    doubTree.tree = "";
                    strTree.tree = "";
                    switch (command) {
                        case "create":
                            if (buf.equals("int")) {
                                intTree = new BinaryTree<>();
                                mode = 1;
                            } else if (buf.equals("doub")) {
                                doubTree = new BinaryTree<>();
                                mode = 2;
                            } else {
                                strTree = new BinaryTree<>();
                                mode = 3;
                            }
                        case "add":
                            if (mode == 1 && integerMatcher.matches()) {
                                intTree.add(Integer.parseInt(buf));
                                Output.print("Added~" + intTree.treeToString(intTree.root));
                            } else if (mode == 2 && doubleMatcher.matches()) {
                                doubTree.add(Double.parseDouble(buf));
                                Output.print("Added~" + doubTree.treeToString(doubTree.root));
                            } else if (mode == 3) {
                                strTree.add(buf);
                                Output.print("Added~" + strTree.treeToString(strTree.root));
                            }
                            break;
                        case "search":
                            if (mode == 1 && integerMatcher.matches()) {
                                if (intTree.search(Integer.parseInt(buf)) != null) {
                                    Output.print("Object found~" + intTree.treeToString(intTree.root));
                                } else {
                                    Output.print("Object not found~" + intTree.treeToString(intTree.root));
                                }
                            } else if (mode == 2 && doubleMatcher.matches()) {
                                if (intTree.search(Integer.parseInt(buf)) != null) {
                                    Output.print("Object found~" + doubTree.treeToString(doubTree.root));
                                } else {
                                    Output.print("Object not found~" + doubTree.treeToString(doubTree.root));
                                }
                            } else if (mode == 3) {
                                if (intTree.search(Integer.parseInt(buf)) != null) {
                                    Output.print("Object found~" + strTree.treeToString(strTree.root));
                                } else {
                                    Output.print("Object not found~" + strTree.treeToString(strTree.root));
                                }
                            }
                            break;
                        case "delete":
                            if (mode == 1 && integerMatcher.matches()) {
                                intTree.delete(Integer.parseInt(buf));
                                Output.print("Deleted~" + intTree.treeToString(intTree.root));
                            } else if (mode == 2 && doubleMatcher.matches()) {
                                doubTree.delete(Double.parseDouble(buf));
                                Output.print("Deleted~" + doubTree.treeToString(doubTree.root));
                            } else if (mode == 3) {
                                strTree.delete(buf);
                                Output.print("Deleted~" + strTree.treeToString(strTree.root));
                            }
                            break;
                    }
                }

                skt.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
