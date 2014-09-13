/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatClient;

import java.util.ArrayList;

/**
 *
 * @author Gruppe 4, Andreas, Michael og Sebastian
 */
public interface ClientListener {
    public void messageArrived(String data);
    public void messageArrived(ArrayList<String> onlineUserList);
}
