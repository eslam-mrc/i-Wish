
import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Eslam
 */
public class Streams {

    boolean flagLogin = false;
    public static boolean flagRegCompleted = true;

    public static String myUsername = "Sam";
    String serverReply = "defaultValue";
    DefaultTableModel tableModel;//Radwa
    DefaultTableModel ptableModel;//Radwa
    String[] columnNames;//Radwa
    String[] pcolumnNames;//Radwa
    DefaultTableModel ftableModel;//Radwa
    String[] fcolumnNames;//Radwa
    public Socket server;
    public DataInputStream dis;
    public PrintStream pos;
    public JOptionPane jop;
    Streams stream;

    public Streams() {

        try {
            server = new Socket("127.0.0.1", 5005);
            dis = new DataInputStream(server.getInputStream());
            pos = new PrintStream(server.getOutputStream());
            columnNames = new String[]{"Product ID", "Product Name", "Prorduct Price", "Amount of contribution"};//Radwa
            tableModel = new DefaultTableModel(null, columnNames);  //Radwa
            fcolumnNames = new String[]{"Product ID", "Product Name", "Prorduct Price", "Amount of contribution"};//Radwa
            ftableModel = new DefaultTableModel(null, fcolumnNames);  //Radwa
            pcolumnNames = new String[]{"Product ID", "Product Name", "Product Price"};//Radwa
            ptableModel = new DefaultTableModel(null, pcolumnNames);  //Radwa
        } catch (ConnectException ex) {
            jop = new JOptionPane("sleepy");
            jop.showMessageDialog(null, "Server is asleep");
            System.exit(0);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    while (true) {
                        // revceived data from server
                        String str = dis.readLine();
                        if (str == null) {
                            jop.showMessageDialog(null, "Hmm. Something is wrong " + myUsername + "\n"
                                    + "We will be back in no time \n"
                                    + "So, try again in a min?");
                            System.exit(0);
                        } else {
                            UserDTO data = new Gson().fromJson(str, UserDTO.class);
                            serverReply = data.tag;

                            if (myUsername.equalsIgnoreCase(data.username)) {
                                //System.out.println(serverReply); //this is just a check
                                switch (serverReply) {
                                    case "UserAlreadyExists": //when the user registers with a username that already exists
                                        flagRegCompleted = false;
                                        RegisterForm.labelUserExist.setVisible(true);
                                        break;
                                    case "UserDoesntExist": //when the user tries to log in with a username that doesn't exist or a wrong password
                                        login.labelInvalidInput.setVisible(true);
                                        break;
                                    case "AccessGranted": //when the user both exists and uses the correct password
                                        //stream = new Streams();
                                        Home home = new Home(login.mainlogin.stream);
                                        home.setVisible(true);
                                        home.pack();
                                        home.setLocationRelativeTo(null);
                                        home.setDefaultCloseOperation(login.EXIT_ON_CLOSE);
                                        login.mainlogin.dispose();
//                                        login log = new login();
//                                        log.dispose();   
                                        System.out.println("I'm in the acces granted case"); //this is just a check
                                        break;
//////////////////////////////////////SARA///////////////////////////////////////////////////////////////////

                                    case "ServerReplyRemoveFriend":
                                        System.out.println("Server Replied Remove Friend!");
                                        //JOptionPane.showMessageDialog(null,"Your friend has been deleted!","Remove Friend",JOptionPane.WARNING_MESSAGE);
                                        //myfriends.friendsTable.getModel().fireTableDataChanged();
                                        break;

                                    case "ServerReplyViewFriendRequests":
                                        System.out.println("Server Replied View Friend Requests!");
                                        DefaultTableModel friendRequestssModel = (DefaultTableModel) friendsrequest.friendRequestsTable.getModel();
                                        Object rowFriendRequestsData[] = new Object[1];

                                        for (int i = 0; i < data.FriendRequestsArr.size(); i++) {
                                            rowFriendRequestsData[0] = data.FriendRequestsArr.get(i);
                                            //System.out.println(data.FriendsArr.get(i));
                                            friendRequestssModel.addRow(rowFriendRequestsData);
                                        }

                                        break;
                                    case "ServerReplyAddFriend":
                                        //Pop Up a Notification to the other userr here!!
                                        System.out.println("Server Replied Add Friend!");

                                        DefaultTableModel updatedUsersModel = (DefaultTableModel) addfriend.jTable1.getModel();
                                        updatedUsersModel.setRowCount(0);
                                        Object updatedRowUsersData[] = new Object[1];

                                        for (int i = 0; i < data.AllUsersArr.size(); i++) {
                                            updatedRowUsersData[0] = data.AllUsersArr.get(i);
                                            //System.out.println(data.AllUsersArr.get(i));
                                            updatedUsersModel.addRow(updatedRowUsersData);
                                        }

                                        break;

                                    case "ServerReplyViewAllUsers":
                                        System.out.println("Server Replied View All Users List!");
                                        DefaultTableModel usersModel = (DefaultTableModel) addfriend.jTable1.getModel();
                                        Object rowUsersData[] = new Object[1];

                                        for (int i = 0; i < data.AllUsersArr.size(); i++) {
                                            rowUsersData[0] = data.AllUsersArr.get(i);
                                            //System.out.println(data.FriendsArr.get(i));
                                            usersModel.addRow(rowUsersData);
                                        }
                                        break;

                                    case "ServerReplyViewMyFriendList":  //SARA Friends List
                                        System.out.println("Server Replied View Friend List!");

                                        DefaultTableModel model = (DefaultTableModel) myfriends.friendsTable.getModel();
                                        Object rowData[] = new Object[1];

                                        for (int i = 0; i < data.FriendsArr.size(); i++) {
                                            rowData[0] = data.FriendsArr.get(i);
                                            //System.out.println(data.FriendsArr.get(i));
                                            model.addRow(rowData);
                                        }

                                        break;

                                    case "ServerReplyAcceptRequest":
                                        System.out.println("Server Replied Accept Friend Request!");
                                        DefaultTableModel friendsRequestssModel = (DefaultTableModel) friendsrequest.friendRequestsTable.getModel();
                                        friendsRequestssModel.setRowCount(0);
                                        Object rowsFriendRequestsData[] = new Object[1];

                                        for (int i = 0; i < data.FriendRequestsArr.size(); i++) {
                                            rowsFriendRequestsData[0] = data.FriendRequestsArr.get(i);
                                            System.out.println(data.FriendRequestsArr.get(i));
                                            friendsRequestssModel.addRow(rowsFriendRequestsData);
                                        }
                                        break;

                                    case "ServerReplyRejectRequest":
                                        System.out.println("Server Replied Reject Friend Request!");
                                        DefaultTableModel friendsRequestssModell = (DefaultTableModel) friendsrequest.friendRequestsTable.getModel();
                                        friendsRequestssModell.setRowCount(0);
                                        Object rowsFriendsRequestsData[] = new Object[1];

                                        for (int i = 0; i < data.FriendRequestsArr.size(); i++) {
                                            rowsFriendsRequestsData[0] = data.FriendRequestsArr.get(i);
                                            //System.out.println(data.FriendsArr.get(i));
                                            friendsRequestssModell.addRow(rowsFriendsRequestsData);
                                        }
                                        break;

                                    case "ServerReplyNoThingRequest":
                                        System.out.println("Server Replied Nothing Friend Request!");
                                        break;

                                    case "ServerReplyCurrentCredit":

                                        uwish.currCreditLabl.setText(Integer.toString(data.currCredit));
                                        break;
//////////////////////////////////////RADWA///////////////////////////////////////////////////////////////////

                                    case "ListOfYourWishList":
                                        System.out.println("ana el wishlistt");
                                        String x = data.username;
                                        //System.out.println(x);
                                        //data.reWishList;
                                        //System.out.println(data.reWishList);
                                        for (UserDTO record : data.reWishList) {
                                            //System.out.println(record.PROD_NAME);
                                            //System.out.println(record.username);
                                            //System.out.println(record.Amount_CONTRIBUTION);
                                            //System.out.println(record.PROD_NAME);
                                        }

                                        int rows = data.reWishList.size();
                                        String[][] items = new String[rows][4];
                                        for (int i = 0; i < rows; i++) {
                                            items[i][0] = String.valueOf(data.reWishList.get(i).PROD_ID);
                                            items[i][1] = String.valueOf(data.reWishList.get(i).PROD_NAME);
                                            items[i][2] = String.valueOf(data.reWishList.get(i).PROD_Price);
                                            items[i][3] = String.valueOf(data.reWishList.get(i).Amount_CONTRIBUTION);

                                        }
                                        tableModel = new DefaultTableModel(items, columnNames);
                                        wishlist.wishtab.setModel(tableModel);
                                        break;

                                    case "product not in your wishlist":
                                        System.out.println("Server Replied product not in your wishlist");
                                        jop.showMessageDialog(null, "product not in your wishlist select valid id");

                                        break;

                                    case "Aready have this one":
                                        jop.showMessageDialog(null, "Aready have this one");
                                        break;

                                    case "TakeProductList":
                                        System.out.println("Server Replied ana el Prolistt");
                                        //String x = data.username;
                                        //System.out.println(x);

                                        //data.reWishList;
                                        //System.out.println(data.reProList);
                                        //System.out.println("///////////////////////////");
//                                       for (UserDTO precord : data.reProList) {
//                                            
//                                            System.out.println(precord.PROD_NAME);
//                                            System.out.println(precord.PROD_Price);
//                                            System.out.println(precord.PROD_ID);
//                                           
//                                        }
                                        int porows = data.reProList.size();
                                        String[][] proc = new String[porows][3];
                                        for (int i = 0; i < porows; i++) {
                                            // proc[i][0] = String.valueOf(data.reProList.get(i).PROD_ID);
                                            proc[i][0] = String.valueOf(data.reProList.get(i).PROD_ID);
                                            proc[i][1] = String.valueOf(data.reProList.get(i).PROD_NAME);
                                            proc[i][2] = String.valueOf(data.reProList.get(i).PROD_Price);

                                        }
                                        ptableModel = new DefaultTableModel(proc, pcolumnNames);
                                        insertitem.protab.setModel(ptableModel);

                                        break;

                                    case "product not exists":
                                        System.out.println("la yogad haza el montag");
                                        jop.showMessageDialog(null, "product not exists");

                                        break;

                                    case "ListOfMyFriendWishList":
                                        System.out.println("EL flwish hna aheh");

//                                         for (UserDTO frecord : data.reFriendWishList) {
//                                        System.out.println(frecord.PROD_NAME);
//                                        System.out.println(frecord.username);
//                                        System.out.println(frecord.Amount_CONTRIBUTION);
//                                        System.out.println(frecord.PROD_NAME);
//                                        }
//                                         System.out.println("/////////////////");
                                        int frows = data.reFriendWishList.size();
                                        String[][] fWish = new String[frows][4];
                                        for (int i = 0; i < frows; i++) {
                                            fWish[i][0] = String.valueOf(data.reFriendWishList.get(i).PROD_ID);
                                            fWish[i][1] = String.valueOf(data.reFriendWishList.get(i).PROD_NAME);
                                            fWish[i][2] = String.valueOf(data.reFriendWishList.get(i).PROD_Price);
                                            fWish[i][3] = String.valueOf(data.reFriendWishList.get(i).Amount_CONTRIBUTION);

                                        }
                                        ftableModel = new DefaultTableModel(fWish, fcolumnNames);
                                        friendwishlist.jTable1.setModel(ftableModel);

                                        break;

                                    case "out of stock":
                                        System.out.println("astna lma ngeb tany");
                                        jop.showMessageDialog(null, "out of stock");
                                        break;

                                    case "Recharge Done":
                                        jop.showMessageDialog(null, "Done");
                                        uwish.currCreditLabl.setText(Integer.toString(data.currCredit));
                                        break;

                                    case "YouHaveNoEnoughMoney":
                                        System.out.println("ash7n el awl w ab2a a3ml aly 3ayzo :)");
                                        jop.showMessageDialog(null, "You don't have enough money please consider recharging");
                                        break;

                                    case "haza kateer wallah":
                                        System.out.println("haza kateer wallah");
                                        jop.showMessageDialog(null, "You went a bit over the price! Please consider a smaller contribution");
                                        break;
                                    case "mabrook":
                                        System.out.println("mabrook");
                                        jop.showMessageDialog(null, "Congratulations! One of your wishes just became true!");
                                        break;
                                    case "you made someone happy today":
                                        System.out.println("you made someone happy today");
                                        jop.showMessageDialog(null, "You made someone happy today :)");
                                        break;
                                    case "already broken":
                                        System.out.println("already broken");
                                        jop.showMessageDialog(null, "This item is completed");
                                        break;

                                }
                            } else {
                                // System.out.println("malnash da3wa ya mohi"); //if the server reply is not for the current user
                            }

                        }

                    }
                } catch (SocketException ex) {
                    pos.close();
                    if (Thread.activeCount() > 0) {
                        Thread.currentThread().stop();
                    }
                } catch (IOException ex) {
                    //System.out.println("sayed");
//ex.printStackTrace();
                }
            }
        });
        t.start();
    }

}

class UserDTO {

    public String tag;
    public String username;
    public String password;
    public String email;
    public String credit;
    //SARA
    public ArrayList FriendsArr;
    public ArrayList AllUsersArr;
    public String RemoveFriend;
    public String AddFriend;
    public ArrayList FriendRequestsArr;
    public String FriendReqName;
    //Radwa//
    public String PROD_NAME;
    public int PROD_ID;
    public int Amount_CONTRIBUTION;
    public Vector<UserDTO> reWishList;
    public String Friendname;
    public int PROD_Price;
    public Vector<UserDTO> reProList;
    public Vector<UserDTO> reFriendWishList;
    int recharge_Amount;
    public int currCredit;
}
