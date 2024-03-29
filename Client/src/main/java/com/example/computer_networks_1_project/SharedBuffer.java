package com.example.computer_networks_1_project;

import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


// we can also set the methods as synchronized instead of a monitor object.
// or we can use Collections.SynchronizedList(List<> list); this is a wrapper to a List similar to this "Shared Buffer" class.
public class SharedBuffer {
    private List<Peer> activePeers = new ArrayList<>();

    final private Object monitor = new Object();

    public void addPeers(List<Peer> users) {
        boolean flag = true;
        synchronized (monitor) {
            List<Peer> activePeersToRemove = new ArrayList<>();
            List<Peer> usersToRemove = new ArrayList<>();

            for(Peer i: activePeers) {
                for(Peer j: users) {
                    if (i.equals(j)) {
                        i.update(j.getName(), j.getIP(), j.getPort());
                        flag = false;

                        usersToRemove.add(j);
                        break;
                    }
                }
                if (flag) {
//                    activePeers.remove(i);
                    activePeersToRemove.add(i);
                }
            }
            activePeers.removeAll(activePeersToRemove);
            users.removeAll(usersToRemove);
            activePeers.addAll(users);
        }
    }

    public Peer getPeer(String name){
        synchronized (monitor) {
            return activePeers.stream()
                    .filter(peer -> Objects.equals(name, peer.getName()))
                    .findAny()
                    .orElse(null);
        }
    }
    public void printActivePeers(){
        synchronized (monitor){
            int i = 1;
            activePeers.forEach(peer -> System.out.println(i + ":" + peer.getName() + " " + + peer.getPort() + " " + peer.getIP()));
        }
    }

    public List<String> getListOfActivePeers(){
        synchronized (monitor) {
            return Arrays.asList(activePeers.stream()
                    .map(peer -> peer.getIP() + ":" + peer.getPort())
                    .toArray(String[]::new)); // typecast object[] to string[]
        } // or return an unmodifiable list of activePeers to ensure thread safety
    }

    public List<String> getNamesOfActivePeers(){
        synchronized (monitor){
            return Arrays.asList(
                    activePeers.stream()
                    .map(peer -> peer.getName())
                    .toArray(String[]::new)
            );
        }
    }

    public List<Peer> getActivePeers(){
        synchronized (monitor){
            return new ArrayList<>(activePeers);
        }
    }
    /*
    * @param direction if false, the message is stored in the send buffer, the message is sent to that peer
    *                  otherwise, the message is stored in the received buffer.
    * */
    public void setPeerMessage(String ip, int port, String message, boolean direction, LocalDateTime date) {
        synchronized (monitor) {
            // TODO remove unwanted code
            Peer requestedPeer = activePeers.stream()
                    .filter(peer -> (Objects.equals(peer.getPort(), port) && Objects.equals(peer.getIP(),ip)))
                    .findAny()
                    .orElse(null);
            Peer r = getPeer(new InetSocketAddress(ip, port));
            setPeerMessage(r, message, direction, date);
        }
    }

    public void deletePeerMessage(String ip, int port, int messageIndex) {
        synchronized (monitor){
//            InetSocketAddress a = new InetSocketAddress(ip, port);
            // TODO remove unwanted code

            Peer requestedPeer = activePeers.stream()
                    .filter(peer -> (Objects.equals(peer.getPort(), port) && Objects.equals(peer.getIP(),ip)))
                    .findAny()
                    .orElse(null);

            if(requestedPeer != null) requestedPeer.deleteMessage(messageIndex);
        }
    }
    public Peer findPeer(Peer peer) {
        synchronized (monitor){
            Peer result = activePeers.stream()
                    .filter(p -> p.equals(peer))
                    .findAny()
                    .orElse(null);

            return result;
        }
    }

    public void setPeerMessage(Peer peer, String message, boolean direction, LocalDateTime date) {
        synchronized (monitor){
            peer.getMessagesOrdered().add(new Message(message, direction, date));
            // TODO remove unwanted code

            if(!direction) {
                peer.getMessagesSent().add(message);
            } else {
                peer.getMessagesReceived().add(message);
            }
        }
    }

    public Peer getPeer(int index) {
        synchronized (monitor) {
            try {
                return activePeers.get(index);
            } catch (ArrayIndexOutOfBoundsException e) {
                return null;
            }
        }
    }

    public Peer getPeer(InetSocketAddress address) {
        synchronized(monitor){
            return activePeers.stream()
                    .filter(peer -> (Objects.equals(address, peer.getAddress())))
                    .findAny()
                    .orElse(null);
        }
    }
}
