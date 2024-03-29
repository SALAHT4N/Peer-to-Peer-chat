package com.example.computer_networks_1_project.requests;

import java.util.stream.IntStream;

public abstract class ServerRequest implements Request {
    protected String username;
    protected String password;
    protected int port;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString(){
        return username + "," + password + "," + port + "\n";
    }

    public static abstract class ServerRequestBuilder<N> implements Builder<N>{
        protected String username;
        protected String password;
        protected int port;

        public ServerRequestBuilder< N> setPort(int port){
            this.port = port;
            return this;
        }

        public ServerRequestBuilder<N> setUsername(String username){
            this.username = username;
            return this;
        }

        public ServerRequestBuilder<N> setPassword(String password){
            this.password = password;
            return this;
        }

    }

}
