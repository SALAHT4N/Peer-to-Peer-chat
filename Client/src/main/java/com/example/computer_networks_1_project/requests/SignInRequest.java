package com.example.computer_networks_1_project.requests;

public class SignInRequest extends ServerRequest{


    private SignInRequest(SignInRequestBuilder x) {
        this.port = x.port;
        this.username = x.username;
        this.password = x.password;
    }

    @Override
    public String toString(){
        return "sign-in," + super.toString();
    }

    public static class SignInRequestBuilder extends ServerRequestBuilder<SignInRequest>{

        public SignInRequestBuilder(){

        }
        @Override
        public SignInRequest build(){
            return new SignInRequest(this);
        }


    }


}
