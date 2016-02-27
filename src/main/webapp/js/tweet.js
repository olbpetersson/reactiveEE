var Row = React.createClass({

    getInitialState: function(){
        return {data: []};
    },

    componentDidMount: function(){
            //this.initREST();
            this.initWS();
    },


    render: function(){
        var tweets = [];
        var tweetData = this.state.data;
        for(var i =0; i < tweetData.length; i++){
            var title = tweetData[i].split(":")[0];
            var body = tweetData[i].substring(tweetData[i].indexOf(":")+1);
            tweets.push(<TweetCard title={title} body={body} key={tweetData[i]}/>);
        }
        return (
            <div className="row">
                {tweets}
            </div>
        );
    },
    initREST: function(){
        var component = this;
        function setComponentState(data){
            component.setState({data: data.split("|")});
        }
        function callServiceService() {
            $.ajax({
                url: "http://localhost:8080/reactiveEE/api/twitter"
            }).then(function (data) {
                setComponentState(data);
            });
        }
        callServiceService()
    },
    initWS: function(){
        var wsUri = "ws://localhost:8080/reactiveEE/websocket";
        var webSocket;

        console.log("calling opensocket")
        openSocket();
        var component = this;
        function setComponentState(data){
            component.setState({data: data.split("|")});
        }

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
                console.log(event)
                console.log(event.data)
                console.log("got a message via the websocket");
                setComponentState(event.data);
            };

            webSocket.onclose = function(event){
                webSocket.send("Connection closed");
            };
        }

        function send(){
            webSocket.send("send function websocket js");
        }

        function closeSocket(){
            webSocket.close();
        }
    },
});

var TweetCard = React.createClass({
    render: function(){
        var title = this.props.title;
        var body = this.props.body;
        return (
            <div className="col s6 center-align section">
                <div className="card-small z-depth-3 section white darken-1 blue-text text-darken-4">
                    <span className="card-title flow-text left-align">{title}</span>
                    <div className="card-content">
                        <p>{body}</p>
                    </div>
                </div>
            </div>

        );
    }
});

ReactDOM.render(
<Row />,
    document.getElementById("container")
);
