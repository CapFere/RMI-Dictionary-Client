/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import models.Dictionary;

/**
 *
 * @author Captain
 */
public interface DictionaryServerInterface extends Remote{
    public Dictionary findWord(Dictionary dictionary) throws RemoteException;
    public Dictionary addWord(Dictionary dictionary) throws RemoteException;
    public Dictionary deleteWord(Dictionary dictionary) throws RemoteException;
}
