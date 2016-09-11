var wsUri = "ws://localhost:8080/reactiveEE/hellowebsocket";
var webSocket;

console.log("calling opensocket");
openSocket();

function openSocket(){
    // singleton
    if(webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED){
        console.log("socket already active");
        return;
    }

    webSocket = new WebSocket(wsUri);
    /**
     * Map socket-functions to our socket
     */
    webSocket.onopen = function(event){

        if(event.data === undefined)
            return;

        webSocket.send("open connection")
    };

    webSocket.onmessage = function(event){
        console.log(event.data);
    };

    webSocket.onclose = function(event){
        console.log("Connection closed");
    };
}

function send(){
    webSocket.send("send function websocket js");
}

function closeSocket(){
    webSocket.close();
}


