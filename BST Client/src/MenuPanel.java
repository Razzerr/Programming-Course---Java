import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class MenuPanel extends JFrame implements ActionListener {
    int width = 1280, height = 720;
    DrawingPanel draw;
    JButton createInt, createDoub, createStr, add, search, delete, reset;
    JTextField addIn, searchIn, deleteIn, process;
    JPanel createMenu, treeOptionsMenu;

    MenuPanel() {
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);

        draw = new DrawingPanel(width - 280, height);
        draw.setLayout(null);
        draw.setBounds(280, 0, width - 280, height);
        draw.setVisible(false);

        createMenu = new JPanel();
        createMenu.setLayout(null);
        createMenu.setBounds(0, 220, width, height - 220);

        treeOptionsMenu = new JPanel();
        treeOptionsMenu.setLayout(null);
        treeOptionsMenu.setBounds(0, 0, 280, height);

        createInt = new JButton("Integer");
        createInt.setBounds(0, 0, width / 3, height - 220);
        createInt.setActionCommand("create~int");
        createInt.addActionListener(this);

        createDoub = new JButton("Double");
        createDoub.setBounds(width / 3, 0, width / 3, height - 220);
        createDoub.setActionCommand("create~doub");
        createDoub.addActionListener(this);

        createStr = new JButton("String");
        createStr.setBounds(2 * width / 3, 0, width / 3, height - 220);
        createStr.setActionCommand("create~str");
        createStr.addActionListener(this);

        add = new JButton("Add");
        add.setBounds(0, 0, 140, 50);
        add.setActionCommand("add~");
        add.addActionListener(this);

        addIn = new JTextField("");
        addIn.setBounds(140, 0, 140, 50);

        delete = new JButton("Delete");
        delete.setBounds(0, 50, 140, 50);
        delete.setActionCommand("delete~");
        delete.addActionListener(this);

        deleteIn = new JTextField("");
        deleteIn.setBounds(140, 50, 140, 50);

        search = new JButton("Search");
        search.setBounds(0, 100, 140, 50);
        search.setActionCommand("search~");
        search.addActionListener(this);

        searchIn = new JTextField("");
        searchIn.setBounds(140, 100, 140, 50);

        reset = new JButton("Reset");
        reset.setBounds(0, 150, 280, 50);
        reset.setActionCommand("reset");
        reset.addActionListener(this);

        process = new JTextField("");
        process.setBounds(0, 200, 280, 50);

        createMenu.add(createInt);
        createMenu.add(createDoub);
        createMenu.add(createStr);

        treeOptionsMenu.add(add);
        treeOptionsMenu.add(addIn);
        treeOptionsMenu.add(delete);
        treeOptionsMenu.add(deleteIn);
        treeOptionsMenu.add(search);
        treeOptionsMenu.add(searchIn);
        treeOptionsMenu.add(reset);
        treeOptionsMenu.add(process);
        treeOptionsMenu.setVisible(false);

        this.add(createMenu);
        this.add(treeOptionsMenu);
        this.add(draw);
    }

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "create~int":
                createMenu.setVisible(false);
                treeOptionsMenu.setVisible(true);
                draw.setVisible(true);
                send("create~int");
                break;
            case "create~doub":
                createMenu.setVisible(false);
                treeOptionsMenu.setVisible(true);
                draw.setVisible(true);
                send("create~doub");
                break;
            case "create~str":
                createMenu.setVisible(false);
                treeOptionsMenu.setVisible(true);
                draw.setVisible(true);
                send("create~str");
                break;
            case "add~":
                send("add~" + addIn.getText());
                break;
            case "delete~":
                send("delete~" + deleteIn.getText());
                break;
            case "search~":
                send("search~" + searchIn.getText());
                break;
            case "reset":
                createMenu.setVisible(true);
                treeOptionsMenu.setVisible(false);
                draw.setVisible(false);
                generalReset();
                break;
        }
        textFieldReset();
    }

    private void send(String command) {
        String host = "localhost";
        int port = 9999;

        try {
            System.out.println("Klient: Próba podłączenia do serwera jako host-" + host + " port: " + port + '.');
            Socket skt = new Socket(host, port);

            BufferedReader Input = new BufferedReader(new InputStreamReader(skt.getInputStream())); //odczyt
            PrintStream Output = new PrintStream(skt.getOutputStream());

            //Sending command to the server:
            Output.println(command);

            String buffer = Input.readLine();
            if (buffer != null) {
                String[] parts = buffer.split("~");
                String action = parts[0];
                String buf = parts[1];
                process.setText(action);
                draw.tree = buf.toCharArray();

                System.out.print(buf);
            }
            draw.repaint();

            skt.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

}
    void textFieldReset() {
        addIn.setText("");
        deleteIn.setText("");
        searchIn.setText("");
    }

    void generalReset(){
        textFieldReset();
        draw.tree = null;
        draw.repaint();
    }
}


