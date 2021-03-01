package server;
import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pc
 */
public class ConnectionHandler extends Thread {

    ResultSet rs;
    PreparedStatement pst;
    Connection con;
    DataInputStream dis;
    PrintStream ps;
    PreparedStatement Old_CONTRIBUTION_Value_pst,Update_CONTRIBUTION_Value_pst,INSERT_CONTRIBUTION_Value_pst,Noti_pst;
    ResultSet Old_CONTRIBUTION_Value_rs, Update_CONTRIBUTION_Value_rs,Noti_rs,Wish_List,Pro_List;
    Socket sc;
     PreparedStatement decrease_Stock_pst;
    ResultSet decrease_Stock_pst_rs;
    PreparedStatement Update_Stock_amount;
    PreparedStatement Refresh_Pro_Data;
    ResultSet Refresh_Pro_Data_rs;
    PreparedStatement check_pro_exist_pst;
    ResultSet check_pro_exist_rs;
    PreparedStatement old_Money_pst;
    ResultSet old_Money_rs;
    PreparedStatement Updated_Money_Amount_pst;
    PreparedStatement recharge_pst;
    PreparedStatement check_wish_list_pst;
    ResultSet check_wish_list_rs;
    PreparedStatement increase_Stock_pst;
    ResultSet increase_Stock_rs;
    PreparedStatement update_increase_Stock_pst;
    ResultSet  update_increase_Stock_rs;
    public static int rowNumber;
    public static Vector<ConnectionHandler> vector = new Vector<>();
    String serverReply;
    public static int total_plus;
    public static int New_Money_Amount;
    public static int Updated_Money_Amount;

    public ConnectionHandler(Socket sc, Connection conn) {
        try {
            this.sc = sc;
            this.con = conn;
            dis = new DataInputStream(sc.getInputStream());
            ps = new PrintStream(sc.getOutputStream());
            ConnectionHandler.vector.add(this);
            start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                // receive data from client
                String str = dis.readLine();
                //   System.out.println("ana in switch: " + str);
                UserDTO objIncomingData = new Gson().fromJson(str, UserDTO.class);
                String incomingRequest = objIncomingData.tag;
                //System.out.println(objIncomingData.username);
                //System.out.println(objIncomingData.password);
                UserDTO objServerReply = new UserDTO();
                switch (incomingRequest) {
                    case "Register":
                        pst = con.prepareStatement("SELECT * FROM users WHERE user_name = ? OR EMAIL =? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        pst.setString(1, objIncomingData.username);
                        pst.setString(2, objIncomingData.email);
                        rs = pst.executeQuery();
                        rs.next();
                        rowNumber = rs.getRow();
                        if (rowNumber > 0) {            //checks whether username exists or not
                            objServerReply.tag = "UserAlreadyExists";
                            objServerReply.username = objIncomingData.username;
                            serverReply = new Gson().toJson(objServerReply);
                            sendMessageToAll(serverReply);
                        } else {
                            pst = con.prepareStatement("INSERT INTO users VALUES(?,?,?,?)");
                            pst.setString(1, objIncomingData.username);
                            pst.setString(2, objIncomingData.password);
                            pst.setString(3, objIncomingData.email);
                            pst.setString(4, objIncomingData.credit);
                            pst.executeUpdate();
                            con.commit();
                        }
                        //System.out.println(data.password);
                        //System.out.println(data.username);
                        break;
                    case "Login":
                        pst = con.prepareStatement("SELECT * FROM USERS WHERE USER_NAME = ? AND PASSWORD = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        pst.setString(1, objIncomingData.username);
                        pst.setString(2, objIncomingData.password);
                        rs = pst.executeQuery();
                        rs.next();
                        rowNumber = rs.getRow();
                        if (rowNumber == 0) {           //checks whether a username and password exist or not
                            objServerReply.tag = "UserDoesntExist";
                            objServerReply.username = objIncomingData.username;
                            serverReply = new Gson().toJson(objServerReply);
                            sendMessageToAll(serverReply);
                        } else {
                            objServerReply.tag = "AccessGranted";
                            objServerReply.username = objIncomingData.username;
                            serverReply = new Gson().toJson(objServerReply);
                            sendMessageToAll(serverReply);
                        }
                        break;

                    case "ViewUsers":
                        System.out.println("ana server el view all users");
                        pst = con.prepareStatement("Select USER_NAME FROM USERS WHERE USER_NAME <> ? AND USER_NAME not in (select USER_NAME2  from USERS_FRIEND_LIST WHERE USER_NAME1 = ?) AND\n" +
                                                    "USER_NAME not in (select USER2  from FRIEND_REQUESTS WHERE USER1 = ?)  AND \n" +
                                                    "USER_NAME not in (select USER1  from FRIEND_REQUESTS WHERE USER2 = ?) ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        pst.setString(1, objIncomingData.username);
                        pst.setString(2, objIncomingData.username);
                        pst.setString(3, objIncomingData.username);
                        pst.setString(4, objIncomingData.username);
                        rs = pst.executeQuery();

                        ArrayList rowsValues = new ArrayList();
                        while (rs.next()) {
                            rowsValues.add(rs.getString(1));
                        }

//                        for(int i = 0; i < rowsValues.size(); i++)
//                        {
//                            System.out.println(rowsValues.get(i));
//                        }
                        //String[] contactsListNames = (String[]) rowsValues.toArray(new String[rowsValues.size()]);
                        objServerReply.username = objIncomingData.username;
                        objServerReply.tag = "ServerReplyViewAllUsers";

                        //objServerReply.resUsersList = rs;
                        objServerReply.AllUsersArr = rowsValues;
                        serverReply = new Gson().toJson(objServerReply);
                        sendMessageToAll(serverReply);
                        break;

                    case "ViewMyFriendList":
                        System.out.println("ana server el view all friends");
                        pst = con.prepareStatement("select USER_NAME2 from USERS_FRIEND_LIST WHERE USER_NAME1 = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        pst.setString(1, objIncomingData.username);
                        rs = pst.executeQuery();

                        ArrayList rowValues = new ArrayList();
                        while (rs.next()) {
                            rowValues.add(rs.getString(1));
                        }
//                        for(int i = 0; i < rowValues.size(); i++)
//                        {
//                            System.out.println(rowValues.get(i));
//                        }
                        //String[] contactListNames = (String[]) rowValues.toArray(new String[rowValues.size()]);
//                        for(int i=0 ; i<contactListNames.length ; i++ )
//                        {
//                            System.out.println(contactListNames[i]);
//
//                        }
                        objServerReply.tag = "ServerReplyViewMyFriendList";
                        objServerReply.username = objIncomingData.username;
                        objServerReply.FriendsArr = rowValues;
                        serverReply = new Gson().toJson(objServerReply);
                        sendMessageToAll(serverReply);
                        break;

                    case "RemoveFriend":
                        System.out.println("ana server el remove friend");

                        pst = con.prepareStatement("DELETE  FROM USERS_FRIEND_LIST WHERE USER_NAME1 = ? AND USER_NAME2 = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        pst.setString(1, objIncomingData.username);
                        pst.setString(2, objIncomingData.RemoveFriend);
                        pst.executeUpdate();
                        
                        pst = con.prepareStatement("DELETE  FROM USERS_FRIEND_LIST WHERE USER_NAME1 = ? AND USER_NAME2 = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        pst.setString(2, objIncomingData.username);
                        pst.setString(1, objIncomingData.RemoveFriend);
                        pst.executeUpdate();
                        
                        pst = con.prepareStatement("select USER_NAME2 from USERS_FRIEND_LIST WHERE USER_NAME1 = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        pst.setString(1, objIncomingData.username);
                        rs = pst.executeQuery();


                        ArrayList removerowValues = new ArrayList();
                        while (rs.next()){
                            removerowValues.add(rs.getString(1));
                        }
                        
                        objServerReply.tag = "ServerReplyRemoveFriend";
                        objServerReply.username = objIncomingData.username;
                        objServerReply.FriendsArr = removerowValues;
                        serverReply = new Gson().toJson(objServerReply);
                        sendMessageToAll(serverReply);
                        break;
                        
                    case "ViewFriendRequests":
                        System.out.println("ana server el view friend requests");
                        pst = con.prepareStatement("SELECT USER1 FROM FRIEND_REQUESTS WHERE USER2= ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        pst.setString(1, objIncomingData.username);
                        rs = pst.executeQuery();

                        ArrayList rowsRequestsValues = new ArrayList();
                        while (rs.next()) {
                            rowsRequestsValues.add(rs.getString(1));
                        }
                        
                        objServerReply.username = objIncomingData.username;
                        objServerReply.tag = "ServerReplyViewFriendRequests";

                        objServerReply.FriendRequestsArr = rowsRequestsValues;
                        serverReply = new Gson().toJson(objServerReply);
                        sendMessageToAll(serverReply);
                        break;
                        
                    case "AddFriend":
                        System.out.println("ana server el Add friend");
                        //Hna el mfrood y3ml check If the users entered correct values or not 
                        
                        pst = con.prepareStatement("INSERT INTO FRIEND_REQUESTS (USER1,USER2) VALUES(?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        pst.setString(1, objIncomingData.username);
                        pst.setString(2, objIncomingData.AddFriend);
                        pst.executeQuery();
                        con.commit();
                        
                        pst = con.prepareStatement("Select USER_NAME FROM USERS WHERE USER_NAME <> ? AND USER_NAME not in (select USER_NAME2  from USERS_FRIEND_LIST WHERE USER_NAME1 = ?) AND\n" +
                                                    "USER_NAME not in (select USER2  from FRIEND_REQUESTS WHERE USER1 = ?)  AND \n" +
                                                    "USER_NAME not in (select USER1  from FRIEND_REQUESTS WHERE USER2 = ?) ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        pst.setString(1, objIncomingData.username);
                        pst.setString(2, objIncomingData.username);
                        pst.setString(3, objIncomingData.username);
                        pst.setString(4, objIncomingData.username);
                        rs = pst.executeQuery();

                        ArrayList updatedRowsValues = new ArrayList();
                        while (rs.next()) {
                            updatedRowsValues.add(rs.getString(1));
                        }
                        
                        objServerReply.tag = "ServerReplyAddFriend";
                        objServerReply.username = objIncomingData.username;
                        objServerReply.AllUsersArr = updatedRowsValues;
                        serverReply = new Gson().toJson(objServerReply);
                        sendMessageToAll(serverReply);
                        
                        break;
                        
                    case "AcceptFRequest":
                        System.out.println("ana server el Accept friend request");
                        
                        pst = con.prepareStatement("INSERT INTO USERS_FRIEND_LIST(USER_NAME1,USER_NAME2) VALUES (?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        pst.setString(1, objIncomingData.username);
                        pst.setString(2, objIncomingData.FriendReqName);
                        pst.executeQuery();
                        
                        pst = con.prepareStatement("INSERT INTO USERS_FRIEND_LIST(USER_NAME1,USER_NAME2) VALUES (?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        pst.setString(2, objIncomingData.username);
                        pst.setString(1, objIncomingData.FriendReqName);
                        pst.executeQuery();
                        
                        pst = con.prepareStatement("DELETE FROM FRIEND_REQUESTS WHERE USER1= ? AND USER2 = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        pst.setString(1, objIncomingData.FriendReqName);
                        pst.setString(2, objIncomingData.username);
                        pst.executeUpdate();
                        
                        con.commit();
                        
                        pst = con.prepareStatement("SELECT USER1 FROM FRIEND_REQUESTS WHERE USER2= ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        pst.setString(1, objIncomingData.username);
                        rs = pst.executeQuery();

                        ArrayList rowssRequestsValues = new ArrayList();
                        while (rs.next()) {
                            rowssRequestsValues.add(rs.getString(1));
                            //System.out.println(data.FriendsArr.get(i));
                        }
                        
                        objServerReply.FriendRequestsArr = rowssRequestsValues;
                        objServerReply.tag = "ServerReplyAcceptRequest";
                        objServerReply.username = objIncomingData.username;
                        
                        serverReply = new Gson().toJson(objServerReply);
                        sendMessageToAll(serverReply);
                        
                        break;
                        
                    case "RejectFRequest":
                        System.out.println("ana server el Reject friend request");
                        pst = con.prepareStatement("DELETE FROM FRIEND_REQUESTS WHERE USER1= ? and USER2= ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        pst.setString(1, objIncomingData.FriendReqName);
                        pst.setString(2, objIncomingData.username);
                        pst.executeUpdate();
                        
                        con.commit();
                        
                        pst = con.prepareStatement("SELECT USER1 FROM FRIEND_REQUESTS WHERE USER2= ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        pst.setString(1, objIncomingData.username);
                        rs = pst.executeQuery();

                        ArrayList rowssRequestsValuess = new ArrayList();
                        while (rs.next()) {
                            rowssRequestsValuess.add(rs.getString(1));
                        }
                        
                        objServerReply.FriendRequestsArr = rowssRequestsValuess;

                        objServerReply.tag = "ServerReplyRejectRequest";
                        objServerReply.username = objIncomingData.username;
                        
                        serverReply = new Gson().toJson(objServerReply);
                        sendMessageToAll(serverReply);
                        break;
                        
                    case"NoThingFRequest":
                        System.out.println("ana server el Nothing friend request");
                        
                        objServerReply.tag = "ServerReplyNoThingRequest";
                        objServerReply.username = objIncomingData.username;
                        serverReply = new Gson().toJson(objServerReply);
                        sendMessageToAll(serverReply);
                        break;
                    
                    case "CurrentCredit":
                        pst = con.prepareStatement("SELECT AMOUNT_OF_MONEY FROM USERS WHERE USER_NAME = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        pst.setString(1, objIncomingData.username);
                        rs = pst.executeQuery();
                        rs.next();
                        
                        objServerReply.currCredit = rs.getInt(1);
                        objServerReply.tag = "ServerReplyCurrentCredit";
                        objServerReply.username = objIncomingData.username;
                        
                        serverReply = new Gson().toJson(objServerReply);
                        sendMessageToAll(serverReply);
                        break;
                    ////////////////////////Radwa//////////////////////////////////

                    case "ShowYourWishList":
                        UserDTO wList = new UserDTO();
                        System.out.println("haat elwish lsit data");
                        wList.reWishList = getWishListByUserName(objIncomingData.username);
                        //System.out.print(reWishList.get(0) + " "); 
                        wList.tag = "ListOfYourWishList";
                        wList.username = objIncomingData.username;
                        serverReply = new Gson().toJson(wList);
                        sendMessageToAll(serverReply);
                        break;

                    case "InsertInMyWishList":
                        check_pro_exist_pst=con.prepareStatement("SELECT PROD_PRICE from PRODUCTS where PROD_ID =?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        check_pro_exist_pst.setInt(1, objIncomingData.PROD_ID);
                        check_pro_exist_rs=check_pro_exist_pst.executeQuery();
                        check_pro_exist_rs.next();
                        int rNum = check_pro_exist_rs.getRow();
                        if (rNum == 0) {
                            objServerReply.tag = "product not exists";
                            objServerReply.username = objIncomingData.username;
                            serverReply = new Gson().toJson(objServerReply);
                            sendMessageToAll(serverReply);}
                        else{
                        //////////////////////////////////////////////
                        check_wish_list_pst = con.prepareStatement("Select  TOTAL_CONTRIBUTION from USERS_PRODUCTS where USER_NAME = ? and PROD_ID =? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        check_wish_list_pst.setString(1, objIncomingData.username);
                        check_wish_list_pst.setInt(2, objIncomingData.PROD_ID);
                        check_wish_list_rs = check_wish_list_pst.executeQuery();
                        check_wish_list_rs.next();
                        int rowNum = check_wish_list_rs.getRow();
                        if (rowNum > 0) {
                            objServerReply.tag = "Aready have this one";
                            objServerReply.username = objIncomingData.username;
                            serverReply = new Gson().toJson(objServerReply);
                            sendMessageToAll(serverReply);

                        } else {
                            System.out.println("hcheak 3ala el stock alawal");
                            decrease_Stock_pst = con.prepareStatement("SELECT  Stock from PRODUCTS where PROD_ID =?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                            decrease_Stock_pst.setInt(1, objIncomingData.PROD_ID);
                            decrease_Stock_pst_rs = decrease_Stock_pst.executeQuery();
                            decrease_Stock_pst_rs.next();
                            int stock_Amount = decrease_Stock_pst_rs.getInt(1);
                            if (stock_Amount == 0) {
                                System.out.println("5ls wllahy ya 3sl ");
                                objServerReply.tag = "out of stock";
                                objServerReply.username = objIncomingData.username;
                                serverReply = new Gson().toJson(objServerReply);
                                sendMessageToAll(serverReply);

                            } else {
                                System.out.println("l2et x el stock whainsert");
                                pst = con.prepareStatement("INSERT INTO USERS_PRODUCTS VALUES(?,?,0)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);  //showWishList=con.prepareStatement("SELECT * FROM DUMMYUSERS WHERE username = ? AND pass = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                                pst.setString(1, objIncomingData.username);
                                pst.setInt(2, objIncomingData.PROD_ID);
                                pst.executeUpdate();
                                con.commit();
                                System.out.println("3malt insert 5las ");
                                System.out.println("////////////////// ");
                                System.out.println("hshof 3ndy kam f el stock");

                                int new_stock_Amount = stock_Amount - 1;
                                System.out.println("stock Amount " + new_stock_Amount);
                                System.out.println("hbd2 ashel mn el stock");
                                Update_Stock_amount = con.prepareStatement("UPDATE PRODUCTS SET Stock = ? where PROD_ID = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                                Update_Stock_amount.setInt(1, new_stock_Amount);
                                Update_Stock_amount.setInt(2, objIncomingData.PROD_ID);
                                Update_Stock_amount.executeUpdate();
                                con.commit();
                                System.out.println("shelto mn el stock 5las");
                                System.out.println("/////////");
                                System.out.println("Yala n3ml refresh l data");
                                UserDTO obj_Refresh_Data_After_Insert = new UserDTO();
                            obj_Refresh_Data_After_Insert.reWishList = getWishListByUserName(objIncomingData.username);
                            //System.out.print(reWishList.get(0) + " "); 
                            obj_Refresh_Data_After_Insert.tag = "ListOfYourWishList";
                            obj_Refresh_Data_After_Insert.username = objIncomingData.username;
                            serverReply = new Gson().toJson(obj_Refresh_Data_After_Insert);
                            sendMessageToAll(serverReply);
                            con.commit();
                            System.out.println("3mlna refresh ");
                            }
                        }
                        }
                        break;

                    case "DeleteFromMyWishList":
                        System.out.println("ana server el deleteee ");

                        pst = con.prepareStatement("SELECT TOTAL_CONTRIBUTION From USERS_PRODUCTS where USER_NAME =? and PROD_ID =? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        pst.setString(1, objIncomingData.username);
                        pst.setInt(2, objIncomingData.PROD_ID);
                        rs = pst.executeQuery();
                        rs.next();
                        rowNumber = rs.getRow();
                        if (rowNumber == 0) {
                            objServerReply.tag = "product not in your wishlist";
                            objServerReply.username = objIncomingData.username;
                            serverReply = new Gson().toJson(objServerReply);
                            sendMessageToAll(serverReply);

                        } else {
                               System.out.println("l2et el product w ray7 ams7");
                            pst = con.prepareStatement("DELETE FROM  USERS_PRODUCTS WHERE PROD_ID= ?  AND  USER_NAME = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);  //showWishList=con.prepareStatement("SELECT * FROM DUMMYUSERS WHERE username = ? AND pass = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                            pst.setInt(1, objIncomingData.PROD_ID);
                            pst.setString(2, objIncomingData.username);
                            rs = pst.executeQuery();
                            con.commit();
                            System.out.println("ms7t 5las ");
                            System.out.println("//////////////////");
                            
                            System.out.println("Ray7 azodha x el stock ");
                            increase_Stock_pst = con.prepareStatement("SELECT  Stock from PRODUCTS where PROD_ID =?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                            increase_Stock_pst.setInt(1, objIncomingData.PROD_ID);
                            increase_Stock_rs =  increase_Stock_pst.executeQuery();
                            increase_Stock_rs.next();
                            int stock_Amount = increase_Stock_rs.getInt(1);
                            int increase_Stock = stock_Amount + 1;
                            update_increase_Stock_pst = con.prepareStatement("UPDATE PRODUCTS SET Stock = ? where PROD_ID = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                            update_increase_Stock_pst.setInt(1, increase_Stock);
                            update_increase_Stock_pst.setInt(2, objIncomingData.PROD_ID);
                            update_increase_Stock_pst.executeUpdate();
                            con.commit();
                            System.out.println("zodt el stock 5laas");
                            UserDTO obj_Refresh_Data = new UserDTO();
                            obj_Refresh_Data.reWishList = getWishListByUserName(objIncomingData.username);
                            //System.out.print(reWishList.get(0) + " "); 
                            obj_Refresh_Data.tag = "ListOfYourWishList";
                            obj_Refresh_Data.username = objIncomingData.username;
                            serverReply = new Gson().toJson(obj_Refresh_Data);
                            sendMessageToAll(serverReply);
                            con.commit();
                        }
                        break;

                    case "BringProductData":
                        UserDTO pList = new UserDTO();
                        System.out.println("haat elproo data");
                        pList.reProList = getProducts();
                        pList.tag = "TakeProductList";
                        pList.username = objIncomingData.username;
                        serverReply = new Gson().toJson(pList);
                        sendMessageToAll(serverReply);
                        break;

                    case "BringFriendWishList":
                        UserDTO fwList = new UserDTO();
                        System.out.println("haat elwish lsit data");
                        fwList.reFriendWishList = getWishListByUserName(objIncomingData.Friendname);
                        //System.out.print(reWishList.get(0) + " "); 
                        fwList.tag = "ListOfMyFriendWishList";
                        fwList.username = objIncomingData.username;
                        serverReply = new Gson().toJson(fwList);
                        sendMessageToAll(serverReply);

                        break;

                    case "Contribute":
                        System.out.println("Amout_CON");
                        int contribution = objIncomingData.Amount_CONTRIBUTION;
                        System.out.println("contribution is " + contribution);//we can use Integer.valueOF() if the returned values is of type String
                        //getting product price from database
                        pst = con.prepareStatement("SELECT PROD_PRICE FROM PRODUCTS WHERE PROD_ID = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        pst.setInt(1, objIncomingData.PROD_ID);
                        rs = pst.executeQuery();
                        rs.next();
                        int prod_price = rs.getInt(1);
                        System.out.println("price is " + prod_price);
                        //geting total contributions from database
                        Old_CONTRIBUTION_Value_pst = con.prepareStatement("SELECT TOTAL_CONTRIBUTION FROM USERS_PRODUCTS WHERE USER_NAME = ? AND PROD_ID = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        Old_CONTRIBUTION_Value_pst.setString(1, objIncomingData.Friendname);
                        Old_CONTRIBUTION_Value_pst.setInt(2, objIncomingData.PROD_ID);
                        Old_CONTRIBUTION_Value_rs = Old_CONTRIBUTION_Value_pst.executeQuery();
                        Old_CONTRIBUTION_Value_rs.next();
                        int total_contribution = Old_CONTRIBUTION_Value_rs.getInt(1);
                        old_Money_pst = con.prepareStatement("Select AMOUNT_OF_MONEY from Users where USER_NAME = ? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        old_Money_pst.setString(1, objIncomingData.username);
                        old_Money_rs = old_Money_pst.executeQuery();
                        old_Money_rs.next();
                        int old_Money_Amount = old_Money_rs.getInt(1);
                        System.out.println("old_Money " + old_Money_Amount);

                        if (old_Money_Amount < contribution) {
                            objServerReply.tag = "YouHaveNoEnoughMoney";
                            objServerReply.username = objIncomingData.username;
                            serverReply = new Gson().toJson(objServerReply);
                            sendMessageToAll(serverReply);
                            System.out.println("laaa tamlk maal ");
                        }// shofyyy el kooos daa hero7 feeen
                        else {
                            System.out.println("total is " + total_contribution);
                            //summation of total contribution and amount of contributio that the user has just entered
                            total_plus = total_contribution + contribution;
                            New_Money_Amount = old_Money_Amount - contribution;
                            System.out.println("plus is " + total_plus);
                            //checking contribution, total contribution+contribution = total plus
                            if (total_contribution == prod_price) {
                                objServerReply.tag = "already broken";
                                objServerReply.username = objIncomingData.username;
                                serverReply = new Gson().toJson(objServerReply);
                                sendMessageToAll(serverReply);
                                System.out.println("plus equals price ");
                            } else {
                                //checking contribution, total contribution+contribution = total plus
                                if ((contribution > prod_price) || (total_plus > prod_price)) {
                                    objServerReply.tag = "haza kateer wallah";
                                    objServerReply.username = objIncomingData.username;
                                    serverReply = new Gson().toJson(objServerReply);
                                    sendMessageToAll(serverReply);
                                    System.out.println("contribute is gt price ");
                                } else {
                                    //inserting the new valid contribution into the users_contributions table
                                    INSERT_CONTRIBUTION_Value_pst = con.prepareStatement("INSERT INTO USERS_CONTRIBUTIONS VALUES(?,?,?,?)");
                                    INSERT_CONTRIBUTION_Value_pst.setString(1, objIncomingData.Friendname);
                                    INSERT_CONTRIBUTION_Value_pst.setInt(2, objIncomingData.PROD_ID);
                                    INSERT_CONTRIBUTION_Value_pst.setString(3, objIncomingData.username); //username = contributer name
                                    INSERT_CONTRIBUTION_Value_pst.setInt(4, objIncomingData.Amount_CONTRIBUTION);
                                    INSERT_CONTRIBUTION_Value_pst.executeUpdate();
                                    con.commit();

                                    //updating the value of total_contributions in users_products table
                                    System.out.println("abl update el USERS_PRODUCTS ");
                                    Update_CONTRIBUTION_Value_pst = con.prepareStatement("UPDATE USERS_PRODUCTS SET TOTAL_CONTRIBUTION = ? WHERE USER_NAME = ? AND PROD_ID = ?");
                                    Update_CONTRIBUTION_Value_pst.setInt(1, total_plus);
                                    Update_CONTRIBUTION_Value_pst.setString(2, objIncomingData.Friendname);
                                    Update_CONTRIBUTION_Value_pst.setInt(3, objIncomingData.PROD_ID);
                                    Update_CONTRIBUTION_Value_pst.executeUpdate();
                                    con.commit();
                                    System.out.println(" ba3d update el USERS_PRODUCTS ");
                                    System.out.println(" ////////////////////////");
                                    System.out.println(" ray7 a3ml update");
                       UserDTO fwListAfterUpdate = new UserDTO();
                        System.out.println("haat elwish lsit data");
                        fwListAfterUpdate.reFriendWishList = getWishListByUserName(objIncomingData.Friendname);
                        //System.out.print(reWishList.get(0) + " "); 
                        fwListAfterUpdate.tag = "ListOfMyFriendWishList";
                        fwListAfterUpdate.username = objIncomingData.username;
                        serverReply = new Gson().toJson(fwListAfterUpdate);
                        sendMessageToAll(serverReply);
                        
                         System.out.println("3mlt update");
                                    //Update the money amount
                                    System.out.println("abl update el USERS ");
                                    Updated_Money_Amount_pst = con.prepareStatement("UPDATE USERS  SET AMOUNT_OF_MONEY = ? WHERE USER_NAME = ?");
                                    Updated_Money_Amount_pst.setInt(1, New_Money_Amount);
                                    Updated_Money_Amount_pst.setString(2, objIncomingData.username);
                                    Updated_Money_Amount_pst.executeUpdate();
                                    con.commit();
                                    System.out.println("ba3d update el USERS ");

                                }
                                //checking if total_plus = product price to send a notification to the person with the wish then rbna ykrm llba2y rly 3mlo contribute
                                if (total_plus == prod_price) {
                                    objServerReply.tag = "mabrook";
                                    objServerReply.username = objIncomingData.Friendname;
                                    serverReply = new Gson().toJson(objServerReply);
                                    sendMessageToAll(serverReply);

                                    Noti_pst = con.prepareStatement("SELECT CONTRIBUTOR_NAME FROM USERS_CONTRIBUTIONS WHERE USER_NAME = ? AND ITEM = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                                    Noti_pst.setString(1, objIncomingData.Friendname);
                                    Noti_pst.setInt(2, objIncomingData.PROD_ID);
                                    Noti_rs = Noti_pst.executeQuery();
                                    System.out.println("before while ");
                                    while (Noti_rs.next()) {
                                        System.out.println("after while ");
                                        Noti_rs.next();
                                        objServerReply.tag = "you made someone happy today";
                                        objServerReply.username = Noti_rs.getString(1); //contributer name
                                        serverReply = new Gson().toJson(objServerReply);
                                        sendMessageToAll(serverReply);
                                        //Noti_rs.next();
                                    }
                                }
                            }
                        }
                        break;
                        
                        
                        case "Recharge My Credit":
                        old_Money_pst = con.prepareStatement("Select AMOUNT_OF_MONEY from Users where USER_NAME = ? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        old_Money_pst.setString(1, objIncomingData.username);
                        old_Money_rs = old_Money_pst.executeQuery();
                        old_Money_rs.next();
                        int old_Money_Amt = old_Money_rs.getInt(1);
                        System.out.println("old_Money " + old_Money_Amt);
                        Updated_Money_Amount = old_Money_Amt + objIncomingData.recharge_Amount;

                        recharge_pst = con.prepareStatement("Update USERS set AMOUNT_OF_MONEY = ? WHERE USER_NAME =?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        recharge_pst.setInt(1, Updated_Money_Amount);
                        recharge_pst.setString(2, objIncomingData.username);
                        recharge_pst.executeUpdate();
                        con.commit();

                        old_Money_pst = con.prepareStatement("Select AMOUNT_OF_MONEY from Users where USER_NAME = ? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        old_Money_pst.setString(1, objIncomingData.username);
                        old_Money_rs = old_Money_pst.executeQuery();
                        old_Money_rs.next();
                        int currAmt = old_Money_rs.getInt(1);
                        
                        objServerReply.currCredit =currAmt;
                        objServerReply.tag = "Recharge Done";
                        objServerReply.username = objIncomingData.username;
                        serverReply = new Gson().toJson(objServerReply);
                        sendMessageToAll(serverReply);
                        break;


                }

            }
        } catch (SocketException ex) {
            ps.close();
            Thread.currentThread().interrupt();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Vector<UserDTO> getWishListByUserName(String userName) throws SQLException {

        Vector<UserDTO> wishList = new Vector<>();
        pst = con.prepareStatement("SELECT us.PROD_ID,p.PROD_NAME,p.PROD_PRICE,us.TOTAL_CONTRIBUTION FROM PRODUCTS  p ,USERS_PRODUCTS  us \n"
                + "   where p.PROD_ID = US.PROD_ID  AND us.USER_NAME = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        pst.setString(1, userName);
        Wish_List = pst.executeQuery();
        while (Wish_List.next()) {
            UserDTO wishListData = new UserDTO();
             wishListData.PROD_ID = Wish_List.getInt("PROD_ID");
            wishListData.PROD_NAME = Wish_List.getString("PROD_NAME");
            wishListData.PROD_Price = Wish_List.getInt("PROD_PRICE");
            wishListData.Amount_CONTRIBUTION = Wish_List.getInt("TOTAL_CONTRIBUTION");
            wishList.add(wishListData);
        }
        pst.close();
        return wishList;
    }

    public Vector<UserDTO> getFriendWishListByHisName(String FuserName) throws SQLException {

        Vector<UserDTO> fwishList = new Vector<>();
        pst = con.prepareStatement("SELECT p.PROD_NAME,p.PROD_PRICE,us.TOTAL_CONTRIBUTION,us.PROD_ID FROM PRODUCTS  p ,USERS_PRODUCTS  us \n"
                + "   where p.PROD_ID = US.PROD_ID  AND us.USER_NAME = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        pst.setString(1, FuserName);
        Wish_List = pst.executeQuery();
        while (Wish_List.next()) {
            UserDTO fwishListData = new UserDTO();
            fwishListData.PROD_NAME = Wish_List.getString("PROD_NAME");
            fwishListData.PROD_Price = Wish_List.getInt("PROD_PRICE");
            fwishListData.Amount_CONTRIBUTION = Wish_List.getInt("TOTAL_CONTRIBUTION");
            fwishListData.PROD_ID = Wish_List.getInt("PROD_ID");
            fwishList.add(fwishListData);
        }
        pst.close();
        return fwishList;
    }

    public Vector<UserDTO> getProducts() throws SQLException {
        Vector<UserDTO> proList = new Vector<>();
        pst = con.prepareStatement("SELECT PROD_NAME,PROD_PRICE,PROD_ID  from PRODUCTS", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        Pro_List = pst.executeQuery();
        while (Pro_List.next()) {
            UserDTO proListData = new UserDTO();

            proListData.PROD_NAME = Pro_List.getString("PROD_NAME");
            proListData.PROD_Price = Pro_List.getInt("PROD_PRICE");
            proListData.PROD_ID = Pro_List.getInt("PROD_ID");
            proList.add(proListData);
        }
        pst.close();
        return proList;
    }

    void sendMessageToAll(String msg) {
        for (ConnectionHandler ch : vector) {
            ch.ps.println(msg);

        }
    }

    public static void disconnectServer() {
        for (ConnectionHandler ch : vector) {
            try {
                ch.stop();
                ch.sc.close();
                ch.ps.close();
                ch.dis.close();
            } catch (IOException ex) {
                Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
