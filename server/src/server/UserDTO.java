/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author pc
 */
public class UserDTO {
  

    public String tag;
    //public int id;
    public String username;
    public String password;
    public String email;
    public String credit;
    //SARA
    public ArrayList FriendsArr;  
    public String RemoveFriend;
    public String AddFriend;
    public ArrayList AllUsersArr;
    public ArrayList FriendRequestsArr;
    public String FriendReqName;
    //Radwa
     public String PROD_NAME;
     public int PROD_ID;
     public int PROD_Price;
     public int Amount_CONTRIBUTION;
     public Vector<UserDTO> reWishList;
     public Vector<UserDTO> reProList;
     public Vector<UserDTO> reFriendWishList;
     public String Friendname;
     int recharge_Amount;
public int currCredit;
     

}
    

